// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DeviceHolder_ViewBinding implements Unbinder {
  private DeviceHolder target;

  @UiThread
  public DeviceHolder_ViewBinding(DeviceHolder target, View source) {
    this.target = target;

    target.icon = Utils.findRequiredViewAsType(source, R.id.device_image, "field 'icon'", ImageView.class);
    target.holder = Utils.findRequiredView(source, R.id.device_image_holder, "field 'holder'");
  }

  @Override
  @CallSuper
  public void unbind() {
    DeviceHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.icon = null;
    target.holder = null;
  }
}
