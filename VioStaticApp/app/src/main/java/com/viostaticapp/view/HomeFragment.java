package com.viostaticapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viostaticapp.R;
import com.viostaticapp.data.model.AllCategory;
import com.viostaticapp.data.model.CategoryItem;
import com.viostaticapp.present.homePresent.HomeAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    RecyclerView homeRecycler;
    HomeAdapter homeAdapter;
    List<AllCategory> allCategoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem(1, R.drawable.test1));
        categoryItems.add(new CategoryItem(1, R.drawable.test2));
        categoryItems.add(new CategoryItem(1, R.drawable.test1));
        categoryItems.add(new CategoryItem(1, R.drawable.test2));
        categoryItems.add(new CategoryItem(1, R.drawable.test1));
        categoryItems.add(new CategoryItem(1, R.drawable.test2));

        allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory("Recommend", categoryItems));
        allCategoryList.add(new AllCategory("Hot Trend", categoryItems));
        allCategoryList.add(new AllCategory("Favourite", categoryItems));

        setHomeRecycler(allCategoryList);
    }

    private void setHomeRecycler(List<AllCategory> allCategories) {
        homeRecycler = this.getView().findViewById(R.id.home_main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        homeRecycler.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(getContext(), allCategories);
        homeRecycler.setAdapter(homeAdapter);
    }
}