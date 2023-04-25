package com.sunmnet.mediaroom.brand.enums;

public enum AttendanceType {
    NO_DATA("", "无"),
    NORMAL("", "正常"),
    LATE("", "迟到"),
    ASK_FOR_LEAVE("", "请假"),
    CUT_SCHOOL("", "旷课"),
    LEAVE_EARLY("", "早退");

    private String type;
    private String typeName;

    AttendanceType(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public static AttendanceType getAttendanceType(String type) {
        for (AttendanceType attendanceType : AttendanceType.values()) {
            if (attendanceType.getType().equals(type)) {
                return attendanceType;
            }
        }
        return null;
    }
}
