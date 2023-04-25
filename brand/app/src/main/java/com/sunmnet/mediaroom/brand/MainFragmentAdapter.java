package com.sunmnet.mediaroom.brand;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sunmnet.mediaroom.brand.bean.ProgramLayoutBean;
import com.sunmnet.mediaroom.brand.fragment.ProgramFragment;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainFragmentAdapter extends MyFragmentStatePagerAdapter {

    List<Map<String, Object>> dataList = new ArrayList<>();

    public MainFragmentAdapter(List<Map<String, Object>> dataList, FragmentManager fm) {
        super(fm);
        setDataList(dataList);
    }

    public void setDataList(List<Map<String, Object>> list) {
        dataList = list;
    }

    @Override
    public Fragment getItem(int position) {
        Map<String, Object> data = dataList.get(position);
        return ProgramFragment.newInstance((ProgramLayoutBean) data.get("bean"), (String) data.get("path"), TypeUtil.objToStr(data.get("examId")));
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public void refreshData() {
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment fragment = mFragments.get(i);
            if (fragment != null && fragment instanceof ProgramFragment) {
                ((ProgramFragment) fragment).refreshControlData();
            }
        }
    }
}
