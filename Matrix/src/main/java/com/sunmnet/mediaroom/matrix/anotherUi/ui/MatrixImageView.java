package com.sunmnet.mediaroom.matrix.anotherUi.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.OnMatrixCheckListener;


/**
 * Created by dengzl_pc on 2017/12/27.
 * 矩阵设置中切换效果
 */

public class MatrixImageView extends FrameLayout implements View.OnClickListener {

    public static boolean isHasInit = false;
    boolean isStartLeft, isStarTop;
    Paint paint = null;
    int input, output;
    boolean isCheck = false;
    ImageView image;
    public MatrixImageView(Context context, boolean isStartLeft, boolean isStarTop, int input, int output) {
        super(context);
        this.isStartLeft = isStartLeft;
        this.isStarTop = isStarTop;
        paint = new Paint();
        image=new ImageView(context);
        image.setScaleType(ImageView.ScaleType.CENTER);
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(image,params);
        paint.setColor(Color.parseColor("#CCCCCC"));
        paint.setAntiAlias(true);
        setPadding(7, 7, 7, 7);
        paint.setStrokeWidth(2.5f);
        this.input = input;
        this.output = output;
        //setTag(input + "" + output);
        //getViewTreeObserver().addOnGlobalLayoutListener(new LayoutObserv(this));
        setOnClickListener(this);
        setWillNotDraw(false);
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void toggle() {
        this.setCheck(!this.isCheck);
    }

    public void setOnMatrixCheckListener(OnMatrixCheckListener listener) {
        this.listener = listener;
    }

    OnMatrixCheckListener listener;

    @Override
    public void setVisibility(int visibility) {
        switch (visibility){
            case View.INVISIBLE:
                this.image.setVisibility(View.INVISIBLE);
                break;
            case View.VISIBLE:
                this.image.setVisibility(visibility);
                super.setVisibility(visibility);
                break;
            case View.GONE:
                this.image.setVisibility(visibility);
                super.setVisibility(visibility);
                break;
        }
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
        if (this.isCheck) {
            Glide.with(getContext()).asBitmap().load(R.drawable.checked)
                    .placeholder(R.drawable.checked).dontAnimate().into(this.image);
        } else
            Glide.with(getContext()).asBitmap().load(R.drawable.unchecked)
                    .placeholder(R.drawable.unchecked).dontAnimate().into(this.image);
    }

    public int getInputInterface() {
        return this.input;
    }

    public int getOutputInterface() {
        return this.output;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);
        if (isStarTop) {
            canvas.drawLine(0, 0, getWidth(), 0, paint);
        }
        if (isStartLeft) {
            canvas.drawLine(0, 0, 0, getHeight(), paint);
        }
    }

    @Override
    public void onClick(View view) {
        this.toggle();
        if (this.listener != null) this.listener.onCheck(this.isCheck, this.input, this.output,this);
    }
}
