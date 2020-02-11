/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Facundo Cordoba
 */
public class Caracteristica {
    
    private String nombre;
    private String valor;
    private String simbolo;
    
    public Caracteristica(String nombre, String valor, String simbolo) {
        this.nombre = nombre;
        this.valor = valor;
        this.simbolo = simbolo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    
    

    @Override
    public String toString() {
        return "Caracteristica{" + "nombre=" + nombre + ", valor=" + valor + ", simbolo=" + simbolo + '}';
    }
    
    

}
