package com.artemisSoftware.movieroomdb.db;

import android.os.AsyncTask;

public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {


    private final MovieDao movieDao;
    private final DirectorDao directorDao;
    MoviesDatabase instance;


    public PopulateDbAsync(MoviesDatabase instance) {

        movieDao = instance.movieDao();
        directorDao = instance.directorDao();
        this.instance = instance;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        movieDao.deleteAll();
        directorDao.deleteAll();


        Director director = new Director("Quentin Tarantino");
        directorDao.insert(director);

        director = new Director("Ridley Scott");
        directorDao.insert(director);

        Director directorOne = new Director("Adam McKay");
        Director directorTwo = new Director("Denis Villeneuve");
        Director directorThree = new Director("Morten Tyldum");


        Movie movieOne = new Movie("The Big Short", (int) directorDao.insert(directorOne), 1978);
        final int dIdTwo = (int) directorDao.insert(directorTwo);
        Movie movieTwo = new Movie("Arrival", dIdTwo);
        Movie movieThree = new Movie("Blade Runner 2049", dIdTwo);
        Movie movieFour = new Movie("Imitation", (int) directorDao.insert(directorThree));

        movieDao.insert(movieFour);
        movieDao.insert(movieOne, movieTwo, movieThree);

        Movie movieUpdate = movieDao.findMovieByTitle("Imitation");
        movieUpdate.title = "Imitation Game";

        movieDao.update(movieUpdate);



        this.instance.transactionDb();

        return null;
    }
}
