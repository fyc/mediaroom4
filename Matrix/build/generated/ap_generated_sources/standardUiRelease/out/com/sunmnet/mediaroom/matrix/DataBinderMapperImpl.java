package com.sunmnet.mediaroom.matrix;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutBindingImpl;
import com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutBindingLandImpl;
import com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutEditBindingImpl;
import com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutEditBindingLandImpl;
import com.sunmnet.mediaroom.matrix.databinding.MatrixLayoutMainBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_MATRIXLAYOUT = 1;

  private static final int LAYOUT_MATRIXLAYOUTEDIT = 2;

  private static final int LAYOUT_MATRIXLAYOUTMAIN = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.matrix.R.layout.matrix_layout, LAYOUT_MATRIXLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.matrix.R.layout.matrix_layout_edit, LAYOUT_MATRIXLAYOUTEDIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.matrix.R.layout.matrix_layout_main, LAYOUT_MATRIXLAYOUTMAIN);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_MATRIXLAYOUT: {
          if ("layout/matrix_layout_0".equals(tag)) {
            return new MatrixLayoutBindingImpl(component, view);
          }
          if ("layout-land/matrix_layout_0".equals(tag)) {
            return new MatrixLayoutBindingLandImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for matrix_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_MATRIXLAYOUTEDIT: {
          if ("layout/matrix_layout_edit_0".equals(tag)) {
            return new MatrixLayoutEditBindingImpl(component, view);
          }
          if ("layout-land/matrix_layout_edit_0".equals(tag)) {
            return new MatrixLayoutEditBindingLandImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for matrix_layout_edit is invalid. Received: " + tag);
        }
        case  LAYOUT_MATRIXLAYOUTMAIN: {
          if ("layout/matrix_layout_main_0".equals(tag)) {
            return new MatrixLayoutMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for matrix_layout_main is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(3);
    result.add(new com.android.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new com.sunmnet.mediaroom.common.DataBinderMapperImpl());
    result.add(new com.sunmnet.mediaroom.device.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(5);

    static {
      sKeys.put("layout/matrix_layout_0", com.sunmnet.mediaroom.matrix.R.layout.matrix_layout);
      sKeys.put("layout-land/matrix_layout_0", com.sunmnet.mediaroom.matrix.R.layout.matrix_layout);
      sKeys.put("layout/matrix_layout_edit_0", com.sunmnet.mediaroom.matrix.R.layout.matrix_layout_edit);
      sKeys.put("layout-land/matrix_layout_edit_0", com.sunmnet.mediaroom.matrix.R.layout.matrix_layout_edit);
      sKeys.put("layout/matrix_layout_main_0", com.sunmnet.mediaroom.matrix.R.layout.matrix_layout_main);
    }
  }
}
