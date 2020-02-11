/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.SubProceso;
import Vista.VArbol;
import Vista.VCrearSubProceso;
import gestionmaquinas.magnumDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Facundo Cordoba
 */
public class CSubProceso implements ActionListener {
    VCrearSubProceso vista = new VCrearSubProceso();
    magnumDB dataBase = new magnumDB();
    int indicePlanta;
    int indiceProceso;
    
    
    public CSubProceso(VCrearSubProceso vista, magnumDB dataBase, int indicePlanta, int indiceProceso){
        this.vista = vista;
        this.dataBase = dataBase;
        this.indicePlanta = indicePlanta;
        this.indiceProceso = indiceProceso;
        
        vista.btnAceptar.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
        vista.campoCodigoProceso.setText(dataBase.getPlantas().get(indicePlanta).getProcesos().get(indiceProceso).getCodigo());
        vista.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().toString() == "Cancelar"){
            VArbol vistaArbol = new VArbol();
            CPrincipal controladorPrincipal = new CPrincipal(vistaArbol,dataBase);
            vista.setVisible(false);
        }
        
        if(e.getActionCommand().toString() == "Aceptar"){
            String codigoSubProceso = vista.campoCodigoProceso.getText()+"-"+vista.campoCodigo.getText();
            SubProceso subProceso = new SubProceso(codigoSubProceso, vista.campoNombre.getText(), vista.campoDescripcion.getText());
            subProceso.setCodProceso(vista.campoCodigoProceso.getText());
            dataBase.getPlantas().get(indicePlanta).getProcesos().get(indiceProceso).agregarSubProceso(subProceso);
            
            VArbol vistaArbol = new VArbol();
            CPrincipal controladorPrincipal = new CPrincipal(vistaArbol,dataBase);
            vista.setVisible(false);
        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
