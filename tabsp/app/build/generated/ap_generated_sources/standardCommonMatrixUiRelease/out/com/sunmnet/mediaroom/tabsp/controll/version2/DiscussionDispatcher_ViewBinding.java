// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.controll.version2;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DiscussionDispatcher_ViewBinding implements Unbinder {
  private DiscussionDispatcher target;

  private View view2131230930;

  private View view2131230931;

  @UiThread
  public DiscussionDispatcher_ViewBinding(final DiscussionDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.inputGrid, "method 'onItemClick'");
    view2131230930 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onItemClick(p0, p1, p2, p3);
      }
    });
    view = Utils.findRequiredView(source, R.id.inputGroup, "method 'onItemClick'");
    view2131230931 = view;
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


    ((AdapterView<?>) view2131230930).setOnItemClickListener(null);
    view2131230930 = null;
    ((AdapterView<?>) view2131230931).setOnItemClickListener(null);
    view2131230931 = null;
  }
}
