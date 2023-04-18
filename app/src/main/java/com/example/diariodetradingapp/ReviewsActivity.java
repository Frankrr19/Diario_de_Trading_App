package com.example.diariodetradingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diariodetradingapp.adapters.ReviewsAdapter;
import com.example.diariodetradingapp.adapters.TradesAdapter;
import com.example.diariodetradingapp.databinding.ActivityReviewsBinding;
import com.example.diariodetradingapp.modelos.Review;
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


public class ReviewsActivity extends AppCompatActivity {

    private ActivityReviewsBinding binding;
    private Toolbar cabecera;

    private List<Review> reviews;
    private ReviewsAdapter adapter;
    private RecyclerView.LayoutManager lm;
    private FirebaseDatabase database;
    private DatabaseReference refUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializaComponents();

        setSupportActionBar(cabecera);

        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviews.clear();
                if (snapshot.exists()){
                    GenericTypeIndicator<ArrayList<Review>> gti = new GenericTypeIndicator<ArrayList<Review>>() {};
                    ArrayList<Review> temp = snapshot.getValue(gti);
                    reviews.addAll(temp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.contenedor.setAdapter(adapter);
        binding.contenedor.setLayoutManager(lm);

        binding.btnAddReviewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertCreateReview().show();
            }
        });

    }

    private AlertDialog alertCreateReview() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);

        View reviewView = LayoutInflater.from(this).inflate(R.layout.create_review_alert, null);
        Spinner spYear = reviewView.findViewById(R.id.spYearCreateReview);
        Spinner spDay = reviewView.findViewById(R.id.spDayCreateReview);
        Spinner spMonth = reviewView.findViewById(R.id.spMonthCreateReview);
        EditText txtTextReview = reviewView.findViewById(R.id.txtTextCreateReview);

        initializaSpinners(spYear, spDay, spMonth);

        builder.setView(reviewView);


        builder.setNegativeButton(R.string.cerrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(spYear.getSelectedItem() != "" ||
                    spDay.getSelectedItem() != "" ||
                    spMonth.getSelectedItem() != "" ||
                    !txtTextReview.getText().toString().isEmpty()){
                    try {
                        int year = Integer.parseInt(spYear.getSelectedItem().toString());
                        int day = Integer.parseInt(spDay.getSelectedItem().toString());
                        String month = spMonth.getSelectedItem().toString();
                        String txtReview = txtTextReview.getText().toString();

                        Review review = new Review(year, day, month, txtReview);
                        reviews.add(review);
                        refUser.setValue(reviews);
                        dialogInterface.dismiss();
                    }catch (Exception e){
                        Toast.makeText(ReviewsActivity.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                        alertCreateReview().show();
                    }
                }
            }
        });
        return builder.create();
    }

    private void initializaSpinners(Spinner spYear, Spinner spDay, Spinner spMonth) {
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this, R.array.selectYear, android.R.layout.simple_spinner_item);
        spYear.setAdapter(adapterYear);

        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this, R.array.selectDay, android.R.layout.simple_spinner_item);
        spDay.setAdapter(adapterDay);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.selectMonth, android.R.layout.simple_spinner_item);
        spMonth.setAdapter(adapterMonth);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReviewsActivity.this, HomeActivity.class));
        finish();
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
            startActivity(new Intent(ReviewsActivity.this, ConfigDatosPersonalesActivity.class));
            finish();
        }
        return true;
    }

    private void initializaComponents() {
        cabecera = findViewById(R.id.cabecera);
        reviews = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://bd-diariotrading-default-rtdb.europe-west1.firebasedatabase.app/");
        refUser = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("lista_reviews");

        adapter = new ReviewsAdapter(reviews, R.layout.review_view_holder, this, refUser);
        lm = new LinearLayoutManager(this);
    }
}