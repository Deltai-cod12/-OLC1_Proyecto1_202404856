/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author aerod
 */
public class TokenRepositorio {

    private static TokenModelo[] listaTokens = new TokenModelo[500]; // capacidad máxima
    private static int cantidad = 0;

    // Limpia el repositorio antes de un nuevo análisis
    public static void limpiar() {
        cantidad = 0;
        listaTokens = new TokenModelo[1000];
    }

    // Agrega un nuevo token
    public static void agregarToken(TokenModelo token) {
        if (cantidad < listaTokens.length) {
            listaTokens[cantidad] = token;
            cantidad++;
        } else {
            System.out.println("❌ Error: límite de tokens alcanzado.");
        }
    }

    // Devuelve todos los tokens como un vector
    public static TokenModelo[] getTokens() {
        TokenModelo[] tokensValidos = new TokenModelo[cantidad];
        for (int i = 0; i < cantidad; i++) {
            tokensValidos[i] = listaTokens[i];
        }
        return tokensValidos;
    }

    // Cantidad actual de tokens almacenados
    public static int getCantidad() {
        return cantidad;
    }
}