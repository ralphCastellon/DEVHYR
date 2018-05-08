package com.castellon.racl9.devhyr.activities;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.castellon.racl9.devhyr.MainActivity;
import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.api.api;
import com.castellon.racl9.devhyr.models.pictures;
import com.castellon.racl9.devhyr.models.products;
import com.castellon.racl9.devhyr.room.db.database;
import com.castellon.racl9.devhyr.room.models.productsDB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tumblr.remember.Remember;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class productAddActivity extends AppCompatActivity {

    private ImageView image;
    private EditText name;
    private EditText description;
    private EditText Price;
    private StorageReference storageReference;
    private final String folder_Root = "DevH&R/";
    private final String Route_image = folder_Root + "myPhotos";
    final int Cod_Select = 10;
    final int Cod_Photo = 20;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        init();
    }

    private void init() {
        name = findViewById(R.id.Name);
        // category = findViewById(R.id.Category);
        description = findViewById(R.id.Description);
        Price = findViewById(R.id.Price);
        image = findViewById(R.id.imagenes);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private void create() {

        final products product = new products();
        product.setName(name.getText().toString());
        product.setDescription(description.getText().toString());
        product.setCategoryId("5ada8e199642b100149ad7e2");
        product.setPrice(Integer.parseInt(Price.getText().toString()));


        Call<products> productsCall = api.instance().saveProducts(product, Remember.getString("access_token", ""));
        productsCall.enqueue(new Callback<products>() {
            @Override
            public void onResponse(Call<products> call, Response<products> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(productAddActivity.this, MainActivity.class);
                    startActivity(intent);
                    saveData();
                } else {
                    Toast.makeText(getApplicationContext(), "An error occurred in the post", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<products> call, Throwable t) {
                Log.e("Error", "Error to connect to the API", t);
            }
        });
    }


    public void message() {
        final CharSequence[] options = {"Take Photo", "Upload Photo", "Cancel"};
        final AlertDialog.Builder alertOptions = new AlertDialog.Builder(productAddActivity.this);

        alertOptions.setTitle("Select an Option");

        alertOptions.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialogInterface, int i) {

                if (options[i].equals("Take Photo")) {

                    takePhoto();

                } else {

                    if (options[i].equals("Upload Photo")) {

                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        intent.setType("image/*");

                        startActivityForResult(intent.createChooser(intent, "Select an app"), Cod_Select);

                    } else {

                        dialogInterface.dismiss();

                    }
                }
            }

        });

        alertOptions.show();

    }

    private void takePhoto() {
        File fileImage = new File(Environment.getExternalStorageDirectory(), Route_image);
        boolean isCreated = fileImage.exists();
        String nameImage = "";

        if (isCreated == false) {
            isCreated = fileImage.mkdirs();
        }

        if (isCreated == true) {
            nameImage = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory() + File.separator + Route_image + File.separator + nameImage;
        File image = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
        startActivityForResult(intent, Cod_Photo);
    }

    public void sendPictures(Uri url) {
        pictures picture = new pictures();
        picture.setTitle(name.getText().toString());
        picture.setUrl(url.toString());
        picture.setProductId("");

        Call<pictures> picturesCall = api.instance().savePicture(picture, Remember.getString("access_token", ""));
        picturesCall.enqueue(new Callback<pictures>() {
            @Override
            public void onResponse(Call<pictures> call, Response<pictures> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<pictures> call, Throwable t) {
                Log.e("Error", "Error to connect to the API", t);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Cod_Select:
                    Uri uri = data.getData();
                    StorageReference filePath = storageReference.child(Route_image).child(uri.getLastPathSegment());
                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadPhoto = taskSnapshot.getDownloadUrl();
                            Glide.with(productAddActivity.this)
                                    .load(downloadPhoto)
                                    .into(image);
                            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                            sendPictures(downloadPhoto);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "failure to upload the image", Toast.LENGTH_LONG).show();
                        }
                    });
                case Cod_Photo:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,

                            new MediaScannerConnection.OnScanCompletedListener() {

                                @Override

                                public void onScanCompleted(String s, Uri uri) {

                                    Log.i("Storage route", "Path: " + path);

                                }

                            });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);

                    image.setImageBitmap(bitmap);

                    break;
            }
        }
    }

    public void saveData(){
        productsDB product = new productsDB();

        database database = Room.databaseBuilder(getApplicationContext(), database.class, "productsDB")
                .allowMainThreadQueries()
                .build();

        product.setName(name.getText().toString());
        product.setDescription(description.getText().toString());
        product.setPrice(Integer.parseInt(Price.getText().toString()));
        product.setCategoryId("5ada8e199642b100149ad7e2");

        database.productsDao().insertAll(product);

    }

    public void cleanFields() {
        Uri uri = null;
        image.setImageURI(uri);
        name.setText("");
        Price.setText("");
        description.setText("");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhoto:
                message();
                break;
            case R.id.Send:
                create();
                break;
            case R.id.Cancel:
                cleanFields();
                break;
        }
    }
}
