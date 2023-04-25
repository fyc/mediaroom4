package com.sunmnet.mediaroom.matrix.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import com.sunmnet.mediaroom.device.bean.MatrixScene;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.matrix.R;
import com.sunmnet.mediaroom.matrix.anotherUi.bean.MatrixSceneForGridView;
import com.sunmnet.mediaroom.matrix.anotherUi.ui.MarqueTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by WangJincheng on 2021/5/10
 * 视频矩阵第二布局的组件2
 */

public class SecondMatrixLayout extends ConstraintLayout {

    // 用于记录选择的矩阵场景
    private List<MatrixSceneForGridView> matrixSceneForGridViewList;
    // 当前页数
    private int pagePosition;
    // 总页数
    private int totalPageCount;
    // 每页的最大场景数量
    private final static int PER_PAGE_MAX_COUNT = 6;
    // 上一次当前页面的显示的视频矩阵场景数
    private int lastPageItemCount = 0;
    // 上一次选择的视频矩阵场景
    private MatrixSceneForGridView previousSelectScene;

    public void setPageListener(OnPageListener listener) {
        this.listener = listener;
    }

    // 页面跳转监听器
    private OnPageListener listener;

    public SecondMatrixLayout(Context context) {
        super(context);
        init();
    }

    public SecondMatrixLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SecondMatrixLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        totalPageCount = 0;
    }

    /**
     * 设置视频矩阵列表
     * @param matrixList 视频矩阵场景列表
     */
    public void setMatrixList(List<MatrixScene> matrixList) {
        if (matrixList == null || matrixList.isEmpty()) return;
        this.matrixSceneForGridViewList = new ArrayList<>();
        for (MatrixScene matrixScene : matrixList) {
            this.matrixSceneForGridViewList.add(new MatrixSceneForGridView(matrixScene));
        }
        pagePosition = 0;
        if ((matrixList.size() % PER_PAGE_MAX_COUNT) > 0) {
            totalPageCount = (matrixList.size() / PER_PAGE_MAX_COUNT) + 1;
        } else {
            totalPageCount = matrixList.size() / PER_PAGE_MAX_COUNT;
        }
        refreshLayout();
        if (this.listener != null) this.listener.onPageChange(pagePosition, totalPageCount);
    }

    /**
     * 刷新界面
     */
    private synchronized void refreshLayout() {
        if (pagePosition == (totalPageCount - 1)) {
            // 最后一页
            int currentPageItemCount = this.matrixSceneForGridViewList.size() % 6;
            if (lastPageItemCount == currentPageItemCount) {
                // 不用全部移除子View
                switch (currentPageItemCount) {
                    case 1:
                        refreshOneMatrixSceneView();
                        break;
                    case 2:
                        refreshTwoMatrixSceneView();
                        break;
                    case 3:
                        refreshThreeMatrixSceneView();
                        break;
                    case 4:
                        refreshFourMatrixSceneView();
                        break;
                    case 5:
                        refreshFiveMatrixSceneView();
                        break;
                    case 6:
                        refreshSixMatrixSceneView();
                        break;
                }
            } else {
                // 需要移除全部子View
                lastPageItemCount = currentPageItemCount;
                removeAllViewsInLayout();
                // 重新布局
                switch (currentPageItemCount) {
                    case 1:
                        addOneMatrixSceneView();
                        break;
                    case 2:
                        addTwoMatrixSceneView();
                        break;
                    case 3:
                        addThreeMatrixSceneView();
                        break;
                    case 4:
                        addFourMatrixSceneView();
                        break;
                    case 5:
                        addFiveMatrixSceneView();
                        break;
                    case 6:
                        addSixMatrixSceneView();
                        break;
                }
            }
        } else {
            // 不是最后一页
            if (lastPageItemCount == PER_PAGE_MAX_COUNT) {
                // 不用全部移除子View
                refreshSixMatrixSceneView();
            } else {
                // 需要移除全部子View
                lastPageItemCount = PER_PAGE_MAX_COUNT;
                removeAllViewsInLayout();
                addSixMatrixSceneView();
            }
        }
    }

    /**
     * 设置已选中的item数据
     * @param position
     */
    private synchronized void setSelectItemData(int position) {
        MatrixSceneForGridView currentScene = this.matrixSceneForGridViewList.get(position);
        if (previousSelectScene != currentScene) {
            currentScene.setSelect(true);
            if (previousSelectScene != null) {
                previousSelectScene.setSelect(false);
            }
            previousSelectScene = currentScene;
        }
    }

    /**
     * 获取基准的 MatrixSceneView
     * @return MatrixSceneView
     */
    private MarqueTextView getCommonMatrixSceneView() {
        MarqueTextView matrixSceneView = new MarqueTextView(getContext());
        LayoutParams lp = new LayoutParams(
                getResources().getDimensionPixelOffset(R.dimen.px_220),
                getResources().getDimensionPixelOffset(R.dimen.px_120));
        matrixSceneView.setLayoutParams(lp);
        matrixSceneView.setPadding(
                getResources().getDimensionPixelOffset(R.dimen.px_10),
                0,
                getResources().getDimensionPixelOffset(R.dimen.px_10),
                0);
        matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
        matrixSceneView.setTextSize(
                getResources().getDimensionPixelOffset(R.dimen.px_29));
        matrixSceneView.setGravity(Gravity.CENTER);
        matrixSceneView.setSingleLine(true);
        matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
        matrixSceneView.setMarqueeRepeatLimit(-1);
        matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        matrixSceneView.setOnClickListener(view -> {
            int position = (int) view.getTag();
            Controller.getInstance().controll(matrixSceneForGridViewList.get(position).getMatrixScene().sceneName);
            Controller.getInstance().controll(matrixSceneForGridViewList.get(position).getMatrixScene());
            setSelectItemData(position);
            refreshLayout();
        });
        return matrixSceneView;
    }

    /**
     * 添加一个MatrixSceneView的布局
     */
    private void addOneMatrixSceneView() {
        MatrixSceneForGridView matrixSceneForGridView;

        MarqueTextView matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_one);
        LayoutParams lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToStart = ConstraintSet.PARENT_ID;
        lp.endToEnd = ConstraintSet.PARENT_ID;
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);
    }

    /**
     * 添加两个MatrixSceneView的布局
     */
    private void addTwoMatrixSceneView() {
        MatrixSceneForGridView matrixSceneForGridView;

        // 指导线
        Guideline guideline = new Guideline(getContext());
        guideline.setId(R.id.matrix_scene_layout_guideline_one);
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.guidePercent = 0.5f;
        lp.orientation = LayoutParams.VERTICAL;
        guideline.setLayoutParams(lp);
        addView(guideline);

        // 第一个view
        MarqueTextView matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_one);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.endToStart = R.id.matrix_scene_layout_guideline_one;
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        lp.rightMargin = getResources().getDimensionPixelOffset(R.dimen.px_10);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第二个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_two);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToEnd = R.id.matrix_scene_layout_guideline_one;
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        lp.leftMargin = getResources().getDimensionPixelOffset(R.dimen.px_10);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);
    }

    /**
     * 添加三个MatrixSceneView的布局
     */
    private void addThreeMatrixSceneView() {
        MatrixSceneForGridView matrixSceneForGridView;

        // 第二个view
        MarqueTextView matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_two);
        LayoutParams lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToStart = ConstraintSet.PARENT_ID;
        lp.endToEnd = ConstraintSet.PARENT_ID;
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第一个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_one);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.endToStart = R.id.matrix_scene_two;
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        lp.rightMargin = getResources().getDimensionPixelOffset(R.dimen.px_10);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第三个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_three);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToEnd = R.id.matrix_scene_two;
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        lp.leftMargin = getResources().getDimensionPixelOffset(R.dimen.px_10);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 2);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 2);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);
    }

    /**
     * 添加四个MatrixSceneView的布局
     */
    private void addFourMatrixSceneView() {
        MatrixSceneForGridView matrixSceneForGridView;

        // 竖指导线
        Guideline guideline = new Guideline(getContext());
        guideline.setId(R.id.matrix_scene_layout_guideline_one);
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.guidePercent = 0.5f;
        lp.orientation = LayoutParams.VERTICAL;
        guideline.setLayoutParams(lp);
        addView(guideline);

        // 横指导线
        guideline = new Guideline(getContext());
        guideline.setId(R.id.matrix_scene_layout_guideline_two);
        lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.guidePercent = 0.5f;
        lp.orientation = LayoutParams.HORIZONTAL;
        guideline.setLayoutParams(lp);
        addView(guideline);

        // 第一个view
        MarqueTextView matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_one);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.endToStart = R.id.matrix_scene_layout_guideline_one;
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        lp.rightMargin = getResources().getDimensionPixelOffset(R.dimen.px_90);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第二个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_two);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToStart = ConstraintSet.PARENT_ID;
        lp.endToEnd = ConstraintSet.PARENT_ID;
        lp.bottomToTop = R.id.matrix_scene_layout_guideline_two;
        lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.px_20);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第三个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_three);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToStart = ConstraintSet.PARENT_ID;
        lp.endToEnd = ConstraintSet.PARENT_ID;
        lp.topToBottom = R.id.matrix_scene_layout_guideline_two;
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.px_20);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 2);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 2);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第四个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_four);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToEnd = R.id.matrix_scene_layout_guideline_one;
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        lp.leftMargin = getResources().getDimensionPixelOffset(R.dimen.px_90);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 3);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 3);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);
    }

    /**
     * 添加五个MatrixSceneView的布局
     */
    private void addFiveMatrixSceneView() {
        MatrixSceneForGridView matrixSceneForGridView;

        // 竖指导线
        Guideline guideline = new Guideline(getContext());
        guideline.setId(R.id.matrix_scene_layout_guideline_one);
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.guidePercent = 0.5f;
        lp.orientation = LayoutParams.VERTICAL;
        guideline.setLayoutParams(lp);
        addView(guideline);

        // 横指导线
        guideline = new Guideline(getContext());
        guideline.setId(R.id.matrix_scene_layout_guideline_two);
        lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.guidePercent = 0.5f;
        lp.orientation = LayoutParams.HORIZONTAL;
        guideline.setLayoutParams(lp);
        addView(guideline);

        // 第一个view
        MarqueTextView matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_one);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.endToStart = R.id.matrix_scene_layout_guideline_one;
        lp.bottomToTop = R.id.matrix_scene_layout_guideline_two;
        lp.rightMargin = getResources().getDimensionPixelOffset(R.dimen.px_95);
        lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.px_4);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第二个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_two);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.endToStart = R.id.matrix_scene_layout_guideline_one;
        lp.topToBottom = R.id.matrix_scene_layout_guideline_two;
        lp.rightMargin = getResources().getDimensionPixelOffset(R.dimen.px_95);
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.px_4);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第三个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_three);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.topToTop = ConstraintSet.PARENT_ID;
        lp.bottomToBottom = ConstraintSet.PARENT_ID;
        lp.startToStart = ConstraintSet.PARENT_ID;
        lp.endToEnd = ConstraintSet.PARENT_ID;
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 2);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 2);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第四个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_four);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToEnd = R.id.matrix_scene_layout_guideline_one;
        lp.bottomToTop = R.id.matrix_scene_layout_guideline_two;
        lp.leftMargin = getResources().getDimensionPixelOffset(R.dimen.px_95);
        lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.px_4);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 3);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT +3);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第五个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_five);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToEnd = R.id.matrix_scene_layout_guideline_one;
        lp.topToBottom = R.id.matrix_scene_layout_guideline_two;
        lp.leftMargin = getResources().getDimensionPixelOffset(R.dimen.px_95);
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.px_4);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 4);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 4);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);
    }

    /**
     * 添加六个MatrixSceneView的布局
     */
    private void addSixMatrixSceneView() {
        MatrixSceneForGridView matrixSceneForGridView;

        // 竖指导线
        Guideline guideline = new Guideline(getContext());
        guideline.setId(R.id.matrix_scene_layout_guideline_one);
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.guidePercent = 0.5f;
        lp.orientation = LayoutParams.VERTICAL;
        guideline.setLayoutParams(lp);
        addView(guideline);

        // 横指导线
        guideline = new Guideline(getContext());
        guideline.setId(R.id.matrix_scene_layout_guideline_two);
        lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.guidePercent = 0.5f;
        lp.orientation = LayoutParams.HORIZONTAL;
        guideline.setLayoutParams(lp);
        addView(guideline);

        // 第一个view
        MarqueTextView matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_one);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.endToStart = R.id.matrix_scene_layout_guideline_one;
        lp.bottomToTop = R.id.matrix_scene_layout_guideline_two;
        lp.rightMargin = getResources().getDimensionPixelOffset(R.dimen.px_95);
        lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.px_4);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第二个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_two);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.endToStart = R.id.matrix_scene_layout_guideline_one;
        lp.topToBottom = R.id.matrix_scene_layout_guideline_two;
        lp.rightMargin = getResources().getDimensionPixelOffset(R.dimen.px_95);
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.px_4);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第三个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_three);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.bottomToTop = R.id.matrix_scene_layout_guideline_two;
        lp.startToStart = ConstraintSet.PARENT_ID;
        lp.endToEnd = ConstraintSet.PARENT_ID;
        lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.px_67);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 2);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 2);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第四个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_four);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.topToBottom = R.id.matrix_scene_layout_guideline_two;
        lp.startToStart = ConstraintSet.PARENT_ID;
        lp.endToEnd = ConstraintSet.PARENT_ID;
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.px_67);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 3);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 3);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第五个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_five);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToEnd = R.id.matrix_scene_layout_guideline_one;
        lp.bottomToTop = R.id.matrix_scene_layout_guideline_two;
        lp.leftMargin = getResources().getDimensionPixelOffset(R.dimen.px_95);
        lp.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.px_4);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 4);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 4);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);

        // 第六个view
        matrixSceneView = getCommonMatrixSceneView();
        matrixSceneView.setId(R.id.matrix_scene_six);
        lp = (LayoutParams) matrixSceneView.getLayoutParams();
        lp.startToEnd = R.id.matrix_scene_layout_guideline_one;
        lp.topToBottom = R.id.matrix_scene_layout_guideline_two;
        lp.leftMargin = getResources().getDimensionPixelOffset(R.dimen.px_95);
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.px_4);
        matrixSceneView.setLayoutParams(lp);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 5);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 5);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
        addView(matrixSceneView);
    }

    /**
     * 刷新一个MatrixSceneView的布局
     */
    private void refreshOneMatrixSceneView() {
        // 第一个view
        MarqueTextView matrixSceneView = findViewById(R.id.matrix_scene_one);
        MatrixSceneForGridView matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
    }

    /**
     * 刷新两个MatrixSceneView的布局
     */
    private void refreshTwoMatrixSceneView() {
        // 第一个view
        MarqueTextView matrixSceneView = findViewById(R.id.matrix_scene_one);
        MatrixSceneForGridView matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第二个view
        matrixSceneView = findViewById(R.id.matrix_scene_two);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
    }

    /**
     * 刷新三个MatrixSceneView的布局
     */
    private void refreshThreeMatrixSceneView() {
        // 第一个view
        MarqueTextView matrixSceneView = findViewById(R.id.matrix_scene_one);
        MatrixSceneForGridView matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第二个view
        matrixSceneView = findViewById(R.id.matrix_scene_two);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第三个view
        matrixSceneView = findViewById(R.id.matrix_scene_three);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 2);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 2);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
    }

    /**
     * 刷新四个MatrixSceneView的布局
     */
    private void refreshFourMatrixSceneView() {
        // 第一个view
        MarqueTextView matrixSceneView = findViewById(R.id.matrix_scene_one);
        MatrixSceneForGridView matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第二个view
        matrixSceneView = findViewById(R.id.matrix_scene_two);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第三个view
        matrixSceneView = findViewById(R.id.matrix_scene_three);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 2);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 2);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第四个view
        matrixSceneView = findViewById(R.id.matrix_scene_four);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 3);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 3);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
    }

    /**
     * 刷新五个MatrixSceneView的布局
     */
    private void refreshFiveMatrixSceneView() {
        // 第一个view
        MarqueTextView matrixSceneView = findViewById(R.id.matrix_scene_one);
        MatrixSceneForGridView matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第二个view
        matrixSceneView = findViewById(R.id.matrix_scene_two);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第三个view
        matrixSceneView = findViewById(R.id.matrix_scene_three);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 2);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 2);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第四个view
        matrixSceneView = findViewById(R.id.matrix_scene_four);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 3);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 3);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第五个view
        matrixSceneView = findViewById(R.id.matrix_scene_five);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 4);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 4);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
    }

    /**
     * 刷新六个MatrixSceneView的布局
     */
    private void refreshSixMatrixSceneView() {
        // 第一个view
        MarqueTextView matrixSceneView = findViewById(R.id.matrix_scene_one);
        MatrixSceneForGridView matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第二个view
        matrixSceneView = findViewById(R.id.matrix_scene_two);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 1);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 1);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第三个view
        matrixSceneView = findViewById(R.id.matrix_scene_three);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 2);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 2);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第四个view
        matrixSceneView = findViewById(R.id.matrix_scene_four);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 3);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 3);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第五个view
        matrixSceneView = findViewById(R.id.matrix_scene_five);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 4);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 4);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }

        // 第六个view
        matrixSceneView = findViewById(R.id.matrix_scene_six);
        matrixSceneForGridView = this.matrixSceneForGridViewList.get(pagePosition * PER_PAGE_MAX_COUNT + 5);
        matrixSceneView.setText(matrixSceneForGridView.getMatrixScene().sceneName);
        matrixSceneView.setTag(pagePosition * PER_PAGE_MAX_COUNT + 5);
        if (matrixSceneForGridView.isSelect()) {
            matrixSceneView.setTextColor(Color.WHITE);
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_selected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            matrixSceneView.setBackgroundResource(R.drawable.rectangle_six_sided_unselected);
            matrixSceneView.setEllipsize(TextUtils.TruncateAt.END);
            matrixSceneView.setTextColor(Color.parseColor("#1B335B"));
        }
    }

    /**
     * 跳到下一页
     */
    public void nextPage() {
        pagePosition++;
        if (pagePosition >= totalPageCount) {
            pagePosition = totalPageCount - 1;
            if (this.listener != null) this.listener.onLastPage();
        } else {
            refreshLayout();
        }
        if (this.listener != null) this.listener.onPageChange(pagePosition, totalPageCount);
    }

    /**
     * 跳到上一页
     */
    public void previousPage() {
        pagePosition--;
        if (pagePosition < 0) {
            pagePosition = 0;
            if (this.listener != null) this.listener.onFirstPage();
        } else {
            refreshLayout();
        }
        if (this.listener != null) this.listener.onPageChange(pagePosition, totalPageCount);
    }

    public interface OnPageListener {
        void onPageChange(int position, int total);
        void onFirstPage();
        void onLastPage();
    }
}
