package com.viostaticapp.present.homePresent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viostaticapp.R;
import com.viostaticapp.model.AllCategory;
import com.viostaticapp.model.CategoryItem;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private Context context;
    private List<AllCategory> allCategories;

    public HomeAdapter(Context context, List<AllCategory> allCategories) {
        this.context = context;
        this.allCategories = allCategories;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_column_item, parent, false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {

        holder.categoryTitle.setText(allCategories.get(position).getCategoryTitle());
        setCategoryItemRecycler(holder.recyclerView, allCategories.get(position).getCategoryItemList());

    }

    @Override
    public int getItemCount() {
        return allCategories.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitle;
        RecyclerView recyclerView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTitle = itemView.findViewById(R.id.category_title);
            recyclerView = itemView.findViewById(R.id.item_row_recycler);
        }
    }

    private void setCategoryItemRecycler(RecyclerView recyclerView, List<CategoryItem> categoryItems) {

        HomeVerticalItemAdapter itemAdapter = new HomeVerticalItemAdapter(context, categoryItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(itemAdapter);

    }

}
