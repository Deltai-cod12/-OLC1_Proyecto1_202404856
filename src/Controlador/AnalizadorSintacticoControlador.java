/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Parser.AnalizadorSintactico;
import Parser.AnalizadorLexico;
import java.io.StringReader;
import java_cup.runtime.Symbol;

public class AnalizadorSintacticoControlador {

    public String ejecutarAnalisisSintactico(String entrada) {
        String resultado = "";
        try {
            AnalizadorLexico lexer = new AnalizadorLexico(new StringReader(entrada));
            AnalizadorSintactico parser = new AnalizadorSintactico(lexer);

            Symbol s = parser.parse();

            if (s != null && s.value != null) {
                resultado = "Análisis sintáctico exitoso.\nResultado: " + s.value.toString();
            } else {
                resultado = "Análisis sintáctico completado sin errores.";
            }
        } catch (Exception e) {
            resultado = "Error sintáctico: " + e.getMessage();
            e.printStackTrace();
        }
        System.out.println(resultado.toString());
        return resultado;
    }
}
