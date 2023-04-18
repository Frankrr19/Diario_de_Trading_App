package com.example.diariodetradingapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diariodetradingapp.R;
import com.example.diariodetradingapp.ReviewsActivity;
import com.example.diariodetradingapp.modelos.Review;
import com.example.diariodetradingapp.modelos.Trade;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewVH> {

    private List<Review> objects;
    private  int resource;
    private Context context;
    private DatabaseReference refDatabase;

    public ReviewsAdapter(List<Review> objects, int resource, Context context, DatabaseReference refDatabase) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.refDatabase = refDatabase;
    }

    @NonNull
    @Override
    public ReviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reviewView = LayoutInflater.from(context).inflate(resource, null);
        reviewView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ReviewVH(reviewView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewVH holder, int position) {
        Review review = objects.get(position);
        holder.lblDay.setText(String.valueOf(review.getDay()));
        holder.lblDay.setText(review.getMonth());
        holder.lblYear.setText(String.valueOf(review.getYear()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDeleteReview(review).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertSeeInfoReview(review).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog alertSeeInfoReview(Review review) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(true);


        View reviewView = LayoutInflater.from(context).inflate(R.layout.review_view_alert, null);
        TextView lblYear = reviewView.findViewById(R.id.lblYearReviewViewAlert);
        TextView lblDay = reviewView.findViewById(R.id.lblDayReviewViewAlert);
        TextView lblMonth = reviewView.findViewById(R.id.lblMonthReviewViewAlert);
        EditText lblTextReview = reviewView.findViewById(R.id.lblMultiReviewReviewViewAlert);

        lblTextReview.setEnabled(true);

        builder.setView(reviewView);

        lblDay.setText(String.valueOf(review.getDay()));
        lblMonth.setText(review.getMonth());
        lblYear.setText(String.valueOf(review.getYear()));
        lblTextReview.setText(review.getText());

        builder.setNegativeButton(R.string.cerrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    private AlertDialog alertDeleteReview(Review review) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.eliminarReview);
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
                objects.remove(review);
                refDatabase.setValue(objects);
            }
        });
        return builder.create();
    }

    public class ReviewVH  extends RecyclerView.ViewHolder {
        TextView lblDay, lblMonth, lblYear;
        ImageButton btnDelete;
        public ReviewVH(@NonNull View itemView) {
            super(itemView);
            lblDay = itemView.findViewById(R.id.lblDayReviewVH);
            lblMonth = itemView.findViewById(R.id.lblMonthReviewVH);
            lblYear = itemView.findViewById(R.id.lblYearReviewVH);
            btnDelete = itemView.findViewById(R.id.btnDeleteReviewVH);
        }
    }
}
