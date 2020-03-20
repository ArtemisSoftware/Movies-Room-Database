package com.artemisSoftware.movieroomdb.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.artemisSoftware.movieroomdb.util.DataBase;

@Database(entities = {Director.class, Movie.class}, version = DataBase.VERSION)
public abstract class MoviesDatabase extends RoomDatabase {


    private static final String TAG = "MoviesDatabase";

    private static MoviesDatabase INSTANCE;


    public static MoviesDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {

            synchronized (MoviesDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, DataBase.NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addMigrations(MIGRATION_1_2)
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    Log.d(TAG, "populating with data...");
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }

        return INSTANCE;
    }



    public abstract MovieDao movieDao();

    public abstract DirectorDao directorDao();




    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {


            String query = "CREATE TABLE IF NOT EXISTS `movie` (" +
                    "`mid` INTEGER NOT NULL, " +
                    "`title` TEXT NOT NULL, " +
                    "`directorId` INTEGER NOT NULL, " +
                    "PRIMARY KEY(`mid`))";

            database.execSQL(query);

            Log.d(TAG, "MIGRATION_1_2");
        }
    };



    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
    }


}
