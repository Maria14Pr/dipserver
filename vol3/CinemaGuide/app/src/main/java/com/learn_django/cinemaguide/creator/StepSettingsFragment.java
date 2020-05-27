package com.learn_django.cinemaguide.creator;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.learn_django.cinemaguide.R;

import java.util.ArrayList;
import java.util.Calendar;

public class StepSettingsFragment extends Fragment{

    private Button btnToNextStep;
    private Button btnSetTime;
    private Button btnGetCost;
    private ProgressBar progressBar;
    private EditText etTime;
    private Spinner spinJourneyCurrency;

    private float [] solvedLocsLat;
    private float [] solvedLocsLong;
    private double [] solvedDistancesArray;

    private ArrayList<Integer> solvedLocsId = new ArrayList<>();

    private ArrayList<Integer> solvedLocsDuration = new ArrayList<>();
    private float [] solvedLocCost;
    private ArrayList<String> solvedLocsCur = new ArrayList<>();
    private ArrayList<String> solvedLocsName = new ArrayList<>();

    private Context mContext;
    private String currencyChosen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View step5RootView = inflater.inflate(R.layout.fragment_step_five, container, false);

        mContext = getContext();
        btnToNextStep = step5RootView.findViewById(R.id.btn_jc_s5_next);
        progressBar = step5RootView.findViewById(R.id.jc_step5_progress);
        progressBar.setProgress(62);
        btnSetTime = step5RootView.findViewById(R.id.btn_s5_set_time);
        btnGetCost = step5RootView.findViewById(R.id.btn_s5_get_costs);
        etTime = step5RootView.findViewById(R.id.et_jc_s5_time);
        spinJourneyCurrency = step5RootView.findViewById(R.id.spinner_jc_s5);

        etTime.setKeyListener(null);

        Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText(hourOfDay+":"+minute);
                    }
                },hour,minute, android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            solvedLocsId = bundle.getIntegerArrayList("solved_locations");
            solvedLocsLat = bundle.getFloatArray("solved_locs_lat");
            solvedLocsLong = bundle.getFloatArray("solved_locs_long");
            solvedLocsDuration = bundle.getIntegerArrayList("solved_locs_duration");
            solvedLocCost = bundle.getFloatArray("solved_locs_cost");
            solvedLocsCur = bundle.getStringArrayList("solved_locs_cur");
            solvedLocsName = bundle.getStringArrayList("solved_locs_names");
            solvedDistancesArray = bundle.getDoubleArray("solved_locs_distances");
            Log.e ("LocsId", String.valueOf(solvedLocsId));
        }

        btnGetCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrencyId();
                Intent intentGetFullCosts = new Intent(mContext, GetFullJourneyCostActivity.class);
                intentGetFullCosts.putExtra("dest_cur", currencyChosen);
                intentGetFullCosts.putExtra("locs_id", solvedLocsId);
                intentGetFullCosts.putExtra("locs_names", solvedLocsName);
                intentGetFullCosts.putExtra("locs_cost", solvedLocCost);
                intentGetFullCosts.putExtra("locs_cur", solvedLocsCur);
                startActivity(intentGetFullCosts);
            }
        });



        return step5RootView;
    }

    private void getCurrencyId() {
        String name_cur  = spinJourneyCurrency.getSelectedItem().toString();

        switch (name_cur) {
            case "Доллар США":
                currencyChosen = "USD";
                break;
            case "Российский рубль":
                currencyChosen = "RUB";
                break;
            case "Польский злотый":
                currencyChosen = "PLN";
                break;
            case "Фунт стерлингов":
                currencyChosen = "GBP";
                break;
            case "Евро":
                currencyChosen = "EUR";
                break;
            case "Чешская крона":
                currencyChosen = "CZK";
                break;
        }

    }




}
