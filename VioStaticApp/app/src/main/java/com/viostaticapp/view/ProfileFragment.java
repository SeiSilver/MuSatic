package com.viostaticapp.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.viostaticapp.R;


public class ProfileFragment extends Fragment {

    TextView profile_logout_tv, profile_tv_user;
    Button profile_login_btn;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        profile_tv_user = view.findViewById(R.id.profile_tv_user);
        profile_logout_tv = view.findViewById(R.id.profile_logout_tv);
        profile_login_btn = view.findViewById(R.id.profile_login_btn);

        reloadLoginStatus();

        setButtonOnClick();

    }

    private void reloadLoginStatus() {

        user = firebaseAuth.getCurrentUser();
        if (user == null) {
            profile_login_btn.setVisibility(View.VISIBLE);
            profile_logout_tv.setVisibility(View.INVISIBLE);
            profile_tv_user.setVisibility(View.INVISIBLE);

        } else {
            profile_login_btn.setVisibility(View.INVISIBLE);
            profile_tv_user.setVisibility(View.VISIBLE);
            profile_logout_tv.setVisibility(View.VISIBLE);
            profile_tv_user.setText(user.getEmail());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        reloadLoginStatus();

    }

    private void setButtonOnClick() {
        profile_logout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutCurrentUser();
            }
        });

        profile_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLoginPage();
            }
        });

    }

    public void logoutCurrentUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Logout")
                .setMessage("Are you sure to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        reloadLoginStatus();

                    }
                }).setNegativeButton("Cancel", null)
                .create().show();
    }

    public void navigateToLoginPage() {

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, 101);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

}