package com.example.diariodetradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.diariodetradingapp.databinding.ActivityCreateTradeBinding;
import com.example.diariodetradingapp.databinding.ActivityHome2Binding;

public class CreateTradeActivity extends AppCompatActivity {

    private ActivityCreateTradeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTradeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initializaSpinners();
    }

    private void initializaSpinners() {
        ArrayAdapter<CharSequence> adapterState = ArrayAdapter.createFromResource(this, R.array.selectState, android.R.layout.simple_spinner_item);
        binding.spEstateCreateTrade.setAdapter(adapterState);

        ArrayAdapter<CharSequence> adapterEntry = ArrayAdapter.createFromResource(this, R.array.selectEntry, android.R.layout.simple_spinner_item);
        binding.spEntryCreateTrade.setAdapter(adapterEntry);

        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this, R.array.selectYear, android.R.layout.simple_spinner_item);
        binding.spYearCreateTrade.setAdapter(adapterYear);

        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this, R.array.selectDay, android.R.layout.simple_spinner_item);
        binding.spDayCreateTrade.setAdapter(adapterDay);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.selectMonth, android.R.layout.simple_spinner_item);
        binding.spMonthCreateTrade.setAdapter(adapterMonth);

        ArrayAdapter<CharSequence> adapterMarket = ArrayAdapter.createFromResource(this, R.array.selectMarket, android.R.layout.simple_spinner_item);
        binding.spMarketCreateTrade.setAdapter(adapterMarket);

        ArrayAdapter<CharSequence> adapterEmotion = ArrayAdapter.createFromResource(this, R.array.selectEmotion, android.R.layout.simple_spinner_item);
        binding.spEmotionCreateTrade.setAdapter(adapterEmotion);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateTradeActivity.this, HomeActivity.class));
        finish();
    }
}