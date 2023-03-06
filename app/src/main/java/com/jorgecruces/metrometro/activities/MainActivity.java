package com.jorgecruces.metrometro.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.sound.BackgroundMusic;
import com.jorgecruces.metrometro.sound.MediaPlayerReproducer;


public class MainActivity extends AppCompatActivity {

    private ImageView imageViewMenuLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.imageViewMenuLogo = findViewById(R.id.imageViewLogo);
        this.animateFadeInButtons();
    }


    @Override
    protected void onStart() {
        super.onStart();
        BackgroundMusic.onStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.startMenuLogoAnimation();
    }
    
    private void animateFadeInButtons() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        ImageView imageViewPlayButton = findViewById(R.id.imageViewButtonPlay);
        ImageView imageViewInformationButton = findViewById(R.id.imageViewButtonInformation);
        ImageView imageViewResetButton = findViewById(R.id.imageViewButtonReset);
        ImageView imageViewConfigurationButton = findViewById(R.id.imageViewButtonConfiguration);

        imageViewPlayButton.startAnimation(fadeInAnimation);
        imageViewInformationButton.startAnimation(fadeInAnimation);
        imageViewResetButton.startAnimation(fadeInAnimation);
        imageViewConfigurationButton.startAnimation(fadeInAnimation);
    }

    private void startMenuLogoAnimation() {
        Animation floatingAnimation = AnimationUtils.loadAnimation(this,R.anim.floating);
        this.imageViewMenuLogo.startAnimation(floatingAnimation);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BackgroundMusic.onStop(this);
    }

    public void goToLinesMenuActivity(View view) {
        // Sound Effect
        MediaPlayerReproducer.getInstance().reproduceClickSound(this);

        Intent intent = new Intent(this, MenuMetroActivity.class);
        startActivity(intent);
    }

    public void resetSharedPreferences() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        Toast.makeText(this, "Juego Reiniciado", Toast.LENGTH_SHORT).show();
    }

    public void goToMetroInfoActivity(View view) {
        MediaPlayerReproducer.getInstance().reproduceClickSound(this);
        Intent intent = new Intent(this, MetroInformationActivity.class);
        startActivity(intent);
    }

    public void showConfigurationDialog(View view) {
        // Sound Effect
        MediaPlayerReproducer.getInstance().reproduceClickSound(this);
        // Show dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.configuration_dialog);

        // Switch
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchSoundConfiguration = (Switch) dialog.findViewById(R.id.switchSoundConfiguration);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchMusicConfiguration = (Switch) dialog.findViewById(R.id.switchMusicConfiguration);

        boolean isAudioOn = MediaPlayerReproducer.getInstance().getAudioBoolean();
        boolean isMusicOn = MediaPlayerReproducer.getInstance().getMusicBoolean();

        switchSoundConfiguration.setChecked(isAudioOn);
        switchMusicConfiguration.setChecked(isMusicOn);


        switchSoundConfiguration.setOnClickListener(switchSound -> {
            MediaPlayerReproducer.getInstance().reproduceClickSound(this);
            MediaPlayerReproducer.getInstance().changeAudioReproducing();
        });

        switchMusicConfiguration.setOnClickListener(switchSound -> {
            MediaPlayerReproducer.getInstance().reproduceClickSound(this);
            MediaPlayerReproducer.getInstance().changeMusicReproducing();
            // Stop Music
            if (MediaPlayerReproducer.getInstance().getMusicBoolean()) {
                BackgroundMusic.onStart(this);
            } else {
                BackgroundMusic.forceStop();

            }
        });

        // ImageView
        ImageView goBackImageView = (ImageView) dialog.findViewById(R.id.goBackButtonConfiguration);
        goBackImageView.setOnClickListener(imageView -> {
            MediaPlayerReproducer.getInstance().reproduceClickSound(this);
            dialog.dismiss();
        });
        dialog.show();
    }

    public void showConfirmationResetDialog(View view) {
        // Sound Effect
        MediaPlayerReproducer.getInstance().reproduceClickSound(this);
        // Show dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirmation_reset_dialog);


        // goBackButton
        Button buttonGoBack = (Button) dialog.findViewById(R.id.buttonGoBackConfirmationDialog);
        // Reset SharedPreferences
        Button buttonResetSharedPreferences = (Button) dialog.findViewById(R.id.buttonResetConfirmationDialog);


        buttonGoBack.setOnClickListener(button -> {
            MediaPlayerReproducer.getInstance().reproduceClickSound(this);
            dialog.dismiss();
        });

        buttonResetSharedPreferences.setOnClickListener(button -> {
            MediaPlayerReproducer.getInstance().reproduceClickSound(this);
            this.resetSharedPreferences();
            dialog.dismiss();
        });

        // Reset Shared Preferences
        dialog.show();
    }
}
