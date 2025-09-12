/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author aerod
 */
public class PasoAFD {
    private String estadoActual;
    private String simbolo;
    private String estadoSiguiente;

    public PasoAFD(String estadoActual, String simbolo, String estadoSiguiente) {
        this.estadoActual = estadoActual;
        this.simbolo = simbolo;
        this.estadoSiguiente = estadoSiguiente;
    }

    public String getEstadoActual() { return estadoActual; }
    public String getSimbolo() { return simbolo; }
    public String getEstadoSiguiente() { return estadoSiguiente; }

    @Override
    public String toString() {
        return estadoActual + " --" + simbolo + "--> " + estadoSiguiente;
    }
}
