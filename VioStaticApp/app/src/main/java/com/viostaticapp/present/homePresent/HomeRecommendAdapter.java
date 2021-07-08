package com.viostaticapp.present.homePresent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.viostaticapp.R;
import com.viostaticapp.data.model.YoutubeVideo;
import com.viostaticapp.present._common.BaseOnClickedEvent;
import com.viostaticapp.present._common.VideoItemClickedEvent;
import com.viostaticapp.service.LibraryItemMenuOption;
import com.viostaticapp.view.YoutubePlayerActivity;

import java.util.List;

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.CustomViewHolder> {

    private Context context;
    private List<YoutubeVideo> videoList;
    private VideoItemClickedEvent videoItemClickedEvent;


    public HomeRecommendAdapter(Context context, List<YoutubeVideo> videoList, VideoItemClickedEvent videoItemClickedEvent) {
        this.context = context;
        this.videoList = videoList;
        this.videoItemClickedEvent = videoItemClickedEvent;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_video_item, parent, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecommendAdapter.CustomViewHolder holder, int position) {

//        holder.rec_image_item.setImageResource(recommendVideoList.get(position).getImage());
        String url = videoList.get(position).getThumbnail();
        Picasso.get().load(url).fit().centerCrop(2).placeholder(R.drawable.img_not_found).into(holder.rec_image_item);

        holder.rec_video_name.setText(videoList.get(position).getTitle());
        holder.date_publish_text.setText(videoList.get(position).getPublishedAt());
//        holder.date_publish_text.setSelected(true);

        holder.popupMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryItemMenuOption.showPopupMenu(v, videoList.get(position), context);
            }
        });

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

        ImageView rec_image_item, popupMenuButton;
        TextView rec_video_name, date_publish_text;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            rec_image_item = itemView.findViewById(R.id.rec_image_item);
            popupMenuButton = itemView.findViewById(R.id.popupMenuButton);

            rec_video_name = itemView.findViewById(R.id.rec_video_name);
            date_publish_text = itemView.findViewById(R.id.date_publish_text);
        }
    }
}
