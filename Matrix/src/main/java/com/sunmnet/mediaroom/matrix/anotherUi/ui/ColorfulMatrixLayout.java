package com.sunmnet.mediaroom.matrix.anotherUi.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.tools.AndroidUtils;
import com.sunmnet.mediaroom.common.tools.GsonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.interfaces.IMatrixLayout;
import com.sunmnet.mediaroom.matrix.utils.MatrixUtils;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_matrix.TabspMatrixSceneDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dengzl on 2018/11/19.
 */

public class ColorfulMatrixLayout extends RelativeLayout implements IMatrixLayout {

    TabspMatrixSceneDto scene;
    MatrixChangedListener listener;
    Map<String, LinearLayout> inputs, outputs;
    Map<String, String> outputWithInput;
    Paint paint;
    // 输入端LinearLayout初始坐标X值
    private int inputLinearLayoutFirstX = 0;
    // 输出端LinearLayout初始坐标X值
    private int outputLinearLayoutFirstX = 0;

    public ColorfulMatrixLayout(Context context) {
        super(context);
        init();
    }

    public ColorfulMatrixLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorfulMatrixLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inputs = new HashMap<>();
        outputs = new HashMap<>();
        outputWithInput = new HashMap<>();
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_2167DA));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.px_3));
        setWillNotDraw(false);
    }

    @Override
    public void setOnVisible() {
        this.reloadImage((LinearLayout) findViewById(R.id.matrix_input_layout));
        this.reloadImage((LinearLayout) findViewById(R.id.matrix_output_layout));
    }

    private void reloadImage(LinearLayout linearLayout) {
        if (linearLayout != null) {
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View view = linearLayout.getChildAt(i);
                View imgView = view.findViewById(R.id.item_image);
                if (imgView != null) {
                    ((ImageView) imgView).setImageDrawable(getResources().getDrawable(R.drawable.matrix_logo_bg));
                }
            }
        }
    }

    @Override
    public void setMatrixScene(TabspMatrixSceneDto scene) {
        RunningLog.run("设置场景:" + GsonUtil.objToJsonStr(scene));
        this.scene = scene;
        Map<String, List<String>> scenes = this.scene.getInterfaces();
        this.outputWithInput.clear();
        onItemSelected(lastInput, false);
        lastInput = null;
        //用于选中输出口时方便更新数据
        for (Map.Entry<String, List<String>> entry : scenes.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);
                outputWithInput.put(value, key);
            }
        }
        Set<Map.Entry<String, LinearLayout>> layoutEntries = outputs.entrySet();
        for (Map.Entry<String, LinearLayout> entry : layoutEntries) {
            onItemSelected(entry.getValue(), false);
        }
        this.postInvalidate();
    }

    private LinearLayout addLinearLayout(boolean isAlgnTop) {
        // 添加HorizontalScrollView
        MyHorizontalScrollView scrollView = new MyHorizontalScrollView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (isAlgnTop) {
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            scrollView.setId(R.id.matrix_input_scroll_view);
            scrollView.setScrollChangedListener((l, t, oldl, oldt) -> postInvalidate());
        } else {
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            scrollView.setId(R.id.matrix_output_scroll_view);
            scrollView.setScrollChangedListener((l, t, oldl, oldt) -> postInvalidate());
        }
        this.addView(scrollView, params);

        // 添加LinearLayout
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (isAlgnTop) {
            layout.setId(R.id.matrix_input_layout);
        } else {
            layout.setId(R.id.matrix_output_layout);
        }
        scrollView.addView(layout, params);
        if (isAlgnTop) {
            int[] location = new int[2];
            layout.getLocationOnScreen(location);
            inputLinearLayoutFirstX = location[0];
        } else {
            int[] location = new int[2];
            layout.getLocationOnScreen(location);
            outputLinearLayoutFirstX = location[0];
        }
        scrollView.setHorizontalScrollBarEnabled(false);
        return layout;
    }

    private void setLayoutParam(LinearLayout layout, Map<String, String> tags,
                                Map<String, LinearLayout> values, OnClickListener listener) {
        if (layout.getChildCount() > tags.size()) {
            int size = layout.getChildCount() - tags.size();
            while (size > 0) {
                String index = layout.getChildCount() + "";
                layout.removeViewAt(layout.getChildCount() - 1);
                if (values.containsKey(index)) {
                    values.remove(index);
                }
                size--;
            }
        }
        Map<String, Integer> rects = new HashMap<>();
        rects.put("width", AndroidUtils.dip2px(getContext(), 92));
        rects.put("height", AndroidUtils.dip2px(getContext(), 69));
        rects.put("margin", AndroidUtils.dip2px(getContext(), getResources().getDimensionPixelOffset(R.dimen.px_17)));
        // mesureRect(rects, getWidth() - getPaddingLeft() - getPaddingRight(), tags.size());
        for (int i = 0; i < tags.size(); i++) {
            String key = (i + 1) + "";
            LinearLayout interfaces;
            if (i < layout.getChildCount()) {
                interfaces = (LinearLayout) layout.getChildAt(i);
            } else {
                interfaces = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.matrix_item, null, false);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                AndroidUtils.dip2px(getContext(), getResources().getDimensionPixelOffset(R.dimen.px_90)),
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i != 0) {
                    param.leftMargin= rects.get("margin");
                }
                layout.addView(interfaces, param);
                values.put(key, interfaces);
                interfaces.setOnClickListener(listener);
                ImageView iv = interfaces.findViewById(R.id.item_image);
                if (iv != null) iv.setImageDrawable(getResources().getDrawable(R.drawable.matrix_logo_bg));
            }
            interfaces.setTag(key);
            String tagName = tags.get(key);
            TextView tv = interfaces.findViewById(R.id.item_text);
            tv.setText(tagName);
            layout.setVisibility(INVISIBLE);
        }

        layout.post(() -> {
            // 适配输入输出端宽度小于父控件宽度
            if (layout.getWidth() < getWidth()) {
                int usefulWith = getWidth() - getPaddingLeft() - getPaddingRight() - layout.getPaddingLeft() - layout.getPaddingRight();
                int perWidth = Math.round(usefulWith * 1.0f / tags.size());
                MarginLayoutParams params = (MarginLayoutParams) layout.getChildAt(0).getLayoutParams();
                int maxWith = AndroidUtils.dip2px(getContext(), getResources().getDimensionPixelOffset(R.dimen.px_90));
                if (perWidth > maxWith) {
                    params.width = maxWith;
                    int leftAndRightMargin = perWidth - maxWith;
                    params.leftMargin = leftAndRightMargin / 2;
                    params.rightMargin = leftAndRightMargin / 2;
                } else {
                    params.width = perWidth - params.leftMargin - params.rightMargin;
                }
                for (int i = 0; i < layout.getChildCount(); i++) {
                    LinearLayout childView = (LinearLayout) layout.getChildAt(i);
                    childView.setLayoutParams(params);
                }
            }
            layout.setVisibility(VISIBLE);
        });
    }

    private void mesureRect(Map<String, Integer> rects, int containerWidth, int count) {
        int width = rects.get("width"), height = rects.get("height"), margin = rects.get("margin");
        int totalWidth = width * count + margin * (count - 1);
        if (totalWidth > containerWidth) {
            //1.当前margin是以8个进出口设计，
            margin = Math.round(margin * (10 / (count * 1.0f)));
            int useableWidth = containerWidth - margin * (count - 1);
            width = useableWidth / count;
            height = Math.round(width * 0.75f);//使用黄金比例设置
            rects.put("width", width);
            rects.put("height", height);
            rects.put("margin", margin);
        }
    }

    @Override
    public void setMatrix(Map<String, String> inputTags, Map<String, String> outputTags) {
        RunningLog.run("矩阵容器宽度:" + this.getWidth() + " 高度：" + this.getHeight());
        LinearLayout layout = this.findViewById(R.id.matrix_input_layout);
        if (layout == null) {
            layout = addLinearLayout(true);
        }
        setLayoutParam(layout, inputTags, this.inputs, this.inputClick);
        layout = this.findViewById(R.id.matrix_output_layout);
        if (layout == null) {
            layout = addLinearLayout(false);
        }
        setLayoutParam(layout, outputTags, this.outputs, this.outputClick);
    }

    @Override
    public void setSelectAll() {
        if (lastInput != null) {
            String input = lastInput.getTag().toString();
            List<String> values = new ArrayList<>();
            for (int i = 0; i < this.outputs.size(); i++) {
                String value = (i + 1) + "";
                values.add(value);
            }
            Map<String, List<String>> all = new LinkedHashMap<>();
            all.put(input, values);
            TabspMatrixSceneDto selected = new TabspMatrixSceneDto();
            selected.setInterfaces(all);
            selected.setSceneName("全选");
            this.setMatrixScene(selected);
            if (listener != null) {
                listener.onMatrixOutputSelected(input, values);
            }
        } else {
            ToastUtil.show(this.getContext(), this.getContext().getString(R.string.select_input));
        }
    }

    @Override
    public void setClearAll() {
        TabspMatrixSceneDto nullScene = MatrixUtils.makeEmptyScene();
        nullScene.setSceneName("清空");
        this.setMatrixScene(nullScene);
    }


    @Override
    public TabspMatrixSceneDto getCurrentScene() {
        return this.scene;
    }

    @Override
    public void setMatrixChangedListener(MatrixChangedListener listener) {
        this.listener = listener;
    }

    View lastInput = null;

    private void afterItemSelected(String input, boolean isSelected) {
        if (getCurrentScene() == null) this.scene = MatrixUtils.makeEmptyScene();
        Map<String, List<String>> maps = getCurrentScene().getInterfaces();
        if (maps.containsKey(input)) {
            List<String> output = maps.get(input);
            for (int i = 0; i < output.size(); i++) {
                String out = output.get(i);
                if (outputs.containsKey(out)) {
                    onItemSelected(outputs.get(out), isSelected);
                }
            }
        }
    }

    OnClickListener inputClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean isSelected = false;
            if (lastInput != view) {
                if (lastInput != null) {
                    onItemSelected(lastInput, false);
                    afterItemSelected(lastInput.getTag().toString(), false);
                    //和这个相关的移除掉背景
                }
                lastInput = view;
                onItemSelected(lastInput, true);
                afterItemSelected(lastInput.getTag().toString(), true);
                isSelected = true;
            } else {
                onItemSelected(view, false);
                afterItemSelected(lastInput.getTag().toString(), false);
                //清除相关信息
                lastInput = null;
            }
            if (listener != null) listener.onMatrixInputSelected(isSelected);
        }
    };

    /**
     * 更新场景中的配置
     *
     * @param output 输出口
     * @param input  输入口
     * @param isAdd  true 输出口指向输入口  false 输出口取消指向输入口
     */
    private void updateDirection(String output, String input, boolean isAdd) {
        List<String> values = null;
        if (isAdd) {
            if (outputWithInput.containsKey(output)) {
                String oldInput = outputWithInput.get(output);
                if (scene.getInterfaces().containsKey(oldInput) && scene.getInterfaces().get(oldInput) != null) {
                    scene.getInterfaces().get(oldInput).remove(output);
                }
            }
            outputWithInput.put(output, input);
            if (scene.getInterfaces().containsKey(input))
                values = scene.getInterfaces().get(input);
            else {
                values = new ArrayList<>();
                scene.getInterfaces().put(input, values);
            }
            if (!values.contains(output)) {
                values.add(output);
                if (listener != null) {
                    listener.onMatrixOutputSelected(input, output);
                }
            }
        } else {
            outputWithInput.remove(output);
            if (scene.getInterfaces().containsKey(input) && scene.getInterfaces().get(input) != null) {
                scene.getInterfaces().get(input).remove(output);
                if (listener != null) listener.onMatrixOutputSelected(input, "0");
            }
        }
    }

    OnClickListener outputClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (lastInput != null) {
                String output = view.getTag().toString();
                String input = lastInput.getTag().toString();
                boolean isSelected = false;
                if (outputWithInput.containsKey(output)) {
                    String oldInput = outputWithInput.get(output);
                    if (input.equals(oldInput)) {
                        isSelected = false;
                    } else isSelected = true;
                } else
                    isSelected = true;
                updateDirection(output, input, isSelected);
                onItemSelected(view, isSelected);
                postInvalidate();
            }
        }
    };

    private void onItemSelected(View view, boolean isSelected) {
        if (view != null) {
            ImageView iv = view.findViewById(R.id.item_image);
            MarqueTextView tv = view.findViewById(R.id.item_text);
            if (isSelected) {
                if (tv != null) {
                    tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    tv.setTextColor(Color.WHITE);
                }
                if (iv != null) iv.setImageResource(R.drawable.matrix_logo_white);
                view.setBackgroundResource(R.drawable.matrix_select_bg);
            } else {
                if (tv != null) {
                    tv.setEllipsize(TextUtils.TruncateAt.END);
                    tv.setTextColor(getResources().getColorStateList(R.color.matrix_text_color));
                }
                if (iv != null) iv.setImageDrawable(getResources().getDrawable(R.drawable.matrix_logo_bg));
                view.setBackgroundResource(R.drawable.matrix_unselect_bg);
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (this.scene != null) {
                RunningLog.run("当前视频矩阵数据:" + GsonUtil.objToJsonStr(this.scene));
                Map<String, List<String>> paths = this.scene.getInterfaces();
                int[] inputLocation = new int[2];
                LinearLayout inputLinearLayout = findViewById(R.id.matrix_input_layout);
                int[] outputLocation = new int[2];
                LinearLayout outputLinearLayout = findViewById(R.id.matrix_output_layout);
                for (Map.Entry<String, List<String>> entry : paths.entrySet()) {
                    String key = entry.getKey();
                    LinearLayout inputLayout = this.inputs.get(key);
                    if (inputLayout != null) {
                        List<String> outputList = paths.get(key);
                        if (outputList.size() > 0) {
                            HorizontalScrollView parent = (HorizontalScrollView) inputLayout.getParent().getParent();
                            inputLinearLayout.getLocationOnScreen(inputLocation);
                            int inputDiff = inputLocation[0] - inputLinearLayoutFirstX;
                            int top = parent.getTop() + inputLayout.getHeight();
                            int left = parent.getLeft() + inputLayout.getLeft() + inputDiff;
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("输入口");
                            buffer.append(key);
                            int y = top;
                            int x = (left + inputLayout.getWidth() / 2);
                            canvas.drawCircle(x, y, 2, paint);
                            buffer.append(",开始坐标:");
                            buffer.append("x=");
                            buffer.append(x);
                            buffer.append(",y=");
                            buffer.append(y);
                            for (int i = 0; i < outputList.size(); i++) {
                                key = outputList.get(i);
                                buffer.append("-->输出");
                                buffer.append(key);
                                LinearLayout outputLayout = this.outputs.get(key);
                                if (outputLayout != null) {
                                    parent = (HorizontalScrollView) outputLayout.getParent().getParent();
                                    outputLinearLayout.getLocationOnScreen(outputLocation);
                                    int outputDiff = outputLocation[0] - outputLinearLayoutFirstX;
                                    top = outputLayout.getTop();
                                    left = parent.getLeft() + outputLayout.getLeft() + outputDiff;
                                    int stopX = outputLayout.getWidth() / 2 + left;
                                    int stopY = parent.getTop();
                                    buffer.append(",结束位置:x=");
                                    buffer.append(stopX);
                                    buffer.append(",y=");
                                    buffer.append(stopY);
                                    canvas.drawCircle(stopX, stopY, 2, paint);
                                    canvas.drawLine(x, y, stopX, stopY, paint);
                                }
                            }
                            // RunningLog.debug(buffer.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            RunningLog.error("(LinearLayout) outputLayout.getParent();抛空指针的话可能是由于输出口数值不正确");
            RunningLog.error(e);
        }

    }

}
