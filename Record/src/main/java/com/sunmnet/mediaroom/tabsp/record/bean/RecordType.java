package com.sunmnet.mediaroom.tabsp.record.bean;

public enum RecordType {
    /**自研录播*/
    DEFAULT(1),
    /**海康录播*/
    HIK(2),
    /**锐取录播*/
    REACH(3),
    /**中庆录播*/
    Zonekey(4),
    /**紫旭录播*/
    Zixu(5),
    /**大洋录播*/
    Dayang(6),
    /**奥威亚录播*/
    AVA(7),
    /**凡龙录播*/
    VANLON(8),
    /**翰博尔录播*/
    HBE(9),
    XIWOO(10),
    /**
     * 希沃
     * */
    Inxedu(11),
    /**
     * 简易录播
     */
    Simple(12),
    ;
    RecordType(int val){

    }
    int value;
    public int getValue(){
        return this.value;
    }
}
