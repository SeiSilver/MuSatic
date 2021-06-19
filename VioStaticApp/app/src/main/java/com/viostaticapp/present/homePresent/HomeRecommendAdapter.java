package com.viostaticapp.present.homePresent;

import android.content.Context;
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

import java.util.List;

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.CustomViewHolder> {

    private Context context;
    private List<YoutubeVideo> videoList;

    public HomeRecommendAdapter(Context context, List<YoutubeVideo> videoList) {
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
    public void onBindViewHolder(@NonNull HomeRecommendAdapter.CustomViewHolder holder, int position) {

//        holder.rec_image_item.setImageResource(recommendVideoList.get(position).getImage());
        String url = videoList.get(position).getThumbnail();
        Picasso.get().load(url).fit().centerCrop(2).placeholder(R.drawable.img_not_found).into(holder.rec_image_item);

        holder.rec_video_name.setText(videoList.get(position).getTitle());
        holder.date_publish_text.setText(videoList.get(position).getPublishedAt());
//        holder.date_publish_text.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView rec_image_item;
        TextView rec_video_name, date_publish_text;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            rec_image_item = itemView.findViewById(R.id.rec_image_item);
            rec_video_name = itemView.findViewById(R.id.rec_video_name);
            date_publish_text = itemView.findViewById(R.id.date_publish_text);
        }
    }
}
