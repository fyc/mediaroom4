package com.sunmnet.mediaroom.tabsp.record;


import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordType;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractPlayer;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPlayer;
import com.sunmnet.mediaroom.tabsp.record.impl.Inxedu.InxeduRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.ava.AvaRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.dayang.DayangRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.hbe.HbeRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.HikPlayer;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.HikRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.reach.ReachRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.simple.SimpleRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.sunmnet.SunmnetRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.vanlon.VanlonRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.xiwoo.XiwooRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.zixu.ZixuRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.zonekey.ZonekeyRecord;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecord;


public class RecordMaker {
    static AbstractPlayer recordPlayer;
    static IRecord record;
    static RecordEntity recordEntity;

    public static AbstractPlayer getRecordPlayer() throws Exception {
        if (recordPlayer == null) throw new Exception("尚未初始化播放器");
        return recordPlayer;
    }

    public static IRecord getRecord() throws Exception {
        if (record == null) throw new Exception("尚未初始化录播平台数据");
        return record;
    }

    public static void initRecord(RecordEntity entity) {
        if (recordEntity != null) {
            //缓存中的录播实体类与初始化的实体类的类型不一致，则重置之前的
            if (recordEntity.getRecordType() != entity.getRecordType() || !recordEntity.getHost().equals(entity.getHost())) {
                if (recordPlayer != null) {
                    recordPlayer.stop();
                    recordPlayer.release();
                }
                if (record != null) {
                    record.release();
                }
                recordEntity = entity;
            } else return;
        } else recordEntity = entity;

        recordPlayer = new IjkPlayer();
        RecordType recordType = recordEntity.getRecordType();
        switch (recordType) {
            case HIK:
                recordPlayer = new HikPlayer();
                record = new HikRecord(entity);
                break;
            case REACH:
                record = new ReachRecord(entity);
                break;
            case Zonekey:
                record = new ZonekeyRecord(entity);
                break;
            case Zixu:
                record = new ZixuRecord(entity);
                break;
            case Dayang:
                record = new DayangRecord(entity);
                break;
            case AVA:
                record = new AvaRecord(entity);
                break;
            case VANLON:
                record = new VanlonRecord(entity);
                break;
            case HBE:
                record = new HbeRecord(entity);
                break;
            case XIWOO:
                record = new XiwooRecord(entity);
                break;
            case Inxedu:
                record = new InxeduRecord(entity);
                break;
            case Simple:
                record = new SimpleRecord(entity);
                break;
            default:
                recordPlayer=new IjkPlayer();
                record = new SunmnetRecord(entity);
                break;
        }
    }

    public static void initRecord(int type, RecordEntity entity) {
        if (recordEntity != null) {
            //缓存中的录播实体类与初始化的实体类的类型不一致，则重置之前的
            if (recordEntity.getRecordType() != entity.getRecordType() || !recordEntity.getHost().equals(entity.getHost())) {
                if (recordPlayer != null) {
                    recordPlayer.stop();
                    recordPlayer.release();
                }
                if (record != null) {
                    record.release();
                }
                recordEntity = entity;
            } else return;
        }
        recordPlayer = new IjkPlayer();
        switch (type) {
            case 2:
                recordPlayer = new HikPlayer();
                entity.setRecordType(RecordType.HIK);
                record = new HikRecord(entity);
                break;
            case 3:
                entity.setRecordType(RecordType.REACH);
                record = new ReachRecord(entity);
                break;
            case 4:
                entity.setRecordType(RecordType.Zonekey);
                record = new ZonekeyRecord(entity);
                break;
            case 5:
                entity.setRecordType(RecordType.Zixu);
                record = new ZixuRecord(entity);
                break;
            case 6:
                entity.setRecordType(RecordType.Dayang);
                record = new DayangRecord(entity);
                break;
            case 7:
                entity.setRecordType(RecordType.AVA);
                record = new AvaRecord(entity);
                break;
            case 8:
                entity.setRecordType(RecordType.VANLON);
                record = new VanlonRecord(entity);
                break;
            case 9:
                entity.setRecordType(RecordType.HBE);
                record = new HbeRecord(entity);
                break;
            case 10:
                entity.setRecordType(RecordType.XIWOO);
                record = new XiwooRecord(entity);
                break;
            case 11:
                entity.setRecordType(RecordType.Inxedu);
                record = new InxeduRecord(entity);
                break;
            case 12:
                entity.setRecordType(RecordType.Simple);
                record = new SimpleRecord(entity);
                break;
            default:
                entity.setRecordType(RecordType.DEFAULT);
                record = new SunmnetRecord(entity);
                break;
        }

    }
}
