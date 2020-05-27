package com.learn_django.cinemaguide.creator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.learn_django.cinemaguide.R;

public class StepSettingsFragment extends Fragment{

    private Button btnToNextStep;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View step5RootView = inflater.inflate(R.layout.fragment_step_five, container, false);

        btnToNextStep = step5RootView.findViewById(R.id.btn_jc_s5_next);
        progressBar = step5RootView.findViewById(R.id.jc_step5_progress);
        progressBar.setProgress(62);

        return step5RootView;
    }

}
