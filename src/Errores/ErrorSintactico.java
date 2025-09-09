/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Errores;

/**
 *
 * @author aerod
 */
public class ErrorSintactico {
    int linea;
    int columna;
    Object token;
    String tipo;

    String error;
    public ErrorSintactico(int linea, int columna, Object token, String tipo) {
        this.linea = linea;
        this.columna = columna;
        this.token = token;
        this.tipo = tipo;
    }

    public ErrorSintactico(String error) {
        this.error = error;
    }

    public String toString() {
        if (error != null) {
            return error;
        }
        return "Error Sintactico en la linea " + linea + " columna " + columna + "no se esperaba " + tipo + " " + token;
    }

    public String[] split(String _en_línea__columna_) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}