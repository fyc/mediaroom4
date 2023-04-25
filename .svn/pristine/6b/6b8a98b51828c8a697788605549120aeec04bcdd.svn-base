package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.QueryExamRoomResultItem;
import com.sunmnet.mediaroom.brand.table.AnnotationParser;
import com.sunmnet.mediaroom.brand.table.FixedWidthTextDrawFormat;

import java.util.ArrayList;
import java.util.List;

//TODO 考场查询接口
public class TemplateQueryExamRoomFragment extends Fragment {


    SmartTable<QueryExamRoomResultItem> smartTable;
    private AnnotationParser<QueryExamRoomResultItem> parser;
    int tableWidth;

    public static TemplateQueryExamRoomFragment newInstance() {
        Bundle args = new Bundle();
        TemplateQueryExamRoomFragment fragment = new TemplateQueryExamRoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_query_exam_room, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTable(view);
    }

    protected void initTable(View view) {
        smartTable = new SmartTable<>(getContext());
        smartTable.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        smartTable.getConfig().setShowColumnTitle(true);
        smartTable.getConfig().setShowTableTitle(false);
        smartTable.getConfig().setShowXSequence(false);
        smartTable.getConfig().setShowYSequence(false);
        smartTable.getConfig().setHorizontalPadding(0);
        smartTable.getConfig().setVerticalPadding(20);
        smartTable.getConfig().getPaint().setAntiAlias(true);
        smartTable.getConfig().setContentStyle(new FontStyle(20, 0xffffffff));
        smartTable.getConfig().setColumnTitleStyle(new FontStyle(22, 0xffffffff));

        parser = new AnnotationParser<>(smartTable.getConfig().dp10);

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.contentLayout);
        frameLayout.removeAllViews();
        frameLayout.addView(smartTable);
        frameLayout.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                tableWidth = frameLayout.getWidth();
                smartTable.getShowRect().width();
                frameLayout.getViewTreeObserver().removeOnDrawListener(this);
                initTestData();
            }
        });

        smartTable.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(0x7f61A5E0));
        smartTable.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                return 0x7F7F8499;
            }
        });
    }

    private void initTestData() {
        List<QueryExamRoomResultItem> list = new ArrayList<>();
        QueryExamRoomResultItem item1 = new QueryExamRoomResultItem("2020-06-24", "9:00-11:00", "高等数学", "教一201", "40");
        QueryExamRoomResultItem item2 = new QueryExamRoomResultItem("2020-06-24", "14:00-16:00", "大学英语", "教一201", "40");
        QueryExamRoomResultItem item3 = new QueryExamRoomResultItem("2020-06-25", "9:00-11:00", "近现代史纲要", "教一201", "40");
        QueryExamRoomResultItem item4 = new QueryExamRoomResultItem("2020-06-25", "14:00-16:00", "大学物理", "教一201", "40");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        onGetData(list);
    }

    private void onGetData(List<QueryExamRoomResultItem> items) {
        List<Column> columnList = parser.getColumnList(QueryExamRoomResultItem.class);
        int width = tableWidth / columnList.size();
        int remainder = tableWidth % columnList.size();
        FixedWidthTextDrawFormat<String> format = new FixedWidthTextDrawFormat<>(width);
        FixedWidthTextDrawFormat<String> format2 = new FixedWidthTextDrawFormat<>(width + 1);
        for (int i = 0; i < columnList.size(); i++) {
            Column column = columnList.get(i);
            if (remainder > 0) {
                column.setDrawFormat(format2);
                remainder--;
            } else {
                column.setDrawFormat(format);
            }
        }
        TableData<QueryExamRoomResultItem> tableData = new TableData<>(parser.getTableName(QueryExamRoomResultItem.class), items, columnList);
        smartTable.setTableData(tableData);
    }
}
