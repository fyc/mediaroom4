package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.event.RefreshControlEvent;
import com.sunmnet.mediaroom.brand.bean.item.BuildingItem;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomStatus;
import com.sunmnet.mediaroom.brand.bean.item.FloorClassroomStatus;
import com.sunmnet.mediaroom.brand.bean.play.Template2Config;
import com.sunmnet.mediaroom.brand.bean.response.BuildingClassroomStatusResponse;
import com.sunmnet.mediaroom.brand.bean.response.BuildingResponse;
import com.sunmnet.mediaroom.brand.bean.response.dto.ClassroomStatusDto;
import com.sunmnet.mediaroom.brand.control.text.ClassNoControl;
import com.sunmnet.mediaroom.brand.control.text.WeekControl;
import com.sunmnet.mediaroom.brand.data.file.FileConstant;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.ui.adapter.BuildingFloorAdapter;
import com.sunmnet.mediaroom.brand.utils.UrlUtil;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.util.FileUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 楼栋教室状态
 */
public class Template2Fragment extends Fragment {

    private String resourcePath;
    private Template2Config config;

    private ImageView logo;
    private Spinner spinner_building;
    private TextView text_date_info;
    private ClassNoControl text_class_no;
    private WeekControl text_week;
    private RecyclerView recyclerView;
    private Timer timer;
    private BuildingFloorAdapter floorAdapter;
    ArrayAdapter<BuildingItem> buildingAdapter;

    private SingleTaskObserver<BuildingClassroomStatusResponse> observer = new SingleTaskObserver<BuildingClassroomStatusResponse>() {
        @Override
        public void onNext(BuildingClassroomStatusResponse response) {
            if (response == null || !response.isSuccess() || response.getObj() == null) {
                RunningLog.run("楼栋教室数据有误");
                return;
            }
            setData(response);
        }

        @Override
        public void onError(Throwable e) {
            RunningLog.run("获取楼栋教室状态失败：" + e.getMessage());
        }
    };

    public static Template2Fragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putString("path", path);
        Template2Fragment fragment = new Template2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_template2, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resourcePath = getArguments().getString("path");
            File file = new File(resourcePath, FileConstant.TEMPLATE_PROGRAM_CONFIG_FILE_NAME);
            String json = FileUtils.readFile(file, "utf-8", "").toString();
            config = JacksonUtil.jsonStrToBean(json, Template2Config.class);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinner_building = view.findViewById(R.id.spinner_building);
        logo = view.findViewById(R.id.logo);
        text_date_info = view.findViewById(R.id.text_date_info);
        text_class_no = view.findViewById(R.id.text_class_no);
        text_week = view.findViewById(R.id.text_week);
        //spinner_building.setAdapter(new ArrayAdapter<String>(""));
        recyclerView = view.findViewById(R.id.recyclerView);
        if (config == null || TextUtils.isEmpty(config.getBuild())) {
            spinner_building.setEnabled(true);
            spinner_building.setClickable(true);
        } else {
            spinner_building.setEnabled(false);
            spinner_building.setClickable(false);
        }
        initConfig();

        text_class_no.setPrefixText("第");
        text_class_no.setSuffixText("节");
        text_class_no.setShowType(1);
        refreshControl(null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(0, 30);
        recyclerView.addItemDecoration(itemDecoration);
        getBuilding();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshControl(RefreshControlEvent event) {
        text_class_no.refreshControlData();
        text_week.refreshControlData();
        Date now = new Date();
        int weekOfSemester = CourseHelper.getDefault().getWeekOfSemester(now);
        String s = DateUtil.formatDate(now, "yyyy.MM.dd");
        String dateText = s + " 第" + weekOfSemester + "周";
        text_date_info.setText(dateText);
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    private void refreshClassroomStatus() {
        //刷新数据
        if (config == null) {
            return;
        }
        ApiManager.getCourseApi().getBuildingClassroomStatus(config.getBuild())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
//        List<FloorClassroomStatus> list = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 9; i++) {
//            FloorClassroomStatus floorClassroomStatus = new FloorClassroomStatus();
//            floorClassroomStatus.setFloorName("第" + i + "层");
//            List<ClassroomStatus> statusList = new ArrayList<>();
//            for (int j = 0; j < 32; j++) {
//                ClassroomStatus classroomStatus = new ClassroomStatus();
//                classroomStatus.setClassroomName(i + "层" + i + "" + String.format("2", j));
//                int nextInt = Math.min(random.nextInt(6), 3);
//                classroomStatus.setStatus(nextInt);
//                switch (nextInt) {
//                    case 0:
//                        classroomStatus.setContent("借用");
//                        break;
//                    case 1:
//                        classroomStatus.setContent("有课");
//                        break;
//                    case 2:
//                        classroomStatus.setContent("预约");
//                        break;
//                    case 3:
//                    default:
//                        classroomStatus.setContent("空闲");
//                        break;
//                }
//                statusList.add(classroomStatus);
//            }
//            floorClassroomStatus.setStatusList(statusList);
//            list.add(floorClassroomStatus);
//        }
//        BuildingFloorAdapter floorAdapter = new BuildingFloorAdapter(list);
//        recyclerView.setAdapter(floorAdapter);
    }

    private void getBuilding() {
        ApiManager.getCourseApi().getBuildingList(null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<BuildingResponse>() {
                    @Override
                    public void onNext(BuildingResponse buildingResponse) {
                        if (buildingResponse != null && buildingResponse.isSuccess() && buildingResponse.getObj() != null) {
                            List<BuildingResponse.Result> results = buildingResponse.getObj();
                            List<BuildingItem> buildingItems = new ArrayList<>();
                            if (config != null && !TextUtils.isEmpty(config.getBuild())) {
                                for (BuildingResponse.Result result : results) {
                                    if (TextUtils.equals(result.getBuildCode(), config.getBuild())) {
                                        BuildingItem buildingItem = new BuildingItem();
                                        buildingItem.setCode(result.getBuildCode());
                                        buildingItem.setName(result.getBuildName());
                                        buildingItem.setCampusCode(result.getCampusCode());
                                        buildingItems.add(buildingItem);
                                        break;
                                    }
                                }
                            } else {
                                for (BuildingResponse.Result result : results) {
                                    BuildingItem buildingItem = new BuildingItem();
                                    buildingItem.setCode(result.getBuildCode());
                                    buildingItem.setName(result.getBuildName());
                                    buildingItem.setCampusCode(result.getCampusCode());
                                    buildingItems.add(buildingItem);
                                }
                            }
                            buildingAdapter = new ArrayAdapter<>(spinner_building.getContext(), R.layout.template2_building_spinner_item, R.id.text, buildingItems);
                            spinner_building.setAdapter(buildingAdapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void setData(BuildingClassroomStatusResponse response) {
        if (response == null || !response.isSuccess() || response.getObj() == null)
            return;
        List<BuildingClassroomStatusResponse.Result> obj = response.getObj();
        List<FloorClassroomStatus> floorList = new ArrayList<>();
        for (BuildingClassroomStatusResponse.Result result : obj) {
            FloorClassroomStatus floorClassroomStatus = new FloorClassroomStatus();
            floorList.add(floorClassroomStatus);
            String floorName;
            if (TextUtils.isEmpty(config.getBuild())) {
                floorName = result.getBuildName() + result.getFloorName();
            } else {
                floorName = result.getFloorName();
            }
            floorClassroomStatus.setFloorName(floorName);

            List<ClassroomStatus> statusList = new ArrayList<>();
            floorClassroomStatus.setStatusList(statusList);
            if (result.getClassroom() != null) {
                for (ClassroomStatusDto statusDto : result.getClassroom()) {
                    ClassroomStatus classroomStatus = new ClassroomStatus();
                    int status = TypeUtil.objToIntDef(statusDto.getType(), -1);
                    classroomStatus.setStatus(status);
                    classroomStatus.setClassroomName(statusDto.getClassroomName());
                    //（0-上课、1-预约、2-借用，空闲为null）
                    switch (status) {
                        case 0:
                            if (statusDto.getCourseTable() != null) {
                                classroomStatus.setContent(statusDto.getCourseTable().getTeacherName() + "\n" + statusDto.getCourseTable().getCourseName());
                            } else {
                                classroomStatus.setContent("上课");
                            }
                            break;
                        case 1:
                            classroomStatus.setContent("预约");
                            break;
                        case 2:
                            classroomStatus.setContent("借用");
                            break;
                        default:
                            classroomStatus.setContent("空闲");
                            break;
                    }
                    statusList.add(classroomStatus);
                }
            }
        }
        if (floorAdapter == null) {
            floorAdapter = new BuildingFloorAdapter(floorList);
            recyclerView.setAdapter(floorAdapter);
        } else {
            floorAdapter.getDataList().clear();
            floorAdapter.getDataList().addAll(floorList);
            floorAdapter.notifyDataSetChanged();
        }
    }

    private void initConfig() {
        if (config != null) {
            String logoPath = config.getLogo();
            String logoUrl = "";
            if (logoPath != null) {
                if (UrlUtil.checkFullUrl(logoPath)) {
                    logoUrl = logoPath;
                } else {
                    if (logoPath.startsWith("/") || logoPath.startsWith("\\"))
                        logoUrl = resourcePath + logoPath.substring(1);
                    else
                        logoUrl = resourcePath + logoPath;
                }
            }
            logoUrl = logoUrl.replaceAll("\\\\", "/");
            Glide.with(getContext()).load(logoUrl).diskCacheStrategy(DiskCacheStrategy.DATA).into(logo);
        }
    }

    //开始轮询
    private void startTimer() {
        stopTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (observer.getDisposable() == null || observer.getDisposable().isDisposed()) {
                    refreshClassroomStatus();
                }
            }
        }, 0, 10000);
    }

    //停止轮询
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
