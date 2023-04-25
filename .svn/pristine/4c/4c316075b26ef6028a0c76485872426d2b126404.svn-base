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

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextStyle;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MultilineRollTextControl extends TextureView implements ITextStyle, TextureView.SurfaceTextureListener {

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

    private boolean hasSignature = false;
    private String nowText;

    private int fontHeight;
    private float baselineOffset;

    private float drawX, drawY;
    private RectF rectBackground = new RectF();

    private Map<String, Integer> keyMap = new HashMap<>();
    private Map<String, SoftReference<String[]>> valueMap = new HashMap<>();

    private ScheduledExecutorService scheduledExecutorService; // 执行滚动线程

    private boolean isPlaying = false;

    public MultilineRollTextControl(Context context) {
        super(context);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init();
    }

    public MultilineRollTextControl(Context context, AttributeSet attrs) {
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
        Map<String, Object> content = textList.get(position);
        String text = TypeUtil.objToStr(content.get("text"));
        String signature = TypeUtil.objToStr(content.get("signature"));
        StringBuffer stringBuffer = new StringBuffer(text);
        if (!TextUtils.isEmpty(signature)) {
            stringBuffer.append("\n");
            if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) { // 水平滚动
                stringBuffer.append("││││").append(signature);
            } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) { // 垂直滚动
                stringBuffer.append("────").append(signature);
            }
            hasSignature = true;
        } else
            hasSignature = false;
        return stringBuffer.toString();
    }

    /**
     * 计算文本所占宽高
     *
     * @param text
     */
    private synchronized void measureText(String text) {
        if (viewHeight == 0 || viewWidth == 0)
            return;
        if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) {
            // 水平滚动，计算宽度，高度适应控件高度
            String[] strings = getMultiTextStringColumn(text, viewHeight);
            textWidth = fontHeight * strings.length;
            totalLength = viewWidth + textWidth + fontHeight / 2;
            textAlignY = getPaddingTop();
            if (paint.getTextAlign() != Paint.Align.CENTER)
                paint.setTextAlign(Paint.Align.CENTER);
//            switch (alignment) {
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
//            }
        } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) {
            // 垂直滚动，计算高度，高度适应控件宽度
            String[] strings = getMultiTextString(text, viewWidth);
            textHeight = fontHeight * strings.length;
            totalLength = viewHeight + textHeight + fontHeight / 2;
            switch (alignment) {
                case ALIGN_RIGHT:
                    if (paint.getTextAlign() != Paint.Align.RIGHT)
                        paint.setTextAlign(Paint.Align.RIGHT);
                    textAlignX = viewWidth - fontHeight / 2 - getPaddingRight();
                    break;
                case ALIGN_CENTER:
                    if (paint.getTextAlign() != Paint.Align.CENTER)
                        paint.setTextAlign(Paint.Align.CENTER);
                    textAlignX = viewWidth / 2;
                    break;
                case ALIGN_LEFT:
                default:
                    if (paint.getTextAlign() != Paint.Align.LEFT)
                        paint.setTextAlign(Paint.Align.LEFT);
                    textAlignX = fontHeight / 2 + getPaddingLeft();
                    break;
            }
            textAlignY = viewHeight - fontHeight / 2 - getPaddingBottom();
        }
        if ("RTL".equals(rollDirection)) {
            textX = 0;
            drawX = viewWidth - textX;
            drawY = textAlignY;
        } else if ("LTR".equals(rollDirection)) {
            textX = viewWidth;
            drawX = viewWidth - textX;
            drawY = textAlignY;
        } else if ("DTU".equals(rollDirection)) {
            textY = 0;
            drawX = textAlignX;
            drawY = totalLength - textY;
        } else if ("UTD".equals(rollDirection)) {
            textY = totalLength;
            drawX = textAlignX;
            drawY = viewHeight - textY;
        }
    }

    public void setText(List<Map<String, Object>> text) {
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


    /**
     * 获取多行文字宽度
     *
     * @param paint
     */
    private int getMultiTextWidth(Paint paint, String[] values) {
        int maxWidth = 0;
        for (String val : values) {
            int width = (int) paint.measureText(val);
            if (maxWidth < width) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    /**
     * 获取多列文字高度
     */
    private int getMultiTextHeight(String[] values) {
        int max = 0;
        for (String val : values) {
            int height = val.length() * fontHeight;
            if (max < height) {
                max = height;
            }
        }
        return max;
    }

    /**
     * 自动计算换行/列，返回分好行/列的字符串数组
     *
     * @param string     要绘制的文字
     * @param rectLength 画布绘制文字的长度，宽度或高度
     * @return
     */
    private String[] getMultiTextString(String string, int rectLength) {
        String[] values = null;
        if (keyMap.get(string) != null && keyMap.get(string) == rectLength) {
            if (valueMap.get(string) != null) {
                values = valueMap.get(string).get();
            }
        }
        if (values == null) {
            values = string.split("\n");
            int textWidth = getMultiTextWidth(paint, values);
            if (textWidth > rectLength) {//需要计算换行
                int lengthPerRow = rectLength / fontHeight;
                List<String> list = new ArrayList<>();
                for (String value : values) {
                    if (value.length() <= lengthPerRow) {
                        list.add(value);
                    } else {
                        int row = (int) Math.ceil(value.length() / (float) lengthPerRow);
                        for (int j = 0; j < row; j++) {
                            int start = j * lengthPerRow;
                            int end = (j + 1) * lengthPerRow;
                            if (end > value.length()) {
                                end = value.length();
                            }
                            list.add(value.substring(start, end));
                        }
                    }
                }
                values = new String[list.size()];
                values = list.toArray(values);
                valueMap.put(string, new SoftReference<String[]>(values));
                keyMap.put(string, rectLength);
            }
        }
        return values;
    }

    /**
     * 纵列换列计算
     */
    private String[] getMultiTextStringColumn(String string, int rectLength) {
        String[] values = null;
        if (keyMap.get(string) != null && keyMap.get(string) == rectLength) {
            if (valueMap.get(string) != null) {
                values = valueMap.get(string).get();
            }
        }
        if (values == null) {
            values = string.split("\n");
            int textWidth = getMultiTextHeight(values);
            if (textWidth > rectLength) {//需要计算换列
                int lengthPerRow = rectLength / fontHeight;
                List<String> list = new ArrayList<>();
                for (String value : values) {
                    if (value.length() <= lengthPerRow) {
                        list.add(value);
                    } else {
                        int row = (int) Math.ceil(value.length() / (float) lengthPerRow);
                        for (int j = 0; j < row; j++) {
                            int start = j * lengthPerRow;
                            int end = (j + 1) * lengthPerRow;
                            if (end > value.length()) {
                                end = value.length();
                            }
                            list.add(value.substring(start, end));
                        }
                    }
                }
                values = new String[list.size()];
                values = list.toArray(values);
                valueMap.put(string, new SoftReference<String[]>(values));
                keyMap.put(string, rectLength);
            }
        }
        return values;
    }

    private void drawText(Canvas canvas, String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            String text = strings[i];
            if (text.length() == 0)
                continue;
            float x = drawX;
            float y = drawY;
            if ("RTL".equals(rollDirection)) {
                x = drawX + i * fontHeight;
            }
            if ("LTR".equals(rollDirection)) {
                x = drawX - i * fontHeight;
            }
            if ("DTU".equals(rollDirection)) {
                y = drawY + i * fontHeight;
            }
            if ("UTD".equals(rollDirection)) {
                y = drawY - i * fontHeight;
            }
            if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) {
                paint.setTextAlign(Paint.Align.CENTER);
                Paint.FontMetrics fm = paint.getFontMetrics();

                if (i == strings.length - 1 && hasSignature) {
                    y = viewHeight - fontHeight * (text.length()) - textAlignY;
                }

                float offset = fm.ascent - fm.top;

                float halfFontHeight = fontHeight / 2;
                rectBackground.set(x - halfFontHeight, y - offset, x + halfFontHeight, y + fontHeight * text.length() + offset);

                paint.setColor(backgroundColor);
                canvas.drawRect(rectBackground, paint);
                paint.setColor(textColor);

                for (int j = 0; j < text.length(); j++) {
                    canvas.drawText(text.charAt(j) + "", x, y + j * fontHeight + baselineOffset, paint);
                }

            } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) {
                Paint.FontMetrics fm = paint.getFontMetrics();
                float top = y + baselineOffset + fm.top;
                float bottom = y + baselineOffset + fm.bottom;
                float offset = fm.ascent - fm.top;
                float lineTextWidth = paint.measureText(text);
                Paint.Align align = paint.getTextAlign();
                if (align == Paint.Align.LEFT) {
                    rectBackground.set(x - offset, top, x + lineTextWidth + offset, bottom);
                } else if (align == Paint.Align.RIGHT) {
                    rectBackground.set(x - offset - lineTextWidth, top, x + offset, bottom);
                } else if (align == Paint.Align.CENTER) {
                    rectBackground.set(x - offset - lineTextWidth / 2, top, x + lineTextWidth / 2 + offset, bottom);
                }

                if (i == strings.length - 1 && hasSignature) {
                    x = viewWidth - fontHeight / 2 - getPaddingRight();
                    paint.setTextAlign(Paint.Align.RIGHT);
                    paint.setColor(backgroundColor);
                    rectBackground.set(x - offset - lineTextWidth, top, x + offset, bottom);
                    canvas.drawRect(rectBackground, paint);
                    paint.setColor(textColor);
                    canvas.drawText(text, x, y + baselineOffset, paint);

                    paint.setTextAlign(align);
                } else {
                    paint.setColor(backgroundColor);
                    canvas.drawRect(rectBackground, paint);
                    paint.setColor(textColor);
                    canvas.drawText(text, x, y + baselineOffset, paint);
                }
            }
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
                        position++;
                        position = position % textList.size();
                        nowText = getText(position);
                        measureText(nowText);
                    }
                } else if ("LTR".equals(rollDirection)) {
                    drawX = viewWidth - textX;
                    drawY = textAlignY;
                    textX -= rollSpeed;
                    if (textX < 0 - textWidth) {
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
                        position++;
                        position = position % textList.size();
                        nowText = getText(position);
                        measureText(nowText);
                    }
                } else if ("UTD".equals(rollDirection)) {
                    drawX = textAlignX;
                    drawY = viewHeight - textY;
                    textY -= rollSpeed;
                    if (textY < 0 - textHeight) {
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
            if (canvas == null) {
                return;
            }
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if ("RTL".equals(rollDirection) || "LTR".equals(rollDirection)) {
                drawText(canvas, getMultiTextStringColumn(nowText, viewHeight));
            } else if ("DTU".equals(rollDirection) || "UTD".equals(rollDirection)) {
                drawText(canvas, getMultiTextString(nowText, viewWidth));
            }
            unlockCanvasAndPost(canvas);
        }
    }

}
