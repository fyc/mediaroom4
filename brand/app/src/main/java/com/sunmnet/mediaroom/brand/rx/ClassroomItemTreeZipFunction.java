package com.sunmnet.mediaroom.brand.rx;

import com.sunmnet.mediaroom.brand.bean.item.BuildingItem;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomItem;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomItemTree;
import com.sunmnet.mediaroom.brand.bean.item.FloorItem;
import com.sunmnet.mediaroom.brand.bean.response.BuildingResponse;
import com.sunmnet.mediaroom.brand.bean.response.ClassroomResponse;
import com.sunmnet.mediaroom.brand.bean.response.FloorResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Function3;

public class ClassroomItemTreeZipFunction implements Function3<BuildingResponse, FloorResponse, ClassroomResponse, ClassroomItemTree> {

    @Override
    public ClassroomItemTree apply(BuildingResponse buildingResponse, FloorResponse floorResponse, ClassroomResponse classroomResponse) throws Exception {
        if (buildingResponse.isSuccess() && floorResponse.isSuccess() && classroomResponse.isSuccess()) {
            ClassroomItemTree classroomItemTree = new ClassroomItemTree();
            Map<String, List<BuildingItem>> buildingMap = new HashMap<>();
            Map<String, List<FloorItem>> floorMap = new HashMap<>();
            Map<String, List<ClassroomItem>> classroomMap = new HashMap<>();
            classroomItemTree.setBuildings(buildingMap);
            classroomItemTree.setFloors(floorMap);
            classroomItemTree.setClassrooms(classroomMap);
            for (BuildingResponse.Result result : buildingResponse.getObj()) {
                List<BuildingItem> buildingItems = buildingMap.get(result.getCampusCode());
                if (buildingItems == null) {
                    buildingItems = new ArrayList<>();
                    buildingMap.put(result.getCampusCode(), buildingItems);
                }
                BuildingItem buildingItem = new BuildingItem();
                buildingItem.setCode(result.getBuildCode());
                buildingItem.setName(result.getBuildName());
                buildingItem.setCampusCode(result.getCampusCode());
                buildingItems.add(buildingItem);
            }

            for (FloorResponse.Result result : floorResponse.getObj()) {
                List<FloorItem> floorItemList = floorMap.get(result.getBuildCode());
                if (floorItemList == null) {
                    floorItemList = new ArrayList<>();
                    floorMap.put(result.getBuildCode(), floorItemList);
                }
                FloorItem floorItem = new FloorItem();
                floorItem.setName(result.getFloorName());
                floorItem.setCode(result.getFloorCode());
                floorItem.setCampusCode(result.getCampusCode());
                floorItem.setBuildingCode(result.getBuildCode());
                floorItemList.add(floorItem);
            }
            for (ClassroomResponse.Result result : classroomResponse.getObj()) {
                List<ClassroomItem> classroomItems = classroomMap.get(result.getFloorCode());
                if (classroomItems == null) {
                    classroomItems = new ArrayList<>();
                    classroomMap.put(result.getFloorCode(), classroomItems);
                }
                ClassroomItem classroomItem = new ClassroomItem();
                classroomItem.setName(result.getClassroomName());
                classroomItem.setCode(result.getClassroomCode());
                classroomItem.setCampusCode(result.getCampusCode());
                classroomItem.setBuildingCode(result.getBuildCode());
                classroomItem.setFloorCode(result.getFloorCode());
                classroomItems.add(classroomItem);
            }
            return classroomItemTree;
        } else {

            return null;
        }
    }
}
