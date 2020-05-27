package com.learn_django.cinemaguide.creator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.learn_django.cinemaguide.InternetUtil;
import com.learn_django.cinemaguide.PostApi;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.SharedDataGetSet;
import com.learn_django.cinemaguide.model.JourneyAddModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StepInitializeJourneyFragment extends Fragment{

    private Button btnToNextStep;
    private EditText etJourneyName;
    private EditText etJourneyDesc;
    private ProgressBar progressBar;
    private Spinner spinJourneyIntensity;
    private int createdJourneyId;

    public static int intensityHours = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View step1RootView = inflater.inflate(R.layout.fragment_step_one, null);
        etJourneyName = step1RootView.findViewById(R.id.et_jour_s1_name);
        btnToNextStep = step1RootView.findViewById(R.id.btn_jc_s1_next);
        etJourneyDesc = step1RootView.findViewById(R.id.et_jour_s1_desc);
        spinJourneyIntensity = step1RootView.findViewById(R.id.spinner_jour_s1);
        progressBar = step1RootView.findViewById(R.id.jc_step1_progress);
        progressBar.setProgress(12);


        btnToNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsEmptyEditData()){
                    if ( InternetUtil.isInternetOnline(getActivity()) ){

                        if (createdJourneyId==0) {
                            SaveJourney();
                        }

                        else {
                            Log.d("id is ", "created "+createdJourneyId);
                            Bundle bundle = new Bundle();
                            bundle.putInt("cretedj_id",createdJourneyId); // Put anything what you want

                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            StepChosseFilmsFragment myFragment = new StepChosseFilmsFragment();
                            myFragment.setArguments(bundle);
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.step_area, myFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }



                    }
                }
            }
        });

        return step1RootView;

    }


    public void SaveJourney(){

        String add_title = etJourneyName.getText().toString();
        int id = 0;
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
                JourneyAddModel createdJourney = response.body();
                createdJourneyId = createdJourney.getId();
                Log.e("createdJourneyId", String.valueOf(createdJourneyId));

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
