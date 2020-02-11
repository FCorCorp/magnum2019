/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.VAgregarCaracteristica;
import Vista.VAgregarMaquina;
import Vista.VArbol;
import Vista.VCrearMaquina;
import Vista.VMaquina;
import gestionmaquinas.magnumDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;

/**
 *
 * @author Facundo Cordoba
 */
public class CMaquina implements ActionListener, MouseListener {
    VMaquina vista = new VMaquina();
    VAgregarMaquina vistaAgregar = new VAgregarMaquina();
    VAgregarCaracteristica vistaCaracteristica = new VAgregarCaracteristica();
    
    magnumDB dataBase = new magnumDB();
    int indicePlanta;
    int indiceProceso;
    int indiceSubProceso;
    int indiceLinea;
    DefaultTreeModel modelo;
    
    Linea linea;
    ArrayList<String> nodos = new ArrayList<>();
    String elementoElegido="";
    
    public CMaquina(VMaquina vista, magnumDB dataBase, int indicePlanta, int indiceProceso, int indiceSubProceso, int indiceLinea){
        
        this.vista = vista;
        this.dataBase = dataBase;
        this.indicePlanta = indicePlanta;
        this.indiceProceso = indiceProceso;
        this.indiceSubProceso = indiceSubProceso;
        this.indiceLinea = indiceLinea;
        
        vista.btnAgregar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnAtras.addActionListener(this);
        vista.arbolMaquinas.addMouseListener(this);
        
        vistaAgregar.btnAceptar.addActionListener(acAgregarMaquina);
        vistaAgregar.btnCancelar.addActionListener(acAgregarMaquina);
        vistaAgregar.btnAgregarCarac.addActionListener(acAgregarMaquina);
        vistaAgregar.btnQuitarCarac.addActionListener(acAgregarMaquina);
        
        vistaCaracteristica.btnAceptar.addActionListener(acCaracteristica);
        vistaCaracteristica.btnCancelar.addActionListener(acCaracteristica);
        
        prepararVista();
        vista.setVisible(true);
    }
    
    
    public void prepararVista(){
        linea = dataBase.getPlantas().get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(indiceSubProceso).getLineas().get(indiceLinea);
        cargarArbol(linea);
    }

    public void cargarArbol(Linea linea){
        
        DefaultMutableTreeNode padre = new DefaultMutableTreeNode(linea.getCodigo()); 
        cargarMaquinasArbol(linea.getMaquinas(), padre);
        modelo = new DefaultTreeModel(padre);
        vista.arbolMaquinas.setModel(modelo);
    }
    
    public void cargarMaquinasArbol(ArrayList<Maquina> maquinas, DefaultMutableTreeNode padre){
        for(Maquina maquina : maquinas){
            DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(maquina.getCodigo());
            if(maquina.getSubMaquinas().size()>0){
                cargarMaquinasArbol(maquina.getSubMaquinas(), hijo);
            }
            padre.add(hijo);
        }
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals("Agregar")){
            if(elementoElegido.equals("")){
                System.out.println("Elija un elemento al cual agregar una maquina");
            }
            else{
                vista.setEnabled(false);
                vistaAgregar.limpiarVentana();
                vistaAgregar.setVisible(true);
            }
        }
        
        if(e.getActionCommand().equals("Eliminar")){
            if(nodos.size()==1){
                System.out.println("No se puede eliminar el nodo de Linea");
            }
            else{
                eliminarMaquina(linea.getMaquinas(),nodos.subList(1, nodos.size()));
            }
            cargarArbol(linea);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    ActionListener acCaracteristica = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if(e.getActionCommand().equals("Aceptar")){
                DefaultTableModel modelo = (DefaultTableModel) vistaAgregar.tablaCaracteristicas.getModel();
                modelo.addRow(new Object[]{vistaCaracteristica.campoNombre.getText(), vistaCaracteristica.campoValor.getText(),vistaCaracteristica.campoSimbolo.getText()});
                vistaAgregar.tablaCaracteristicas.setModel(modelo);
                
                vistaCaracteristica.setVisible(false);
                vistaCaracteristica.limpiarVentana();
                vistaAgregar.setEnabled(true);
                vistaAgregar.toFront();
            }
            if(e.getActionCommand().equals("Cancelar")){
                vistaCaracteristica.setVisible(false);
                vistaCaracteristica.limpiarVentana();
                vistaAgregar.setEnabled(true);
                vistaAgregar.toFront();
            }
        }
    };
    
    
    ActionListener acAgregarMaquina = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            
            if(e.getActionCommand().equals("Aceptar")){
                Maquina maquina = new Maquina(vistaAgregar.campoCodigo.getText(), vistaAgregar.campoNombre.getText());
                maquina.setCodLinea(linea.getCodigo());
                DefaultTableModel modelo = (DefaultTableModel) vistaAgregar.tablaCaracteristicas.getModel();
                //maquina.setCodigoPadre(elementoElegido);
                for(int i=0;i<vistaAgregar.tablaCaracteristicas.getRowCount();i++){
                    maquina.getCaracteristicas().add(new Caracteristica(modelo.getValueAt(i, 0).toString(),modelo.getValueAt(i, 1).toString(),modelo.getValueAt(i, 2).toString()));
                }
                
                if(nodos.size()==1){
                    maquina.setCodigoPadre(linea.getCodigo());
                    dataBase.getPlantas().get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(indiceSubProceso).getLineas().get(indiceLinea).agregarMaquina(maquina);
                }
                else{
                    Maquina maquinaR = linea.getMaquinas().get(buscarIndice(nodos.get(1),linea.getMaquinas()));
                    System.out.println(nodos.subList(1,nodos.size()));
                    agregarMaquina(maquina,maquinaR,nodos.subList(2,nodos.size()));
                }

                vistaAgregar.setVisible(false);
                cargarArbol(linea);
                vista.setEnabled(true);
                vista.toFront();
                
                System.out.println(maquina.getCaracteristicas());
            }
            
            if(e.getActionCommand().equals("Cancelar")){
                vistaAgregar.setVisible(false);
                vista.setEnabled(true);
                vista.toFront();
            }

            if(e.getActionCommand().equals("Agregar caracteristica")){
                vistaAgregar.setEnabled(false);
                vistaCaracteristica.setVisible(true);
            }
            
            if(e.getActionCommand().equals("Quitar caracteristica")){
                DefaultTableModel modelo = (DefaultTableModel) vistaAgregar.tablaCaracteristicas.getModel();
                modelo.removeRow(vistaAgregar.tablaCaracteristicas.getSelectedRow());
                vistaAgregar.tablaCaracteristicas.setModel(modelo);
            }
        }
    };
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int selRow = vista.arbolMaquinas.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = vista.arbolMaquinas.getPathForLocation(e.getX(), e.getY());
        if(selRow != -1) {
            if(e.getClickCount() == 1) { //CLICK SIMPLE
                //mySingleClick(selRow, selPath);
                String codigo = selPath.toString().replace("[", "").replace("]", "");
                cadenaToArray(codigo);
                //System.out.println("Simple click: "+ nodos.get(nodos.size()-1));
                elementoElegido = nodos.get(nodos.size()-1);
                if(nodos.size()>1){
                    buscarMaquina(linea.getMaquinas(),nodos);
                }
                else{
                vista.campoCodigo.setText(linea.getCodigo());
                vista.campoNombre.setText("Linea numero "+linea.getNumero());
                DefaultTableModel modelo = (DefaultTableModel) vista.tablaPreviaCaract.getModel();
                modelo.setRowCount(0);
                vista.tablaPreviaCaract.setModel(modelo);
                }
            }
            /*else if(e.getClickCount() == 2) { //DOBLE CLICK
                //myDoubleClick(selRow, selPath);
                System.out.println("Doble click: "+ selPath.toString());
            }*/
        }
    }
    
    
    public void cadenaToArray(String camino){
        nodos = new ArrayList<String>();
        camino = camino.replaceAll(", ", ",");
        camino = camino+",";
        
        
        while(camino.length()!=0){
            //System.out.println(camino.substring(0,camino.indexOf(",")+1));
            nodos.add(camino.substring(0,camino.indexOf(",")));
            camino = camino.replace(camino.substring(0,camino.indexOf(",")+1), "");
        }
        ///System.out.println(nodos);
    }
    
    public int buscarIndice(String codigo, ArrayList<Maquina> maquinas){
        int indice=-1;
        for(int j=0; j<maquinas.size();j++){
            if(maquinas.get(j).getCodigo().equals(codigo)){
                indice=j;
                break;
            }
        }
        return indice;
    }
    
    public void agregarMaquina(Maquina maquinaNueva, Maquina maquina, List<String> nodos){
        System.out.println(nodos);
        if(nodos.size()>0){
            System.out.println("entra mayor a cero");
            System.out.println(maquina.getSubMaquinas().get(buscarIndice(nodos.get(0),maquina.getSubMaquinas())).getCodigo());
            System.out.println(nodos.subList(1, nodos.size()));
            agregarMaquina(maquinaNueva,maquina.getSubMaquinas().get(buscarIndice(nodos.get(0),maquina.getSubMaquinas())),nodos.subList(1, nodos.size()));
        }
        else{
            System.out.println("entra igual a cero");
            maquinaNueva.setCodigoPadre(maquina.getCodigo());
            maquina.getSubMaquinas().add(maquinaNueva);
        }
    }
    
    public void buscarMaquina(ArrayList<Maquina> maquinas, ArrayList<String> nodos){
        ArrayList<Maquina> maquinasBuscar = maquinas;
         Maquina maquina = null;
        List<String> listaNodos = nodos.subList(1, nodos.size());
        for(String codMaquina: listaNodos){
            maquina = maquinasBuscar.get(buscarIndice(codMaquina, maquinasBuscar));
            maquinasBuscar = maquina.getSubMaquinas();
        }
        vista.campoCodigo.setText(maquina.getCodigo());
        vista.campoNombre.setText(maquina.getNombre());
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaPreviaCaract.getModel();
        modelo.setRowCount(indicePlanta);
        for(Caracteristica caracteristica: maquina.getCaracteristicas()){
            modelo.addRow(new Object[]{caracteristica.getNombre(),caracteristica.getValor(),caracteristica.getSimbolo()});
        }
        vista.tablaPreviaCaract.setModel(modelo);
    }
    
    
    public void eliminarMaquina(ArrayList<Maquina> maquinas, List<String> nodos){
        if(nodos.size()>1){
            eliminarMaquina(maquinas.get(buscarIndice(nodos.get(0), maquinas)).getSubMaquinas(),nodos.subList(1, nodos.size()));
        }
        else{
            maquinas.remove(buscarIndice(nodos.get(0), maquinas));
        }
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
