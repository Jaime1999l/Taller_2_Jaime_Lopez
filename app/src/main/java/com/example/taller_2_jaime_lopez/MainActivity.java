package com.example.taller_2_jaime_lopez;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.taller_2_jaime_lopez.activity.ConfiguracionActivity;
import com.example.taller_2_jaime_lopez.activity.NameActivity;
import com.example.taller_2_jaime_lopez.task.FetchGreetingTask;
import com.example.taller_2_jaime_lopez.util.BackgroundUtil;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView greetingTextView;
    private ExecutorService executorService;
    private String lastGreeting = "";
    BackgroundUtil backgroundUtil;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundUtil = new BackgroundUtil(this);

        // Aplicar el fondo al iniciar la actividad.
        backgroundUtil.applyBackground(this);

        greetingTextView = findViewById(R.id.greetingTextView);
        Button mainActivityButton = findViewById(R.id.mainActivityButton);
        Button configActivityButton = findViewById(R.id.configActivityButton);
        ConstraintLayout mainLayout = findViewById(R.id.mainLayout);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        startGreetingChecker();

        // Iniciar la tarea simulada de red para obtener el saludo
        new FetchGreetingTask(this, greetingTextView).execute();

        mainActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NameActivity.class);
            startActivity(intent);
        });

        configActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundUtil.applyBackground(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener el executor cuando la actividad se destruya para evitar fugas de memoria
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        backgroundUtil.shutdown();
    }

    private void startGreetingChecker() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            while (!executorService.isShutdown()) {
                // Obtenemos el saludo adecuado según la hora del día
                String newGreeting = getGreetingMessage();

                // Si el saludo ha cambiado, actualizamos la UI desde el hilo principal
                if (!newGreeting.equals(lastGreeting)) {
                    lastGreeting = newGreeting;
                    runOnUiThread(() -> updateGreeting(newGreeting));
                }

                // Calculamos cuánto tiempo falta para el próximo cambio significativo.
                try {
                    long sleepTime = calculateNextCheckDelay();
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Log.e(TAG, "El hilo de verificación del saludo fue interrumpido", e);
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private long calculateNextCheckDelay() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Calcular la cantidad de milisegundos hasta la próxima hora relevante:
        // - Si es de mañana (entre 6 y 12), esperar hasta las 12:00.
        // - Si es de tarde (entre 12 y 18), esperar hasta las 18:00.
        // - Si es de noche (después de las 18), esperar hasta las 6:00 del día siguiente.
        long delay;

        if (hour >= 6 && hour < 12) {
            delay = getMillisUntilNextHour(12);
        } else if (hour >= 12 && hour < 18) {
            delay = getMillisUntilNextHour(18);
        } else {
            delay = getMillisUntilNextHour(6);
        }

        Log.d(TAG, "Esperando " + delay + " milisegundos antes de la próxima verificación de saludo.");
        return delay;
    }

    private long getMillisUntilNextHour(int targetHour) {
        Calendar now = Calendar.getInstance();
        Calendar next = (Calendar) now.clone();
        next.set(Calendar.HOUR_OF_DAY, targetHour);
        next.set(Calendar.MINUTE, 0);
        next.set(Calendar.SECOND, 0);
        next.set(Calendar.MILLISECOND, 0);

        // Si la hora objetivo ya ha pasado hoy, ajustar para el día siguiente.
        if (next.before(now)) {
            next.add(Calendar.DAY_OF_MONTH, 1);
        }

        return next.getTimeInMillis() - now.getTimeInMillis();
    }

    private String getGreetingMessage() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String greeting;

        if (hour >= 6 && hour < 12) {
            greeting = "¡Buenos días!";
        } else if (hour >= 12 && hour < 18) {
            greeting = "¡Buenas tardes!";
        } else {
            greeting = "¡Buenas noches!";
        }

        return greeting;
    }

    private void updateGreeting(String newGreeting) {
        // Mostrar el saludo en el TextView
        greetingTextView.setText(newGreeting);
        Log.d(TAG, "Saludo actualizado: " + newGreeting);
        Toast.makeText(MainActivity.this, "Saludo actualizado: " + newGreeting, Toast.LENGTH_SHORT).show();
    }
}







