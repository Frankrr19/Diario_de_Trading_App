package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.diariodetradingapp.databinding.ActivityCreateTradeBinding;
import com.example.diariodetradingapp.modelos.Trade;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateTradeActivity extends AppCompatActivity {

    private ActivityCreateTradeBinding binding;
    private ArrayList<Trade> trades;
    private Toolbar cabecera;
    private FirebaseDatabase database;
    private DatabaseReference refUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTradeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cabecera = findViewById(R.id.cabecera);
        setSupportActionBar(cabecera);

        trades = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://bd-diariotrading-default-rtdb.europe-west1.firebasedatabase.app/");
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lista_trades");

        initializaSpinners();

        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trades.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Trade>> gti = new GenericTypeIndicator<ArrayList<Trade>>() {};
                    ArrayList<Trade> temp = snapshot.getValue(gti);
                    trades.addAll(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnCreateCreateTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearTrade();
            }
        });
    }

    private void crearTrade() {
        if (binding.spEstateCreateTrade.getSelectedItem() != "" ||
            binding.spEntryCreateTrade.getSelectedItem() != "" ||
            binding.spYearCreateTrade.getSelectedItem() != "" ||
            binding.spDayCreateTrade.getSelectedItem() != "" ||
            binding.spMonthCreateTrade.getSelectedItem() != "" ||
            binding.spMarketCreateTrade.getSelectedItem() != "" ||
            !binding.txtContractsCreateTrade.getText().toString().isEmpty() ||
            !binding.txtPointValueCreateTrade.getText().toString().isEmpty() ||
            !binding.txtPointsCreateTrade.getText().toString().isEmpty() ||
            binding.spEmotionCreateTrade.getSelectedItem() != ""){
            try {
                String state = binding.spEstateCreateTrade.getSelectedItem().toString();
                String entry = binding.spEntryCreateTrade.getSelectedItem().toString();
                int year = Integer.parseInt(binding.spYearCreateTrade.getSelectedItem().toString());
                int day = Integer.parseInt(binding.spDayCreateTrade.getSelectedItem().toString());
                String month = binding.spMonthCreateTrade.getSelectedItem().toString();
                String market = binding.spMarketCreateTrade.getSelectedItem().toString();
                float contracts = Float.parseFloat(binding.txtContractsCreateTrade.getText().toString());
                float pointValue = Float.parseFloat(binding.txtPointValueCreateTrade.getText().toString());
                float points = Float.parseFloat(binding.txtPointsCreateTrade.getText().toString());
                String emotion = binding.spEmotionCreateTrade.getSelectedItem().toString();

                Trade trade = new Trade(state, entry, year, day, month, market, contracts, pointValue, points, emotion);
                trades.add(trade);
                refUser.setValue(trades);
                startActivity(new Intent(CreateTradeActivity.this, HomeActivity.class));
                finish();
            }catch (Exception e){
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            }
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.btnConfigUser) {
            startActivity(new Intent(CreateTradeActivity.this, ConfigDatosPersonalesActivity.class));
            finish();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateTradeActivity.this, HomeActivity.class));
        finish();
    }
}