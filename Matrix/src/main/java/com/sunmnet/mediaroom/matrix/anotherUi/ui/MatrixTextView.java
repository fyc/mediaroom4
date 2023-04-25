package com.sunmnet.mediaroom.matrix.anotherUi.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;

/**
 * Created by dengzl_pc on 2017/12/26.
 */

public class MatrixTextView extends AutoTextView {
    public MatrixTextView(Context context, boolean isStartLeft, boolean isStarTop) {
        super(context);
        this.setGravity(Gravity.CENTER);
        this.isStartLeft=isStartLeft;
        this.isStarTop=isStarTop;
        paint=new Paint();
        paint.setColor(Color.parseColor("#CCCCCC"));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2.5f);
        setWillNotDraw(false);
    }
    boolean isStartLeft,isStarTop;
    Paint paint;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0,getHeight(),getWidth(),getHeight(),paint);
        canvas.drawLine(getWidth(),0,getWidth(),getHeight(),paint);
        if(isStarTop)
        {
            canvas.drawLine(0,0,getWidth(),0,paint);
        }
        if(isStartLeft)
        {
            canvas.drawLine(0,0,0,getHeight(),paint);
        }
    }
}
