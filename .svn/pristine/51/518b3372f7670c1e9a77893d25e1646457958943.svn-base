package com.sunmnet.mediaroom.matrix.anotherUi.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.OnMatrixCheckListener;
import com.sunmnet.mediaroom.matrix.utils.MatrixUtils;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_matrix.TabspMatrixDto;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_matrix.TabspMatrixSceneDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengzl_pc on 2018/3/22.
 */

public class MatrixTableLayout extends LinearLayout implements OnMatrixCheckListener {
    LayoutParams params;//参数
    TabspMatrixDto matrixDto;//实体类
    TabspMatrixSceneDto templateScene;//临时场景设置
    int clickedRow = -1, //上一次选中的行号
            clickedColumn = -1;//上一次选中的列号
    Map<String, String> inputMap,outputMap;
    public MatrixTableLayout(Context context) {
        super(context);
        init();
    }

    public MatrixTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Map<Integer, Integer> outputWithinput;

    private void init() {
        this.params = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        this.params.weight = 1;
        this.setOrientation(HORIZONTAL);
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        for (int i = 0; i < 9; i++) {
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(VERTICAL);
            this.addView(layout, params);
        }
    }
    public void changeTemplateScene(TabspMatrixSceneDto sceneDto){
        this.templateScene=sceneDto;
    }
    /**
     * 更新当前场景的选中状态
     */
    private void updateMatrix(int column, LinearLayout layout, List<String> string) {
        if (string == null) string = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String key = i + "";
            MatrixImageView imageView = null;
            if (i >= layout.getChildCount()) {
                imageView = new MatrixImageView(getContext(), true, true, column, i);
                layout.addView(imageView, this.params);
                imageView.setOnMatrixCheckListener(this);
            } else
                imageView = (MatrixImageView) layout.getChildAt(i);
            if(this.inputMap.containsKey(key)
                    &&this.outputMap.containsKey(column+""))
            {
                imageView.setVisibility(View.VISIBLE);
                if (string.contains(key)) {
                    this.outputWithinput.put(i, column);
                    imageView.setCheck(true);
                } else
                    imageView.setCheck(false);
            }else
                imageView.setVisibility(View.INVISIBLE);

        }
    }


    /**
     * 设置表格竖向的值(输出口名称)
     */
    private void setOutput(Map<String, String> outputMap, LinearLayout layout) {
        if (layout.getChildCount() <= 0) {
            DiagonalView view = new DiagonalView(getContext());
            layout.addView(view, this.params);
        }
        MatrixTextView textView = null;
        for (int i = 1; i <= 8; i++) {
            if (i >= layout.getChildCount()) {
                textView = new MatrixTextView(getContext(), true, true);
                layout.addView(textView, this.params);
            } else textView = (MatrixTextView) layout.getChildAt(i);
            String key = (i + "");
            textView.setText(outputMap.get(key));
        }
    }
    public void initRow(Map<String, String> outputs){
        this.outputMap=outputs;
        this.setOutput(outputs, (LinearLayout) this.getChildAt(0));
    }
    private Map<String, String> fillMap(Map<String, String> maps){
        if(maps.size()<8)
        {
            Map<String, String> news=new HashMap<>();
            news.putAll(maps);
            int start=maps.size()+1;
            for (int i = start; i <=8 ; i++) {
                String key=i+"";
                news.put(key,null);
            }
            maps=news;
        }
        return maps;
    }
    /**
     * 设置列数据
     * */
    public void initColumn(Map<String, String> inputs){
        this.inputMap=inputs;
        //inputs=fillMap(inputs);
        for (int i = 1; i <= 8; i++) {
            LinearLayout layout = (LinearLayout) this.getChildAt(i);
            this.setInput(i, layout, inputs,null);
        }
    }
    /**
     * 横向设置值(输入口名称)
     */
    private void setInput(int column, LinearLayout layout, Map<String, String> inputMap, List<String> selected) {
        MatrixTextView textView = null;
        if (layout.getChildCount() <= 0) {
            if(column==0)
            {
                textView = new MatrixTextView(getContext(), true, false);
            }else{
                textView = new MatrixTextView(getContext(), true, true);
            }
            layout.addView(textView, this.params);
        } else {
            textView = (MatrixTextView) layout.getChildAt(0);
        }
        String res=inputMap.get((column + ""));
        res=res==null?"":res;
        textView.setText(res);
        //updateMatrix(column, layout, selected);
    }
    public void setScene(TabspMatrixSceneDto scene){
        this.templateScene = MatrixUtils.copy(scene);
        outputWithinput = new HashMap<>();
        for (int i = 1; i <= 8; i++) {
            LinearLayout layout = (LinearLayout) this.getChildAt(i);
            updateMatrix(i, layout, scene.getInterfaces().get(i+""));
        }
    }
    /**
     * 设置列
     */
    public void setMatrix(TabspMatrixDto matrixDto, TabspMatrixSceneDto scene) {
        this.matrixDto = matrixDto;
        if (scene == null) scene = new TabspMatrixSceneDto(new LinkedHashMap<String, List<String>>(),"");
        this.templateScene = MatrixUtils.copy(scene);
        outputWithinput = new HashMap<>();
        this.setOutput(this.matrixDto.getOutputMap(), (LinearLayout) getChildAt(0));
        for (int i = 1; i <= 8; i++) {
            LinearLayout layout = (LinearLayout) this.getChildAt(i);
            List<String> values = null;
            if (!scene.getInterfaces().containsKey("" + i))
                values = new ArrayList<>();
            else values = scene.getInterfaces().get("" + i);
            this.setInput(i, layout, this.matrixDto.getInputMap(), values);
        }
    }

    public void switchScene(TabspMatrixSceneDto scene) {
        this.templateScene = MatrixUtils.copy(scene);
        for (int i = 1; i <= 8; i++) {
            LinearLayout layout = (LinearLayout) this.getChildAt(i);
            List<String> strings = this.templateScene.getInterfaces().get(i + "");
            updateMatrix(i, layout, strings);
        }
    }

    public TabspMatrixSceneDto getCurrentInterface() {
        return this.templateScene;
    }

    /**
     * 操作后更新实体类的设置值
     *
     * @param input  输入接口key
     * @param output 输出接口 key
     * @param isAdd  true为设置 false 为移除值
     */
    private void updateInterface(int input, int output, boolean isAdd) {
        String inputKey = input + "", outputKey = output + "";
        List<String> values = this.templateScene.getInterfaces().get(inputKey);
        if (values == null) {
            values = new ArrayList<>();
            this.templateScene.getInterfaces().put(inputKey, values);
        }
        if (isAdd) {
            if (!values.contains(outputKey)) values.add(outputKey);
        } else
            values.remove(outputKey);
    }

    @Override
    public void onCheck(boolean ischeck, int input, int output, View view) {
        String s = "输入口:" + input + "  输出口:" + output;
        String result = ischeck ? "选中" + s : "取消选中:" + s;
        RunningLog.debug(result);
        LinearLayout layout = null;
        if (ischeck) {
            MatrixImageView imageView = null;
            if (this.outputWithinput.containsKey(output)) {
                //取消上一个的选中
                int preKey = this.outputWithinput.get(output);
                layout = (LinearLayout) this.getChildAt(preKey);
                imageView = (MatrixImageView) layout.getChildAt(output);
                imageView.setCheck(false);
                updateInterface(preKey, output, false);
            }
            layout = (LinearLayout) this.getChildAt(input);
            imageView = (MatrixImageView) layout.getChildAt(output);
            imageView.setCheck(true);
            updateInterface(input, output, true);
            this.outputWithinput.put(output, input);
        } else {
            this.outputWithinput.remove(output);
            updateInterface(input, output, false);
        }
        if (clickedColumn != -1) {
            layout = (LinearLayout) this.getChildAt(clickedColumn);
            layout.setBackgroundResource(0);
        }
        layout = (LinearLayout) this.getChildAt(input);
        layout.setBackgroundResource(R.drawable.radius_15_transparent_drawable);
        for (int i = 0; i < this.getChildCount(); i++) {
            layout = (LinearLayout) this.getChildAt(i);
            View row = null;
            if (clickedRow != -1) {
                row = layout.getChildAt(clickedRow);
                row.setBackgroundResource(0);
            }
            if (i != input)//前面的列已经设置了相同的透明背景，如果重复设置会导致背景颜色加深
            {
                row = layout.getChildAt(output);
                row.setBackgroundResource(R.drawable.radius_15_transparent_drawable);
            }
        }
        this.clickedRow = output;
        this.clickedColumn = input;
    }

}
