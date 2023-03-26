package com.example.diariodetradingapp.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.diariodetradingapp.HomeActivity;
import com.example.diariodetradingapp.LoginActivity;
import com.example.diariodetradingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler;

    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initializeComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                user = auth.getCurrentUser();
                updateUI(user);
            }
        }, 3000); // 3000 = 3 segundos de duración de la pantalla de bienvenida


    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            finish();
        }else{
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void initializeComponents() {
        auth = FirebaseAuth.getInstance();
    }
}