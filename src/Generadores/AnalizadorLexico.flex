package lexer;

import java.util.ArrayList;
import java_cup.runtime.*;
import Modelo.sym;
import Parser.AnalizadorSintactico;
import Errores.ErrorLexico;

%%

%public
%class AnalizadorLexico
%unicode
%line
%column
%cup

%{
    // Lista estática para almacenar errores léxicos
    public static ArrayList<ErrorLexico> erroresLexicos = new ArrayList<>();

    // Referencia al parser para llamadas opcionales
    private AnalizadorSintactico parser;

    // Constructor que recibe el parser
    public AnalizadorLexico(java.io.Reader in, AnalizadorSintactico parser) {
        this(in);
        this.parser = parser;
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    // Método auxiliar para registrar errores
    private void registrarError(String texto) {
        ErrorLexico error = new ErrorLexico(yyline+1, yycolumn+1, texto);
        erroresLexicos.add(error);
        System.err.println(error);
    }
%}

/* ===== Macros ===== */
ESPACIO       = [ \t\f\r\n]+
DIGITO        = [0-9]
LETRA         = [a-zA-Z_áéíóúÁÉÍÓÚñÑ]
ID            = {LETRA}({LETRA}|{DIGITO})*
NUM           = {DIGITO}+
CADENA        = \"([^\"\\]|\\[\"\\])*\"    // string entre comillas

%state COMM_MULTI

%%

/* ===== Ignorar espacios ===== */
{ESPACIO}                         { /*ignorar*/ }

/* ===== Comentarios ===== */
// comentario de línea
"//".*                             { /*ignorar*/ }

// comentario multilínea
"/*"                               { yybegin(COMM_MULTI); }
<COMM_MULTI>"*/"                   { yybegin(YYINITIAL); }
<COMM_MULTI>[^*]+                  { }
<COMM_MULTI>"*"[^/]                { }
<COMM_MULTI>"*/"                   { yybegin(YYINITIAL); }

/* ===== Palabras clave ===== */
"AFD"               { return symbol(sym.AFD, yytext()); }
"AP"                { return symbol(sym.AP, yytext()); }
"Nombre"            { return symbol(sym.NOMBRE, yytext()); }
"Transiciones"      { return symbol(sym.TRANSICIONES, yytext()); }
"verAutomatas"      { return symbol(sym.VER_AUTOMATAS, yytext()); }
"desc"              { return symbol(sym.DESC, yytext()); }

/* ===== Símbolos ===== */
"</"                { return symbol(sym.MENOR_DIAGONAL, yytext()); }
"<"                 { return symbol(sym.MENOR, yytext()); }
">"                 { return symbol(sym.MAYOR, yytext()); }
"="                 { return symbol(sym.IGUAL, yytext()); }
"{"                 { return symbol(sym.LLAVE_IZQ, yytext()); }
"}"                 { return symbol(sym.LLAVE_DER, yytext()); }
"("                 { return symbol(sym.PAREN_IZQ, yytext()); }
")"                 { return symbol(sym.PAREN_DER, yytext()); }
","                 { return symbol(sym.COMA, yytext()); }
";"                 { return symbol(sym.PUNTO_COMA, yytext()); }
":"                 { return symbol(sym.DOS_PUNTOS, yytext()); }
"->"                { return symbol(sym.FLECHA, yytext()); }
"|"                 { return symbol(sym.OR, yytext()); }
"#"                 { return symbol(sym.STACKTOP, yytext()); }
"$"                 { return symbol(sym.LAMBDA, yytext()); }

/* ===== Literales ===== */
{CADENA}            {
    String cadena = yytext().substring(1, yytext().length()-1);
    if (parser != null) {
        parser.capturarCadena(cadena);
    }
    return symbol(sym.CADENA, cadena);
}

{NUM}               { return symbol(sym.NUMERO, yytext()); }

{ID}                {
    String id = yytext();
    if (parser != null) {
        parser.capturarNombre(id);
    }
    return symbol(sym.ID, id);
}

/* ===== Error ===== */
.                   {
    registrarError(yytext());
    return symbol(sym.error, yytext());
}