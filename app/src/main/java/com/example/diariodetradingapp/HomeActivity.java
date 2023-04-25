package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.diariodetradingapp.databinding.ActivityHome2Binding;
import com.example.diariodetradingapp.modelos.Constantes;
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

public class HomeActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference refUser;
    private ActivityHome2Binding binding;
    private List<Trade> trades;


    private Toolbar cabecera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeComponents();

        setSupportActionBar(cabecera);

        readUserConfig();

        binding.imgAddTradeHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreateTradeActivity.class));
                finish();
            }
        });

        binding.imgSeeTradesHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SeeTradesActivity.class));
                finish();
            }
        });

        binding.imgReviewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ReviewsActivity.class));
                finish();
            }
        });

        binding.imgStatsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, StatsActivity.class));
                finish();
            }
        });
    }

    private void readUserConfig() {
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trades.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Trade>> gti = new GenericTypeIndicator<ArrayList<Trade>>() {};
                    ArrayList<Trade> temp = snapshot.getValue(gti);
                    trades.addAll(temp);

                    int c = 0;
                    int v = 0;
                    Trade trade = new Trade();
                    for (int i = 0; i < trades.size(); i++) {
                        trade = trades.get(i);

                        if (trade.getEntry().equals("LARGO") || trade.getEntry().equals("LONG")){
                            c++;
                        }else{
                            if (trade.getEntry().equals("CORTO") || trade.getEntry().equals("SHORT")){
                                v++;
                            }
                        }
                    }

                    binding.lblBuyHomeActivity.setText(String.valueOf(c));
                    binding.lblSellHomeActivity.setText(String.valueOf(v));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeComponents() {
        cabecera = findViewById(R.id.cabecera);
        database = FirebaseDatabase.getInstance("https://bd-diariotrading-default-rtdb.europe-west1.firebasedatabase.app/");
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lista_trades");
        trades = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        alertConfirmarSalir().show();
    }

    private AlertDialog alertConfirmarSalir() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tittle_exit_app);
        builder.setCancelable(false);

        builder.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton(R.string.CONFIRM, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        return builder.create();
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
            startActivity(new Intent(HomeActivity.this, ConfigDatosPersonalesActivity.class));
            finish();
        }
        return true;
    }
}