/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.List;


/**
 *
 * @author aerod
 */

public abstract class Automata {
    protected String nombre;
    protected List<Estado> estados;
    protected List<String> alfabeto;
    protected Estado estadoInicial;
    protected List<Estado> estadosAceptacion;

    public Automata(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public List<String> getAlfabeto() {
        return alfabeto;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public List<Estado> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public abstract boolean validarCadena(String cadena);
}
