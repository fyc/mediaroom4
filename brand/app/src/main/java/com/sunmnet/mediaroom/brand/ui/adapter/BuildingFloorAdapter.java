package com.sunmnet.mediaroom.brand.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.FloorClassroomStatus;

import java.util.List;


public class BuildingFloorAdapter extends RecyclerView.Adapter<BuildingFloorAdapter.ViewHolder> {

    List<FloorClassroomStatus> dataList;

    public BuildingFloorAdapter(List<FloorClassroomStatus> dataList) {
        this.dataList = dataList;
    }

    public List<FloorClassroomStatus> getDataList() {
        return dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_building_floor_list, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (viewHolder.classroomAdapter == null) {
            viewHolder.classroomAdapter = new FloorClassroomStatusAdapter();
            viewHolder.classroomList.setAdapter(viewHolder.classroomAdapter);
        }
        viewHolder.classroomAdapter.setDataList(dataList.get(i).getStatusList());
        viewHolder.classroomAdapter.notifyDataSetChanged();
        viewHolder.floorName.setText(dataList.get(i).getFloorName());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView classroomList;
        TextView floorName;
        FloorClassroomStatusAdapter classroomAdapter;

        ViewHolder(View view) {
            super(view);
            classroomList = view.findViewById(R.id.classroom_list);
            floorName = view.findViewById(R.id.text_floor_name);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
            classroomList.setLayoutManager(linearLayoutManager);
            SpacingItemDecoration itemDecoration = new SpacingItemDecoration(10, 10);
            classroomList.addItemDecoration(itemDecoration);
        }
    }
}
