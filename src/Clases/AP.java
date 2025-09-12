/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.ArrayList;
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
    private List<PasoAP> ultimosPasos = new ArrayList<>();


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
        List<PasoAP> pasos = new ArrayList<>();

        int i = 0; // índice de la cadena

        while (true) {
            String simbolo = (i < cadena.length()) ? String.valueOf(cadena.charAt(i)) : "$";
            boolean aplicada = false;

            for (TransicionAP t : transiciones) {
                if (t.getOrigen().equals(actual) &&
                   (t.getSimboloEntrada().equals(simbolo) || t.getSimboloEntrada().equals("$"))) {

                    // Extraer de la pila si no es lambda
                    String extraido = "$";
                    if (!t.getSimboloExtrae().equals("$")) {
                        if (pila.isEmpty() || !pila.peek().equals(t.getSimboloExtrae())) continue;
                        extraido = pila.pop();
                    }

                    // Insertar símbolos en la pila (en orden inverso)
                    List<String> simbolosInserta = new ArrayList<>();
                    if (!t.getSimboloInserta().equals("$")) {
                        for (int j = t.getSimboloInserta().length() - 1; j >= 0; j--) {
                            String s = String.valueOf(t.getSimboloInserta().charAt(j));
                            pila.push(s);
                            simbolosInserta.add(s);
                        }
                    }

                    // Guardar paso
                    pasos.add(new PasoAP(
                        actual.getNombre(),
                        t.getSimboloEntrada(),
                        extraido,
                        simbolosInserta,
                        t.getDestino().getNombre()
                    ));

                    actual = t.getDestino();
                    aplicada = true;

                    if (!t.getSimboloEntrada().equals("$")) i++; // consumir símbolo real
                    break;
                }
            }

            // Si no se aplicó ninguna transición, terminar y rechazar
            if (!aplicada) {
                this.ultimosPasos = pasos;
                System.out.println("[INFO] Pasos del AP guardados correctamente (" + pasos.size() + " pasos).");
                return false; // No hay transición válida, cadena inválida
            }

            // Si llegamos al final de la cadena y aplicamos transición final, salir
            if (i >= cadena.length() && simbolo.equals("$")) break;
        }

        // Guardar pasos
        this.ultimosPasos = pasos;
        System.out.println("[INFO] Pasos del AP guardados correctamente (" + pasos.size() + " pasos).");

        // Aceptar si estado final está en ACEPTACION **y** pila está en símbolo inicial
        return estadosAceptacion.contains(actual) && pila.size() == 1 && pila.peek().equals("#");
    }

    // Getter para los pasos
    public List<PasoAP> getUltimosPasos() {
        return ultimosPasos;
    }

    public List<TransicionAP> getTransiciones() {
        return transiciones;
    }

    public List<String> getSimbolosPila() {
        return simbolosPila;
    }
}
