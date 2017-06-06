package com.zzcar.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zzcar.zzc.models.MyEaseUser;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MY_EASE_USER".
*/
public class MyEaseUserDao extends AbstractDao<MyEaseUser, String> {

    public static final String TABLENAME = "MY_EASE_USER";

    /**
     * Properties of entity MyEaseUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Avatar = new Property(1, String.class, "avatar", false, "AVATAR");
        public final static Property Nick = new Property(2, String.class, "nick", false, "NICK");
    }


    public MyEaseUserDao(DaoConfig config) {
        super(config);
    }
    
    public MyEaseUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MY_EASE_USER\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"AVATAR\" TEXT," + // 1: avatar
                "\"NICK\" TEXT);"); // 2: nick
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MY_EASE_USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MyEaseUser entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(2, avatar);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(3, nick);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MyEaseUser entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(2, avatar);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(3, nick);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public MyEaseUser readEntity(Cursor cursor, int offset) {
        MyEaseUser entity = new MyEaseUser( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // avatar
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // nick
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MyEaseUser entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAvatar(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNick(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final String updateKeyAfterInsert(MyEaseUser entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(MyEaseUser entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MyEaseUser entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
