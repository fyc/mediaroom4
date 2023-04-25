package com.sunmnet.mediaroom.matrix.anotherUi.adapter;

import android.view.View;
import android.widget.TextView;

import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.bean.MatrixSceneForGridView;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.IHolder;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_matrix.TabspMatrixSceneDto;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by dengzl_pc on 2018/3/22.
 */

public class MatrixSceneHolder implements IHolder<MatrixSceneForGridView>, Serializable {
    TextView textView;
    MatrixSceneForGridView property;

    public MatrixSceneHolder() {
    }

    @Override
    public void setView(View view) {
        textView = view.findViewById(R.id.value);
    }

    @Override
    public void setProperty(MatrixSceneForGridView property) {
        this.property = property;
        if(textView!=null)
        {
            textView.setText(property.getMatrixScene().sceneName);
        }
    }

    @Override
    public MatrixSceneForGridView getProperty() {
        return this.property;
    }

    @Override
    public void setIcon(int icon) {

    }

    @Override
    public void setIcon(Map<String, Integer> icons) {

    }
}
