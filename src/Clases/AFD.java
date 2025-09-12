/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author aerod
 */
public class AFD extends Automata {
    private List<TransicionAFD> transiciones;
    private List<PasoAFD> ultimosPasos;

    public AFD(String nombre, List<Estado> estados, List<String> alfabeto, Estado inicial, List<Estado> aceptacion, List<TransicionAFD> transiciones) {
        super(nombre);
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = inicial;
        this.estadosAceptacion = aceptacion;
        this.transiciones = transiciones;
    }

    @Override
    public boolean validarCadena(String cadena) {
        Estado actual = estadoInicial;
        for (char c : cadena.toCharArray()) {
            String simbolo = String.valueOf(c);
            TransicionAFD encontrada = null;
            for (TransicionAFD t : transiciones) {
                if (t.getOrigen().equals(actual) && t.getSimbolo().equals(simbolo)) {
                    encontrada = t;
                    break;
                }
            }
            if (encontrada == null) return false;
            actual = encontrada.getDestino();
        }
        return estadosAceptacion.contains(actual);
    }

    public List<TransicionAFD> getTransiciones() {
        return transiciones;
    }

    // Nuevo método para registrar pasos
    public List<PasoAFD> validarCadenaConPasos(String cadena) {
        List<PasoAFD> pasos = new ArrayList<>();
        Estado actual = estadoInicial;

        for (char c : cadena.toCharArray()) {
            String simbolo = String.valueOf(c);
            TransicionAFD encontrada = null;

            for (TransicionAFD t : transiciones) {
                if (t.getOrigen().equals(actual) && t.getSimbolo().equals(simbolo)) {
                    encontrada = t;
                    break;
                }
            }

            if (encontrada == null) {
                pasos.add(new PasoAFD(actual.getNombre(), simbolo, "ERROR"));
                ultimosPasos = pasos; // guardar pasos antes de salir
                return pasos;
            }

            pasos.add(new PasoAFD(actual.getNombre(), simbolo, encontrada.getDestino().getNombre()));
            actual = encontrada.getDestino();
        }

        if (estadosAceptacion.contains(actual)) {
            pasos.add(new PasoAFD(actual.getNombre(), "FIN", "ACEPTADO"));
        } else {
            pasos.add(new PasoAFD(actual.getNombre(), "FIN", "NO ACEPTADO"));
        }

        ultimosPasos = pasos; // guardar pasos al final
        return pasos;
    }
    
    public List<PasoAFD> getUltimosPasos() {
        return ultimosPasos;
    }

}
