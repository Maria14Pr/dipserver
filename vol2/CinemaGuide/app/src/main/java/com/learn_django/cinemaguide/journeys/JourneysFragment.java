package com.learn_django.cinemaguide.journeys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.learn_django.cinemaguide.MainMenuActivity;
import com.learn_django.cinemaguide.R;

public class JourneysFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setActionBarTitle("Путешествия");
        return inflater.inflate(R.layout.fragment_journeys, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       /*view.findViewById(R.id.imgbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Путешествия", Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}
