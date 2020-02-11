/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionmaquinas;

import Modelo.*;
import java.util.ArrayList;

/**
 *
 * @author Facundo Cordoba
 */
public class magnumDB {
    
    private ArrayList<Planta> plantas = new ArrayList<>();
    private ArrayList<Producto> almacen = new ArrayList<>();
    
    public magnumDB(){
    };

    public ArrayList<Planta> getPlantas() {
        return plantas;
    }

    public void setPlantas(ArrayList<Planta> plantas) {
        this.plantas = plantas;
    }

    public void agregarPlanta(Planta planta){
        plantas.add(planta);
    }
    
    public ArrayList<Producto> getAlmacen() {
        return almacen;
    }

    public void setAlmacen(ArrayList<Producto> almacen) {
        this.almacen = almacen;
    }
    
    public void agregarProducto(Producto producto){
        almacen.add(producto);
    }

}
