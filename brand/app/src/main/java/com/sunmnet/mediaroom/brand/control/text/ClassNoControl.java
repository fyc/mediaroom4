package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ClassNoControlProperty;
import com.sunmnet.mediaroom.brand.utils.NumberChangeToChinese;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 课节组件
 */
public class ClassNoControl extends PrefixSuffixTextControl<ClassNoControlProperty> {

    private final static Map<Integer, String> integerStringMap = new HashMap<Integer, String>();

    private String classNo;//匹配课节，0为自动匹配，其余为匹配对应课节，双课节用逗号隔开

    private String connect;//双课节连接符

    private int classType;//1 单课节 2双课节

    private int showType;//0:中文;1阿拉伯数字

    public ClassNoControl(Context context) {
        super(context);
    }

    public ClassNoControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ClassNoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClassNoControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        if (classNo == null) {
            classNo = "0";
        }
        if (connect == null) {
            connect = "";
        }
        if (classType == 0) {
            classType = 1;
        }
        super.init();
    }

    @Override
    public void refreshControlData() {
        //根据匹配课节,课程表里的课节时间，当前时间，和控件属性显示数据
        if (CourseHelper.getDefault().isLoaded()) {
            String[] clsNoArr = classNo.split(",");
            if (clsNoArr.length == 1 && TypeUtil.objToInt(clsNoArr[0]) == 0) {//自动匹配
                CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(new Date(), 0, "0", CourseConfig.PRE_START_TIME);
                if (courseSchedule != null) {
                    String clsNo = courseSchedule.getClassNo();//课节
                    int clsNoInt = TypeUtil.objToInt(clsNo);
                    if (classType == 1) {//单课节
                        String[] arr = new String[1];
                        arr[0] = clsNo;
                        setText(getClassNoText(arr));
                    } else {//双课节
                        String[] arr = new String[2];
                        if (clsNoInt % 2 == 0) {//偶数课节
                            arr[0] = "" + (clsNoInt - 1);
                            arr[1] = "" + clsNo;
                        } else {//奇数课节
                            arr[0] = "" + clsNo;
                            arr[1] = "" + (clsNo + 1);
                        }
                        setText(getClassNoText(arr));
                    }
                } else
                    setText(null);
            } else {//固定课节
                setText(getClassNoText(clsNoArr));
            }
        } else
            setText(null);
    }

    private String getClassNoText(String[] classNo) {
        StringBuffer stringBuffer = new StringBuffer();
        if (showType == 0) {//中文
            NumberChangeToChinese numToChinese = new NumberChangeToChinese();
            for (int i = 0; i < classNo.length; i++) {
                classNo[i] = numToChinese.numberToChinese(TypeUtil.objToInt(classNo[i]));
            }
        }
        try {
//            stringBuffer.append("第");
            stringBuffer.append(classNo[0]);
            if (classType == 2) {
                stringBuffer.append(connect);
                stringBuffer.append(classNo[1]);
            }
//            stringBuffer.append("节");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        classNo = TypeUtil.objToStrNotNull(attrDataMap.get("classNo"));
//        showType = TypeUtil.objToInt(attrDataMap.get("showType"));
//        classType = TypeUtil.objToIntDef(attrDataMap.get("classType"), 1);
//        connect = TypeUtil.objToStrNotNull(attrDataMap.get("connect"));
//    }

    @Override
    public void setProperty(ClassNoControlProperty property) {
        super.setProperty(property);
        classNo = property.getAttr().getClassNo();
        showType = TypeUtil.objToInt(property.getAttr().getShowType());
        classType = TypeUtil.objToIntDef(property.getAttr().getClassType(), 1);
        connect = TypeUtil.objToStrNotNull(property.getAttr().getConnect());
    }

    public String getClassNo() {
        return classNo;
    }

    public String getConnect() {
        return connect;
    }

    public int getClassType() {
        return classType;
    }

    public int getShowType() {
        return showType;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
