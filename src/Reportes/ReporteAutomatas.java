/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import Clases.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author aerod
 */
public class ReporteAutomatas {
    public static void generarDot(Automata automata, String rutaSalida) {
        if (automata == null) {
            System.err.println("ReporteAutomatas: automata nulo");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        sb.append("  rankdir=LR;\n");
        sb.append("  node [shape=circle,fontname=\"Helvetica\"];\n\n");

        // Nodo inicial (punto)
        Estado inicial = automata.getEstadoInicial();
        if (inicial != null) {
            sb.append("  __inicio [shape=point];\n");
            sb.append("  __inicio -> \"" + escape(inicial.getNombre()) + "\";\n\n");
        }

        // Declarar nodos: doble círculo para aceptación
        List<Estado> estados = automata.getEstados();
        List<Estado> aceptaciones = automata.getEstadosAceptacion();
        for (Estado e : estados) {
            String name = escape(e.getNombre());
            if (aceptaciones != null && aceptaciones.contains(e)) {
                sb.append("  \"" + name + "\" [shape=doublecircle];\n");
            } else {
                sb.append("  \"" + name + "\" [shape=circle];\n");
            }
        }
        sb.append("\n");

        // Transiciones AFD
        if (automata instanceof AFD) {
            AFD afd = (AFD) automata;
            List<TransicionAFD> trans = afd.getTransiciones();
            if (trans != null) {
                for (TransicionAFD t : trans) {
                    // Usar getters
                    Estado o = t.getOrigen();
                    Estado d = t.getDestino();
                    String simbolo = t.getSimbolo();
                    String label = escape(simbolo);
                    String on = (o != null) ? escape(o.getNombre()) : "NULL";
                    String dn = (d != null) ? escape(d.getNombre()) : "NULL";
                    sb.append("  \"" + on + "\" -> \"" + dn + "\" [label=\"" + label + "\"];\n");
                }
            }
        }

        // Transiciones AP
        if (automata instanceof AP) {
            AP ap = (AP) automata;
            List<TransicionAP> trans = ap.getTransiciones();
            if (trans != null) {
                for (TransicionAP t : trans) {
                    Estado o = t.getOrigen();
                    Estado d = t.getDestino();
                    String entrada = t.getSimboloEntrada();
                    String pop = t.getSimboloExtrae();
                    String push = t.getSimboloInserta();
                    String on = (o != null) ? escape(o.getNombre()) : "NULL";
                    String dn = (d != null) ? escape(d.getNombre()) : "NULL";
                    // etiqueta clara: (entrada, pop -> push)
                    String label = "(" + escape(entrada) + ")  " + escape(pop) + " -> " + escape(push);
                    sb.append("  \"" + on + "\" -> \"" + dn + "\" [label=\"" + label + "\"];\n");
                }
            }
        }

        sb.append("}\n");

        // Escribir .dot
        try (FileWriter fw = new FileWriter(rutaSalida + ".dot")) {
            fw.write(sb.toString());
            System.out.println("Reporte DOT generado: " + rutaSalida + ".dot");
        } catch (IOException ex) {
            System.err.println("Error al escribir DOT: " + ex.getMessage());
            ex.printStackTrace();
            return;
        }

        // Intentar ejecutar Graphviz 'dot' para generar PNG
        try {
            ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", rutaSalida + ".dot", "-o", rutaSalida + ".png");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            int code = p.waitFor();
            if (code == 0) {
                System.out.println("Imagen generada: " + rutaSalida + ".png");
            } else {
                System.err.println("Graphviz dot devolvió código " + code + ". Asegúrate de tener 'dot' en el PATH.");
            }
        } catch (IOException | InterruptedException ex) {
            System.err.println("No se pudo ejecutar Graphviz dot: " + ex.getMessage());
            // no hacemos más; el dot ya fue generado en .dot
        }
    }

    // Escapa comillas dobles para Graphviz
    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
