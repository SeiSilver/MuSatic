package com.example.seiaudioplayer.presenter.songPresenter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seiaudioplayer.R;
import com.example.seiaudioplayer.data.model.Music;
import com.example.seiaudioplayer.view.PlayerActivity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private ArrayList<Music> musicFiles;

    public MusicAdapter(Context context, ArrayList<Music> mFiles) {
        this.context = context;
        this.musicFiles = mFiles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_items, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.file_name.setText(musicFiles.get(position).getTitle());
        byte[] image = getAlbumArt(musicFiles.get(position).getPath());
        if (image != null) {
            Glide.with(context).asBitmap().load(image).into(holder.album_art);
        } else {
            Glide.with(context).asBitmap().load(R.drawable.aqua_neko).into(holder.album_art);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }


    private byte[] getAlbumArt(String path) {

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;

    }

}

