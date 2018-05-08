package com.castellon.racl9.devhyr.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.castellon.racl9.devhyr.R;
import com.paypal.android.sdk.payments.LoginActivity;

public class homeActivity extends AppCompatActivity {
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       /* Button btn1 =(Button) findViewById(R.id.ButtonHome);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        });*/

    }
}
