package com.sunmnet.mediaroom.tabsp.common;

import android.app.Activity;
import android.content.Context;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.bean.MatrixInterface;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MatrixInterfaceItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * 好像有用
 * */
public class DiscussionHelper {
    static int DFAULT_TEXT_COLOR;
    static int DEFAULT_SELECT_TEXT_COLOR;

    public static void init(Activity context) {
        DFAULT_TEXT_COLOR = CommonUtil.getColorByAttribute(context, R.attr.colorPrimary);
        DEFAULT_SELECT_TEXT_COLOR = CommonUtil.getColorByAttribute(context, R.attr.common_text_color);
    }

    public static List<MatrixInterfaceItem> getInputItems() {
        List<MatrixInterfaceItem> inputItems = new ArrayList<>();
        createItem(1, inputItems);
        createItem(5, inputItems);
        createItem(2, inputItems);
        createItem(3, inputItems);
        createItem(11, inputItems);
        createItem(4, inputItems);
        return inputItems;
    }

    public static List<MatrixInterfaceItem> getInputGroups() {
        List<MatrixInterfaceItem> groups = new ArrayList<>();
        createItem(6, groups);
        createItem(7, groups);
        createItem(8, groups);
        createItem(9, groups);
        return groups;
    }

    public static List<MatrixInterfaceItem> getOutputItems() {
        List<MatrixInterfaceItem> inputItems = new ArrayList<>();
        getMatrixOutput(1, inputItems);
        getMatrixOutput(1, inputItems);
        getMatrixOutput(-1, inputItems);
        return inputItems;
    }

    public static List<MatrixInterfaceItem> getOutputGroups() {
        List<MatrixInterfaceItem> inputItems = new ArrayList<>();
        getMatrixOutput(3, inputItems);
        getMatrixOutput(4, inputItems);
        getMatrixOutput(5, inputItems);
        getMatrixOutput(6, inputItems);
        return inputItems;
    }

    private static MatrixInterfaceItem getMatrixOutput(int output, List<MatrixInterfaceItem> items) {
        Locale language = TabspApplication.getInstance().getConfig().getLang();
        MatrixInterfaceItem item = null;
        MatrixInterface matrixInterface = null;
        switch (output) {
            case -1:
                break;
            case 1:
                matrixInterface = new MatrixInterface(output + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Primary Screen" : "左大屏";
                item = new MatrixInterfaceItem(matrixInterface, R.drawable.mediaroom4_dpzj_selected, R.drawable.mediaroom4_dpzj_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 2:
                matrixInterface = new MatrixInterface(output + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Primary Screen" : "右大屏";
                item = new MatrixInterfaceItem(matrixInterface, R.drawable.mediaroom4_dpzj_selected, R.drawable.mediaroom4_dpzj_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);
                break;
            case 3:
                matrixInterface = new MatrixInterface(output + "");
                matrixInterface.nickName = "1";
                matrixInterface.inputName = language == Locale.ENGLISH ? "First Group" : "第一组";
                item = new MatrixInterfaceItem(matrixInterface, null, null, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DEFAULT_SELECT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 4:
                matrixInterface = new MatrixInterface(output + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Second Group" : "第二组";
                matrixInterface.nickName = "2";
                item = new MatrixInterfaceItem(matrixInterface, null, null, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DEFAULT_SELECT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 5:
                matrixInterface = new MatrixInterface(output + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Third Group" : "第三组";
                matrixInterface.nickName = "3";
                item = new MatrixInterfaceItem(matrixInterface, null, null, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DEFAULT_SELECT_TEXT_COLOR, DFAULT_TEXT_COLOR);
                break;
            case 6:
                matrixInterface = new MatrixInterface(output + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Fourth Group" : "第四组";
                matrixInterface.nickName = "4";
                item = new MatrixInterfaceItem(matrixInterface, null, null, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DEFAULT_SELECT_TEXT_COLOR, DFAULT_TEXT_COLOR);
                break;
        }
        if (item != null) {
            items.add(item);
        }
        return item;
    }


    private static MatrixInterfaceItem createItem(int input, List<MatrixInterfaceItem> items) {
        Locale language = TabspApplication.getInstance().getConfig().getLang();
        MatrixInterfaceItem item = null;
        MatrixInterface matrixInterface = null;
        switch (input) {
            case 1:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Computer" : "讲台电脑";
                item = new MatrixInterfaceItem(matrixInterface, R.drawable.mediaroom4_pc_open, R.drawable.mediaroom4_pc_off, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 2:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Nootbook" : "便携电脑有线连接";
                item = new MatrixInterfaceItem(matrixInterface, R.drawable.mediaroom4_notebook_selected, R.drawable.mediaroom4_notebook_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 3:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Primary Screen" : "大屏主机";
                item = new MatrixInterfaceItem(matrixInterface, R.drawable.mediaroom4_dpzj_selected, R.drawable.mediaroom4_dpzj_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 4:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Slave Screen" : "大屏主机二";
                item = new MatrixInterfaceItem(matrixInterface, R.drawable.mediaroom4_dpzj_selected, R.drawable.mediaroom4_dpzj_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 5:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Teacher Projection" : "教师投屏";
                item = new MatrixInterfaceItem(matrixInterface, R.drawable.mediaroom4_portable_selected, R.drawable.mediaroom4_portable_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 6:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.nickName = "1";
                matrixInterface.inputName = language == Locale.ENGLISH ? "First Group" : "第一组";
                item = new MatrixInterfaceItem(matrixInterface, null, null, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DEFAULT_SELECT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 7:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Second Group" : "第二组";
                matrixInterface.nickName = "2";
                item = new MatrixInterfaceItem(matrixInterface, null, null, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DEFAULT_SELECT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 8:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Third Group" : "第三组";
                matrixInterface.nickName = "3";
                item = new MatrixInterfaceItem(matrixInterface, null, null, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DEFAULT_SELECT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            case 9:
                matrixInterface = new MatrixInterface(input + "");
                matrixInterface.inputName = language == Locale.ENGLISH ? "Fourth Group" : "第四组";
                matrixInterface.nickName = "4";
                item = new MatrixInterfaceItem(matrixInterface, null, null, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DEFAULT_SELECT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
            default:
                matrixInterface = new MatrixInterface();
                matrixInterface.inputName = language == Locale.ENGLISH ? "Slave Screen" : "分组讨论";
                item = new MatrixInterfaceItem(matrixInterface, R.drawable.mediaroom4_dpzj_selected, R.drawable.mediaroom4_dpzj_unselect, R.drawable.device_on_corner_background, R.drawable.device_off_corner_background, DFAULT_TEXT_COLOR, DFAULT_TEXT_COLOR);

                break;
        }
        if (item != null) items.add(item);
        return item;
    }
}
