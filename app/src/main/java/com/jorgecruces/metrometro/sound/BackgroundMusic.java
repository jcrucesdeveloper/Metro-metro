package com.jorgecruces.metrometro.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.jorgecruces.metrometro.R;

public abstract class BackgroundMusic {
    private static MediaPlayer mediaPlayer;
    private static int startCounter = 0;

    public static void onStart(Context context) {
        startCounter++;
        Log.d("COUNTER START",Integer.toString(startCounter));
        if (startCounter == 1) {
            mediaPlayer = MediaPlayer.create(context,R.raw.music_menu_loop);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.start();
        }
    }

    public static void onStop(Context context) {
        startCounter--;
        Log.d("COUNTER STOP",Integer.toString(startCounter));
        if (startCounter == 0) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
