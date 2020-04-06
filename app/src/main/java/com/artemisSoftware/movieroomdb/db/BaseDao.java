package com.artemisSoftware.movieroomdb.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(T entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(T... entity);



    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(T entity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(T... entity);


    @Delete
    void delete_(T entity);
}
