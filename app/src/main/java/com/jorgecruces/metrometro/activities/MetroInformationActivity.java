package com.jorgecruces.metrometro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.customViews.ZoomableImageView;
import com.jorgecruces.metrometro.sound.BackgroundMusic;
import com.jorgecruces.metrometro.sound.MediaPlayerReproducer;

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

    @Override
    protected void onStart() {
        super.onStart();
        BackgroundMusic.onStart(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        BackgroundMusic.onStop(this);
    }

    public void goToMainActivity(View view) {
        // Sound Effect
        MediaPlayerReproducer.getInstance().reproduceClickSound(this);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}