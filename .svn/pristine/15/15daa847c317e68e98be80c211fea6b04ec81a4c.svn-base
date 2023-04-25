package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.graphics.Color;
import android.text.TextUtils;

import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.bean.Device;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.device.events.DeviceLoadedEvent;
import com.sunmnet.mediaroom.devicebean.form.IForm;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.bean.SysSpTempConfigFileDto;
import com.sunmnet.mediaroom.tabsp.bean.TabspConfig;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.AbstractDispatcher;
import com.sunmnet.mediaroom.tabsp.controll.version3.adapters.DeviceMenu;
import com.sunmnet.mediaroom.tabsp.controll.version3.bean.MenuType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Version3Dispatcher extends AbstractDispatcher {
    TabspConfig config;
    public final static String DETAIL_MENU_MEDIA = "MEDIA";
    public final static String DETAIL_MENU_ENVIRMENT = "ENVIRMENT";
    public final static String DETAIL_MENU_RECORD = "RECORD";
    public final static String DETAIL_MENU_MATRIX = "MATRIX";
    Map<String, List<DeviceMenu>> menuTypes;
    Map<String, DeviceMenu> typeInMenus;

    void init() {
        menuTypes = new HashMap<>();
        typeInMenus = new HashMap<>();
        DeviceType[] types = new DeviceType[]{
                DeviceType.PC, DeviceType.INTERACTIVE, DeviceType.PROJECTOR, DeviceType.SCREEN, DeviceType.SOUND
        };
        menuTypes.put(Version3Dispatcher.DETAIL_MENU_MEDIA, addMenu(types));
        types = new DeviceType[]{
                DeviceType.LIGHT, DeviceType.AIRCONDITIONER, DeviceType.CURTAIN, DeviceType.FRESHAIR, DeviceType.DOOR
        };
        menuTypes.put(Version3Dispatcher.DETAIL_MENU_ENVIRMENT, addMenu(types));
        types = new DeviceType[]{
                new MenuType(Constants.ROUTERPATH_CONTROLL_DEVICE_RECORD, "录播"), new MenuType(DeviceType.REMOTEINTERACTION, "远程互动")
        };
        menuTypes.put(Version3Dispatcher.DETAIL_MENU_RECORD, addMenu(types));
        types = new DeviceType[]{
                new MenuType(Constants.ROUTERPATH_CONTROLL_V3_DEVICE_MATRIX_SCENE, "矩阵场景"),
                new MenuType(Constants.ROUTERPATH_CONTROLL_V3_DEVICE_MATRIX, "自由控制")
        };
        menuTypes.put(Version3Dispatcher.DETAIL_MENU_MATRIX, addMenu(types));
    }

    private List<DeviceMenu> addMenu(DeviceType... types) {
        List<DeviceMenu> menus = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            DeviceMenu menu = new DeviceMenu();
            menu.deviceType = types[i];
            menu.deviceTypeName = types[i].getDeviceTypeName();
            menus.add(menu);
            typeInMenus.put(menu.deviceType.getDeviceType(), menu);
        }
        return menus;
    }

    public Version3Dispatcher(TabspConfig config) {
        this.config = config;
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true)
    public void onDeviceLoaded(DeviceLoadedEvent event) {
        init();
        EventBus.getDefault().removeStickyEvent(event);
        EventBus.getDefault().unregister(this);
    }

    private List<DeviceMenu> filter(List<DeviceMenu> menus, String key) {
        if (config.getMenuNames() != null && key != null && menuTypes.containsKey(key)) {
            for (int i = 0, size = config.getMenuNames().size(); i < size; i++) {
                SysSpTempConfigFileDto.MenuSetting setting = config.getMenuNames().get(i);
                if (!TextUtils.isEmpty(setting.getKey())
                        && typeInMenus.containsKey(setting.getKey())
                        && !setting.isEnable()) {
                    menus.remove(typeInMenus.get(setting.getKey()));
                }
            }
        }
        return menus;
    }

    private List<DeviceMenu> getRecordMenu(String key) {
        Controller controller = Controller.getInstance();
        List<DeviceMenu> menus = menuTypes.get(key);
        if (controller.getDevicesByDeviceType(DeviceType.RECORDED) == null) {
            menus.remove(typeInMenus.get(Constants.ROUTERPATH_CONTROLL_DEVICE_RECORD));
        }
        if (controller.getDevicesByDeviceType(DeviceType.REMOTEINTERACTION) == null) {
            menus.remove(typeInMenus.get(DeviceType.REMOTEINTERACTION.getDeviceType()));
        }
        return menus;
    }

    private List<DeviceMenu> getMatrixMenu(String key) {
        List<DeviceMenu> menus = new ArrayList<>();
        Controller controller = Controller.getInstance();
        if (controller.getDevicesByDeviceType(DeviceType.MATRIX) != null) {
            return this.menuTypes.get(key);
        }
        return menus;
    }

    public List<DeviceMenu> getEffectiveMenu(String key) {
        List<DeviceMenu> menus = new ArrayList<>();
        List<DeviceMenu> cache = null;
        if (key != null) {
            switch (key) {
                case DETAIL_MENU_MEDIA:
                    cache = this.menuTypes.get(key);
                    menus = filter(cache, key);
                    //添加自定义设备菜单
                    DeviceType[] deviceTypes = new DeviceType[DeviceType.getCustomTypeList().size()];
                    DeviceType.getCustomTypeList().toArray(deviceTypes);
                    menus.addAll(addMenu(deviceTypes));
                    break;
                case DETAIL_MENU_ENVIRMENT:
                    cache = this.menuTypes.get(key);
                    menus = filter(cache, key);
                    break;
                case DETAIL_MENU_RECORD:
                    menus = getRecordMenu(key);
                    break;
                case DETAIL_MENU_MATRIX:
                    menus = getMatrixMenu(key);
                    break;
            }
        } else {
            DeviceType[] types = new DeviceType[]{
                    DeviceType.PROJECTOR, DeviceType.SCREEN, DeviceType.INTERACTIVE, DeviceType.LIGHT, DeviceType.AIRCONDITIONER,
                    DeviceType.PC, DeviceType.SOUND, DeviceType.CURTAIN
            };
            for (int i = 0; i < types.length; i++) {
                menus.add(new DeviceMenu(types[i], types[i].getDeviceTypeName()));
            }
        }
        return menus;
    }

    @Override
    public String getPageByType(String type) {
        String routerPath = null;
        switch (type) {
            case PAGE_REDIRECT_LOGIN_ENTER:
                routerPath = Constants.ROUTERPATH_LOGIN_MAIN;
                break;
            case PAGE_REDIRECT_CONTENT:
                routerPath = Constants.ROUTERPATH_ACTIVITY_VERSION3_CONTENT;
                break;
            case FRAGMENT_TITLE:
                routerPath = Constants.ROUTERPATH_CONTROLL_V3_TITLE;
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
    public <T> List<String> setCompareMenu(List<T> menus) {
        init();
        return super.setCompareMenu(menus);
    }

    private boolean existRecord() {
        Controller controller = Controller.getInstance();
        if (controller.getDevicesByDeviceType(DeviceType.RECORDED) != null
                || controller.getDevicesByDeviceType(DeviceType.REMOTEINTERACTION) != null) {
            return true;
        }
        return false;
    }

    private MenuOperator createMenu(int name) {
        MenuEntity menuEntity = null;
        MenuOperator operator = null;
        switch (name) {
            case R.string.name_version3_media:
                menuEntity = new MenuEntity(TabspApplication.getInstance().getString(name), Constants.ROUTERPATH_CONTROLL_V3_DEVICE_OPT, DETAIL_MENU_MEDIA, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_media_unselect, R.drawable.mediaroom4_menu_media_selected, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                break;
            case R.string.name_version3_envirment:
                menuEntity = new MenuEntity(TabspApplication.getInstance().getString(name), Constants.ROUTERPATH_CONTROLL_V3_DEVICE_OPT, DETAIL_MENU_ENVIRMENT, null);
                operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_version3_envirment_unselect, R.drawable.mediaroom4_version3_envirment_selected, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                break;
            case R.string.name_version3_record:
                if (existRecord()) {
                    menuEntity = new MenuEntity(TabspApplication.getInstance().getString(name), null, DETAIL_MENU_RECORD, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_version3_record_unselect, R.drawable.mediaroom4_version3_record_selected, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                }
                break;
            case R.string.name_version3_matrix:
                if (Controller.getInstance().getDevicesByDeviceType(DeviceType.MATRIX) != null) {
                    menuEntity = new MenuEntity(TabspApplication.getInstance().getString(name), null, DETAIL_MENU_MATRIX, null);
                    operator = new MenuOperator(menuEntity, R.drawable.mediaroom4_menu_matrix_unselect, R.drawable.mediaroom4_menu_matrix_selected, R.drawable.mediaroom4_menuitem_selected, null, Color.WHITE, Color.WHITE);
                }
                break;
        }
        return operator;
    }

    @Override
    public List<MenuOperator> getMenus() {
        List<MenuOperator> operators = new ArrayList<>();
        int[] strings = new int[]{
                R.string.name_version3_media, R.string.name_version3_envirment,
                R.string.name_version3_record, R.string.name_version3_matrix
        };
        for (int a : strings
        ) {
            MenuOperator operator = createMenu(a);
            if (operator != null) operators.add(operator);
        }
        return operators;
    }

}
