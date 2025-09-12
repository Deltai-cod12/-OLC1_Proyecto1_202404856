/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vistas.*;
import Clases.*;
import static Parser.AnalizadorLexico.erroresLexicos;
import static Parser.AnalizadorSintactico.erroresSintacticos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import proyecto1.compi.PROYECTO1COMPI;

/**
 *
 * @author aerod
 */
public class AppControlador {
    private static VistaGeneral vista;
    private AnalizadorLexicoControlador lexCtrl;
    private AnalizadorSintacticoControlador sintCtrl;

    // Variable para guardar la ruta actual del archivo
    private String rutaActual = null;

    public AppControlador(VistaGeneral vista) {
        AppControlador.vista = vista; // asigna referencia
        this.lexCtrl = new AnalizadorLexicoControlador();
        this.sintCtrl = new AnalizadorSintacticoControlador();

        this.vista.Ejecutar.addActionListener(e -> ejecutarAnalisis());
        this.vista.Reportes.addActionListener(e -> AbrirReportes());

        // Nuevo: listener para el ComboBox de archivo
        this.vista.ArchivoCombox.addActionListener(e -> manejarArchivoCombox());
    }

    private void ejecutarAnalisis() {
        String entrada = vista.Entrada.getText();
        vista.Salida.setText("");
        TokenRepositorio.limpiar(); // reinicia tokens
        erroresLexicos = new ArrayList<>(); // reinicia lista de errores léxicos
        erroresSintacticos = new ArrayList<>(); 

        lexCtrl.ejecutarAnalisisLexico(entrada);
        sintCtrl.ejecutarAnalisisSintactico(entrada);

        int cantidadTokens = TokenRepositorio.getCantidad();
        int erroresLex = erroresLexicos.size();
        int erroresSint = erroresSintacticos.size();
        int totalErrores = erroresLex + erroresSint;

        StringBuilder resumen = new StringBuilder();
        resumen.append("=== RESUMEN DEL ANÁLISIS ===\n");
        if (totalErrores == 0) {
            resumen.append("Análisis léxico realizado con éxito.\n");
            resumen.append("Análisis sintáctico realizado con éxito.\n");
        } else {
            resumen.append("Se encontraron ").append(totalErrores).append(" errores.\n");
            resumen.append("Errores léxicos: ").append(erroresLex).append("\n");
            resumen.append("Errores sintácticos: ").append(erroresSint).append("\n");
        }
        resumen.append("Cantidad de tokens: ").append(cantidadTokens).append("\n");

        agregarSalida(resumen.toString());
    }

    private void AbrirReportes() {
        PROYECTO1COMPI.abrirReportes();
    }

    public static void agregarSalida(String mensaje) {
        if (vista != null) {
            vista.Salida.append(mensaje + "\n");
            vista.Salida.setCaretPosition(vista.Salida.getDocument().getLength());
        }
    }

    // ----------------- MÉTODOS PARA ARCHIVO -----------------

    private void manejarArchivoCombox() {
        String seleccion = (String) vista.ArchivoCombox.getSelectedItem();
        if (seleccion == null) return;

        switch (seleccion) {
            case "Nuevo":
                nuevoArchivo();
                break;
            case "Abrir":
                abrirArchivo();
                break;
            case "Guardar":
                guardarArchivo();
                break;
        }
    }

    private void nuevoArchivo() {
        vista.Entrada.setText("");    // limpia el área de texto
        vista.Salida.setText("");     // limpia la salida
        rutaActual = null;            // resetea ruta
        agregarSalida("[INFO] Nuevo archivo creado.");
    }

    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos .atm", "atm");
        fileChooser.setFileFilter(filtro);

        int resultado = fileChooser.showOpenDialog(vista);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            rutaActual = archivo.getAbsolutePath();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                vista.Entrada.setText(contenido.toString());
                agregarSalida("[INFO] Archivo abierto: " + archivo.getName());
            } catch (IOException e) {
                agregarSalida("[ERROR] No se pudo abrir el archivo: " + e.getMessage());
            }
        }
    }

    private void guardarArchivo() {
        if (rutaActual == null) {
            // Si no hay archivo existente, usar guardar como
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos .atm", "atm");
            fileChooser.setFileFilter(filtro);

            int resultado = fileChooser.showSaveDialog(vista);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                // Asegurarse que tenga extensión .atm
                if (!archivo.getName().endsWith(".atm")) {
                    archivo = new File(archivo.getAbsolutePath() + ".atm");
                }
                rutaActual = archivo.getAbsolutePath();
            } else {
                return; // cancelar guardado
            }
        }

        // Guardar contenido actual
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaActual))) {
            bw.write(vista.Entrada.getText());
            agregarSalida("[INFO] Archivo guardado correctamente: " + new File(rutaActual).getName());
        } catch (IOException e) {
            agregarSalida("[ERROR] No se pudo guardar el archivo: " + e.getMessage());
        }
    }
}
