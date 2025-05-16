package com.example.restappstore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

public class Buscar extends AppCompatActivity {

    private final String BASE_URL = "https://rest-api-software-production-46c8.up.railway.app/api/softwares";

    RequestQueue requestQueue;

    Button btnBuscarSoftware;
    EditText txtIdBuscar;

    // UI para mostrar resultados
    LinearLayout resultadoLayout;
    TextView tvNombreResultado, tvVersionResultado, tvEspacioResultado, tvPrecioResultado;

    private void loadUI() {
        btnBuscarSoftware = findViewById(R.id.btnBuscarSoftware);
        txtIdBuscar = findViewById(R.id.txtIdBuscar);

        // Referencias a la UI de resultados
        resultadoLayout = findViewById(R.id.resultadoLayout);
        tvNombreResultado = findViewById(R.id.tvNombreResultado);
        tvVersionResultado = findViewById(R.id.tvVersionResultado);
        tvEspacioResultado = findViewById(R.id.tvEspacioResultado);
        tvPrecioResultado = findViewById(R.id.tvPrecioResultado);

        // Inicialmente ocultar el layout de resultados
        resultadoLayout.setVisibility(View.GONE);
    }

    /**
     * Busca un software por su ID
     */
    private void buscarPorId() {
        // Validar que se haya ingresado un ID
        if (txtIdBuscar.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Debes ingresar un ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el ID ingresado
        int id;
        try {
            id = Integer.parseInt(txtIdBuscar.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El ID debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear la URL con el ID específico
        String urlBusqueda = BASE_URL + "/" + id;

        // Inicializar RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Crear la solicitud GET
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                urlBusqueda,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Búsqueda: ", response.toString());

                        try {
                            // Extraer datos del software
                            String nombre = response.getString("nombre");
                            int espacio = response.getInt("espaciomb");
                            String version = response.getString("versionsoft");
                            double precio = response.getDouble("precio");

                            // Mostrar los datos en la UI
                            tvNombreResultado.setText("Nombre: " + nombre);
                            tvVersionResultado.setText("Versión: " + version);
                            tvEspacioResultado.setText("Espacio: " + espacio + " MB");
                            tvPrecioResultado.setText("Precio: $" + precio);

                            // Mostrar el layout de resultados
                            resultadoLayout.setVisibility(View.VISIBLE);

                            Toast.makeText(Buscar.this, "Software encontrado", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            Log.e("Error JSON: ", e.toString());
                            Toast.makeText(Buscar.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                            resultadoLayout.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WS: ", error.toString());

                        // Verificar si es un error 404 (no encontrado)
                        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                            Toast.makeText(Buscar.this, "Software no encontrado con el ID: " + id, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Buscar.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }

                        // Ocultar el layout de resultados
                        resultadoLayout.setVisibility(View.GONE);
                    }
                }
        );

        // Enviar la solicitud
        requestQueue.add(jsonRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });

        loadUI();

        btnBuscarSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarPorId();
            }
        });
    }
}