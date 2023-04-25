package com.sunmnet.mediaroom.brand.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.enums.AttendanceType;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceInfoDialogFragment extends DialogFragment {

    String attendanceType;
    TextView title;
    static Map<AttendanceType, Integer> signStatus = new HashMap<>();
    RecyclerView recyclerView;

    static {
        signStatus.put(AttendanceType.CUT_SCHOOL, R.drawable.bg_attend_cut_school);
        signStatus.put(AttendanceType.NORMAL, R.drawable.bg_attend_normal);
        signStatus.put(AttendanceType.ASK_FOR_LEAVE, R.drawable.bg_attend_ask_for_leave);
        signStatus.put(AttendanceType.LEAVE_EARLY, R.drawable.bg_attend_leave_early);
        signStatus.put(AttendanceType.LATE, R.drawable.bg_attend_late);
    }

    public static AttendanceInfoDialogFragment newInstance(String attendanceType) {
        Bundle args = new Bundle();
        args.putString("attendanceType", attendanceType);
        AttendanceInfoDialogFragment fragment = new AttendanceInfoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
        setCancelable(true);
        this.attendanceType = getArguments().getString("attendanceType", "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_attendance_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void onSuccess() {
        AttendAdapter adapter = new AttendAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }

    private String getTitleString() {
        if ("1".equals(this.attendanceType)) {
            return "正常考勤人员";
        } else if ("2".equals(this.attendanceType)) {
            return "迟到人员";
        } else if ("3".equals(this.attendanceType)) {
            return "旷课人员";
        } else if ("4".equals(this.attendanceType)) {
            return "早退人员";
        } else if ("5".equals(this.attendanceType)) {
            return "请假人员";
        } else {
            return "应到人员";
        }
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

    public static class AttendAdapter extends RecyclerView.Adapter<AttendAdapter.ViewHolder> {

        private List<Map<String, String>> dataList;

        public AttendAdapter(List<Map<String, String>> dataList) {
            this.dataList = dataList;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView portrait;
            TextView name;
            TextView sex;
            TextView signTime;
            TextView attendResult;

            ViewHolder(View view) {
                super(view);
                portrait = (ImageView) view.findViewById(R.id.portrait);
                name = (TextView) view.findViewById(R.id.name);
                sex = (TextView) view.findViewById(R.id.sex);
                signTime = (TextView) view.findViewById(R.id.signTime);
                attendResult = (TextView) view.findViewById(R.id.attendResult);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_info, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Map<String, String> map = dataList.get(position);
            Glide.with(holder.portrait).load(map.get("")).into(holder.portrait);
            holder.name.setText(map.get(""));
            holder.sex.setText(map.get(""));
            holder.signTime.setText(map.get(""));
            holder.attendResult.setText(map.get(""));
            holder.attendResult.setBackgroundResource(signStatus.get(AttendanceType.getAttendanceType(map.get(""))));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
}
