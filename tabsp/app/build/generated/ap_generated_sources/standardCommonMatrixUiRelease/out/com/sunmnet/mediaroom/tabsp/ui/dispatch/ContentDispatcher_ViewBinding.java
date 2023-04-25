// Generated code from Butter Knife. Do not modify!
package com.sunmnet.mediaroom.tabsp.ui.dispatch;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.sunmnet.mediaroom.tabsp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ContentDispatcher_ViewBinding implements Unbinder {
  private ContentDispatcher target;

  @UiThread
  public ContentDispatcher_ViewBinding(ContentDispatcher target, View source) {
    this.target = target;

    target.coverLayout = Utils.findRequiredView(source, R.id.coverLayout, "field 'coverLayout'");
    target.contentLayout = Utils.findRequiredView(source, R.id.contentPart, "field 'contentLayout'");
  }

  @Override
  @CallSuper
  public void unbind() {
    ContentDispatcher target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.coverLayout = null;
    target.contentLayout = null;
  }
}
