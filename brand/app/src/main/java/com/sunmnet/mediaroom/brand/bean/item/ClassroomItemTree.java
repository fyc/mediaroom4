package com.sunmnet.mediaroom.brand.bean.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassroomItemTree {

    private List<CampusItem> campusList;
    private Map<String, List<BuildingItem>> buildings;
    private Map<String, List<FloorItem>> floors;
    private Map<String, List<ClassroomItem>> classrooms;

    public List<BuildingItem> getAllBuilding() {
        List<BuildingItem> list = new ArrayList<>();
        for (List<BuildingItem> items : buildings.values()) {
            list.addAll(items);
        }
        return list;
    }

    public List<FloorItem> getAllFloor() {
        List<FloorItem> list = new ArrayList<>();
        for (List<FloorItem> items : floors.values()) {
            list.addAll(items);
        }
        return list;
    }

    public List<ClassroomItem> getAllClassroom() {
        List<ClassroomItem> list = new ArrayList<>();
        for (List<ClassroomItem> items : classrooms.values()) {
            list.addAll(items);
        }
        return list;
    }

    public List<BuildingItem> getBuildingList(String campusCode) {
        return buildings.get(campusCode);
    }

    public List<ClassroomItem> getClassroomList(String floorCode) {
        return classrooms.get(floorCode);
    }

    public List<FloorItem> getFloorList(String buildingCode) {
        return floors.get(buildingCode);
    }


    public Map<String, List<BuildingItem>> getBuildings() {
        return buildings;
    }

    public void setBuildings(Map<String, List<BuildingItem>> buildings) {
        this.buildings = buildings;
    }

    public Map<String, List<FloorItem>> getFloors() {
        return floors;
    }

    public void setFloors(Map<String, List<FloorItem>> floors) {
        this.floors = floors;
    }

    public Map<String, List<ClassroomItem>> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Map<String, List<ClassroomItem>> classrooms) {
        this.classrooms = classrooms;
    }

    public List<CampusItem> getCampusList() {
        return campusList;
    }

    public void setCampusList(List<CampusItem> campusList) {
        this.campusList = campusList;
    }
}
