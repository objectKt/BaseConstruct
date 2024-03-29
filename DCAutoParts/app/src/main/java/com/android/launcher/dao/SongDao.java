package com.android.launcher.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.android.launcher.entity.Song;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SONG".
*/
public class SongDao extends AbstractDao<Song, Long> {

    public static final String TABLENAME = "SONG";

    /**
     * Properties of entity Song.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Artist = new Property(1, String.class, "artist", false, "ARTIST");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Image = new Property(3, byte[].class, "image", false, "IMAGE");
        public final static Property Path = new Property(4, String.class, "path", false, "PATH");
        public final static Property Album = new Property(5, String.class, "album", false, "ALBUM");
        public final static Property Duration = new Property(6, int.class, "duration", false, "DURATION");
        public final static Property Size = new Property(7, long.class, "size", false, "SIZE");
    }


    public SongDao(DaoConfig config) {
        super(config);
    }
    
    public SongDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SONG\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ARTIST\" TEXT," + // 1: artist
                "\"TITLE\" TEXT," + // 2: title
                "\"IMAGE\" BLOB," + // 3: image
                "\"PATH\" TEXT," + // 4: path
                "\"ALBUM\" TEXT," + // 5: album
                "\"DURATION\" INTEGER NOT NULL ," + // 6: duration
                "\"SIZE\" INTEGER NOT NULL );"); // 7: size
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SONG\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Song entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String artist = entity.getArtist();
        if (artist != null) {
            stmt.bindString(2, artist);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        byte[] image = entity.getImage();
        if (image != null) {
            stmt.bindBlob(4, image);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(5, path);
        }
 
        String album = entity.getAlbum();
        if (album != null) {
            stmt.bindString(6, album);
        }
        stmt.bindLong(7, entity.getDuration());
        stmt.bindLong(8, entity.getSize());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Song entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String artist = entity.getArtist();
        if (artist != null) {
            stmt.bindString(2, artist);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        byte[] image = entity.getImage();
        if (image != null) {
            stmt.bindBlob(4, image);
        }
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(5, path);
        }
 
        String album = entity.getAlbum();
        if (album != null) {
            stmt.bindString(6, album);
        }
        stmt.bindLong(7, entity.getDuration());
        stmt.bindLong(8, entity.getSize());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Song readEntity(Cursor cursor, int offset) {
        Song entity = new Song( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // artist
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getBlob(offset + 3), // image
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // path
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // album
            cursor.getInt(offset + 6), // duration
            cursor.getLong(offset + 7) // size
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Song entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setArtist(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImage(cursor.isNull(offset + 3) ? null : cursor.getBlob(offset + 3));
        entity.setPath(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAlbum(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDuration(cursor.getInt(offset + 6));
        entity.setSize(cursor.getLong(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Song entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Song entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Song entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
