// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.controll.version3.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TypeAdapter$NormalDeviceHolder_ViewBinding implements Unbinder {
  private TypeAdapter.NormalDeviceHolder target;

  private View view2131231122;

  private View view2131231121;

  @UiThread
  public TypeAdapter$NormalDeviceHolder_ViewBinding(final TypeAdapter.NormalDeviceHolder target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.single_open, "method 'onClick'");
    view2131231122 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.single_close, "method 'onClick'");
    view2131231121 = view;
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


    view2131231122.setOnClickListener(null);
    view2131231122 = null;
    view2131231121.setOnClickListener(null);
    view2131231121 = null;
  }
}
