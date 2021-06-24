package com.viostaticapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.viostaticapp.R;
import com.viostaticapp.service.YoutubeAPISearch;
import com.viostaticapp.service.YoutubeAPISearchImp;

import org.apache.commons.text.StringEscapeUtils;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.base_custom_toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);

        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    private void initData() {

//        YoutubeAPISearch test = new YoutubeAPISearchImp();
//        test.search("yoasobi song");
//        test.search("hololive song");
//        test.search("phim reviewer");
//        test.search("vì sao lại phế");
//        test.search("schannel");
//        test.search("android studio");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}