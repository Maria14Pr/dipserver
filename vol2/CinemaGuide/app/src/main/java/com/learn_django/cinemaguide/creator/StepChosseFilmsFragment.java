package com.learn_django.cinemaguide.creator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.learn_django.cinemaguide.InternetUtil;
import com.learn_django.cinemaguide.PostApi;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.films.RecyclerFilmsList;
import com.learn_django.cinemaguide.model.FilmModel;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StepChosseFilmsFragment extends Fragment implements BlockingStep {

    private RecyclerView rvCjFilmsList;

    private List<Integer> idSelectedFilms = new ArrayList<>();

    private ArrayList<Integer> idFilms = new ArrayList<>();
    private ArrayList<Integer> yearFilms = new ArrayList<>();
    private ArrayList<String> posterFilms = new ArrayList<>();
    private ArrayList<String> nameRusFilms = new ArrayList<>();
    private ArrayList<String> nameOrigFilms = new ArrayList<>();
    private ArrayList<String> genresFilms = new ArrayList<>();

    private int countSelected = 0;

    private RecyclerCreateJourneyFilmsList mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View step2RootView = inflater.inflate(R.layout.fragment_step_two, container, false);

        rvCjFilmsList = step2RootView.findViewById(R.id.rv_sone_films);

        if ( InternetUtil.isInternetOnline(getActivity()) ){
            ClearCjFilmsList();
            cjShowAllFilms();
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
        Log.d("Films", "initRecyclerView: init recyclerview.");
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



    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

        countSelected = mAdapter.getSelectedItemCount();
        Log.e("количество фильмов ", "с импорта "+String.valueOf(countSelected));
        idSelectedFilms = mAdapter.getSelectedItems();
        Toast.makeText(getContext(), "Read: "+ countSelected, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }


    private void toggleSelection(int position) {

        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

    }
}
