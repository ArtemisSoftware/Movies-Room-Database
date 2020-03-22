package com.artemisSoftware.movieroomdb.db;

public class Film {

    private String title;
    private String directorName;
    private int year;

    public Film(String title, String directorName, int year) {
        this.title = title;
        this.directorName = directorName;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getDirectorName() {
        return directorName;
    }

    public int getYear() {
        return year;
    }
}
