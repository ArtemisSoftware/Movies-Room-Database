package com.artemisSoftware.movieroomdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.artemisSoftware.movieroomdb.db.MoviesDatabase;
import com.artemisSoftware.movieroomdb.director.DirectorSaveDialogFragment;
import com.artemisSoftware.movieroomdb.director.DirectorsListFragment;
import com.artemisSoftware.movieroomdb.movie.MovieSaveDialogFragment;
import com.artemisSoftware.movieroomdb.movie.MoviesListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private boolean MOVIES_SHOWN = true;
    private Fragment shownFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(getString(R.string.app_name));
        initView();

        if (savedInstanceState == null) {
            showFragment(MoviesListFragment.newInstance());
        }
    }

    public void setToolbar(@NonNull String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    private void initView() {

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_movies:
                        MOVIES_SHOWN = true;
                        showFragment(MoviesListFragment.newInstance());
                        return true;

                    case R.id.navigation_directors:
                        MOVIES_SHOWN = false;
                        showFragment(DirectorsListFragment.newInstance());
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaveDialog();
            }
        });
    }

    private void showFragment(final Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentHolder, fragment);
        fragmentTransaction.commitNow();
        shownFragment = fragment;
    }

    private void showSaveDialog() {

        DialogFragment dialogFragment;
        String tag;

        if (MOVIES_SHOWN) {
            dialogFragment = MovieSaveDialogFragment.newInstance(null, null, null);
            tag = MovieSaveDialogFragment.TAG_DIALOG_MOVIE_SAVE;
        }
        else {

            dialogFragment = DirectorSaveDialogFragment.newInstance(null);
            tag = DirectorSaveDialogFragment.TAG_DIALOG_DIRECTOR_SAVE;
        }

        dialogFragment.show(getSupportFragmentManager(), tag);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_delete_list_data) {
            deleteCurrentListData();
            return true;
        }
        else if (id == R.id.action_re_create_database) {
            reCreateDatabase();
            return true;
        }
        else if (id == R.id.action_author) {
            author();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void deleteCurrentListData() {
        if (MOVIES_SHOWN) {
            ((MoviesListFragment) shownFragment).removeData();
        } else {
            ((DirectorsListFragment) shownFragment).removeData();
        }
    }


    private void reCreateDatabase() {
        MoviesDatabase.getDatabase(this).clearDb();
    }


    private void author() {

        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.artemis_software_logo);
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

}