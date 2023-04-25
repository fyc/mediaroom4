// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.controll.version3;

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

public class V3TitleDispatcher_ViewBinding implements Unbinder {
  private V3TitleDispatcher target;

  private View view2131230945;

  private View view2131230840;

  @UiThread
  public V3TitleDispatcher_ViewBinding(final V3TitleDispatcher target, View source) {
    this.target = target;

    View view;
    target.classRoomName = Utils.findRequiredViewAsType(source, R.id.classRoomName, "field 'classRoomName'", TextView.class);
    target.date = Utils.findRequiredViewAsType(source, R.id.date, "field 'date'", TextView.class);
    target.time = Utils.findRequiredViewAsType(source, R.id.time, "field 'time'", Chronometer.class);
    target.logo = Utils.findRequiredViewAsType(source, R.id.tabsp_logo, "field 'logo'", ImageView.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.detail_changer, "field 'imageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.languageswitcher, "method 'onClick'");
    view2131230945 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.detail_switcher, "method 'onClick'");
    view2131230840 = view;
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
    V3TitleDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.classRoomName = null;
    target.date = null;
    target.time = null;
    target.logo = null;
    target.imageView = null;

    view2131230945.setOnClickListener(null);
    view2131230945 = null;
    view2131230840.setOnClickListener(null);
    view2131230840 = null;
  }
}
