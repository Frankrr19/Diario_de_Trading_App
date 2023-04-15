package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.diariodetradingapp.adapters.TradesAdapter;
import com.example.diariodetradingapp.databinding.ActivitySeeTradesBinding;
import com.example.diariodetradingapp.modelos.Trade;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SeeTradesActivity extends AppCompatActivity {

    private ActivitySeeTradesBinding binding;
    private List<Trade> trades;
    private TradesAdapter adapter;
    private RecyclerView.LayoutManager lm;
    private FirebaseDatabase database;
    private DatabaseReference refUser;
    private Toolbar cabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeTradesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cabecera = findViewById(R.id.cabecera);
        setSupportActionBar(cabecera);

        database = FirebaseDatabase.getInstance("https://bd-diariotrading-default-rtdb.europe-west1.firebasedatabase.app/");
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lista_trades");

        trades = new ArrayList<>();
        adapter = new TradesAdapter(trades, R.layout.trade_view_holder, this, refUser);
        lm = new LinearLayoutManager(this);

        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trades.clear();
                if (snapshot.exists()) {
                    GenericTypeIndicator<ArrayList<Trade>> gti = new GenericTypeIndicator<ArrayList<Trade>>() {};
                    ArrayList<Trade> temp = snapshot.getValue(gti);
                    trades.addAll(temp);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.contenedor.setAdapter(adapter);
        binding.contenedor.setLayoutManager(lm);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SeeTradesActivity.this, HomeActivity.class));
        finish();
    }
}