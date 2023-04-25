// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RateDialogFragment_ViewBinding implements Unbinder {
  private RateDialogFragment target;

  private View view2131230815;

  private View view2131230818;

  private View view2131230797;

  @UiThread
  public RateDialogFragment_ViewBinding(final RateDialogFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.close, "method 'onClick'");
    view2131230815 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.confirm, "method 'onClick'");
    view2131230818 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.cancel, "method 'onClick'");
    view2131230797 = view;
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


    view2131230815.setOnClickListener(null);
    view2131230815 = null;
    view2131230818.setOnClickListener(null);
    view2131230818 = null;
    view2131230797.setOnClickListener(null);
    view2131230797 = null;
  }
}
