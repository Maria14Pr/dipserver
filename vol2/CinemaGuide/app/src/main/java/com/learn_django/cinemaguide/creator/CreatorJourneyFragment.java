package com.learn_django.cinemaguide.creator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn_django.cinemaguide.MainMenuActivity;
import com.learn_django.cinemaguide.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class CreatorJourneyFragment extends Fragment implements StepperLayout.StepperListener{

    private StepperLayout mStepperLayout;
    private StepperAdapter mStepperAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainMenuActivity) getActivity()).setActionBarTitle("Создать путешествие");

        View creatorRootView = inflater.inflate(R.layout.fragment_journey_creator, null);

        mStepperLayout = creatorRootView.findViewById(R.id.stepperLayout);

        mStepperAdapter = new StepperAdapter(getFragmentManager(), getContext());
        mStepperLayout.setAdapter(mStepperAdapter);
        mStepperLayout.setListener(CreatorJourneyFragment.this);

        return creatorRootView;
    }


    @Override
    public void onCompleted(View completeButton) {

    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {

    }
}
