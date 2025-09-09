/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author aerod
 */
public class Estado {
    private String nombre;
    private boolean esInicial;
    private boolean esAceptacion;

    public Estado(String nombre) {
        this.nombre = nombre;
        this.esInicial = false;
        this.esAceptacion = false;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isInicial() {
        return esInicial;
    }

    public void setInicial(boolean esInicial) {
        this.esInicial = esInicial;
    }

    public boolean isAceptacion() {
        return esAceptacion;
    }

    public void setAceptacion(boolean esAceptacion) {
        this.esAceptacion = esAceptacion;
    }

    @Override
    public String toString() {
        return nombre + (esInicial ? " (Inicial)" : "") + (esAceptacion ? " (Aceptación)" : "");
    }
}
