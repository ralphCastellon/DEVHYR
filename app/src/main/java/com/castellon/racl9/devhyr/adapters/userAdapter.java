package com.castellon.racl9.devhyr.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    public userAdapter() {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder (View view){
            super(view);
        }
    }
    @NonNull
    @Override
    public userAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
