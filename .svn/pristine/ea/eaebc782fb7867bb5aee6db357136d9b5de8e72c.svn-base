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
import com.sunmnet.mediaroom.brand.bean.item.QueryExamScoreResultItem;
import com.sunmnet.mediaroom.brand.table.AnnotationParser;
import com.sunmnet.mediaroom.brand.table.FixedWidthTextDrawFormat;

import java.util.ArrayList;
import java.util.List;

//TODO 考试成绩查询接口
public class TemplateQueryExamScoreFragment extends Fragment {

    SmartTable<QueryExamScoreResultItem> smartTable;
    private AnnotationParser<QueryExamScoreResultItem> parser;
    int tableWidth;

    public static TemplateQueryExamScoreFragment newInstance() {
        Bundle args = new Bundle();
        TemplateQueryExamScoreFragment fragment = new TemplateQueryExamScoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_query_exam_score, null);
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
        List<QueryExamScoreResultItem> list = new ArrayList<>();
        QueryExamScoreResultItem item1 = new QueryExamScoreResultItem("高等数学", "78", "大学英语", "76");
        QueryExamScoreResultItem item2 = new QueryExamScoreResultItem("大学物理", "88", "毛邓", "66");
        QueryExamScoreResultItem item3 = new QueryExamScoreResultItem("计算机原理", "4", null,null);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        onGetData(list);
    }

    private void onGetData(List<QueryExamScoreResultItem> items) {
        List<Column> columnList = parser.getColumnList(QueryExamScoreResultItem.class);
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
        TableData<QueryExamScoreResultItem> tableData = new TableData<>(parser.getTableName(QueryExamScoreResultItem.class), items, columnList);
        smartTable.setTableData(tableData);
    }
}
