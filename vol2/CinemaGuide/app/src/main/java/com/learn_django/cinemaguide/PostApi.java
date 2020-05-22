package com.learn_django.cinemaguide;

import com.learn_django.cinemaguide.model.JourneyAddModel;
import com.learn_django.cinemaguide.model.Login;
import com.learn_django.cinemaguide.model.User;
import com.learn_django.cinemaguide.model.FilmModel;
import com.learn_django.cinemaguide.model.LocationModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostApi {


    String root = "http://192.168.0.104:8000/";
//    String root = "http://127.0.0.1:8000/";


    String base_local = root + "api/v1/";
    String BASE_URL = base_local + "account/";
    String POST_URL = base_local + "post/";
    String API_URL = root + "api/v1/";

    @POST("api-token-auth/")
    Call<User> login(@Body Login login);

    @POST("register/")
    Call<User> registrationUser(@Body User userModel);

    @GET("post/films/list/ ")
    Call<List<FilmModel>> getFilmsList();

    @GET("post/locations/list/ ")
    Call<List<LocationModel>> getLocationsList();

    @GET("post/films/{id}/")
    Call<FilmModel> getFilm(@Path(value = "id", encoded = true) String id);

    @GET("post/locations/{id}/")
    Call<LocationModel> getLocation(@Path(value = "id", encoded = true) String id);

    @POST("add/journey/")
    Call<JourneyAddModel> addJourney(@Header("Authorization")  String authToken, @Body JourneyAddModel journeyAddModel);

//    @POST("add/")
//    Call<PostModel> addPost(@Header("Authorization")  String authToken, @Body PostModel postModel);
//
//    @GET("profile/list/")
//    Call<List<PostModel>> getProfileList(@Header("Authorization")  String authToken);
//
//
//    @PUT("profile/edit/{id}/")
//    Call<PostModel> updatePost(@Header("Authorization")  String authToken, @Body PostModel postModelUpdate, @Path(value = "id", encoded = true) String id);
//
//
//    @DELETE("profile/delete/{id}/")
//    Call<List<PostModel>> deletePost(@Header("Authorization")  String authToken, @Path(value = "id", encoded = true) String id);
//
//
//    @GET("category/list/")
//    Call<List<CategoryModel>> getAllCategory();
//
//    @GET("list/{id}/")
//    Call<List<CategoryModel>> getCategoryById(@Path(value = "id", encoded = true) Integer id);

}