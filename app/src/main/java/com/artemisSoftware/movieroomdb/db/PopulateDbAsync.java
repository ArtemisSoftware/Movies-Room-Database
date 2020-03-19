package com.artemisSoftware.movieroomdb.db;

import android.os.AsyncTask;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    /*
    private final MovieDao movieDao;
    private final DirectorDao directorDao;
    */

    public PopulateDbAsync(MoviesDatabase instance) {
        /*
        movieDao = instance.movieDao();
        directorDao = instance.directorDao();
        */
    }

    @Override
    protected Void doInBackground(Void... voids) {
        /*
        movieDao.deleteAll();
        directorDao.deleteAll();
        */
        Director directorOne = new Director("Adam McKay");
        Director directorTwo = new Director("Denis Villeneuve");
        Director directorThree = new Director("Morten Tyldum");

        /*
        Movie movieOne = new Movie("The Big Short", (int) directorDao.insert(directorOne));
        final int dIdTwo = (int) directorDao.insert(directorTwo);
        Movie movieTwo = new Movie("Arrival", dIdTwo);
        Movie movieThree = new Movie("Blade Runner 2049", dIdTwo);
        Movie movieFour = new Movie("Passengers", (int) directorDao.insert(directorThree));

        movieDao.insert(movieOne, movieTwo, movieThree, movieFour);
        */
        return null;
    }
}
