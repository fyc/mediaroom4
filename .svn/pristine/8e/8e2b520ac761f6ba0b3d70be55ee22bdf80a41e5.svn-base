package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamStudentsControlProperty;
import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.interfaces.control.IExamControl;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.ExamTimetableUtil;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.util.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 考试学生组件
 */
public class ExamStudentsControl extends BaseFrameControl<ExamStudentsControlProperty> implements IExamControl {

    private RecyclerView recyclerView;
    protected String examTimetableId;

    public ExamStudentsControl(Context context) {
        super(context);
    }

    public ExamStudentsControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamStudentsControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setExamTimetableId(String examId) {
        this.examTimetableId = examId;
    }

    @Override
    public String getExamTimetableId() {
        return examTimetableId;
    }

    @Override
    public void refreshControlData() {
        if (DeviceApp.getApp().isRegistered()) {
            loadExam();
        }
    }

    protected void loadExam() {
        //调用接口获取数据，使用文件缓存
        if (TextUtils.isEmpty(examTimetableId)) {
            setExamData(null);
            RunningLog.run("考试时间表ID为空，不加载数据");
            return;
        }
        ApiManager.getInformationApi().getExamDetailList(examTimetableId, DeviceApp.getApp().getClassroomCode())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<ExamDetailResponse>() {
                    @Override
                    public void onNext(ExamDetailResponse examDetailResponse) {
                        if (examDetailResponse == null || !examDetailResponse.isSuccess() || examDetailResponse.getObj().size() == 0) {
                            RunningLog.debug("请求考试时间表数据无数据或数据格式有误");
                            setExamData(null);
                            return;
                        }
                        //写缓存
                        String jsonStr = JacksonUtil.objToJsonStr(examDetailResponse);
                        FileUtils.writeFile(FileResourceUtil.getExamFolderPath() + FileResourceUtil.getExamTimetableFileName(examTimetableId), jsonStr);

                        ExamDetailResponse.ExamDetail timeTableBean = ExamTimetableUtil.getNearestExam(examDetailResponse.getObj());
                        timeTableBean = ExamTimetableUtil.filterTimeTable(timeTableBean);
                        if (timeTableBean != null) {
                            Date endTime = ExamTimetableUtil.getEndTime(timeTableBean);
                            Date date = new Date(endTime.getTime() + TimeUnit.MINUTES.toMillis(5));
                            long timeDiff = date.getTime() - System.currentTimeMillis();
                            if (timeDiff > 0) {
                                setExamData(timeTableBean);
                            } else {
                                setExamData(null);
                            }
                        } else {
                            setExamData(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.debug("请求考试时间表失败：" + e.getMessage());
                        RunningLog.run("网络加载考试时间表失败，尝试读取缓存数据");
                        File file = new File(FileResourceUtil.getExamFolderPath(), FileResourceUtil.getExamTimetableFileName(examTimetableId));
                        if (file.exists() && !FileResourceUtil.isExpired(file, 10, TimeUnit.HOURS)) {
                            RunningLog.run("读取考试时间表缓存文件数据");
                            String str = FileUtils.readFile(file, "utf-8").toString();
                            RunningLog.debug("考试时间表缓存文件数据：" + str);
                            ExamDetailResponse timetable = JacksonUtil.jsonStrToBean(str, ExamDetailResponse.class);
                            ExamDetailResponse.ExamDetail timeTableBean = ExamTimetableUtil.getNearestExam(timetable.getObj());
                            timeTableBean = ExamTimetableUtil.filterTimeTable(timeTableBean);
                            setExamData(timeTableBean);
                        } else {
                            RunningLog.run("考试时间表缓存不存在，或已超过有效期");
                        }
                    }
                });
    }

    @Override
    protected void init() {
        removeAllViews();
        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(getContext())
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.TOP)
                //whether RecyclerView can scroll. TRUE by default
                .setScrollingEnabled(true)
                //set maximum views count in a particular row
                .setMaxViewsInRow(4)
                //set gravity resolver where you can determine gravity for item in position.
                //This method have priority over previous one
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                //you are able to break row due to your conditions. Row breaker should return true for that views
                //a layoutOrientation of layout manager, could be VERTICAL OR HORIZONTAL. HORIZONTAL by default
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                // row strategy for views in completed row, could be STRATEGY_DEFAULT, STRATEGY_FILL_VIEW,
                //STRATEGY_FILL_SPACE or STRATEGY_CENTER
                .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_SPACE)
                // whether strategy is applied to last row. FALSE by default
                .withLastRow(true)
                .build();
        recyclerView.setLayoutManager(chipsLayoutManager);
        recyclerView.addItemDecoration(new SpacingItemDecoration(10, 10));
        addView(recyclerView);
    }

    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null || timeTableBean.getExaminees() == null || timeTableBean.getExaminees().size() == 0) {
            recyclerView.setAdapter(null);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (ExamDetailResponse.ExamDetail.Examinees examinees : timeTableBean.getExaminees()) {
                //李丽：372131182100101 座位号:01
                stringBuilder.append(examinees.getName()).append(":")
                        .append(examinees.getRegistrationNumber())
                        .append(" 座位号：").append(examinees.getSeatNumber()).append("\n");
            }
            MyAdapter adapter = new MyAdapter(timeTableBean.getExaminees());
            recyclerView.setAdapter(adapter);
        }
    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<ExamDetailResponse.ExamDetail.Examinees> dataList;

        public MyAdapter(List<ExamDetailResponse.ExamDetail.Examinees> dataList) {
            this.dataList = dataList;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            ViewHolder(View view) {
                super(view);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(35);
            ViewHolder holder = new ViewHolder(textView);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ExamDetailResponse.ExamDetail.Examinees examinees = dataList.get(position);
            String text = examinees.getName() + ":" + examinees.getRegistrationNumber() + " 座位号：" + examinees.getSeatNumber();
            ((TextView) holder.itemView).setText(text);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

}
