package com.sunmnet.mediaroom.brand.control.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sunmnet.mediaroom.brand.interfaces.control.ITextStyle;
import com.sunmnet.mediaroom.brand.bean.play.SignatureContent;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SingleLineRollTextControl extends TextureView implements ITextStyle, TextureView.SurfaceTextureListener {

    protected String font;
    protected String controlTextColor;
    protected String textBackgroundColor;

    protected boolean underline;
    protected boolean italic;
    protected boolean bold;

    protected String alignment = ALIGN_CENTER;

    private Paint paint = null;// 画笔

    private float rollSpeed = 1; // 滚动速度
    private String rollDirection = "RTL";//滚动方向 UTD从上到下 DTU从下到上 LTR从左到右 RTL从右到左 只做从下往上以及左右的

    private final List<SignatureContent> textList = new ArrayList<>();//文本内容列表
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

    private String nowText;

    private int fontHeight;
    private float baselineOffset;

    private float drawX, drawY;
    private RectF rectBackground = new RectF();

    private ScheduledExecutorService scheduledExecutorService; // 执行滚动线程

    private boolean isPlaying = false;

    public SingleLineRollTextControl(Context context) {
        super(context);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init();
    }

    public SingleLineRollTextControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setTypeface(Typeface.DEFAULT);
        setControlTextSize(textSize);
        paint.setColor(textColor);
        rollDirection = "RTL";
        setRollSpeed(5);
        setOpaque(false);//设置背景透明，记住这里是[是否不透明]
        setSurfaceTextureListener(this);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getText(int position) {
        SignatureContent content = textList.get(position);
        String text = content.getText();
        String signature = content.getSignature();
        StringBuffer stringBuffer = new StringBuffer(text);
        if (!TextUtils.isEmpty(signature)) {
            stringBuffer.append("\n");
            if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) { // 水平滚动
                stringBuffer.append("────").append(signature);
            } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) { // 垂直滚动
                stringBuffer.append("││││").append(signature);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 计算文本所占宽高
     *
     * @param text
     */
    private synchronized void measureText(String text) {
        if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) { // 水平滚动
            textWidth = paint.measureText(text);// 获取text的长度
            totalLength = viewWidth + textWidth + fontHeight / 2;
            switch (alignment) {
//                case ALIGN_CENTER:
//                    textAlignX = viewWidth / 2;
//                    textAlignY = viewHeight / 2;
//                case ALIGN_TOP:
//                    textAlignX = viewWidth / 2;
//                    textAlignY = fontHeight / 2 + getPaddingTop();
//                case ALIGN_BOTTOM:
//                    textAlignX = viewWidth / 2;
//                    textAlignY = viewHeight - fontHeight / 2 - getPaddingBottom();
//                    break;
                case ALIGN_CENTER:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight / 2;
                    break;
                case ALIGN_BOTTOM:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight - fontHeight - getPaddingBottom();
                    break;
                case ALIGN_TOP:
                default:
                    textAlignX = viewWidth / 2;
                    textAlignY = getPaddingTop();
                    break;
            }
        } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) { // 垂直滚动
            textHeight = fontHeight * text.length();// 获取text的长度
            totalLength = viewHeight + textHeight + fontHeight / 2;
            switch (alignment) {
                case ALIGN_CENTER:
                    textAlignX = viewWidth / 2;
                    textAlignY = viewHeight / 2;
                    break;
                case ALIGN_RIGHT:
                    textAlignX = viewWidth - fontHeight / 2 - getPaddingRight();
                    textAlignY = viewHeight / 2;
                    break;
                case ALIGN_LEFT:
                default:
                    textAlignX = fontHeight / 2 + getPaddingLeft();
                    textAlignY = viewHeight / 2;
                    break;
            }
        } else {
            textAlignY = viewHeight / 2;
            switch (alignment) {
                case ALIGN_LEFT:
                    textAlignX = fontHeight / 2 + getPaddingLeft();
                    break;
                case ALIGN_RIGHT:
                    textAlignX = viewWidth - fontHeight / 2 - getPaddingRight();
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

    public void setText(List<SignatureContent> text) {
        synchronized (textList) {
            textList.clear();
            textList.addAll(text);
            position = 0;
            if (textList.size() > 0) {
                nowText = getText(position);
                measureText(nowText);
            }
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

    private void drawText(Canvas canvas, String text) {
        if (TextUtils.isEmpty(text))
            return;
        float x = drawX;
        float y = drawY;
        if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) {
            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fm = paint.getFontMetrics();
            float offset = fm.ascent - fm.top;
            float halfFontHeight = fontHeight / 2;
            rectBackground.set(x - halfFontHeight, y - offset, x + halfFontHeight, y + fontHeight * text.length() + offset);

            paint.setColor(backgroundColor);
            canvas.drawRect(rectBackground, paint);
            paint.setColor(textColor);

            for (int j = 0; j < text.length(); j++) {
                canvas.drawText(text.charAt(j) + "", x, y + j * fontHeight + baselineOffset, paint);
            }

        } else if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) {
            Paint.FontMetrics fm = paint.getFontMetrics();
            float top = y + baselineOffset + fm.top;
            float bottom = y + baselineOffset + fm.bottom;
            float offset = fm.ascent - fm.top;
            Paint.Align align = paint.getTextAlign();
            float lineTextWidth = paint.measureText(text);
            if (align == Paint.Align.LEFT) {
                rectBackground.set(x - offset, top, x + lineTextWidth + offset, bottom);
            } else if (align == Paint.Align.RIGHT) {
                rectBackground.set(x - offset - lineTextWidth, top, x + offset, bottom);
            } else if (align == Paint.Align.CENTER) {
                rectBackground.set(x - offset - lineTextWidth / 2, top, x + lineTextWidth / 2 + offset, bottom);
            }

            paint.setColor(backgroundColor);
            canvas.drawRect(rectBackground, paint);
            paint.setColor(textColor);
            canvas.drawText(text, x, y + baselineOffset, paint);
        }
    }

    @Override
    public void setFont(String font) {
        this.font = font;
        try {
            int style = paint.getTypeface().getStyle();
            Typeface typeface = ControlTextHelper.getFontTypeface(getContext(), font);
            typeface = Typeface.create(typeface, style);
            paint.setTypeface(typeface);

            Paint.FontMetrics fm = paint.getFontMetrics();
            fontHeight = (int) Math.ceil(fm.descent - fm.ascent);
            baselineOffset = Math.abs(fm.ascent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setControlTextSize(int textSize) {
        this.textSize = textSize;
        if (paint != null) {
            paint.setTextSize(textSize);
            Paint.FontMetrics fm = paint.getFontMetrics();
            fontHeight = (int) Math.ceil(fm.descent - fm.ascent);
            baselineOffset = Math.abs(fm.ascent);
        }
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

    public void onControlShow() {
        try {
            if (scheduledExecutorService != null)
                onControlHide();
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(new RollThread(), 300,
                    100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isPlaying = true;
    }

    public void onControlHide() {
        try {
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdown();
                scheduledExecutorService.awaitTermination(1, TimeUnit.SECONDS);
                scheduledExecutorService = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isPlaying = false;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        onControlShow();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        onControlHide();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    private class RollThread implements Runnable {

        @Override
        public void run() {
            if (!isPlaying)
                return;
            synchronized (textList) {
                if ("RTL".equals(rollDirection)) {
                    drawX = viewWidth - textX;
                    drawY = textAlignY;
                    textX += rollSpeed;
                    if (textX > totalLength) {
                        textX = 0;
                        position++;
                        position = position % textList.size();
                        nowText = getText(position);
                        measureText(nowText);
                    }
                } else if ("DTU".equals(rollDirection)) {
                    drawX = textAlignX;
                    drawY = viewHeight - textY;
                    textY += rollSpeed;
                    if (textY > totalLength) {
                        textY = 0;
                        position++;
                        position = position % textList.size();
                        nowText = getText(position);
                        measureText(nowText);
                    }
                } else if ("LTR".equals(rollDirection)) {
                    drawX = viewWidth - textX;
                    drawY = textAlignY;
                    textX -= rollSpeed;
                    if (textX <= 0) {
                        textX = totalLength;
                        position++;
                        position = position % textList.size();
                        nowText = getText(position);
                        measureText(nowText);
                    }
                } else if ("UTD".equals(rollDirection)) {
                    drawX = textAlignX;
                    drawY = viewHeight - textY;
                    textY -= rollSpeed;
                    if (textY <= 0) {
                        textY = totalLength;
                        position++;
                        position = position % textList.size();
                        nowText = getText(position);
                        measureText(nowText);
                    }
                }
            }
            if (TextUtils.isEmpty(nowText) || !isPlaying)
                return;
            Canvas canvas = lockCanvas();
            if (canvas == null)
                return;
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) {
                drawText(canvas, nowText);
            } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) {
                drawText(canvas, nowText);
            }
            unlockCanvasAndPost(canvas);
        }

    }

}
