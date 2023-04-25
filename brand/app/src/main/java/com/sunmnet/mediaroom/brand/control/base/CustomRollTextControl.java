package com.sunmnet.mediaroom.brand.control.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextStyle;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomRollTextControl extends View implements ITextStyle {

    protected String font;
    protected String controlTextColor;
    protected String textBackgroundColor;

    protected boolean underline;
    protected boolean italic;
    protected boolean bold;

    protected String alignment = ALIGN_CENTER;

    private Paint paint = null;// 画笔

    private float rollSpeed = 1; // 滚动速度
    private String rollDirection = "RTL";//滚动方向 UTD从上到下 DTU从下到上 LTR从左到右 RTL从右到左


    private final List<Map<String, Object>> textList = new ArrayList<>();//文本内容列表
    private int position = 0;//当前显示的文本列表位置

    private int textSize = 30; // 字号
    private int textColor = Color.BLACK; // 文字颜色
    private int backgroundColor = Color.TRANSPARENT; // 文字背景颜色

    private int viewWidth = 0;// 控件的长度
    private int viewHeight = 0; // 控件的高度
    private float textWidth = 0f;// 水平滚动时的文本长度
    private float textHeight = 0f; // 垂直滚动时的文本高度

    private float textAlignX = 0f;// 对齐横坐标
    private float textAlignY = 0f;// 对齐纵坐标

    private float textX = 0f;// 文字的横坐标
    private float textY = 0f;// 文字的纵坐标
    private float totalLength = 0.0f;// 显示总长度

    private float drawX, drawY;

    private ScheduledExecutorService scheduledExecutorService; // 执行滚动线程


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public CustomRollTextControl(Context context) {
        super(context);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init();
    }

    public CustomRollTextControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(textSize);
        rollDirection = "RTL";
        setRollSpeed(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);//得到控件的宽度
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);//得到控件的高度
        try {
            if (textList.size() > 0) {
                position = position % textList.size();
                measureText(getText(position));
                if ("RTL".equals(rollDirection)) {
                    drawX = viewWidth - textX;
                    drawY = textAlignY;
                    textX = 0;
                } else if ("DTU".equals(rollDirection)) {
                    drawX = textAlignX;
                    drawY = viewHeight - textY;
                    textY = 0;
                } else if ("LTR".equals(rollDirection)) {
                    drawX = viewWidth - textX;
                    drawY = textAlignY;
                    textX = totalLength;
                } else if ("UTD".equals(rollDirection)) {
                    drawX = textAlignX;
                    drawY = viewHeight - textY;
                    textY = totalLength;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getText(int position) {
        Map<String, Object> content = textList.get(position);
        String text = TypeUtil.objToStr(content.get("text"));
        String signature = TypeUtil.objToStr(content.get("signature"));
        StringBuffer stringBuffer = new StringBuffer(text);
        if (!TextUtils.isEmpty(signature)) {
            stringBuffer.append("\n").append("  ");
            if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) { // 水平滚动
                stringBuffer.append("────").append(signature);
            } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) { // 垂直滚动
                stringBuffer.append("││││").append(signature);
            }
        }
        return stringBuffer.toString();
    }

    private synchronized void measureText(String text) {
        if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) { // 水平滚动
            textWidth = paint.measureText(text);// 获取text的长度
            totalLength = viewWidth + textWidth + getFontHeight() / 2;
            switch (alignment) {
                case ALIGN_CENTER:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight / 2;
                case ALIGN_TOP:
                    textAlignX = viewWidth / 2;
                    textAlignY = getFontHeight() / 2 + getPaddingTop();
                case ALIGN_BOTTOM:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight - getFontHeight() / 2 - getPaddingBottom();
                    break;
            }
        } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) { // 垂直滚动
            textHeight = getFontHeight() * text.length();// 获取text的长度
            totalLength = viewHeight + textHeight + getFontHeight() / 2;
            switch (alignment) {
                case ALIGN_LEFT:
                    textAlignX = getFontHeight() / 2 + getPaddingLeft();
                    textAlignY = viewHeight / 2;
                    break;
                case ALIGN_RIGHT:
                    textAlignX = viewWidth - getFontHeight() / 2 - getPaddingRight();
                    textAlignY = viewHeight / 2;
                    break;
                case ALIGN_CENTER:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight / 2;
                    break;
            }
        } else {
            textAlignY = viewHeight / 2;
            switch (alignment) {
                case ALIGN_LEFT:
                    textAlignX = getFontHeight() / 2 + getPaddingLeft();
                    break;
                case ALIGN_RIGHT:
                    textAlignX = viewWidth - getFontHeight() / 2 - getPaddingRight();
                    break;
                case ALIGN_CENTER:
                    textAlignX = viewWidth / 2;
                    break;
            }
        }
        if ("RTL".equals(rollDirection)) {
            drawX = viewWidth - textX;
            drawY = textAlignY;
            textX = 0;
        } else if ("DTU".equals(rollDirection)) {
            drawX = textAlignX;
            drawY = viewHeight - textY;
            textY = 0;
        } else if ("LTR".equals(rollDirection)) {
            drawX = viewWidth - textX;
            drawY = textAlignY;
            textX = totalLength;
        } else if ("UTD".equals(rollDirection)) {
            drawX = textAlignX;
            drawY = viewHeight - textY;
            textY = totalLength;
        }
    }

    // 获取字体高度
    private int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    // 获取字体高度
    private int getFontHeight() {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }


    public void setText(List<Map<String, Object>> text) {
        synchronized (textList) {
            textList.clear();
            textList.addAll(text);
            position = 0;
            if (textList.size() > 0)
                measureText(getText(position));
        }
    }

    public void setRollSpeed(float rollSpeed) {
        synchronized (textList) {
            if (rollSpeed > 0) {
                float s = (int) rollSpeed;
                if (getContext() != null) {
                    float scale = getContext().getResources().getDisplayMetrics().density;
                    s = rollSpeed * scale;
                    s = s / 5;
                }
                this.rollSpeed = s;
            }
        }
    }

    public void setRollDirection(String rollDirection) {
        synchronized (textList) {
            if ("LTR".equals(rollDirection) || "RTL".equals(rollDirection) || "UTD".equals(rollDirection) || "DTU".equals(rollDirection))
                this.rollDirection = rollDirection;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        synchronized (textList) {
            String text = "";
            try {
                position = position % textList.size();
                text = getText(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(text))
                return;
            if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) {
                paint.setTextAlign(Paint.Align.LEFT);
                Paint.FontMetrics fm = paint.getFontMetrics();
                float top = drawY + fm.top;
                float bottom = drawY + fm.bottom;
                rectBackground.set(drawX, top, drawX + textWidth, bottom);
                paint.setColor(backgroundColor);
                canvas.drawRect(rectBackground, paint);
                paint.setColor(textColor);
                canvas.drawText(text, drawX, drawY, paint);
            } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) {
                paint.setTextAlign(Paint.Align.CENTER);
                Paint.FontMetrics fm = paint.getFontMetrics();
                float offset = fm.ascent - fm.top;
                float offset2 = getFontHeight() / 2;
                rectBackground.set(drawX - offset2, drawY - offset * 2, drawX + offset2, drawY + textHeight + offset * 2);
                paint.setColor(backgroundColor);
                canvas.drawRect(rectBackground, paint);
                paint.setColor(textColor);
                for (int i = 0; i < text.length(); i++) {
                    canvas.drawText(text.charAt(i) + "", drawX, drawY + (i + 1)
                            * getFontHeight(), paint);
                }
            }
        }
    }

    RectF rectBackground = new RectF();

    @Override
    public void setFont(String font) {
        this.font = font;
        try {
            int style = paint.getTypeface().getStyle();
            Typeface typeface = ControlTextHelper.getFontTypeface(getContext(), font);
            typeface = Typeface.create(typeface, style);
            paint.setTypeface(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setControlTextSize(int textSize) {
        this.textSize = textSize;
        if (paint != null)
            paint.setTextSize(textSize);
    }

    @Override
    public void setControlTextColor(String textColor) {
        this.controlTextColor = textColor;
        try {
            this.textColor = ColorUtil.parseColor(textColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTextBackgroundColor(String textBackgroundColor) {
        this.textBackgroundColor = textBackgroundColor;
        try {
            backgroundColor = ColorUtil.parseColor(textBackgroundColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUnderline(boolean underline) {
        this.underline = underline;
        if (underline) {
            paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            paint.setFlags(0);
        }
        invalidate();
    }

    @Override
    public void setItalic(boolean italic) {
        this.italic = italic;
        Typeface typeface = paint.getTypeface();
        int textStyle = 0;
        if (typeface.isBold()) {
            textStyle = textStyle | Typeface.BOLD;
        }
        if (italic) {
            textStyle = textStyle | Typeface.ITALIC;
        }
        typeface = Typeface.create(typeface, textStyle);
        paint.setTypeface(typeface);
    }

    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
        Typeface typeface = paint.getTypeface();
        int textStyle = 0;
        if (bold) {
            textStyle = textStyle | Typeface.BOLD;
        }
        if (typeface.isItalic()) {
            textStyle = textStyle | Typeface.ITALIC;
        }
        typeface = Typeface.create(typeface, textStyle);
        paint.setTypeface(typeface);
    }

    @Override
    public void setAlignment(String alignment) {
        this.alignment = alignment;
        if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) { // 水平滚动
            switch (alignment) {
                case ALIGN_CENTER:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight / 2;
                case ALIGN_TOP:
                    textAlignX = viewWidth / 2;
                    textAlignY = 0;
                case ALIGN_BOTTOM:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight;
                    break;
            }
        } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) { // 垂直滚动
            switch (alignment) {
                case ALIGN_LEFT:
                    textAlignX = getFontHeight() / 2 + getPaddingLeft();
                    textAlignY = viewHeight / 2;
                    break;
                case ALIGN_RIGHT:
                    textAlignX = viewWidth - getFontHeight() / 2 - getPaddingRight();
                    textAlignY = viewHeight / 2;
                    break;
                case ALIGN_CENTER:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight / 2;
                    break;
            }
        } else {
            textAlignY = viewHeight / 2;
            switch (alignment) {
                case ALIGN_LEFT:
                    textAlignX = getFontHeight() / 2;
                    break;
                case ALIGN_RIGHT:
                    textAlignX = viewWidth - getFontHeight() / 2;
                    break;
                case ALIGN_CENTER:
                    textAlignX = viewWidth / 2;
                    break;
            }
        }
    }

    @Override
    public String getFont() {
        return font;
    }

    @Override
    public int getControlTextSize() {
        return textSize;
    }

    @Override
    public String getControlTextColor() {
        return controlTextColor;
    }

    @Override
    public String getTextBackgroundColor() {
        return textBackgroundColor;
    }

    @Override
    public boolean isUnderline() {
        return underline;
    }

    @Override
    public boolean isItalic() {
        return italic;
    }

    @Override
    public boolean isBold() {
        return bold;
    }

    @Override
    public String getAlignment() {
        return alignment;
    }

    @Override
    protected void onAttachedToWindow() {
        onControlShow();
        super.onAttachedToWindow();
    }

    public void onControlShow() {
        if (scheduledExecutorService != null)
            onControlHide();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new RollThread(), 1000,
                20, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onDetachedFromWindow() {
        onControlHide();
        super.onDetachedFromWindow();
    }

    public void onControlHide() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    class RollThread implements Runnable {

        @Override
        public void run() {
            synchronized (textList) {
                if ("RTL".equals(rollDirection)) {
                    drawX = viewWidth - textX;
                    drawY = textAlignY;
                    textX += rollSpeed;
                    if (textX > totalLength) {
                        textX = 0;
                        position++;
                        position = position % textList.size();
                        measureText(getText(position));
                    }
                } else if ("DTU".equals(rollDirection)) {
                    drawX = textAlignX;
                    drawY = viewHeight - textY;
                    textY += rollSpeed;
                    if (textY > totalLength) {
                        textY = 0;
                        position++;
                        position = position % textList.size();
                        measureText(getText(position));
                    }
                } else if ("LTR".equals(rollDirection)) {
                    drawX = viewWidth - textX;
                    drawY = textAlignY;
                    textX -= rollSpeed;
                    if (textX <= 0) {
                        textX = totalLength;
                        position++;
                        position = position % textList.size();
                        measureText(getText(position));
                    }
                } else if ("UTD".equals(rollDirection)) {
                    drawX = textAlignX;
                    drawY = viewHeight - textY;
                    textY -= rollSpeed;
                    if (textY <= 0) {
                        textY = totalLength;
                        position++;
                        position = position % textList.size();
                        measureText(getText(position));
                    }
                }
            }
            try {
                final Handler handler = getHandler();
                if (handler != null) {
                    handler.post(runnable);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
