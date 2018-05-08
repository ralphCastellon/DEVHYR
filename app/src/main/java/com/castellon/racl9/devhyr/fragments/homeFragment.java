package com.castellon.racl9.devhyr.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.adapters.productAdapter;
import com.castellon.racl9.devhyr.adapters.productImgAdapter;
import com.castellon.racl9.devhyr.api.api;
import com.castellon.racl9.devhyr.models.pictures;
import com.castellon.racl9.devhyr.models.products;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tumblr.remember.Remember;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView name;
    private TextView description;
    private TextView price;

    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Fresco.initialize(getContext());
        init(view);
        return view;
    }

    public void init(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        name = view.findViewById(R.id.Name);
        description = view.findViewById(R.id.Description);
        price = view.findViewById(R.id.Price);

        Call<List<products>> productsCall = api.instance().getProducts(Remember.getString("access_token",""));
        productsCall.enqueue(new Callback<List<products>>() {
            @Override
            public void onResponse(Call<List<products>> call, Response<List<products>> response) {
                if(response.isSuccessful()){
                    productAdapter productAdapter = new productAdapter(getContext(), response.body());
                    recyclerView.setAdapter(productAdapter);

                }else{
                    Toast.makeText(getContext(),"Error to show",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<products>> call, Throwable t) {
                Log.e("Err","Error trying to connect to the API", t);
            }
        });

           /* Call<List<pictures>> picturesCall = api.instance().getPictures(Remember.getString("access_token",""));
            picturesCall.enqueue(new Callback<List<pictures>>() {
                @Override
                public void onResponse(Call<List<pictures>> call, Response<List<pictures>> response) {
                    if(response.isSuccessful()){
                        productImgAdapter pictureAdapter = new productImgAdapter(response.body(),getContext());
                        recyclerView.setAdapter(pictureAdapter);
                    }else{
                        Toast.makeText(getContext(),"Error to show the pictures",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<pictures>> call, Throwable t) {
                    Log.e("Err","Error trying to connect to the API", t);
                }
            });*/
    }


}
