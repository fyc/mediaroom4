package com.sunmnet.mediaroom.tabsp;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import com.sunmnet.mediaroom.tabsp.databinding.AirconditionerBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.AirconditionerBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.AirconditionerControllItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.AirconditionerControllItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.ControllItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.ControllItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.CustomBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.CustomBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.CustomDetailBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.CustomDetailBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.CustomDeviceItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.CustomDeviceItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceAdapterImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceAdapterLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceInfoBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceInfoBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceSceneItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceSceneItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceTypeListImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DeviceTypeListLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DialogFaultDescriptionBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DialogSelectMainOutScreenBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DimmerBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DimmerBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DiscussItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DiscussItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DiscussionBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DiscussionBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DisplayItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DisplayItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DoorBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DoorBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DoorInfoBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.DoorInfoBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.EthernetDataBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.EthernetDataBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.FreshAirBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.FreshAirBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.FreshAirControllItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.FreshAirControllItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.InteracitveControllerImpl;
import com.sunmnet.mediaroom.tabsp.databinding.InteracitveControllerLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.InteractiveBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.InteractiveBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.InteractiveControllItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.InteractiveControllItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.LectureBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.LectureBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.LectureItemBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.LectureItemBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MainpageBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MainpageBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixInterfaceBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixInterfaceBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixScene3BindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MatrixScene3BindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MediaDeviceAdapterImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MediaDeviceAdapterLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MediaInfoBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MediaInfoBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MenuAdapterBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MenuAdapterBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MenuBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.MenuBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.ModeItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.ModeItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.NumberItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.NumberItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.QuitAppDataBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.RadioButtonItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.RadioButtonItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.RateBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.RateBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.RollingItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.RollingItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.SceneItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.SceneItemLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.SettingAdapterMatcherImpl;
import com.sunmnet.mediaroom.tabsp.databinding.SettingAdapterMatcherLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.SettingItemMenuImpl;
import com.sunmnet.mediaroom.tabsp.databinding.SettingItemMenuLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspDiscussionInputLayoutBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspDiscussionInputLayoutBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1CourseLayoutBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1CourseLayoutBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1EnvirmentLayoutBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1EnvirmentLayoutBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1MatrixSceneLayoutBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1MatrixSceneLayoutBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1SceneLayoutBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion1SceneLayoutBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion3MainMatrixLayoutBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.TabspVersion3MainMatrixLayoutBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.V3ContentImpl;
import com.sunmnet.mediaroom.tabsp.databinding.V3ContentLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.V3MatrixBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.V3MatrixBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.VgaItemBindingImpl;
import com.sunmnet.mediaroom.tabsp.databinding.VgaItemBindingLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.WirelessDataImpl;
import com.sunmnet.mediaroom.tabsp.databinding.WirelessDataLandImpl;
import com.sunmnet.mediaroom.tabsp.databinding.WirelessItemImpl;
import com.sunmnet.mediaroom.tabsp.databinding.WirelessItemLandImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_DIALOGFAULTDESCRIPTION = 1;

  private static final int LAYOUT_DIALOGSELECTMAINOUTSCREEN = 2;

  private static final int LAYOUT_TABSPAIRCONDITIONERLAYOUT = 3;

  private static final int LAYOUT_TABSPCUSTOMDEVICEDETAILLAYOUT = 4;

  private static final int LAYOUT_TABSPCUSTOMDEVICEITEMLAYOUT = 5;

  private static final int LAYOUT_TABSPCUSTOMDEVICELAYOUT = 6;

  private static final int LAYOUT_TABSPDEFAULTDOORITEMLAYOUT = 7;

  private static final int LAYOUT_TABSPDEVICECOMMONLAYOUT = 8;

  private static final int LAYOUT_TABSPDEVICEITEMLAYOUT = 9;

  private static final int LAYOUT_TABSPDEVICELISTVIEWITEMLAYOUT = 10;

  private static final int LAYOUT_TABSPDISCUSSIONINPUTLAYOUT = 11;

  private static final int LAYOUT_TABSPDISCUSSTIONGROUPITEM = 12;

  private static final int LAYOUT_TABSPDOORITEMLAYOUT = 13;

  private static final int LAYOUT_TABSPFRESHAIRLAYOUT = 14;

  private static final int LAYOUT_TABSPINTERACTIVELAYOUT = 15;

  private static final int LAYOUT_TABSPLECTUREITEMLAYOUT = 16;

  private static final int LAYOUT_TABSPLIGHTITEMLAYOUT = 17;

  private static final int LAYOUT_TABSPMATRIXINTERFACEITEMLAYOUT = 18;

  private static final int LAYOUT_TABSPMATRIXLAYOUT = 19;

  private static final int LAYOUT_TABSPMENUITEMLAYOUT = 20;

  private static final int LAYOUT_TABSPMENULAYOUT = 21;

  private static final int LAYOUT_TABSPMODEITEMLAYOUT = 22;

  private static final int LAYOUT_TABSPRATEITEM = 23;

  private static final int LAYOUT_TABSPROLLINGTEXTITEM = 24;

  private static final int LAYOUT_TABSPSETTINGETHERNETLAYOUT = 25;

  private static final int LAYOUT_TABSPSETTINGLAYOUT = 26;

  private static final int LAYOUT_TABSPSETTINGQUITAPPLAYOUT = 27;

  private static final int LAYOUT_TABSPSETTINGMENUITEM = 28;

  private static final int LAYOUT_TABSPVERSION1COURSELAYOUT = 29;

  private static final int LAYOUT_TABSPVERSION1ENVIRMENTLAYOUT = 30;

  private static final int LAYOUT_TABSPVERSION1MAINPAGELAYOUT = 31;

  private static final int LAYOUT_TABSPVERSION1MATRIXSCENELAYOUT = 32;

  private static final int LAYOUT_TABSPVERSION1MEDIAITEM = 33;

  private static final int LAYOUT_TABSPVERSION1MEDIALAYOUT = 34;

  private static final int LAYOUT_TABSPVERSION1MEDIARVGAITEM = 35;

  private static final int LAYOUT_TABSPVERSION1SCENEITEMLAYOUT = 36;

  private static final int LAYOUT_TABSPVERSION1SCENELAYOUT = 37;

  private static final int LAYOUT_TABSPVERSION2CUSTOMDISCUSSITEM = 38;

  private static final int LAYOUT_TABSPVERSION2DISCUSSIONLAYOUT = 39;

  private static final int LAYOUT_TABSPVERSION2DISPLAYITEM = 40;

  private static final int LAYOUT_TABSPVERSION2DISPLAYLAYOUT = 41;

  private static final int LAYOUT_TABSPVERSION2LECTURELAYOUT = 42;

  private static final int LAYOUT_TABSPVERSION3AIRCONDITIONERITEMLAYOUT = 43;

  private static final int LAYOUT_TABSPVERSION3DEVICECONTROLLITEMLAYOUT = 44;

  private static final int LAYOUT_TABSPVERSION3DEVICYPEITEMLAYOUT = 45;

  private static final int LAYOUT_TABSPVERSION3FRESHAIRITEMLAYOUT = 46;

  private static final int LAYOUT_TABSPVERSION3INTERACTIVEITEMLAYOUT = 47;

  private static final int LAYOUT_TABSPVERSION3MAINCONTENTLAYOUT = 48;

  private static final int LAYOUT_TABSPVERSION3MAINMATRIXLAYOUT = 49;

  private static final int LAYOUT_TABSPVERSION3MATRIXFORFREE = 50;

  private static final int LAYOUT_TABSPVERSION3MATRIXSCENELAYOUT = 51;

  private static final int LAYOUT_TABSPVERSION3SCENEITEM = 52;

  private static final int LAYOUT_TABSPWIRELESSITEMLAYOUT = 53;

  private static final int LAYOUT_TABSPWIRELESSLAYOUT = 54;

  private static final int LAYOUT_TABSPWIRELESSSELECTIONITEM = 55;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(55);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.dialog_fault_description, LAYOUT_DIALOGFAULTDESCRIPTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.dialog_select_main_out_screen, LAYOUT_DIALOGSELECTMAINOUTSCREEN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_airconditioner_layout, LAYOUT_TABSPAIRCONDITIONERLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_detail_layout, LAYOUT_TABSPCUSTOMDEVICEDETAILLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_item_layout, LAYOUT_TABSPCUSTOMDEVICEITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_layout, LAYOUT_TABSPCUSTOMDEVICELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_default_door_item_layout, LAYOUT_TABSPDEFAULTDOORITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_common_layout, LAYOUT_TABSPDEVICECOMMONLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_item_layout, LAYOUT_TABSPDEVICEITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_listviewitem_layout, LAYOUT_TABSPDEVICELISTVIEWITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_discussion_input_layout, LAYOUT_TABSPDISCUSSIONINPUTLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_discusstion_group_item, LAYOUT_TABSPDISCUSSTIONGROUPITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_door_item_layout, LAYOUT_TABSPDOORITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_freshair_layout, LAYOUT_TABSPFRESHAIRLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_interactive_layout, LAYOUT_TABSPINTERACTIVELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_lecture_item_layout, LAYOUT_TABSPLECTUREITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_light_item_layout, LAYOUT_TABSPLIGHTITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_matrix_interface_item_layout, LAYOUT_TABSPMATRIXINTERFACEITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_matrix_layout, LAYOUT_TABSPMATRIXLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_menu_item_layout, LAYOUT_TABSPMENUITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_menu_layout, LAYOUT_TABSPMENULAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_mode_item_layout, LAYOUT_TABSPMODEITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_rate_item, LAYOUT_TABSPRATEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_rollingtext_item, LAYOUT_TABSPROLLINGTEXTITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_setting_ethernet_layout, LAYOUT_TABSPSETTINGETHERNETLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_setting_layout, LAYOUT_TABSPSETTINGLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_setting_quit_app_layout, LAYOUT_TABSPSETTINGQUITAPPLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_settingmenu_item, LAYOUT_TABSPSETTINGMENUITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_course_layout, LAYOUT_TABSPVERSION1COURSELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_envirment_layout, LAYOUT_TABSPVERSION1ENVIRMENTLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_mainpage_layout, LAYOUT_TABSPVERSION1MAINPAGELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_matrix_scene_layout, LAYOUT_TABSPVERSION1MATRIXSCENELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_media_item, LAYOUT_TABSPVERSION1MEDIAITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_media_layout, LAYOUT_TABSPVERSION1MEDIALAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_mediar_vga_item, LAYOUT_TABSPVERSION1MEDIARVGAITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_scene_item_layout, LAYOUT_TABSPVERSION1SCENEITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_scene_layout, LAYOUT_TABSPVERSION1SCENELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_custom_discuss_item, LAYOUT_TABSPVERSION2CUSTOMDISCUSSITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_discussion_layout, LAYOUT_TABSPVERSION2DISCUSSIONLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_display_item, LAYOUT_TABSPVERSION2DISPLAYITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_display_layout, LAYOUT_TABSPVERSION2DISPLAYLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_lecture_layout, LAYOUT_TABSPVERSION2LECTURELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_airconditioner_item_layout, LAYOUT_TABSPVERSION3AIRCONDITIONERITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_device_controll_item_layout, LAYOUT_TABSPVERSION3DEVICECONTROLLITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_devicype_item_layout, LAYOUT_TABSPVERSION3DEVICYPEITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_freshair_item_layout, LAYOUT_TABSPVERSION3FRESHAIRITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_interactive_item_layout, LAYOUT_TABSPVERSION3INTERACTIVEITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_main_content_layout, LAYOUT_TABSPVERSION3MAINCONTENTLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_main_matrix_layout, LAYOUT_TABSPVERSION3MAINMATRIXLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_matrix_for_free, LAYOUT_TABSPVERSION3MATRIXFORFREE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_matrix_scene_layout, LAYOUT_TABSPVERSION3MATRIXSCENELAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_scene_item, LAYOUT_TABSPVERSION3SCENEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_item_layout, LAYOUT_TABSPWIRELESSITEMLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_layout, LAYOUT_TABSPWIRELESSLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_selection_item, LAYOUT_TABSPWIRELESSSELECTIONITEM);
  }

  private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_DIALOGFAULTDESCRIPTION: {
        if ("layout/dialog_fault_description_0".equals(tag)) {
          return new DialogFaultDescriptionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_fault_description is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSELECTMAINOUTSCREEN: {
        if ("layout/dialog_select_main_out_screen_0".equals(tag)) {
          return new DialogSelectMainOutScreenBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_select_main_out_screen is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPAIRCONDITIONERLAYOUT: {
        if ("layout-land/tabsp_airconditioner_layout_0".equals(tag)) {
          return new AirconditionerBindingLandImpl(component, view);
        }
        if ("layout/tabsp_airconditioner_layout_0".equals(tag)) {
          return new AirconditionerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_airconditioner_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPCUSTOMDEVICEDETAILLAYOUT: {
        if ("layout-land/tabsp_custom_device_detail_layout_0".equals(tag)) {
          return new CustomDetailBindingLandImpl(component, view);
        }
        if ("layout/tabsp_custom_device_detail_layout_0".equals(tag)) {
          return new CustomDetailBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_custom_device_detail_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPCUSTOMDEVICEITEMLAYOUT: {
        if ("layout-land/tabsp_custom_device_item_layout_0".equals(tag)) {
          return new CustomDeviceItemLandImpl(component, view);
        }
        if ("layout/tabsp_custom_device_item_layout_0".equals(tag)) {
          return new CustomDeviceItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_custom_device_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPCUSTOMDEVICELAYOUT: {
        if ("layout/tabsp_custom_device_layout_0".equals(tag)) {
          return new CustomBindingImpl(component, view);
        }
        if ("layout-land/tabsp_custom_device_layout_0".equals(tag)) {
          return new CustomBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_custom_device_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPDEFAULTDOORITEMLAYOUT: {
        if ("layout/tabsp_default_door_item_layout_0".equals(tag)) {
          return new DoorInfoBindingImpl(component, view);
        }
        if ("layout-land/tabsp_default_door_item_layout_0".equals(tag)) {
          return new DoorInfoBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_default_door_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPDEVICECOMMONLAYOUT: {
        if ("layout-land/tabsp_device_common_layout_0".equals(tag)) {
          return new DeviceAdapterLandImpl(component, view);
        }
        if ("layout/tabsp_device_common_layout_0".equals(tag)) {
          return new DeviceAdapterImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_device_common_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPDEVICEITEMLAYOUT: {
        if ("layout-land/tabsp_device_item_layout_0".equals(tag)) {
          return new DeviceInfoBindingLandImpl(component, view);
        }
        if ("layout/tabsp_device_item_layout_0".equals(tag)) {
          return new DeviceInfoBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_device_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPDEVICELISTVIEWITEMLAYOUT: {
        if ("layout-land/tabsp_device_listviewitem_layout_0".equals(tag)) {
          return new DeviceItemLandImpl(component, view);
        }
        if ("layout/tabsp_device_listviewitem_layout_0".equals(tag)) {
          return new DeviceItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_device_listviewitem_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPDISCUSSIONINPUTLAYOUT: {
        if ("layout-land/tabsp_discussion_input_layout_0".equals(tag)) {
          return new TabspDiscussionInputLayoutBindingLandImpl(component, view);
        }
        if ("layout/tabsp_discussion_input_layout_0".equals(tag)) {
          return new TabspDiscussionInputLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_discussion_input_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPDISCUSSTIONGROUPITEM: {
        if ("layout-land/tabsp_discusstion_group_item_0".equals(tag)) {
          return new NumberItemLandImpl(component, view);
        }
        if ("layout/tabsp_discusstion_group_item_0".equals(tag)) {
          return new NumberItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_discusstion_group_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPDOORITEMLAYOUT: {
        if ("layout-land/tabsp_door_item_layout_0".equals(tag)) {
          return new DoorBindingLandImpl(component, view);
        }
        if ("layout/tabsp_door_item_layout_0".equals(tag)) {
          return new DoorBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_door_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPFRESHAIRLAYOUT: {
        if ("layout/tabsp_freshair_layout_0".equals(tag)) {
          return new FreshAirBindingImpl(component, view);
        }
        if ("layout-land/tabsp_freshair_layout_0".equals(tag)) {
          return new FreshAirBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_freshair_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPINTERACTIVELAYOUT: {
        if ("layout/tabsp_interactive_layout_0".equals(tag)) {
          return new InteractiveBindingImpl(component, view);
        }
        if ("layout-land/tabsp_interactive_layout_0".equals(tag)) {
          return new InteractiveBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_interactive_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPLECTUREITEMLAYOUT: {
        if ("layout-land/tabsp_lecture_item_layout_0".equals(tag)) {
          return new LectureItemBindingLandImpl(component, view);
        }
        if ("layout/tabsp_lecture_item_layout_0".equals(tag)) {
          return new LectureItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_lecture_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPLIGHTITEMLAYOUT: {
        if ("layout-land/tabsp_light_item_layout_0".equals(tag)) {
          return new DimmerBindingLandImpl(component, view);
        }
        if ("layout/tabsp_light_item_layout_0".equals(tag)) {
          return new DimmerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_light_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPMATRIXINTERFACEITEMLAYOUT: {
        if ("layout-land/tabsp_matrix_interface_item_layout_0".equals(tag)) {
          return new MatrixInterfaceBindingLandImpl(component, view);
        }
        if ("layout/tabsp_matrix_interface_item_layout_0".equals(tag)) {
          return new MatrixInterfaceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_matrix_interface_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPMATRIXLAYOUT: {
        if ("layout/tabsp_matrix_layout_0".equals(tag)) {
          return new MatrixBindingImpl(component, view);
        }
        if ("layout-land/tabsp_matrix_layout_0".equals(tag)) {
          return new MatrixBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_matrix_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPMENUITEMLAYOUT: {
        if ("layout-land/tabsp_menu_item_layout_0".equals(tag)) {
          return new MenuBindingLandImpl(component, view);
        }
        if ("layout/tabsp_menu_item_layout_0".equals(tag)) {
          return new MenuBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_menu_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPMENULAYOUT: {
        if ("layout/tabsp_menu_layout_0".equals(tag)) {
          return new MenuAdapterBindingImpl(component, view);
        }
        if ("layout-land/tabsp_menu_layout_0".equals(tag)) {
          return new MenuAdapterBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_menu_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPMODEITEMLAYOUT: {
        if ("layout-land/tabsp_mode_item_layout_0".equals(tag)) {
          return new ModeItemLandImpl(component, view);
        }
        if ("layout/tabsp_mode_item_layout_0".equals(tag)) {
          return new ModeItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_mode_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPRATEITEM: {
        if ("layout-land/tabsp_rate_item_0".equals(tag)) {
          return new RateBindingLandImpl(component, view);
        }
        if ("layout/tabsp_rate_item_0".equals(tag)) {
          return new RateBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_rate_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPROLLINGTEXTITEM: {
        if ("layout-land/tabsp_rollingtext_item_0".equals(tag)) {
          return new RollingItemLandImpl(component, view);
        }
        if ("layout/tabsp_rollingtext_item_0".equals(tag)) {
          return new RollingItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_rollingtext_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPSETTINGETHERNETLAYOUT: {
        if ("layout/tabsp_setting_ethernet_layout_0".equals(tag)) {
          return new EthernetDataBindingImpl(component, view);
        }
        if ("layout-land/tabsp_setting_ethernet_layout_0".equals(tag)) {
          return new EthernetDataBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_setting_ethernet_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPSETTINGLAYOUT: {
        if ("layout-land/tabsp_setting_layout_0".equals(tag)) {
          return new SettingAdapterMatcherLandImpl(component, view);
        }
        if ("layout/tabsp_setting_layout_0".equals(tag)) {
          return new SettingAdapterMatcherImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_setting_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPSETTINGQUITAPPLAYOUT: {
        if ("layout/tabsp_setting_quit_app_layout_0".equals(tag)) {
          return new QuitAppDataBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_setting_quit_app_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPSETTINGMENUITEM: {
        if ("layout-land/tabsp_settingmenu_item_0".equals(tag)) {
          return new SettingItemMenuLandImpl(component, view);
        }
        if ("layout/tabsp_settingmenu_item_0".equals(tag)) {
          return new SettingItemMenuImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_settingmenu_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1COURSELAYOUT: {
        if ("layout-land/tabsp_version1_course_layout_0".equals(tag)) {
          return new TabspVersion1CourseLayoutBindingLandImpl(component, view);
        }
        if ("layout/tabsp_version1_course_layout_0".equals(tag)) {
          return new TabspVersion1CourseLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_course_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1ENVIRMENTLAYOUT: {
        if ("layout/tabsp_version1_envirment_layout_0".equals(tag)) {
          return new TabspVersion1EnvirmentLayoutBindingImpl(component, view);
        }
        if ("layout-land/tabsp_version1_envirment_layout_0".equals(tag)) {
          return new TabspVersion1EnvirmentLayoutBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_envirment_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1MAINPAGELAYOUT: {
        if ("layout/tabsp_version1_mainpage_layout_0".equals(tag)) {
          return new MainpageBindingImpl(component, view);
        }
        if ("layout-land/tabsp_version1_mainpage_layout_0".equals(tag)) {
          return new MainpageBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_mainpage_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1MATRIXSCENELAYOUT: {
        if ("layout-land/tabsp_version1_matrix_scene_layout_0".equals(tag)) {
          return new TabspVersion1MatrixSceneLayoutBindingLandImpl(component, view);
        }
        if ("layout/tabsp_version1_matrix_scene_layout_0".equals(tag)) {
          return new TabspVersion1MatrixSceneLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_matrix_scene_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1MEDIAITEM: {
        if ("layout/tabsp_version1_media_item_0".equals(tag)) {
          return new MediaInfoBindingImpl(component, view);
        }
        if ("layout-land/tabsp_version1_media_item_0".equals(tag)) {
          return new MediaInfoBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_media_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1MEDIALAYOUT: {
        if ("layout/tabsp_version1_media_layout_0".equals(tag)) {
          return new MediaDeviceAdapterImpl(component, view);
        }
        if ("layout-land/tabsp_version1_media_layout_0".equals(tag)) {
          return new MediaDeviceAdapterLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_media_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1MEDIARVGAITEM: {
        if ("layout/tabsp_version1_mediar_vga_item_0".equals(tag)) {
          return new VgaItemBindingImpl(component, view);
        }
        if ("layout-land/tabsp_version1_mediar_vga_item_0".equals(tag)) {
          return new VgaItemBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_mediar_vga_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1SCENEITEMLAYOUT: {
        if ("layout/tabsp_version1_scene_item_layout_0".equals(tag)) {
          return new SceneItemImpl(component, view);
        }
        if ("layout-land/tabsp_version1_scene_item_layout_0".equals(tag)) {
          return new SceneItemLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_scene_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION1SCENELAYOUT: {
        if ("layout/tabsp_version1_scene_layout_0".equals(tag)) {
          return new TabspVersion1SceneLayoutBindingImpl(component, view);
        }
        if ("layout-land/tabsp_version1_scene_layout_0".equals(tag)) {
          return new TabspVersion1SceneLayoutBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version1_scene_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION2CUSTOMDISCUSSITEM: {
        if ("layout-land/tabsp_version2_custom_discuss_item_0".equals(tag)) {
          return new DiscussItemLandImpl(component, view);
        }
        if ("layout/tabsp_version2_custom_discuss_item_0".equals(tag)) {
          return new DiscussItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version2_custom_discuss_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION2DISCUSSIONLAYOUT: {
        if ("layout/tabsp_version2_discussion_layout_0".equals(tag)) {
          return new DiscussionBindingImpl(component, view);
        }
        if ("layout-land/tabsp_version2_discussion_layout_0".equals(tag)) {
          return new DiscussionBindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version2_discussion_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION2DISPLAYITEM: {
        if ("layout/tabsp_version2_display_item_0".equals(tag)) {
          return new DisplayItemImpl(component, view);
        }
        if ("layout-land/tabsp_version2_display_item_0".equals(tag)) {
          return new DisplayItemLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version2_display_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION2DISPLAYLAYOUT: {
        if ("layout-land/tabsp_version2_display_layout_0".equals(tag)) {
          return new InteracitveControllerLandImpl(component, view);
        }
        if ("layout/tabsp_version2_display_layout_0".equals(tag)) {
          return new InteracitveControllerImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version2_display_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION2LECTURELAYOUT: {
        if ("layout-land/tabsp_version2_lecture_layout_0".equals(tag)) {
          return new LectureBindingLandImpl(component, view);
        }
        if ("layout/tabsp_version2_lecture_layout_0".equals(tag)) {
          return new LectureBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version2_lecture_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3AIRCONDITIONERITEMLAYOUT: {
        if ("layout/tabsp_version3_airconditioner_item_layout_0".equals(tag)) {
          return new AirconditionerControllItemImpl(component, view);
        }
        if ("layout-land/tabsp_version3_airconditioner_item_layout_0".equals(tag)) {
          return new AirconditionerControllItemLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_airconditioner_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3DEVICECONTROLLITEMLAYOUT: {
        if ("layout-land/tabsp_version3_device_controll_item_layout_0".equals(tag)) {
          return new ControllItemLandImpl(component, view);
        }
        if ("layout/tabsp_version3_device_controll_item_layout_0".equals(tag)) {
          return new ControllItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_device_controll_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3DEVICYPEITEMLAYOUT: {
        if ("layout/tabsp_version3_devicype_item_layout_0".equals(tag)) {
          return new DeviceTypeListImpl(component, view);
        }
        if ("layout-land/tabsp_version3_devicype_item_layout_0".equals(tag)) {
          return new DeviceTypeListLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_devicype_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3FRESHAIRITEMLAYOUT: {
        if ("layout-land/tabsp_version3_freshair_item_layout_0".equals(tag)) {
          return new FreshAirControllItemLandImpl(component, view);
        }
        if ("layout/tabsp_version3_freshair_item_layout_0".equals(tag)) {
          return new FreshAirControllItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_freshair_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3INTERACTIVEITEMLAYOUT: {
        if ("layout-land/tabsp_version3_interactive_item_layout_0".equals(tag)) {
          return new InteractiveControllItemLandImpl(component, view);
        }
        if ("layout/tabsp_version3_interactive_item_layout_0".equals(tag)) {
          return new InteractiveControllItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_interactive_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3MAINCONTENTLAYOUT: {
        if ("layout/tabsp_version3_main_content_layout_0".equals(tag)) {
          return new V3ContentImpl(component, view);
        }
        if ("layout-land/tabsp_version3_main_content_layout_0".equals(tag)) {
          return new V3ContentLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_main_content_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3MAINMATRIXLAYOUT: {
        if ("layout-land/tabsp_version3_main_matrix_layout_0".equals(tag)) {
          return new TabspVersion3MainMatrixLayoutBindingLandImpl(component, view);
        }
        if ("layout/tabsp_version3_main_matrix_layout_0".equals(tag)) {
          return new TabspVersion3MainMatrixLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_main_matrix_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3MATRIXFORFREE: {
        if ("layout-land/tabsp_version3_matrix_for_free_0".equals(tag)) {
          return new V3MatrixBindingLandImpl(component, view);
        }
        if ("layout/tabsp_version3_matrix_for_free_0".equals(tag)) {
          return new V3MatrixBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_matrix_for_free is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_TABSPVERSION3MATRIXSCENELAYOUT: {
        if ("layout/tabsp_version3_matrix_scene_layout_0".equals(tag)) {
          return new MatrixScene3BindingImpl(component, view);
        }
        if ("layout-land/tabsp_version3_matrix_scene_layout_0".equals(tag)) {
          return new MatrixScene3BindingLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_matrix_scene_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPVERSION3SCENEITEM: {
        if ("layout-land/tabsp_version3_scene_item_0".equals(tag)) {
          return new DeviceSceneItemLandImpl(component, view);
        }
        if ("layout/tabsp_version3_scene_item_0".equals(tag)) {
          return new DeviceSceneItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_version3_scene_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPWIRELESSITEMLAYOUT: {
        if ("layout-land/tabsp_wireless_item_layout_0".equals(tag)) {
          return new WirelessItemLandImpl(component, view);
        }
        if ("layout/tabsp_wireless_item_layout_0".equals(tag)) {
          return new WirelessItemImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_wireless_item_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPWIRELESSLAYOUT: {
        if ("layout/tabsp_wireless_layout_0".equals(tag)) {
          return new WirelessDataImpl(component, view);
        }
        if ("layout-land/tabsp_wireless_layout_0".equals(tag)) {
          return new WirelessDataLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_wireless_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TABSPWIRELESSSELECTIONITEM: {
        if ("layout/tabsp_wireless_selection_item_0".equals(tag)) {
          return new RadioButtonItemImpl(component, view);
        }
        if ("layout-land/tabsp_wireless_selection_item_0".equals(tag)) {
          return new RadioButtonItemLandImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for tabsp_wireless_selection_item is invalid. Received: " + tag);
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      // find which method will have it. -1 is necessary becausefirst id starts with 1;
      int methodIndex = (localizedLayoutId - 1) / 50;
      switch(methodIndex) {
        case 0: {
          return internalGetViewDataBinding0(component, view, localizedLayoutId, tag);
        }
        case 1: {
          return internalGetViewDataBinding1(component, view, localizedLayoutId, tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(5);
    result.add(new com.android.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new com.sunmnet.mediaroom.common.DataBinderMapperImpl());
    result.add(new com.sunmnet.mediaroom.device.DataBinderMapperImpl());
    result.add(new com.sunmnet.mediaroom.matrix.DataBinderMapperImpl());
    result.add(new com.sunmnet.mediaroom.wirelessprojection.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(46);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "adapter");
      sKeys.put(2, "allDayTemp");
      sKeys.put(3, "component");
      sKeys.put(4, "constrollSceneAdapter");
      sKeys.put(5, "course");
      sKeys.put(6, "dev");
      sKeys.put(7, "device");
      sKeys.put(8, "deviceAdapter");
      sKeys.put(9, "devive");
      sKeys.put(10, "dns1");
      sKeys.put(11, "dns2");
      sKeys.put(12, "doorInfo");
      sKeys.put(13, "envirment");
      sKeys.put(14, "functionAdapter");
      sKeys.put(15, "gateway");
      sKeys.put(16, "highTemp");
      sKeys.put(17, "holderAdapter");
      sKeys.put(18, "inputAdapter");
      sKeys.put(19, "inputGroupAdapter");
      sKeys.put(20, "inter");
      sKeys.put(21, "item");
      sKeys.put(22, "localIP");
      sKeys.put(23, "lowTemp");
      sKeys.put(24, "menu");
      sKeys.put(25, "mode");
      sKeys.put(26, "modeAdapter");
      sKeys.put(27, "netmask");
      sKeys.put(28, "network");
      sKeys.put(29, "networkName");
      sKeys.put(30, "outputAdapter");
      sKeys.put(31, "outputGroupAdapter");
      sKeys.put(32, "platformIP");
      sKeys.put(33, "platformPort");
      sKeys.put(34, "scene");
      sKeys.put(35, "setting");
      sKeys.put(36, "settingAdapter");
      sKeys.put(37, "sourceAdapter");
      sKeys.put(38, "state");
      sKeys.put(39, "temp");
      sKeys.put(40, "tempPath");
      sKeys.put(41, "titleName");
      sKeys.put(42, "type");
      sKeys.put(43, "value");
      sKeys.put(44, "vgaAdapter");
      sKeys.put(45, "vgaItem");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(107);

    static {
      sKeys.put("layout/dialog_fault_description_0", com.sunmnet.mediaroom.tabsp.R.layout.dialog_fault_description);
      sKeys.put("layout/dialog_select_main_out_screen_0", com.sunmnet.mediaroom.tabsp.R.layout.dialog_select_main_out_screen);
      sKeys.put("layout-land/tabsp_airconditioner_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_airconditioner_layout);
      sKeys.put("layout/tabsp_airconditioner_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_airconditioner_layout);
      sKeys.put("layout-land/tabsp_custom_device_detail_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_detail_layout);
      sKeys.put("layout/tabsp_custom_device_detail_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_detail_layout);
      sKeys.put("layout-land/tabsp_custom_device_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_item_layout);
      sKeys.put("layout/tabsp_custom_device_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_item_layout);
      sKeys.put("layout/tabsp_custom_device_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_layout);
      sKeys.put("layout-land/tabsp_custom_device_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_custom_device_layout);
      sKeys.put("layout/tabsp_default_door_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_default_door_item_layout);
      sKeys.put("layout-land/tabsp_default_door_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_default_door_item_layout);
      sKeys.put("layout-land/tabsp_device_common_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_common_layout);
      sKeys.put("layout/tabsp_device_common_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_common_layout);
      sKeys.put("layout-land/tabsp_device_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_item_layout);
      sKeys.put("layout/tabsp_device_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_item_layout);
      sKeys.put("layout-land/tabsp_device_listviewitem_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_listviewitem_layout);
      sKeys.put("layout/tabsp_device_listviewitem_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_device_listviewitem_layout);
      sKeys.put("layout-land/tabsp_discussion_input_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_discussion_input_layout);
      sKeys.put("layout/tabsp_discussion_input_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_discussion_input_layout);
      sKeys.put("layout-land/tabsp_discusstion_group_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_discusstion_group_item);
      sKeys.put("layout/tabsp_discusstion_group_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_discusstion_group_item);
      sKeys.put("layout-land/tabsp_door_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_door_item_layout);
      sKeys.put("layout/tabsp_door_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_door_item_layout);
      sKeys.put("layout/tabsp_freshair_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_freshair_layout);
      sKeys.put("layout-land/tabsp_freshair_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_freshair_layout);
      sKeys.put("layout/tabsp_interactive_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_interactive_layout);
      sKeys.put("layout-land/tabsp_interactive_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_interactive_layout);
      sKeys.put("layout-land/tabsp_lecture_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_lecture_item_layout);
      sKeys.put("layout/tabsp_lecture_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_lecture_item_layout);
      sKeys.put("layout-land/tabsp_light_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_light_item_layout);
      sKeys.put("layout/tabsp_light_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_light_item_layout);
      sKeys.put("layout-land/tabsp_matrix_interface_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_matrix_interface_item_layout);
      sKeys.put("layout/tabsp_matrix_interface_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_matrix_interface_item_layout);
      sKeys.put("layout/tabsp_matrix_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_matrix_layout);
      sKeys.put("layout-land/tabsp_matrix_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_matrix_layout);
      sKeys.put("layout-land/tabsp_menu_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_menu_item_layout);
      sKeys.put("layout/tabsp_menu_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_menu_item_layout);
      sKeys.put("layout/tabsp_menu_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_menu_layout);
      sKeys.put("layout-land/tabsp_menu_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_menu_layout);
      sKeys.put("layout-land/tabsp_mode_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_mode_item_layout);
      sKeys.put("layout/tabsp_mode_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_mode_item_layout);
      sKeys.put("layout-land/tabsp_rate_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_rate_item);
      sKeys.put("layout/tabsp_rate_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_rate_item);
      sKeys.put("layout-land/tabsp_rollingtext_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_rollingtext_item);
      sKeys.put("layout/tabsp_rollingtext_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_rollingtext_item);
      sKeys.put("layout/tabsp_setting_ethernet_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_setting_ethernet_layout);
      sKeys.put("layout-land/tabsp_setting_ethernet_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_setting_ethernet_layout);
      sKeys.put("layout-land/tabsp_setting_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_setting_layout);
      sKeys.put("layout/tabsp_setting_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_setting_layout);
      sKeys.put("layout/tabsp_setting_quit_app_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_setting_quit_app_layout);
      sKeys.put("layout-land/tabsp_settingmenu_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_settingmenu_item);
      sKeys.put("layout/tabsp_settingmenu_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_settingmenu_item);
      sKeys.put("layout-land/tabsp_version1_course_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_course_layout);
      sKeys.put("layout/tabsp_version1_course_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_course_layout);
      sKeys.put("layout/tabsp_version1_envirment_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_envirment_layout);
      sKeys.put("layout-land/tabsp_version1_envirment_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_envirment_layout);
      sKeys.put("layout/tabsp_version1_mainpage_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_mainpage_layout);
      sKeys.put("layout-land/tabsp_version1_mainpage_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_mainpage_layout);
      sKeys.put("layout-land/tabsp_version1_matrix_scene_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_matrix_scene_layout);
      sKeys.put("layout/tabsp_version1_matrix_scene_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_matrix_scene_layout);
      sKeys.put("layout/tabsp_version1_media_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_media_item);
      sKeys.put("layout-land/tabsp_version1_media_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_media_item);
      sKeys.put("layout/tabsp_version1_media_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_media_layout);
      sKeys.put("layout-land/tabsp_version1_media_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_media_layout);
      sKeys.put("layout/tabsp_version1_mediar_vga_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_mediar_vga_item);
      sKeys.put("layout-land/tabsp_version1_mediar_vga_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_mediar_vga_item);
      sKeys.put("layout/tabsp_version1_scene_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_scene_item_layout);
      sKeys.put("layout-land/tabsp_version1_scene_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_scene_item_layout);
      sKeys.put("layout/tabsp_version1_scene_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_scene_layout);
      sKeys.put("layout-land/tabsp_version1_scene_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version1_scene_layout);
      sKeys.put("layout-land/tabsp_version2_custom_discuss_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_custom_discuss_item);
      sKeys.put("layout/tabsp_version2_custom_discuss_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_custom_discuss_item);
      sKeys.put("layout/tabsp_version2_discussion_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_discussion_layout);
      sKeys.put("layout-land/tabsp_version2_discussion_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_discussion_layout);
      sKeys.put("layout/tabsp_version2_display_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_display_item);
      sKeys.put("layout-land/tabsp_version2_display_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_display_item);
      sKeys.put("layout-land/tabsp_version2_display_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_display_layout);
      sKeys.put("layout/tabsp_version2_display_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_display_layout);
      sKeys.put("layout-land/tabsp_version2_lecture_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_lecture_layout);
      sKeys.put("layout/tabsp_version2_lecture_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version2_lecture_layout);
      sKeys.put("layout/tabsp_version3_airconditioner_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_airconditioner_item_layout);
      sKeys.put("layout-land/tabsp_version3_airconditioner_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_airconditioner_item_layout);
      sKeys.put("layout-land/tabsp_version3_device_controll_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_device_controll_item_layout);
      sKeys.put("layout/tabsp_version3_device_controll_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_device_controll_item_layout);
      sKeys.put("layout/tabsp_version3_devicype_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_devicype_item_layout);
      sKeys.put("layout-land/tabsp_version3_devicype_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_devicype_item_layout);
      sKeys.put("layout-land/tabsp_version3_freshair_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_freshair_item_layout);
      sKeys.put("layout/tabsp_version3_freshair_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_freshair_item_layout);
      sKeys.put("layout-land/tabsp_version3_interactive_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_interactive_item_layout);
      sKeys.put("layout/tabsp_version3_interactive_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_interactive_item_layout);
      sKeys.put("layout/tabsp_version3_main_content_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_main_content_layout);
      sKeys.put("layout-land/tabsp_version3_main_content_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_main_content_layout);
      sKeys.put("layout-land/tabsp_version3_main_matrix_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_main_matrix_layout);
      sKeys.put("layout/tabsp_version3_main_matrix_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_main_matrix_layout);
      sKeys.put("layout-land/tabsp_version3_matrix_for_free_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_matrix_for_free);
      sKeys.put("layout/tabsp_version3_matrix_for_free_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_matrix_for_free);
      sKeys.put("layout/tabsp_version3_matrix_scene_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_matrix_scene_layout);
      sKeys.put("layout-land/tabsp_version3_matrix_scene_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_matrix_scene_layout);
      sKeys.put("layout-land/tabsp_version3_scene_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_scene_item);
      sKeys.put("layout/tabsp_version3_scene_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_version3_scene_item);
      sKeys.put("layout-land/tabsp_wireless_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_item_layout);
      sKeys.put("layout/tabsp_wireless_item_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_item_layout);
      sKeys.put("layout/tabsp_wireless_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_layout);
      sKeys.put("layout-land/tabsp_wireless_layout_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_layout);
      sKeys.put("layout/tabsp_wireless_selection_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_selection_item);
      sKeys.put("layout-land/tabsp_wireless_selection_item_0", com.sunmnet.mediaroom.tabsp.R.layout.tabsp_wireless_selection_item);
    }
  }
}
