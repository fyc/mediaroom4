// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MenuDispatcher_ViewBinding implements Unbinder {
  private MenuDispatcher target;

  private View view2131230986;

  @UiThread
  public MenuDispatcher_ViewBinding(final MenuDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.menu, "field 'menuGrid' and method 'onItemClick'");
    target.menuGrid = Utils.castView(view, R.id.menu, "field 'menuGrid'", GridView.class);
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
    MenuDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.menuGrid = null;

    ((AdapterView<?>) view2131230986).setOnItemClickListener(null);
    view2131230986 = null;
  }
}
