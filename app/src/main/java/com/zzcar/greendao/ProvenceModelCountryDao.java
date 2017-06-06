package com.zzcar.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zzcar.zzc.models.ProvenceModelCountry;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PROVENCE_MODEL_COUNTRY".
*/
public class ProvenceModelCountryDao extends AbstractDao<ProvenceModelCountry, Void> {

    public static final String TABLENAME = "PROVENCE_MODEL_COUNTRY";

    /**
     * Properties of entity ProvenceModelCountry.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, int.class, "id", false, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Full_name = new Property(2, String.class, "full_name", false, "FULL_NAME");
        public final static Property Region_name = new Property(3, String.class, "region_name", false, "REGION_NAME");
        public final static Property First_letter = new Property(4, String.class, "first_letter", false, "FIRST_LETTER");
    }


    public ProvenceModelCountryDao(DaoConfig config) {
        super(config);
    }
    
    public ProvenceModelCountryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROVENCE_MODEL_COUNTRY\" (" + //
                "\"ID\" INTEGER NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"FULL_NAME\" TEXT," + // 2: full_name
                "\"REGION_NAME\" TEXT," + // 3: region_name
                "\"FIRST_LETTER\" TEXT);"); // 4: first_letter
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROVENCE_MODEL_COUNTRY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ProvenceModelCountry entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String full_name = entity.getFull_name();
        if (full_name != null) {
            stmt.bindString(3, full_name);
        }
 
        String region_name = entity.getRegion_name();
        if (region_name != null) {
            stmt.bindString(4, region_name);
        }
 
        String first_letter = entity.getFirst_letter();
        if (first_letter != null) {
            stmt.bindString(5, first_letter);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ProvenceModelCountry entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String full_name = entity.getFull_name();
        if (full_name != null) {
            stmt.bindString(3, full_name);
        }
 
        String region_name = entity.getRegion_name();
        if (region_name != null) {
            stmt.bindString(4, region_name);
        }
 
        String first_letter = entity.getFirst_letter();
        if (first_letter != null) {
            stmt.bindString(5, first_letter);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public ProvenceModelCountry readEntity(Cursor cursor, int offset) {
        ProvenceModelCountry entity = new ProvenceModelCountry( //
            cursor.getInt(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // full_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // region_name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // first_letter
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ProvenceModelCountry entity, int offset) {
        entity.setId(cursor.getInt(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFull_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRegion_name(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFirst_letter(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(ProvenceModelCountry entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(ProvenceModelCountry entity) {
        return null;
    }

    @Override
    public boolean hasKey(ProvenceModelCountry entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
