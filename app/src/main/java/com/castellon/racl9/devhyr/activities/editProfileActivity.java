package com.castellon.racl9.devhyr.activities;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.castellon.racl9.devhyr.R;
import com.castellon.racl9.devhyr.api.api;
import com.castellon.racl9.devhyr.models.userPictures;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tumblr.remember.Remember;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editProfileActivity extends AppCompatActivity {

    private CircleImageView imageView;
    private EditText name;
    private EditText userName;
    private EditText email;
    private EditText phoneNumber;
    private Button cp;
    private StorageReference storageReference;
    private final String folder_Root = "DevH&R/";
    private final String Route_image = folder_Root + "myProfilePhotos";
    final int Cod_Select = 10;
    final int Cod_Photo = 20;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    private void init() {
        name = findViewById(R.id.Name);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        imageView = findViewById(R.id.imgProfile);
        cp = findViewById(R.id.changePicture);
        storageReference = FirebaseStorage.getInstance().getReference();

        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message();
            }
        });
    }

    public void message() {
        final CharSequence[] options = {"Take Photo", "Upload Photo", "Cancel"};
        final AlertDialog.Builder alertOptions = new AlertDialog.Builder(editProfileActivity.this);

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
        userPictures picture = new userPictures();
        /* picture.setTitle(name.getText().toString());*/
        picture.setUrl(url.toString());

        Call<userPictures> picturesCall = api.instance().saveImageProfile(picture, Remember.getString("access_token", ""));
        picturesCall.enqueue(new Callback<userPictures>() {
            @Override
            public void onResponse(Call<userPictures> call, Response<userPictures> response) {
                if (response.isSuccessful()) {

                } else {
                    Toast.makeText(getApplicationContext(), "An error occurred while the photo was posted", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<userPictures> call, Throwable t) {
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
                            Glide.with(editProfileActivity.this)
                                    .load(downloadPhoto)
                                    .into(imageView);
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

                    imageView.setImageBitmap(bitmap);

                    break;
            }
        }
    }
}
