package com.jorgecruces.metrometro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.jorgecruces.metrometro.R;
import com.jorgecruces.metrometro.customViews.StationView;
import com.jorgecruces.metrometro.logic.MetroReaderXML;
import com.jorgecruces.metrometro.logic.PickerStationsAlternative;
import com.jorgecruces.metrometro.model.Line;
import com.jorgecruces.metrometro.model.Metro;
import com.jorgecruces.metrometro.model.Station;
import com.jorgecruces.metrometro.sound.MediaPlayerReproducer;

import java.util.ArrayList;
import java.util.Collections;

public class PlayGameActivity extends AppCompatActivity {

    // Static Level Data
    private String lineName;
    private String lineColorHex;
    private ArrayList<Station> stations;
    private int stationsSize;

    // Non-Static Level Data
    private int position;
    private String currentStationName;
    private Station correctAlternative;
    private ArrayList<Station> alternatives;
    private ArrayList<StationView> stationViews;

    // ProgressBar 
    private ProgressBar progressBar;
    private int timeRemaining = 25; // Default value: 25 seconds
    private static final int TIME_INTERVAL = 100; // Interval in milliseconds (0.1 second for smoother animation)
    private final Handler handler = new Handler();
    private Runnable timeRunnable;
    private final float decrementValue = 0.1f; // Amount to decrement each interval for smooth animation
    private float currentProgress;

    // Music
    private MediaPlayer mediaPlayerGameplayGameplay;
    private boolean isReproducingGameplayMusic = false;

    // Ads
    private AdRequest adRequest;
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        this.setLineName();
        this.initializeLevelData();
        this.initializeLevelViews();
        this.initializeProgressBar();
        this.setCurrentStationQuestion(this.position);
        this.drawStationView(150, this.currentStationName, this.lineColorHex);
        this.loadAds();
    }

    private void initializeProgressBar() {
        progressBar = findViewById(R.id.timeLeftProgressBar);
        timeRemaining = this.stations.size() * 2;
        progressBar.setMax(timeRemaining * 10); // Multiply by 10 for smoother animation
        currentProgress = timeRemaining * 10; // Initialize current progress
        progressBar.setProgress((int)currentProgress); // Set the initial value
        this.startProgressBarTimer();
    }

    private void startProgressBarTimer() {
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentProgress > 0) {
                    currentProgress -= decrementValue * 10; // Decrease smoothly
                    progressBar.setProgress((int)currentProgress); // Update ProgressBar
                    handler.postDelayed(this, TIME_INTERVAL); // Repeat every interval
                } else {
                    // Time out: lose level
                    onTimeOut();
                }
            }
        };
        handler.post(timeRunnable); // Start the timer
    }

    private void stopTimer() {
        if (handler != null && timeRunnable != null) {
            handler.removeCallbacks(timeRunnable);
        }
    }
    private void onTimeOut() {
        stopTimer(); // Stop the timer
        onLostLevel(); // Call the existing method to lose the level
    }


    private void loadAds() {
        adRequest = new AdRequest.Builder().build();

        // Banner
        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView mAdView = findViewById(R.id.adViewGameplay);
        mAdView.loadAd(adRequest);
        // Bonus Interstitial
        this.loadInterstitial(adRequest);
    }

    private void loadInterstitial(AdRequest adRequest) {
        RewardedAd.load(this, "ca-app-pub-8814283715092277/4208494666",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.reproduceMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.stopMusic();
        this.stopTimer();
    }

    private void drawStationView(float marginStart, String stationName, String colorHex) {
        RelativeLayout gameLayout = findViewById(R.id.gameLayout);
        // Layout Params
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        // Margin Start
        layoutParams.setMarginStart(this.transformDpToPixel(marginStart));

        // Add Station View to GameLayout
        StationView stationView = new StationView(this);
        stationView.setLineName(stationName);
        stationView.setColor(colorHex);
        stationView.setLayoutParams(layoutParams);

        stationViews.add(stationView);
        gameLayout.addView(stationView);
    }

    private void reproduceMusic() {
        if (MediaPlayerReproducer.getInstance().getMusicBoolean()) {
            this.mediaPlayerGameplayGameplay = MediaPlayer.create(this, R.raw.music_gameplay_loop);
            this.mediaPlayerGameplayGameplay.setLooping(true);
            this.mediaPlayerGameplayGameplay.start();
            isReproducingGameplayMusic = true;
        }
    }

    private void stopMusic() {
        if (this.mediaPlayerGameplayGameplay != null) {
            this.mediaPlayerGameplayGameplay.stop();
        }
    }



    /**
     * Transform Dp value to pixel
     * @param valueDp dp value that is going to be transformed to pixel
     * @return Pixel value
     */
    private int transformDpToPixel(float valueDp) {
        Resources r = this.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                valueDp,
                r.getDisplayMetrics()
        );
    }


    /**
     * Get the lineName from the Intent
     */
    private void setLineName() {
        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            Toast.makeText(this, "Hubo un problema", Toast.LENGTH_SHORT).show();
            throw new Error("Hubo un problema al iniciar el level");
        }
        this.lineName = extra.getString("LINEA");
        
        // Set the line name in the TextView
        TextView lineNameTextView = findViewById(R.id.lineName);
        if (lineNameTextView != null) {
            lineNameTextView.setText(this.lineName);
        }
    }

    /**
     * Initialize the level data:
     * - Set stations list
     * - Set position to 0
     * - Set stations size
     */
    private void initializeLevelData() {
        this.setStationList();
        this.position = 0;
        this.stationsSize = this.stations.size();
        this.stationViews = new ArrayList<>();
    }

    /**
     * Initialize static level views:
     * - LevelTitleTextView
     * - StationSizeTextView
     */
    private void initializeLevelViews() {
        TextView stationSizeTextView = findViewById(R.id.textViewMaxPosition);

        stationSizeTextView.setText(String.valueOf(this.stationsSize));
    }

    private void setStationList() {
        MetroReaderXML metroReaderXML = new MetroReaderXML(this);
        Metro metro = metroReaderXML.createMetro();
        ArrayList<Line> lines = metro.getLines();
        for (Line tempLine: lines) {
            if (tempLine.getName().equals(lineName)) {
                this.stations = tempLine.getStations();
                this.lineColorHex = tempLine.getColor();
                return;
            }
        }
        throw new Error("Algo fallo");
    }

    /**
     * This method set the current Station Question:
     * - Set current Station Data and View
     * - Set current Alternatives Data and Views
     * @param position position to set the data
     */
    private void setCurrentStationQuestion(int position) {
        // Number Question
        this.setCurrentNumberStationView(position + 1);

        // Current station
        this.setCurrentStationData(position);

        // Alternatives
        this.setCurrentAlternativesData(position);
        this.setCurrentAlternativesViews();

        // StationView
        this.setStationView(position);
    }

    private void scrollView() {
        HorizontalScrollView scrollView = findViewById(R.id.mainScrollView);
        scrollView.setSmoothScrollingEnabled(true);
        scrollView.postDelayed(() -> scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT), 100);
    }

    private void setStationView(int position) {
        if (position == 0) {return;}
        float margin = this.calculateMargin(position);
        this.drawStationView(margin, this.currentStationName, this.lineColorHex);
        this.scrollView();
    }

    private float calculateMargin(int position) {
        float margin = 120;
        float sizeStation = 300;
        float offset = 10 * position;
        return margin + (sizeStation * position) - offset;
    }
    private void setCurrentNumberStationView(int position) {
        TextView textViewCurrentNumberQuestion = findViewById(R.id.textViewCurrentNumberQuestion);
        textViewCurrentNumberQuestion.setText(String.valueOf(position));
    }

    private void setCurrentAlternativesData(int position) {
        // Set correct alternativeData
        this.correctAlternative = this.stations.get(position + 1);
        // Set alternatives data
        PickerStationsAlternative pickerStationsAlternative = new PickerStationsAlternative();

        ArrayList<Station> alternatives = pickerStationsAlternative.getAlternatives(this.stations, position);
        alternatives.add(this.correctAlternative);

        this.alternatives = alternatives;
    }

    private void setCurrentAlternativesViews() {

        ArrayList<TextView> alternativesTextView = new ArrayList<>();

        TextView textViewAlternative1 = findViewById(R.id.textViewAlternative1);
        TextView textViewAlternative2 = findViewById(R.id.textViewAlternative2);
        TextView textViewAlternative3 = findViewById(R.id.textViewAlternative3);
        TextView textViewAlternative4 = findViewById(R.id.textViewAlternative4);

        alternativesTextView.add(textViewAlternative1);
        alternativesTextView.add(textViewAlternative2);
        alternativesTextView.add(textViewAlternative3);
        alternativesTextView.add(textViewAlternative4);


        // Shuffle!!!
        Collections.shuffle(alternativesTextView);

        // Set Text Alternatives
        for (int i = 0; i < alternativesTextView.size(); i++) {
            TextView currentTextView = alternativesTextView.get(i);
            Station currentStation = this.alternatives.get(i);
            currentTextView.setText(currentStation.getName());
        }

        Animation scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scale);


        // OnClickListener
        for (TextView textView: alternativesTextView) {
            String lineName = textView.getText().toString();
            textView.setOnClickListener(view -> {
                view.startAnimation(scaleAnimation);
                this.checkAlternative(lineName);
            });
        }



    }

    private void setCurrentStationData(int position) {
        Station currentStation = this.stations.get(position);
        this.currentStationName = currentStation.getName();
    }

    private void checkAlternative(String alternativeString) {

        if (alternativeString.equals(this.correctAlternative.getName())) {
            // Correct Alternative
            this.onCorrectAlternative();
        } else {
            // Incorrect Alternative
            this.onIncorrectAlternative();

        }
    }

    private void onCorrectAlternative() {
        // Reproduce Correct alternative sound
        MediaPlayerReproducer.getInstance().reproduceCorrectAlternativeSound(this);
        this.position = this.position + 1;
        if (this.position < (this.stations.size() - 1)) {
            this.setCurrentStationQuestion(this.position);
        } else {
            this.onWinLevel();
        }
    }

    public void onWinLevel() {
        this.stopMusicGameplay();
        this.stopTimer(); 
        // Reproduce win sound
        MediaPlayerReproducer.getInstance().reproduceSoundWinLevel(this);
        this.updateProgressInfo();
        this.showWinningDialog();
    }

    private void resetLevel() {
        Intent intent = new Intent(this.getApplicationContext(), PlayGameActivity.class);
        intent.putExtra("LINEA", this.lineName);
        this.startActivity(intent);
    }

    private void updateProgressInfo() {

        SharedPreferences sharedPref = this.getSharedPreferences(
                String.valueOf(R.string.app_name),Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        // Check if we have updated this level before
        if(!sharedPref.getBoolean(lineName, false)) {
            int lastScore = sharedPref.getInt("score", 0);
            // Score
            if (lastScore < 6) {
                editor.putInt("score",lastScore + 1);
            }
            // Star
            editor.putBoolean(this.lineName,true);
            editor.apply();
        }
    }

    private void showWinningDialog() {
        // Show dialog
        Dialog winningDialog = new Dialog(this);
        winningDialog.setCancelable(false);
        winningDialog.setCanceledOnTouchOutside(false);
        winningDialog.setContentView(R.layout.winning_dialog);

        // Button
        Button winningButton = winningDialog.findViewById(R.id.buttonResetConfirmationDialog);

        winningButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuMetroActivity.class);
            intent.putExtra("RateIt",true);
            startActivity(intent);
        });
        winningDialog.show();
    }


    private void onIncorrectAlternative() {
        this.onLostLevel();
    }

    private void stopMusicGameplay() {
        if(isReproducingGameplayMusic) {
            if(mediaPlayerGameplayGameplay != null) {
                this.mediaPlayerGameplayGameplay.stop();
                isReproducingGameplayMusic = false;
            }
        }
    }

    private void onLostLevel() {
        // Stop Gameplay Music
        this.stopMusicGameplay();
        // Stop the timer
        this.stopTimer();
        MediaPlayerReproducer.getInstance().reproduceLostSound(this);

        Dialog lostDialog = new Dialog(this);
        lostDialog.setCancelable(false);
        lostDialog.setCanceledOnTouchOutside(false);
        lostDialog.setContentView(R.layout.lost_dialog);

        ImageView resetLevelImageView = lostDialog.findViewById(R.id.imageViewResetLevel);
        ImageView imageViewAds = lostDialog.findViewById(R.id.imageViewAds);

        resetLevelImageView.setOnClickListener(listener-> {
            MediaPlayerReproducer.getInstance().reproduceClickSound(this);
            this.resetLevel();
        });

        imageViewAds.setOnClickListener(listener -> this.showAds(lostDialog));

        lostDialog.show();
    }

    private void showAds(Dialog lostDialog) {
        // Add this flag for local testing
        // Set to true for local testing, false for production
        boolean isTestMode = false;
        if (isTestMode) {
            // Simulate ad viewing in test mode
            new Handler().postDelayed(() -> {
                // Simulate ad completion after 1 second
                lostDialog.dismiss();
                // Restart timer and continue the game
                restartTimer();
                // Resume gameplay music if it was enabled
                if (!isReproducingGameplayMusic && MediaPlayerReproducer.getInstance().getMusicBoolean()) {
                    reproduceMusic();
                }
                Toast.makeText(this, "Test Mode: Ad simulation completed", Toast.LENGTH_SHORT).show();
            }, 1000);
        } else if (rewardedAd != null) {
            rewardedAd.show(this, rewardItem -> {
                // Handle the reward.
                lostDialog.dismiss();
                loadInterstitial(adRequest);
                // Restart timer and continue the game
                restartTimer();
                // Resume gameplay music if it was enabled
                if (!isReproducingGameplayMusic && MediaPlayerReproducer.getInstance().getMusicBoolean()) {
                    reproduceMusic();
                }
            });
        } else {
            Toast.makeText(this, "No ad available", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to restart the timer
    private void restartTimer() {
        // Reset progress value
        currentProgress = timeRemaining * 10;
        progressBar.setProgress((int)currentProgress);
        
        // Start timer again
        startProgressBarTimer();
    }

    public void goBackToMenu(View view) {
        MediaPlayerReproducer.getInstance().reproduceClickSound(this);
        Intent intent = new Intent(this, MenuMetroActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.goBackToMenu(null);
    }

}