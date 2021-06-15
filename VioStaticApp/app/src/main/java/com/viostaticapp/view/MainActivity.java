package com.viostaticapp.view;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;

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

        String titleFormat = StringEscapeUtils.unescapeHtml4("&nbsp;asdasdasd adasd");

        Log.e("ads", "asd");


//        YoutubeAPISearch test = new YoutubeAPISearchImp();
//        test.search("son tung");
//        test.search("yoasobi");
//        test.search("eve");
//        test.search("relaxing music sleep");
//        test.search("phe phim");
//        test.search("phe game");
//        test.search("english song");

    }

}