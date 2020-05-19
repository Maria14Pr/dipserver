package com.learn_django.cinemaguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;
import com.learn_django.cinemaguide.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegistrationActivity extends AppCompatActivity {

    Button regBtn;
    EditText usernameEt;
    EditText passwordEt;
    EditText emailEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        regBtn = findViewById(R.id.btn_register);

        usernameEt = findViewById(R.id.reg_username);
        passwordEt = findViewById(R.id.reg_password);
        emailEt = findViewById(R.id.reg_email);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegButtonClick();

            }
        });


    }


    public void RegButtonClick()
    {


        String str_reg_username = usernameEt.getText().toString();
        String str_reg_password = passwordEt.getText().toString();
        String str_reg_email = emailEt.getText().toString();


        User userModel = new User(
                /*1*/
                str_reg_email,
                str_reg_username,
                str_reg_password,
                "sadasdasd"
        );



        if (!IsEmptyEditTextLogin()){

            if ( InternetUtil.isInternetOnline(RegistrationActivity.this) ){
                RegisterInServer(userModel);
            }

        }

    }

    public void RegisterInServer(User userModel) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi postApi= retrofit.create(PostApi.class);
        Call<User> call = postApi.registrationUser(userModel);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    if (response.body() != null) {

                        SharedPreferences preferences = RegistrationActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                        prefLoginEdit.putBoolean("registration",true);
                        prefLoginEdit.commit();

                        finish();
                        /*Intent goToHomeIntent = new Intent(RegistrationActivity.this, MainMenuActivity.class);
                        startActivity(goToHomeIntent);*/

                    }
                }else {
                    Log.d("fail", "response body is null");
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("fail", "failure");
            }
        });

    }



    private Boolean IsEmptyEditTextLogin(){


        if(passwordEt.getText().toString().isEmpty() || usernameEt.getText().toString().isEmpty()|| emailEt.getText().toString().isEmpty()){

            Toast toast = Toast.makeText(RegistrationActivity.this,"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


            return true;
        }else{
            return false;
        }

    }


}
