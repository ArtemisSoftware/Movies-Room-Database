package com.artemisSoftware.movieroomdb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
abstract public class DirectorDao implements BaseDao<Director>{

    @Query("SELECT * FROM director WHERE did = :id LIMIT 1")
    abstract public Director findDirectorById(int id);

    @Query("SELECT * FROM director WHERE full_name = :fullName LIMIT 1")
    abstract public Director findDirectorByName(String fullName);


    @Query("DELETE FROM director")
    abstract public void deleteAll();

    @Query("SELECT * FROM director ORDER BY full_name ASC")
    abstract public LiveData<List<Director>> getAllDirectors();
}
