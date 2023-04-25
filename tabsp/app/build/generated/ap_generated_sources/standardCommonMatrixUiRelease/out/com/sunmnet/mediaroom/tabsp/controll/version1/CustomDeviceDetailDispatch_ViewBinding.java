// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CustomDeviceDetailDispatch_ViewBinding implements Unbinder {
  private CustomDeviceDetailDispatch target;

  private View view2131231147;

  private View view2131230775;

  private View view2131231124;

  private View view2131230908;

  @UiThread
  public CustomDeviceDetailDispatch_ViewBinding(final CustomDeviceDetailDispatch target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.switcher, "method 'onCheckedChanged'");
    view2131231147 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.back_btn, "method 'onClick'");
    view2131230775 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.source, "method 'onComponentItemClick'");
    view2131231124 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onComponentItemClick(p0, p1, p2, p3);
      }
    });
    view = Utils.findRequiredView(source, R.id.function, "method 'onComponentItemClick'");
    view2131230908 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onComponentItemClick(p0, p1, p2, p3);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    ((CompoundButton) view2131231147).setOnCheckedChangeListener(null);
    view2131231147 = null;
    view2131230775.setOnClickListener(null);
    view2131230775 = null;
    ((AdapterView<?>) view2131231124).setOnItemClickListener(null);
    view2131231124 = null;
    ((AdapterView<?>) view2131230908).setOnItemClickListener(null);
    view2131230908 = null;
  }
}
