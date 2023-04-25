package com.sunmnet.mediaroom.brand.utils;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.LruCache;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextPrefixSuffix;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextStyle;
import com.sunmnet.mediaroom.brand.bean.control.base.PrefixSuffixTextControlAttr;
import com.sunmnet.mediaroom.brand.bean.control.base.BaseTextControlStyle;

import java.util.Map;


public class ControlTextHelper {

    private static LruCache<String, Typeface> typefaceLruCache = new LruCache<>(10);

    public static Typeface getFontTypeface(Context context, String font, int style) {
        Typeface typeface = null;
        if (!TextUtils.isEmpty(font)) {
            String fontLowerCase = font.toLowerCase();
            String key = fontLowerCase + "_" + style;
            typeface = typefaceLruCache.get(key);
            if (typeface == null) {
                try {
                    typeface = Typeface.create(getFontTypeface(context, fontLowerCase), style);
                    typefaceLruCache.put(key, typeface);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (typeface == null)
            typeface = Typeface.DEFAULT;
        return typeface;
    }

    public static Typeface getFontTypeface(Context context, String font) {
        Typeface typeface = null;
        if (!TextUtils.isEmpty(font)) {
            String fontLowerCase = font.toLowerCase();
            typeface = typefaceLruCache.get(fontLowerCase);
            if (typeface == null) {
                try {
                    StringBuilder path = new StringBuilder("fonts/");
                    String[] files = context.getAssets().list("fonts");
                    boolean hasFile = false;
                    for (String file : files) {
                        if (file.contains(fontLowerCase)) {
                            path.append(file);
                            hasFile = true;
                            break;
                        }
                    }
                    if (hasFile) {
                        typeface = Typeface.createFromAsset(context.getAssets(), path.toString());
                        typefaceLruCache.put(fontLowerCase, typeface);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (typeface == null)
            typeface = Typeface.DEFAULT;
        return typeface;
    }

    public static void setFont(TextView textView, String font) {
        try {
            Typeface typeface;
            if (textView.getTypeface() != null) {
                int style = textView.getTypeface().getStyle();
                typeface = getFontTypeface(textView.getContext(), font, style);
            } else {
                typeface = getFontTypeface(textView.getContext(), font);
            }
            textView.setTypeface(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setControlTextSize(TextView textView, int textSize) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (textView instanceof ITextStyle) {
            ((ITextStyle) textView).setControlTextSize(textSize);
        }
    }

    public static void setControlTextColor(TextView textView, String textColor) {
        try {
            textView.setTextColor(ColorUtil.parseColor(textColor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTextBackgroundColor(TextView textView, String textBackgroundColor) {
        if (textView instanceof ITextStyle)
            ((ITextStyle) textView).setTextBackgroundColor(textBackgroundColor);
    }

    public static void setUnderline(TextView textView, boolean underline) {
        if (underline) {
            textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            textView.getPaint().setFlags(0);
        }
        textView.invalidate();
    }

    public static void setItalic(TextView textView, boolean italic) {
        Typeface typeface = textView.getTypeface();
        if (typeface == null)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
        int textStyle = 0;
        if (typeface.isBold()) {
            textStyle = textStyle | Typeface.BOLD;
        }
        if (italic) {
            textStyle = textStyle | Typeface.ITALIC;
        }
        typeface = Typeface.create(typeface, textStyle);
        textView.setTypeface(typeface);
    }

    public static void setBold(TextView textView, boolean bold) {
        Typeface typeface = textView.getTypeface();
        if (typeface == null)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
        int textStyle = 0;
        if (bold) {
            textStyle = textStyle | Typeface.BOLD;
        }
        if (typeface.isItalic()) {
            textStyle = textStyle | Typeface.ITALIC;
        }
        typeface = Typeface.create(typeface, textStyle);
        textView.setTypeface(typeface);
    }

    public static void setAlignment(TextView textView, String alignment) {
        switch (alignment) {
            case ITextStyle.ALIGN_LEFT:
                textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case ITextStyle.ALIGN_RIGHT:
                textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            case ITextStyle.ALIGN_CENTER:
                textView.setGravity(Gravity.CENTER);
                break;
        }
    }

    public static void setPrefixText(TextView textView, String prefix) {
        if (textView instanceof ITextPrefixSuffix) {
            ITextPrefixSuffix textPrefixSuffix = (ITextPrefixSuffix) textView;
            textPrefixSuffix.setPrefixText(prefix);
        }
    }

    public static void setSuffixText(TextView textView, String suffix) {
        if (textView instanceof ITextPrefixSuffix) {
            ITextPrefixSuffix textPrefixSuffix = (ITextPrefixSuffix) textView;
            textPrefixSuffix.setSuffixText(suffix);
        }
    }


    public static void setControlTextStyle(TextView textView, Map<String, Object> data) {
        if (!data.containsKey("style") || !(data.get("style") instanceof Map))
            return;
        Map<String, Object> textStyle = TypeUtil.castToMap_SO(data.get("style"));

        setTextBackgroundColor(textView, TypeUtil.objToStr(textStyle.get("backgroundColor")));

        setControlTextSize(textView, TypeUtil.objToInt(textStyle.get("fontSize")));
        setControlTextColor(textView, TypeUtil.objToStr(textStyle.get("color")));
        setFont(textView, TypeUtil.objToStrNotNull(textStyle.get("fontFamily")));
        setUnderline(textView, TypeUtil.objToBoolean(textStyle.get("underline")));
        setItalic(textView, TypeUtil.objToBoolean(textStyle.get("italic")));
        setBold(textView, TypeUtil.objToBoolean(textStyle.get("bold")));
        setAlignment(textView, TypeUtil.objToStrDef(textStyle.get("textAlign"), ITextStyle.ALIGN_CENTER));

        if (data.containsKey("attr") && (data.get("attr") instanceof Map)) {
            Map<String, Object> attr = TypeUtil.castToMap_SO(data.get("attr"));
            setPrefixText(textView, TypeUtil.objToStrNotNull(attr.get("prefix")));
            setSuffixText(textView, TypeUtil.objToStrNotNull(attr.get("suffix")));
        }
    }

    public static void setControlTextStyle(TextView textView, BaseTextControlStyle textStyle) {
        setTextBackgroundColor(textView, textStyle.getBackgroundColor());
        setControlTextSize(textView, textStyle.getFontSize());
        setControlTextColor(textView, textStyle.getColor());
        setFont(textView, textStyle.getFontFamily());
        setUnderline(textView, textStyle.isUnderline());
        setItalic(textView, textStyle.isItalic());
        setBold(textView, textStyle.isBold());
        setAlignment(textView, textStyle.getTextAlign());
    }

    public static void setControlTextAttr(TextView textView, PrefixSuffixTextControlAttr attr) {
        setPrefixText(textView, TypeUtil.objToStrNotNull(attr.getPrefix()));
        setSuffixText(textView, TypeUtil.objToStrNotNull(attr.getSuffix()));
    }
}
