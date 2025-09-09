/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import proyecto1.compi.PROYECTO1COMPI;
import Clases.TokenModelo;
import Clases.TokenRepositorio;
import Errores.ErrorLexico;
import Errores.ErrorSintactico;
import Vistas.Reportes;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class ReportesControlador {

    private Reportes vista;

    public ReportesControlador(Reportes vista) {
        this.vista = vista;

        this.vista.Regresar.addActionListener(e -> Regresar());

        // Cargar autómatas al iniciar
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
            Object[] fila = {
                i + 1,
                token.getLexema(),
                token.getTipo(),
                token.getLinea(),
                token.getColumna()
            };
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        int contador = 1;

        for (ErrorLexico error : erroresLexicos) {
            String tipo = "Léxico";
            ErrorLexico descripcion = error;
            int linea = 0;
            int columna = 0;
            try {
                String[] partes = error.split(" en línea |, columna ");
                if (partes.length == 3) {
                    linea = Integer.parseInt(partes[1].trim());
                    columna = Integer.parseInt(partes[2].trim());
                }
            } catch (Exception e) {}

            Object[] fila = {contador++, tipo, descripcion, linea, columna};
            modelo.addRow(fila);
        }

        for (ErrorSintactico error : erroresSintacticos) {
            String tipo = "Sintáctico";
            ErrorSintactico descripcion = error;
            int linea = 0;
            int columna = 0;
            try {
                String[] partes = error.split(" en línea |, columna ");
                if (partes.length == 3) {
                    linea = Integer.parseInt(partes[1].trim());
                    columna = Integer.parseInt(partes[2].trim());
                }
            } catch (Exception e) {
                System.out.println("Error al parsear línea/columna: " + e.getMessage());
            }

            System.out.println("Fila: " + contador + ", Tipo: " + tipo + ", Descripción: " + descripcion +
                       ", Línea: " + linea + ", Columna: " + columna);

            Object[] fila = {contador++, tipo, descripcion, linea, columna};
            modelo.addRow(fila);
        }

        vista.Pasos.setModel(modelo);

        // Render para que la columna Descripción haga wrap
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
    // Acción del botón Regresar
    // -------------------------------
    private void Regresar() {
        PROYECTO1COMPI.cerrarReportes();
        PROYECTO1COMPI.abrirVistaGeneral();
    }
}
