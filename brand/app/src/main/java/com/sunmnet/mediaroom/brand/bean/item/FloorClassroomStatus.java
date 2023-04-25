package com.sunmnet.mediaroom.brand.bean.item;

import java.util.List;

public class FloorClassroomStatus {

    String floorName;

    List<ClassroomStatus> statusList;

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public List<ClassroomStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<ClassroomStatus> statusList) {
        this.statusList = statusList;
    }
}
