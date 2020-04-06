package com.artemisSoftware.movieroomdb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
abstract public class MovieDao implements BaseDao<Movie> {

    @Query("SELECT * FROM movie WHERE title = :title LIMIT 1")
    abstract public Movie findMovieByTitle(String title);

    @Query("DELETE FROM movie")
    abstract public void deleteAll();

    @Query("SELECT title, full_name as directorName, year FROM movie as mov LEFT JOIN (SELECT full_name, did FROM director) as dir ON mov.directorId = dir.did ORDER BY title ASC\n")
    abstract public LiveData<List<Film>> getAllMovies();

}