package com.jorgecruces.metrometro.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.sound.BackgroundMusic;
import com.jorgecruces.metrometro.sound.MediaPlayerReproducer;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void goToLinesMenuActivity(View view) {
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
            MediaPlayerReproducer.getInstance().changeAudioReproducing();
        });

        switchMusicConfiguration.setOnClickListener(switchSound -> {
            MediaPlayerReproducer.getInstance().changeMusicReproducing();
        });

        // ImageView
        ImageView goBackImageView = (ImageView) dialog.findViewById(R.id.goBackButtonConfiguration);
        goBackImageView.setOnClickListener(imageView -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public void showConfirmationResetDialog(View view) {
        // Show dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.confirmation_reset_dialog);


        // goBackButton
        Button buttonGoBack = (Button) dialog.findViewById(R.id.buttonGoBackConfirmationDialog);
        // Reset SharedPreferences
        Button buttonResetSharedPreferences = (Button) dialog.findViewById(R.id.buttonResetConfirmationDialog);


        buttonGoBack.setOnClickListener(button -> {
            dialog.dismiss();
        });

        buttonResetSharedPreferences.setOnClickListener(button -> {
            this.resetSharedPreferences();
            dialog.dismiss();
        });

        // Reset Shared Preferences
        dialog.show();
    }
}
