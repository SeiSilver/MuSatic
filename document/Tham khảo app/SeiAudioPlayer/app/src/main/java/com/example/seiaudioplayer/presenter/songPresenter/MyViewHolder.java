package com.example.seiaudioplayer.presenter.songPresenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seiaudioplayer.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView file_name;
    ImageView album_art;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        file_name = itemView.findViewById(R.id.music_file_name);
        album_art = itemView.findViewById(R.id.music_img);
    }
}

