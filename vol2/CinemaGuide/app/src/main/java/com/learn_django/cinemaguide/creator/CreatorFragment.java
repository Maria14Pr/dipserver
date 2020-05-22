package com.learn_django.cinemaguide.creator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn_django.cinemaguide.MainActivity;
import com.learn_django.cinemaguide.MainMenuActivity;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.films.FilmItemFragment;

public class CreatorFragment extends Fragment {

    private CardView cvAddFilm;
    private CardView cvAddLocation;
    private CardView cvCreateJourney;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setActionBarTitle("Пополнить базу");
        View creatorRootView = inflater.inflate(R.layout.fragment_creator, null);

        cvAddFilm = creatorRootView.findViewById(R.id.cv_add_film);
        cvAddLocation = creatorRootView.findViewById(R.id.cv_add_loc);
        cvCreateJourney = creatorRootView.findViewById(R.id.cv_add_journey);


        cvCreateJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckLogin()) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Fragment creatorJourneyFragment = new CreatorJourneyFragment();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.screen_area, creatorJourneyFragment)
                            .addToBackStack(null)
                            .commit();
                }else {
                    Intent loginIntent = new Intent(getActivity(), MainActivity.class);
                    startActivity(loginIntent);
                }
            }
        });

        return creatorRootView;
    }

    public void replaceFragment(Fragment someFragment) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.screen_area, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public Boolean CheckLogin() {

        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Boolean shered_category_id = preferences.getBoolean("loggedin", false);

        Log.e("пользователь", String.valueOf(shered_category_id));

        if (shered_category_id) {
            return true;
        } else {
            return false;
        }
    }



}
