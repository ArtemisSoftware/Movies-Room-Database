package com.artemisSoftware.movieroomdb.movie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemisSoftware.movieroomdb.R;
import com.artemisSoftware.movieroomdb.db.Film;
import com.artemisSoftware.movieroomdb.db.Movie;

import java.util.List;


public class MoviesListFragment extends Fragment {


    private MoviesListAdapter moviesListAdapter;
    private MoviesViewModel moviesViewModel;

    private Context context;


    public static MoviesListFragment newInstance() {
        return new MoviesListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        moviesListAdapter = new MoviesListAdapter(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_movies);
        recyclerView.setAdapter(moviesListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }


    private void initData() {

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        moviesViewModel.getMoviesList().observe(this, new Observer<List<Film>>() {
            @Override
            public void onChanged(@Nullable List<Film> movies) {
                moviesListAdapter.setMovieList(movies);
            }
        });
    }


    public void removeData() {

        if (moviesListAdapter != null) {
            moviesViewModel.deleteAll();
        }
    }

}
