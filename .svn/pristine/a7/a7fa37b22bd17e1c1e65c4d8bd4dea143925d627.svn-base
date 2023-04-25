package com.sunmnet.mediaroom.brand.fragment.template;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.table.ArrayTableData;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.TemplateCourseTableCell;
import com.sunmnet.mediaroom.brand.bean.response.UserCourseScheduleResponse;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.control.table.TemplateCourseTableControl;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.bean.course.CourseTime;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TemplateCourseTableFragment extends Fragment implements TemplateCourseTableControl.TableDataListener {

    List<UserCourseScheduleResponse.Result> schedule;
    TemplateCourseTableControl tableControl;
    LoginUser loginUser;
    Spinner spinnerWeek;

    public static TemplateCourseTableFragment newInstance(LoginUser loginUser) {
        Bundle args = new Bundle();
        TemplateCourseTableFragment fragment = new TemplateCourseTableFragment();
        args.putSerializable("user", loginUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginUser = (LoginUser) getArguments().getSerializable("user");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template_course_table, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tableControl = view.findViewById(R.id.course_table);
        tableControl.setTableDataListener(this);
        tableControl.getTable().getConfig().setColumnTitleBackground(new BaseBackgroundFormat(0x677F8499));
        tableControl.getTable().getConfig().setContentBackground(new BaseBackgroundFormat(0x677F8499));

        spinnerWeek = view.findViewById(R.id.spinner_week);
        if (CourseHelper.getDefault().isLoaded()) {
            int weekCount = CourseHelper.getDefault().getWeekCount();
            List<String> strings = new ArrayList<>();
            strings.add("当周");
            for (int i = 1; i <= weekCount; i++) {
                strings.add("第" + i + "周");
            }
            ArrayAdapter adapter = new ArrayAdapter<>(spinnerWeek.getContext(), R.layout.spinner_item, R.id.text, strings);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinnerWeek.setAdapter(adapter);
            spinnerWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        getData(null);
                    } else {
                        getData(position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData(null);
    }

    private void getData(Integer week) {
        if (loginUser != null) {
            showProgressDialog();
            ApiManager.getCourseApi().getCourseScheduleByUser(loginUser.getToken(), loginUser.getId(), week)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleTaskObserver<UserCourseScheduleResponse>() {
                        @Override
                        public void onNext(UserCourseScheduleResponse userCourseScheduleResponse) {
                            if (!isDetached() && getContext() != null) {
                                ToastUtil.show(getContext(), "查询课表完毕");
                            }
                            if (userCourseScheduleResponse != null && userCourseScheduleResponse.isSuccess()) {
                                RunningLog.run("查询用户课表数据有误");
                                schedule = userCourseScheduleResponse.getObj();
                                if (tableControl != null) {
                                    tableControl.refreshControlData();
                                }
                            } else {
                                RunningLog.run("查询用户课表成功");
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (tableControl != null) {
                                tableControl.refreshControlData();
                            }
                            if (!isDetached() && getContext() != null) {
                                ToastUtil.show(getContext(), "查询课表失败...");
                            }
                            RunningLog.debug("查询课表失败： " + e.getMessage());
                            hideProgressDialog();
                        }
                    });
        }
    }

    private ProgressDialog progressDialog;

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setTitle("查询");
        progressDialog.setMessage("正在查询课表...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateTableData(TemplateCourseTableCell[][] tableCells) {
        if (CourseHelper.getDefault().isLoaded() &&
                schedule != null && schedule.size() > 0) {
            try {
                for (int x = 0; x < tableCells.length; x++) {//课节数据
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("第").append(x + 1).append("节");
                    CourseTime courseTime = CourseHelper.getDefault().getCourseTimeArray().get(x + 1);
                    if (courseTime != null) {
                        buffer.append("\n").append(CourseHelper.getDefault().getCourseTimeArray().get(x + 1).getStart());
                        buffer.append("-");
                        buffer.append(CourseHelper.getDefault().getCourseTimeArray().get(x + 1).getEnd());
                    }
                    tableCells[x][0].setText(buffer.toString());
                    tableCells[x][0].setBold(true);
                    tableCells[x][0].setTextColor("#3EF2C3");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                List<UserCourseScheduleResponse.Result> courseList = new ArrayList<>(schedule);
                for (int x = 0; x < tableCells.length; x++) {//课程信息,同行同课节，同列同一天
                    int classNo = x + 1;
                    List<UserCourseScheduleResponse.Result> curXCourseList = new ArrayList<>();
                    for (UserCourseScheduleResponse.Result course : courseList) {
                        if (course.getClassNo() == classNo) {
                            curXCourseList.add(course);
                        }
                    }
                    courseList.removeAll(curXCourseList);
                    for (int y = 1; y < tableCells[x].length; y++) {
                        StringBuilder builder = new StringBuilder();
                        UserCourseScheduleResponse.Result curCourse = null;
                        for (UserCourseScheduleResponse.Result course : curXCourseList) {
                            if (course.getWeek() == y) {
                                curCourse = course;
                                break;
                            }
                        }
                        curXCourseList.remove(curCourse);
                        if (curCourse != null) {
                            if (!TextUtils.isEmpty(curCourse.getCourseName())) {
                                builder.append(curCourse.getCourseName());
                            } else {
                                builder.append("空闲");
                            }
                            if (!TextUtils.isEmpty(curCourse.getTeacherName())) {
                                builder.append("\n").append(curCourse.getTeacherName());
                            }
                            if (!TextUtils.isEmpty(curCourse.getGradeName())) {
                                builder.append("\n").append(curCourse.getGradeName());
                            }
                            if (!TextUtils.isEmpty(curCourse.getClassroomName())) {
                                builder.append("\n").append(curCourse.getClassroomName());
                            }
                        } else {
                            builder.append("无");
                        }
                        tableCells[x][y].setText(builder.toString());
                        tableCells[x][y].setTextColor("#FFFFFF");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                for (int x = 0; x < tableCells.length; x++) {//课节数据
                    int classNo = x + 1;
                    tableCells[x][0].setText("第" + classNo + "节");
                    tableCells[x][0].setBold(true);
                    tableCells[x][0].setTextColor("#3EF2C3");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (int x = 0; x < tableCells.length; x++) {//课程信息,行为课节，列为星期
                    for (int y = 1; y < tableCells[x].length; y++) {
                        tableCells[x][y].setText("空闲");
                        tableCells[x][y].setTextColor("#FFFFFF");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void afterTableDataCreated(ArrayTableData<TemplateCourseTableCell> tableData) {

    }
}
