package com.learn_django.cinemaguide.creator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.learn_django.cinemaguide.CurrencyConverter;
import com.learn_django.cinemaguide.InternetUtil;
import com.learn_django.cinemaguide.R;

import java.util.ArrayList;

public class GetFullJourneyCostActivity extends AppCompatActivity {

    private String destinationCur;
    private ArrayList<Integer> solvedLocsId = new ArrayList<>();
    private ArrayList<String> solvedLocsName = new ArrayList<>();
    private float [] solvedLocCost;
    private ArrayList<String> solvedLocsCur = new ArrayList<>();

    private ArrayList<Float> locationsCostTo = new ArrayList<>();

    private Button btnSave;
    private RecyclerView rvConvertedCosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_full_journey_cost);

        btnSave = findViewById(R.id.btn_s5_get_costs);
        rvConvertedCosts = findViewById(R.id.rv_s5_converted_costs);
        getSupportActionBar().setElevation(0);

        Intent intent = getIntent();
        if (intent!=null) {

            destinationCur = intent.getStringExtra("dest_cur");
            solvedLocsId = intent.getIntegerArrayListExtra("locs_id");
            solvedLocsName = intent.getStringArrayListExtra("locs_names");
            solvedLocCost = intent.getFloatArrayExtra("locs_cost");
            solvedLocCost = intent.getFloatArrayExtra("locs_cost");
            solvedLocsCur = intent.getStringArrayListExtra("locs_cur");

        }

        if ( InternetUtil.isInternetOnline(GetFullJourneyCostActivity.this) ){
            showConvertedLocs();
        }



        Log.d("come from ", solvedLocsId +" "+destinationCur);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
            }
        });

    }

    public void showConvertedLocs (){

        CurrencyConverter currencyConverter;

        for (int i = 0; i<solvedLocsId.size(); i++) {

            currencyConverter = new CurrencyConverter(solvedLocsCur.get(i), destinationCur, solvedLocCost[i]);
            locationsCostTo.add(currencyConverter.convertToFrom());

        }


    }
}
