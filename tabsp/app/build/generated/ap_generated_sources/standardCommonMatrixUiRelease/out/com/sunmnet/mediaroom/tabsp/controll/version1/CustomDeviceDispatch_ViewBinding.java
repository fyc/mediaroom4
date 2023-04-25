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

public class CustomDeviceDispatch_ViewBinding implements Unbinder {
  private CustomDeviceDispatch target;

  private View view2131231152;

  private View view2131231149;

  private View view2131230775;

  private View view2131230843;

  private View view2131231124;

  private View view2131230908;

  @UiThread
  public CustomDeviceDispatch_ViewBinding(final CustomDeviceDispatch target, View source) {
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
    view = Utils.findRequiredView(source, R.id.back_btn, "method 'onClick'");
    view2131230775 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.deviceListView, "method 'onListViewItemClick'");
    view2131230843 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onListViewItemClick(p0, p1, p2, p3);
      }
    });
    view = Utils.findRequiredView(source, R.id.source, "method 'onComponentItemClick'");
    view2131231124 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onComponentItemClick(p0, p1, p2, p3);
      }
    });
    view = Utils.findRequiredView(source, R.id.function, "method 'onComponentItemClick'");
    view2131230908 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onComponentItemClick(p0, p1, p2, p3);
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
    view2131230775.setOnClickListener(null);
    view2131230775 = null;
    ((AdapterView<?>) view2131230843).setOnItemClickListener(null);
    view2131230843 = null;
    ((AdapterView<?>) view2131231124).setOnItemClickListener(null);
    view2131231124 = null;
    ((AdapterView<?>) view2131230908).setOnItemClickListener(null);
    view2131230908 = null;
  }
}
