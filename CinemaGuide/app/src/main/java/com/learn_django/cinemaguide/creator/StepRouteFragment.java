package com.learn_django.cinemaguide.creator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn_django.cinemaguide.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class StepRouteFragment extends Fragment implements Step{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View step3RootView = inflater.inflate(R.layout.fragment_step_three, container, false);

        return step3RootView;
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
}
