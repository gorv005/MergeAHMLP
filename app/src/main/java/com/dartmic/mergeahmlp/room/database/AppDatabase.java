package com.dartmic.mergeahmlp.room.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.dartmic.mergeahmlp.room.dao.MechDataDao;
import com.dartmic.mergeahmlp.room.entity.MechData;

@Database(entities = { MechData.class }, version = 5)
public abstract class  AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract MechDataDao getMechDataDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mech-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                           // .addMigrations(MIGRATION_3_4,MIGRATION_4_5)
                           // .fallbackToDestructiveMigration()   // will do empty table
                            .build();
        }
        return INSTANCE;
    }

   /* static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE mechdata "
                    + " ADD COLUMN total_point TEXT");
        }
    };
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE mechdata "
                    + " ADD COLUMN total_point TEXT");
        }
    };*/
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
