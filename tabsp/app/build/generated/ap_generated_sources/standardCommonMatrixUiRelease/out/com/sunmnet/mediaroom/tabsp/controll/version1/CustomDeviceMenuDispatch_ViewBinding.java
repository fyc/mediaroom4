// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CustomDeviceMenuDispatch_ViewBinding implements Unbinder {
  private CustomDeviceMenuDispatch target;

  private View view2131230780;

  private View view2131230779;

  private View view2131230817;

  @UiThread
  public CustomDeviceMenuDispatch_ViewBinding(final CustomDeviceMenuDispatch target, View source) {
    this.target = target;

    View view;
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
    view = Utils.findRequiredView(source, R.id.commondevicegrid, "method 'onItemClick'");
    view2131230817 = view;
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


    view2131230780.setOnClickListener(null);
    view2131230780 = null;
    view2131230779.setOnClickListener(null);
    view2131230779 = null;
    ((AdapterView<?>) view2131230817).setOnItemClickListener(null);
    view2131230817 = null;
  }
}
