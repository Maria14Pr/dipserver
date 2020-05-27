package com.learn_django.cinemaguide.creator;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;


public class RecyclerCreateJourneyFilmsList extends RecyclerView.Adapter<RecyclerCreateJourneyFilmsList.ViewHolder> {

    private static final String TAG = "RecyclerProfileListAdapter";

    private boolean[] checks;

    private ArrayList<Integer> idFilms = new ArrayList<>();
    private ArrayList<Integer> yearFilms = new ArrayList<>();
    private ArrayList<String> posterFilms = new ArrayList<>();
    private ArrayList<String> nameRusFilms = new ArrayList<>();
    private ArrayList<String> nameOrigFilms = new ArrayList<>();
    private ArrayList<String> genresFilms = new ArrayList<>();

    private Context mContext;

    private List<Integer> selectedItems;
    private List<String> selectedItemsNames;
    private int current_selected_idx = -1;

    private OnFilmListener mOnFilmListener = null;



    public RecyclerCreateJourneyFilmsList(Context context, ArrayList<Integer> idFilmsL, ArrayList<String> nameRusFilmsL,
                                          ArrayList<String> nameOrigFilmsL, ArrayList<String> genresFilmsL,
                                          ArrayList<String> posterFilmsL, ArrayList<Integer> yearFilmsL) {// OnFilmListener onFilmListener) {
        this.idFilms = idFilmsL;
        this.nameRusFilms = nameRusFilmsL;
        this.posterFilms = posterFilmsL;
        this.yearFilms = yearFilmsL;
        this.nameOrigFilms = nameOrigFilmsL;
        this.genresFilms = genresFilmsL;


        mContext = context;

        selectedItems = new ArrayList<Integer>();
        checks = new boolean[this.idFilms.size()];

        //selectedItems = new ArrayList<Integer> ();
    }

    @Override
    public RecyclerCreateJourneyFilmsList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jc_films_list_item, parent, false);
        return new ViewHolder(view, mOnFilmListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerCreateJourneyFilmsList.ViewHolder holder, final int position) {

        holder.tvCJFlNameRus.setText(nameRusFilms.get(position));
        holder.tvCJFlNameOrig.setText(nameOrigFilms.get(position));
        holder.tvCJFlGenres.setText(genresFilms.get(position));
        holder.tvCJFlYear.setText(String.valueOf(yearFilms.get(position)));

        //holder.parentCJFilmsCv.setActivated(selectedItems.get(position, false));

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_main_icon_foreground);

        Glide.with(mContext)
                .load(posterFilms.get(position))
                .apply(requestOptions)
                .into(holder.ivCJFlPoster);


        holder.parentCJFilmsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checkChecks(position);
                //if(checks[position]) {

                   // toggleCheckedIcon(holder, position);
                if (mOnFilmListener == null) return;
                mOnFilmListener.OnFilmClick(v, position);

            }

        });

        toggleCheckedIcon(holder, position);
    }


    private void checkChecks(int position) {

        notifyItemChanged(position); //обновляем. да, так надо, не знаю почему
        checks[position] = true; //меняем значение в массиве по позиции нажатого айтема
        notifyItemChanged(position);

    }

    private void toggleCheckedIcon(ViewHolder holder, int position) {

        if (!selectedItems.contains(idFilms.get(position))) {
            holder.rlItemChecked.setVisibility(View.GONE);
            holder.cjFilmPosterHolder.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        } else {
            holder.cjFilmPosterHolder.setVisibility(View.GONE);
            holder.rlItemChecked.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        }
    }


    public int getSelectedItemCount() {
        Log.e("количество фильмов ", "на экспорт "+String.valueOf(selectedItems.size()));
        return selectedItems.size();
    }

    public ArrayList<Integer> getSelectedItems() {
        ArrayList<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.get(i));
        }
        return items;
    }


    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    public void toggleSelection(int position) {
        current_selected_idx = position;
        if (selectedItems.contains(idFilms.get(position))){
            selectedItems.remove(idFilms.get(position));
            Log.e("удален фильм", nameRusFilms.get(position));
            Log.e("количество фильмов ", String.valueOf(selectedItems.size()));
        }
        else {

            selectedItems.add(idFilms.get(position));
            Log.e("добавлен фильм ", nameRusFilms.get(position));
            Log.e("количество фильмов ", String.valueOf(selectedItems.size()));
        }
        notifyItemChanged(position);
    }

    public void setOnClickListener(OnFilmListener onClickListener) {
        this.mOnFilmListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return idFilms.size();
    }

    public interface OnFilmListener {
        void OnFilmClick (View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener {

        TextView tvCJFlNameRus;
        TextView tvCJFlNameOrig;
        TextView tvCJFlYear;
        TextView tvCJFlGenres;
        CardView parentCJFilmsCv;
        CardView cjFilmPosterHolder;
        ImageView ivCJFlPoster;

        RelativeLayout rlItemChecked;

        OnFilmListener onFilmListener;

        public ViewHolder(View itemView, OnFilmListener onFilmListener) {
            super(itemView);
            tvCJFlNameRus = itemView.findViewById(R.id.tv_cj_fl_name_rus);
            rlItemChecked = itemView.findViewById(R.id.rl_film_checked);
            tvCJFlNameOrig = itemView.findViewById(R.id.tv_cj_fl_name_orig);
            tvCJFlYear = itemView.findViewById(R.id.tv_cj_fl_year);
            tvCJFlGenres = itemView.findViewById(R.id.tv_cj_fl_genres);
            ivCJFlPoster = itemView.findViewById(R.id.iv_cj_films_list_poster);
            cjFilmPosterHolder = itemView.findViewById(R.id.cv_cj_fliv);
            parentCJFilmsCv = itemView.findViewById(R.id.cv_cj_parent_films);

            this.onFilmListener = onFilmListener;

        }

    }
}
