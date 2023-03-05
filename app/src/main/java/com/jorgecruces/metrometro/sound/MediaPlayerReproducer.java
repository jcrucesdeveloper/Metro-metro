package com.jorgecruces.metrometro.sound;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.jorgecruces.metrometro.R;


/**
 * Singleton class that works as a general Audio Manager
 */
public class MediaPlayerReproducer {

    private static final MediaPlayerReproducer mp = new MediaPlayerReproducer();
    private MediaPlayer mediaPlayer;

    private boolean isAudioReproducing = true;
    private boolean isMusicOn = true;

    private boolean isMusicReproducing = false;

    private Intent musicIntent;

    /**
     Singleton Pattern
     */
    private MediaPlayerReproducer()
    {
    }

    public static MediaPlayerReproducer getInstance()
    {
        return mp;
    }


    // Audio and Music Setting
    public void changeAudioReproducing() {
        isAudioReproducing = !isAudioReproducing;
    }
    public void changeMusicReproducing() {
        isMusicOn = !isMusicOn;
    }
    public boolean getAudioBoolean() {
        return this.isAudioReproducing;
    }
    public boolean getMusicBoolean() {
        return this.isMusicOn;
    }


    /**
     * Reproduce click Sound
     * @param context activity
     */
    public void reproduceClickSound(Context context)
    {
        reproduceSound(context, R.raw.sound_click_all);
    }

    public void reproduceLostSound(Context context) {
        reproduceSound(context, R.raw.sound_lost);
    }

    public void reproduceCorrectAlternativeSound(Context context) {
        reproduceSound(context, R.raw.sound_correct_alternative);
    }

    public void reproduceSoundWinLevel(Context context) {
        reproduceSound(context, R.raw.sound_win);

    }


    /**
     * Reproduce Win Sound
     * @param context activity
     */
//    public void reproduceWinSound(Context context)
//    {
//        reproduceSound(context, R.raw.win_sound);
//    }

    public void reproduceMusicMainMenu(Context context) {
        if (!isMusicOn || isMusicReproducing) {return;}

        // Reproduce music


        isMusicReproducing = true;
    }

    public void stopMusicMainMenu(Context context) {
        if (isMusicReproducing && musicIntent != null) {

            //Stop music
            isMusicReproducing = false;
        }
    }

    /**
     * Reproduce sound
     * @param context the activity where we reproduce the sound
     * @param R the sound file from Resources
     */
    private void reproduceSound(Context context, int R)
    {
        if(!isAudioReproducing)
        {
            return;
        }

        MediaPlayer mp = MediaPlayer.create(context, R);
        try
        {
            mp.start();
        } catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
    }

}
