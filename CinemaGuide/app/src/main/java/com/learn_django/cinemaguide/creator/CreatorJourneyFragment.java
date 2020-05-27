package com.learn_django.cinemaguide.creator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn_django.cinemaguide.MainMenuActivity;
import com.learn_django.cinemaguide.R;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setActionBarTitle("Создать путешествие");

        View creatorRootView = inflater.inflate(R.layout.fragment_journey_creator, null);

        AppCompatActivity activity = (AppCompatActivity) getContext();
        Fragment myFragment = new StepInitializeJourneyFragment();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.step_area, myFragment)
                .addToBackStack(null)
                .commit();



        return creatorRootView;
    }

}
