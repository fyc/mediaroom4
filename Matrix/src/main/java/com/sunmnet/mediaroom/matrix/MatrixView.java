package com.sunmnet.mediaroom.matrix;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmnet.mediaroom.device.bean.MatrixConfig;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.device.bean.MatrixScene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MatrixView extends ViewGroup implements View.OnClickListener {
    Map<MatrixInterface, List<MatrixInterface>> selecting = new HashMap<>();
    Map<MatrixInterface, MatrixInterface> outputInInput = new HashMap<>();
    int selectedDrawable, unselectDrawale, centerviewSelectDrawable;
    int selectColor, unselectColor;


    MatrixInterface prevInput;

    public void setInput(MatrixInterface inputInteraface) {
        setSelected(centerView, true);
        ((TextView) centerView).setText(inputInteraface.inputName);
        List<MatrixInterface> selectingOutput = null;
        if (prevInput != null && this.selecting.containsKey(prevInput)) {
            selectingOutput = this.selecting.get(prevInput);
            for (int i = 0, size = selectingOutput.size(); i < size; i++) {
                setSelected(selectingOutput.get(i), false);
            }
        }
        if (this.selecting.containsKey(inputInteraface)) {
            selectingOutput = this.selecting.get(inputInteraface);
            for (int i = 0, size = selectingOutput.size(); i < size; i++) {
                setSelected(selectingOutput.get(i), true);
            }
        }
        this.selectedInterface = inputInteraface;
        this.prevInput = inputInteraface;
    }

    public void clear() {
        Map<String, MatrixInterface> outputs = config.toMap(config.getOutputInterface());
        Iterator<String> keys = outputs.keySet().iterator();
        while (keys.hasNext()) {
            MatrixInterface output = outputs.get(keys.next());
            setSelected(output, false);
        }
        prevInput = null;
        this.selectedInterface = null;
        this.outputInInput.clear();
        this.selecting.clear();
    }

    public MatrixInterface setScene(MatrixScene scene) {
        MatrixInterface first = null;
        clear();
        Map<String, MatrixInterface> inputs = config.toMap(config.getInputInterface());
        Map<String, MatrixInterface> outputs = config.toMap(config.getOutputInterface());
        Map<String, List<String>> scenes = scene.getSceneMap();
        if (scenes != null && scenes.size() > 0) {
            Iterator<String> keys = scenes.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                MatrixInterface input = inputs.get(key);
                if (input != null) {
                    List<MatrixInterface> outputInterfaces = new ArrayList<>();
                    selecting.put(input, outputInterfaces);
                    List<String> outputString = scenes.get(key);
                    if (outputString.size() > 0 && first == null) first = input;
                    for (int i = 0, size = outputString.size(); i < size; i++) {
                        String outputKey = outputString.get(i);
                        if (outputs.containsKey(outputKey) && outputs.get(outputKey) != null) {
                            outputInterfaces.add(outputs.get(outputKey));
                            this.outputInInput.put(outputs.get(outputKey), input);
                        }
                    }
                }
            }
        }
        return first;
    }

    public void setScene(String input, List<String> outputs) {
        MatrixInterface matrixInterface = config.findInterface(input, config.getInputInterface());
        List<MatrixInterface> aa = config.getInterfacesByList(config.getOutputInterface(), outputs);
        if (aa != null && matrixInterface != null) {
            setInput(matrixInterface);
            for (int i = 0, size = aa.size(); i < size; i++) {
                onOutputSelected(aa.get(i));
            }
        }
    }

    public void onOutputSelected(MatrixInterface matrixInterface) {
        List<MatrixInterface> outputs = null;
        if (!this.selecting.containsKey(this.selectedInterface)) {
            outputs = new ArrayList<>();
            selecting.put(this.selectedInterface, outputs);
        } else {
            outputs = selecting.get(this.selectedInterface);
        }
        if (!outputs.contains(matrixInterface)) {
            outputs.add(matrixInterface);
            if (outputInInput.containsKey(matrixInterface)) {
                MatrixInterface input = outputInInput.get(matrixInterface);
                if (selecting.containsKey(input) && selecting.get(input).contains(matrixInterface)) {
                    selecting.get(input).remove(matrixInterface);
                }
            }
            outputInInput.put(matrixInterface, this.selectedInterface);
            setSelected(matrixInterface, true);
        } else {
            outputs.remove(matrixInterface);
            this.outputInInput.remove(matrixInterface);
            setSelected(matrixInterface, false);
        }
    }

    @Override
    public void onClick(View v) {
        if (this.selectedInterface == null) return;
        if (v.getTag() != null) {
            MatrixInterface matrixInterface = (MatrixInterface) v.getTag();
            onOutputSelected(matrixInterface);
            if (this.matrixSelectListener != null) {
                this.matrixSelectListener.onMatrixSelected(this.selectedInterface.input, this.selecting.get(this.selectedInterface));
            }
        }
    }

    private void setSelected(View view, boolean select) {
        int drawable = select ? selectedDrawable : unselectDrawale;
        view.setBackgroundResource(drawable);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(select ? selectColor : unselectColor);
        }
    }

    private void setSelected(MatrixInterface matrixInterface, boolean select) {
        View view = findViewWithTag(matrixInterface);
        if (view != null) {
            setSelected(view, select);
        }
    }

    class InterafaceHolder {
        int outsideSize, insideSize;
        List<View> outside = null, inside = null;
        float outsideRadius, insideRadius;
    }

    InterafaceHolder holder;

    public void apply(MatrixConfig config) {
        this.config = config;
        List<MatrixInterface> output = config.getOutputInterface();
        int size = output.size();
        holder = new InterafaceHolder();
        if (size <= 12) {
            holder.outside = new ArrayList<>();
            holder.outsideSize = size;
        } else if (size > 12 && size <= 16) {
            holder.outsideSize = 8;
            holder.insideSize = size - holder.outsideSize;
            holder.outside = new ArrayList<>();
            holder.inside = new ArrayList<>();
        } else if (size > 16) {
            holder.outsideSize = 16;
            holder.insideSize = size - holder.outsideSize;
            holder.outside = new ArrayList<>();
            holder.inside = new ArrayList<>();
        }
        int wh = getContext().getResources().getDimensionPixelSize(R.dimen.px_68);
        LayoutParams params = new LayoutParams(wh, wh);
        for (int i = 0; i < size; i++) {
            MatrixInterface matrixInterface = output.get(i);
            TextView textView = new TextView(getContext());
            textView.setText(matrixInterface.inputName);
            textView.setTag(matrixInterface);
            textView.setBackgroundResource(R.drawable.circle_default_drawable);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(this);
            textView.setTextColor(unselectColor);
            addView(textView, params);
            if (i < holder.outsideSize) {
                holder.outside.add(textView);
            } else {
                holder.inside.add(textView);
            }
        }
    }

    int dashedColor;

    public interface OnMatrixSelectListener {
        public void onMatrixSelected(String input, String output);

        public void onMatrixSelected(String input, List<MatrixInterface> interfaces);
    }

    MatrixConfig config;
    OnMatrixSelectListener matrixSelectListener;

    public void setOnMatrixSelectListener(OnMatrixSelectListener matrixSelectListener) {
        this.matrixSelectListener = matrixSelectListener;
    }

    private float radius;//圆半径
    private float mDegreeDelta; //角度间距
    private int offset;//偏移角度
    View centerView;
    MatrixInterface selectedInterface;


    public MatrixView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        // TODO Auto-generated constructor stub
    }

    public void addCenterView(View view) {
        if (centerView != null) {
            removeView(centerView);
        }
        centerView = view;
        super.addView(centerView);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MatrixView);
        //圆半径
        radius = a.getDimension(R.styleable.MatrixView_radius, 120);
        //偏移角度
        offset = a.getInteger(R.styleable.MatrixView_offset, 0);
        dashedColor = a.getColor(R.styleable.MatrixView_dashed_color, Color.BLUE);
        selectedDrawable = a.getResourceId(R.styleable.MatrixView_selected_background, R.drawable.circle_selected_drawable);
        unselectDrawale = a.getResourceId(R.styleable.MatrixView_unselect_background, R.drawable.circle_default_drawable);
        centerviewSelectDrawable = a.getResourceId(R.styleable.MatrixView_center_view_selected, R.drawable.circle_selected_drawable);
        selectColor = a.getColor(R.styleable.MatrixView_selected_text_color, Color.WHITE);
        unselectColor = a.getColor(R.styleable.MatrixView_unselect_text_color, Color.BLUE);
        // TODO Auto-generated constructor stub
        a.recycle();
    }

    public MatrixView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    private void setSelect(List<MatrixInterface> interfaces, boolean select) {
        for (int i = 0; i < interfaces.size(); i++) {
            setSelected(interfaces.get(i), select);
        }
    }

    @Override
    public void addView(View child) {
        if (centerView == null) throw new RuntimeException("you must call addCenterView() first.");
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (centerView == null) throw new RuntimeException("you must call addCenterView() first.");
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (centerView == null) throw new RuntimeException("you must call addCenterView() first.");
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, LayoutParams params) {
        if (centerView == null) throw new RuntimeException("you must call addCenterView() first.");
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (centerView == null) throw new RuntimeException("you must call addCenterView() first.");
        super.addView(child, index, params);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO Auto-generated method stub
        //获取子view个数
        final int count = getChildCount();
        if (count < 2) {
            return;
        }
        /***
         * 摆放中心视图
         * */

        int centerViewWidth = centerView.getMeasuredWidth();
        int centerViewHeight = centerView.getMeasuredHeight();
        int centerViewleft = (getWidth() - getPaddingLeft() - centerViewWidth) / 2;
        int centerViewTop = (getHeight() - getPaddingTop() - centerViewHeight) / 2;
        centerView.layout(centerViewleft, centerViewTop, centerViewleft + centerViewWidth, centerViewTop + centerViewHeight);
        /**
         * 摆放内圈
         * */
        if (holder.inside != null && holder.inside.size() > 0) {
            holder.insideRadius = radius;
            layout(holder.inside, this.radius, 0, left, top, right, bottom);

        }
        /**
         * 内圈容量有限，摆放外圈
         * */
        if (holder.outside != null && holder.outside.size() > 0) {
            View child = holder.outside.get(0);
            final int width = child.getMeasuredWidth();
            holder.outsideRadius = radius + width * 3 / 2;
            layout(holder.outside, holder.outsideRadius, 20, left, top, right, bottom);
        }
    }

    private void layout(List<View> views, float radius, float offset, int left, int top, int right, int bottom) {
        float mDegreeDelta = 360 / (views.size() * 1.0f);
        final int parentLeft = getPaddingLeft();
        final int parentRight = right - left - getPaddingRight();

        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();
        for (int i = 0, size = views.size(); i < size; i++) {
            final View child = views.get(i);
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();
            float cacheRadius = radius;
            int childLeft, childTop;
            childLeft = (int) (parentLeft + (parentRight - parentLeft - width) / 2 - (cacheRadius * Math.sin((i * mDegreeDelta + offset) * Math.PI / 180)));
            childTop = (int) (parentTop + (parentBottom - parentTop - height) / 2 - (cacheRadius * Math.cos((i * mDegreeDelta + offset) * Math.PI / 180)));
            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画虚线
         * */
        if (getChildCount() < 3) return;

        float x = centerView.getX() + centerView.getWidth() / 2;
        float y = centerView.getY() + centerView.getHeight() / 2;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(dashedColor);
        paint.setStrokeWidth(1);
        PathEffect effects = new DashPathEffect(new float[]{4, 4}, 0);
        paint.setPathEffect(effects);
        RectF oval;
        if (holder.inside != null && holder.inside.size() > 0) {
            oval = new RectF(x - holder.insideRadius, y - holder.insideRadius, x + holder.insideRadius, y + holder.insideRadius);
            drawArc(canvas, oval, holder.insideRadius, paint, holder.inside, 0);
        }
        if (holder.outside != null && holder.outside.size() > 0) {
            oval = new RectF(x - holder.outsideRadius, y - holder.outsideRadius, x + holder.outsideRadius, y + holder.outsideRadius);
            drawArc(canvas, oval, holder.outsideRadius, paint, holder.outside, 20);
        }

    }

    private void drawArc(Canvas canvas, RectF rectF, float radius, Paint paint, List<View> views, float offset) {
        if (views == null) return;
        View view = views.get(views.size() - 1);
        View childView = views.get(views.size() - 2);
        double circle = 360 / (views.size() * 1.0f);
        //计算圆弧角度
        float alphaValue = 0;
        float viewX = view.getX() + view.getWidth() / 2;
        float viewY = view.getY() + view.getHeight() / 2;
        float childX = childView.getX() + childView.getWidth() / 2;
        float childY = childView.getY() + childView.getHeight() / 2;
        double lef = Math.pow((childX - viewX), 2);
        double t = Math.pow((childY - viewY), 2);
        double c = Math.sqrt(lef + t) - view.getWidth();
        alphaValue = (float) Math.sin(c / radius);
        double degree = Math.toDegrees(alphaValue);
        alphaValue = (float) Math.toDegrees(Math.sin((view.getWidth() / 2) / radius));
        float startAngle = -90 + alphaValue - offset;
        for (int i = 0, size = views.size(); i < size; i++) {
            //System.out.println(i + "   起始角度：" + startAngle + "   偏移量：" + offset + "   偏移角度：" + degree);
            canvas.drawArc(rectF, startAngle, (float) degree, false, paint);
            startAngle = (float) (startAngle + circle);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(sizeWidth, sizeHeight);
    }
}
