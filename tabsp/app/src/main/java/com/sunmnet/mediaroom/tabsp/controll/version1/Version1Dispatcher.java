package com.sunmnet.mediaroom.tabsp.controll.version1;

import android.graphics.Color;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.DeviceTag;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.bean.SysSpTempConfigFileDto;
import com.sunmnet.mediaroom.tabsp.bean.TabspConfig;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.AbstractDispatcher;
import com.sunmnet.mediaroom.wirelessprojection.WirelessOperator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 版本1分发器
 */
public class Version1Dispatcher extends AbstractDispatcher {
    public static final String CONTROLLER_VERSION1_MAINPAGE = "/fragment/controller/version1/mainpage";
    public static final String CONTROLLER_VERSION1_MEDIA = "/fragment/controller/version1/media";
    TabspConfig config;
    public static int WirelessMode = 0; //0,代表该模块不存在，1，代表存在

    public Version1Dispatcher(TabspConfig config) {
        this.config = config;
        Version1Dispatcher.WirelessMode = 0;
    }

    @Override
    public String getPageByType(String type) {
        String routerPath = null;

        switch (type) {
            case PAGE_REDIRECT_LOGIN_ENTER:
                //如果需要修改登录界面，增加类继承AbstractActivityUiDispatcher后，
                // 标记@Router(path="唯一标记")这种方式即可
                routerPath = Constants.ROUTERPATH_LOGIN_MAIN;
                break;
            case PAGE_REDIRECT_CONTENT:
                //如果需要修改内容界面，增加类继承AbstractActivityUiDispatcher后，
                // 标记@Router(path="唯一标记")这种方式即可
                routerPath = Constants.ROUTERPATH_ACTIVITY_CONTENT;
                break;
            case FRAGMENT_TITLE:
                //如果需要修改标题界面，增加类继承AbstractFragmentDispatcher，
                // 标记@Router(path="唯一标记")这种方式即可
                routerPath = Constants.ROUTERPATH_CONTROLLER_TITLE;
                break;
            case FRAGMENT_MENU:
                //如果需要修改菜单界面，增加类继承AbstractFragmentDispatcher
                // 标记@Router(path="唯一标记")这种方式即可
                routerPath = Constants.ROUTERPATH_CONTROLLER_MENU;
                break;
            case "":
                routerPath = Constants.ROUTERPATH_LOGIN_WAITING;
                break;
            default:
                //如果上面都不适合，则是传递什么返回什么，由ARouter寻找到对应的实例
                routerPath = type;
                break;
        }
        return routerPath;
    }

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

        //灯光、门锁、以及其他可控制设备使用  Constants.ROUTERPATH_CONTROLL_DEVICE 获取fragmentDispatcher
        //其他设备都需要使用单独的控制界面，MenuOperator的第二个参数为ARouter的path
        switch (deviceType.getDeviceType()) {
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.LIGHT:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_light_selected, R.drawable.mediaroom4_menu_light_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.CURTAIN:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_curtain_selected, R.drawable.mediaroom4_menu_curtain_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.FAN:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE, deviceType.getDeviceType(), null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_fan_selected, R.drawable.mediaroom4_menu_fan_unselect, R.drawable.mediaroom4_menuitem_selected, null);
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

            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.RECORDED_BROAD_CAST:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_RECORD, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_record_selected, R.drawable.mediaroom4_menu_record_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.VIDEO_MATRIX:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_MATRIX2, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_matrix_selected, R.drawable.mediaroom4_menu_matrix_unselect, R.drawable.mediaroom4_menuitem_selected, null);
                break;

            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.WIRELESS_DISPLAY:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_WIRELESS, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_wireless_closed, R.drawable.mediaroom4_wireless_closed, R.drawable.mediaroom4_menuitem_selected, null);
                Version1Dispatcher.WirelessMode = 1;
            default:
                if (deviceType.getDeviceType().startsWith(DeviceTag.DEVICE_CUSTOM)) {
                    menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM_MENU, deviceType.getDeviceType(), null);
                    operator = new MenuOperator(menuEntity, deviceType.getMenuSelectedIconResId(), deviceType.getMenuUnselectedIconResId(), R.drawable.mediaroom4_menuitem_selected, null);
                }
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
        //灯光、门锁、以及其他可控制设备使用  Constants.ROUTERPATH_CONTROLL_DEVICE 获取fragmentDispatcher
        //其他设备都需要使用单独的控制界面，MenuOperator的第二个参数为ARouter的path
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
            case com.sunmnet.mediaroom.devicebean.enums.DeviceType.WIRELESS_DISPLAY:
                menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_WIRELESS, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_wireless_closed, R.drawable.mediaroom4_wireless_closed, R.drawable.mediaroom4_menuitem_selected, null);
                Version1Dispatcher.WirelessMode = 1;
                break;
            default:
                if (deviceType.getDeviceType().startsWith(DeviceTag.DEVICE_CUSTOM)) {
                    menuEntity = new MenuEntity(menuName, Constants.ROUTERPATH_CONTROLL_DEVICE_CUSTOM_MENU, deviceType.getDeviceType(), null);
                    operator = new MenuOperator(menuEntity, deviceType.getMenuSelectedIconResId(), deviceType.getMenuUnselectedIconResId(), R.drawable.mediaroom4_menuitem_selected, null);
                }
                break;
        }
        if (operator != null) {
            menus.add(operator);
        }
    }

    private void addMenus(SysSpTempConfigFileDto.MenuSetting setting, List<MenuOperator> menus) {
        MenuOperator operator = null;
        MenuEntity menuEntity = null;
        //灯光、门锁、以及其他可控制设备使用  Constants.ROUTERPATH_CONTROLL_DEVICE 获取fragmentDispatcher
        //其他设备都需要使用单独的控制界面，MenuOperator的第二个参数为ARouter的path
        if (setting.isEnable()) {
            switch (setting.getKey()) {
                case "MAIN":
                    menuEntity = new MenuEntity(setting.getAliasName(), CONTROLLER_VERSION1_MAINPAGE, null, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_mainpage_selected, R.drawable.mediaroom4_menu_mainpage_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                    menus.add(operator);
                    break;
                case "MEDIA":
                    menuEntity = new MenuEntity(setting.getAliasName(), CONTROLLER_VERSION1_MEDIA, null, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_media_selected, R.drawable.mediaroom4_menu_media_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                    menus.add(operator);
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

    private void addMenus(String deviceType, List<MenuOperator> menus) {
        MenuOperator operator = null;
        MenuEntity menuEntity = null;
        //灯光、门锁、以及其他可控制设备使用  Constants.ROUTERPATH_CONTROLL_DEVICE 获取fragmentDispatcher
        //其他设备都需要使用单独的控制界面，MenuOperator的第二个参数为ARouter的path
        switch (deviceType) {
            case "MAINPAGE":
                menuEntity = new MenuEntity(TabspApplication.getInstance().getString(R.string.name_mainpage), CONTROLLER_VERSION1_MAINPAGE, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_mainpage_selected, R.drawable.mediaroom4_menu_mainpage_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                break;
            case "MEDIA":
                menuEntity = new MenuEntity(TabspApplication.getInstance().getString(R.string.name_media), CONTROLLER_VERSION1_MEDIA, null, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_media_selected, R.drawable.mediaroom4_menu_media_unselect, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                break;
        }
        if (operator != null) {
            menus.add(operator);
        }
    }

    @Override
    public <T> List<String> setCompareMenu(List<T> menus) {
        List<String> strings = new ArrayList<>();

        return strings;
    }

    /**
     * 根据设备类型生成菜单
     */
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

    /**
     * 根据配置文件中的数据加载菜单
     */
    private List<MenuOperator> getMenusByMenuNames() {
        List<MenuOperator> operators = new ArrayList<>();
        for (int i = 0, size = this.config.getMenuNames().size(); i < size; i++) {
            addMenus(this.config.getMenuNames().get(i), operators);
        }
        List<DeviceType> customTypeList = DeviceType.getCustomTypeList();
        DeviceType[] customTypes = new DeviceType[customTypeList.size()];
        customTypeList.toArray(customTypes);
        addMenus(operators, customTypes);
        return operators;
    }

    /**
     * 未在平台指定菜单时，使用默认的
     */
    private List<MenuOperator> getDefaultMenus() {
        List<MenuOperator> operators = new ArrayList<>();
        addMenus("MAINPAGE", operators);
        addMenus("MEDIA", operators);
        DeviceType[] types = new DeviceType[]{
                DeviceType.AIRCONDITIONER, DeviceType.LIGHT,
                DeviceType.CURTAIN, DeviceType.FAN,
                DeviceType.DOOR, DeviceType.FRESHAIR,
                DeviceType.MATRIX, DeviceType.RECORDED,
                DeviceType.WIRELESS
        };
        addMenus(operators, types);

        List<DeviceType> customTypeList = DeviceType.getCustomTypeList();
        DeviceType[] customTypes = new DeviceType[customTypeList.size()];
        customTypeList.toArray(customTypes);
        addMenus(operators, customTypes);
        //testWireless(operators);
        return operators;
    }

    private void testWireless(List<MenuOperator> operators) {
        addMenus(DeviceType.WIRELESS, operators);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                WirelessOperator operator = new WirelessOperator("172.16.2.120");
                EventBus.getDefault().postSticky(operator);
            }
        });
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
}
