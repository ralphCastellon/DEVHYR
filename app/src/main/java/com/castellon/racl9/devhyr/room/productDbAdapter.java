package com.castellon.racl9.devhyr.room;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.room.models.productsDB;

import java.util.List;

public class productDbAdapter extends RecyclerView.Adapter<productDbAdapter.ViewHolder> {

    List<productsDB> product;

    public productDbAdapter(List<productsDB> product) {
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
    public productDbAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_my_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(productDbAdapter.ViewHolder holder, int position) {
        final productsDB productsDB = product.get(position);
        holder.name.setText(productsDB.getName());
        holder.description.setText(productsDB.getDescription());
        holder.price.setText(String.valueOf(productsDB.getPrice()));
    }

    @Override
    public int getItemCount() {
        return product.size();
    }
}
