package com.sunmnet.mediaroom.brand.data.database.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sunmnet.mediaroom.brand.bean.play.TemplateProgramInfo;
import com.sunmnet.mediaroom.brand.data.database.play.TemplateProgramInfoPropertyConverter;

import com.sunmnet.mediaroom.brand.data.database.play.TemplateProgramRuleDBEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEMPLATE_PROGRAM_RULE_DBENTITY".
*/
public class TemplateProgramRuleDBEntityDao extends AbstractDao<TemplateProgramRuleDBEntity, String> {

    public static final String TABLENAME = "TEMPLATE_PROGRAM_RULE_DBENTITY";

    /**
     * Properties of entity TemplateProgramRuleDBEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property TimeType = new Property(2, int.class, "timeType", false, "TIME_TYPE");
        public final static Property Date = new Property(3, String.class, "date", false, "DATE");
        public final static Property Time = new Property(4, String.class, "time", false, "TIME");
        public final static Property Week = new Property(5, String.class, "week", false, "WEEK");
        public final static Property Type = new Property(6, int.class, "type", false, "TYPE");
        public final static Property TemplateType = new Property(7, int.class, "templateType", false, "TEMPLATE_TYPE");
        public final static Property PlayType = new Property(8, int.class, "playType", false, "PLAY_TYPE");
        public final static Property PlayStatus = new Property(9, int.class, "playStatus", false, "PLAY_STATUS");
        public final static Property CleanOld = new Property(10, boolean.class, "cleanOld", false, "CLEAN_OLD");
        public final static Property Resource = new Property(11, String.class, "resource", false, "RESOURCE");
    }

    private final TemplateProgramInfoPropertyConverter resourceConverter = new TemplateProgramInfoPropertyConverter();

    public TemplateProgramRuleDBEntityDao(DaoConfig config) {
        super(config);
    }
    
    public TemplateProgramRuleDBEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEMPLATE_PROGRAM_RULE_DBENTITY\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TIME_TYPE\" INTEGER NOT NULL ," + // 2: timeType
                "\"DATE\" TEXT," + // 3: date
                "\"TIME\" TEXT," + // 4: time
                "\"WEEK\" TEXT," + // 5: week
                "\"TYPE\" INTEGER NOT NULL ," + // 6: type
                "\"TEMPLATE_TYPE\" INTEGER NOT NULL ," + // 7: templateType
                "\"PLAY_TYPE\" INTEGER NOT NULL ," + // 8: playType
                "\"PLAY_STATUS\" INTEGER NOT NULL ," + // 9: playStatus
                "\"CLEAN_OLD\" INTEGER NOT NULL ," + // 10: cleanOld
                "\"RESOURCE\" TEXT);"); // 11: resource
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEMPLATE_PROGRAM_RULE_DBENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TemplateProgramRuleDBEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getTimeType());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(5, time);
        }
 
        String week = entity.getWeek();
        if (week != null) {
            stmt.bindString(6, week);
        }
        stmt.bindLong(7, entity.getType());
        stmt.bindLong(8, entity.getTemplateType());
        stmt.bindLong(9, entity.getPlayType());
        stmt.bindLong(10, entity.getPlayStatus());
        stmt.bindLong(11, entity.getCleanOld() ? 1L: 0L);
 
        TemplateProgramInfo resource = entity.getResource();
        if (resource != null) {
            stmt.bindString(12, resourceConverter.convertToDatabaseValue(resource));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TemplateProgramRuleDBEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getTimeType());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(4, date);
        }
 
        String time = entity.getTime();
        if (time != null) {
            stmt.bindString(5, time);
        }
 
        String week = entity.getWeek();
        if (week != null) {
            stmt.bindString(6, week);
        }
        stmt.bindLong(7, entity.getType());
        stmt.bindLong(8, entity.getTemplateType());
        stmt.bindLong(9, entity.getPlayType());
        stmt.bindLong(10, entity.getPlayStatus());
        stmt.bindLong(11, entity.getCleanOld() ? 1L: 0L);
 
        TemplateProgramInfo resource = entity.getResource();
        if (resource != null) {
            stmt.bindString(12, resourceConverter.convertToDatabaseValue(resource));
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TemplateProgramRuleDBEntity readEntity(Cursor cursor, int offset) {
        TemplateProgramRuleDBEntity entity = new TemplateProgramRuleDBEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.getInt(offset + 2), // timeType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // date
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // time
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // week
            cursor.getInt(offset + 6), // type
            cursor.getInt(offset + 7), // templateType
            cursor.getInt(offset + 8), // playType
            cursor.getInt(offset + 9), // playStatus
            cursor.getShort(offset + 10) != 0, // cleanOld
            cursor.isNull(offset + 11) ? null : resourceConverter.convertToEntityProperty(cursor.getString(offset + 11)) // resource
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TemplateProgramRuleDBEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTimeType(cursor.getInt(offset + 2));
        entity.setDate(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setWeek(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setType(cursor.getInt(offset + 6));
        entity.setTemplateType(cursor.getInt(offset + 7));
        entity.setPlayType(cursor.getInt(offset + 8));
        entity.setPlayStatus(cursor.getInt(offset + 9));
        entity.setCleanOld(cursor.getShort(offset + 10) != 0);
        entity.setResource(cursor.isNull(offset + 11) ? null : resourceConverter.convertToEntityProperty(cursor.getString(offset + 11)));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TemplateProgramRuleDBEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(TemplateProgramRuleDBEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TemplateProgramRuleDBEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
