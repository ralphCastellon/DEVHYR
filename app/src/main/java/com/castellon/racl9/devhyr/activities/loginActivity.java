package com.castellon.racl9.devhyr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.castellon.racl9.devhyr.MainActivity;
import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.api.api;
import com.castellon.racl9.devhyr.models.accessToken;
import com.castellon.racl9.devhyr.models.user;
import com.tumblr.remember.Remember;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity {

    private EditText email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Remember.init(getApplicationContext(),"com.example.racl9.devhyr.activities");
        init();
    }

    private void init() {
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
    }

    private void login() {
        String email2 = String.valueOf(email.getText().toString());
        String pass2 = String.valueOf(pass.getText().toString());
        if(email2.equals("") || pass2.equals("")) {
            Toast.makeText(getApplicationContext(),"Can't leave empty fields",Toast.LENGTH_SHORT).show();
        }else{
            user users = new user();
            users.setEmail(email.getText().toString());
            users.setPassword(pass.getText().toString());
            saveUserData(email2,pass2);
            Call<accessToken> accessTokenCall = api.instance().loginUser(users);
            accessTokenCall.enqueue(new Callback<accessToken>() {
                @Override
                public void onResponse(@NonNull Call<accessToken> call,@NonNull Response<accessToken> response) {

                    if(response.isSuccessful()) {
                        Remember.putString("access_token", response.body().getId(), new Remember.Callback() {
                            @Override
                            public void apply(Boolean success) {
                                Toast.makeText(getApplicationContext(), "Success to login", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(loginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"An error occur while login was doing",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<accessToken> call,@NonNull Throwable t) {
                    Log.e("Err","An error occur while login was doing", t);
                }
            });
        }
    }

    public void saveUserData(String u, String p){

        SharedPreferences sharedPreferences = getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("userEmail",u);
        editor.putString("userPassword",p);

        editor.commit();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnlogin:
                login();
                break;
            case R.id.btnSignUp:
                Intent intent = new Intent(loginActivity.this, signUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
