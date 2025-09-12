/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.HashMap;

/**
 *
 * @author aerod
 */
public class GestorAutomatas {
    private static HashMap<String, Automata> automatas = new HashMap<>();

    public static void agregarAutomata(Automata automata) {
        automatas.put(automata.getNombre(), automata);
    }

    public static Automata getAutomata(String nombre) {
        return automatas.get(nombre);
    }

    public static String verAutomatas() {
        StringBuilder sb = new StringBuilder();
        for (String key : automatas.keySet()) {
            Automata a = automatas.get(key);
            String tipo = (a instanceof AFD) ? "Autómata Finito Determinista" : "Autómata de Pila";
            sb.append(a.getNombre()).append("   ").append(tipo).append("\n");
        }
        return sb.toString();
    }
    
    public static java.util.Set<String> getAutomatasNombres() {
        return automatas.keySet();
    }

}
