/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.VArbol;
import Vista.VCrearProceso;
import gestionmaquinas.magnumDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Facundo Cordoba
 */
public class CProceso implements ActionListener{
    VCrearProceso vista = new VCrearProceso();
    magnumDB dataBase = new magnumDB();
    ArrayList<Planta> plantas = new ArrayList<Planta>();
    int indicePlanta;
    
    public CProceso(VCrearProceso vista, int indicePlanta, magnumDB dataBase){
        this.vista = vista;
        this.dataBase = dataBase;
        this.plantas = dataBase.getPlantas();
        this.indicePlanta = indicePlanta;
        
        vista.campoCodigoPadre.setText(plantas.get(indicePlanta).getCodigo());
        vista.setVisible(true);
        
        vista.btnAceptar.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand() == "Cancelar"){
            VArbol vistaArbol = new VArbol();
            CPrincipal controladorPrincipal = new CPrincipal(vistaArbol,dataBase);
            vista.setVisible(false);
        }
        
        if(e.getActionCommand() == "Aceptar"){
            String codigoProceso = vista.campoCodigoPadre.getText()+"-"+vista.campoCodigo.getText();
            Proceso proceso = new Proceso(codigoProceso, vista.campoNombre.getText(), vista.campoDescripcion.getText());
            proceso.setCodPlanta(vista.campoCodigoPadre.getText());
            dataBase.getPlantas().get(indicePlanta).agregagrProceso(proceso);
            
            VArbol vistaArbol = new VArbol();
            CPrincipal controladorPrincipal = new CPrincipal(vistaArbol,dataBase);
            vista.setVisible(false);
        }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
