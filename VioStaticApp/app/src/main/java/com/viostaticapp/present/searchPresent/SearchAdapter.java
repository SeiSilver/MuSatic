package com.viostaticapp.present.searchPresent;

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
import com.viostaticapp.view.YoutubePlayerActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder> {

    private Context context;
    private List<YoutubeVideo> videoList;

    public SearchAdapter(Context context, List<YoutubeVideo> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_video_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.CustomViewHolder holder, int position) {

        String url = videoList.get(position).getThumbnail();
        Picasso.get().load(url).fit().centerCrop(2).placeholder(R.drawable.img_not_found).into(holder.rec_image_item);

        holder.rec_video_name.setText(videoList.get(position).getTitle());
        holder.date_publish_text.setText(videoList.get(position).getPublishedAt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoutubePlayerActivity.class);
                intent.putExtra("youtubeVideo", videoList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView rec_image_item;
        TextView rec_video_name, date_publish_text;
        View itemView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            rec_image_item = itemView.findViewById(R.id.rec_image_item);
            rec_video_name = itemView.findViewById(R.id.rec_video_name);
            date_publish_text = itemView.findViewById(R.id.date_publish_text);
            this.itemView = itemView;
        }

    }

}
