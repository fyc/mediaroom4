package com.sunmnet.mediaroom.brand;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sunmnet.mediaroom.brand.fragment.TemplateFragment;
import com.sunmnet.mediaroom.brand.fragment.template.Template1Fragment;
import com.sunmnet.mediaroom.brand.fragment.template.Template2Fragment;

public class TemplateFragmentAdapter extends MyFragmentStatePagerAdapter {

    private int templateType;
    private String path;

    public TemplateFragmentAdapter(FragmentManager fm, int templateType,String path) {
        super(fm);
        this.templateType = templateType;
        this.path = path;
    }

    @Override
    public Fragment getItem(int position) {
        switch (templateType) {
            case 1:
                return Template1Fragment.newInstance(path);
            case 2:
                return Template2Fragment.newInstance(path);
        }
        return TemplateFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
