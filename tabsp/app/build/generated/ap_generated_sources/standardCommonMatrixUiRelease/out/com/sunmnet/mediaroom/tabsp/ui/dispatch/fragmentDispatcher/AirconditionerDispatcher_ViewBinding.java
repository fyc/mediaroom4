// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AirconditionerDispatcher_ViewBinding implements Unbinder {
  private AirconditionerDispatcher target;

  private View view2131231152;

  private View view2131231149;

  private View view2131230764;

  private View view2131230760;

  @UiThread
  public AirconditionerDispatcher_ViewBinding(final AirconditionerDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.switcher_open, "method 'onClick'");
    view2131231152 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.switcher_close, "method 'onClick'");
    view2131231149 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.airconditioner_plus, "method 'onClick'");
    view2131230764 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.airconditioner_minus, "method 'onClick'");
    view2131230760 = view;
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
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131231152.setOnClickListener(null);
    view2131231152 = null;
    view2131231149.setOnClickListener(null);
    view2131231149 = null;
    view2131230764.setOnClickListener(null);
    view2131230764 = null;
    view2131230760.setOnClickListener(null);
    view2131230760 = null;
  }
}
