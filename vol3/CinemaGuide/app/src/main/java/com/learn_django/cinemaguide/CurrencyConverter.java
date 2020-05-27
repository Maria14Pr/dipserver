package com.learn_django.cinemaguide;

import android.util.Log;

import com.learn_django.cinemaguide.model.currencies.CurrencyModelRC;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRE;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRG;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRP;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRU;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUC;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUE;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUG;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUP;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUR;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyConverter {

    private String currencyIdFrom;
    private String currencyIdTo;
    private float costBefore;
    private float costAfter;
    private  ArrayList <Float> currency_today_course;

    public CurrencyConverter(String currencyFrom, String currencyTo, float cost){

        currencyIdFrom = currencyFrom;
        currencyIdTo = currencyTo;
        costBefore = cost;

    }

    public float convertToFrom() {

        float course = 0;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CurrencyApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CurrencyApi currencyApi= retrofit.create(CurrencyApi.class);

        switch (currencyIdTo) {
            case "USD":
                switch (currencyIdFrom) {
                    case "USD":
                        course = 1;
                        break;
                    case "RUB":
                        course = convertFromUsdToRub(currencyApi);
                        break;
                    case "PLN":
                        course = convertFromUsdToPln(currencyApi);
                        break;
                    case "GBP":
                        course = convertFromUsdToGbp(currencyApi);
                        break;
                    case "EUR":
                        course = convertFromUsdToEur(currencyApi);
                        break;
                    case "CZK":
                        course = convertFromUsdToCzk(currencyApi);
                        break;
                }
                break;
            case "RUB":
                switch (currencyIdFrom) {
                    case "USD":
                        course = convertFromRubToUsd(currencyApi);
                        break;
                    case "RUB":
                        course = 1;
                        break;
                    case "PLN":
                        course = convertFromRubToPln(currencyApi);
                        break;
                    case "GBP":
                        course = convertFromRubToGbp(currencyApi);
                        break;
                    case "EUR":
                        course = convertFromRubToEur(currencyApi);
                        break;
                    case "CZK":
                        course = convertFromRubToCzk(currencyApi);
                        break;
                }
                break;

            case "PLN":
                switch (currencyIdFrom) {
                    case "USD":
                        costAfter = 1;
                        break;
                    case "RUB":
                        costAfter = 1;

                        break;
                    case "PLN":
                        course = 1;
                        break;
                    case "GBP":
                        costAfter = 1;

                        break;
                    case "EUR":
                        costAfter = 1;
                        break;
                    case "CZK":
                        costAfter = 1;
                        break;
                }

                break;
            case "GBP":
                costAfter = 1;

                switch (currencyIdFrom) {
                    case "USD":
                        costAfter = 1;
                        break;
                    case "RUB":
                        costAfter = 1;

                        break;
                    case "PLN":
                        costAfter = 1;

                        break;
                    case "GBP":
                        course = 1;
                        break;
                    case "EUR":
                        costAfter = 1;
                        break;
                    case "CZK":
                        costAfter = 1;
                        break;
                }

                break;
            case "EUR":

                switch (currencyIdFrom) {
                    case "USD":
                        costAfter = 1;
                        break;
                    case "RUB":
                        costAfter = 1;

                        break;
                    case "PLN":
                        costAfter = 1;

                        break;
                    case "GBP":
                        costAfter = 1;

                        break;
                    case "EUR":
                        course = 1;
                        break;
                    case "CZK":
                        costAfter = 1;
                        break;
                }

                break;
            case "CZK":

                switch (currencyIdFrom) {
                    case "USD":
                        costAfter = 1;
                        break;
                    case "RUB":
                        costAfter = 1;

                        break;
                    case "PLN":
                        costAfter = 1;

                        break;
                    case "GBP":
                        costAfter = 1;

                        break;
                    case "EUR":
                        costAfter = 1;
                        break;
                    case "CZK":
                        course = 1;
                        break;
                }

                break;
        }
        costAfter = course*costBefore;

        return costAfter;

    }

    private float convertFromUsdToRub(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelUR> call = currencyApi.convertFromUsdToRub();

        call.enqueue(new Callback<CurrencyModelUR>() {

            @Override
            public void onResponse(Call<CurrencyModelUR> call, Response<CurrencyModelUR> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelUR curItem = response.body();

                        result[0] = (float)curItem.getResult();

                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelUR> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });

        return result[0];
    }

    private float convertFromUsdToPln(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelUP> call = currencyApi.convertFromUsdToPln();

        call.enqueue(new Callback<CurrencyModelUP>() {

            @Override
            public void onResponse(Call<CurrencyModelUP> call, Response<CurrencyModelUP> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelUP curItem = response.body();

                        result[0] = (float)curItem.getResult();

                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelUP> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });

        return result[0];
    }

    private float convertFromUsdToGbp(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelUG> call = currencyApi.convertFromUsdToGbp();

        call.enqueue(new Callback<CurrencyModelUG>() {

            @Override
            public void onResponse(Call<CurrencyModelUG> call, Response<CurrencyModelUG> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelUG curItem = response.body();

                        result[0] = (float)curItem.getResult();
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelUG> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });

        return result[0];
    }

    private float convertFromUsdToEur(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelUE> call = currencyApi.convertFromUsdToEur();

        call.enqueue(new Callback<CurrencyModelUE>() {

            @Override
            public void onResponse(Call<CurrencyModelUE> call, Response<CurrencyModelUE> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelUE curItem = response.body();

                        result[0] = (float)curItem.getResult();
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelUE> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });

        return result[0];
    }

    private float convertFromUsdToCzk(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelUC> call = currencyApi.convertFromUsdToCzk();

        call.enqueue(new Callback<CurrencyModelUC>() {

            @Override
            public void onResponse(Call<CurrencyModelUC> call, Response<CurrencyModelUC> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelUC curItem = response.body();

                        result[0] = (float)curItem.getResult();
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelUC> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });

        return result[0];
    }

    private float convertFromRubToUsd(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelRU> call = currencyApi.convertFromRubToUsd();

        call.enqueue(new Callback<CurrencyModelRU>() {

            @Override
            public void onResponse(Call<CurrencyModelRU> call, Response<CurrencyModelRU> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelRU curItem = response.body();

                        result[0] = (float)curItem.getResult();

                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelRU> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });
        return result[0];
    }

    private float convertFromRubToPln(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelRP> call = currencyApi.convertFromRubToPln();

        call.enqueue(new Callback<CurrencyModelRP>() {

            @Override
            public void onResponse(Call<CurrencyModelRP> call, Response<CurrencyModelRP> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelRP curItem = response.body();

                        result[0] = (float)curItem.getResult();

                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelRP> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });
        return result[0];
    }

    private float convertFromRubToGbp(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelRG> call = currencyApi.convertFromRubToGbp();

        call.enqueue(new Callback<CurrencyModelRG>() {

            @Override
            public void onResponse(Call<CurrencyModelRG> call, Response<CurrencyModelRG> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelRG curItem = response.body();

                        result[0] = (float)curItem.getResult();

                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelRG> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });
        return result[0];
    }

    private float convertFromRubToCzk(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelRC> call = currencyApi.convertFromRubToCZK();

        call.enqueue(new Callback<CurrencyModelRC>() {

            @Override
            public void onResponse(Call<CurrencyModelRC> call, Response<CurrencyModelRC> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelRC curItem = response.body();

                        result[0] = (float)curItem.getResult();

                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelRC> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });
        return result[0];
    }

    private float convertFromRubToEur(CurrencyApi currencyApi) {
        final float[] result = {0};

        Call<CurrencyModelRE> call = currencyApi.convertFromRubToEUR();

        call.enqueue(new Callback<CurrencyModelRE>() {

            @Override
            public void onResponse(Call<CurrencyModelRE> call, Response<CurrencyModelRE> response) {

                if(response.isSuccessful()) {
                    Log.d("response", "is Successful");

                    if (response.body() != null) {

                        Log.d("response", "body");
                        CurrencyModelRE curItem = response.body();

                        result[0] = (float)curItem.getResult();

                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyModelRE> call, Throwable t) {
                Log.e("resp", t.getMessage());
            }
        });
        return result[0];
    }

}
