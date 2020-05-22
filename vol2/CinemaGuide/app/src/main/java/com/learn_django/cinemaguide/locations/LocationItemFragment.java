package com.learn_django.cinemaguide.locations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn_django.cinemaguide.MainMenuActivity;
import com.learn_django.cinemaguide.PostApi;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.model.FilmModel;
import com.learn_django.cinemaguide.model.LocationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationItemFragment extends Fragment {

    private TextView tvLocationItemTitleRus;
    private TextView tvLocationItemCountry;
    private TextView tvLocationItemTime;
    private TextView tvLocationItemCost;
    private TextView tvLocationItemCurrency;
    private ImageView ivLocationItemPhoto;

    private int locItemId;
    private String locItemPhotoUrl;
    private String locOperMode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setActionBarTitle("Локация");

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Integer bundle_id = bundle.getInt("location_id");
            Log.d("data is ", String.valueOf(bundle_id));
            GetLocFromServer(bundle_id);
        }

        return inflater.inflate(R.layout.fragment_location_item, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvLocationItemTitleRus = view.findViewById(R.id.tv_li_name);
        tvLocationItemCountry = view.findViewById(R.id.tv_li_country);
        tvLocationItemCost = view.findViewById(R.id.tv_li_visit_cost);
        tvLocationItemCurrency = view.findViewById(R.id.tv_li_currency);
        tvLocationItemTime = view.findViewById(R.id.tv_li_fromotoc);
        ivLocationItemPhoto = view.findViewById(R.id.iv_li_photo);


    }

    private void GetLocFromServer(Integer getted_id) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String data = String.valueOf(getted_id);
        Log.d("data is ", data);
        PostApi postApi= retrofit.create(PostApi.class);
        Call<LocationModel> call = postApi.getLocation(data);

        call.enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {

                if(response.isSuccessful()){
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        LocationModel locItem = response.body();

                        Integer loc_id = locItem.getId();
                        locItemId = loc_id;

                        String rus_title = locItem.getLocRusTitle();
                        tvLocationItemTitleRus.setText(rus_title);

                        String country = locItem.getLocCountry();
                        tvLocationItemCountry.setText(country);

                        String currency = " "+locItem.getLocCurrency();
                        tvLocationItemCurrency.setText(currency);

                        String open_time = locItem.getLocation_opening_time();
                        String close_time = locItem.getLocation_close_time();
                        locOperMode=open_time.substring(0,5)+"-"+close_time.substring(0,5);
                        tvLocationItemTime.setText(locOperMode);

                        float loc_cost = locItem.getLocation_cost();
                        tvLocationItemCost.setText(String.valueOf(loc_cost));

                        String photo_url = locItem.getLocPhoto();
                        locItemPhotoUrl= photo_url;

                        if (locItemPhotoUrl!=null){
                            RequestOptions requestOptions = new RequestOptions()
                                    .placeholder(R.drawable.ic_main_icon_foreground);

                            Glide.with(getContext())
                                    .load(locItemPhotoUrl)
                                    .apply(requestOptions)
                                    .into(ivLocationItemPhoto);
                        }

                    }

                }else {
                    Log.d("fail", "fail");
                }
            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                Log.d("fail", t.getMessage() == null ? "" : t.getMessage());
            }

        });
    }
}
