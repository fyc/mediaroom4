// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingDispatcher_ViewBinding implements Unbinder {
  private SettingDispatcher target;

  private View view2131231114;

  @UiThread
  public SettingDispatcher_ViewBinding(final SettingDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.setting_menu, "method 'onItemClick'");
    view2131231114 = view;
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
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    ((AdapterView<?>) view2131231114).setOnItemClickListener(null);
    view2131231114 = null;
  }
}
