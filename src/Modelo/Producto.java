/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Facundo Cordoba
 */
public class Producto {
    private String codigo;
    private String nombre;
    private String caracterisitcas;
    private String fabricante;
    
    private ArrayList<Maquina> maquinas = new ArrayList<>();
}
