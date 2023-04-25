package com.sunmnet.mediaroom.brand.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomStatus;

import java.util.List;


public class FloorClassroomStatusAdapter extends RecyclerView.Adapter<FloorClassroomStatusAdapter.ViewHolder> {

    List<ClassroomStatus> dataList;

    public void setDataList(List<ClassroomStatus> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_classroom_status, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.classroomName.setText(dataList.get(i).getClassroomName());
        viewHolder.content.setText(dataList.get(i).getContent());
        //0-上课、1-预约、2-借用，空闲为null
        switch (dataList.get(i).getStatus()) {
            case 0:
                viewHolder.itemView.setBackgroundResource(R.color.status_has_course);
                break;
            case 1:
                viewHolder.itemView.setBackgroundResource(R.color.status_reserved);
                break;
            case 2:
                viewHolder.itemView.setBackgroundResource(R.color.status_borrowed);
                break;
            default:
                viewHolder.itemView.setBackgroundResource(R.color.status_unused);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView classroomName;
        TextView content;

        ViewHolder(View view) {
            super(view);
            classroomName = view.findViewById(R.id.text_classroom_name);
            content = view.findViewById(R.id.text_content);
        }
    }
}
