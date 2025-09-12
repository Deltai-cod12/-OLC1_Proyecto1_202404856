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
    public int linea;
    public int columna;
    private Object token;
    private String tipo;
    private String error;

    public ErrorSintactico(int linea, int columna, Object token, String tipo) {
        this.linea = linea;
        this.columna = columna;
        this.token = token;
        this.tipo = tipo;
    }

    public ErrorSintactico(String error) {
        this.error = error;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public Object getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        if (error != null) return error;
        return "Error Sintáctico en la línea " + linea + " columna " + columna + ", no se esperaba " + tipo + " " + token;
    }
}
