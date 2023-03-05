package com.example.diariodetradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CreateTradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trade);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateTradeActivity.this, HomeActivity.class));
        finish();
    }
}