package com.sunmnet.mediaroom.brand.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.brand.R;

/**
 * Created by dengzl_pc on 2018/3/8.
 * 自定义radiogroup
 */

public class ImageTextRadio extends LinearLayout {
    public ImageTextRadio(Context context) {
        super(context);
        this.init();
    }

    ImageView imageView;
    TextView textView;
    int normal, selected;

    public ImageTextRadio(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextRadio(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ImageTextRadio);
        this.normal = ta.getResourceId(R.styleable.ImageTextRadio_normal_drawable, 0);
        this.selected = ta.getResourceId(R.styleable.ImageTextRadio_select_drawable, 0);
        String text = ta.getString(R.styleable.ImageTextRadio_text);
        this.textView.setText(text);
        Glide.with(context).load(this.normal).into(this.imageView);
        ta.recycle();
    }

    public void setCheck(boolean isCheck) {
        int drawable = isCheck ? this.selected : this.normal;
        int color = isCheck ? getResources().getColor(R.color.controll_panel_color)
                : Color.WHITE;
        Glide.with(getContext()).load(drawable).into(this.imageView);
        this.textView.setTextColor(color);
    }

    private void init() {
        this.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.image_text_radio_item, null, false);
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(view,params);
        this.imageView = (ImageView) view.findViewById(R.id.device_image);
        this.textView = (TextView) view.findViewById(R.id.device_name);
        this.textView.setTextColor(Color.WHITE);
    }
}
