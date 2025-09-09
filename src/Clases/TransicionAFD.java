/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author aerod
 */
public class TransicionAFD {
    private Estado origen;
    private String simbolo;
    private Estado destino;
    
    // Constructor por defecto
    public TransicionAFD() {}
    
    // Constructor con parámetros
    public TransicionAFD(Estado origen, String simbolo, Estado destino) {
        this.origen = origen;
        this.simbolo = simbolo;
        this.destino = destino;
    }
    
    // Setters
    public void setOrigen(Estado origen) { this.origen = origen; }
    public void setSimbolo(String simbolo) { this.simbolo = simbolo; }
    public void setDestino(Estado destino) { this.destino = destino; }
    
    // Getters
    public Estado getOrigen() { return origen; }
    public String getSimbolo() { return simbolo; }
    public Estado getDestino() { return destino; }
    
    @Override
    public String toString() {
        if (origen == null || destino == null) {
            return "TransicionAFD[INVALIDA]";
        }
        return origen.getNombre() + " --" + simbolo + "--> " + destino.getNombre();
    }
}