package com.castellon.racl9.devhyr.fragments;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.activities.editProfileActivity;
import com.castellon.racl9.devhyr.api.api;
import com.castellon.racl9.devhyr.models.userPictures;
import com.tumblr.remember.Remember;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {

       private CircleImageView userimage;
       private TextView name;
       private TextView productsSell;
       private TextView email;
       private Button editProfile;

       private FragmentTabHost tabHost;

    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        createTabhHost();
        return view;
    }

    private void createTabhHost() {

        tabHost.setup(getContext(),getChildFragmentManager(), android.R.id.tabcontent);

        Resources resources = getResources();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");

        tab1.setIndicator(resources.getString(R.string.Text_tabhost_pas1),resources.getDrawable(R.drawable.ic_dashboard_black_24dp));
        tab2.setIndicator(resources.getString(R.string.Text_tabhost_pas2),resources.getDrawable(R.drawable.ic_bookmark_border_black_24dp));

        tabHost.addTab(tab1, ps1MyProductsFragment.class, null);
        tabHost.addTab(tab2, ps2MyProductsSavedFragment.class, null);


    }

    private void init(View view) {
        userimage = view.findViewById(R.id.imgProfile);
        name = view.findViewById(R.id.name);
        productsSell = view.findViewById(R.id.productsSell);
        email = view.findViewById(R.id.email);
        editProfile = view.findViewById(R.id.edit);
        tabHost = view.findViewById(R.id.tabHost);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), editProfileActivity.class);
                startActivity(intent);
            }
        });

        callUser();

    }

    private void callUser() {
        /* Call<List<user>> uCall = api.instance().getUserInfo(Remember.getString("access_token",""));
        uCall.enqueue(new Callback<List<user>>() {
            @Override
            public void onResponse(Call<List<user>> call, Response<List<user>> response) {
                if(response.isSuccessful()){
                    //userAdapter userAdapter = new userAdapter(response.body());
                }else{
                    Toast.makeText(getContext(),"An Error occurred while userCall was doing",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<user>> call, Throwable t) {
                Log.e("Error","An Error occurred",t);
            }
        });*/

        Call<List<userPictures>> upCall = api.instance().getImageProfile(Remember.getString("access_token",""));
        upCall.enqueue(new Callback<List<userPictures>>() {
            @Override
            public void onResponse(Call<List<userPictures>> call, Response<List<userPictures>> response) {
                if(response.isSuccessful()){

                }else{
                    Toast.makeText(getContext(),"An Error occurred while userImageCall was doing",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<userPictures>> call, Throwable t) {
                Log.e("Error","An Error occurred",t);
            }
        });
    }

}
