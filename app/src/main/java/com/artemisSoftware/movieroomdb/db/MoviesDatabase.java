package com.artemisSoftware.movieroomdb.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//--@Database(entities = {Movie.class, Director.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase INSTANCE;
    private static final String DB_NAME = "movies.db";

    private static final String TAG = "MoviesDatabase";

    public static MoviesDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {

            synchronized (MoviesDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    Log.d(TAG, "populating with data...");
                                    //--new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }

        return INSTANCE;
    }


    /*
    public abstract MovieDao movieDao();

    public abstract DirectorDao directorDao();
    */

    public void clearDb() {
        if (INSTANCE != null) {
            //--new PopulateDbAsync(INSTANCE).execute();
        }
    }


}