package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.diariodetradingapp.databinding.ActivityStatsBinding;
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

    private Float initialCash;
    private Float total;
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

        getInitialCash();
        binding.lblInitialCashStats.setText(String.valueOf(initialCash));
        //getTrades();

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StatsActivity.this, "error ", Toast.LENGTH_LONG).show();
            }
        });
        for (int i = 0; i < trades.size(); i++) {
            Trade trade = trades.get(i);
            if (trade.getTakeProfitOrLoss() == true){
                profit = trade.getTotal();
                profit++;
            }else{
                stop = trade.getTotal();
                stop++;
            }
        }
        suma = (profit + stop);
        total = suma + initialCash;
        binding.lblInitialCashStats.setText(String.valueOf(initialCash));
        binding.lblProfitsStats.setText(String.valueOf(profit));
        binding.lblStopLossStats.setText(String.valueOf(stop));
        binding.lblTotalStats.setText(String.valueOf(total));
    }

    private void getInitialCash() {
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("datos_personales");

        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator<User> gti = new GenericTypeIndicator<User>() {};
                    User user = snapshot.getValue(gti);

                    initialCash = user.getInitialCash();
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