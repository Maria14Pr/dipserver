package com.learn_django.cinemaguide.creator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.learn_django.cinemaguide.CurrencyApi;
import com.learn_django.cinemaguide.InternetUtil;
import com.learn_django.cinemaguide.R;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUR;
import com.learn_django.cinemaguide.solving_problems.SalesmanProblem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StepRouteFragment extends Fragment{

    private Button btnToNextStep;
    private ProgressBar progressBar;
    private TextView header;

    private ArrayList<Integer> selectedLocsId = new ArrayList<>();
    private float [] selectedLocsLat;
    private float [] selectedLocsLong;
    private ArrayList<Integer> selectedLocsDuration = new ArrayList<>();
    private float [] selectedLocCost;
    private ArrayList<String> selectedLocsCur = new ArrayList<>();
    private ArrayList<String> selectedLocsName = new ArrayList<>();

    private float [] solvedLocsLat;
    private float [] solvedLocsLong;
    private double [] solvedDistancesArray;

    private ArrayList<Integer> solvedLocsId = new ArrayList<>();
    private ArrayList<Double> solvedDistances = new ArrayList<>();

    private ArrayList<Integer> solvedLocsDuration = new ArrayList<>();
    private float [] solvedLocCost;
    private ArrayList<String> solvedLocsCur = new ArrayList<>();
    private ArrayList<String> solvedLocsName = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View step4RootView = inflater.inflate(R.layout.fragment_step_four, container, false);

        btnToNextStep = step4RootView.findViewById(R.id.btn_jc_s4_next);
        progressBar = step4RootView.findViewById(R.id.jc_step4_progress);
        progressBar.setProgress(50);
        header = step4RootView.findViewById(R.id.sfour_header);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            selectedLocsId = bundle.getIntegerArrayList("selected_locations");
            selectedLocsLat = bundle.getFloatArray("selected_locs_lat");
            selectedLocsLong = bundle.getFloatArray("selected_locs_long");

            selectedLocsDuration = bundle.getIntegerArrayList("selected_locs_duration");
            selectedLocCost = bundle.getFloatArray("selected_locs_cost");
            selectedLocsCur = bundle.getStringArrayList("selected_locs_cur");
            selectedLocsName = bundle.getStringArrayList("selected_locs_names");
            Log.e ("LocsId", String.valueOf(selectedLocsId));
        }

        if (selectedLocsId.size()>0 && selectedLocsLat.length > 0 && selectedLocsLong.length>0) {

            SalesmanProblem salesmanProblem = new SalesmanProblem(selectedLocsId, selectedLocsLat, selectedLocsLong);
            solvedLocsId = salesmanProblem.getSolvedFinalLocsId();
            solvedLocsLat = salesmanProblem.getSolvedFinalLat();
            solvedLocsLong = salesmanProblem.getSolvedFinalLong();
            solvedDistances = salesmanProblem.getDistancesFinal();

            double distanceSum = 0;
            for (double el:solvedDistances) {
                distanceSum+=el;
            }

            //String solution = routedLocsId + " в метрах " + salesmanProblem.getSolvedDistence();
            header.setText("solution: "+String.valueOf(solvedLocsId) + "length, km: "+distanceSum);
        }

        else {
            Log.e ("LocsId", String.valueOf(selectedLocsId));
            Log.e ("LocsLat", String.valueOf(selectedLocsLat));
            Log.e ("LocsLong", String.valueOf(selectedLocsLong));
        }

        btnToNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] responseAnswer = {"resp"};


                if ( InternetUtil.isInternetOnline(getActivity()) ) {

                    /*try {

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(CurrencyApi.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        CurrencyApi currencyApi= retrofit.create(CurrencyApi.class);
                        Call<CurrencyModelUR> call = currencyApi.convertFromUsdToRub();

                        call.enqueue(new Callback<CurrencyModelUR>() {

                            @Override
                            public void onResponse(Call<CurrencyModelUR> call, Response<CurrencyModelUR> response) {
                                if(response.isSuccessful()){
                                    responseAnswer[0] = String.valueOf(response.body());

                                }
                                if(response.isSuccessful()) {
                                    Log.d("response", "is Successful");

                                    if (response.body() != null) {

                                        Log.d("response", "body");
                                        CurrencyModelUR curItem = response.body();

                                        responseAnswer[0] = String.valueOf( curItem.getResult());

                                        Toast.makeText(getContext(), "USD_PHP " + responseAnswer[0], Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<CurrencyModelUR> call, Throwable t) {
                                Log.e("resp", t.getMessage());
                            }
                        });

                    } catch (Exception e) {
                        Log.e("resp", e.getMessage());
                    }*/

                    int pos = -1;
                    solvedDistancesArray = new double[solvedDistances.size()];
                    solvedLocCost = new float[solvedLocsId.size()];
                    for (int i=0; i < solvedLocsId.size(); i++) {
                        pos = selectedLocsId.indexOf(solvedLocsId.get(i));
                        solvedLocCost[i] = selectedLocCost[pos];
                        solvedLocsCur.add(selectedLocsCur.get(pos));
                        solvedLocsDuration.add(selectedLocsDuration.get(pos));
                        solvedLocsName.add(selectedLocsName.get(pos));
                        if (i+1<solvedLocsId.size()) {
                            solvedDistancesArray[i] = solvedDistances.get(i);
                        }
                    }

                    Bundle bundle2 = new Bundle();
                    bundle2.putIntegerArrayList("solved_locations",  solvedLocsId);
                    bundle2.putFloatArray("solved_locs_lat",  solvedLocsLat);
                    bundle2.putFloatArray("solved_locs_long",  solvedLocsLong);

                    bundle2.putIntegerArrayList("solved_locs_duration",  solvedLocsDuration);
                    bundle2.putFloatArray("solved_locs_cost",  solvedLocCost);
                    bundle2.putStringArrayList("solved_locs_cur",  solvedLocsCur);
                    bundle2.putStringArrayList("solved_locs_names",  solvedLocsName);
                    bundle2.putDoubleArray("solved_locs_distances",  solvedDistancesArray);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    StepSettingsFragment myFragment = new StepSettingsFragment();
                    myFragment.setArguments(bundle2);
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.step_area, myFragment)
                            .addToBackStack(null)
                            .commit();

                }


            }
        });



        return step4RootView;
    }
}
