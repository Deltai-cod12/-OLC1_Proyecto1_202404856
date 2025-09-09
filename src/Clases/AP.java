/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.List;
import java.util.Stack;
/**
 *
 * @author aerod
 */
public class AP extends Automata {
    private List<TransicionAP> transiciones;
    private List<String> simbolosPila; // alfabeto de la pila
    private Stack<String> pila;

    public AP(String nombre, List<Estado> estados, List<String> alfabeto, List<String> simbolosPila, Estado inicial, List<Estado> aceptacion, List<TransicionAP> transiciones) {
        super(nombre);
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.simbolosPila = simbolosPila;
        this.estadoInicial = inicial;
        this.estadosAceptacion = aceptacion;
        this.transiciones = transiciones;
        this.pila = new Stack<>();
    }

    @Override
    public boolean validarCadena(String cadena) {
        Estado actual = estadoInicial;
        pila.clear();
        pila.push("#"); // base de pila

        for (char c : cadena.toCharArray()) {
            String simbolo = String.valueOf(c);
            boolean aplicada = false;
            for (TransicionAP t : transiciones) {
                if (t.getOrigen().equals(actual) && t.getSimboloEntrada().equals(simbolo)) {
                    if (!pila.isEmpty() && pila.peek().equals(t.getSimboloExtrae())) {
                        pila.pop();
                        if (!t.getSimboloInserta().equals("$")) {
                            pila.push(t.getSimboloInserta());
                        }
                        actual = t.getDestino();
                        aplicada = true;
                        break;
                    }
                }
            }
            if (!aplicada) return false;
        }
        return estadosAceptacion.contains(actual);
    }

    public List<TransicionAP> getTransiciones() {
        return transiciones;
    }
}
