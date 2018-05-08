package com.castellon.racl9.devhyr.fragments;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.room.db.database;
import com.castellon.racl9.devhyr.room.models.productsSavedDB;
import com.castellon.racl9.devhyr.room.productSavedDBAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ps2MyProductsSavedFragment extends Fragment {


    private RecyclerView recyclerView;
    private TextView name;
    private TextView description;
    private TextView price;

    public ps2MyProductsSavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ps2_my_products_saved, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        name = view.findViewById(R.id.Name);
        description = view.findViewById(R.id.Description);
        price = view.findViewById(R.id.Price);

        database database = Room.databaseBuilder(getContext(), database.class, "productsDB")
                .allowMainThreadQueries()
                .build();

        List<productsSavedDB> productsDB = database.productsDao().getAllProductSaved();
        productSavedDBAdapter productDbAdapters = new productSavedDBAdapter(productsDB);
        recyclerView.setAdapter(productDbAdapters);

    }

}
