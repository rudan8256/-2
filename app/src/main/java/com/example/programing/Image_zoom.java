package com.example.programing;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.otaliastudios.zoom.ZoomLayout;

public class Image_zoom extends AppCompatActivity {

    ZoomLayout zoomLayout;
    ImageView zoom_image;
    Uri image_url;
    Button btn_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);

        zoom_image=findViewById(R.id.zoom_image);
        image_url=getIntent().getParcelableExtra("uri");

        if (image_url != null) {

            Glide.with(Image_zoom.this).load(image_url).into(zoom_image);

        }
    }
}