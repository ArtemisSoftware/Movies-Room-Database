package com.artemisSoftware.movieroomdb.director;

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

import java.util.List;

public class DirectorsListAdapter extends RecyclerView.Adapter<DirectorsListAdapter.DirectorsViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Director> directorList;
    private Context context;

    public DirectorsListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setDirectorList(List<Director> directorList) {
        this.directorList = directorList;
        notifyDataSetChanged();
    }

    @Override
    public DirectorsListAdapter.DirectorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemView = layoutInflater.inflate(R.layout.item_list_director, parent, false);
        return new DirectorsListAdapter.DirectorsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DirectorsListAdapter.DirectorsViewHolder holder, int position) {


        if (directorList == null) {
            return;
        }

        final Director director = directorList.get(position);
        if (director != null) {
            holder.directorText.setText(director.fullName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DialogFragment dialogFragment = DirectorSaveDialogFragment.newInstance(director.fullName);
                    dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), DirectorSaveDialogFragment.TAG_DIALOG_DIRECTOR_SAVE);
                }
            });
        }


    }

    @Override
    public int getItemCount() {

        if (directorList == null) {
            return 0;
        }
        else {
            return directorList.size();
        }
    }


    static class DirectorsViewHolder extends RecyclerView.ViewHolder {

        private TextView directorText;

        public DirectorsViewHolder(View itemView) {
            super(itemView);
            directorText = itemView.findViewById(R.id.tvDirector);
        }
    }
}