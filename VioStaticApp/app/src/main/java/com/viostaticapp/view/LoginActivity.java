package com.viostaticapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.R;
import com.viostaticapp.data.EnumInit;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    EditText edtEmail, edtPassword;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edt_login_email);
        edtPassword = findViewById(R.id.edt_login_password);
        pref = getSharedPreferences("VioStaticPref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // onClickEvent
    public void login(View view) {

        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sEmail = edtEmail.getText().toString().trim();
        String sPassword = edtPassword.getText().toString().trim();

        if (sEmail.isEmpty()) {

            createAlert("Error", "Please enter your email!", "OK");
            progressDialog.dismiss();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {

            createAlert("Error", "Please enter a valid email!", "OK");
            progressDialog.dismiss();

        } else if (sPassword.isEmpty()) {

            createAlert("Error", "Please enter your password!", "OK");
            progressDialog.dismiss();

        } else {
            firebaseAuth.signInWithEmailAndPassword(sEmail, sPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    progressDialog.dismiss();
                    FirebaseUser user = authResult.getUser();
                    startMainActivity(user);

                    saveSharedPreferences(user);

                    Toast.makeText(getApplicationContext(), "Login Success at " + user.getEmail(), Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    if (e instanceof FirebaseAuthInvalidUserException) {
                        createAlert("Error", "This email is not registered with us!", "OK");
                    } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        createAlert("Error", "Invalid Password! Please try again.", "OK");
                    } else {
                        Toast.makeText(getApplicationContext(), "Unable to login! Please try after some time.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }

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

    private void startMainActivity(FirebaseUser user) {
        if (user != null) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void createAlert(String alertTitle, String alertMessage, String positiveText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle)
                .setMessage(alertMessage)
                .setPositiveButton(positiveText, null)
                .create().show();
    }

    // onClickEvent
    public void redirectToSignup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // onClickEvent
    public void backToPrevious(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}