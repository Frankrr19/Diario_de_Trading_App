package com.example.diariodetradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView lblSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        goRegister();
    }

    private void goRegister() {
        lblSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void initializeComponents() {
        txtEmail = findViewById(R.id.txtEmailLoginActivity);
        txtPassword = findViewById(R.id.txtPasswordLoginActivity);
        btnLogin = findViewById(R.id.btnLoginLoginActivity);
        lblSignUp = findViewById(R.id.lblSignUpLoginActivity);
    }
}