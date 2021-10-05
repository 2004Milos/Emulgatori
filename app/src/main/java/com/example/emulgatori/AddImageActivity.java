package com.example.emulgatori;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddImageActivity extends AppCompatActivity {

    ImageView imageView;
    FloatingActionButton camFab; //DUGME ZA OTVARANJE KAMERE

    ActivityResultLauncher<Intent> StartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bitmap photo = (Bitmap)result.getData().getExtras().get("data");

                        //TODO -> BITMAPA SE PROSJEDJUJE U OPENCV/TESSERACT...
                        imageView.setImageBitmap(photo);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image_activity);

        camFab = findViewById(R.id.fabCamera);

        imageView = findViewById(R.id.img);

        camFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //POKRETANJE KAMERE
                StartForResult.launch(i);
            }
        });
    }
}