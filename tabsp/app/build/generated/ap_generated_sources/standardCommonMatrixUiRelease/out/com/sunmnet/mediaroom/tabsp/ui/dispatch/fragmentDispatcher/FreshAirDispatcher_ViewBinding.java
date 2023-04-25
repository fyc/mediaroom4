// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

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

public class FreshAirDispatcher_ViewBinding implements Unbinder {
  private FreshAirDispatcher target;

  private View view2131231152;

  private View view2131231149;

  private View view2131230858;

  private View view2131230907;

  @UiThread
  public FreshAirDispatcher_ViewBinding(final FreshAirDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.switcher_open, "method 'onClick'");
    view2131231152 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.switcher_close, "method 'onClick'");
    view2131231149 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.devicelist, "method 'onDeviceSelect'");
    view2131230858 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onDeviceSelect(p0, p1, p2, p3);
      }
    });
    view = Utils.findRequiredView(source, R.id.freshair_mode, "method 'onModeSelect'");
    view2131230907 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onModeSelect(p0, p1, p2, p3);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131231152.setOnClickListener(null);
    view2131231152 = null;
    view2131231149.setOnClickListener(null);
    view2131231149 = null;
    ((AdapterView<?>) view2131230858).setOnItemClickListener(null);
    view2131230858 = null;
    ((AdapterView<?>) view2131230907).setOnItemClickListener(null);
    view2131230907 = null;
  }
}
