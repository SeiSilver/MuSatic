package com.viostaticapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.viostaticapp.R;

public class LoginActivity extends AppCompatActivity {
    private Context context;

    ProgressDialog progressDialog;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        progressDialog = new ProgressDialog(context);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edt_login_email);
        edtPassword=findViewById(R.id.edt_login_password);
    }

    public void login(View view){
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String sEmail = edtEmail.getText().toString().trim();
        String sPassword = edtPassword.getText().toString().trim();

        if(sEmail.isEmpty())
            createAlert("Error", "Please enter your email!", "OK");
        else if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches())
            createAlert("Error", "Please enter a valid email!", "OK");
        else if(sPassword.isEmpty())
            createAlert("Error", "Please enter your password!", "OK");
        else{
            firebaseAuth.signInWithEmailAndPassword(sEmail, sPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    progressDialog.dismiss();
                    FirebaseUser user = authResult.getUser();
                    startMainActivity(user);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    if (e instanceof FirebaseAuthInvalidUserException){
                        createAlert("Error", "This email is not registered with us!", "OK");
                    }else if(e instanceof FirebaseAuthInvalidCredentialsException){
                        createAlert("Error", "Invalid Password! Please try again.", "OK");
                    }else{
                        Toast.makeText(context, "Unable to login! Please try after some time.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private void startMainActivity(FirebaseUser user){
        if (user != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void createAlert(String alertTitle, String alertMessage, String positiveText){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle)
                .setMessage(alertMessage)
                .setPositiveButton(positiveText, null)
                .create().show();
    }

    public void redirectToSignup(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);

        //Close login activity
        finish();
    }
}