/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import Clases.PasoAFD;
import Clases.AFD;
import Clases.Estado;
import Clases.TransicionAFD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportePasosAFD {

    public static void generarReporte(List<PasoAFD> pasos, AFD afd, String nombreArchivo) {
        if (pasos == null || pasos.isEmpty()) {
            System.out.println("[WARN] No hay pasos para generar el reporte.");
            return;
        }

        StringBuilder dot = new StringBuilder();
        dot.append("digraph G {\n");
        dot.append("    rankdir=LR;\n"); // de izquierda a derecha
        dot.append("    node [shape=circle];\n\n");

        // Dibujar todos los estados y marcar los de aceptación
        for (Estado e : afd.getEstados()) {
            if (afd.getEstadosAceptacion().contains(e)) {
                dot.append("    ").append(e.getNombre())
                   .append(" [shape=doublecircle, style=filled, fillcolor=lightgreen];\n");
            } else {
                dot.append("    ").append(e.getNombre()).append(" [shape=circle];\n");
            }
        }
        dot.append("\n");

        // Estado inicial
        dot.append("    init [shape=point];\n");
        dot.append("    init -> ").append(afd.getEstadoInicial().getNombre()).append(";\n\n");

        // Dibujar todas las transiciones del AFD en gris claro
        for (TransicionAFD t : afd.getTransiciones()) {
            dot.append("    ")
               .append(t.getOrigen().getNombre())
               .append(" -> ")
               .append(t.getDestino().getNombre())
               .append(" [label=\"").append(t.getSimbolo()).append("\", color=lightgray];\n");
        }
        dot.append("\n");

        // Dibujar los pasos de la cadena en azul y más grueso, con número de paso
        int pasoNum = 1;
        for (PasoAFD p : pasos) {
            String etiqueta = p.getSimbolo() + " (" + pasoNum + ")";
            dot.append("    ")
               .append(p.getEstadoActual())
               .append(" -> ")
               .append(p.getEstadoSiguiente())
               .append(" [label=\"").append(etiqueta)
               .append("\", color=blue, penwidth=2.5];\n");
            pasoNum++;
        }

        dot.append("}\n");

        // Guardar archivo DOT y generar PNG
        try {
            File carpeta = new File("Reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
                System.out.println("[INFO] Carpeta 'Reportes' creada correctamente.");
            }

            File dotFile = new File(nombreArchivo + ".dot");
            try (FileWriter fw = new FileWriter(dotFile)) {
                fw.write(dot.toString());
            }
            System.out.println("[INFO] Archivo DOT generado: " + dotFile.getAbsolutePath());

            String comando = "dot -Tpng " + dotFile.getAbsolutePath() + " -o " + nombreArchivo + ".png";
            Process proceso = Runtime.getRuntime().exec(comando);
            proceso.waitFor();
            System.out.println("[INFO] Imagen PNG generada: " + nombreArchivo + ".png");

        } catch (IOException | InterruptedException e) {
            System.err.println("[ERROR] No se pudo generar el reporte: " + e.getMessage());
        }
    }
}
