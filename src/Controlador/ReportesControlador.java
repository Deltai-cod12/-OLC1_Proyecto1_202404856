/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import proyecto1.compi.PROYECTO1COMPI;
import Clases.*;
import Errores.ErrorLexico;
import Errores.ErrorSintactico;
import Reportes.ReporteAutomatas;
import Reportes.ReportePasosAFD;
import Reportes.ReportePasosAP;
import Vistas.Reportes;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class ReportesControlador {

    private Reportes vista;

    public ReportesControlador(Reportes vista) {
        this.vista = vista;

        this.vista.Regresar.addActionListener(e -> Regresar());
        this.vista.GenerarReporte.addActionListener(e -> generarReporteSeleccionado());

        // Cargar autómatas al iniciar
        cargarAutomatas();
    }

    // -------------------------------
    // Cargar autómatas en el JComboBox
    // -------------------------------
    public void cargarAutomatas() {
        vista.Automatas.removeAllItems(); // Limpiar antes de agregar
        for (String nombre : GestorAutomatas.getAutomatasNombres()) {
            vista.Automatas.addItem(nombre);
        }
    }

    // -------------------------------
    // Mostrar tokens en la tabla
    // -------------------------------
    public void mostrarTokens() {
        TokenModelo[] tokens = TokenRepositorio.getTokens();
        String[] columnas = {"#", "Lexema", "Tipo", "Linea", "Columna"};

        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < tokens.length; i++) {
            TokenModelo token = tokens[i];
            Object[] fila = {i + 1, token.getLexema(), token.getTipo(), token.getLinea(), token.getColumna()};
            modelo.addRow(fila);
        }

        vista.Tokens.setModel(modelo);
    }

    // -------------------------------
    // Mostrar errores en la tabla
    // -------------------------------
    public void mostrarErrores(ArrayList<ErrorLexico> erroresLexicos, ArrayList<ErrorSintactico> erroresSintacticos) {
        String[] columnas = {"#", "Tipo", "Descripción", "Línea", "Columna"};
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        int contador = 1;
        for (ErrorLexico error : erroresLexicos) {
            String tipo = "Léxico";
            String descripcion = error.toString();
            int linea = error.getLinea();
            int columna = error.getColumna();
            Object[] fila = {contador++, tipo, descripcion, linea, columna};
            modelo.addRow(fila);
        }

        for (ErrorSintactico error : erroresSintacticos) {
            String tipo = "Sintáctico";
            String descripcion = error.toString();
            int linea = error.linea;
            int columna = error.columna;
            Object[] fila = {contador++, tipo, descripcion, linea, columna};
            modelo.addRow(fila);
        }

        vista.Pasos.setModel(modelo);

        vista.Pasos.getColumnModel().getColumn(2).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JTextArea area = new JTextArea(value != null ? value.toString() : "");
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setOpaque(true);
                if (isSelected) {
                    area.setBackground(table.getSelectionBackground());
                    area.setForeground(table.getSelectionForeground());
                } else {
                    area.setBackground(table.getBackground());
                    area.setForeground(table.getForeground());
                }
                int alturaPreferida = area.getPreferredSize().height;
                if (table.getRowHeight(row) != alturaPreferida) {
                    table.setRowHeight(row, alturaPreferida);
                }
                return area;
            }
        });
    }

    // -------------------------------
    // Generar reporte según selección
    // -------------------------------
    private void generarReporteSeleccionado() {
        String nombreAutomata = (String) vista.Automatas.getSelectedItem();
        String tipoReporte = (String) vista.TipoReporte.getSelectedItem();

        if (nombreAutomata == null || tipoReporte == null) {
            System.err.println("Seleccione un autómata y un tipo de reporte");
            return;
        }

        Automata automata = GestorAutomatas.getAutomata(nombreAutomata);

        switch (tipoReporte) {
            case "Reporte Automata":
                ReporteAutomatas.generarDot(automata, "Reportes/" + nombreAutomata);
                break;

            case "Reporte de Pasos AFD":
                if (automata instanceof AFD) {
                    AFD afd = (AFD) automata;

                    //Verificar que haya pasos guardados
                    List<PasoAFD> pasos = afd.getUltimosPasos();
                    if (pasos == null || pasos.isEmpty()) {
                        System.err.println("No hay pasos disponibles. Valide la cadena primero.");
                        return;
                    }

                    //Generar reporte de pasos
                    ReportePasosAFD.generarReporte(pasos, afd, "Reportes/" + nombreAutomata + "_PasosAFD");
                } else {
                    System.err.println("El autómata seleccionado no es un AFD");
                }
                break;

            case "Reporte de Pasos AP":
                if (automata instanceof AP) {
                    ReportePasosAP.generarReporte((AP) automata, "Reportes/" + nombreAutomata + "_PasosAP");
                } else {
                    System.err.println("El autómata seleccionado no es un AP");
                }
                break;

            default:
                System.err.println("Tipo de reporte desconocido");
        }
    }

    private void Regresar() {
        PROYECTO1COMPI.cerrarReportes();
        PROYECTO1COMPI.abrirVistaGeneral();
    }
}
