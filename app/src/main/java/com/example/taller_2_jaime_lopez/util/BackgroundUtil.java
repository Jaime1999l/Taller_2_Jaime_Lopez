package com.example.taller_2_jaime_lopez.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.example.taller_2_jaime_lopez.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundUtil {

    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String KEY_BACKGROUND_TYPE = "backgroundType";
    private static final String KEY_BACKGROUND_VALUE = "backgroundValue";
    private static final String TAG = "BackgroundUtil";

    private final SharedPreferences sharedPreferences;
    private final ExecutorService executorService;

    public BackgroundUtil(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void applyBackground(Activity activity) {
        executorService.execute(() -> {
            // Recuperar el tipo de fondo y el valor desde las preferencias.
            String backgroundType = sharedPreferences.getString(KEY_BACKGROUND_TYPE, "color");
            int backgroundValue = sharedPreferences.getInt(KEY_BACKGROUND_VALUE, Color.WHITE);

            Log.d(TAG, "Background Type: " + backgroundType);
            Log.d(TAG, "Background Value: " + backgroundValue);

            // Actualizar la UI desde el hilo principal.
            activity.runOnUiThread(() -> {
                View rootView = activity.getWindow().getDecorView().getRootView();

                if ("color".equals(backgroundType)) {
                    Log.d(TAG, "Applying color background: " + backgroundValue);
                    rootView.setBackgroundColor(backgroundValue);
                } else if ("image".equals(backgroundType)) {
                    Log.d(TAG, "Applying image background: " + backgroundValue);
                    rootView.setBackgroundResource(backgroundValue);
                }
            });
        });
    }

    public void shutdown() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}



