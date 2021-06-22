package com.viostaticapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Context context;

    EditText edtEmail, edtPassword, edtRePassword;

    ProgressDialog progressDialog;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        context=this;

        edtEmail = findViewById(R.id.edt_signup_email);
        edtPassword = findViewById(R.id.edt_signup_password);
        edtRePassword = findViewById(R.id.edt_signup_retypePassword);

        progressDialog = new ProgressDialog(context);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void signup(View view){
        progressDialog.setTitle("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sEmail=edtEmail.getText().toString().trim();
        String sPassword = edtPassword.getText().toString().trim();
        String sRePassword = edtRePassword.getText().toString().trim();

        if(sEmail.isEmpty())
            createAlert("Error","Please enter your email!","OK");
        else if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches())
            createAlert("Error","Please enter a valid email!","OK");
        else if(sPassword.isEmpty())
            createAlert("Error", "Please enter password!", "OK");
        else if(sPassword.length() < 6)
            createAlert("Error", "Minimum password length must be 6!", "OK");
        else if(sPassword.compareTo(sRePassword)!=0)
           createAlert("Error","Password not match!","OK");
        else
            uploadData(sEmail,sPassword);

        progressDialog.dismiss();

    }

    private void uploadData(String sEmail, String sPassword) {
        firebaseAuth.createUserWithEmailAndPassword(sEmail, sPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.setMessage("Saving...");
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("email", sEmail);
                dataMap.put("password", sPassword);

                db.collection("Accounts").add(dataMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "User registered successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createAlert("Error", "Registered successfully but couldn't save details!", "OK");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthUserCollisionException){
                    createAlert("Error", "This email address is already registered with us!", "OK");
                }else{
                    Toast.makeText(context, "Failed to register! Please try again later.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void createAlert(String alertTitle, String alertMessage, String positiveText){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle)
                .setMessage(alertMessage)
                .setPositiveButton(positiveText, null)
                .create().show();
    }

    public void redirectLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}