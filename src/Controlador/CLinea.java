/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Linea;
import Vista.VArbol;
import Vista.VCrearLinea;
import gestionmaquinas.magnumDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Facundo Cordoba
 */
public class CLinea implements ActionListener{
    
    VCrearLinea vista = new VCrearLinea();
    magnumDB dataBase = new magnumDB();
    int indicePlanta;
    int indiceProceso;
    int indiceSubProceso;
    
    public CLinea(VCrearLinea vista, magnumDB dataBase, int indicePlanta, int indiceProceso, int indiceSubProceso){
        
        this.vista = vista;
        this.dataBase = dataBase;
        this.indicePlanta = indicePlanta;
        this.indiceProceso = indiceProceso;
        this.indiceSubProceso = indiceSubProceso;
        
        vista.btnAceptar.addActionListener(this);
        vista.btnCancelar.addActionListener(this);
        vista.campoCodigoSubproceso.setText(dataBase.getPlantas().get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(indiceSubProceso).getCodigo());
        
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals("Cancelar")){
            VArbol arbol = new VArbol();
            CPrincipal controladorPrincipal = new CPrincipal(arbol, dataBase);
            vista.setVisible(false);
        }
        
        if(e.getActionCommand().equals("Aceptar")){
            String codigoLinea = vista.campoCodigoSubproceso.getText()+"-"+vista.campoCodigo.getText();
            Linea linea = new Linea(codigoLinea, Integer.parseInt(vista.campoNumero.getText()),vista.campoDescripcion.getText());
            linea.setCodSubProceso(vista.campoCodigoSubproceso.getText());
            dataBase.getPlantas().get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(indiceSubProceso).agregarLinea(linea);
            
            VArbol vistaArbol = new VArbol();
            CPrincipal controladorPrincipal = new CPrincipal(vistaArbol,dataBase);
            vista.setVisible(false);
            
        }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
