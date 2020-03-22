package com.artemisSoftware.movieroomdb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie WHERE title = :title LIMIT 1")
    Movie findMovieByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie... directors);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Movie director);

    @Query("DELETE FROM movie")
    void deleteAll();

    @Query("SELECT title, full_name as directorName, year FROM movie as mov LEFT JOIN (SELECT full_name, did FROM director) as dir ON mov.directorId = dir.did ORDER BY title ASC\n")
    LiveData<List<Film>> getAllMovies();
}
