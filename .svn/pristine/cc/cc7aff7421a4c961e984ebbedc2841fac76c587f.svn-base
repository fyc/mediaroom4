package com.sunmnet.mediaroom.brand.control.table;

import android.content.Context;
import android.util.AttributeSet;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.bean.TableCell;
import com.sunmnet.mediaroom.brand.bean.control.table.BaseCourseTableControlProperty;

/**
 * 课程表控件
 */
public class CourseTableControl<P extends BaseCourseTableControlProperty> extends BaseTableControl<P> {

    //课程表行列表示变换 courseHeaderRow课节数据排列为一行  courseHeaderCol课节数据排列为一列
    protected String direction = CourseTableDirection.COURSE_HEADER_COL.getValue();

    //单课节single 双课节double
    protected String courseStyle = CourseTableStyle.DOUBLE.getValue();

    public CourseTableControl(Context context) {
        super(context);
    }

    public CourseTableControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public CourseTableControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        direction = TypeUtil.objToStrDef(attrDataMap.get("direction"), CourseTableDirection.COURSE_HEADER_COL.getValue());
//        courseStyle = TypeUtil.objToStrDef(attrDataMap.get("courseStyle"), CourseTableStyle.DOUBLE.getValue());
//    }

    @Override
    public void setProperty(P property) {
        super.setProperty(property);
        direction = TypeUtil.objToStrDef(property.getAttr().getDirection(), CourseTableDirection.COURSE_HEADER_COL.getValue());
        courseStyle = TypeUtil.objToStrDef(property.getAttr().getCourseStyle(), CourseTableStyle.DOUBLE.getValue());
    }

    @Override
    protected void onCreateTableData(TableCell[][] tableCells) {
        if (CourseTableDirection.COURSE_HEADER_COL.getValue().equalsIgnoreCase(direction)) {
            loadCourseDataCol();
        } else {
            loadCourseDataRow();
        }
    }

    /**
     * 加载数据，课节数据排列为列
     */
    protected void loadCourseDataCol() {

    }

    /**
     * 加载数据，课节数据排列为行
     */
    protected void loadCourseDataRow() {

    }

}
