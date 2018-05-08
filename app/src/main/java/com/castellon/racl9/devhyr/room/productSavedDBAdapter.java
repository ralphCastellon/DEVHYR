package com.castellon.racl9.devhyr.room;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.room.models.productsSavedDB;

import java.util.List;

public class productSavedDBAdapter  extends RecyclerView.Adapter<productSavedDBAdapter.ViewHolder>{

    List<productsSavedDB> product;

    public productSavedDBAdapter(List<productsSavedDB> product) {
        this.product = product;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView description;
        public TextView price;
        public CardView cardView;

        public ViewHolder (View view){
            super (view);
            name = view.findViewById(R.id.Name);
            description = view.findViewById(R.id.Description);
            price = view.findViewById(R.id.Price);
            cardView = view.findViewById(R.id.card_view);
        }

    }

    @Override
    public productSavedDBAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_my_products_saved, parent, false);
        return new productSavedDBAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(productSavedDBAdapter.ViewHolder holder, int position) {
        final productsSavedDB productsDB = product.get(position);
        holder.name.setText(productsDB.getName());
        holder.description.setText(productsDB.getDescription());
        holder.price.setText(String.valueOf(productsDB.getPrice()));
    }

    @Override
    public int getItemCount() {
        return product.size();
    }
}
