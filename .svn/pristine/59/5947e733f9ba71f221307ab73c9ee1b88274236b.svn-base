package com.sunmnet.mediaroom.brand.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.control.base.SingleLineRollTextControl;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextStyle;
import com.sunmnet.mediaroom.brand.bean.play.SignatureContent;
import com.sunmnet.mediaroom.brand.bean.play.NotificationRule;

import java.util.List;


public class NotificationFragment extends Fragment {

    public static NotificationFragment newInstance(NotificationRule data) {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            NotificationRule rule = (NotificationRule) args.getSerializable("data");

            SingleLineRollTextControl rollTextControl = new SingleLineRollTextControl(getContext());
            rollTextControl.setTextBackgroundColor(rule.getBackground());
            rollTextControl.setControlTextSize(rule.getSize());
            rollTextControl.setControlTextColor(rule.getColor());
            rollTextControl.setFont(rule.getFont());
            rollTextControl.setUnderline(rule.isUnderline());
            rollTextControl.setItalic(rule.isItalic());
            rollTextControl.setBold(rule.isBold());

            rollTextControl.setAlignment(TypeUtil.objToStrDef(rule.getAlignment(), ITextStyle.ALIGN_CENTER));

            String rollDirection = TypeUtil.objToStrDef(rule.getRollDirection(), "DTU");
            rollTextControl.setRollDirection(rollDirection);

            List<SignatureContent> contents = rule.getContents();
            rollTextControl.setText(contents);

            try {
                ((ViewGroup) getView()).removeAllViews();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FrameLayout frameLayout = (FrameLayout) getView().findViewById(R.id.contentLayout);
            frameLayout.removeAllViews();
            frameLayout.addView(rollTextControl);
        }
    }
}
