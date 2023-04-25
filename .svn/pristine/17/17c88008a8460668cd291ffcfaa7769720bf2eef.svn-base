package com.sunmnet.mediaroom.brand.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.sunmnet.mediaroom.brand.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundCvCameraView extends CvCameraView {

    private Paint paint;
    private float radius;
    private Path circlePath;

    public RoundCvCameraView(Context context) {
        super(context);
    }

    public RoundCvCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundCvCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
                    radius = Math.min(view.getWidth(), view.getHeight()) * 0.5f;
                    outline.setRoundRect(rect, radius);
                    circlePath = new Path();
                    circlePath.addRoundRect(new RectF(0, 0, view.getWidth(), view.getHeight()), radius, radius, Path.Direction.CW);
                }
            });
            setClipToOutline(true);
        }
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(0xFF0E91DD);
        paint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.px_6));
    }


    @Override
    public void unlockCanvasAndPost(Canvas canvas) {
        drawBorder(canvas, paint, circlePath);
        super.unlockCanvasAndPost(canvas);
    }

    private void drawBorder(Canvas canvas, Paint paint, Path path) {
        if (canvas == null || paint == null || path == null) {
            return;
        }
        canvas.drawPath(path, paint);
    }

    protected Camera.Size chooseOptimalSize(List<Camera.Size> choices, int textureViewWidth,
                                            int textureViewHeight, int maxWidth, int maxHeight) {
        List<Camera.Size> bigEnough = new ArrayList<>();
        List<Camera.Size> notBigEnough = new ArrayList<>();
        for (Camera.Size option : choices) {
            if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
                bigEnough.add(option);
            } else {
                notBigEnough.add(option);
            }
        }
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices.get(0);
        }
    }
}
