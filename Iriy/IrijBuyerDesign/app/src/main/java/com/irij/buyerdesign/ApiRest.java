package com.irij.buyerdesign;

import com.irij.buyerdesign.model.CategoryModel;
import com.irij.buyerdesign.model.GoodModel;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRest {

    String root = "http://iriy.online/";
    //String root = "http://192.168.43.81:8000/";
    //String root = "http://127.0.0.1:8000/";


    String API_URL = root + "api/";

    @GET("category")
    Call<List<CategoryModel>> getCategoryList();

    @GET("category/{id}")
    Call<List<GoodModel>> getGoodsOfCategory(@Path(value = "id", encoded = true) String id);

}
