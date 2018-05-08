package com.castellon.racl9.devhyr.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.models.pictures;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class productImgAdapter extends RecyclerView.Adapter<productImgAdapter.ViewHolder> {

    private List<pictures> picturesList;
    private Context context;

    public productImgAdapter(List<pictures> picturesList1, Context context1) {
        this.picturesList = picturesList1;
        this.context = context1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView draweeView;
        public ViewHolder (View view){
            super(view);
            draweeView = (SimpleDraweeView) view.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public productImgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_products, parent, false);
        return new productImgAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productImgAdapter.ViewHolder holder, int position) {
        final pictures pic = picturesList.get(position);
        Uri uri = Uri.parse(pic.getUrl());
        holder.draweeView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return picturesList.size();
    }
}
