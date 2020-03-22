package com.artemisSoftware.movieroomdb.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "movie",
        foreignKeys = @ForeignKey(entity = Director.class,
                parentColumns = "did",
                childColumns = "directorId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("title"), @Index("directorId")})
public class Movie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid")
    public int id;

    @ColumnInfo(name = "title")
    @NonNull
    public String title;

    @ColumnInfo(name = "directorId")
    public int directorId;

    @ColumnInfo(name = "year")
    public int year;


    public Movie(@NonNull String title, int directorId) {
        this.title = title;
        this.directorId = directorId;
    }

    @Ignore
    public Movie(@NonNull String title, int directorId, int year) {
        this.title = title;
        this.directorId = directorId;
        this.year = year;
    }

}
