/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Errores;

/**
 *
 * @author aerod
 */
public class ErrorLexico {
    private int linea;
    private int columna;
    private String lexema;

    public ErrorLexico(int linea, int columna, String lexema) {
        this.linea = linea;
        this.columna = columna;
        this.lexema = lexema;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public String getLexema() {
        return lexema;
    }

    @Override
    public String toString() {
        return linea + " ".repeat(6 - String.valueOf(linea).length()) 
             + columna + " ".repeat(8 - String.valueOf(columna).length()) 
             + "Caracter no reconocido: " + lexema;
    }
}
