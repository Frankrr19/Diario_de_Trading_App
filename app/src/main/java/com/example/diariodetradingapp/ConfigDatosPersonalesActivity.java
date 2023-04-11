package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diariodetradingapp.modelos.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfigDatosPersonalesActivity extends AppCompatActivity {
    private EditText txtName;
    private EditText txtLastname;
    private EditText txtEmail;
    private Button btnSave;
    private Button btnLogOut;
    private String a;
    private FirebaseDatabase database;
    private DatabaseReference refUser;

    private GoogleSignInClient mGoogleSignInClient;

    private GoogleSignInOptions gso;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_datos_personales);

        initializeComponents();
        readUserConfig();

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

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent mainActivity = new Intent(ConfigDatosPersonalesActivity.this, LoginActivity.class);
                            startActivity(mainActivity);
                            ConfigDatosPersonalesActivity.this.finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesion con google", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void readUserConfig() {
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    txtName.setText(snapshot.child("name").getValue().toString());
                    txtLastname.setText(snapshot.child("lastname").getValue().toString());
                    txtEmail.setText(snapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConfigDatosPersonalesActivity.this, HomeActivity.class));
        finish();
    }

    private void initializeComponents() {
        txtName = findViewById(R.id.txtNameCDPActivity);
        txtLastname = findViewById(R.id.txtLastnameCDPActivity);
        txtEmail = findViewById(R.id.txtEmailCDPActivity);
        btnSave = findViewById(R.id.btnSaveCDPActivity);
        btnLogOut = findViewById(R.id.btnLogOutCDPActivity);
        a = "a";
        database = FirebaseDatabase.getInstance("https://bd-diariotrading-default-rtdb.europe-west1.firebasedatabase.app/");
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("datos_personales");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
}