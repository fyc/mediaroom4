package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.response.dto.ClassroomStatusDto;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomStatusCell;
import com.sunmnet.mediaroom.brand.bean.response.ClassroomStatusResponse;
import com.sunmnet.mediaroom.brand.table.ClassroomQueryDrawFormat;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

// 教室查询接口
public class TemplateQueryClassroomFragment extends Fragment {

    SmartTable<ClassroomStatusCell> smartTable;
    protected ClassroomStatusCell[][] cells;

    private String[] title = {"", "6:00  ~", "7:00  ~", "8:00  ~", "9:00  ~", "10:00  ~", "11:00  ~", "12:00  ~",
            "13:00  ~", "14:00  ~", "15:00  ~", "16:00  ~", "17:00  ~", "18:00  ~", "19:00  ~", "20:00  ~", "21:00  ~", "22:00  ~"};
    private int rows = 7;//一周7天
    private int columns = title.length;

    //每个整点的毫秒数
    private SparseArray<Long> hourTimeMillis = new SparseArray<>();
    private float oneHourMillis = (float) TimeUnit.HOURS.toMillis(1);
    private String[] weekTitle = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    private int width;
    private int height;

    private void resetTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        for (int i = 6; i < 24; i++) {
            calendar.set(Calendar.HOUR_OF_DAY, i);
            hourTimeMillis.put(i, calendar.getTimeInMillis());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetTime();
    }

    public static TemplateQueryClassroomFragment newInstance() {
        Bundle args = new Bundle();
        TemplateQueryClassroomFragment fragment = new TemplateQueryClassroomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    protected void initTable(View view) {
        smartTable = new SmartTable<>(getContext());
        smartTable.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        smartTable.getConfig().setShowColumnTitle(true);
        smartTable.getConfig().setShowTableTitle(false);
        smartTable.getConfig().setShowXSequence(false);
        smartTable.getConfig().setShowYSequence(false);
        smartTable.getConfig().setHorizontalPadding(0);
        smartTable.getConfig().setVerticalPadding(0);
        smartTable.getConfig().getPaint().setAntiAlias(true);
        smartTable.getConfig().setContentStyle(new FontStyle(22, 0xffffffff));
        smartTable.getConfig().setColumnTitleStyle(new FontStyle(18, 0xffffffff));
        LineStyle lineStyle = new LineStyle(1, 0x20FFFFFF);
        smartTable.getConfig().setColumnTitleGridStyle(lineStyle);
        smartTable.getConfig().setContentGridStyle(lineStyle);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.contentLayout);
        frameLayout.removeAllViews();
        frameLayout.addView(smartTable);
        frameLayout.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                height = frameLayout.getHeight() / 9;
                smartTable.setMinimumHeight(height);
                frameLayout.getViewTreeObserver().removeOnDrawListener(this);
//                initTestData();
            }
        });

        smartTable.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(0x3F61A4E0));
        smartTable.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                return 0x7F7F8499;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_query_classroom, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTable(view);
    }

    @Override
    public void onResume() {
        super.onResume();
//        initTestData();
        getData();
    }

    private int getDrawColor(int type) {
        //0-上课、1-预约、2-借用
        switch (type) {
            case 0:
                return getResources().getColor(R.color.status_has_course);
            case 1:
                return getResources().getColor(R.color.status_reserved);
            case 2:
                return getResources().getColor(R.color.status_borrowed);
            default:
                return 0x00000000;
        }
    }

    private void setData(ClassroomStatusResponse response) {
        initTableCell();
        if (response != null && response.isSuccess()) {
            if (response.getObj() != null && response.getObj().size() > 0) {
                //按星期几weekNum整合数据
                HashMap<Integer, List<ClassroomStatusDto>> classroomStatusDtoListMap = new HashMap<>();
                for (ClassroomStatusDto classroomStatusDto : response.getObj()) {
                    if (classroomStatusDto.getType() == null)
                        continue;
                    Integer weekNum = classroomStatusDto.getWeekNum();
                    List<ClassroomStatusDto> statusDtoList = classroomStatusDtoListMap.get(weekNum);
                    if (statusDtoList == null) {
                        statusDtoList = new ArrayList<>();
                        classroomStatusDtoListMap.put(weekNum, statusDtoList);
                    }
                    statusDtoList.add(classroomStatusDto);
                }
                for (int i = 0; i < rows; i++) {
                    List<ClassroomStatusDto> statusDtoList = classroomStatusDtoListMap.get(i + 1);
                    if (statusDtoList != null && statusDtoList.size() > 0) {
                        for (ClassroomStatusDto statusDto : statusDtoList) {
                            Date startTime = DateUtil.parseDateStr(statusDto.getStartTime(), DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
                            Date endTime = DateUtil.parseDateStr(statusDto.getEndTime(), DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
                            if (startTime == null || endTime == null)
                                continue;
                            long startTimeMillis = startTime.getTime();
                            long endTimeMillis = endTime.getTime();

                            //存储跨单元格时间戳变量
                            long curTimeMillis = startTimeMillis;

                            for (int j = 0; j < hourTimeMillis.size() - 1; j++) {
                                if ((j + 1) < hourTimeMillis.size()) {
                                    long timeMillis = hourTimeMillis.valueAt(j);
                                    long nextTime = hourTimeMillis.valueAt(j + 1);
                                    if (curTimeMillis >= timeMillis && curTimeMillis <= nextTime) {
                                        //判断还有没有下一 整点时间
                                        ClassroomStatusCell.DrawStatus drawStatus = new ClassroomStatusCell.DrawStatus();
                                        float startPercent = (curTimeMillis - timeMillis) / oneHourMillis;
                                        drawStatus.setStartPercent(startPercent);
                                        drawStatus.setColor(getDrawColor(statusDto.getType()));
                                        cells[i][j + 1].addDrawStatus(drawStatus);

                                        if (endTimeMillis <= nextTime) {
                                            //如果在下一整点时间前，则在一个单元格里
                                            float endPercent = (endTimeMillis - timeMillis) / oneHourMillis;
                                            drawStatus.setEndPercent(endPercent);
                                            break;
                                        } else {
                                            //如果不在，则跨多个单元格
                                            drawStatus.setEndPercent(1f);

                                            //修改为下一时间节点，继续下一时间节点（单元格）判断
                                            curTimeMillis = nextTime;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ArrayTableData<ClassroomStatusCell> tableData = ArrayTableData.create("", title, ArrayTableData.transformColumnArray(cells), new ClassroomQueryDrawFormat());
        tableData.getArrayColumns();
        tableData.setFormat(new IFormat<ClassroomStatusCell>() {
            @Override
            public String format(ClassroomStatusCell classroomStatusCell) {
                return classroomStatusCell.getContent();
            }
        });
        smartTable.setTableData(tableData);
    }

    private void getData() {
        ApiManager.getCourseApi().getClassroomStatus(DeviceApp.getApp().getClassroomCode())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<ClassroomStatusResponse>() {
                    @Override
                    public void onNext(ClassroomStatusResponse response) {
                        if (response == null || !response.isSuccess() || response.getObj() == null) {
                            RunningLog.run("查询教室状态数据有误");
                        } else {
                            RunningLog.run("查询教室状态成功");
                        }
                        if (!isDetached() && getContext() != null) {
                            setData(response);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.run("查询教室状态出错：" + e.getMessage());
                        setData(null);
                    }
                });
    }

    private void initTestData() {
        ClassroomStatusResponse statusResponse = new ClassroomStatusResponse();
        statusResponse.setSuccess(true);
        List<ClassroomStatusDto> list = new ArrayList<>();

        ClassroomStatusDto result1 = new ClassroomStatusDto();
        result1.setStartTime("7:20");
        result1.setEndTime("8:30");
        result1.setType(1);
        result1.setWeekNum(1);


        ClassroomStatusDto result2 = new ClassroomStatusDto();
        result2.setStartTime("9:00");
        result2.setEndTime("9:50");
        result2.setType(2);
        result2.setWeekNum(1);

        ClassroomStatusDto result3 = new ClassroomStatusDto();
        result3.setStartTime("17:00");
        result3.setEndTime("19:50");
        result3.setType(3);
        result3.setWeekNum(6);

        ClassroomStatusDto result4 = new ClassroomStatusDto();
        result4.setStartTime("22:30");
        result4.setEndTime("23:00");
        result4.setType(3);
        result4.setWeekNum(6);

        list.add(result1);
        list.add(result2);
        list.add(result1);
        list.add(result3);
        list.add(result4);

        statusResponse.setObj(list);
        setData(statusResponse);
    }

    private void initTableCell() {
        if (cells == null) {
            cells = new ClassroomStatusCell[rows][columns];
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cells[i][j] == null) {
                    cells[i][j] = new ClassroomStatusCell();
                    cells[i][j].setWidth(width);
                    cells[i][j].setHeight(height);
                } else {
                    cells[i][j].setWidth(width);
                    cells[i][j].setHeight(height);
                    cells[i][j].setContent(null);
                    cells[i][j].clearDrawStatus();
                    cells[i][j].clearClassroomStatus();
                }
            }
        }
        for (int i = 0; i < rows; i++) {
            cells[i][0].setContent(weekTitle[i]);
        }

    }
}
