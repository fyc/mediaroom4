// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Version3DeviceControllDispatcher_ViewBinding implements Unbinder {
  private Version3DeviceControllDispatcher target;

  private View view2131230780;

  private View view2131230779;

  @UiThread
  public Version3DeviceControllDispatcher_ViewBinding(final Version3DeviceControllDispatcher target,
      View source) {
    this.target = target;

    View view;
    target.textView = Utils.findRequiredViewAsType(source, R.id.deviceTypeName, "field 'textView'", TextView.class);
    target.listView = Utils.findRequiredViewAsType(source, R.id.device_menu, "field 'listView'", ListView.class);
    view = Utils.findRequiredView(source, R.id.batch_open, "method 'onClick'");
    view2131230780 = view;
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
  }

  @Override
  @CallSuper
  public void unbind() {
    Version3DeviceControllDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView = null;
    target.listView = null;

    view2131230780.setOnClickListener(null);
    view2131230780 = null;
    view2131230779.setOnClickListener(null);
    view2131230779 = null;
  }
}
