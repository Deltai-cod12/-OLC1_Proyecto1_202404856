/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import Clases.*;
import Clases.AP;
import java.io.File;
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
            System.err.println("ReporteAutomatas: autómata nulo");
            return;
        }

        //Verificar y crear carpeta si no existe
        File archivoDestino = new File(rutaSalida);
        File carpeta = archivoDestino.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            if (carpeta.mkdirs()) {
                System.out.println("[INFO] Carpeta creada: " + carpeta.getAbsolutePath());
            }
        }

        // Resumen del automata
        StringBuilder resumen = new StringBuilder();
        resumen.append("Nombre: ").append(automata.getNombre()).append("\\l"); // \l para salto de línea a la izquierda
        resumen.append("Tipo: ").append(automata instanceof AFD ? "AFD" : "AP").append("\\l");
        resumen.append("Estado Inicial: ").append(
                automata.getEstadoInicial() != null ? automata.getEstadoInicial().getNombre() : "N/A"
        ).append("\\l");
        resumen.append("Estados de Aceptación: ");
        List<Estado> aceptaciones = automata.getEstadosAceptacion();
        if (aceptaciones != null && !aceptaciones.isEmpty()) {
            for (Estado e : aceptaciones) {
                resumen.append(e.getNombre()).append(" ");
            }
        } else {
            resumen.append("Ninguno");
        }
        resumen.append("\\l");

        if (automata instanceof AFD) {
            AFD afd = (AFD) automata;
            resumen.append("Alfabeto: ").append(
                    afd.getAlfabeto() != null ? afd.getAlfabeto().toString() : "N/A"
            ).append("\\l");
        } else if (automata instanceof AP) {
            AP ap = (AP) automata;
            resumen.append("Alfabeto Entrada: ").append(
                    ap.getAlfabeto() != null ? ap.getAlfabeto().toString() : "N/A"
            ).append("\\l");
            resumen.append("Alfabeto Pila: ").append(
                    ap.getSimbolosPila() != null ? ap.getSimbolosPila().toString() : "N/A"
            ).append("\\l");
        }

        //Generar dot
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        sb.append("  rankdir=LR;\n");
        sb.append("  node [shape=circle,fontname=\"Helvetica\"];\n\n");

        // Nodo resumen (visible en la imagen)
        sb.append("  info [shape=box, style=filled, fillcolor=lightgray, label=\"")
          .append(resumen.toString())
          .append("\"];\n\n");

        Estado inicial = automata.getEstadoInicial();
        if (inicial != null) {
            sb.append("  __inicio [shape=point];\n");
            sb.append("  __inicio -> \"").append(escape(inicial.getNombre())).append("\";\n\n");
        }

        List<Estado> estados = automata.getEstados();
        for (Estado e : estados) {
            String name = escape(e.getNombre());
            sb.append("  \"").append(name).append("\"")
              .append(aceptaciones != null && aceptaciones.contains(e) ? " [shape=doublecircle]" : " [shape=circle]")
              .append(";\n");
        }
        sb.append("\n");

        if (automata instanceof AFD) {
            AFD afd = (AFD) automata;
            List<TransicionAFD> trans = afd.getTransiciones();
            if (trans != null) {
                for (TransicionAFD t : trans) {
                    sb.append("  \"").append(escape(t.getOrigen().getNombre())).append("\" -> \"")
                      .append(escape(t.getDestino().getNombre())).append("\"")
                      .append(" [label=\"").append(escape(t.getSimbolo())).append("\"];\n");
                }
            }
        } else if (automata instanceof AP) {
            AP ap = (AP) automata;
            List<TransicionAP> trans = ap.getTransiciones();
            if (trans != null) {
                for (TransicionAP t : trans) {
                    String label = "(" + escape(t.getSimboloEntrada()) + ") "
                                 + escape(t.getSimboloExtrae()) + "->" + escape(t.getSimboloInserta());
                    sb.append("  \"").append(escape(t.getOrigen().getNombre())).append("\" -> \"")
                      .append(escape(t.getDestino().getNombre())).append("\"")
                      .append(" [label=\"").append(label).append("\"];\n");
                }
            }
        }

        sb.append("}\n");

        // Guardar archivo DOT y generar PNG
        try {
            File fileDot = new File(rutaSalida + ".dot");
            try (FileWriter fw = new FileWriter(fileDot)) {
                fw.write(sb.toString());
            }
            System.out.println("Reporte DOT generado: " + rutaSalida + ".dot");

            // Ejecutar Graphviz (solo PNG)
            String comando = "dot -Tpng " + fileDot.getAbsolutePath() + " -o " + rutaSalida + ".png";
            Process proceso = Runtime.getRuntime().exec(comando);
            int exitCode = proceso.waitFor();
            if (exitCode == 0) {
                System.out.println("Imagen PNG generada: " + rutaSalida + ".png");
            } else {
                System.err.println("Error al generar PNG: código " + exitCode);
            }

        } catch (IOException | InterruptedException ex) {
            System.err.println("Error al escribir DOT/PNG: " + ex.getMessage());
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
