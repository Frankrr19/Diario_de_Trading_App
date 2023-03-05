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

public class HomeActivity extends AppCompatActivity {

    private ActivityHome2Binding binding;

    private Toolbar cabecera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cabecera = findViewById(R.id.cabecera);
        setSupportActionBar(cabecera);


        binding.imgAddTradeHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreateTradeActivity.class));
                finish();
            }
        });
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