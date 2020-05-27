package com.learn_django.cinemaguide.creator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.learn_django.cinemaguide.InternetUtil;
import com.learn_django.cinemaguide.PostApi;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.films.RecyclerFilmsList;
import com.learn_django.cinemaguide.model.FilmModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StepChosseFilmsFragment extends Fragment{

    private Button btnToNextStep;
    private ProgressBar progressBar;

    private RecyclerView rvCjFilmsList;

    private ArrayList<Integer> idSelectedFilms = new ArrayList<>();

    private ArrayList<Integer> idFilms = new ArrayList<>();
    private ArrayList<Integer> yearFilms = new ArrayList<>();
    private ArrayList<String> posterFilms = new ArrayList<>();
    private ArrayList<String> nameRusFilms = new ArrayList<>();
    private ArrayList<String> nameOrigFilms = new ArrayList<>();
    private ArrayList<String> genresFilms = new ArrayList<>();

    private int countSelected = 0;
    private int createdJourId = 0;

    private RecyclerCreateJourneyFilmsList mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View step2RootView = inflater.inflate(R.layout.fragment_step_two, container, false);

        rvCjFilmsList = step2RootView.findViewById(R.id.rv_sone_films);
        btnToNextStep = step2RootView.findViewById(R.id.btn_jc_s2_next);
        progressBar = step2RootView.findViewById(R.id.jc_step2_progress);
        progressBar.setProgress(25);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            createdJourId = bundle.getInt("cretedj_id");
        }

        if ( InternetUtil.isInternetOnline(getActivity()) ){
            ClearCjFilmsList();
            cjShowAllFilms();

            btnToNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countSelected = mAdapter.getSelectedItemCount();
                    Log.e("количество фильмов ", "с импорта "+String.valueOf(countSelected));
                    idSelectedFilms = mAdapter.getSelectedItems();

                    if (countSelected>0) {
                        Bundle bundle2 = new Bundle();
                        //bundle2.putInt("count_films",countSelected); // Put anything what you want
                        bundle2.putIntegerArrayList("selected_films",  idSelectedFilms);

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        StepChooseLocationsFragment myFragment = new StepChooseLocationsFragment();
                        myFragment.setArguments(bundle2);
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.step_area, myFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        }

        return step2RootView;
    }

    public void ClearCjFilmsList()
    {
        idFilms.clear();
        nameRusFilms.clear();
        posterFilms.clear();
        yearFilms.clear();
        nameOrigFilms.clear();
        genresFilms.clear();

        RecyclerFilmsList adapter = new RecyclerFilmsList(getActivity(), idFilms, nameRusFilms, posterFilms, yearFilms);
        adapter.notifyDataSetChanged();
        rvCjFilmsList.setAdapter(adapter);
    }

    private void cjShowAllFilms() {

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

                            String orig_title = h.getOrigTitle();
                            nameOrigFilms.add(orig_title);

                            String genres = h.getFilmGenres();
                            genresFilms.add(genres);

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



    private void initRecyclerView(){
        Log.e("FilmsStep", "initRecyclerView: init recyclerview.");
        rvCjFilmsList.setLayoutManager(new LinearLayoutManager(getContext()));

        //set data and list adapter
        mAdapter = new RecyclerCreateJourneyFilmsList(getContext(), idFilms, nameRusFilms, nameOrigFilms, genresFilms,
                posterFilms, yearFilms);// StepChosseFilmsFragment.this);

        rvCjFilmsList.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new RecyclerCreateJourneyFilmsList.OnFilmListener() {
            @Override
            public void OnFilmClick(View view, int position) {

                mAdapter.toggleSelection(position);
                int count = mAdapter.getSelectedItemCount();
                Log.e("выбрано фильмов", String.valueOf(count));

            }
        });

    }

}
