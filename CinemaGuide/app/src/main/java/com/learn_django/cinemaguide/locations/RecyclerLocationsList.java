package com.learn_django.cinemaguide.locations;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn_django.cinemaguide.R;

import java.util.ArrayList;

public class RecyclerLocationsList extends RecyclerView.Adapter<RecyclerLocationsList.ViewHolder>{

    private static final String TAG = "RecyclerProfileListAdapter";


    private ArrayList<Integer> idLocations = new ArrayList<>();
    private ArrayList<String> countryLocations = new ArrayList<>();
    private ArrayList<String> photoLocations = new ArrayList<>();
    private ArrayList<String> nameRusLocations = new ArrayList<>();

    private Context mContext;

    public RecyclerLocationsList(Context context, ArrayList<Integer> idLocationsL, ArrayList<String> nameRusLocationsL,
                             ArrayList<String> photoLocationsL, ArrayList<String> countryLocationsL) {
        idLocations = idLocationsL;
        nameRusLocations = nameRusLocationsL;
        photoLocations = photoLocationsL;
        countryLocations = countryLocationsL;

        mContext = context;
    }

    @Override
    public RecyclerLocationsList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locations_list_item, parent, false);
        RecyclerLocationsList.ViewHolder holder = new RecyclerLocationsList.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerLocationsList.ViewHolder holder, final int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_main_icon_foreground);

        Glide.with(mContext)
                .load(photoLocations.get(position))
                .apply(requestOptions)
                .into(holder.ivLlPhoto);

        holder.tvLlNameRus.setText(nameRusLocations.get(position));
        holder.tvLlCountry.setText(countryLocations.get(position));

        holder.parentLocationsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("location_id",idLocations.get(position)); // Put anything what you want
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new LocationItemFragment();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.screen_area, myFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return idLocations.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvLlNameRus;
        TextView tvLlCountry;
        CardView parentLocationsCv;
        ImageView ivLlPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            tvLlNameRus = itemView.findViewById(R.id.tv_ll_name_rus);
            tvLlCountry = itemView.findViewById(R.id.tv_ll_country);
            ivLlPhoto = itemView.findViewById(R.id.locations_list_photo);
            parentLocationsCv = itemView.findViewById(R.id.cv_parent_locations);
        }
    }
}
