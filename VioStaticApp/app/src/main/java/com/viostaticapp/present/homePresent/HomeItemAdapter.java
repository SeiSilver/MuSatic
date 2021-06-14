package com.viostaticapp.present.homePresent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viostaticapp.R;
import com.viostaticapp.data.model.CategoryItem;

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeViewHolder> {

    private Context context;
    private List<CategoryItem> categoryItemList;

    public HomeItemAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_home_row_item, parent, false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemAdapter.HomeViewHolder holder, int position) {

        holder.imageView.setImageResource(categoryItemList.get(position).getImage());
        holder.textView.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
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
