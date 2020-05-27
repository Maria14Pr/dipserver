package com.learn_django.cinemaguide;

import retrofit2.Call;
import retrofit2.http.GET;

//US dollar
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUR;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUP;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUG;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUE;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelUC;

//Russian ruble
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRU;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRP;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRG;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRE;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelRC;

//Polish zloty
import com.learn_django.cinemaguide.model.currencies.CurrencyModelPU;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelPR;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelPG;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelPE;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelPC;

//Pound sterling
import com.learn_django.cinemaguide.model.currencies.CurrencyModelGU;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelGP;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelGR;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelGE;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelGC;

//Euro
import com.learn_django.cinemaguide.model.currencies.CurrencyModelEC;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelEU;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelEP;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelEG;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelER;

//Czech koruna
import com.learn_django.cinemaguide.model.currencies.CurrencyModelCE;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelCU;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelCP;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelCG;
import com.learn_django.cinemaguide.model.currencies.CurrencyModelCR;


/**
 * Created by tomm on 4/4/16 AD.
 */
public interface CurrencyApi {

    String BASE_URL = "https://free.currconv.com/api/v7/";
    String KEY = "&compact=ultra&apiKey=418e768cb549c5809fbd";

    //US dollar
    @GET("convert?q=USD_RUB"+KEY)
    Call<CurrencyModelUR> convertFromUsdToRub();

    @GET("convert?q=USD_PLN"+KEY)
    Call<CurrencyModelUP> convertFromUsdToPln();

    @GET("convert?q=USD_GBP"+KEY)
    Call<CurrencyModelUG> convertFromUsdToGbp();

    @GET("convert?q=USD_EUR"+KEY)
    Call<CurrencyModelUE> convertFromUsdToEur();

    @GET("convert?q=USD_CZK"+KEY)
    Call<CurrencyModelUC> convertFromUsdToCzk();

    //Russian ruble
    @GET("convert?q=RUB_USD"+KEY)
    Call<CurrencyModelRU> convertFromRubToUsd();

    @GET("convert?q=RUB_PLN"+KEY)
    Call<CurrencyModelRP> convertFromRubToPln();

    @GET("convert?q=RUB_GBP"+KEY)
    Call<CurrencyModelRG> convertFromRubToGbp();

    @GET("convert?q=RUB_EUR"+KEY)
    Call<CurrencyModelRE> convertFromRubToEUR();

    @GET("convert?q=RUB_CZK"+KEY)
    Call<CurrencyModelRC> convertFromRubToCZK();

    //Polish zloty
    @GET("convert?q=PLN_USD"+KEY)
    Call<CurrencyModelPU> convertFromUsdPlnToUsd();

    @GET("convert?q=PLN_GBP"+KEY)
    Call<CurrencyModelPG> convertFromUsdPlnToGBP();

    @GET("convert?q=PLN_EUR"+KEY)
    Call<CurrencyModelPE> convertFromUsdPlnToEUR();

    @GET("convert?q=PLN_RUB"+KEY)
    Call<CurrencyModelPR> convertFromUsdPlnToRUB();

    @GET("convert?q=PLN_CZK"+KEY)
    Call<CurrencyModelPC> convertFromUsdPlnToCZK();

    //Pound sterling
    @GET("convert?q=GBP_USD"+KEY)
    Call<CurrencyModelGU> convertFromUsdGbpToUsd();

    @GET("convert?q=GBP_RUB"+KEY)
    Call<CurrencyModelGR> convertFromUsdGbpToRUB();

    @GET("convert?q=GBP_EUR"+KEY)
    Call<CurrencyModelGE> convertFromUsdGbpToEUR();

    @GET("convert?q=GBP_PLN"+KEY)
    Call<CurrencyModelGP> convertFromUsdGbpToPLN();

    @GET("convert?q=GBP_CZK"+KEY)
    Call<CurrencyModelGC> convertFromUsdGbpToCZK();

    //Euro
    @GET("convert?q=EUR_USD"+KEY)
    Call<CurrencyModelEU> convertFromUsdEurToUsd();

    @GET("convert?q=EUR_GBP"+KEY)
    Call<CurrencyModelEG> convertFromUsdEurToGBP();

    @GET("convert?q=EUR_RUB"+KEY)
    Call<CurrencyModelER> convertFromUsdEurToRUB();

    @GET("convert?q=EUR_PLN"+KEY)
    Call<CurrencyModelEP> convertFromUsdEurToPLN();

    @GET("convert?q=EUR_CZK"+KEY)
    Call<CurrencyModelEC> convertFromUsdEurToCZK();

    //Czech koruna
    @GET("convert?q=CZK_USD"+KEY)
    Call<CurrencyModelCU> convertFromUsdCzkToUsd();

    @GET("convert?q=CZK_EUR"+KEY)
    Call<CurrencyModelCE> convertFromUsdCzkToEUR();

    @GET("convert?q=CZK_GBP"+KEY)
    Call<CurrencyModelCG> convertFromUsdCzkToGBP();

    @GET("convert?q=CZK_RUB"+KEY)
    Call<CurrencyModelCR> convertFromUsdCzkToRUB();

    @GET("convert?q=CZK_PLN"+KEY)
    Call<CurrencyModelCP> convertFromUsdCzkToPLN();







}
