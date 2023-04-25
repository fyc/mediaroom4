// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import com.warkiz.widget.IndicatorSeekBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WirelessDispatcher_ViewBinding implements Unbinder {
  private WirelessDispatcher target;

  private View view2131231123;

  private View view2131230998;

  private View view2131230786;

  private View view2131230787;

  private View view2131230905;

  private View view2131230967;

  private View view2131231214;

  private View view2131230900;

  private View view2131231142;

  private View view2131230960;

  private View view2131231207;

  private View view2131231079;

  @UiThread
  public WirelessDispatcher_ViewBinding(final WirelessDispatcher target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.singleton, "field 'single' and method 'onClick'");
    target.single = view;
    view2131231123 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.muiltiple, "field 'muilt' and method 'onClick'");
    target.muilt = view;
    view2131230998 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.title = Utils.findRequiredViewAsType(source, R.id.showing_mode, "field 'title'", TextView.class);
    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.wire_device_drawer, "field 'drawerLayout'", DrawerLayout.class);
    target.volumn_drawerLayout = Utils.findRequiredViewAsType(source, R.id.volumn_drawerLayout, "field 'volumn_drawerLayout'", DrawerLayout.class);
    target.volumn = Utils.findRequiredViewAsType(source, R.id.volumn_seeker, "field 'volumn'", IndicatorSeekBar.class);
    target.drawer_listview = Utils.findRequiredViewAsType(source, R.id.drawer_listview, "field 'drawer_listview'", ListView.class);
    target.bottomView = Utils.findRequiredView(source, R.id.bottom_content, "field 'bottomView'");
    target.tv_stop = Utils.findRequiredViewAsType(source, R.id.tv_stop, "field 'tv_stop'", TextView.class);
    target.icon_stop = Utils.findRequiredViewAsType(source, R.id.icon_stop, "field 'icon_stop'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.broadcast_all, "method 'onClick'");
    view2131230786 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.broadcast_part, "method 'onClick'");
    view2131230787 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.finish, "method 'onClick'");
    view2131230905 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.mark, "method 'onClick'");
    view2131230967 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.whiteboard, "method 'onClick'");
    view2131231214 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.file, "method 'onClick'");
    view2131230900 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.stop, "method 'onClick'");
    view2131231142 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.locker, "method 'onClick'");
    view2131230960 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.volumn, "method 'onClick'");
    view2131231207 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.restart_app, "method 'onClick'");
    view2131231079 = view;
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
    WirelessDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.single = null;
    target.muilt = null;
    target.title = null;
    target.drawerLayout = null;
    target.volumn_drawerLayout = null;
    target.volumn = null;
    target.drawer_listview = null;
    target.bottomView = null;
    target.tv_stop = null;
    target.icon_stop = null;

    view2131231123.setOnClickListener(null);
    view2131231123 = null;
    view2131230998.setOnClickListener(null);
    view2131230998 = null;
    view2131230786.setOnClickListener(null);
    view2131230786 = null;
    view2131230787.setOnClickListener(null);
    view2131230787 = null;
    view2131230905.setOnClickListener(null);
    view2131230905 = null;
    view2131230967.setOnClickListener(null);
    view2131230967 = null;
    view2131231214.setOnClickListener(null);
    view2131231214 = null;
    view2131230900.setOnClickListener(null);
    view2131230900 = null;
    view2131231142.setOnClickListener(null);
    view2131231142 = null;
    view2131230960.setOnClickListener(null);
    view2131230960 = null;
    view2131231207.setOnClickListener(null);
    view2131231207 = null;
    view2131231079.setOnClickListener(null);
    view2131231079 = null;
  }
}
