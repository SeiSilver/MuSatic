package com.viostaticapp.present.homePresent;

import android.content.Context;
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

import java.util.List;

public class HomeLatestAdapter extends RecyclerView.Adapter<HomeLatestAdapter.CustomViewHolder> {

    private Context context;
    private List<YoutubeVideo> videoList;

    public HomeLatestAdapter(Context context, List<YoutubeVideo> videoList) {
        this.context = context;
        this.videoList = videoList;
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
