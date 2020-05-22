package com.learn_django.cinemaguide.creator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.learn_django.cinemaguide.InternetUtil;
import com.learn_django.cinemaguide.PostApi;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.SharedDataGetSet;
import com.learn_django.cinemaguide.model.JourneyAddModel;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StepInitializeJourneyFragment extends Fragment implements BlockingStep {

    private EditText etJourneyName;
    private EditText etJourneyDesc;
    private Spinner spinJourneyIntensity;

    public static int intensityHours = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View step1RootView = inflater.inflate(R.layout.fragment_step_one, null);
        etJourneyName = step1RootView.findViewById(R.id.et_jour_s1_name);
        etJourneyDesc = step1RootView.findViewById(R.id.et_jour_s1_desc);
        spinJourneyIntensity = step1RootView.findViewById(R.id.spinner_jour_s1);
        return step1RootView;
    }


    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        if (!IsEmptyEditData()){
            if ( InternetUtil.isInternetOnline(getActivity()) ){
                SaveJourney();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //you can do anythings you want
                        callback.goToNextStep();
                    }
                }, 0L);// delay open another fragment,

            }
        }
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

    public void SaveJourney(){

        String add_title = etJourneyName.getText().toString();
        String add_desc  = etJourneyDesc.getText().toString();
        String add_intens  = spinJourneyIntensity.getSelectedItem().toString();

        switch (add_intens) {
            case "низкая":
                intensityHours = 7;
                break;
            case "средняя":
                intensityHours = 10;
                break;
            case "высокая":
                intensityHours = 12;
                break;
        }

        JourneyAddModel journeyAddModel = new JourneyAddModel( add_title, add_desc, add_intens);

        if ( InternetUtil.isInternetOnline(getActivity()) ){
            AddJournyServer(journeyAddModel);
        }
    }

    public void AddJournyServer(JourneyAddModel postModelModel) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.POST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String queryToken_ap = SharedDataGetSet.getMySavedToken(getActivity());
        PostApi postApi= retrofit.create(PostApi.class);
        Call<JourneyAddModel> call = postApi.addJourney(queryToken_ap, postModelModel);

        call.enqueue(new Callback<JourneyAddModel>() {
            @Override
            public void onResponse(Call<JourneyAddModel> call, Response<JourneyAddModel> response) {
                Log.d("good", "good");
            }
            @Override
            public void onFailure(Call<JourneyAddModel> call, Throwable t) {
                Log.d("fail", "fail");
            }
        });
    }

    private Boolean IsEmptyEditData(){

        if(etJourneyName.getText().toString().isEmpty() || etJourneyDesc.getText().toString().isEmpty()){

            Toast toast = Toast.makeText(getActivity(),"Заполните параметры", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return true;
        }else{
            return false;
        }
    }

}
