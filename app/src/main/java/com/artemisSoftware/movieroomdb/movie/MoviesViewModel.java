package com.artemisSoftware.movieroomdb.movie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.artemisSoftware.movieroomdb.db.Film;
import com.artemisSoftware.movieroomdb.db.Movie;
import com.artemisSoftware.movieroomdb.db.MovieDao;
import com.artemisSoftware.movieroomdb.db.MoviesDatabase;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MovieDao movieDao;
    private LiveData<List<Film>> moviesLiveData;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MoviesDatabase.getDatabase(application).movieDao();
        moviesLiveData = movieDao.getAllMovies();
    }

    public LiveData<List<Film>> getMoviesList() {
        return moviesLiveData;
    }


    public void insert(Movie... movies) {
        movieDao.insert(movies);
    }

    public void update(Movie movie) {
        movieDao.update(movie);
    }

    public void deleteAll() {
        movieDao.deleteAll();
    }

}