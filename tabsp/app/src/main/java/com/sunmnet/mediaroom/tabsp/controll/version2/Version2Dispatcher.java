package com.sunmnet.mediaroom.tabsp.controll.version2;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.DeviceTag;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.bean.SysSpTempConfigFileDto;
import com.sunmnet.mediaroom.tabsp.bean.TabspConfig;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.AbstractDispatcher;

import java.util.ArrayList;
import java.util.List;

public class Version2Dispatcher extends AbstractDispatcher {
    public static final String CONTROLLER_VERSION2_LECTURE = "/fragment/controller/version2/lecture";
    public static final String CONTROLLER_VERSION2_TALKING = "/fragment/controller/version2/talking";
    public static final String CONTROLLER_VERSION2_CUSTOM_TALKING = "/fragment/controlller/version2/custom_talking";
    public static final String CONTROLLER_VERSION2_DISPLAY = "/fragment/controller/version2/display";
    public static final String CONTROLLER_VERSION2_CONFERECE = "/fragment/controller/version2/conference";
    private static final String MENU_LECTURE = "LECTTURE";
    private static final String MENU_TALKING = "TALKING";
    private static final String MENU_DISPLAY = "DISPLAY";
    TabspConfig config;

    public Version2Dispatcher(TabspConfig config) {
        super();
        this.config = config;
    }

    @Override
    public String getPageByType(String type) {
        String routerPath = null;
        switch (type) {
            case PAGE_REDIRECT_LOGIN_ENTER:
                routerPath = Constants.ROUTERPATH_LOGIN_MAIN;
                break;
            case PAGE_REDIRECT_CONTENT:
                routerPath = Constants.ROUTERPATH_ACTIVITY_CONTENT;
                break;
            case FRAGMENT_TITLE:
                routerPath = Constants.ROUTERPATH_CONTROLLER_TITLE;
                break;
            case FRAGMENT_MENU:
                routerPath = Constants.ROUTERPATH_CONTROLLER_MENU;
                break;
            case "":
                routerPath = Constants.ROUTERPATH_LOGIN_WAITING;
                break;
            default:
                routerPath = type;
                break;
        }
        return routerPath;
    }

    @Override
    public void apply(String type, View view) {
        if (Constants.ROUTERPATH_CONTROLLER_MENU.equals(type)) {
            FrameLayout other = view.findViewById(R.id.other);
            if (other != null) {
                int height = view.getContext().getResources().getDimensionPixelSize(R.dimen.px_147);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
                View switcher = LayoutInflater.from(view.getContext()).inflate(R.layout.tabsp_version2_class_switcher_layout, null, false);
                other.addView(switcher, params);
                ImageView switcherButton = switcher.findViewById(R.id.class_switcher);
                if (switcherButton != null) {
                    switcherButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //做下课操作
                        }
                    });
                    CommonUtil.loadResourceIntoImage(TabspApplication.getInstance(), R.drawable.opened, switcherButton);
                }
            }
        }
    }

    /*
        @Override
        public List<MenuOperator> getMenus() {
            List<MenuOperator> operators = new ArrayList<>();
            addMenus(MENU_LECTURE, operators);
            addMenus(MENU_TALKING, operators);
            DeviceType[] types = new DeviceType[]{
                    DeviceType.AIRCONDITIONER, DeviceType.LIGHT,
                    DeviceType.CURTAIN, DeviceType.FAN,
                    DeviceType.DOOR, DeviceType.FRESHAIR,
                    DeviceType.RECORDED,
            };
            addMenus(operators, types);
            addMenus(CONTROLLER_VERSION2_DISPLAY, operators);
            addMenus(CONTROLLER_VERSION2_CONFERECE, operators);
            return operators;
        }



        private void addMenus(String deviceType, List<MenuOperator> menus) {
            MenuOperator operator = null;
            MenuEntity menuEntity = null;
            switch (deviceType) {
                case MENU_LECTURE:
                    break;
                case MENU_TALKING:
                    break;
                case CONTROLLER_VERSION2_DISPLAY:
                    menuEntity = new MenuEntity(TabspApplication.getInstance().getString(R.string.name_display_mode), CONTROLLER_VERSION2_DISPLAY, null, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_display_selected, R.drawable.mediaroom4_menu_display_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                    break;
                case CONTROLLER_VERSION2_CONFERECE:
                    menuEntity = new MenuEntity(TabspApplication.getInstance().getString(R.string.name_conference), Constants.ROUTERPATH_CONTROLL_CONFERENCE, null, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_conference_selected, R.drawable.mediaroom4_menu_conferenceunselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                    break;
            }
            if (operator != null) {
                menus.add(operator);
            }
        }

        private void addMenus(List<MenuOperator> menus, DeviceType... types) {
            for (int i = 0; i < types.length; i++) {
                DeviceType type = types[i];
                List devices = Controller.getInstance().getDevicesByDeviceType(type);
                if (devices != null && devices.size() > 0) {
                    addMenus(type, menus);
                } else {
                    if (type == DeviceType.LIGHT) {
                        devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.DIMMER);
                        if (devices != null && devices.size() > 0) {
                            addMenus(type.getDeviceType(), menus);
                        }
                    }
                }
            }
        }
    */
    private void addMenus(DeviceType deviceType, List<MenuOperator> menus) {
        MenuOperator operator = null;
        MenuEntity menuEntity = null;
        int deviceTypeResource = DeviceBuilder.getDeviceTypeNameResource(deviceType.getDeviceType());
        String menuName;
        if (deviceTypeResource == 0) {
            menuName = deviceType.getDeviceTypeName();
        } else {
            menuName = TabspApplication.getInstance().getString(deviceTypeResource);
        }
        menuName = menuName + TabspApplication.getInstance().getString(R.string.name_controll);
        switch (deviceType.getDeviceType()) {
            case DeviceTag.DEVICE_LIGHT:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_light_selected, R.drawable.mediaroom4_menu_light_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case DeviceTag.DEVICE_DOORLOCKER:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_doorlocker_selected, R.drawable.mediaroom4_menu_doorlocker_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case DeviceTag.DEVICE_AIRCONDITIONER:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_AIRCONDITIONER, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_aircondtioner_selected, R.drawable.mediaroom4_menu_airconditioner_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case DeviceTag.DEVICE_FRESHAIR:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_FRESHAIR, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_freshair_selected, R.drawable.mediaroom4_menu_freshair_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case DeviceTag.DEVICE_FAN:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_fan_selected, R.drawable.mediaroom4_menu_fan_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case DeviceTag.DEVICE_RECORDED:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_RECORD, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_record_selected, R.drawable.mediaroom4_menu_record_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case DeviceTag.DEVICE_MATRIX:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_MATRIX2, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_matrix_selected, R.drawable.mediaroom4_menu_matrix_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case DeviceTag.DEVICE_CURTAIN:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_curtain_selected, R.drawable.mediaroom4_menu_curtain_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
        }
        if (operator != null) {
            menus.add(operator);
        }
    }

    private void addMenus(DeviceType deviceType, List<MenuOperator> menus, String nickName) {
        MenuOperator operator = null;
        MenuEntity menuEntity = null;
        String menuName = nickName;
        switch (deviceType.getDeviceType()) {
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.LIGHT:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_light_selected, R.drawable.mediaroom4_menu_light_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.DOOR:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_doorlocker_selected, R.drawable.mediaroom4_menu_doorlocker_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.AIR_CONDITIONER:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_AIRCONDITIONER, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_aircondtioner_selected, R.drawable.mediaroom4_menu_airconditioner_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.FRESH_AIR:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_FRESHAIR, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_freshair_selected, R.drawable.mediaroom4_menu_freshair_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.FAN:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_fan_selected, R.drawable.mediaroom4_menu_fan_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.RECORDED_BROAD_CAST:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_RECORD, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_record_selected, R.drawable.mediaroom4_menu_record_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.VIDEO_MATRIX:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_MATRIX2, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_matrix_selected, R.drawable.mediaroom4_menu_matrix_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.CURTAIN:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_curtain_selected, R.drawable.mediaroom4_menu_curtain_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.SOUND:
                break;
        }
        if (operator != null) {
            menus.add(operator);
        }
    }

    private void addMenus(String key, List<MenuOperator> menus) {
        MenuOperator operator = null;
        MenuEntity menuEntity = null;
        switch (key) {
            case "LECTURE":
                menuEntity = new MenuEntity(TabspApplication.getInstance().getString(R.string.name_lecture_mode), CONTROLLER_VERSION2_LECTURE, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_lecture_unselect, R.drawable.mediaroom4_menu_lecture_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                menus.add(operator);
                break;
            case "DISCUSS":
                menuEntity = new MenuEntity(TabspApplication.getInstance().getString(R.string.name_talking_mode), CONTROLLER_VERSION2_TALKING, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_lecture_discussing_select, R.drawable.mediaroom4_menu_lecture_discussing_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                menus.add(operator);
                break;
            case CONTROLLER_VERSION2_DISPLAY:
                menuEntity = new MenuEntity(TabspApplication.getInstance().getString(R.string.name_display_mode), CONTROLLER_VERSION2_DISPLAY, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_display_selected, R.drawable.mediaroom4_menu_display_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                menus.add(operator);
                break;
            case CONTROLLER_VERSION2_CONFERECE:
                menuEntity = new MenuEntity(TabspApplication.getInstance().getString(R.string.name_conference), Constants.ROUTERPATH_CONTROLL_CONFERENCE, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_conference_selected, R.drawable.mediaroom4_menu_conferenceunselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                menus.add(operator);
                break;
            default:

                break;
        }
    }

    private void addMenus(SysSpTempConfigFileDto.MenuSetting setting, List<MenuOperator> menus) {
        MenuOperator operator = null;
        MenuEntity menuEntity = null;
        if (setting.isEnable()) {
            switch (setting.getKey()) {
                case "LECTURE":
                    menuEntity = new MenuEntity(setting.getAliasName(), CONTROLLER_VERSION2_LECTURE, null, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_lecture_unselect, R.drawable.mediaroom4_menu_lecture_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                    menus.add(operator);
                    break;
                case "DISCUSS":
                    //如果从平台设置了相关的输入输出方式，则加载另外一个界面
                    if (config.getConfiguration().getDiscussionModeList() != null) {
                        menuEntity = new MenuEntity(setting.getAliasName(), CONTROLLER_VERSION2_CUSTOM_TALKING, null, null);
                    } else {
                        menuEntity = new MenuEntity(setting.getAliasName(), CONTROLLER_VERSION2_TALKING, null, null);
                    }
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_lecture_discussing_select, R.drawable.mediaroom4_menu_lecture_discussing_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                    menus.add(operator);
                    break;
                case MENU_DISPLAY:
                    menuEntity = new MenuEntity(setting.getAliasName(), CONTROLLER_VERSION2_DISPLAY, null, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_display_selected, R.drawable.mediaroom4_menu_display_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                    menus.add(operator);
                    break;
                case CONTROLLER_VERSION2_CONFERECE:
                    menuEntity = new MenuEntity(setting.getAliasName(), Constants.ROUTERPATH_CONTROLL_CONFERENCE, null, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_conference_selected, R.drawable.mediaroom4_menu_conferenceunselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                    menus.add(operator);
                    break;
                case "VOLUME":
                    DeviceType sound = DeviceType.SOUND;
                    if (sound != null && Controller.getInstance().getDevicesByDeviceType(sound) != null) {
                        addMenus(sound, menus, setting.getAliasName());
                    }
                    break;
                default:
                    DeviceType type = null;
                    if ("matrix".equalsIgnoreCase(setting.getKey())) {
                        type = DeviceType.MATRIX;
                    } else type = DeviceBuilder.getDeviceType(setting.getKey());
                    if (type != null && Controller.getInstance().getDevicesByDeviceType(type) != null) {
                        addMenus(type, menus, setting.getAliasName());
                    }
                    break;
            }
        }
    }

    @Override
    public <T> List<String> setCompareMenu(List<T> menus) {
        List<String> strings = new ArrayList<>();

        return strings;
    }

    private void addMenus(List<MenuOperator> menus, DeviceType... types) {
        for (int i = 0; i < types.length; i++) {
            DeviceType type = types[i];
            List devices = Controller.getInstance().getDevicesByDeviceType(type);
            if (devices != null && devices.size() > 0) {
                addMenus(type, menus);
            } else {
                if (type == DeviceType.LIGHT) {
                    devices = Controller.getInstance().getDevicesByDeviceType(DeviceType.DIMMER);
                    if (devices != null && devices.size() > 0) {
                        addMenus(type, menus);
                    }
                }
            }
        }
    }

    private List<MenuOperator> getMenusByMenuNames() {
        List<MenuOperator> operators = new ArrayList<>();
        for (int i = 0, size = this.config.getMenuNames().size(); i < size; i++) {
            addMenus(this.config.getMenuNames().get(i), operators);
        }
        return operators;
    }

    private List<MenuOperator> getDefaultMenus() {
        List<MenuOperator> operators = new ArrayList<>();
        addMenus("LECTURE", operators);
        addMenus("DISCUSS", operators);
        DeviceType[] types = new DeviceType[]{
                DeviceType.AIRCONDITIONER, DeviceType.LIGHT,
                DeviceType.CURTAIN, DeviceType.FAN,
                DeviceType.DOOR, DeviceType.FRESHAIR,
                DeviceType.MATRIX, DeviceType.RECORDED,
                DeviceType.SOUND
        };
        addMenus(operators, types);
        return operators;
    }

    @Override
    public List<MenuOperator> getMenus() {
        if (this.config != null && this.config.getMenuNames() != null) {
            try {
                return getMenusByMenuNames();
            } catch (Exception e) {
                return getDefaultMenus();
            }
        } else return getDefaultMenus();
    }

    public List<SysSpTempConfigFileDto.LectureMode> getLectureModes() {
        if (config.getConfiguration() != null)
            return config.getConfiguration().getLectureModeList();
        return null;
    }

    public List<SysSpTempConfigFileDto.DiscussMode> getDiscussModes() {
        if (config.getConfiguration() != null)
            return config.getConfiguration().getDiscussionModeList();
        return null;
    }
}
