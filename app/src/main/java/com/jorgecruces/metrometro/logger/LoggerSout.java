package com.jorgecruces.metrometro.logger;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class LoggerSout {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void printArrayList(ArrayList<?> list) {
        list.forEach(System.out::println);
    }
}
