package com.viostaticapp.present.searchPresent;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viostaticapp.R;
import com.viostaticapp.data.model.CategoryItem;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.HomeViewHolder> {



    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.HomeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTitle;
        RecyclerView recyclerView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

//            categoryTitle = itemView.findViewById(R.id.category_title);
//            recyclerView = itemView.findViewById(R.id.item_row_recycler);
        }

    }

    private void setCategoryItemRecycler(RecyclerView recyclerView, List<CategoryItem> categoryItems) {

//        HomeVerticalItemAdapter itemAdapter = new HomeVerticalItemAdapter(context, categoryItems);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//        recyclerView.setAdapter(itemAdapter);

    }

}
