package com.viostaticapp.present.homePresent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viostaticapp.R;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.present._common.BaseOnClickedEvent;
import com.viostaticapp.present._common.VideoItemClickedEvent;
import com.viostaticapp.view.YoutubePlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeLatestAdapter extends RecyclerView.Adapter<HomeLatestAdapter.CustomViewHolder> {

    private Context context;
    private List<YoutubeVideo> videoList;
    private VideoItemClickedEvent videoItemClickedEvent;


    public HomeLatestAdapter(Context context, List<YoutubeVideo> videoList, VideoItemClickedEvent videoItemClickedEvent) {
        this.context = context;
        this.videoList = videoList;
        this.videoItemClickedEvent = videoItemClickedEvent;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_home_row_item, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLatestAdapter.CustomViewHolder holder, int position) {

        String url = videoList.get(position).getThumbnail();
        Picasso.get().load(url).fit().centerCrop(2).placeholder(R.drawable.img_not_found).into(holder.imageView);

        holder.textView.setText(videoList.get(position).getTitle());
        holder.textView.setSelected(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoItemClickedEvent.onClicked(videoList.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
            textView = itemView.findViewById(R.id.text_video_name);
        }
    }

}
