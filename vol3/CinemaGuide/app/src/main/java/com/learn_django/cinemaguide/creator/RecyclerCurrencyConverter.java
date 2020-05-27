package com.learn_django.cinemaguide.creator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn_django.cinemaguide.R;

import java.util.ArrayList;

public class RecyclerCurrencyConverter extends RecyclerView.Adapter<RecyclerCurrencyConverter.ViewHolder> {


    private ArrayList<String> locationsNameRus = new ArrayList<>();
    private ArrayList<Float> locationsCostFrom = new ArrayList<>();
    private ArrayList<Float> locationsCostTo = new ArrayList<>();
    private ArrayList<String> locsCurrencyFrom = new ArrayList<>();
    private String locsCurrencyTo;

    private Context mContext;

    public RecyclerCurrencyConverter(Context context, ArrayList<String> locationsNameRusL,
                                     ArrayList<Float> locationsCostFromL, ArrayList<Float> locationsCostToL, ArrayList<String> locsCurrencyFromL,
                                     String locsCurrencyToL) {

        locationsNameRus = locationsNameRusL;
        locationsCostFrom = locationsCostFromL;
        locationsCostTo = locationsCostToL;
        locsCurrencyFrom = locsCurrencyFromL;
        locsCurrencyTo = locsCurrencyToL;

        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jc_locs_cost_item, parent, false);
        RecyclerCurrencyConverter.ViewHolder holder = new RecyclerCurrencyConverter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvLcNameRus.setText(locationsNameRus.get(position));
        holder.tvLcCurFrom.setText(locsCurrencyFrom.get(position));

        holder.tvLcCostFrom.setText(String.valueOf(locationsCostFrom.get(position)));
        holder.tvLcCurTo.setText(locsCurrencyTo);
        holder.tvLcCostTo.setText(String.valueOf(locationsCostTo.get(position)));

    }

    @Override
    public int getItemCount() {
        return locationsNameRus.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvLcNameRus;
        TextView tvLcCostFrom;
        TextView tvLcCurFrom;
        TextView tvLcCostTo;
        TextView tvLcCurTo;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLcNameRus = itemView.findViewById(R.id.tv_cj_lc_name_rus);
            tvLcCostFrom = itemView.findViewById(R.id.tv_jc_cost_before);
            tvLcCurFrom = itemView.findViewById(R.id.tv_jc_cur_before);
            tvLcCostTo = itemView.findViewById(R.id.tv_jc_cost_after);
            tvLcCurTo = itemView.findViewById(R.id.tv_jc_cur_after);
        }
    }

}

