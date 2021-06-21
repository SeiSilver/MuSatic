package com.viostaticapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity {

    Context context;

    EditText edtEmail, edtPassword, edtRePassword;

    ProgressDialog progressDialog;

    FirebaseFirestore db;

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


    }

    public void signup(View view){
        String sEmail=edtEmail.getText().toString().trim();
        String sPassword = edtPassword.getText().toString().trim();
        String sRePassword = edtRePassword.getText().toString().trim();

        Log.i("LogMess",sPassword+" "+sRePassword+" "+sPassword.compareTo(sRePassword));


        if(sPassword.compareTo(sRePassword)!=0) {
            Toast.makeText(context, "Password not match", Toast.LENGTH_SHORT).show();
            Log.i("LogMess","Not match");
        }
        else
            uploadData(sEmail,sPassword);
    }

    private void uploadData(String sEmail, String sPassword) {
        progressDialog.setTitle("Registering account...");
        progressDialog.show();

        String id = UUID.randomUUID().toString();

        Map<String, Object> account = new HashMap<>();
        account.put("id", id);
        account.put("email",sEmail);
        account.put("password",sPassword);

        db.collection("Accounts").document(id).set(account)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(context,"Register successfully!",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void redirectLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}