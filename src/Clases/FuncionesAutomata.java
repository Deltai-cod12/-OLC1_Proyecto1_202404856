/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author aerod
 */
public class FuncionesAutomata {

    // Lista todos los autómatas
    public static void verAutomatas() {
        System.out.println("=== Lista de Autómatas ===");
        GestorAutomatas.verAutomatas();
        System.out.println("==========================");
    }

    // Muestra descripción de un autómata
    public static void descripcionAutomata(String nombre) {
        Automata automata = GestorAutomatas.getAutomata(nombre);
        if (automata == null) {
            System.out.println("El autómata \"" + nombre + "\" no existe.");
            return;
        }

        System.out.println("=== Descripción del Autómata ===");
        System.out.println("Nombre: " + automata.getNombre());
        if (automata instanceof AFD) {
            System.out.println("Tipo: Autómata Finito Determinista");
        } else if (automata instanceof AP) {
            System.out.println("Tipo: Autómata de Pila");
        }

        // Estados
        System.out.print("Estados: ");
        for (Estado e : automata.getEstados()) {
            System.out.print(e.getNombre() + " ");
        }
        System.out.println();

        // Alfabeto
        System.out.print("Alfabeto: ");
        for (String s : automata.getAlfabeto()) {
            System.out.print(s + " ");
        }
        System.out.println();

        // Estado inicial
        System.out.println("Estado Inicial: " + automata.getEstadoInicial().getNombre());

        // Estados de aceptación
        System.out.print("Estados de Aceptación: ");
        for (Estado e : automata.getEstadosAceptacion()) {
            System.out.print(e.getNombre() + " ");
        }
        System.out.println();

        // Transiciones
        System.out.println("Transiciones:");
        if (automata instanceof AFD afd) {
            for (TransicionAFD t : afd.getTransiciones()) {
                System.out.println("  " + t.toString());
            }
        } else if (automata instanceof AP ap) {
            for (TransicionAP t : ap.getTransiciones()) {
                System.out.println("  " + t.toString());
            }
        }

        System.out.println("================================");
    }

    // Validación de cadena
    public static void validarCadena(String nombre, String cadena) {
        Automata automata = GestorAutomatas.getAutomata(nombre);
        if (automata == null) {
            System.out.println("El autómata \"" + nombre + "\" no existe.");
            return;
        }

        boolean resultado = automata.validarCadena(cadena);
        String tipo = (automata instanceof AFD) ? "AFD" : "AP";
        System.out.println(automata.getNombre() + "  " + cadena + "  " + (resultado ? "Cadena Válida" : "Cadena Inválida"));
    }
}
