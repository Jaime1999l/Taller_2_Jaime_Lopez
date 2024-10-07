package com.example.taller_2_jaime_lopez.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.taller_2_jaime_lopez.R;
import com.example.taller_2_jaime_lopez.util.BackgroundUtil;

public class ConfiguracionActivity extends AppCompatActivity {

    private static final String TAG = "ConfiguracionActivity";
    private BackgroundUtil backgroundUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        backgroundUtil = new BackgroundUtil(this);

        // Aplicar el fondo al iniciar la configuración.
        backgroundUtil.applyBackground(this);

        RadioGroup radioGroupBackgroundType = findViewById(R.id.radioGroupBackgroundType);
        LinearLayout colorSelectionLayout = findViewById(R.id.colorSelectionLayout);
        LinearLayout imageSelectionLayout = findViewById(R.id.imageSelectionLayout);
        CardView colorRedButton = findViewById(R.id.colorRedButton);
        CardView colorGreenButton = findViewById(R.id.colorGreenButton);
        CardView colorBlueButton = findViewById(R.id.colorBlueButton);
        CardView image1Button = findViewById(R.id.image1Button);
        CardView image2Button = findViewById(R.id.image2Button);
        Button backButton = findViewById(R.id.backButton);

        radioGroupBackgroundType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonColor) {
                colorSelectionLayout.setVisibility(LinearLayout.VISIBLE);
                imageSelectionLayout.setVisibility(LinearLayout.GONE);
            } else if (checkedId == R.id.radioButtonImage) {
                colorSelectionLayout.setVisibility(LinearLayout.GONE);
                imageSelectionLayout.setVisibility(LinearLayout.VISIBLE);
            }
        });

        colorRedButton.setOnClickListener(v -> setColorAndReturn(Color.RED));
        colorGreenButton.setOnClickListener(v -> setColorAndReturn(Color.GREEN));
        colorBlueButton.setOnClickListener(v -> setColorAndReturn(Color.BLUE));
        image1Button.setOnClickListener(v -> setImageAndReturn(R.drawable.background_image1));
        image2Button.setOnClickListener(v -> setImageAndReturn(R.drawable.background_image2));

        backButton.setOnClickListener(v -> finish());
    }

    private void setColorAndReturn(int color) {
        SharedPreferences.Editor editor = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).edit();
        editor.putString("backgroundType", "color");
        editor.putInt("backgroundValue", color);
        editor.apply();
        Log.d(TAG, "Color background saved: " + color);
        backgroundUtil.applyBackground(this);
    }

    private void setImageAndReturn(int imageResId) {
        SharedPreferences.Editor editor = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).edit();
        editor.putString("backgroundType", "image");
        editor.putInt("backgroundValue", imageResId);
        editor.apply();
        Log.d(TAG, "Image background saved: " + imageResId);
        backgroundUtil.applyBackground(this);
    }
}


