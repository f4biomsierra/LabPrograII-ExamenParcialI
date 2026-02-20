/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_examenI;


import java.util.*;
import javax.swing.JOptionPane;

public class Game extends RentItem implements MenuActions {
    protected Calendar fechaPublicacion;
    protected ArrayList<String> especificaciones;
    
    public Game(String codigo, String nombre, double precioBase){
        super(codigo, nombre, 20.0);
        this.fechaPublicacion.getInstance();
        this.especificaciones=new ArrayList<>();
    }
    
    public void setFechaPublicacion(int year, int mes, int dia){
        fechaPublicacion.set(year, mes-1, dia);
    }
    
    private String listEspecificaciones(int index){
        if(index >= especificaciones.size())
            return "";
        return "- " + especificaciones.get(index) + "\n" + listEspecificaciones(index+1);
    }
    
    @Override
    public double pagoRenta(int dias){
        return precioBase*dias;
    }
    
    @Override
    public void submenu(){
        String[] opciones={"Actualizar fecha de publicación", "Agregar especificación, Ver especificaciones", "Cerrar"};
        int opcionSeleccionada=JOptionPane.showOptionDialog(
                null, 
                "Opciones para: "+nombre,
                "Submenú de Juego", 
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        
        if(opcionSeleccionada != -1 && opcionSeleccionada != 3){
            ejecutarOpcion(opcionSeleccionada+1);
        } 
    }
    
    @Override
    public void ejecutarOpcion(int opcion){
        switch (opcion){
            case 1:
                int year=Integer.parseInt(JOptionPane.showInputDialog("Año: "));
                int mes=Integer.parseInt(JOptionPane.showInputDialog("Mes (1-12):"));
                int dia=Integer.parseInt(JOptionPane.showInputDialog("Día:"));
                fechaPublicacion.set(year, mes-1, dia);
                break;
            case 2:
                String spec=JOptionPane.showInputDialog("Nuevo especificación:");
                if(spec != null) especificaciones.add(spec);
                break;
            case 3:
                String listado=especificaciones.isEmpty() ?
                        "No hay especificaciones." : listEspecificaciones(0);
                JOptionPane.showMessageDialog(null, listado);
                break;
        }
    }
    
    @Override
    public String toString(){
        return super.toString() + " | PS3 Game";
    }
}
