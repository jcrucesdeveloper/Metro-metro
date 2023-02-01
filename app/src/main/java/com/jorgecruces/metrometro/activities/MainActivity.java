package com.jorgecruces.metrometro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.jorgecruces.metrometro.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void goNextActivity(View view) {
        Intent intent = new Intent(this, MenuMetroActivity.class);
        startActivity(intent);
    }

    public void resetButton(View view) {
        SharedPreferences sharedPref = this.getSharedPreferences(
                String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        Toast.makeText(this, "SharedPref Reset", Toast.LENGTH_SHORT).show();
    }
}
