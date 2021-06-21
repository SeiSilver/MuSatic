package com.viostaticapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.viostaticapp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void redirectToSignup(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);

        //Close login activity
        finish();
    }
}