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
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.TableData;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.api.impl.CourseApi;
import com.sunmnet.mediaroom.brand.bean.item.CourseQueryResultItem;
import com.sunmnet.mediaroom.brand.bean.item.SpinnerItem;
import com.sunmnet.mediaroom.brand.bean.response.QueryCourseInfoResponse;
import com.sunmnet.mediaroom.brand.enums.CourseQueryType;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.table.AnnotationParser;
import com.sunmnet.mediaroom.brand.table.FixedWidthTextDrawFormat;
import com.sunmnet.mediaroom.brand.utils.SoftKeyboardUtil;
import com.sunmnet.mediaroom.brand.view.DropEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class QueryCourseDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener, CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {

    private View searchingTips, prevView, nextView, searchContentLayout;
    private Spinner searchTypeSpinner;
    private EditText editText;
    private TextView btnSearch, btnClear, searchDate;
    private FrameLayout tableLayout;
    private SmartTable<CourseQueryResultItem> table;
    private AnnotationParser<CourseQueryResultItem> parser;
    private int tableWidth;

    private ProgressBar progressBar;

    private ArrayAdapter<SpinnerItem<CourseQueryType>> typeAdapter;

    private CalendarView mCalendarView;

    private DropEditText unionText;
    private EditText unionStudentName, unionClassroomName, unionGradeName, unionTeacherName;
    private CourseApi courseApi = ApiManager.getCourseApi();


    private SingleTaskObserver<QueryCourseInfoResponse> observer = new SingleTaskObserver<QueryCourseInfoResponse>() {

        @Override
        public void onNext(QueryCourseInfoResponse queryCourseInfoResponse) {
            setSearchState(false);
            if (queryCourseInfoResponse == null || !queryCourseInfoResponse.isSuccess()) {
                RunningLog.debug("课表查询失败");
                setTableData(null);
                setSearchResult(false);
            } else if (queryCourseInfoResponse.getObj() == null || queryCourseInfoResponse.getObj().size() == 0) {
                RunningLog.debug("课表查询结果无数据");
                setTableData(null);
                setSearchResult(false);
            } else {
                RunningLog.debug("课表查询成功");
                setTableData(queryCourseInfoResponse.getObj());
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
        btnSearch = (TextView) view.findViewById(R.id.searchConfirm);
        btnSearch.setOnClickListener(this);

        prevView = view.findViewById(R.id.prevDate);
        nextView = view.findViewById(R.id.nextDate);
        prevView.setOnClickListener(this);
        nextView.setOnClickListener(this);

        searchContentLayout = view.findViewById(R.id.searchContentLayout);
        searchingTips = view.findViewById(R.id.searchingTips);

        tableLayout = (FrameLayout) view.findViewById(R.id.tableLayout);
        table = new SmartTable<>(getActivity());
        table.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tableLayout.removeAllViews();
        tableLayout.addView(table);
        initTable();

        editText = (EditText) view.findViewById(R.id.searchText);

        unionText = (DropEditText) view.findViewById(R.id.unionText);
        View popupContentView = LayoutInflater.from(view.getContext()).inflate(R.layout.popup_query_course_union_param, null, false);
        unionStudentName = (EditText) popupContentView.findViewById(R.id.text_studentName);
        unionClassroomName = (EditText) popupContentView.findViewById(R.id.text_classroomName);
        unionGradeName = (EditText) popupContentView.findViewById(R.id.text_gradeName);
        unionTeacherName = (EditText) popupContentView.findViewById(R.id.text_teacherName);
        popupContentView.findViewById(R.id.btn_clear).setOnClickListener(this);
        unionText.setContentView(popupContentView, () -> unionText.setText(getUnionText(unionStudentName, unionClassroomName, unionGradeName, unionTeacherName)));

        btnClear = (TextView) view.findViewById(R.id.searchClear);
        btnClear.setOnClickListener(this);

        searchTypeSpinner = (Spinner) view.findViewById(R.id.searchType);

        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);

        searchDate = (TextView) view.findViewById(R.id.search_date);
        searchDate.setOnClickListener(this);

        resetDate(null);

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
        return inflater.inflate(R.layout.dialog_query_course, container, false);
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        String s = "{\"success\":true,\"msg\":\"操作成功\",\"obj\":[{\"studentName\":\"1\",\"teacherName\":\"何老师\",\"courseName\":\"大学英语\",\"className\":\"测试班级\",\"classroomName\":\"财经楼2栋-17楼175\",\"classTime\":\"13:45-14:30\",\"classTimeStart\":\"13:45:00\",\"classTimeEnd\":\"14:30:00\"},{\"studentName\":\"1\",\"teacherName\":\"何老师\",\"courseName\":\"大学英语\",\"className\":\"测试班级\",\"classroomName\":\"财经楼2栋-17楼175\",\"classTime\":\"13:45-14:30\",\"classTimeStart\":\"13:45:00\",\"classTimeEnd\":\"14:30:00\"},{\"studentName\":\"李同学\",\"teacherName\":\"何老师\",\"courseName\":\"大学英语\",\"className\":\"高级班\",\"classroomName\":\"财经楼2栋-17楼175\",\"classTime\":\"13:45-14:30\",\"classTimeStart\":\"13:45:00\",\"classTimeEnd\":\"14:30:00\"},{\"studentName\":\"李同学\",\"teacherName\":\"何老师\",\"courseName\":\"大学英语\",\"className\":\"高级班\",\"classroomName\":\"财经楼2栋-17楼175\",\"classTime\":\"13:45-14:30\",\"classTimeStart\":\"13:45:00\",\"classTimeEnd\":\"14:30:00\"},{\"studentName\":\"黄同学\",\"teacherName\":\"何老师\",\"courseName\":\"大学英语\",\"className\":\"高级班\",\"classroomName\":\"财经楼2栋-17楼175\",\"classTime\":\"13:45-14:30\",\"classTimeStart\":\"13:45:00\",\"classTimeEnd\":\"14:30:00\"},{\"studentName\":\"黄同学\",\"teacherName\":\"何老师\",\"courseName\":\"大学英语\",\"className\":\"高级班\",\"classroomName\":\"财经楼2栋-17楼175\",\"classTime\":\"13:45-14:30\",\"classTimeStart\":\"13:45:00\",\"classTimeEnd\":\"14:30:00\"},{\"studentName\":null,\"teacherName\":\"何老师\",\"courseName\":\"大学英语\",\"className\":\"大大班\",\"classroomName\":\"财经楼2栋-17楼175\",\"classTime\":\"13:45-14:30\",\"classTimeStart\":\"13:45:00\",\"classTimeEnd\":\"14:30:00\"},{\"studentName\":null,\"teacherName\":\"何老师\",\"courseName\":\"高等数学\",\"className\":\"大大班\",\"classroomName\":\"财经楼2栋-10楼107\",\"classTime\":\"15:44-16:29\",\"classTimeStart\":\"15:44:00\",\"classTimeEnd\":\"16:29:00\"}],\"errorCode\":null}";
//        QueryCourseInfoResponse queryCourseInfoResponse = JacksonUtil.jsonStrToBean(s, QueryCourseInfoResponse.class);
//        setTableData(queryCourseInfoResponse.getObj());
//        setSearchResult(true);
//    }

    private void initTable() {
        table.getConfig().setShowColumnTitle(true);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowYSequence(false);
        table.getConfig().setVerticalPadding(10);
        table.getConfig().setHorizontalPadding(0);
        LineStyle lineStyle = new LineStyle().setColor(0x00000000);
        table.getConfig().setContentGridStyle(lineStyle);
        table.getConfig().setColumnTitleGridStyle(lineStyle);
        table.getConfig().getPaint().setAntiAlias(true);

        FontStyle titleStyle = new FontStyle(20, 0xFF000000);
        FontStyle fontStyle = new FontStyle(16, 0xFF343434);
        table.getConfig().setContentStyle(fontStyle);
        table.getConfig().setColumnTitleStyle(titleStyle);

        parser = new AnnotationParser<>(table.getConfig().dp10);
    }

    /**
     * 重置日期
     */
    private void resetDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        searchDate.setTag(date);
        String dateStr = DateUtil.getDateFormat("yyyy-MM-dd EEEE", Locale.CHINA).format(date);
        searchDate.setText(dateStr);
    }


    private List<SpinnerItem<CourseQueryType>> getQueryTypeList() {
        List<SpinnerItem<CourseQueryType>> strings = new ArrayList<>();
        strings.add(new SpinnerItem<>(CourseQueryType.STUDENT, "学生姓名"));
        strings.add(new SpinnerItem<>(CourseQueryType.TEACHER, "教师姓名"));
        strings.add(new SpinnerItem<>(CourseQueryType.GRADE_NAME, "班级名称"));
        strings.add(new SpinnerItem<>(CourseQueryType.CLASSROOM, "教室名称"));
        strings.add(new SpinnerItem<>(CourseQueryType.UNION, "复合查询"));
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
        if (view != null) {
            SpinnerItem<CourseQueryType> item = typeAdapter.getItem(i);
            resetDate(null);
            editText.setText("");
            unionText.setText("");
            cleanUnionEditText();
            SoftKeyboardUtil.hideSoftKeyboard(getContext(), editText);
            if (item.getKey() != null) {
                if (item.getKey() == CourseQueryType.UNION) {
                    clearEditTextFocus();
                    editText.setVisibility(View.GONE);
                    unionText.setVisibility(View.VISIBLE);
                    unionText.onApply();
                } else {
                    editText.setVisibility(View.VISIBLE);
                    unionText.setVisibility(View.GONE);
                    clearEditTextFocus();
                }
            } else {
                editText.setHint("请选择查询类型");
            }
        }
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
                unionText.setText("");
                cleanUnionEditText();
                setSearchState(false);
                resetDate(null);
                break;
            case R.id.searchConfirm:
                SoftKeyboardUtil.hideSoftKeyboard(getContext(), editText);
                String date = DateUtil.formatDate((Date) searchDate.getTag(), "yyyy-MM-dd");
                setSearch(date);
                break;
            case R.id.prevDate:
                onPreClick(view);
                break;
            case R.id.nextDate:
                onNextClick(view);
                break;
            case R.id.btn_clear://复合查询中的清空按钮
                cleanUnionEditText();
                break;
            case R.id.search_date:
                // 显示隐藏日历
                toggleCalendarViewVisible();
                break;
        }
    }

    /**
     * 查询按钮启用以及关闭
     */
    private void setSearchState(boolean isSearching) {
        btnSearch.setText(!isSearching ? "搜索" : "搜索中...");
        btnSearch.setEnabled(!isSearching);
        prevView.setEnabled(!isSearching);
        nextView.setEnabled(!isSearching);
        btnClear.setEnabled(!isSearching);
        if (isSearching) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setSearchResult(boolean success) {
        if (success) {
            tableLayout.setVisibility(View.VISIBLE);
            searchingTips.setVisibility(View.INVISIBLE);
        } else {
            tableLayout.setVisibility(View.INVISIBLE);
            searchingTips.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置开始查询
     */
    private void setSearch(String dateStr) {
        SpinnerItem<CourseQueryType> item = typeAdapter.getItem(searchTypeSpinner.getSelectedItemPosition());
        if (item == null) {
            item = new SpinnerItem<>(CourseQueryType.CLASSROOM, "教室名称");
        }
        Observable<QueryCourseInfoResponse> observable = null;
        if (item.getKey() == CourseQueryType.UNION) {
            String studentName = getTextValue(unionStudentName);
            String teacherName = getTextValue(unionTeacherName);
            String gradeName = getTextValue(unionGradeName);
            String classroomName = getTextValue(unionClassroomName);
            if (studentName == null && teacherName == null && gradeName == null && classroomName == null) {
                ToastUtil.show(getContext(), "请在复合查询中输入条件!!");
                return;
            }
            observable = courseApi.getCourseList(dateStr, studentName, teacherName, gradeName, classroomName);
        } else {
            String text = editText.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                ToastUtil.show(getContext(), "请输入查询参数!!");
                return;
            }
            switch (item.getKey()) {
                case STUDENT:
                    observable = courseApi.getCourseList(dateStr, text, null, null, null);
                    break;
                case TEACHER:
                    observable = courseApi.getCourseList(dateStr, null, text, null, null);
                    break;
                case GRADE_NAME:
                    observable = courseApi.getCourseList(dateStr, null, null, text, null);
                    break;
                case CLASSROOM:
                    observable = courseApi.getCourseList(dateStr, null, null, null, text);
                    break;
            }
        }
        if (observable != null) {
            setSearchState(true);
            RunningLog.debug("开始查询课表数据");
            observable.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        }
    }

    private void setTableData(List<QueryCourseInfoResponse.Result> resultList) {
        List<CourseQueryResultItem> courseQueryResultItems = new LinkedList<>();
        if (resultList != null) {
            for (QueryCourseInfoResponse.Result result : resultList) {
                courseQueryResultItems.add(new CourseQueryResultItem(result));
            }
        }
        List<Column> columnList = parser.getColumnList(CourseQueryResultItem.class);
        int width = tableWidth / columnList.size();
        int remainder  = tableWidth % columnList.size();
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
        TableData<CourseQueryResultItem> tableData = new TableData<>(parser.getTableName(CourseQueryResultItem.class), courseQueryResultItems, columnList);
        table.setTableData(tableData);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        resetDate(new Date(calendar.getTimeInMillis()));
        if (isClick) {
            toggleCalendarViewVisible();
        }
    }

    @Override
    public void onYearChange(int year) {

    }

    private void toggleCalendarViewVisible() {
        if (mCalendarView.getVisibility() != View.VISIBLE) {
            mCalendarView.setVisibility(View.VISIBLE);
            searchContentLayout.setVisibility(View.INVISIBLE);
            Date date = (Date) searchDate.getTag();
            if (date != null) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(date);
                mCalendarView.scrollToCalendar(calendar.get(java.util.Calendar.YEAR),
                        calendar.get(java.util.Calendar.MONTH) + 1,
                        calendar.get(java.util.Calendar.DAY_OF_MONTH), false);
            } else {
                mCalendarView.scrollToCurrent(false);
            }
        } else {
            mCalendarView.setVisibility(View.INVISIBLE);
            searchContentLayout.setVisibility(View.VISIBLE);
        }
    }

    private void onPreClick(View v) {
        if (mCalendarView.getVisibility() != View.VISIBLE) {
            Date dt = (Date) searchDate.getTag();
            dt.setTime(dt.getTime() - TimeUnit.DAYS.toMillis(1));
            resetDate(dt);
            setSearch(DateUtil.formatDate(dt, "yyyy-MM-dd"));
        } else {
            mCalendarView.scrollToPre();
        }
    }

    private void onNextClick(View v) {
        if (mCalendarView.getVisibility() != View.VISIBLE) {
            Date now = (Date) searchDate.getTag();
            now.setTime(now.getTime() + TimeUnit.DAYS.toMillis(1));
            v.setTag(now);
            resetDate(now);
            setSearch(DateUtil.formatDate(now, "yyyy-MM-dd"));
        } else {
            mCalendarView.scrollToNext();
        }
    }


    private String getUnionText(EditText... editTexts) {
        StringBuilder builder = new StringBuilder();
        for (EditText editText : editTexts) {
            String text = editText.getText().toString();
            if (!TextUtils.isEmpty(text.trim())) {
                builder.append(editText.getTag().toString());
                builder.append(text);
                builder.append(",");
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    private String getTextValue(EditText editText) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0) {
            return null;
        }
        return s;
    }

    private void cleanUnionEditText() {
        unionStudentName.setText("");
        unionClassroomName.setText("");
        unionGradeName.setText("");
        unionTeacherName.setText("");
    }
}
