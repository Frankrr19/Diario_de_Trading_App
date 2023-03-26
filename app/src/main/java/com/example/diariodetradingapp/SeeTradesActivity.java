package com.example.diariodetradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SeeTradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_trades);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SeeTradesActivity.this, HomeActivity.class));
        finish();
    }
}