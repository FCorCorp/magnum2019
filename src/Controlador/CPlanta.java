/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Planta;
import Vista.VArbol;
import Vista.VCrearPlanta;
import gestionmaquinas.magnumDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Facundo Cordoba
 */
public class CPlanta implements ActionListener {

    VCrearPlanta vista = new VCrearPlanta();
    magnumDB dataBase = new magnumDB();
    
    public CPlanta (VCrearPlanta vista,magnumDB dataBase){
        this.vista = vista;
        this.dataBase = dataBase;
        
        this.vista.btnAceptar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        vista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(e.getActionCommand()=="Cancelar"){
            VArbol vista = new VArbol();
            CPrincipal controladorPrincipal =new CPrincipal(vista,dataBase);
            vista.setVisible(false);
        }
        if(e.getActionCommand()=="Aceptar"){
            Planta planta = new Planta(vista.campoCodigo.getText(),vista.campoNombre.getText());
            dataBase.agregarPlanta(planta);
            VArbol vista = new VArbol();
            CPrincipal controladorPrincipal =new CPrincipal(vista, dataBase);
            vista.setVisible(false);
        }
    }
    
    public void agregarPlanta(){
        
    }
    
    
}
