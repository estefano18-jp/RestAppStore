package com.example.restappstore;

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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

public class Registrar extends AppCompatActivity {

    private final String URL = "https://rest-api-software-production-46c8.up.railway.app/api/softwares";

    RequestQueue requestQueue;

    Button btnRegistrarSoftware;
    EditText txtNombre, txtEspacio, txtVersion, txtPrecio;

    private void loadUI()
    {
        btnRegistrarSoftware = findViewById(R.id.btnRegistrarSoftware);
        txtNombre = findViewById(R.id.txtNombre);
        txtEspacio = findViewById(R.id.txtEspacio);
        txtVersion = findViewById(R.id.txtVersion);
        txtPrecio = findViewById(R.id.txtPrecio);
    }

    /**
     * Envia los datos recolectados al REST API - utilizado POST
     */
    private void saveData()
    {
        //Paso 1
        requestQueue = Volley.newRequestQueue(this);

        //Paso 1.1 - Creacion de un JSON
        JSONObject datos = new JSONObject();

        //Paso 1.2 - Guardar la informacion en el JSON
        try{
            String nombre = txtNombre.getText().toString().trim();
            String espacio = txtEspacio.getText().toString().trim();
            String version = txtVersion.getText().toString().trim();
            String precio = txtPrecio.getText().toString().trim();

            // Validación básica
            if(nombre.isEmpty() || espacio.isEmpty() || version.isEmpty() || precio.isEmpty()) {
                Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                return;
            }

            datos.put("nombre", nombre);
            datos.put("espaciomb", Integer.parseInt(espacio));
            datos.put("versionsoft", version);
            datos.put("precio", Double.parseDouble(precio));
        }catch (Exception e){
            Log.e("Error JSON", "Los valores son incorrectos");
            Toast.makeText(this, "Formato de datos incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }

        //Paso 2
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                datos,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Resultado: ", response.toString());
                        Toast.makeText(Registrar.this, "Software registrado correctamente", Toast.LENGTH_SHORT).show();
                        // Limpiar los campos
                        txtNombre.setText("");
                        txtEspacio.setText("");
                        txtVersion.setText("");
                        txtPrecio.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WS: ", error.toString());
                        Toast.makeText(Registrar.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //Paso 3
        requestQueue.add(jsonRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUI();

        btnRegistrarSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
}