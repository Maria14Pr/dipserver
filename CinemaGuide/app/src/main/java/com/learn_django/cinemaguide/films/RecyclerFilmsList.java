package com.learn_django.cinemaguide.films;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn_django.cinemaguide.R;

import java.util.ArrayList;


public class RecyclerFilmsList extends RecyclerView.Adapter<RecyclerFilmsList.ViewHolder>{

    private static final String TAG = "RecyclerProfileListAdapter";


    private ArrayList<Integer> idFilms = new ArrayList<>();
    private ArrayList<Integer> yearFilms = new ArrayList<>();
    private ArrayList<String> posterFilms = new ArrayList<>();
    private ArrayList<String> nameRusFilms = new ArrayList<>();

    private Context mContext;

    public RecyclerFilmsList(Context context, ArrayList<Integer> idFilmsL, ArrayList<String> nameRusFilmsL,
                             ArrayList<String> posterFilmsL, ArrayList<Integer> yearFilmsL) {
        idFilms = idFilmsL;
        nameRusFilms = nameRusFilmsL;
        posterFilms = posterFilmsL;
        yearFilms = yearFilmsL;

        mContext = context;
    }

    @Override
    public RecyclerFilmsList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.films_list_item, parent, false);
        RecyclerFilmsList.ViewHolder holder = new RecyclerFilmsList.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerFilmsList.ViewHolder holder, final int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_main_icon_foreground);

        Glide.with(mContext)
                .load(posterFilms.get(position))
                .apply(requestOptions)
                .into(holder.ivFlPoster);

        //holder.tv.setText(String.valueOf(pkPost.get(position)));
        holder.tvFlNameRus.setText(nameRusFilms.get(position));
        holder.tvFlYear.setText(String.valueOf(yearFilms.get(position)));

        holder.parentFilmsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                Bundle bundle = new Bundle();
                bundle.putInt("post_id",pkPost.get(position)); // Put anything what you want
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ShowPost();
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, myFragment)
                        .addToBackStack(null)
                        .commit();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return idFilms.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvFlNameRus;
        TextView tvFlYear;
        CardView parentFilmsCv;
        ImageView ivFlPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFlNameRus = itemView.findViewById(R.id.tv_fl_name_rus);
            tvFlYear = itemView.findViewById(R.id.tv_fl_name_year);
            ivFlPoster = itemView.findViewById(R.id.films_list_poster);
            parentFilmsCv = itemView.findViewById(R.id.cv_parent_films);
        }
    }
}
