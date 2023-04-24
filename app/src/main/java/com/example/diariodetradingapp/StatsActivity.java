package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.diariodetradingapp.databinding.ActivityStatsBinding;
import com.example.diariodetradingapp.modelos.Constantes;
import com.example.diariodetradingapp.modelos.Stat;
import com.example.diariodetradingapp.modelos.Trade;
import com.example.diariodetradingapp.modelos.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {
    private ActivityStatsBinding binding;

    private List<Trade> trades;
    private List<User> usuario;

    private String initialCash;
    private Float total;
    private Float saldo;
    private Float profit;
    private Float stop;
    private Float suma;

    private FirebaseDatabase database;
    private DatabaseReference refUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializaComponents();

        try {
            getInitialCash();
        }catch (NumberFormatException e){
            Toast.makeText(this, ""+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        binding.btnOcultarCollapseEconomics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.collapseEconomicResults.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            }
        });

        binding.btnMostrarCollapseEconomics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.collapseEconomicResults.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        });
    }

    private void getTrades() {
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lista_trades");

        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trades.clear();
                if (snapshot.exists()) {
                    GenericTypeIndicator<ArrayList<Trade>> gti = new GenericTypeIndicator<ArrayList<Trade>>() {};
                    ArrayList<Trade> temp = snapshot.getValue(gti);
                    trades.addAll(temp);

                    int n = trades.size();
                    Trade trade = new Trade();
                    for (int i = 0; i < trades.size(); i++) {
                        trade = trades.get(i);
                        if (trade.getTakeProfit().equals(Constantes.PROFIT)){
                            profit = trade.getTotal();
                            profit++;
                        }else{
                            stop = trade.getTotal();
                            stop++;
                        }
                    }

                    stop -= 1;
                    profit -= 1;

                    suma = (profit + stop) + saldo;

                    binding.lblProfitsStats.setText(String.valueOf(profit));
                    binding.lblStopLossStats.setText(String.valueOf(stop));
                    binding.lblTotalStats.setText(String.valueOf(suma));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StatsActivity.this, "error ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getInitialCash() {
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("datos_personales");

        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    try {
                        initialCash = snapshot.child("initialCash").getValue().toString();
                        Log.d("initialCash", initialCash);
                        saldo = Float.parseFloat(initialCash);
                        Log.d("saldo", ""+saldo);
                        binding.lblInitialCashStats.setText(initialCash);
                        getTrades();
                    }catch (Exception e){
                        Toast.makeText(StatsActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializaComponents() {
        database = FirebaseDatabase.getInstance("https://bd-diariotrading-default-rtdb.europe-west1.firebasedatabase.app/");
        trades = new ArrayList<>();
        usuario = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StatsActivity.this, HomeActivity.class));
        finish();
    }
}