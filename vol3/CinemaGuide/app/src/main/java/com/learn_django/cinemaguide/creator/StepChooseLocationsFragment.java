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
import android.widget.TextView;

import com.learn_django.cinemaguide.InternetUtil;
import com.learn_django.cinemaguide.PostApi;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.model.FilmModel;
import com.learn_django.cinemaguide.model.LocationsByFilmModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StepChooseLocationsFragment extends Fragment{

    private Button btnToNextStep;
    private Button btnShowLocs;
    private ProgressBar progressBar;
    private TextView tvTitle;

    private String title_beg = "На предыдущем шаге Вы выбрали ";
    private String title_end = " фильм(-а/-ов)";

    private ArrayList<Integer> selectedFilmsId;

    private RecyclerView rvCjLocsList;

    private ArrayList<Integer> idLocs = new ArrayList<>();
    private ArrayList<Float> costLocs = new ArrayList<>();
    private ArrayList<Float> latLocs = new ArrayList<>();
    private ArrayList<Float> longLocs = new ArrayList<>();
    private ArrayList<String> currencyLocs = new ArrayList<>();
    private ArrayList<String> nameRusLocs = new ArrayList<>();
    private ArrayList<String> nameOrigLocs = new ArrayList<>();
    private ArrayList<String> countryLocs = new ArrayList<>();
    private ArrayList<String> photoLocs = new ArrayList<>();
    private ArrayList<Integer> durationLocs = new ArrayList<>();

    private RecyclerCreateJourneyLocsList mAdapter;

    private int countSelected = 0;

    //выбранные
    private ArrayList<Integer> idSelectedLocs = new ArrayList<>();
    private ArrayList<Integer> durationSelectedLocs = new ArrayList<>();
    private float [] latSelectedLocs;
    private float [] longSelectedLoc;
    private float [] costSelectedLoc;
    private ArrayList<String> curSelectedLocs = new ArrayList<>();
    private ArrayList<String> nameSelectedLocs = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View step3RootView = inflater.inflate(R.layout.fragment_step_three, container, false);

        rvCjLocsList = step3RootView.findViewById(R.id.rv_sthree_locs);

        btnToNextStep = step3RootView.findViewById(R.id.btn_jc_s3_next);
        progressBar = step3RootView.findViewById(R.id.jc_step3_progress);
        tvTitle = step3RootView.findViewById(R.id.choooose);
        progressBar.setProgress(37);
        btnShowLocs = step3RootView.findViewById(R.id.btn_s3_seelocs);
        final int[] dontshow = {0};

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedFilmsId = bundle.getIntegerArrayList("selected_films");
        }

        tvTitle.setText(title_beg+selectedFilmsId.size()+title_end);

        if ( InternetUtil.isInternetOnline(getActivity()) &&  selectedFilmsId.size()>0) {
            cjShowAllLocsWhereFilms();

            btnShowLocs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nameRusLocs.size()>0 && dontshow[0] != 1) {
                        Log.d("получено локаций ", String.valueOf(nameRusLocs.size()));
                        initRecyclerView();
                        dontshow[0] = 1;
                    }
                }
            });

            btnToNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countSelected = mAdapter.getSelectedItemCount();
                    Log.e("количество локаций ", "с импорта "+String.valueOf(countSelected));
                    idSelectedLocs = mAdapter.getSelectedItems();

                    if (countSelected>0) {

                        latSelectedLocs = new float[idSelectedLocs.size()];
                        longSelectedLoc = new float[idSelectedLocs.size()];
                        costSelectedLoc = new float[idSelectedLocs.size()];

                        int pos = -1;
                        for (int i=0; i < idSelectedLocs.size(); i++) {
                            pos = idLocs.indexOf(idSelectedLocs.get(i));
                            latSelectedLocs[i] = latLocs.get(pos);
                            longSelectedLoc[i] = longLocs.get(pos);
                            durationSelectedLocs.add(durationLocs.get(pos));
                            costSelectedLoc[i] = costLocs.get(pos);
                            curSelectedLocs.add(currencyLocs.get(pos));
                            nameSelectedLocs.add(nameRusLocs.get(pos));

                        }

                        Bundle bundle2 = new Bundle();
                        bundle2.putIntegerArrayList("selected_locations",  idSelectedLocs);
                        bundle2.putFloatArray("selected_locs_lat",  latSelectedLocs);
                        bundle2.putFloatArray("selected_locs_long",  longSelectedLoc);

                        bundle2.putIntegerArrayList("selected_locs_duration",  durationSelectedLocs);
                        bundle2.putFloatArray("selected_locs_cost",  costSelectedLoc);
                        bundle2.putStringArrayList("selected_locs_cur",  curSelectedLocs);
                        bundle2.putStringArrayList("selected_locs_names",  nameSelectedLocs);

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        StepRouteFragment myFragment = new StepRouteFragment();
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

        return step3RootView;
    }

    private void cjShowAllLocsWhereFilms() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi postApi= retrofit.create(PostApi.class);

        Call<List<LocationsByFilmModel>> call = postApi.getLocationsWhereFilms();


        call.enqueue(new Callback<List<LocationsByFilmModel>>() {
            @Override
            public void onResponse(Call<List<LocationsByFilmModel>> call, Response<List<LocationsByFilmModel>> response) {

                if(response.isSuccessful()){

                    if (response.body() != null) {

                        Log.d("response", "is Successful");
                        List<LocationsByFilmModel> locsWhereFilmList = response.body();

                        for(LocationsByFilmModel h:locsWhereFilmList){

                                Integer film_id = h.getLocFilmFilmId();
                                Integer loc_id = h.getLocFilmId();

                                if (selectedFilmsId.contains(film_id) && !idLocs.contains(loc_id)) {

                                    idLocs.add(loc_id);

                                    String rus_title = h.getLocFilmNameRus();
                                    nameRusLocs.add(rus_title);

                                    Log.d("added name ", rus_title);

                                    String orig_title = h.getLocFilmNameOrig();
                                    nameOrigLocs.add(orig_title);

                                    String country = h.getLocFilmCountry();
                                    countryLocs.add(country);

                                    String photo_loc = h.getLocFilmPhoto();
                                    photoLocs.add(photo_loc);

                                    String currency = h.getLocFilmCurrency();
                                    currencyLocs.add(currency);

                                    Float cost = h.getLocFilmCost();
                                    costLocs.add(cost);

                                    Float latLoc = h.getLocFilmLat();
                                    latLocs.add(latLoc);

                                    Float longLoc = h.getLocFilmLong();
                                    longLocs.add(longLoc);

                                    int duration = h.getLocFilmDuration();
                                    durationLocs.add(duration);
                                }

                            }

                        } else {
                            Log.e("response", "has null body");
                        }


                    }else {
                        Log.e("fail", "is not Successful");
                    }
                }

                @Override
                public void onFailure(Call<List<LocationsByFilmModel>> call, Throwable t) {
                    Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });

    }

    private void initRecyclerView(){
        Log.e("LocsStep", "initRecyclerView: init recyclerview.");
        rvCjLocsList.setLayoutManager(new LinearLayoutManager(getContext()));

        //set data and list adapter
        mAdapter = new RecyclerCreateJourneyLocsList(getContext(), idLocs, nameRusLocs, nameOrigLocs, countryLocs,
                photoLocs, costLocs, currencyLocs);// StepChosseFilmsFragment.this);

        rvCjLocsList.setAdapter(mAdapter);



        mAdapter.setOnClickListener(new RecyclerCreateJourneyLocsList.OnLocationListener() {
            @Override
            public void OnLocationClick(View view, int position) {
                mAdapter.toggleSelection(position);
                int count = mAdapter.getSelectedItemCount();
                Log.e("выбрано локаций", String.valueOf(count));
            }


        });

    }


}
