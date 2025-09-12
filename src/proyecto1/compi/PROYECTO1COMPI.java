/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1.compi;

import Controlador.AppControlador;
import Controlador.ReportesControlador;
import static Parser.AnalizadorLexico.erroresLexicos;
import static Parser.AnalizadorSintactico.erroresSintacticos;
import Vistas.Reportes;
import Vistas.VistaGeneral;

/**
 *
 * @author aerod
 */
public class PROYECTO1COMPI {

    // Instancias estáticas de las vistas
    private static VistaGeneral vistaGeneral;
    private static Reportes vistaReportes;
    private static ReportesControlador controladorReportes;


    public static void main(String[] args) {
        
        
        // Inicializa la vista principal
        vistaGeneral = new VistaGeneral();
        AppControlador app = new AppControlador(vistaGeneral);
        vistaGeneral.setVisible(true);
    }


    public static void abrirVistaGeneral() {
        if (vistaGeneral == null) {
            vistaGeneral = new VistaGeneral();
            new AppControlador(vistaGeneral); // asigna el controlador
        }
        vistaGeneral.setVisible(true);
    }

    public static void cerrarVistaGeneral() {
        if (vistaGeneral != null) {
            vistaGeneral.setVisible(false);
        }
    }

    public static void abrirReportes() {
        if (vistaReportes == null) {
            vistaReportes = new Reportes();
            controladorReportes = new ReportesControlador(vistaReportes);
        }
        vistaReportes.setVisible(true);
        controladorReportes.mostrarTokens();
        controladorReportes.mostrarErrores(erroresLexicos, erroresSintacticos);
    }
      
    public static void cerrarReportes() {
        if (vistaReportes != null) {
            vistaReportes.setVisible(false);
        }
    }

    public static Reportes getVistaReportes() {
        return vistaReportes;    
    }
    
    public static ReportesControlador getControladorReportes() {
        return controladorReportes;
    }

}
