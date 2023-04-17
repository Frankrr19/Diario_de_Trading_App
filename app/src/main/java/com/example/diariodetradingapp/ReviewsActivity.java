package com.example.diariodetradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.example.diariodetradingapp.databinding.ActivityReviewsBinding;


public class ReviewsActivity extends AppCompatActivity {

    private ActivityReviewsBinding binding;
    private Toolbar cabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializaComponents();

        setSupportActionBar(cabecera);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReviewsActivity.this, HomeActivity.class));
        finish();
    }
    private void initializaComponents() {
        cabecera = findViewById(R.id.cabecera);
    }
}