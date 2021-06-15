package com.viostaticapp.present.homePresent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viostaticapp.R;
import com.viostaticapp.data.model.AllCategory;
import com.viostaticapp.data.model.CategoryItem;
import com.viostaticapp.data.model.YoutubeVideo;

import java.util.List;

public class HomeLatestAdapter extends RecyclerView.Adapter<HomeLatestAdapter.HomeViewHolder> {

    private Context context;
    private List<YoutubeVideo> latestVideoList;

    public HomeLatestAdapter(Context context, List<YoutubeVideo> latestVideoList) {
        this.context = context;
        this.latestVideoList = latestVideoList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_home_row_item, parent, false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLatestAdapter.HomeViewHolder holder, int position) {

        String url = latestVideoList.get(position).getThumbnail();
        Picasso.get().load(url).fit().centerCrop(2).placeholder(R.drawable.img_not_found).into(holder.imageView);

        holder.textView.setText(latestVideoList.get(position).getTitle());
        holder.textView.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return latestVideoList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
            textView = itemView.findViewById(R.id.text_video_name);
        }
    }

}
