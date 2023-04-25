package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.control.media.PictureControlAttr;
import com.sunmnet.mediaroom.brand.bean.event.RefreshControlEvent;
import com.sunmnet.mediaroom.brand.bean.play.Template1Config;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.control.media.PictureControl;
import com.sunmnet.mediaroom.brand.control.text.ClassNoControl;
import com.sunmnet.mediaroom.brand.control.weather.WeatherControl;
import com.sunmnet.mediaroom.brand.data.file.FileConstant;
import com.sunmnet.mediaroom.brand.fragment.template.dialog.TemplateFaceAttendanceDialog;
import com.sunmnet.mediaroom.brand.fragment.template.dialog.LoginFragmentDialog;
import com.sunmnet.mediaroom.brand.fragment.template.dialog.LoginResultFragmentDialog;
import com.sunmnet.mediaroom.brand.fragment.template.dialog.TemplateSwipeCardAttendanceDialog;
import com.sunmnet.mediaroom.brand.utils.ControlBaseHelper;
import com.sunmnet.mediaroom.brand.utils.UrlUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.util.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Template1Fragment extends Fragment implements View.OnClickListener {

    private String resourcePath;
    private Template1Config config;

    public static Template1Fragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putString("path", path);
        Template1Fragment fragment = new Template1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resourcePath = getArguments().getString("path");
            File file = new File(resourcePath, FileConstant.TEMPLATE_PROGRAM_CONFIG_FILE_NAME);
            String json = FileUtils.readFile(file, "utf-8", "").toString();
            config = JacksonUtil.jsonStrToBean(json, Template1Config.class);
        }
    }

    private ViewGroup contentLayout;
    private TextView text;
    private TextView sign;
    private ImageView logo;

    private Button btn_face_uname, btn_face_login, btn_swipe_card_login;

    private PictureControl pictureControl;
    private WeatherControl weatherControl;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        text = (TextView) view.findViewById(R.id.text);
        sign = (TextView) view.findViewById(R.id.sign);
        logo = (ImageView) view.findViewById(R.id.logo);

        pictureControl = (PictureControl) view.findViewById(R.id.picture);
        pictureControl.setResourcePath(resourcePath);

        weatherControl = (WeatherControl) view.findViewById(R.id.weatherControl);
        btn_face_uname = (Button) view.findViewById(R.id.btn_face_uname);
        btn_face_login = (Button) view.findViewById(R.id.btn_face_login);
        btn_swipe_card_login = (Button) view.findViewById(R.id.btn_swipe_card_login);
        contentLayout = (ViewGroup) view.findViewById(R.id.contentLayout);
        btn_face_uname.setOnClickListener(this);
        btn_face_login.setOnClickListener(this);
        btn_swipe_card_login.setOnClickListener(this);

        ClassNoControl classNoControl = view.findViewById(R.id.class_no);
        classNoControl.setShowType(1);
        classNoControl.setPrefixText("第");
        classNoControl.setSuffixText("节");

        initView();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }


    private void initView() {
        refreshConfig();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshControl(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshControl(RefreshControlEvent event) {
        ControlBaseHelper.refreshControlRecursion(contentLayout);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_face_login) {
            Fragment fragment = getFragmentManager().findFragmentByTag("attend");
            if (fragment != null)
                return;
            TemplateFaceAttendanceDialog fragmentDialog = new TemplateFaceAttendanceDialog();
            fragmentDialog.show(getFragmentManager(), "attend");
        } else if (v.getId() == R.id.btn_swipe_card_login) {
            Fragment fragment = getFragmentManager().findFragmentByTag("swipeCardAttend");
            if (fragment != null)
                return;
            TemplateSwipeCardAttendanceDialog fragmentDialog = new TemplateSwipeCardAttendanceDialog();
            fragmentDialog.show(getFragmentManager(), "swipeCardAttend");
        } else {
//            Intent intent = new Intent(getContext(), TemplateActivity2.class);
//            startActivity(intent);
            LoginFragmentDialog fragment = new LoginFragmentDialog();
            fragment.setLoginListener(new LoginFragmentDialog.LoginListener() {
                @Override
                public void onLoginSuccess(LoginUser loginResult) {
                    if (loginResult.getRights().isEmpty()) {
                        if (!isDetached() && getContext() != null) {
                            ToastUtil.show(getContext(), "登录用户无相关权限");
                        }
                    } else {
                        LoginResultFragmentDialog.newInstance(loginResult).show(getFragmentManager(), "result");
                    }
                }
            });
            fragment.show(getFragmentManager(), "login");
        }
    }

    public void refreshConfig() {
        if (config != null) {
            text.setText(config.getText());
            if (TextUtils.isEmpty(config.getSignature())) {
                sign.setText("");
            } else {
                sign.setText("———— " + config.getSignature());
            }
            List<String> picture = config.getPicture();
            if (picture != null) {
                ArrayList<PictureControlAttr.FilesBean> pictureList = new ArrayList<>();
                for (String path : picture) {
                    PictureControlAttr.FilesBean filesBean = new PictureControlAttr.FilesBean(path);
                    pictureList.add(filesBean);
                }
                pictureControl.setFiles(pictureList);
            }

            String logoPath = config.getLogo();
            String logoUrl = "";
            if (logoPath != null) {
                if (UrlUtil.checkFullUrl(logoPath)) {
                    logoUrl = logoPath;
                } else {
                    if (logoPath.startsWith("/") || logoPath.startsWith("\\"))
                        logoUrl = resourcePath + logoPath.substring(1);
                    else
                        logoUrl = resourcePath + logoPath;
                }
            }
            logoUrl = logoUrl.replaceAll("\\\\", "/");
            Glide.with(getContext()).load(logoUrl).diskCacheStrategy(DiskCacheStrategy.DATA).into(logo);
            weatherControl.setControlWidth(135);
            weatherControl.setControlHeight(45);
            weatherControl.setIconStyle(2);
            weatherControl.setColor("#FFFFFFFF");
        }
    }

}
