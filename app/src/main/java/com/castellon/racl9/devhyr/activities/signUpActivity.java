package com.castellon.racl9.devhyr.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.api.api;
import com.castellon.racl9.devhyr.models.user;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signUpActivity extends AppCompatActivity {

    private EditText email, pass, nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }
    private void init() {
        nombre = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
    }
    private void signUp() {
        String nombre2 = String.valueOf(nombre.getText().toString());
        String email2 = String.valueOf(email.getText().toString());
        String pass2 = String.valueOf(pass.getText().toString());
        if(nombre2.equals(" ")||email2.equals("") || pass2.equals("")) {
            Toast.makeText(getApplicationContext(),"Can't leave empty fields",Toast.LENGTH_SHORT).show();
        }else {
            user users = new user();
            users.setEmail(email.getText().toString());
            users.setPassword(pass.getText().toString());

            Call<user> userCall = api.instance().saveUser(users);
            userCall.enqueue(new Callback<user>() {
                @Override
                public void onResponse(Call<user> call, Response<user> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Success to Register", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(signUpActivity.this, loginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "An error occur while register was doing", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<user> call, Throwable t) {
                    Log.e("Err", "Error to show");
                }
            });
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:
                signUp();
                break;
            case R.id.btnLogin:
                Intent intent = new Intent(signUpActivity.this, loginActivity.class);
                startActivity(intent);
                break;
        }

    }
}
