package lab_examenI;

import javax.swing.ImageIcon;

//Clase abstracta
public abstract class RentItem {
    
    //atributos
    private String codigo;
    private String nombre;
    private double precioBase;
    private int copiasDisponibles;
    private ImageIcon imagen;
    
    //constructor inicializar datos
    public RentItem(String codigo, String nombre, double precioBase){
        this.codigo=codigo;
        this.nombre=nombre;
        this.precioBase=precioBase;
        this.copiasDisponibles=0;
    }
    
    //getters
    public String getCodigo(){
        return codigo;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public double getPrecioBase(){
        return precioBase;
    }
    
    public int getCopiasDisponibles(){
        return copiasDisponibles;
    }
    
    public ImageIcon getImagen(){
        return imagen;
    }
    
    //setters
    public void setImagen(ImageIcon imagen){
        this.imagen=imagen;
    }
    
    public void setCopiasDisponibles(int copiasDisponibles){
        this.copiasDisponibles=copiasDisponibles;
    } 
    
    //metodo abstracto q implementa movie y game
    public abstract double pagoRenta(int dias);
    
    @Override
    public String toString(){
        return "Codigo: " + codigo +
                "\nNombre: " + nombre +
                "\nPrecio Base: lps" + precioBase +
                "\nCopias Disponibles: " + copiasDisponibles;
    }
}
