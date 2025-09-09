/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vistas.*;
import Controlador.*;
import Clases.*;
import static Parser.AnalizadorLexico.erroresLexicos;
import static Parser.AnalizadorSintactico.erroresSintacticos;
import proyecto1.compi.PROYECTO1COMPI;

/**
 *
 * @author aerod
 */
public class AppControlador {
    private VistaGeneral vista;
    private AnalizadorLexicoControlador lexCtrl;
    private AnalizadorSintacticoControlador sintCtrl;

    public AppControlador(VistaGeneral vista) {
        this.vista = vista;
        this.lexCtrl = new AnalizadorLexicoControlador();
        this.sintCtrl = new AnalizadorSintacticoControlador();

        // Aquí se conecta el botón con la lógica
        this.vista.Ejecutar.addActionListener(e -> ejecutarAnalisis());
        this.vista.Reportes.addActionListener(e -> AbrirReportes());
    }

    private void ejecutarAnalisis() {
        String entrada = vista.Entrada.getText();

        // Ejecutar análisis léxico
        lexCtrl.ejecutarAnalisisLexico(entrada);

        // Ejecutar análisis sintáctico
        sintCtrl.ejecutarAnalisisSintactico(entrada);

        // Cantidad de tokens
        int cantidadTokens = TokenRepositorio.getCantidad();

        // Cantidad de errores
        int erroresLex = erroresLexicos.size();
        int erroresSint = erroresSintacticos.size();
        int totalErrores = erroresLex + erroresSint;

        // Construir resumen
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

        // Mostrar resumen en el TextArea de salida
        vista.Salida.setText(resumen.toString());
    }

    private void AbrirReportes() {
        PROYECTO1COMPI.abrirReportes();
    }
}
