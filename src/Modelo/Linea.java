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
public class Linea {
    private String codigo;
    private int numero;
    private String estado;
    private String caracteristicas;
    
    private String codSubProceso;
    private ArrayList<Maquina> maquinas = new ArrayList<>();

    public Linea(String codigo, int numero, String caracteristicas) {
        this.codigo = codigo;
        this.numero = numero;
        this.caracteristicas = caracteristicas;
        this.estado = "PARADO";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public String getCodSubProceso() {
        return codSubProceso;
    }

    public void setCodSubProceso(String codSubProceso) {
        this.codSubProceso = codSubProceso;
    }

    public ArrayList<Maquina> getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(ArrayList<Maquina> maquinas) {
        this.maquinas = maquinas;
    }
    
    public void agregarMaquina(Maquina maquina){
        maquinas.add(maquina);
    }
    
    
}
