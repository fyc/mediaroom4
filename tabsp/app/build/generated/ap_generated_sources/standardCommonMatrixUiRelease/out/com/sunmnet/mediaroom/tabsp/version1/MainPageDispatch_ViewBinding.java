// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.version1;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainPageDispatch_ViewBinding implements Unbinder {
  private MainPageDispatch target;

  private View view2131231210;

  private View view2131231211;

  private View view2131230810;

  private View view2131230811;

  private View view2131230827;

  @UiThread
  public MainPageDispatch_ViewBinding(final MainPageDispatch target, View source) {
    this.target = target;

    View view;
    target.volumnTextView = Utils.findRequiredViewAsType(source, R.id.volumn_value, "field 'volumnTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.volumn_minus_btn, "field 'volumnMinusBtn' and method 'onClick'");
    target.volumnMinusBtn = Utils.castView(view, R.id.volumn_minus_btn, "field 'volumnMinusBtn'", ImageView.class);
    view2131231210 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.volumn_plus_btn, "field 'volumnPlusBtn' and method 'onClick'");
    target.volumnPlusBtn = Utils.castView(view, R.id.volumn_plus_btn, "field 'volumnPlusBtn'", ImageView.class);
    view2131231211 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progressBar'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.classon_onekey, "field 'classOnOneKey' and method 'onClick'");
    target.classOnOneKey = Utils.castView(view, R.id.classon_onekey, "field 'classOnOneKey'", LinearLayout.class);
    view2131230810 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.classover_onekey, "field 'classOverOneKey' and method 'onClick'");
    target.classOverOneKey = Utils.castView(view, R.id.classover_onekey, "field 'classOverOneKey'", LinearLayout.class);
    view2131230811 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.classOnOneKeyLogo = Utils.findRequiredViewAsType(source, R.id.iv_class_on_logo, "field 'classOnOneKeyLogo'", ImageView.class);
    target.classOverOneKeyLogo = Utils.findRequiredViewAsType(source, R.id.iv_class_over_logo, "field 'classOverOneKeyLogo'", ImageView.class);
    target.classOnText = Utils.findRequiredViewAsType(source, R.id.tv_class_on_text, "field 'classOnText'", TextView.class);
    target.classOverText = Utils.findRequiredViewAsType(source, R.id.tv_class_over_text, "field 'classOverText'", TextView.class);
    view = Utils.findRequiredView(source, R.id.controll_scene_grid, "method 'onItemClick'");
    view2131230827 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onItemClick(p0, p1, p2, p3);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainPageDispatch target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.volumnTextView = null;
    target.volumnMinusBtn = null;
    target.volumnPlusBtn = null;
    target.progressBar = null;
    target.classOnOneKey = null;
    target.classOverOneKey = null;
    target.classOnOneKeyLogo = null;
    target.classOverOneKeyLogo = null;
    target.classOnText = null;
    target.classOverText = null;

    view2131231210.setOnClickListener(null);
    view2131231210 = null;
    view2131231211.setOnClickListener(null);
    view2131231211 = null;
    view2131230810.setOnClickListener(null);
    view2131230810 = null;
    view2131230811.setOnClickListener(null);
    view2131230811 = null;
    ((AdapterView<?>) view2131230827).setOnItemClickListener(null);
    view2131230827 = null;
  }
}
