package com.learn_django.cinemaguide.films;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn_django.cinemaguide.MainMenuActivity;
import com.learn_django.cinemaguide.PostApi;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.model.FilmModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmItemFragment extends Fragment {

    private TextView tvFilmItemTitleRus;
    private TextView tvFilmItemTitleOrig;
    private TextView tvFilmItemGenres;
    private TextView tvFilmItemYear;
    private TextView tvFilmItemDesc;
    private ImageView ivFilmItemPoster;
    private ImageView ivKpButton;

    private String kpUrl;
    private String posterUrl;
    private int filmItemId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setActionBarTitle("Фильм");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Integer bundle_id = bundle.getInt("film_id");
            GetServerData(bundle_id);
        }

        return inflater.inflate(R.layout.fragment_film_item, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        tvFilmItemTitleRus = view.findViewById(R.id.tv_fi_rus_title);
        tvFilmItemTitleOrig = view.findViewById(R.id.tv_fi_orig_title);
        tvFilmItemGenres = view.findViewById(R.id.tv_fi_genres);
        tvFilmItemYear = view.findViewById(R.id.tv_fi_year);
        tvFilmItemDesc = view.findViewById(R.id.tv_fi_desc);
        ivFilmItemPoster = view.findViewById(R.id.fi_poster);
        ivKpButton = view.findViewById(R.id.kp_btn);

    }

    private void GetServerData(Integer getted_id) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String data = String.valueOf(getted_id);
        Log.d("data is ", data);
        PostApi postApi= retrofit.create(PostApi.class);
        Call<FilmModel> call = postApi.getFilm(data);

        call.enqueue(new Callback<FilmModel>() {
            @Override
            public void onResponse(Call<FilmModel> call, Response<FilmModel> response) {

                if(response.isSuccessful()){
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        FilmModel filmItem = response.body();

                        Integer film_id = filmItem.getId();
                        filmItemId = film_id;

                        String rus_title = filmItem.getRusTitle();
                        tvFilmItemTitleRus.setText(rus_title);

                        String orig_title = filmItem.getOrigTitle();
                        tvFilmItemTitleOrig.setText(orig_title);

                        String desc = filmItem.getFilmDesc();
                        tvFilmItemDesc.setText(desc);


                        String poster_url = filmItem.getFilmPoster();
                        posterUrl= poster_url;

                        Integer film_year = filmItem.getFilmYear();
                        tvFilmItemYear.setText(String.valueOf(film_year));

                        if (posterUrl!=null){
                            RequestOptions requestOptions = new RequestOptions()
                                .placeholder(R.drawable.ic_main_icon_foreground);

                        Glide.with(getContext())
                                .load(posterUrl)
                                .apply(requestOptions)
                                .into(ivFilmItemPoster);
                        }

                        String film_genres = filmItem.getFilmGenres();
                        tvFilmItemGenres.setText(film_genres);
                        Log.d("genres", film_genres);

                    }

                }else {
                    Log.d("fail", "fail");
                }
            }

            @Override
            public void onFailure(Call<FilmModel> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }

        });
    }
}
