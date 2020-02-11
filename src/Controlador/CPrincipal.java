/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
import gestionmaquinas.magnumDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import sun.reflect.generics.tree.Tree;

/**
 *
 * @author Facundo Cordoba
 */
public class CPrincipal implements MouseListener, TreeExpansionListener {
    VArbol vista = new VArbol();
    magnumDB dataBase = new magnumDB();
    ArrayList<Planta> plantas = new ArrayList<Planta>();
    
    public CPrincipal(VArbol vista, magnumDB dataBase){
        this.vista = vista;
        this.dataBase = dataBase;
        this.plantas = dataBase.getPlantas();
        
        vista.jTree1.addMouseListener(this);
        //vista.jTree1.addTreeExpansionListener(this);
        cargarArbol(this.plantas);
    }
    
    
    //ARMAR ARBOL HASTA LISTAR PROCESOS Y OPERACIONES BASICAS
    public void cargarArbol(ArrayList<Planta> plantas){
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("MAGNUM");
        DefaultMutableTreeNode hijo = new DefaultMutableTreeNode("Agregar planta");
        raiz.add(hijo);
        
        //AGREGAR PLANTAS, PROCESOS, SUBPROCESOS, LINEAS Y MAQUINAS
        for(int i=0; i<plantas.size(); i++){
            agregarPlantaArbol(raiz,plantas.get(i));
        }
        
        //CARGAR ARBOL EN VISTA
        DefaultTreeModel modelo = new DefaultTreeModel(raiz);
        vista.setArbol(modelo);
        vista.setVisible(true);
    }
    
    public void agregarPlantaArbol(DefaultMutableTreeNode raiz, Planta planta){
        //AGREGAR PLANTA
        DefaultMutableTreeNode nodoPlanta = new DefaultMutableTreeNode(planta.getNombre()+" ("+planta.getCodigo()+")");
        //OPCIONES BASICAS EN PLANTA
        //DefaultMutableTreeNode opcion = new DefaultMutableTreeNode("Ver planta"); nodoPlanta.add(opcion);
        //opcion = new DefaultMutableTreeNode("Deshabilitar planta"); nodoPlanta.add(opcion);
        DefaultMutableTreeNode opcion = new DefaultMutableTreeNode("Agregar proceso"); nodoPlanta.add(opcion);
        
        //AGREGAR PROCESOS DE LA PLANTA
        for(Proceso proceso : planta.getProcesos()) {
            DefaultMutableTreeNode nodoProceso = new DefaultMutableTreeNode(proceso.getNombre()+" ("+proceso.getCodigo()+")");
            //OPCIONES BASICAS EN PROCESO
            //opcion = new DefaultMutableTreeNode("Ver proceso"); nodoProceso.add(opcion);
            //opcion = new DefaultMutableTreeNode("Deshabilitar proceso"); nodoProceso.add(opcion);
            opcion = new DefaultMutableTreeNode("Agregar subproceso"); nodoProceso.add(opcion);
            
            //AGREGAR SUBPROCESOS DEL PROCESO
            for(SubProceso subProceso: proceso.getSubProcesos()){
                DefaultMutableTreeNode nodoSubProceso = new DefaultMutableTreeNode(subProceso.getNombre()+" ("+subProceso.getCodigo()+")");
                //OPCIONES BASICAS EN SUBPROCESO
                //opcion = new DefaultMutableTreeNode("Ver subproceso"); nodoSubProceso.add(opcion);
                //opcion = new DefaultMutableTreeNode("Deshabilitar subproceso"); nodoSubProceso.add(opcion);
                opcion = new DefaultMutableTreeNode("Agregar linea"); nodoSubProceso.add(opcion);
                
                //AGREGAR LINEAS DEL SUBPROCESO
                for(Linea linea: subProceso.getLineas()){
                    DefaultMutableTreeNode nodoLinea = new DefaultMutableTreeNode("Linea "+linea.getNumero()+" ("+linea.getCodigo()+")");
                    //OPCIONES BASICAS EN SUBPROCESO
                    opcion = new DefaultMutableTreeNode("Ver maquinas"); nodoLinea.add(opcion);
                    //opcion = new DefaultMutableTreeNode("Deshabilitar linea"); nodoLinea.add(opcion);
                    //opcion = new DefaultMutableTreeNode("Agregar maquina"); nodoLinea.add(opcion);
                    
                    /*
                    //AGREGAR MAQUINAS DE LA LINEA
                    for(Maquina maquina: linea.getMaquinas()){
                        DefaultMutableTreeNode nodoMaquina = new DefaultMutableTreeNode(maquina.getNombre()+" ("+maquina.getCodigo()+")");
                        //OPCIONES BASICAS EN SUBPROCESO
                        opcion = new DefaultMutableTreeNode("Ver maquina"); nodoMaquina.add(opcion);
                        opcion = new DefaultMutableTreeNode("Deshabilitar maquina"); nodoMaquina.add(opcion);
                        
                        nodoLinea.add(nodoMaquina);
                    }
                    */
                    nodoSubProceso.add(nodoLinea);
                }
                nodoProceso.add(nodoSubProceso);
            }
            nodoPlanta.add(nodoProceso);
        }
        raiz.add(nodoPlanta);
    }
    
    //DETECTAR QUE ELEMENTO FUE CLICKEADO
    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int selRow = vista.jTree1.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = vista.jTree1.getPathForLocation(e.getX(), e.getY());
        if(selRow != -1) {
            if(e.getClickCount() == 1) { //CLICK SIMPLE
                //mySingleClick(selRow, selPath);      
        }
            else if(e.getClickCount() == 2) { //DOBLE CLICK
                //myDoubleClick(selRow, selPath);
                //PASAR PATH A ARRAY
                ArrayList<String> camino = new ArrayList<String>();
                String cadena = (selPath.toString()+",").replaceAll("\\[", "");
                cadena = cadena.toString().replaceAll("\\]", "");
                cadena = cadena.toString().replaceAll(", ", ",");
                
                while(cadena.length()!=0){
                    camino.add(cadena.substring(0,cadena.indexOf(",")));
                    cadena = cadena.replace(cadena.substring(0,cadena.indexOf(",")+1), "");
                }
                
                
                for (int i=1; i<camino.size();i++) {
                    String codigoObtenido = camino.get(i);
                    //System.out.println(codigoObtenido);
                    if(codigoObtenido.indexOf("(")!= -1){
                        codigoObtenido = camino.get(i).substring(camino.get(i).indexOf("(")+1, camino.get(i).indexOf(")"));
                        camino.set(i, codigoObtenido);
                    }
                   // System.out.println(codigoObtenido);
                }
                operacion(camino);
            }
        }
    }
    
    //OPERACION A EJECUTAR
    public void operacion(ArrayList camino){
        //AGREGAR PLANTA
        if(camino.get(camino.size()-1).equals("Agregar planta")){
            VCrearPlanta agregarPlanta = new VCrearPlanta();
            vista.setVisible(false);
            CPlanta controladorPlanta = new CPlanta(agregarPlanta, dataBase);
        }
        
        //AGREGAR PROCESO
        if(camino.get(camino.size()-1).equals("Agregar proceso")){
            //BUSCAR INDICE DE PLANTA EN DB DE PLANTAS
            int indice = 0;
            String codigoPlanta = camino.get(1).toString();
            for(int i=0; i< plantas.size(); i++){
                if(plantas.get(i).getCodigo().equals(codigoPlanta)){
                    indice=i;
                    break;
                }
            }
            
            //INICIALIZAR CREACION DE PROCESO
            VCrearProceso agregarProceso = new VCrearProceso();
            vista.setVisible(false);
            CProceso controladorProceso = new CProceso(agregarProceso, indice, dataBase);
        }
        
        //AGREGAR SUBPROCESO
        if(camino.get(camino.size()-1).equals("Agregar subproceso")){
            
            //OBTENER INDICE DE PLANTA Y DE PROCESO
            int indicePlanta = 0;
            int indiceProceso = 0;
            String codigoPlanta = camino.get(1).toString();
            String codigoProceso = camino.get(2).toString();
            
            for(int i=0; i< plantas.size(); i++){
                if(plantas.get(i).getCodigo().equals(codigoPlanta)){
                    indicePlanta=i;
                    for(i=0; i<plantas.get(indicePlanta).getProcesos().size(); i++){
                        if(plantas.get(indicePlanta).getProcesos().get(i).getCodigo().equals(codigoProceso)){
                            indiceProceso = i;
                            break;
                        }
                    }
                    break;
                }
            }
            
            VCrearSubProceso agregarSubProceso = new VCrearSubProceso();
            vista.setVisible(false);
            CSubProceso controladorSubProceso = new CSubProceso(agregarSubProceso, dataBase, indicePlanta, indiceProceso);
            
            //vista.setVisible(false);
            //CSubProceso controladorSubProceso = new CSubProceso(agregarSubProceso, dataBase);
        }
        
        if(camino.get(camino.size()-1).equals("Agregar linea")){
            //DEBERIA LLAMAR AL CONTROLADOR DE LINEA
            int indicePlanta=0;
            int indiceProceso=0;
            int indiceSubProceso=0;
            
            String codigoPlanta = camino.get(1).toString();
            String codigoProceso = camino.get(2).toString();
            String codigoSubProceso = camino.get(3).toString();
            
            for(int i=0; i< plantas.size(); i++){
                if(plantas.get(i).getCodigo().equals(codigoPlanta)){
                    indicePlanta=i;
                    for(i=0; i<plantas.get(indicePlanta).getProcesos().size(); i++){
                        if(plantas.get(indicePlanta).getProcesos().get(i).getCodigo().equals(codigoProceso)){
                            indiceProceso = i;
                            for(i=0;i<plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().size(); i++){
                                if(plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(i).getCodigo().equals(codigoSubProceso)){
                                    indiceSubProceso = i;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            
            VCrearLinea agregarLinea = new VCrearLinea();
            CLinea controladorLinea = new CLinea(agregarLinea, dataBase, indicePlanta, indiceProceso, indiceSubProceso);
            vista.setVisible(false);
        }
        
        /*if(camino.get(camino.size()-1).equals("Agregar maquina")){
            //DEBERIA LLAMAR AL CONTROLADOR DE LINEA
            int indicePlanta=0;
            int indiceProceso=0;
            int indiceSubProceso=0;
            int indiceLinea=0;
            
            String codigoPlanta = camino.get(1).toString();
            String codigoProceso = camino.get(2).toString();
            String codigoSubProceso = camino.get(3).toString();
            String codigoLinea = camino.get(4).toString();
            
            for(int i=0; i< plantas.size(); i++){
                if(plantas.get(i).getCodigo().equals(codigoPlanta)){
                    indicePlanta=i;
                    for(i=0; i<plantas.get(indicePlanta).getProcesos().size(); i++){
                        if(plantas.get(indicePlanta).getProcesos().get(i).getCodigo().equals(codigoProceso)){
                            indiceProceso = i;
                            for(i=0;i<plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().size(); i++){
                                if(plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(i).getCodigo().equals(codigoSubProceso)){
                                    indiceSubProceso = i;
                                    for(i=0;i<plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(indiceProceso).getLineas().size(); i++){
                                        if(plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(i).getLineas().get(i).getCodigo().equals(codigoLinea)){
                                            indiceLinea = i;
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            
            VCrearMaquina agregarMaquina = new VCrearMaquina();
            CMaquina controladorMaquina = new CMaquina(agregarMaquina, dataBase, indicePlanta, indiceProceso, indiceSubProceso, indiceLinea);
            vista.setVisible(false);
        }
        */
        
        if(camino.get(camino.size()-1).equals("Ver maquinas")){
            //DEBERIA LLAMAR AL CONTROLADOR DE LINEA
            int indicePlanta=0;
            int indiceProceso=0;
            int indiceSubProceso=0;
            int indiceLinea=0;
            
            String codigoPlanta = camino.get(1).toString();
            String codigoProceso = camino.get(2).toString();
            String codigoSubProceso = camino.get(3).toString();
            String codigoLinea = camino.get(4).toString();
            
            for(int i=0; i< plantas.size(); i++){
                if(plantas.get(i).getCodigo().equals(codigoPlanta)){
                    indicePlanta=i;
                    for(i=0; i<plantas.get(indicePlanta).getProcesos().size(); i++){
                        if(plantas.get(indicePlanta).getProcesos().get(i).getCodigo().equals(codigoProceso)){
                            indiceProceso = i;
                            for(i=0;i<plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().size(); i++){
                                if(plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(i).getCodigo().equals(codigoSubProceso)){
                                    indiceSubProceso = i;
                                    for(i=0;i<plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(indiceProceso).getLineas().size(); i++){
                                        if(plantas.get(indicePlanta).getProcesos().get(indiceProceso).getSubProcesos().get(i).getLineas().get(i).getCodigo().equals(codigoLinea)){
                                            indiceLinea = i;
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            
            VMaquina agregarMaquina = new VMaquina();
            CMaquina controladorMaquina = new CMaquina(agregarMaquina, dataBase, indicePlanta, indiceProceso, indiceSubProceso, indiceLinea);
            vista.setVisible(false);
        }
        
        
        
        
        
        
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    
    
    //EVENTOS CUANDO SE EXPANDE Y CONTRAE EL ARBOL
    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        
        /*
        System.out.println("Expandido");
        
        //PASAR PATH A ARRAYLIST
        ArrayList<String> camino2 = new ArrayList<String>();
                
        String cadena = (event.getPath().toString()+",").replaceAll("\\[", "");
        cadena = cadena.toString().replaceAll("\\]", "");
        cadena = cadena.toString().replaceAll(", ", ",");

        while(cadena.length()!=0){
            camino2.add(cadena.substring(0,cadena.indexOf(",")));
            cadena = cadena.replace(cadena.substring(0,cadena.indexOf(",")+1), "");
        }
        
        System.out.println(camino2.get(camino2.size()-1));
        
        
        DefaultTreeModel model = (DefaultTreeModel) vista.jTree1.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 0);
        
        for(int i=1; i<model.getChildCount(model.getRoot()); i++){
            //SI EL HIJO DE EL NODO RAIZ ES EL NODO CLICKEADO
            if(model.getChild(model.getRoot(), i).toString().equals(camino2.get(camino2.size()-1).toString())){
                //CARGAR PROCESOS Y SUBPROCESOS
            }
        }
        
        System.out.println(model.getChildCount(model.getRoot()));
        System.out.println(root);
        //model.insertNodeInto(new DefaultMutableTreeNode("another_child"), root, root.getChildCount());
        
        */
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
