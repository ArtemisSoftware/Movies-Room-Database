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
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
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


    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {


            String query = "ALTER TABLE 'movie' ADD COLUMN 'year' INTEGER NOT NULL DEFAULT 0";

            database.execSQL(query);

            Log.d(TAG, "MIGRATION_2_3");
        }
    };


    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            String query = "CREATE UNIQUE INDEX index_full_name ON  director(full_name)";

            database.execSQL(query);

            Log.d(TAG, "MIGRATION_3_4");
        }
    };


    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.beginTransaction();

            try {

                String query = "ALTER TABLE movie RENAME TO tmp_movie";
                database.execSQL(query);

                query = "CREATE TABLE IF NOT EXISTS movie (" +
                        "mid INTEGER NOT NULL, " +
                        "title TEXT NOT NULL, " +
                        "directorId INTEGER NOT NULL, " +
                        "year INTEGER NOT NULL, " +
                        "PRIMARY KEY(mid), " +
                        "FOREIGN KEY (directorId) REFERENCES director (did) ON DELETE CASCADE " +
                        ")";

                database.execSQL(query);


                query = "CREATE INDEX index_movie_title ON  movie(title)";
                database.execSQL(query);

                query = "CREATE INDEX index_movie_directorId ON  movie(directorId)";
                database.execSQL(query);



                query = "INSERT INTO movie(title, directorId, year) " +
                        "SELECT title, directorId, year FROM tmp_movie ORDER BY mid";

                database.execSQL(query);

                query = "DROP TABLE tmp_movie";
                database.execSQL(query);

                database.setTransactionSuccessful();
                Log.d(TAG, "MIGRATION_4_5");
            }
            finally {
                database.endTransaction();
            }
        }
    };




    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
    }


}
