package com.sunmnet.mediaroom.tabsp.controll.service;

import android.view.View;

import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.bean.enums.SoftType;

import java.util.List;

/**
 * 版本分发器
 */
public interface Dispatcher {
    /**
     * 参数key
     * */
    public static final String PAGE_KEY = "REDIRECT_KEY";
    public static final String PAGE_REDIRECT_GATE = "mediaroom.tabsp.gateway";
    /**
     * 登录界面的key-value
     * */
    public static final String PAGE_REDIRECT_LOGIN_ENTER = "mediaroom.tabsp.login";
    /**
     * 首页主内容的key-value
     * */
    public static final String PAGE_REDIRECT_CONTENT = "mediaroom.tabsp.content";
    /**
     * 标题key-value
     * */
    public static final String FRAGMENT_TITLE = "mediaroom.tabsp.title";
    /**
     * 菜单key-value
     * */
    public static final String FRAGMENT_MENU = "mediaroom.tabsp.menu";
    public static final String FRAGMENT_COMMON_DEVICE = "mediaroom.tabsp.device";
    /**
     * 根据类型获取  Arouter路径
     */
    public String getPageByType(String type);

    /**
     * 获取菜单按钮
     */
    public List<MenuOperator> getMenus();

    public <T> List<String> setCompareMenu(List<T> menus);

    /**
     * 部署界面
     */
    public void apply(String type, View view);
}
