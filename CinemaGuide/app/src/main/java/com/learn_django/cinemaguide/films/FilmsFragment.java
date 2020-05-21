package com.learn_django.cinemaguide.films;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn_django.cinemaguide.InternetUtil;
import com.learn_django.cinemaguide.MainMenuActivity;
import com.learn_django.cinemaguide.PostApi;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.model.FilmModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmsFragment extends Fragment {

    private static final int NUM_COLUMNS = 2;

    private ArrayList<Integer> idFilms = new ArrayList<>();
    private ArrayList<Integer> yearFilms = new ArrayList<>();
    private ArrayList<String> posterFilms = new ArrayList<>();
    private ArrayList<String> nameRusFilms = new ArrayList<>();
    private RecyclerView recyclerViewFilms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setActionBarTitle("Фильмы");

        View filmsRootView = inflater.inflate(R.layout.fragment_films, container, false);
        recyclerViewFilms = filmsRootView.findViewById(R.id.recycler_films_list);

        Log.d("FilmsFragment", "---------start------------");

        if ( InternetUtil.isInternetOnline(getActivity()) ){
            ClearFilmsList();
            showAllFilms();
        }

        return filmsRootView;
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.imgbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Путешествия", Toast.LENGTH_SHORT).show();
            }
        });

    }
*/
    private void showAllFilms() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi postApi= retrofit.create(PostApi.class);
        Call<List<FilmModel>> call = postApi.getFilmsList();

        call.enqueue(new Callback<List<FilmModel>>() {
            @Override
            public void onResponse(Call<List<FilmModel>> call, Response<List<FilmModel>> response) {

                if(response.isSuccessful()){



                    if (response.body() != null) {

                        Log.d("response", "is Successful");
                        List<FilmModel> filmList = response.body();

                        for(FilmModel h:filmList){

                            Integer film_id = h.getId();
                            idFilms.add(film_id);

                            String rus_title = h.getRusTitle();
                            nameRusFilms.add(rus_title);


                            String poster_url = h.getFilmPoster();
                            posterFilms.add(poster_url);

                            Integer film_year = h.getFilmYear();
                            yearFilms.add(film_year);
                        }
                        initRecyclerView();
                    }

                }else {
                    Log.d("fail", "fail");
                }
            }

            @Override
            public void onFailure(Call<List<FilmModel>> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });
    }

    public void ClearFilmsList()
    {
        idFilms.clear();
        nameRusFilms.clear();
        posterFilms.clear();
        yearFilms.clear();

        RecyclerFilmsList adapter = new RecyclerFilmsList(getActivity(), idFilms, nameRusFilms, posterFilms, yearFilms);
        adapter.notifyDataSetChanged();
        recyclerViewFilms.setAdapter(adapter);
    }

    private void initRecyclerView(){
        Log.d("Films", "initRecyclerView: init recyclerview.");
        RecyclerFilmsList adapter = new RecyclerFilmsList(getActivity(), idFilms, nameRusFilms, posterFilms, yearFilms);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerViewFilms.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewFilms.setAdapter(adapter);
    }




    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }


}
