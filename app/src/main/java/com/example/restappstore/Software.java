package com.example.restappstore;

public class Software {
    private int id;
    private String nombre;
    private int espacio;
    private String version;
    private double precio;

    public Software(int id, String nombre, int espacio, String version, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.espacio = espacio;
        this.version = version;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEspacio() {
        return espacio;
    }

    public void setEspacio(int espacio) {
        this.espacio = espacio;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}