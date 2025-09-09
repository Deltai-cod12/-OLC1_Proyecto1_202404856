/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author aerod
 */
public class TransicionAP {
    private Estado origen;
    private String simboloEntrada;   // símbolo leído de la cadena
    private String simboloExtrae;    // símbolo que saca de la pila
    private Estado destino;
    private String simboloInserta;   // símbolo que mete en la pila

    // Constructor por defecto (necesario para el parser)
    public TransicionAP() {
    }

    // Constructor con parámetros
    public TransicionAP(Estado origen, String simboloEntrada, String simboloExtrae, Estado destino, String simboloInserta) {
        this.origen = origen;
        this.simboloEntrada = simboloEntrada;
        this.simboloExtrae = simboloExtrae;
        this.destino = destino;
        this.simboloInserta = simboloInserta;
    }

    // Setters (necesarios para el parser)
    public void setOrigen(Estado origen) {
        this.origen = origen;
    }

    public void setSimboloEntrada(String simboloEntrada) {
        this.simboloEntrada = simboloEntrada;
    }

    public void setSimboloExtrae(String simboloExtrae) {
        this.simboloExtrae = simboloExtrae;
    }

    public void setDestino(Estado destino) {
        this.destino = destino;
    }

    public void setSimboloInserta(String simboloInserta) {
        this.simboloInserta = simboloInserta;
    }

    // Getters
    public Estado getOrigen() {
        return origen;
    }

    public String getSimboloEntrada() {
        return simboloEntrada;
    }

    public String getSimboloExtrae() {
        return simboloExtrae;
    }

    public Estado getDestino() {
        return destino;
    }

    public String getSimboloInserta() {
        return simboloInserta;
    }

    @Override
    public String toString() {
        if (origen == null || destino == null) {
            return "TransicionAP[INVALIDA]";
        }
        return origen.getNombre() + " (" + simboloEntrada + ") -> (" + simboloExtrae + "), " + destino.getNombre() + " : (" + simboloInserta + ")";
    }
}