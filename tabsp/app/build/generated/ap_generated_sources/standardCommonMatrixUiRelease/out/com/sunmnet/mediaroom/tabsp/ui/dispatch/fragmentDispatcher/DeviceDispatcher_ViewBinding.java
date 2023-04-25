// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceDispatcher_ViewBinding implements Unbinder {
  private DeviceDispatcher target;

  private View view2131230817;

  private View view2131230775;

  private View view2131230779;

  private View view2131230780;

  @UiThread
  public DeviceDispatcher_ViewBinding(final DeviceDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.commondevicegrid, "field 'deviceList' and method 'onItemClick'");
    target.deviceList = Utils.castView(view, R.id.commondevicegrid, "field 'deviceList'", GridView.class);
    view2131230817 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onItemClick(p0, p1, p2, p3);
      }
    });
    target.setting = Utils.findRequiredViewAsType(source, R.id.device_setting_text, "field 'setting'", TextView.class);
    view = Utils.findRequiredView(source, R.id.back_btn, "field 'back' and method 'onClick'");
    target.back = view;
    view2131230775 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.batch_close, "method 'onClick'");
    view2131230779 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.batch_open, "method 'onClick'");
    view2131230780 = view;
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
    DeviceDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.deviceList = null;
    target.setting = null;
    target.back = null;

    ((AdapterView<?>) view2131230817).setOnItemClickListener(null);
    view2131230817 = null;
    view2131230775.setOnClickListener(null);
    view2131230775 = null;
    view2131230779.setOnClickListener(null);
    view2131230779 = null;
    view2131230780.setOnClickListener(null);
    view2131230780 = null;
  }
}
