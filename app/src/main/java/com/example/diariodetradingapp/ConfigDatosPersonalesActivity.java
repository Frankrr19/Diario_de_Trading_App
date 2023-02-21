package com.example.diariodetradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.diariodetradingapp.modelos.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigDatosPersonalesActivity extends AppCompatActivity {
    private EditText txtName;
    private EditText txtLastname;
    private EditText txtEmail;
    private Button btnSave;
    private FirebaseDatabase database;
    private DatabaseReference refUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_datos_personales);

        initializeComponents();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtName.getText().toString().isEmpty() || !txtLastname.getText().toString().isEmpty() || !txtEmail.getText().toString().isEmpty()){
                    User user = new User(txtName.getText().toString(), txtLastname.getText().toString(), txtEmail.getText().toString());
                    refUser.setValue(user);
                    startActivity(new Intent(ConfigDatosPersonalesActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
    }

    private void initializeComponents() {
        txtName = findViewById(R.id.txtNameCDPActivity);
        txtLastname = findViewById(R.id.txtLastnameCDPActivity);
        txtEmail = findViewById(R.id.txtEmailCDPActivity);
        btnSave = findViewById(R.id.btnSaveCDPActivity);

        database = FirebaseDatabase.getInstance("https://bd-diariotrading-default-rtdb.europe-west1.firebasedatabase.app/");
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("datos_personales");
    }
}