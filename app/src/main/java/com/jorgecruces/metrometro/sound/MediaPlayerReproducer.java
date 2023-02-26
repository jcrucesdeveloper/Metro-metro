package com.jorgecruces.metrometro.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import com.jorgecruces.metrometro.R;

import java.io.IOException;

/**
 * Singleton that reproduce the sound across the App
 */
public class MediaPlayerReproducer {

    private static final MediaPlayerReproducer mp = new MediaPlayerReproducer();
    private boolean isAudioReproducing = true;
    private boolean isMusicOn = true;

    private MediaPlayerReproducer()
    {

    }

    /**
     * Change between reproduce Sound
     */
    public void changeAudioReproducing() {
        isAudioReproducing = !isAudioReproducing;
    }

    /**
     * Change between reproduce Music
     */
    public void changeMusicReproducing() {
        isMusicOn = !isMusicOn;
    }

    public boolean getAudioBoolean() {
        return this.isAudioReproducing;
    }

    public boolean getMusicBoolean() {
        return this.isMusicOn;
    }

    public static MediaPlayerReproducer getInstance()
    {
        return mp;
    }

    /**
     * Reproduce click Sound
     * @param context activity
     */
    public void reproduceClickSound(Context context)
    {
        reproduceSound(context, R.raw.click);
    }

    /**
     * Reproduce Win Sound
     * @param context activity
     */
    public void reproduceWinSound(Context context)
    {
        reproduceSound(context, R.raw.win_sound);
    }

    public void reproduceMusic(Context context) {
        if (!isMusicOn) {return;}
        PerfectLoopMediaPlayer perfectLoopMediaPlayer = PerfectLoopMediaPlayer.create(context,R.raw.music_intro_loop_3);

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
