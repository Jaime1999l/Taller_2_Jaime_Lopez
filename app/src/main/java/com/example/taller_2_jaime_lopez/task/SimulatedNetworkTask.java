package com.example.taller_2_jaime_lopez.task;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.taller_2_jaime_lopez.activity.NameActivity;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulatedNetworkTask {

    private static final String TAG = "SimulatedNetworkTask";
    private final Context context;
    private final TextView resultTextView;
    private final ExecutorService executorService;

    public SimulatedNetworkTask(Context context, TextView resultTextView) {
        this.context = context;
        this.resultTextView = resultTextView;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void execute() {
        executorService.execute(() -> {
            try {
                // Simula una espera de red de 3 segundos (como si fuese una llamada HTTP)
                Thread.sleep(3000);

                // Simulamos la respuesta JSON
                String jsonResponse = "{ \"status\": \"success\", \"message\": \"Datos cargados correctamente\", \"data\": { \"greeting\": \"Operacion HTTP realizada con exito\", \"time\": \"2024-10-07 10:30:00\" }}";
                JSONObject jsonObject = new JSONObject(jsonResponse);

                // Extraer información del JSON
                String status = jsonObject.getString("status");
                String message = jsonObject.getString("message");
                JSONObject data = jsonObject.getJSONObject("data");
                String greeting = data.getString("greeting");
                String time = data.getString("time");

                // Crear un resultado a mostrar en la UI.
                String result = "Estado: " + status + "\nMensaje: " + message + "\nSaludo: " + greeting + "\nHora: " + time;

                // Actualizar la UI desde el hilo principal.
                ((NameActivity) context).runOnUiThread(() -> {
                    resultTextView.setText(result);
                    Toast.makeText(context, "Datos cargados desde la simulación de red.", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                Log.e(TAG, "Error al simular la tarea de red.", e);
                ((NameActivity) context).runOnUiThread(() -> {
                    resultTextView.setText("Error al cargar los datos.");
                    Toast.makeText(context, "Error al simular la tarea de red.", Toast.LENGTH_SHORT).show();
                });
            } finally {
                executorService.shutdown();
            }
        });
    }
}

