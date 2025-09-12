/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import Clases.AP;
import Clases.PasoAP;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 *
 * @author aerod
 */
public class ReportePasosAP {

    public static void generarReporte(AP automata, String nombreArchivo) {
        List<PasoAP> pasos = automata.getUltimosPasos();
        if (pasos == null || pasos.isEmpty()) {
            System.out.println("[WARN] No hay pasos para generar el reporte.");
            return;
        }

        StringBuilder dot = new StringBuilder();
        dot.append("digraph G {\n");
        dot.append("rankdir=LR;\n");
        dot.append("node [shape=box, style=filled, color=lightblue, fontname=\"Helvetica\"];\n");
        dot.append("splines=true;\n");
        dot.append("ranksep=1.2;\n");
        dot.append("nodesep=0.6;\n");

        int pasoNum = 1;

        for (PasoAP paso : pasos) {
            String estadoActual = paso.getEstadoActual();
            String estadoSiguiente = paso.getEstadoSiguiente();
            String simboloEntrada = paso.getSimboloEntrada();
            String extraido = paso.getSimboloExtrae();
            List<String> pila = paso.getSimbolosInserta();

            // Nodo principal del paso con número
            String nodoPaso = String.format(
                    "Paso%d [label=\"Paso %d\\n%s -> %s\\nEntrada: %s\\nExtraído: %s\"];\n",
                    pasoNum, pasoNum, estadoActual, estadoSiguiente, simboloEntrada, extraido);
            dot.append(nodoPaso);

            // Mostrar pila vertical
            if (pila != null && !pila.isEmpty()) {
                String prevNodo = null;
                int pilaNum = 1;
                for (int i = pila.size() - 1; i >= 0; i--) { // de arriba hacia abajo
                    String simbolo = pila.get(i);
                    String nodoPila = String.format(
                            "Paso%d_Pila%d [label=\"%s\", shape=ellipse, style=filled, color=lightyellow];\n",
                            pasoNum, pilaNum, simbolo);
                    dot.append(nodoPila);

                    if (prevNodo != null) {
                        dot.append(String.format("%s -> %s [dir=back, style=dashed];\n", nodoPila, prevNodo));
                    }

                    prevNodo = String.format("Paso%d_Pila%d", pasoNum, pilaNum);
                    pilaNum++;
                }

                // Conectar nodo principal con el tope de la pila
                if (prevNodo != null) {
                    dot.append(String.format("Paso%d -> %s [style=dotted];\n", pasoNum, prevNodo));
                }
            }

            // Conectar pasos entre sí
            if (pasoNum > 1) {
                dot.append(String.format("Paso%d -> Paso%d [style=bold];\n", pasoNum - 1, pasoNum));
            }

            pasoNum++;
        }

        // Determinar si la cadena fue aceptada: 
        // Cadena válida si último paso terminó en un estado de aceptación del AP
        PasoAP ultimoPaso = pasos.get(pasos.size() - 1);
        String estadoFinal = ultimoPaso.getEstadoSiguiente();
        boolean aceptado = automata.getEstadosAceptacion().stream()
                .anyMatch(e -> e.getNombre().equals(estadoFinal));
        String resultado = aceptado ? "ACEPTADO" : "NO ACEPTADO";
        String colorResultado = aceptado ? "green" : "red";

        // Nodo final
        dot.append(String.format("Resultado [label=\"%s\", shape=box, style=filled, color=%s, fontname=\"Helvetica\"];\n",
                resultado, colorResultado));
        dot.append(String.format("Paso%d -> Resultado [style=bold];\n", pasoNum - 1));

        dot.append("}\n");

        try {
            // Crear carpeta si no existe
            File archivoDestino = new File(nombreArchivo);
            File carpeta = archivoDestino.getParentFile();
            if (carpeta != null && !carpeta.exists()) carpeta.mkdirs();

            // Guardar DOT
            File fileDot = new File(nombreArchivo + ".dot");
            try (FileWriter writer = new FileWriter(fileDot)) {
                writer.write(dot.toString());
            }

            // Generar PNG
            String comando = String.format("dot -Tpng %s -o %s.png",
                    fileDot.getAbsolutePath(), nombreArchivo);
            Process proceso = Runtime.getRuntime().exec(comando);
            proceso.waitFor();

            System.out.println("[OK] Reporte generado: " + nombreArchivo + ".png");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
