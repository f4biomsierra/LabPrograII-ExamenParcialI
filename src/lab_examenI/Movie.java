/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_examenI;


import java.util.*;

public class Movie extends RentItem {
    protected Calendar fechaEstreno;
    
    public Movie(String codigo, String nombre, double precioBase){
        super(codigo, nombre, precioBase);
        this.fechaEstreno=Calendar.getInstance();
    }

    public Calendar getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Calendar fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }
    
    public void setFechaEstreno(int year, int mes, int dia){
        fechaEstreno.set(year, mes-1, dia);
    }
    
    public String getEstado(){
        Calendar hoy=Calendar.getInstance();
        Calendar limite=(Calendar) fechaEstreno.clone();
        limite.add(Calendar.MONTH, 3);
        return hoy.before(limite) ? "ESTRENO" : "NORMAL";
    }
    
    @Override
    public double pagoRenta(int dias){
        double total=precioBase;
        if(getEstado().equals("ESTRENO") && dias>2){
            total=total+(dias-2)*50;
        } else if (getEstado().equals("NORMAL") && dias>5){
            total=total+(dias-5)*50;
        }
        return total;
    }
    
    public String toString(){
        return super.toString()+" | Estado: "+getEstado()+" - Movie";
    }
}
