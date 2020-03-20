package com.artemisSoftware.movieroomdb.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.artemisSoftware.movieroomdb.R;
import com.artemisSoftware.movieroomdb.db.Director;
import com.artemisSoftware.movieroomdb.db.Movie;
import com.artemisSoftware.movieroomdb.db.MoviesDatabase;

import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Movie> movieList;
    private Context context;

    public MoviesListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemView = layoutInflater.inflate(R.layout.item_list_movie, parent, false);
        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

        if (movieList == null) {
            return;
        }


        final Movie movie = movieList.get(position);
        if (movie != null) {
            holder.titleText.setText(movie.title);


            final Director director = MoviesDatabase.getDatabase(context).directorDao().findDirectorById(movie.directorId);

            final String directorFullName;

            if (director != null) {
                holder.directorText.setText(director.fullName);
                directorFullName = director.fullName;
            }
            else {
                directorFullName = "";
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String year = "";

                    if(movie.year != 0){
                        year = movie.year + "";
                    }

                    DialogFragment dialogFragment = MovieSaveDialogFragment.newInstance(movie.title, directorFullName, year);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), MovieSaveDialogFragment.TAG_DIALOG_MOVIE_SAVE);
                }
            });

        }
    }

    @Override
    public int getItemCount() {

        if (movieList == null) {
            return 0;
        } else {
            return movieList.size();
        }
    }


    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView directorText;

        public MoviesViewHolder(View itemView) {
            super(itemView);


            titleText = itemView.findViewById(R.id.tvMovieTitle);
            directorText = itemView.findViewById(R.id.tvMovieDirectorFullName);

        }
    }
}