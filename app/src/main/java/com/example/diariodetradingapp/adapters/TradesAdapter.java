package com.example.diariodetradingapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diariodetradingapp.R;
import com.example.diariodetradingapp.modelos.Trade;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class TradesAdapter extends RecyclerView.Adapter<TradesAdapter.TradeVH> {

    private List<Trade> objects;
    private  int resource;
    private Context context;
    private DatabaseReference refDatabase;

    public TradesAdapter(List<Trade> objects, int resource, Context context, DatabaseReference refDatabase) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
        this.refDatabase = refDatabase;
    }

    @NonNull
    @Override
    public TradeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tradeView = LayoutInflater.from(context).inflate(resource, null);
        tradeView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new TradeVH(tradeView);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeVH holder, int position) {
        Trade trade = objects.get(position);
        holder.lblDay.setText(String.valueOf(trade.getDay()));
        holder.lblMonth.setText(trade.getMonth());
        holder.lblYear.setText(String.valueOf(trade.getYear()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDeleteTrade(trade).show();
            }
        });
    }

    private AlertDialog alertDeleteTrade(Trade trade) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.eliminarTrade);
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
                objects.remove(trade);
                refDatabase.setValue(objects);
            }
        });
        return builder.create();
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class TradeVH extends RecyclerView.ViewHolder {
        TextView lblDay, lblMonth, lblYear;
        ImageButton btnDelete;
        public TradeVH(@NonNull View itemView) {
            super(itemView);
            lblDay = itemView.findViewById(R.id.lblDayVH);
            lblMonth = itemView.findViewById(R.id.lblMonthVH);
            lblYear = itemView.findViewById(R.id.lblYearVH);
            btnDelete = itemView.findViewById(R.id.btnDeleteVH);
        }
    }
}
