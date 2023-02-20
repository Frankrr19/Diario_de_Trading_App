package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diariodetradingapp.modelos.Constantes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtConfirmEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnSignUp;
    private TextView lblLogIn;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inicializarComponentes();

        gologIn();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                //comprobarEmail(email);

                if (txtEmail.getText().toString().equals(txtConfirmEmail.getText().toString()) &&
                        txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
                    if (!txtEmail.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()){
                        doRegister(email,txtPassword.getText().toString());
                    }
                }
            }
        });
    }

    private void comprobarEmail(String email) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()){
                            SignInMethodQueryResult result = task.getResult();
                            if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0){
                                //alertRegister(Constantes.TITULO_ERROR, Constantes.EMAIL_EXISTE).show();
                                Toast.makeText(RegisterActivity.this, "Email ya existe", Toast.LENGTH_SHORT).show();
                            }else{
                                //alertRegister(Constantes.TITULO_EXITO, Constantes.EMAIL_INEXISTE).show();
                                Toast.makeText(RegisterActivity.this, "Exito", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            //alertRegister(Constantes.TITULO_ERROR,Constantes.ERROR_CONSULTA).show();
                            Toast.makeText(RegisterActivity.this, "Error consulta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private AlertDialog alertRegister(String tituloError, String emailExiste) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tituloError);
        builder.setMessage(emailExiste);
        builder.setCancelable(false);

        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();

    }


    private void doRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    user = mAuth.getCurrentUser();
                    user.getUid();
                    updateUi(user);
                }
            }
        });
    }

    private void updateUi(FirebaseUser user) {
        if (user != null){
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void gologIn() {

        lblLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void inicializarComponentes() {
        txtEmail = findViewById(R.id.txtEmailRegisterActivity);
        txtConfirmEmail = findViewById(R.id.txtConfirmEmailRegisterActivity);
        txtPassword = findViewById(R.id.txtPasswordRegisterActivity);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordRegisterActivity);
        btnSignUp = findViewById(R.id.btnSignUpRegisterActivity);
        lblLogIn = findViewById(R.id.lblLogInRegisterActivity);

        mAuth = FirebaseAuth.getInstance();
    }
}