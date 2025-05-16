package com.example.restappstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listar extends AppCompatActivity implements SoftwareAdapter.OnDeleteClickListener, SoftwareAdapter.OnEditClickListener {

    //EndPoint (ruta)
    private final String BASE_URL = "https://rest-api-software-production-46c8.up.railway.app/api/softwares";

    //Objeto para REST API
    RequestQueue requestQueue;

    //UI
    ListView listaSoftwares;

    //Datos
    ArrayList<Software> listaSoftware = new ArrayList<>();
    SoftwareAdapter adaptador;

    private void loadUI() {
        listaSoftwares = findViewById(R.id.listaSoftwares);
        adaptador = new SoftwareAdapter(this, listaSoftware);
        adaptador.setOnDeleteClickListener(this);
        adaptador.setOnEditClickListener(this); // Añadimos el listener de edición
        listaSoftwares.setAdapter(adaptador);
    }

    /**
     * Obtener los datos del servicio externo REST API y los renderiza en la aplicación
     */
    private void getData() {
        //Paso 1: Habilita un canal de comunicación
        requestQueue = Volley.newRequestQueue(this);

        //Paso 2: Preparar una solicitud
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Lista: ", response.toString());
                        listaSoftware.clear();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject software = response.getJSONObject(i);
                                int id = software.getInt("id");
                                String nombre = software.getString("nombre");
                                int espacio = software.getInt("espaciomb");
                                String version = software.getString("versionsoft");
                                double precio = software.getDouble("precio");

                                Software softwareObj = new Software(id, nombre, espacio, version, precio);
                                listaSoftware.add(softwareObj);
                            }
                            adaptador.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e("Error JSON:", e.toString());
                            Toast.makeText(Listar.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WS: ", error.toString());
                        Toast.makeText(Listar.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //Paso 3: Enviar la solicitud por el canal
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Eliminar un software de la API
     */
    private void deleteSoftware(Software software) {
        String deleteUrl = BASE_URL + "/" + software.getId();

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                deleteUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Eliminación: ", response.toString());
                        Toast.makeText(Listar.this, "Software eliminado correctamente", Toast.LENGTH_SHORT).show();
                        // Actualizar la lista después de eliminar
                        getData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error WS: ", error.toString());
                        Toast.makeText(Listar.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonRequest);
    }

    @Override
    public void onDeleteClick(Software software) {
        // Mostrar diálogo de confirmación
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Confirmar eliminación");
        builder.setMessage("¿Estás seguro de que quieres eliminar este producto?");

        // Botón Sí
        builder.setPositiveButton("Sí", (dialog, which) -> {
            deleteSoftware(software);
        });

        // Botón No
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        // Mostrar el diálogo
        builder.create().show();
    }

    @Override
    public void onEditClick(Software software) {
        // Crear un intent para abrir la actividad de edición
        Intent intent = new Intent(this, Editar.class);

        // Pasar los datos del software como extras
        intent.putExtra("id", software.getId());
        intent.putExtra("nombre", software.getNombre());
        intent.putExtra("espacio", software.getEspacio());
        intent.putExtra("version", software.getVersion());
        intent.putExtra("precio", software.getPrecio());

        // Iniciar la actividad
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });
        //el funcionamiento
        loadUI();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista cuando regresamos a esta actividad
        getData();
    }
}