package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.customViews.ZoomableImageView;

public class MetroInformationActivity extends AppCompatActivity {

    private ZoomableImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_information);
        imageView=findViewById(R.id.metroMap);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.info_metro_image_map);
        imageView.setImageBitmap(bm);
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}