// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TitleDispatcher_ViewBinding implements Unbinder {
  private TitleDispatcher target;

  private View view2131230808;

  private View view2131231155;

  private View view2131230945;

  private View view2131231156;

  @UiThread
  public TitleDispatcher_ViewBinding(final TitleDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.classRoomName, "field 'classRoomName' and method 'onClick'");
    target.classRoomName = Utils.castView(view, R.id.classRoomName, "field 'classRoomName'", TextView.class);
    view2131230808 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.date = Utils.findRequiredViewAsType(source, R.id.date, "field 'date'", TextView.class);
    target.time = Utils.findRequiredViewAsType(source, R.id.time, "field 'time'", Chronometer.class);
    view = Utils.findRequiredView(source, R.id.tabsp_logo, "field 'logo' and method 'onClick'");
    target.logo = Utils.castView(view, R.id.tabsp_logo, "field 'logo'", ImageView.class);
    view2131231155 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.languageswitcher, "method 'onClick'");
    view2131230945 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tabsp_report_fault, "method 'onClick'");
    view2131231156 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    TitleDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.classRoomName = null;
    target.date = null;
    target.time = null;
    target.logo = null;

    view2131230808.setOnClickListener(null);
    view2131230808 = null;
    view2131231155.setOnClickListener(null);
    view2131231155 = null;
    view2131230945.setOnClickListener(null);
    view2131230945 = null;
    view2131231156.setOnClickListener(null);
    view2131231156 = null;
  }
}
