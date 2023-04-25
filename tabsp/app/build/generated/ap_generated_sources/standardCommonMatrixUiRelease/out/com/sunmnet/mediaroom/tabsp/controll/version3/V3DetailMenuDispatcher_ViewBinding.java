// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class V3DetailMenuDispatcher_ViewBinding implements Unbinder {
  private V3DetailMenuDispatcher target;

  private View view2131230986;

  @UiThread
  public V3DetailMenuDispatcher_ViewBinding(final V3DetailMenuDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.menu, "field 'menuView' and method 'onItemClick'");
    target.menuView = Utils.castView(view, R.id.menu, "field 'menuView'", ListView.class);
    view2131230986 = view;
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
    V3DetailMenuDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.menuView = null;

    ((AdapterView<?>) view2131230986).setOnItemClickListener(null);
    view2131230986 = null;
  }
}
