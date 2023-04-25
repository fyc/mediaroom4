package com.sunmnet.mediaroom.brand.fragment.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.TableData;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.api.impl.InformationApi;
import com.sunmnet.mediaroom.brand.bean.item.QueryExamRoomResultItem;
import com.sunmnet.mediaroom.brand.bean.item.SpinnerItem;
import com.sunmnet.mediaroom.brand.bean.response.QueryExamRoomResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.table.AnnotationParser;
import com.sunmnet.mediaroom.brand.table.FixedWidthTextDrawFormat;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;
import com.sunmnet.mediaroom.brand.utils.SoftKeyboardUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class QueryExamRoomDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner searchTypeSpinner;
    private EditText editText;
    private TextView btnSearch, btnClear;
    private FrameLayout tableLayout;
    private SmartTable<QueryExamRoomResultItem> table;
    private AnnotationParser<QueryExamRoomResultItem> parser;
    private int tableWidth;

    private TextView textExamineeNum, textIdNum;

    private ProgressBar progressBar;
    private View dataLayout;
    private View searchingTips;

    private ArrayAdapter<SpinnerItem<Integer>> typeAdapter;

    private InformationApi informationApi = ApiManager.getInformationApi();

    private SingleTaskObserver<QueryExamRoomResponse> observer = new SingleTaskObserver<QueryExamRoomResponse>() {

        @Override
        public void onNext(QueryExamRoomResponse response) {
            setSearchState(false);
            if (response == null || !response.isSuccess()) {
                RunningLog.debug("考场查询失败");
                ToastUtil.show(getContext(), "查询失败");
                setData(null);
                setSearchResult(false);
            } else if (response.getObj() == null || response.getObj().size() == 0) {
                RunningLog.debug("考场查询结果无数据");
                ToastUtil.show(getContext(), "查询结果为空");
            } else {
                RunningLog.debug("考场查询成功");
                setData(response.getObj());
                setSearchResult(true);
            }
        }

        @Override
        public void onError(Throwable e) {
            setSearchState(false);
            Toast.makeText(DeviceApp.getApp(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        observer.dispose();
        SoftKeyboardUtil.hideSoftKeyboard(getContext(), editText);
        super.onDismiss(dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, getTheme());
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        setCancelable(true);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayUtil.hideNavigationBar(getDialog().getWindow());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnSearch = view.findViewById(R.id.searchConfirm);
        btnSearch.setOnClickListener(this);

        dataLayout = view.findViewById(R.id.dataLayout);
        searchingTips = view.findViewById(R.id.searchingTips);
        tableLayout = view.findViewById(R.id.tableLayout);

        table = new SmartTable<>(getActivity());
        table.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.removeAllViews();
        tableLayout.addView(table);
        initTable();

        textExamineeNum = view.findViewById(R.id.text_examinee_num);
        textIdNum = view.findViewById(R.id.text_id_num);
        editText = (EditText) view.findViewById(R.id.searchText);

        btnClear = (TextView) view.findViewById(R.id.searchClear);
        btnClear.setOnClickListener(this);

        searchTypeSpinner = (Spinner) view.findViewById(R.id.searchType);

        typeAdapter = new ArrayAdapter<>(view.getContext(), R.layout.dialog_query_type_spinner_item, R.id.text, getQueryTypeList());
        searchTypeSpinner.setAdapter(typeAdapter);
        searchTypeSpinner.setOnItemSelectedListener(this);
        searchTypeSpinner.setSelection(0);

        progressBar = (ProgressBar) view.findViewById(R.id.search_progress);

        tableLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tableLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                tableWidth = tableLayout.getWidth();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_query_exam, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            DisplayMetrics dm = new DisplayMetrics();
            dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = (int) (dm.widthPixels * 0.85f);
            int height = (int) (dm.heightPixels * 0.9f);
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }

    }

    private void initTable() {
        table.getConfig().setShowColumnTitle(true);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowYSequence(false);
        table.getConfig().setVerticalPadding(10);
        table.getConfig().setHorizontalPadding(0);
        LineStyle lineStyle = new LineStyle().setColor(0xFFE9ECF5);
        table.getConfig().setContentGridStyle(lineStyle);
        table.getConfig().setColumnTitleGridStyle(lineStyle);
        table.getConfig().getPaint().setAntiAlias(true);
        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(0xFFF0F4F9));
        FontStyle titleStyle = new FontStyle(16, 0xFF19233E);
        FontStyle fontStyle = new FontStyle(16, 0xFF333333);
        table.getConfig().setContentStyle(fontStyle);
        table.getConfig().setColumnTitleStyle(titleStyle);

        parser = new AnnotationParser<>(table.getConfig().dp10);
    }


    private List<SpinnerItem<Integer>> getQueryTypeList() {
        List<SpinnerItem<Integer>> strings = new ArrayList<>();
        strings.add(new SpinnerItem<>(0, "准考证号"));
        strings.add(new SpinnerItem<>(1, "证件号"));
        return strings;
    }

    private void clearEditTextFocus() {
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setOnFocusChangeListener(null);
        editText.setOnClickListener(null);
        editText.setTag(null);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        editText.setText("");
        SoftKeyboardUtil.hideSoftKeyboard(getContext(), editText);
        clearEditTextFocus();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchClear://搜索栏的清空
                SoftKeyboardUtil.hideSoftKeyboard(getContext(), editText);
                editText.setText("");
                setSearchState(false);
                break;
            case R.id.searchConfirm:
                SoftKeyboardUtil.hideSoftKeyboard(getContext(), editText);
                setSearch();
                break;
        }
    }

    /**
     * 查询按钮启用以及关闭
     */
    private void setSearchState(boolean isSearching) {
        btnSearch.setText(!isSearching ? "搜索" : "搜索中...");
        btnSearch.setEnabled(!isSearching);
        btnClear.setEnabled(!isSearching);
        if (isSearching) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setSearchResult(boolean success) {
        if (success) {
            dataLayout.setVisibility(View.VISIBLE);
            searchingTips.setVisibility(View.INVISIBLE);
        } else {
            dataLayout.setVisibility(View.INVISIBLE);
            searchingTips.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置开始查询
     */
    private void setSearch() {
        SpinnerItem<Integer> item = typeAdapter.getItem(searchTypeSpinner.getSelectedItemPosition());
        if (item == null) {
            item = new SpinnerItem<>(0, "准考证号");
        }
        Observable<QueryExamRoomResponse> observable = null;
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.show(getContext(), "请输入查询参数!!");
            return;
        }
        if (item.getKey() == 0) {
            observable = informationApi.findUserExamInfo(text, null);
        } else {

            observable = informationApi.findUserExamInfo(null, text);
        }
        if (observable != null) {
            setSearchState(true);
            RunningLog.debug("开始查询课表数据");
            observable.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        }
    }

    private void setData(List<QueryExamRoomResponse.Result> resultList) {
        List<QueryExamRoomResultItem> courseQueryResultItems = new LinkedList<>();
        String examNum = null;
        String idNum = null;
        if (resultList != null) {
            for (QueryExamRoomResponse.Result result : resultList) {
                QueryExamRoomResultItem item = new QueryExamRoomResultItem();
                item.setRoom(result.getExamRoom());
                item.setDate(result.getExamDate());
                item.setSeat(result.getSeatNumber());
                item.setTime(result.getExamTimeStart() + result.getExamTimeEnd());
                item.setSubject(result.getExamCourse());
                courseQueryResultItems.add(item);
                if (TextUtils.isEmpty(idNum)) {
                    idNum = result.getIdNumber();
                }
                if (TextUtils.isEmpty(examNum)) {
                    examNum = result.getRegistrationNumber();
                }
            }
        }
        textExamineeNum.setText(examNum);
        textIdNum.setText(idNum);
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
        TableData<QueryExamRoomResultItem> tableData = new TableData<>(parser.getTableName(QueryExamRoomResultItem.class), courseQueryResultItems, columnList);
        table.setTableData(tableData);
    }

}
