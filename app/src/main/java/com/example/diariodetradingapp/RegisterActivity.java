package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtConfirmEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnSignUp;
    private TextView lblLogIn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeComponents();

        gologIn();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();

                checkEmail(email);

                if (txtEmail.getText().toString().equals(txtConfirmEmail.getText().toString()) &&
                        txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
                    if (!txtEmail.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()){
                        doRegister(email,txtPassword.getText().toString());
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, R.string.incorrect_email_or_password, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkEmail(String email) {
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()){
                            SignInMethodQueryResult result = task.getResult();
                            if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0){
                                Toast.makeText(RegisterActivity.this, R.string.email_in_use, Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(RegisterActivity.this, R.string.account_created, Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, R.string.query_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void doRegister(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    updateUI(auth.getCurrentUser());
                }else{
                    Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void gologIn() {
        lblLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void initializeComponents() {
        txtEmail = findViewById(R.id.txtEmailRegisterActivity);
        txtConfirmEmail = findViewById(R.id.txtConfirmEmailRegisterActivity);
        txtPassword = findViewById(R.id.txtPasswordRegisterActivity);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordRegisterActivity);
        btnSignUp = findViewById(R.id.btnSignUpRegisterActivity);
        lblLogIn = findViewById(R.id.lblLogInRegisterActivity);
        auth = FirebaseAuth.getInstance();
    }
}