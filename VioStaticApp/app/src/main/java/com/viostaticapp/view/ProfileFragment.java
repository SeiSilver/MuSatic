package com.viostaticapp.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.viostaticapp.R;
import com.viostaticapp.data.EnumInit;


public class ProfileFragment extends Fragment {

    TextView profile_logout_tv, profile_tv_user;
    Button profile_login_btn;
    ImageView iv_changeUsername_arrowIcon, iv_changeUsername_save, iv_changePassword_show,iv_changePassword_save, iv_changePassword_arrowIcon;
    CardView cv_changeUsername, cv_changePassword,cv_accountSetting;
    ConstraintLayout lo_changeUsername, lo_changePassword;
    EditText edt_confirmPassword, edt_changeUsername_newUsername,edt_changePassword_newPassword,edt_changePassword_retypePassword;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser user;

    ProgressDialog progressDialog;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

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
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getContext());

        pref = getActivity().getSharedPreferences("VioStaticPref", Context.MODE_PRIVATE);
        editor = pref.edit();

        profile_tv_user = view.findViewById(R.id.profile_tv_user);
        profile_logout_tv = view.findViewById(R.id.profile_logout_tv);
        profile_login_btn = view.findViewById(R.id.profile_login_btn);
        cv_accountSetting = view.findViewById(R.id.cv_accountSetting);
        /*Change username*/
        iv_changeUsername_arrowIcon = view.findViewById(R.id.iv_changeUsername_arrowIcon);
        iv_changeUsername_save = view.findViewById(R.id.iv_saveUsername);
        lo_changeUsername = view.findViewById(R.id.lo_changeUsernameExpand);
        cv_changeUsername=view.findViewById(R.id.cv_changeUsername);
        edt_changeUsername_newUsername = view.findViewById(R.id.edt_changeUsername_newUsername);
        /*Change password*/
        iv_changePassword_show = view.findViewById(R.id.iv_changePassword_show);
        iv_changePassword_save = view.findViewById(R.id.iv_changePassword_save);
        cv_changePassword = view.findViewById(R.id.cv_changePassword);
        lo_changePassword = view.findViewById(R.id.lo_changePasswordExpand);
        edt_changePassword_newPassword = view.findViewById(R.id.edt_changePassword_newPassword);
        edt_changePassword_retypePassword = view.findViewById(R.id.edt_changePassword_retypePassword);
        iv_changePassword_arrowIcon = view.findViewById(R.id.iv_changePassword_arrowIcon);

        reloadLoginStatus();

        setButtonOnClick();

    }

    private void reloadLoginStatus() {

        user = firebaseAuth.getCurrentUser();
        if (user == null) {
            profile_login_btn.setVisibility(View.VISIBLE);
            profile_logout_tv.setVisibility(View.INVISIBLE);
            profile_tv_user.setVisibility(View.INVISIBLE);
            cv_accountSetting.setVisibility(View.INVISIBLE);
        } else {
            profile_login_btn.setVisibility(View.INVISIBLE);
            profile_tv_user.setVisibility(View.VISIBLE);
            profile_logout_tv.setVisibility(View.VISIBLE);
            profile_tv_user.setText(pref.getString("username","Username"));

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

        cv_changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {layoutChangeUsername();}
        });

        iv_changeUsername_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {changeUsername();}
        });

        iv_changePassword_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showPassword();}
        });

        iv_changePassword_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {changePassword();}
        });

        cv_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {layoutChangePassword();}
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

    public void layoutChangeUsername(){
        if(lo_changeUsername.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(lo_changeUsername, new AutoTransition());
            lo_changeUsername.setVisibility(View.VISIBLE);
            iv_changeUsername_arrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_36);
        }else{
            TransitionManager.beginDelayedTransition(lo_changeUsername, new AutoTransition());
            lo_changeUsername.setVisibility(View.GONE);
            iv_changeUsername_arrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_36);
        }
    }

    public void changeUsername(){

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm_password, (ViewGroup)getView(),false);
        edt_confirmPassword =view.findViewById(R.id.et_confirmPassword);
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm")
                .setMessage("Please confirm!")
                .setView(view)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if(user!=null){
                            db.collection(EnumInit.Collections.User.name).document(user.getUid()).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.getResult().get("password").toString().equals(edt_confirmPassword.getText().toString())){
                                                update("name",edt_changeUsername_newUsername.getText().toString());
                                                saveSharedPreferences(user);
                                                profile_tv_user.setText(edt_changeUsername_newUsername.getText().toString());
                                                reloadProfile();
                                            }
                                            else{
                                                progressDialog.dismiss();
                                                Toast.makeText(getContext(), "Incorrect password!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Failed!",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //just cancel. what you waiting for?
                    }
                })
                .show();

    }

    private void saveSharedPreferences(FirebaseUser user){

        editor.clear();

        db.collection(EnumInit.Collections.User.name).document(user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                        editor.putString("username", task.getResult().get("name").toString()) ;
                        editor.commit();
                    }
                });

    }


    private void update(String field, String value){
        progressDialog.setTitle("Update "+field+"...");
        progressDialog.show();

        db.collection(EnumInit.Collections.User.name).document(user.getUid())
                .update(field,value)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Change "+field+" successful!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Change "+field+" failed!", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private void showPassword(){
        if(edt_changePassword_newPassword.getInputType()==InputType.TYPE_CLASS_TEXT)
            // https://stackoverflow.com/questions/9307680/show-the-password-with-edittext
            edt_changePassword_newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        else
            edt_changePassword_newPassword.setInputType(InputType.TYPE_CLASS_TEXT);
    }
    private void changePassword(){
        if(edt_changePassword_newPassword.getText().toString().equals(
                edt_changePassword_retypePassword.getText().toString())){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm_password, (ViewGroup)getView(),false);
            edt_confirmPassword =view.findViewById(R.id.et_confirmPassword);
            new AlertDialog.Builder(getContext())
                    .setTitle("Confirm")
                    .setMessage("Please confirm!")
                    .setView(view)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if(user!=null){
                                db.collection(EnumInit.Collections.User.name).document(user.getUid()).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.getResult().get("password").toString().equals(edt_confirmPassword.getText().toString())){
                                                    update("password",edt_changePassword_newPassword.getText().toString());
                                                    reloadLoginStatus();
                                                }
                                                else{
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getContext(), "Incorrect password!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Failed!",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //just cancel. what you waiting for?
                        }
                    })
                    .show();
        }else
            Toast.makeText(getContext(),"Password not match!", Toast.LENGTH_SHORT).show();
    }

    public void layoutChangePassword(){
        if(lo_changePassword.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(lo_changePassword, new AutoTransition());
            lo_changePassword.setVisibility(View.VISIBLE);
            iv_changePassword_arrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_36);
        }else{
            TransitionManager.beginDelayedTransition(lo_changePassword, new AutoTransition());
            lo_changePassword.setVisibility(View.GONE);
            iv_changePassword_arrowIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_36);
        }
    }


    void reloadProfile(){
        lo_changeUsername.setVisibility(View.GONE);
        lo_changePassword.setVisibility(View.GONE);
        edt_changeUsername_newUsername.setText("");
        edt_changePassword_newPassword.setText("");
        edt_changePassword_retypePassword.setText("");
    }

}