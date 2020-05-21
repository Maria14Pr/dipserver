package com.learn_django.cinemaguide.locations;

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

import com.learn_django.cinemaguide.model.LocationModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationsFragment extends Fragment {

    private static final int NUM_COLUMNS = 2;

    private ArrayList<Integer> idLocations = new ArrayList<>();
    private ArrayList<String> countryLocations = new ArrayList<>();
    private ArrayList<String> photoLocations = new ArrayList<>();
    private ArrayList<String> nameRusLocations = new ArrayList<>();
    private RecyclerView recyclerViewLocations;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setActionBarTitle("Локации");

        View locationsRootView = inflater.inflate(R.layout.fragment_locations, container, false);
        recyclerViewLocations = locationsRootView.findViewById(R.id.recycler_locations_list);

        Log.d("LocationsFragment", "---------start------------");

        if ( InternetUtil.isInternetOnline(getActivity()) ){
            ClearLocationsList();
            showAllLocations();
        }

        else {
            Log.e("loc", "no internet");
        }

        return locationsRootView;
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewLocations = view.findViewById(R.id.recycler_locations_list);
        view.findViewById(R.id.imgbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Путешествия", Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    private void showAllLocations() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi postApi= retrofit.create(PostApi.class);
        Call<List<LocationModel>> call = postApi.getLocationsList();

        call.enqueue(new Callback<List<LocationModel>>() {
            @Override
            public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {

                if(response.isSuccessful()){

                    if (response.body() != null) {

                        Log.d("response", "is Successful");
                        List<LocationModel> filmList = response.body();

                        for(LocationModel h:filmList){

                            Integer location_id = h.getId();
                            idLocations.add(location_id);
                            Log.e("response", String.valueOf(location_id));

                            String rus_title = h.getLocRusTitle();
                            nameRusLocations.add(rus_title);

                            String photo_url = h.getLocPhoto();
                            photoLocations.add(photo_url);

                            String location_fk_country = h.getLocCountry();
                            countryLocations.add(location_fk_country);
                            Log.e("response", location_fk_country);
                        }

                        initRecyclerView();
                    }

                }else {
                    Log.e("fail", "fail");
                }
            }

            @Override
            public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }
        });
    }

    public void ClearLocationsList()
    {
        idLocations.clear();
        nameRusLocations.clear();
        photoLocations.clear();
        countryLocations.clear();

        RecyclerLocationsList adapter = new RecyclerLocationsList(getActivity(), idLocations, nameRusLocations, photoLocations, countryLocations);
        adapter.notifyDataSetChanged();
        recyclerViewLocations.setAdapter(adapter);
    }

    private void initRecyclerView(){
        Log.d("Locations", "initRecyclerView: init recyclerview.");
        RecyclerLocationsList adapter = new RecyclerLocationsList(getActivity(), idLocations, nameRusLocations, photoLocations, countryLocations);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerViewLocations.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewLocations.setAdapter(adapter);
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
