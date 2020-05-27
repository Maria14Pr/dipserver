package com.learn_django.cinemaguide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.learn_django.cinemaguide.creator.CreatorJourneyFragment;
import com.learn_django.cinemaguide.model.Login;
import com.learn_django.cinemaguide.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private TextView tvRegister;
    private Button btnLogin;
    EditText Edreg_username;
    EditText Edreg_password;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (CheckLogin()) {
            Intent autorisedRegIntent = new Intent(MainActivity.this, MainMenuActivity.class);
            startActivity(autorisedRegIntent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().setElevation(0);

        Edreg_username = findViewById(R.id.et_login);
        Edreg_password = findViewById(R.id.et_password);

        btnLogin = findViewById(R.id.btn_login);


        tvRegister = findViewById(R.id.tvtext_registration);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogButtonClick();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Желаете зарегестрироваться?");
                builder.setMessage("Без регистрации Вы не сможете получить доступ ко всем функциям приложения");

                builder.setPositiveButton("Зарегестрироваться", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //deletePost();
                        dialog.dismiss();
                        Intent registerIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                        startActivity(registerIntent);
                    }
                });

                builder.setNegativeButton("Продолжить без регистрации", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent withoutRegIntent = new Intent(MainActivity.this, MainMenuActivity.class);
                        startActivity(withoutRegIntent);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    public Boolean CheckLogin() {

        SharedPreferences preferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Boolean shered_category_id = preferences.getBoolean("loggedin", false);

        Log.e("пользователь", String.valueOf(shered_category_id));

        if (shered_category_id) {
            return true;
        } else {
            return false;
        }
    }

    public void LogButtonClick()     {

        if (!IsEmptyEditTextLogin()){
            if ( InternetUtil.isInternetOnline(this) ){
                login();
            }
        }
    }

    private void login(){

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(PostApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        PostApi postApi = retrofit.create(PostApi.class);
        String add1      =  Edreg_username.getText().toString();
        String add2      =  Edreg_password.getText().toString();
        Login login = new Login(add1, add2);
        Call<User> call = postApi.login(login);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    if (response.body() != null) {

                        String token = response.body().getToken();

                        SharedPreferences preferences = MainActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                        prefLoginEdit.putBoolean("loggedin", true);
                        prefLoginEdit.putString("token", token);
                        prefLoginEdit.commit();

                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();

                        Intent goToHomeIntent = new Intent(MainActivity.this, MainMenuActivity.class);
                        startActivity(goToHomeIntent);

                    }

                }else {
                    Toast.makeText(MainActivity.this, "login no correct :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Boolean IsEmptyEditTextLogin(){

        if(Edreg_password.getText().toString().isEmpty() || Edreg_username.getText().toString().isEmpty()){

            Toast toast = Toast.makeText(MainActivity.this,"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return true;
        }else{
            return false;
        }
    }

}