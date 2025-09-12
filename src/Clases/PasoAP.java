/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author aerod
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PasoAP {
    private String estadoActual;      // Estado en el que estamos
    private String simboloEntrada;    // Símbolo leído de la entrada
    private String simboloExtrae;     // Símbolo que se extrae de la pila
    private List<String> simbolosInserta; // Símbolos que se insertan en la pila (pueden ser varios)
    private String estadoSiguiente;   // Estado al que se mueve

    public PasoAP(String estadoActual, String simboloEntrada, String simboloExtrae, List<String> simbolosInserta, String estadoSiguiente) {
        this.estadoActual = estadoActual;
        this.simboloEntrada = simboloEntrada;
        this.simboloExtrae = simboloExtrae;
        this.simbolosInserta = simbolosInserta;
        this.estadoSiguiente = estadoSiguiente;
    }

    // Getters
    public String getEstadoActual() { return estadoActual; }
    public String getSimboloEntrada() { return simboloEntrada; }
    public String getSimboloExtrae() { return simboloExtrae; }
    public List<String> getSimbolosInserta() { return simbolosInserta; }
    public String getEstadoSiguiente() { return estadoSiguiente; }

    @Override
    public String toString() {
        return estadoActual + " --" + simboloEntrada + "/" + simboloExtrae + "->" + simbolosInserta + "--> " + estadoSiguiente;
    }

    // Método estático para convertir una lista de pasos en texto para debug
    public static String mostrarPasos(List<PasoAP> pasos) {
        StringBuilder sb = new StringBuilder();
        for (PasoAP paso : pasos) {
            sb.append(paso.toString()).append("\n");
        }
        return sb.toString();
    }
}
