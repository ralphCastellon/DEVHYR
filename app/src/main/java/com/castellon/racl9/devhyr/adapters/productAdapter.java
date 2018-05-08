package com.castellon.racl9.devhyr.adapters;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.activities.infoActivity;
import com.castellon.racl9.devhyr.models.products;
import com.castellon.racl9.devhyr.room.db.database;
import com.castellon.racl9.devhyr.room.models.productsSavedDB;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.ViewHolder> {

    private List<products> productos;
    private Context context;

    public productAdapter(Context context, List<products> product) {
        this.productos = product;
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView description;
        public TextView price;
        public CardView cardView;
        public TextView id;
        public Button share;
        public Button like;
        public Button save;
        public SimpleDraweeView draweeView;


        public ViewHolder (View view){
            super (view);
            name = view.findViewById(R.id.Name);
            description = view.findViewById(R.id.Description);
            price = view.findViewById(R.id.Price);
            id = view.findViewById(R.id.userName);
            share = view.findViewById(R.id.share);
            like = view.findViewById(R.id.likes);
            save = view.findViewById(R.id.saveDB);
            cardView = view.findViewById(R.id.card_view);
            draweeView = (SimpleDraweeView) view.findViewById(R.id.image);
        }

    }

    @NonNull
    @Override
    public productAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_products, parent, false);
        return new productAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final productAdapter.ViewHolder holder, final int position) {
        final products post = productos.get(position);
        holder.name.setText(post.getName());
        holder.description.setText(post.getDescription());
        holder.price.setText(String.valueOf(post.getPrice()));
        holder.id.setText("User Name");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, infoActivity.class);
                intent.putExtra("name",productos.get(position).getName());
                intent.putExtra("description",productos.get(position).getDescription());
                intent.putExtra("price",String.valueOf(productos.get(position).getPrice()));
                context.startActivity(intent);
            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"product Save",Toast.LENGTH_LONG).show();
                String name = String.valueOf(holder.name.getText());
                String desc = String.valueOf(holder.description.getText());
                String pri = String.valueOf(holder.price.getText());
                saveData(name,desc,pri);
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"likes +1",Toast.LENGTH_LONG).show();
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareSubject = "Devhyr";
                String shareText = "This is a great app, you should try it out";
                String shareTitle = "Share product via";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                context.startActivity(Intent.createChooser(shareIntent,shareTitle));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void saveData(String n, String d, String p){
        productsSavedDB product = new productsSavedDB();

        database database = Room.databaseBuilder(context, database.class, "productsDB")
                .allowMainThreadQueries()
                .build();

        product.setName(n);
        product.setDescription(d);
        product.setPrice(Integer.parseInt(p));
        product.setCategoryId("5ada8e199642b100149ad7e2");

        database.productsDao().insertAllPS(product);

    }

}
