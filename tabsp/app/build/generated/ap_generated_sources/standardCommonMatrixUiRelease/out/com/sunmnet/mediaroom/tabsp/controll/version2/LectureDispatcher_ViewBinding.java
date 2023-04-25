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

public class LectureDispatcher_ViewBinding implements Unbinder {
  private LectureDispatcher target;

  private View view2131230948;

  @UiThread
  public LectureDispatcher_ViewBinding(final LectureDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lecture_grid, "method 'onItemClick'");
    view2131230948 = view;
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


    ((AdapterView<?>) view2131230948).setOnItemClickListener(null);
    view2131230948 = null;
  }
}
