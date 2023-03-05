package com.example.diariodetradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.diariodetradingapp.databinding.ActivityCreateTradeBinding;
import com.example.diariodetradingapp.databinding.ActivityHome2Binding;

public class CreateTradeActivity extends AppCompatActivity {

    private ActivityCreateTradeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTradeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateTradeActivity.this, HomeActivity.class));
        finish();
    }
}