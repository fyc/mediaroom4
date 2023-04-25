package com.sunmnet.mediaroom.brand;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.enums.TemplateRight;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateAcademicManageFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateAskForLeaveFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateAttendanceFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateBorrowClassroomFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateControlCenterFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateCourseTableFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateReportFaultFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateMonitorFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateTeachingInspectionFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateQueryClassroomFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateQueryExamRoomFragment;
import com.sunmnet.mediaroom.brand.fragment.template.TemplateQueryExamScoreFragment;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;
import com.sunmnet.mediaroom.brand.utils.SoftKeyboardUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateActivity2 extends AppCompatActivity {

    RadioButton radioBtnCourseTable;
    RadioButton radioBtnAskForLeave;
    RadioButton radioBtnAttendance;
    RadioButton radioBtnControlCenter;
    RadioButton radioBtnMonitor;
    RadioButton radioBtnReportFault;
    RadioButton radioBtnQueryClassroom;
    RadioButton radioBtnBorrowClassroom;
    RadioButton radioBtnQueryExamRoom;
    RadioButton radioBtnQueryExamScore;
    RadioButton radioBtnTeachingInspection;
    RadioButton radioBtnAcademicManage;


    View layoutCourseTable;
    View layoutAskForLeave;
    View layoutAttendance;
    View layoutControlCenter;
    View layoutMonitor;
    View layoutReportFault;
    View layoutQueryClassroom;
    View layoutBorrowClassroom;
    View layoutQueryExamRoom;
    View layoutQueryExamScore;
    View layoutTeachingInspection;
    View layoutAcademicManage;

    TextView title;

    List<RadioButton> btnList;

    TemplateCheckChangeListener checkChangeListener;

    ViewGroup fragmentLayout;

    LoginUser loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loginUser = (LoginUser) getIntent().getSerializableExtra("user");
        findView();
        init();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        SoftKeyboardUtil.hideInputWhenTouchOtherView(this, ev, null);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayUtil.hideNavigationBar(getWindow());
    }

    private void findView() {
        radioBtnCourseTable = (RadioButton) findViewById(R.id.radioBtn_course_table);
        radioBtnAskForLeave = (RadioButton) findViewById(R.id.radioBtn_ask_for_leave);
        radioBtnAttendance = (RadioButton) findViewById(R.id.radioBtn_attendance);
        radioBtnControlCenter = (RadioButton) findViewById(R.id.radioBtn_control_center);
        radioBtnMonitor = (RadioButton) findViewById(R.id.radioBtn_monitor);
        radioBtnReportFault = (RadioButton) findViewById(R.id.radioBtn_report_fault);
        radioBtnQueryClassroom = (RadioButton) findViewById(R.id.radioBtn_query_classroom);
        radioBtnBorrowClassroom = (RadioButton) findViewById(R.id.radioBtn_borrow_classroom);
        radioBtnQueryExamRoom = (RadioButton) findViewById(R.id.radioBtn_query_exam_room);
        radioBtnQueryExamScore = (RadioButton) findViewById(R.id.radioBtn_query_exam_score);
        radioBtnTeachingInspection = (RadioButton) findViewById(R.id.radioBtn_teaching_inspection);
        radioBtnAcademicManage = (RadioButton) findViewById(R.id.radioBtn_academic_manage);

        title = (TextView) findViewById(R.id.title);

        fragmentLayout = (ViewGroup) findViewById(R.id.fragmentLayout);

        layoutCourseTable = findViewById(R.id.layout_course_table);
        layoutAskForLeave = findViewById(R.id.layout_ask_for_leave);
        layoutAttendance = findViewById(R.id.layout_attendance);
        layoutControlCenter = findViewById(R.id.layout_control_center);
        layoutMonitor = findViewById(R.id.layout_monitor);
        layoutReportFault = findViewById(R.id.layout_report_fault);
        layoutQueryClassroom = findViewById(R.id.layout_query_classroom);
        layoutBorrowClassroom = findViewById(R.id.layout_borrow_classroom);
        layoutQueryExamRoom = findViewById(R.id.layout_query_exam_room);
        layoutQueryExamScore = findViewById(R.id.layout_query_exam_score);
        layoutTeachingInspection = findViewById(R.id.layout_teaching_inspection);
        layoutAcademicManage = findViewById(R.id.layout_academic_manage);

    }

    public void onBackClick(View view) {
        AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setCancelable(false);
        ad.setTitle("提示");
        ad.setMessage("是否退出登录并返回主界面？");
        ad.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (DialogInterface.OnClickListener) null);
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        ad.show();
    }


    private void init() {
        btnList = new ArrayList<>();
        btnList.add(radioBtnCourseTable);
        btnList.add(radioBtnAskForLeave);
        btnList.add(radioBtnAttendance);
        btnList.add(radioBtnControlCenter);
        btnList.add(radioBtnMonitor);
        btnList.add(radioBtnReportFault);
        btnList.add(radioBtnQueryClassroom);
        btnList.add(radioBtnBorrowClassroom);
        btnList.add(radioBtnQueryExamRoom);
        btnList.add(radioBtnQueryExamScore);
        btnList.add(radioBtnTeachingInspection);
        btnList.add(radioBtnAcademicManage);


        checkChangeListener = new TemplateCheckChangeListener();
        radioBtnCourseTable.setOnCheckedChangeListener(checkChangeListener);
        radioBtnAskForLeave.setOnCheckedChangeListener(checkChangeListener);
        radioBtnAttendance.setOnCheckedChangeListener(checkChangeListener);
        radioBtnControlCenter.setOnCheckedChangeListener(checkChangeListener);
        radioBtnMonitor.setOnCheckedChangeListener(checkChangeListener);
        radioBtnReportFault.setOnCheckedChangeListener(checkChangeListener);
        radioBtnQueryClassroom.setOnCheckedChangeListener(checkChangeListener);
        radioBtnBorrowClassroom.setOnCheckedChangeListener(checkChangeListener);
        radioBtnQueryExamRoom.setOnCheckedChangeListener(checkChangeListener);
        radioBtnQueryExamScore.setOnCheckedChangeListener(checkChangeListener);
        radioBtnTeachingInspection.setOnCheckedChangeListener(checkChangeListener);
        radioBtnAcademicManage.setOnCheckedChangeListener(checkChangeListener);


        //权限对应模块关系
        Map<TemplateRight, View> rightViewMap = new HashMap<>();
        rightViewMap.put(TemplateRight.AskForLeave, layoutAskForLeave);
        rightViewMap.put(TemplateRight.AttendanceResult, layoutAttendance);
        rightViewMap.put(TemplateRight.BorrowClassroom, layoutBorrowClassroom);
        rightViewMap.put(TemplateRight.ControlCenter, layoutControlCenter);
        rightViewMap.put(TemplateRight.CourseTable, layoutCourseTable);
        rightViewMap.put(TemplateRight.Monitor, layoutMonitor);
        rightViewMap.put(TemplateRight.QueryClassroomStatus, layoutQueryClassroom);
        rightViewMap.put(TemplateRight.ReportFault, layoutReportFault);
        rightViewMap.put(TemplateRight.TeachingInspection, layoutTeachingInspection);

        //控制模块显示隐藏
        if (loginUser != null && loginUser.getRights() != null) {
            for (Map.Entry<TemplateRight, View> entry : rightViewMap.entrySet()) {
                if (loginUser.getRights().contains(entry.getKey())) {
                    entry.getValue().setVisibility(View.VISIBLE);
                } else {
                    entry.getValue().setVisibility(View.GONE);
                }
            }
        } else {
            for (View v : rightViewMap.values()) {
                v.setVisibility(View.GONE);
            }
        }

        //查找第一个有权限的模块按钮
        for (RadioButton button : btnList) {
            if (button.getVisibility() == View.VISIBLE) {
                changeChecked(button.getId());
                break;
            }
        }
    }

    private void changeChecked(int checkedRadioBtnId) {
        if (btnList == null && checkedRadioBtnId != View.NO_ID)
            return;
        for (RadioButton radioButton : btnList) {
            if (radioButton.getId() == checkedRadioBtnId) {
                radioButton.setChecked(true);
            } else {
                radioButton.setChecked(false);
            }
        }

    }


    private class TemplateCheckChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!isChecked)
                return;
            int id = buttonView.getId();
            changeChecked(id);
            Fragment fragment = null;
            switch (id) {
                case R.id.radioBtn_course_table:
                    fragment = TemplateCourseTableFragment.newInstance(loginUser);
                    title.setText("课程表");
                    break;
                case R.id.radioBtn_ask_for_leave:
                    fragment = TemplateAskForLeaveFragment.newInstance(loginUser);
                    title.setText("请假");
                    break;
                case R.id.radioBtn_attendance:
                    fragment = TemplateAttendanceFragment.newInstance();
                    title.setText("考勤结果");
                    break;
                case R.id.radioBtn_control_center:
                    fragment = TemplateControlCenterFragment.newInstance();
                    title.setText("控制中心");
                    break;
                case R.id.radioBtn_monitor:
                    fragment = TemplateMonitorFragment.newInstance();
                    title.setText("视频监控");
                    break;

                case R.id.radioBtn_report_fault:
                    fragment = TemplateReportFaultFragment.newInstance(loginUser);
                    title.setText("故障申报");
                    break;

                case R.id.radioBtn_query_classroom:
                    fragment = TemplateQueryClassroomFragment.newInstance();
                    title.setText("教室查询");
                    break;

                case R.id.radioBtn_borrow_classroom:
                    fragment = TemplateBorrowClassroomFragment.newInstance(loginUser);
                    title.setText("教室借用");
                    break;

                case R.id.radioBtn_query_exam_room:
                    fragment = TemplateQueryExamRoomFragment.newInstance();
                    title.setText("考场查询");
                    break;

                case R.id.radioBtn_query_exam_score:
                    fragment = TemplateQueryExamScoreFragment.newInstance();
                    title.setText("成绩查询");
                    break;

                case R.id.radioBtn_teaching_inspection:
                    fragment = TemplateTeachingInspectionFragment.newInstance(loginUser);
                    title.setText("教学巡查");
                    break;

                case R.id.radioBtn_academic_manage:
                    fragment = TemplateAcademicManageFragment.newInstance();
                    title.setText("教务管理");
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentLayout, fragment);
            transaction.commitAllowingStateLoss();
        }
    }
}