package Controlador;

import Parser.sym;
import Parser.AnalizadorLexico;
import java.io.StringReader;
import java_cup.runtime.Symbol;
import Clases.*;

public class AnalizadorLexicoControlador {

    public String ejecutarAnalisisLexico(String entrada) {
        StringBuilder resultado = new StringBuilder();
        try {
            TokenRepositorio.limpiar();
            AnalizadorLexico lexer = new AnalizadorLexico(new StringReader(entrada));
            Symbol simbolo;

            while ((simbolo = lexer.next_token()).sym != sym.EOF) {
                String tipo = sym.terminalNames[simbolo.sym];
                String lexema = (simbolo.value != null) ? simbolo.value.toString() : "";

                // Usar simbolo.left y simbolo.right para línea y columna
                TokenModelo token = new TokenModelo(
                        lexema,
                        tipo,
                        simbolo.left,
                        simbolo.right
                );
                TokenRepositorio.agregarToken(token);

                resultado.append(token.toString()).append("\n");
            }
        } catch (Exception e) {
            resultado.append("❌ Error léxico: ").append(e.getMessage());
        }
        return resultado.toString();
    }
}

