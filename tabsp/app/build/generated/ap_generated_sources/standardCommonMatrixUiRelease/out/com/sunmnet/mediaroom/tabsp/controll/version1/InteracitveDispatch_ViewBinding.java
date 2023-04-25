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

public class InteracitveDispatch_ViewBinding implements Unbinder {
  private InteracitveDispatch target;

  private View view2131231147;

  private View view2131231211;

  private View view2131231210;

  private View view2131230775;

  private View view2131230935;

  private View view2131230932;

  @UiThread
  public InteracitveDispatch_ViewBinding(final InteracitveDispatch target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.switcher, "method 'onClick'");
    view2131231147 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.volumn_plus_btn, "method 'onClick'");
    view2131231211 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.volumn_minus_btn, "method 'onClick'");
    view2131231210 = view;
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
    view = Utils.findRequiredView(source, R.id.interactives, "method 'onListviewItemClick'");
    view2131230935 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onListviewItemClick(p0, p1, p2, p3);
      }
    });
    view = Utils.findRequiredView(source, R.id.interactive_modes, "method 'onModeClick'");
    view2131230932 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onModeClick(p0, p1, p2, p3);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131231147.setOnClickListener(null);
    view2131231147 = null;
    view2131231211.setOnClickListener(null);
    view2131231211 = null;
    view2131231210.setOnClickListener(null);
    view2131231210 = null;
    view2131230775.setOnClickListener(null);
    view2131230775 = null;
    ((AdapterView<?>) view2131230935).setOnItemClickListener(null);
    view2131230935 = null;
    ((AdapterView<?>) view2131230932).setOnItemClickListener(null);
    view2131230932 = null;
  }
}
