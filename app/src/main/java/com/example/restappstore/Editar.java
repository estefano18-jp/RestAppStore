package com.example.restappstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Editar extends AppCompatActivity {

    private final String BASE_URL = "https://rest-api-software-production-46c8.up.railway.app/api/softwares";

    RequestQueue requestQueue;

    Button btnActualizarSoftware;
    EditText txtNombre, txtEspacio, txtVersion, txtPrecio;

    // Datos del software a editar
    private int softwareId;
    private String nombre;
    private int espacio;
    private String version;
    private double precio;

    private void loadUI() {
        btnActualizarSoftware = findViewById(R.id.btnActualizarSoftware);
        txtNombre = findViewById(R.id.txtNombreEditar);
        txtEspacio = findViewById(R.id.txtEspacioEditar);
        txtVersion = findViewById(R.id.txtVersionEditar);
        txtPrecio = findViewById(R.id.txtPrecioEditar);
    }

    /**
     * Carga los datos del software en los campos
     */
    private void loadSoftwareData() {
        txtNombre.setText(nombre);
        txtEspacio.setText(String.valueOf(espacio));
        txtVersion.setText(version);
        txtPrecio.setText(String.valueOf(precio));
    }

    /**
     * Actualiza los datos del software en la API
     */
    private void updateData() {
        // Paso 1
        requestQueue = Volley.newRequestQueue(this);

        // Paso 1.1 - Creación de un JSON
        JSONObject datos = new JSONObject();

        // Paso 1.2 - Guardar la información en el JSON
        try {
            String nombreActualizado = txtNombre.getText().toString().trim();
            String espacioActualizado = txtEspacio.getText().toString().trim();
            String versionActualizada = txtVersion.getText().toString().trim();
            String precioActualizado = txtPrecio.getText().toString().trim();

            // Validación básica
            if (nombreActualizado.isEmpty() || espacioActualizado.isEmpty() ||
                    versionActualizada.isEmpty() || precioActualizado.isEmpty()) {
                Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                return;
            }

            datos.put("nombre", nombreActualizado);
            datos.put("espaciomb", Integer.parseInt(espacioActualizado));
            datos.put("versionsoft", versionActualizada);
            datos.put("precio", Double.parseDouble(precioActualizado));
        } catch (Exception e) {
            Log.e("Error JSON", "Los valores son incorrectos: " + e.getMessage());
            Toast.makeText(this, "Formato de datos incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }

        // Paso 2 - Crear la URL para actualizar
        String updateUrl = BASE_URL + "/" + softwareId;

        // Paso 3 - Crear la solicitud
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.PUT,
                updateUrl,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Actualización: ", response.toString());
                        Toast.makeText(Editar.this, "Software actualizado correctamente", Toast.LENGTH_SHORT).show();

                        // Regresar a la actividad de listar
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WS: ", error.toString());
                        Toast.makeText(Editar.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Paso 4 - Enviar la solicitud
        requestQueue.add(jsonRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });

        // Obtener los datos del intent
        Intent intent = getIntent();
        softwareId = intent.getIntExtra("id", 0);
        nombre = intent.getStringExtra("nombre");
        espacio = intent.getIntExtra("espacio", 0);
        version = intent.getStringExtra("version");
        precio = intent.getDoubleExtra("precio", 0.0);

        loadUI();
        loadSoftwareData();

        // En tu método onCreate después de loadUI():
        Button btnCancelarEdicion = findViewById(R.id.btnCancelarEdicion);
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cierra esta actividad y vuelve a la anterior
                finish();
            }
        });

        btnActualizarSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }
}