/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_examenI;


import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Game extends RentItem implements MenuActions {
    protected Calendar fechaPublicacion;
    protected ArrayList<String> especificaciones;
    
    public Game(String codigo, String nombre){
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
        JDialog ventanaSubmenu=new JDialog();
        ventanaSubmenu.setTitle("Opciones de Videojuego: "+nombre);
        ventanaSubmenu.setSize(300, 350);
        ventanaSubmenu.setModal(true);
        ventanaSubmenu.setLocationRelativeTo(null);
        
        ventanaSubmenu.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        Dimension tamañoBtn=new Dimension(260, 45);
        
        JButton btnFecha=new JButton("Actualizar fecha de publicación");
        JButton btnAgregar=new JButton("Agregar especificación");
        JButton btnEspec=new JButton("Ver especificaciones");
        JButton btnCerrar=new JButton("Regresar al Menú");
        
        JButton[] botones={btnFecha, btnAgregar, btnEspec, btnCerrar};
        
        btnFecha.addActionListener(evento->{
            ejecutarOpcion(1);
        });
        
        btnAgregar.addActionListener(evento->{
            ejecutarOpcion(2);
        });
        
        btnEspec.addActionListener(evento->{
            ejecutarOpcion(3);
        });
        
        btnCerrar.addActionListener(evento -> ventanaSubmenu.dispose());
        
        ventanaSubmenu.add(btnFecha);
        ventanaSubmenu.add(btnAgregar);
        ventanaSubmenu.add(btnEspec);
        ventanaSubmenu.add(btnCerrar);
        
        ventanaSubmenu.setVisible(true);
    }
    
    @Override
    public void ejecutarOpcion(int opcion){
        switch (opcion){
            case 1:
                try{
                    int year = Integer.parseInt(JOptionPane.showInputDialog("Año: "));
                    int mes = Integer.parseInt(JOptionPane.showInputDialog("Mes (1-12):"));
                    int dia = Integer.parseInt(JOptionPane.showInputDialog("Día:"));
                    fechaPublicacion.set(year, mes - 1, dia);
                    JOptionPane.showMessageDialog(null, "Fecha actualizada con éxito.");
                } catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Error en los datos ingresados.");
                }
                break;
            case 2:
                String nuevaEspecificacion=JOptionPane.showInputDialog("Nuevo especificación:");
                if(nuevaEspecificacion != null && !nuevaEspecificacion.isEmpty()){
                    especificaciones.add(nuevaEspecificacion);
                }
                break;
            case 3:
                String textoFinal=especificaciones.isEmpty()?
                        "No hay especifiaciones registradas." : listEspecificaciones(0);
                
                JTextArea areaTexto=new JTextArea(textoFinal);
                areaTexto.setEditable(false);
                JOptionPane.showMessageDialog(null, new JScrollPane(areaTexto), "Lista de Especificaciones", 1);
                break;
        }
    }
    
    @Override
    public String toString(){
        return super.toString() + " | PS3 Game";
    }
}
