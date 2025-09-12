/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.List;
import Controlador.AppControlador;

/**
 *
 * @author aerod
 */
public class FuncionesAutomata {

    // Lista todos los autómatas
    public static void verAutomatas() {
        AppControlador.agregarSalida("=== Lista de Autómatas ===");
        String lista = GestorAutomatas.verAutomatas(); // devuelve todo como String
        AppControlador.agregarSalida(lista);           // se imprime en JTextArea
        AppControlador.agregarSalida("==========================");
    }

    // Muestra descripción de un autómata
    public static void descripcionAutomata(String nombre) {
        Automata automata = GestorAutomatas.getAutomata(nombre);
        if (automata == null) {
            AppControlador.agregarSalida("El autómata \"" + nombre + "\" no existe.");
            return;
        }

        AppControlador.agregarSalida("=== Descripción del Autómata ===");
        AppControlador.agregarSalida("Nombre: " + automata.getNombre());
        AppControlador.agregarSalida("Tipo: " + ((automata instanceof AFD) ? "Autómata Finito Determinista" : "Autómata de Pila"));

        // Estados
        StringBuilder estados = new StringBuilder("Estados: ");
        for (Estado e : automata.getEstados()) estados.append(e.getNombre()).append(" ");
        AppControlador.agregarSalida(estados.toString());

        // Alfabeto
        StringBuilder alfabeto = new StringBuilder("Alfabeto: ");
        for (String s : automata.getAlfabeto()) alfabeto.append(s).append(" ");
        AppControlador.agregarSalida(alfabeto.toString());

        // Estado inicial
        AppControlador.agregarSalida("Estado Inicial: " + automata.getEstadoInicial().getNombre());

        // Estados de aceptación
        StringBuilder aceptacion = new StringBuilder("Estados de Aceptación: ");
        for (Estado e : automata.getEstadosAceptacion()) aceptacion.append(e.getNombre()).append(" ");
        AppControlador.agregarSalida(aceptacion.toString());

        // Transiciones
        AppControlador.agregarSalida("Transiciones:");
        if (automata instanceof AFD afd) {
            for (TransicionAFD t : afd.getTransiciones()) AppControlador.agregarSalida("  " + t.toString());
        } else if (automata instanceof AP ap) {
            for (TransicionAP t : ap.getTransiciones()) AppControlador.agregarSalida("  " + t.toString());
        }

        AppControlador.agregarSalida("================================");
    }

    // Validación de cadena
    public static void validarCadena(String nombre, String cadena) {
        Automata automata = GestorAutomatas.getAutomata(nombre);
        if (automata == null) {
            AppControlador.agregarSalida("El autómata \"" + nombre + "\" no existe.");
            return;
        }

        boolean resultado;

        if (automata instanceof AFD afd) {
            // Validar cadena y generar pasos
            List<PasoAFD> pasos = afd.validarCadenaConPasos(cadena);

            // La cadena es válida si el último paso indica "ACEPTADO"
            if (!pasos.isEmpty()) {
                resultado = pasos.get(pasos.size() - 1).getEstadoSiguiente().equals("ACEPTADO");
            } else {
                resultado = false;
            }

            System.out.println("[INFO] Pasos del AFD guardados correctamente (" + pasos.size() + " pasos).");

        } else {
            // Para AP, usar método normal
            resultado = automata.validarCadena(cadena);
        }

        AppControlador.agregarSalida(automata.getNombre() + "  " + cadena + "  " + (resultado ? "Cadena Válida" : "Cadena Inválida"));
    }
}
