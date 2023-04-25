package com.sunmnet.mediaroom.brand.control.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.response.WeatherResponse;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.bean.control.other.WeatherControlProperty;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class WeatherControl extends BaseFrameControl<WeatherControlProperty> {

    private View weatherView;
    private String city;
    private String province;
    private int iconStyle;
    private String color;

    private static HashMap<Integer, WeatherViewConfig> configHashMap;

    public WeatherControl(Context context) {
        super(context);
    }

    public WeatherControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public WeatherControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        if (configHashMap == null) {
            configHashMap = new HashMap<>();
            configHashMap.put(0, new Style1Config());
            configHashMap.put(1, new Style2Config());
            configHashMap.put(2, new Style3Config());
            configHashMap.put(3, new Style4Config());
        }
    }

    private void initView(WeatherViewConfig config) {
        removeAllViews();
        if (config == null)
            return;
        weatherView = LayoutInflater.from(getContext()).inflate(config.getLayoutId(), null, false);
        LayoutParams params = new LayoutParams(config.getWidth(), config.getHeight());
        params.gravity = Gravity.CENTER;
        addView(weatherView, params);

        //根据控件宽高计算缩放比例，对weatherView进行等比缩放
        float ratioW = (float) width / config.getWidth();
        float ratioH = (float) height / config.getHeight();
        float ratio = ratioH > ratioW ? ratioW : ratioH;
        weatherView.setScaleX(ratio);
        weatherView.setScaleY(ratio);

        int colorInt = ColorUtil.parseColor(color);
        if (colorInt != 0)
            setColor(colorInt);
    }

    @Override
    public void setProperty(WeatherControlProperty property) {
        super.setProperty(property);
        city = TypeUtil.objToStrNotNull(property.getAttr().getCity());
        province = TypeUtil.objToStrNotNull(property.getAttr().getProvince());
        iconStyle = TypeUtil.objToIntDef(property.getAttr().getIconStyle(), 0);
        WeatherViewConfig config = configHashMap.get(iconStyle);
        if (config == null) {
            config = configHashMap.get(0);
        }
        color = TypeUtil.objToStrNotNull(property.getStyle().getColor());
        initView(config);
    }

    @Override
    public void refreshControlData() {
        super.refreshControlData();
        if (!DeviceApp.getApp().isRegistered())
            return;
        RunningLog.run("正在请求天气数据");
        ApiManager.getSysApi().getTodayWeather("广州")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<WeatherResponse>() {
                    @Override
                    public void onNext(WeatherResponse weatherResponse) {
                        if (weatherResponse == null) {
                            RunningLog.debug("返回天气数据格式化错误");
                            return;
                        }
                        if (!weatherResponse.isSuccess() || weatherResponse.getObj() == null) {
                            RunningLog.debug("获取天气数据失败, isSuccess:" + weatherResponse.isSuccess() + " , obj: " + weatherResponse.getObj());
                            return;
                        }
                        setWeather(weatherResponse.getObj());
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.error("请求天气数据失败：" + e.getMessage());
                    }
                });
    }

    private void setWeather(WeatherResponse.Result result) {
        if (result == null)
            return;
        TextView currentTemperature = (TextView) weatherView.findViewById(R.id.currentTemperature);
        TextView temperatureUnit = (TextView) weatherView.findViewById(R.id.temperatureUnit);
        if (currentTemperature != null) {
            if (temperatureUnit != null && temperatureUnit.getVisibility() == VISIBLE) {
                String temp = TypeUtil.objToStrDef(result.getCurrentWeather().getTemperature(), "--");
                temp = temp.replace("℃", "");
                temp = temp.replace("°C", "");
                currentTemperature.setText(temp);
            } else {
                currentTemperature.setText(TypeUtil.objToStrDef(result.getCurrentWeather().getTemperature(), "--℃"));
            }
        }

        TextView weatherName = (TextView) weatherView.findViewById(R.id.weatherName);
        if (weatherName != null)
            weatherName.setText(result.getCurrentWeather().getWeather());

        TextView temperatureRange = (TextView) weatherView.findViewById(R.id.temperatureRange);
        if (temperatureRange != null)
            temperatureRange.setText(result.getGeneralWeather().getTempRange());

        String name = "file:///android_asset/weatherIcons/";

        name += result.getCurrentWeather().getWeatherCode() + ".png";

        ImageView iv = (ImageView) weatherView.findViewById(R.id.currentWeatherImage);
        if (iv != null)
            Glide.with(getContext()).load(name).into(iv);

//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                viewSaveToImage(weatherView);
//            }
//        }, 5000);

    }

    private void setColor(int color) {
        ImageView iv = (ImageView) weatherView.findViewById(R.id.currentWeatherImage);
        if (iv != null)
            iv.setColorFilter(color);

        TextView currentTemperature = (TextView) weatherView.findViewById(R.id.currentTemperature);
        if (currentTemperature != null)
            currentTemperature.setTextColor(color);

        TextView temperatureUnit = (TextView) weatherView.findViewById(R.id.temperatureUnit);
        if (temperatureUnit != null)
            temperatureUnit.setTextColor(color);

        TextView temperatureRange = (TextView) weatherView.findViewById(R.id.temperatureRange);
        if (temperatureRange != null)
            temperatureRange.setTextColor(color);

        TextView weatherName = (TextView) weatherView.findViewById(R.id.weatherName);
        if (weatherName != null)
            weatherName.setTextColor(color);
    }

    private void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        FileOutputStream fos;
        String imagePath = "";
        try {
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                File sdRoot = Environment.getExternalStorageDirectory();
                File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis() + ".png");
                fos = new FileOutputStream(file);
                imagePath = file.getAbsolutePath();
            } else
                throw new Exception("创建文件失败!");

            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("生成天气组件图片", "imagePath=" + imagePath);
        view.destroyDrawingCache();
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    public void setIconStyle(int iconStyle) {
        this.iconStyle = iconStyle;
        WeatherViewConfig config = configHashMap.get(iconStyle);
        if (config == null) {
            config = configHashMap.get(0);
        }
        initView(config);
    }

    public void setColor(String color) {
        this.color = color;
        WeatherViewConfig config = configHashMap.get(iconStyle);
        if (config == null) {
            config = configHashMap.get(0);
        }
        initView(config);
    }
}
