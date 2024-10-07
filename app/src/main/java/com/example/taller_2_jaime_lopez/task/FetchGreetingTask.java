package com.example.taller_2_jaime_lopez.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class FetchGreetingTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "FetchGreetingTask";

    @SuppressLint("StaticFieldLeak")
    private final Context context;

    @SuppressLint("StaticFieldLeak")
    private final TextView greetingTextView;

    public FetchGreetingTask(Context context, TextView greetingTextView) {
        this.context = context;
        this.greetingTextView = greetingTextView;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            // Simulacion del tiempo de espera de la red
            Thread.sleep(2000);

            // Simulacion de una respuesta JSON desde el servidor
            String jsonResponse = "{ \"greeting\": \"TALLER 2 - MENU\" }";
            JSONObject jsonObject = new JSONObject(jsonResponse);

            return jsonObject.getString("greeting");

        } catch (Exception e) {
            Log.e(TAG, "Error al simular la tarea de red.", e);
            return "Error al cargar el mensaje de bienvenida.";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Actualiza la UI con el saludo obtenido de la "tarea de red"
        greetingTextView.setText(result);
        Toast.makeText(context, "Carga finalizada", Toast.LENGTH_SHORT).show();
    }
}
