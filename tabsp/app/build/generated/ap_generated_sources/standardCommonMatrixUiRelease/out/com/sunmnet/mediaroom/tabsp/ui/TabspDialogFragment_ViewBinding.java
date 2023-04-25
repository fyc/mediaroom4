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

public class TabspDialogFragment_ViewBinding implements Unbinder {
  private TabspDialogFragment target;

  private View view2131230861;

  private View view2131230859;

  private View view2131230860;

  @UiThread
  public TabspDialogFragment_ViewBinding(final TabspDialogFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.dialog_confirm, "method 'onClick'");
    view2131230861 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.dialog_cancel, "method 'onClick'");
    view2131230859 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.dialog_close, "method 'onClick'");
    view2131230860 = view;
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


    view2131230861.setOnClickListener(null);
    view2131230861 = null;
    view2131230859.setOnClickListener(null);
    view2131230859 = null;
    view2131230860.setOnClickListener(null);
    view2131230860 = null;
  }
}
