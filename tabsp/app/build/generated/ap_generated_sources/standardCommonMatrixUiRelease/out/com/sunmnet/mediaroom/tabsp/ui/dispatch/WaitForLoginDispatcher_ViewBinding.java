// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WaitForLoginDispatcher_ViewBinding implements Unbinder {
  private WaitForLoginDispatcher target;

  private View view2131231146;

  private View view2131231155;

  private View view2131231172;

  private View view2131230945;

  @UiThread
  public WaitForLoginDispatcher_ViewBinding(final WaitForLoginDispatcher target, View source) {
    this.target = target;

    View view;
    target.contentView = Utils.findRequiredView(source, R.id.login_container, "field 'contentView'");
    target.qrcode = Utils.findRequiredViewAsType(source, R.id.qrcode, "field 'qrcode'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.switchLogintype, "field 'switchLoginType' and method 'onClick'");
    target.switchLoginType = view;
    view2131231146 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tabsp_logo, "field 'logo' and method 'onClick'");
    target.logo = Utils.castView(view, R.id.tabsp_logo, "field 'logo'", ImageView.class);
    view2131231155 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.spinning = Utils.findRequiredView(source, R.id.spinning, "field 'spinning'");
    target.qrcodeContainer = Utils.findRequiredView(source, R.id.qrcode_container, "field 'qrcodeContainer'");
    target.allowcard = Utils.findRequiredView(source, R.id.allowcard, "field 'allowcard'");
    view = Utils.findRequiredView(source, R.id.tips, "field 'qrCodeTips' and method 'onClick'");
    target.qrCodeTips = Utils.castView(view, R.id.tips, "field 'qrCodeTips'", TextView.class);
    view2131231172 = view;
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
  }

  @Override
  @CallSuper
  public void unbind() {
    WaitForLoginDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.contentView = null;
    target.qrcode = null;
    target.switchLoginType = null;
    target.logo = null;
    target.spinning = null;
    target.qrcodeContainer = null;
    target.allowcard = null;
    target.qrCodeTips = null;

    view2131231146.setOnClickListener(null);
    view2131231146 = null;
    view2131231155.setOnClickListener(null);
    view2131231155 = null;
    view2131231172.setOnClickListener(null);
    view2131231172 = null;
    view2131230945.setOnClickListener(null);
    view2131230945 = null;
  }
}
