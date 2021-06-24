package com.viostaticapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.R;
import com.viostaticapp.data.EnumInit;
import com.viostaticapp.data.model.User;

public class SignupActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword, edtRePassword;

    ProgressDialog progressDialog;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtEmail = findViewById(R.id.edt_signup_email);
        edtPassword = findViewById(R.id.edt_signup_password);
        edtRePassword = findViewById(R.id.edt_signup_retypePassword);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    // onClickEvent
    public void signup(View view) {
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sEmail = edtEmail.getText().toString().trim();
        String sPassword = edtPassword.getText().toString().trim();
        String sRePassword = edtRePassword.getText().toString().trim();

        if (sEmail.isEmpty()) {

            progressDialog.dismiss();
            createAlert("Error", "Please enter your email!", "OK");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {

            progressDialog.dismiss();
            createAlert("Error", "Please enter a valid email!", "OK");

        } else if (sPassword.isEmpty()) {
            progressDialog.dismiss();
            createAlert("Error", "Please enter password!", "OK");
        } else if (sPassword.length() < 6) {
            progressDialog.dismiss();
            createAlert("Error", "Minimum password length must be 6!", "OK");
        } else if (sPassword.compareTo(sRePassword) != 0) {
            progressDialog.dismiss();
            createAlert("Error", "Password not match!", "OK");
        } else
            updateToDatabase(sEmail, sPassword);

    }

    private void updateToDatabase(String sEmail, String sPassword) {

        firebaseAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        user.setUserId(authResult.getUser().getUid());
                        user.setEmail(sEmail);
                        user.setPassword(sPassword);
                        user.setName(sEmail);

                        db.collection(EnumInit.Collections.User.name).document(user.getUserId()).set(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "User registered successfully!",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        createAlert("Error", "Registered successfully but couldn't save details!", "OK");

                                    }
                                });
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            createAlert("Error", "This email address is already registered with us!", "OK");
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to register! Please try again later.", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void createAlert(String alertTitle, String alertMessage, String positiveText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle)
                .setMessage(alertMessage)
                .setPositiveButton(positiveText, null)
                .create().show();
    }

    // onClickEvent
    public void redirectLogin(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}