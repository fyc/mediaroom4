// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginDispatcher_ViewBinding implements Unbinder {
  private LoginDispatcher target;

  private View view2131231155;

  private View view2131231092;

  private View view2131230945;

  private View view2131231146;

  @UiThread
  public LoginDispatcher_ViewBinding(final LoginDispatcher target, View source) {
    this.target = target;

    View view;
    target.contentView = Utils.findRequiredView(source, R.id.login_container, "field 'contentView'");
    view = Utils.findRequiredView(source, R.id.tabsp_logo, "field 'logo' and method 'onClick'");
    target.logo = Utils.castView(view, R.id.tabsp_logo, "field 'logo'", ImageView.class);
    view2131231155 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.inputInfo = Utils.findRequiredView(source, R.id.info_input, "field 'inputInfo'");
    target.loginResult = Utils.findRequiredViewAsType(source, R.id.loginResult, "field 'loginResult'", TextView.class);
    target.userName = Utils.findRequiredViewAsType(source, R.id.user_name, "field 'userName'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.user_password, "field 'password'", EditText.class);
    view = Utils.findRequiredView(source, R.id.scan_text, "field 'scanText' and method 'onClick'");
    target.scanText = Utils.castView(view, R.id.scan_text, "field 'scanText'", TextView.class);
    view2131231092 = view;
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
    view = Utils.findRequiredView(source, R.id.switchLogintype, "method 'onClick'");
    view2131231146 = view;
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
    LoginDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.contentView = null;
    target.logo = null;
    target.inputInfo = null;
    target.loginResult = null;
    target.userName = null;
    target.password = null;
    target.scanText = null;

    view2131231155.setOnClickListener(null);
    view2131231155 = null;
    view2131231092.setOnClickListener(null);
    view2131231092 = null;
    view2131230945.setOnClickListener(null);
    view2131230945 = null;
    view2131231146.setOnClickListener(null);
    view2131231146 = null;
  }
}
