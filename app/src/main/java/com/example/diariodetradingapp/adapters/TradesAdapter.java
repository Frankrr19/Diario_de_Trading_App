package com.example.diariodetradingapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertSeeInfoTrade(trade).show();
            }
        });
    }

    private AlertDialog alertSeeInfoTrade(Trade trade) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(true);


        View tradeView = LayoutInflater.from(context).inflate(R.layout.trade_view_alert, null);
        TextView lblState = tradeView.findViewById(R.id.lblStateSeeTrade);
        TextView lblEntry = tradeView.findViewById(R.id.lblEntrySeeTrade);
        TextView lblYear = tradeView.findViewById(R.id.lblYearSeeTrade);
        TextView lblDay = tradeView.findViewById(R.id.lblDaySeeTrade);
        TextView lblMonth = tradeView.findViewById(R.id.lblMonthSeeTrade);
        TextView lblMarket = tradeView.findViewById(R.id.lblMarketSeeTrade);
        TextView lblContracts = tradeView.findViewById(R.id.lblContractsSeeTrade);
        TextView lblPointValue = tradeView.findViewById(R.id.lblPointValueSeeTrade);
        TextView lblPoints = tradeView.findViewById(R.id.lblPointsSeeTrade);
        TextView lblEmotion = tradeView.findViewById(R.id.lblEmotionSeeTrade);
        TextView lblTotal = tradeView.findViewById(R.id.lblTotalEditTrade);
        builder.setView(tradeView);

        lblState.setText(trade.getState());
        lblEntry.setText(trade.getEntry());
        lblYear.setText(String.valueOf(trade.getYear()));
        lblDay.setText(String.valueOf(trade.getDay()));
        lblMonth.setText(trade.getMonth());
        lblMarket.setText(trade.getMarket());
        lblContracts.setText(String.valueOf(trade.getContracts()));
        lblPointValue.setText(String.valueOf(trade.getPointValue()));
        lblPoints.setText(String.valueOf(trade.getPoints()));
        lblEmotion.setText(trade.getEmotion());
        lblTotal.setText(String.valueOf(trade.getTotal()));

        builder.setNegativeButton(R.string.cerrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    //initializaSpinners(spEstateEditTrade, spEntryEditTrade, spYearEditTrade, spDayEditTrade, spMonthEditTrade, spMarketEditTrade, spEmotionEditTrade);
    private void initializaSpinners(Spinner spEstateEditTrade, Spinner spEntryEditTrade, Spinner spYearEditTrade, Spinner spDayEditTrade, Spinner spMonthEditTrade, Spinner spMarketEditTrade, Spinner spEmotionEditTrade) {
        ArrayAdapter<CharSequence> adapterState = ArrayAdapter.createFromResource(context, R.array.selectState, android.R.layout.simple_spinner_item);
        spEstateEditTrade.setAdapter(adapterState);

        ArrayAdapter<CharSequence> adapterEntry = ArrayAdapter.createFromResource(context, R.array.selectEntry, android.R.layout.simple_spinner_item);
        spEntryEditTrade.setAdapter(adapterEntry);

        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(context, R.array.selectYear, android.R.layout.simple_spinner_item);
        spYearEditTrade.setAdapter(adapterYear);

        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(context, R.array.selectDay, android.R.layout.simple_spinner_item);
        spDayEditTrade.setAdapter(adapterDay);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(context, R.array.selectMonth, android.R.layout.simple_spinner_item);
        spMonthEditTrade.setAdapter(adapterMonth);

        ArrayAdapter<CharSequence> adapterMarket = ArrayAdapter.createFromResource(context, R.array.selectMarket, android.R.layout.simple_spinner_item);
        spMarketEditTrade.setAdapter(adapterMarket);

        ArrayAdapter<CharSequence> adapterEmotion = ArrayAdapter.createFromResource(context, R.array.selectEmotion, android.R.layout.simple_spinner_item);
        spEmotionEditTrade.setAdapter(adapterEmotion);
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
