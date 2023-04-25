// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Version3ContentDispatcher_ViewBinding implements Unbinder {
  private Version3ContentDispatcher target;

  private View view2131230827;

  private View view2131231211;

  private View view2131231210;

  private View view2131230810;

  private View view2131230811;

  @UiThread
  public Version3ContentDispatcher_ViewBinding(final Version3ContentDispatcher target,
      View source) {
    this.target = target;

    View view;
    target.volumnTextView = Utils.findRequiredViewAsType(source, R.id.volumn_value, "field 'volumnTextView'", TextView.class);
    target.quickContainer = Utils.findRequiredViewAsType(source, R.id.v3quick_container, "field 'quickContainer'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.controll_scene_grid, "field 'scene' and method 'onItemClick'");
    target.scene = Utils.castView(view, R.id.controll_scene_grid, "field 'scene'", GridView.class);
    view2131230827 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onItemClick(p0, p1, p2, p3);
      }
    });
    view = Utils.findRequiredView(source, R.id.volumn_plus_btn, "method 'onClick'");
    view2131231211 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.volumn_minus_btn, "method 'onClick'");
    view2131231210 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.classon_onekey, "method 'onClick'");
    view2131230810 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.classover_onekey, "method 'onClick'");
    view2131230811 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    Version3ContentDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.volumnTextView = null;
    target.quickContainer = null;
    target.scene = null;

    ((AdapterView<?>) view2131230827).setOnItemClickListener(null);
    view2131230827 = null;
    view2131231211.setOnClickListener(null);
    view2131231211 = null;
    view2131231210.setOnClickListener(null);
    view2131231210 = null;
    view2131230810.setOnClickListener(null);
    view2131230810 = null;
    view2131230811.setOnClickListener(null);
    view2131230811 = null;
  }
}
