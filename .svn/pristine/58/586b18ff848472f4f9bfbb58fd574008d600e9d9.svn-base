package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.tabsp.R;

import java.util.List;

/**
 * Create by WangJincheng on 2021-09-03
 * 视频矩阵输出接口列表
 */

public class MatrixMainOutAdapter extends BaseAdapter {

    private Context context;
    private List<MatrixInterface> outList;

    public MatrixMainOutAdapter(Context context, List<MatrixInterface> outList) {
        this.context = context;
        this.outList = outList;
    }

    @Override
    public int getCount() {
        return outList.size();
    }

    @Override
    public MatrixInterface getItem(int position) {
        return outList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View mConvertView = convertView;
        if (mConvertView == null) {
            mConvertView = LayoutInflater.from(context).inflate(R.layout.select_matrix_main_out_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = mConvertView.findViewById(R.id.tv_main_out_item);
            mConvertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) mConvertView.getTag();
        }
        if (viewHolder.textView != null) {
            viewHolder.textView.setText(getItem(position).inputName);
        }
        return mConvertView;
    }

    private class ViewHolder {
        public TextView textView;
    }
}
