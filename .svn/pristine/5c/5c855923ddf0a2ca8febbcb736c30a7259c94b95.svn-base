package com.sunmnet.mediaroom.tabsp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunmnet.mediaroom.common.BaseDialogFragment;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.device.bean.RegisterInfo;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.VgaItem;
import com.sunmnet.mediaroom.tabsp.databinding.RateBinding;
import com.sunmnet.mediaroom.tabsp.ui.adapter.AbstractHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;
import okhttp3.Response;

public class RateDialogFragment extends BaseDialogFragment implements View.OnClickListener, Runnable {
    int count = 61;
    TextView time_counter;
    List<VgaItem> items = null;
    static final String RATE_LEVEL_EXCELLENT = "1", RATE_LEVEL_GOOD = "2", RATE_LEVEL_AVERAGE = "3", RATE_LEVEL_POOR = "4";

    CourseSchedule courseSchedule;

    public RateDialogFragment() {
    }

    public static RateDialogFragment newInstance(CourseSchedule courseSchedule) {

        Bundle args = new Bundle();
        args.putSerializable("course", courseSchedule);
        RateDialogFragment fragment = new RateDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            courseSchedule = (CourseSchedule) arguments.getSerializable("course");
        }
        items = new ArrayList<>();
        items.add(new VgaItem(R.drawable.tabsp_excellent_selected, R.drawable.tabsp_excellent, getString(R.string.excellent), RATE_LEVEL_EXCELLENT));
        items.add(new VgaItem(R.drawable.tabsp_good_selected, R.drawable.tabsp_good, getString(R.string.good), RATE_LEVEL_GOOD));
        items.add(new VgaItem(R.drawable.tabsp_average_selected, R.drawable.tabsp_average, getString(R.string.name_average), RATE_LEVEL_AVERAGE));
        items.add(new VgaItem(R.drawable.tabsp_poor_selected, R.drawable.tabsp_poor, getString(R.string.name_poor), RATE_LEVEL_POOR));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabsp_rate_layout, null, false);
        setOnClick(view.findViewById(R.id.confirm), this);
        setOnClick(view.findViewById(R.id.cancel), this);
        setOnClick(view.findViewById(R.id.close), this);
        setIcon(view.findViewById(R.id.rate_container), inflater);
        time_counter = view.findViewById(R.id.time_counter);
        return view;
    }

    AbstractHolder<RateBinding, VgaItem> prevHolder = null;

    private void setIcon(LinearLayout linearLayout, LayoutInflater inflater) {
        if (linearLayout != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            for (int i = 0, size = items.size(); i < size; i++) {
                VgaItem item = items.get(i);
                View view = inflater.inflate(R.layout.tabsp_rate_item, null, false);
                RateBinding binding = DataBindingUtil.bind(view);
                AbstractHolder holder = new AbstractHolder<RateBinding, VgaItem>() {
                    View view;

                    @Override
                    public void bindView(View view) {
                        this.view = view;
                    }

                    @Override
                    public void setProperty(RateBinding vgaItemBinding, VgaItem vgaItem) {
                        super.setProperty(vgaItemBinding, vgaItem);
                        vgaItemBinding.setItem(vgaItem);
                        setSelected(vgaItem.isSelect());
                    }

                    @Override
                    public void setSelected(boolean selected) {
                        int drawable = selected ? this.property.getSelectedDrawable() : this.property.getUnselectDrawable();
                        CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), drawable, binding.rateImg);
                        drawable = selected ? R.drawable.circle_rate_selected : R.drawable.circle_rate_default;
                        binding.rateItemBg.setBackgroundResource(drawable);
                    }
                };
                holder.bindView(view);
                holder.setProperty(binding, item);
                view.setTag(holder);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AbstractHolder holder = (AbstractHolder) v.getTag();
                        if (holder != prevHolder) {
                            if (prevHolder != null) prevHolder.setSelected(false);
                            prevHolder = holder;
                            holder.setSelected(true);
                        }
                    }
                });
                linearLayout.addView(view, params);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (time_counter != null) {
            time_counter.postDelayed(this, 1000);
        }
    }

    private void doDismiss() {
        time_counter.removeCallbacks(this);
        dismiss();
    }

    @OnClick({R.id.close, R.id.confirm, R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (prevHolder != null) {
                    handleRate(prevHolder.getProperty().getTag());
                    doDismiss();
                } else {
                    Toast.makeText(view.getContext(), view.getContext().getString(R.string.tipes_rate_unselect_level), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                doDismiss();
                break;
        }
    }

    /**
     * 选中rate
     */
    private void handleRate(String top) {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                CourseSchedule schedule = null;
                if (courseSchedule != null) {
                    schedule = courseSchedule;
                } else {
                    Date date = new Date();
                    schedule = CourseHelper.getDefault().getCourseSchedule(date, 0, "0");
                    if (schedule == null) {
                        RunningLog.warn(DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss") + " 不在上课期间，开始回溯到15分钟前的课节！");
                        date.setMinutes(date.getMinutes() - 15);
                        schedule = CourseHelper.getDefault().getCourseSchedule(date, 0, "0");
                    }
                }
                handleRate(top, schedule);
            }
        });
    }

    private void handleRate(String top, CourseSchedule courseSchedule) {
        if (courseSchedule != null && !TextUtils.isEmpty(courseSchedule.getCourseCode())) {
            try {
                RegisterInfo registerInfo = TabspApplication.getInstance().getConfig().getRegisterInfo();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("classNo", Integer.parseInt(courseSchedule.getClassNo()));
                jsonObject.addProperty("courseCode", courseSchedule.getCourseCode());
                //com.sunmnet.mediaroom.util.bean.course.LessonDto 中不存在departmentCode.暂用departmentName代替
//                jsonObject.addProperty("departmentCode", courseSchedule.getDepartmentName());
                jsonObject.addProperty("courseDate", DateUtil.formatDate(new Date(), DateUtil.DEFAULT_FORMAT_DATE));
                jsonObject.addProperty("gradeCode", courseSchedule.getGradeCode());
                jsonObject.addProperty("level", Integer.parseInt(top));
                jsonObject.addProperty("teacherId", courseSchedule.getAccountId());
                jsonObject.addProperty("classroomCode", registerInfo.getClassroomCode());
                String responseUrl = "http://" + registerInfo.getPlatformIp() + ":" + registerInfo.getPlatformPort() + "/course-svr/api/deviceEvaluation/save";
                Response response = OkHttpUtil.postJson(responseUrl, jsonObject.toString());
                if (response.isSuccessful()) {
                    String res = response.body().string();
                    JsonObject result = new JsonParser().parse(res).getAsJsonObject();
                    boolean isSuccess = result.get("success").getAsBoolean();
                    if (isSuccess) {
                        RunningLog.run(responseUrl + "设备评价提交成功！！");
                    } else {
                        RunningLog.warn(responseUrl + "设备评价提交失败！！！！错误信息：" + result.get("message").getAsString() + "  请求信息：" + jsonObject.toString());
                    }
                } else {
                    RunningLog.warn("设备评价提交出错！！ response.code = " + response.code());
                }
            } catch (Exception e) {
                RunningLog.error(e);
            }
        } else {
            RunningLog.warn("无课程信息，不执行提交设备评价结果！");
        }
    }

    @Override
    public void run() {
        if (count <= 1) {
            //默认好评
            handleRate(RATE_LEVEL_EXCELLENT);
            doDismiss();
        } else {
            count--;
            time_counter.postDelayed(this, 1000);
            time_counter.setText(count + "");
        }
    }
}
