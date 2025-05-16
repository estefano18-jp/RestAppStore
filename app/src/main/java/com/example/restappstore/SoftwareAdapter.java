package com.example.restappstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SoftwareAdapter extends ArrayAdapter<Software> {

    private OnDeleteClickListener deleteListener;
    private OnEditClickListener editListener;

    public SoftwareAdapter(Context context, ArrayList<Software> softwares) {
        super(context, 0, softwares);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el software para esta posición
        Software software = getItem(position);

        // Verificar si una vista existente está siendo reutilizada, de lo contrario inflar la vista
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_software, parent, false);
        }

        // Referencias a los elementos de la UI en cada ítem
        TextView tvNombre = convertView.findViewById(R.id.tvNombre);
        TextView tvVersion = convertView.findViewById(R.id.tvVersion);
        TextView tvEspacio = convertView.findViewById(R.id.tvEspacio);
        TextView tvPrecio = convertView.findViewById(R.id.tvPrecio);
        Button btnEliminar = convertView.findViewById(R.id.btnEliminar);
        Button btnEditar = convertView.findViewById(R.id.btnEditar);

        // Asignar valores
        tvNombre.setText(software.getNombre());
        tvVersion.setText("Versión: " + software.getVersion());
        tvEspacio.setText("Espacio: " + software.getEspacio() + " MB");
        tvPrecio.setText("Precio: $" + software.getPrecio());

        // Configurar botón eliminar
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(software);
                }
            }
        });

        // Configurar botón editar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editListener != null) {
                    editListener.onEditClick(software);
                }
            }
        });

        return convertView;
    }

    // Interfaz para el evento de clic en eliminar
    public interface OnDeleteClickListener {
        void onDeleteClick(Software software);
    }

    // Interfaz para el evento de clic en editar
    public interface OnEditClickListener {
        void onEditClick(Software software);
    }

    // Método para establecer el listener de eliminación
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    // Método para establecer el listener de edición
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editListener = listener;
    }
}