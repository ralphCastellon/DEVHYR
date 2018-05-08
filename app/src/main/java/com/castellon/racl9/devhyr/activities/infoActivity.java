package com.castellon.racl9.devhyr.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.api.api;
import com.castellon.racl9.devhyr.models.pictures;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tumblr.remember.Remember;

import java.math.BigDecimal;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class infoActivity extends AppCompatActivity {

    private ImageView image;
    private Button buyProduct;
    private TextView name;
    private TextView description;
    private TextView price;
    private TextView response;
    private CircleImageView imgUser;
    PayPalConfiguration configuration;
    Intent services;
    int paypalRequestCode = 999;
    String paypalClientId="Ab_rPcZz586YDv1QOMlgpaBADdZ_bKer7PSnl1QrrMy8gVVJDoF8G6xjz-2W3LprnsfK2dH90ZKmlRg0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
    }

    private void init() {

        image = findViewById(R.id.image_product);
        buyProduct = findViewById(R.id.buy_product);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        imgUser = findViewById(R.id.imgProfile);
        configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(paypalClientId);
        services = new Intent(this, PayPalService.class);
        services.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(services);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null){
            String nombre = (String) bundle.get("name");
            String prices = (String) bundle.get("price");
            String desc = (String) bundle.get("description");

            name.setText(nombre);
            price.setText(prices);
            description.setText(desc);
        }

        final int BigDecimal = Integer.parseInt(price.getText().toString());

        buyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPayment payment = new PayPalPayment(new BigDecimal(BigDecimal), "USD", "Payment with Paypal", PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent1 = new Intent(getApplicationContext(), PaymentActivity.class);
                intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
                intent1.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivityForResult(intent1,paypalRequestCode);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == paypalRequestCode){
            if (resultCode == Activity.RESULT_OK){

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmation != null){

                    String state = confirmation.getProofOfPayment().getState();

                    if (state.equals("approved")){

                        response.setText("Product bought");

                    }else{
                        response.setText("Error in the payment");
                    }



                }else{
                    response.setText("confirmation is null");
                }
            }
        }
    }
}
