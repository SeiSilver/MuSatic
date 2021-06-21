package com.viostaticapp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.viostaticapp.R;


public class ProfileFragment extends Fragment{

    TextView tvLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container,false);

        tvLogout = view.findViewById(R.id.tv_logout);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity activity = getActivity();
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.finish();
                activity.startActivity(intent);
            }
        });

        return view;
    }
}