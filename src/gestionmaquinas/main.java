/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionmaquinas;
import Modelo.*;
import Vista.*;
import Controlador.*;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
/**
 *
 * @author Facundo Cordoba
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        VArbol vista = new VArbol();
        magnumDB dataBase = new magnumDB();
        
        //Inicializacion de plantas
        Planta plantaSM = new Planta("PSM","Planta San Miguel");
        Planta plantaCCBA = new Planta("PCCBA", "Planta Ciudad de Cordoba");
        plantaSM.agregagrProceso(new Proceso("PSM-LLND","Llenado","XD"));
        plantaSM.getProcesos().get(0).agregarSubProceso(new SubProceso("PSM-LLND-INY", "Inyeccion", "LOLOLOL"));
        plantaSM.getProcesos().get(0).getSubProcesos().get(0).agregarLinea(new Linea("PSM-LLND-INY-LN01", 1, "asddsa"));
        
        dataBase.agregarPlanta(plantaSM);
        dataBase.agregarPlanta(plantaCCBA);
        ArrayList<String> xd = new ArrayList<>();
        /*xd.add("aa");
        xd.add("bb");
        xd.add("cc");
        xd.add("dd");
        System.out.println(xd+"tamaño: "+xd.size());
        System.out.println(xd.subList(1, xd.size())+"tamaño: "+xd.subList(1, xd.size()).size());*/
        CPrincipal controlador = new CPrincipal(vista, dataBase);    
    }
}
