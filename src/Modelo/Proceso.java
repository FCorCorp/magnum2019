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
public class Proceso {
    private String codigo;
    private String nombre;
    private String estado;
    private String caracteristicas;
    
    private String codPlanta;
    private ArrayList<SubProceso> subProcesos = new ArrayList<>();

    public Proceso(String codigo, String nombre, String caracteristicas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.caracteristicas = caracteristicas;
        this.estado="PARADO";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getCodPlanta() {
        return codPlanta;
    }

    public void setCodPlanta(String codPlanta) {
        this.codPlanta = codPlanta;
    }

    public ArrayList<SubProceso> getSubProcesos() {
        return subProcesos;
    }

    public void setSubProcesos(ArrayList<SubProceso> subProcesos) {
        this.subProcesos = subProcesos;
    }
    
    public void agregarSubProceso(SubProceso subProceso){
        subProceso.setCodProceso(codigo);
        subProcesos.add(subProceso);
    }
    
    
}
