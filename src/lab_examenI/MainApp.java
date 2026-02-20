/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_examenI;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Fabio Sierra
 */
public class MainApp extends JFrame{
    private ArrayList <RentItem> items= new ArrayList<>();
    private JPanel panelTarjetas;
    private JPanel panelCentro;  
    private Font fuente = new Font ("Arial", Font.PLAIN,30);
    
    public static void main(String[] args) {
        MainApp main = new MainApp();
        main.setVisible(true);
    }
    
    public MainApp(){
        setTitle("Sistema de Renta Multimedia");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        JButton btnAdd = new JButton("Agregar Ítem");
        btnAdd.setFont(fuente);
        JButton btnRent = new JButton("Rentar");
        btnRent.setFont(fuente);
        JButton btnPrint = new JButton("Imprimir Todo");
        btnPrint.setFont(fuente);

        menu.add(btnAdd); menu.add(btnRent); menu.add(btnPrint);
        add(menu, BorderLayout.NORTH);

        
        panelCentro = new JPanel();
        add(panelCentro, BorderLayout.CENTER);
        
        btnAdd.addActionListener(e -> agregarItem());
        btnRent.addActionListener(e -> rentar());
        btnPrint.addActionListener(e -> imprimirTodo());
    }
    
     private void limpiarCentro() {
        panelCentro.removeAll();
        panelCentro.revalidate();
        panelCentro.repaint();
    }
    
    private void agregarItem(){
        limpiarCentro();
        panelCentro.setLayout(null);

        JLabel labelTipo = new JLabel("Tipo de Ítem:");
        labelTipo.setFont(fuente);
        labelTipo.setBounds(50, 50, 200, 30); 

        String[] tipos = {"Movie", "Game"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        comboTipo.setFont(fuente);
        comboTipo.setBounds(350, 50, 200, 30); 
        
        JLabel labelCodigo = new JLabel("Código:");
        labelCodigo.setFont(fuente);
        labelCodigo.setBounds(50, 100, 200, 30); 

        JTextField codigo_texto = new JTextField();
        codigo_texto.setFont(fuente);
        codigo_texto.setBounds(350, 100, 200, 30);
        
        JLabel labelNombre =new JLabel("Nombre:");
        labelNombre.setFont(fuente);
        labelNombre.setBounds(50, 150, 200, 30); 

        JTextField nombre_texto= new JTextField();
        nombre_texto.setFont(fuente);
        nombre_texto.setBounds(350, 150, 200, 30); 

        JButton btnImagen=new JButton("Cargar Imagen");
        btnImagen.setFont(fuente);
        btnImagen.setBounds(50, 200, 250, 40);

        JLabel preview= new JLabel();
        preview.setBounds(350, 200, 100, 100);
        
        JButton btnGuardar= new JButton("Guardar Ítem");
        btnGuardar.setFont(fuente);
        btnGuardar.setBounds(350, 350, 300, 40);
        
        panelCentro.add(labelTipo);
        panelCentro.add(comboTipo);
        panelCentro.add(labelCodigo);
        panelCentro.add(codigo_texto);
        panelCentro.add(labelNombre);
        panelCentro.add(nombre_texto);
        panelCentro.add(btnImagen);
        panelCentro.add(preview);
        panelCentro.add(btnGuardar);
        
        btnImagen.addActionListener(e -> {
            ImageIcon img= cargarImagen();
                if (img!= null) {
                    preview.setIcon(img);
                }
        });

        btnGuardar.addActionListener(e -> {
            String tipo = comboTipo.getSelectedItem().toString();
            String codigo = codigo_texto.getText();
            String nombre = nombre_texto.getText();
            ImageIcon imagenSeleccionada=(ImageIcon) preview.getIcon();

            if (tipo.equals("Movie")) {
                Movie movie = new Movie(codigo, nombre);
                movie.setImagen(imagenSeleccionada);
                items.add(movie);
            } else {
                Game game = new Game(codigo, nombre);
                game.setImagen(imagenSeleccionada);
                items.add(game);
                game.submenu();
            }

            JOptionPane.showMessageDialog(this, "Ítem agregado correctamente");
            limpiarCentro();
        });
    }
    
    public void rentar(){
        limpiarCentro();

        JLabel labelCodigo = new JLabel("Código:");
        labelCodigo.setFont(fuente);
        labelCodigo.setBounds(50, 80, 150, 30);

        JTextField codigo_texto = new JTextField();
        codigo_texto.setFont(fuente);
        codigo_texto.setBounds(250, 80, 200, 30);

        JLabel labelDias = new JLabel("Días:");
        labelDias.setFont(fuente);
        labelDias.setBounds(50, 130, 150, 30);

        JTextField dias_texto = new JTextField();
        dias_texto.setFont(fuente);
        dias_texto.setBounds(250, 130, 200, 30);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setFont(fuente);
        btnCalcular.setBounds(250, 190, 200, 40);

        panelCentro.add(labelCodigo);
        panelCentro.add(codigo_texto);
        panelCentro.add(labelDias);
        panelCentro.add(dias_texto);
        panelCentro.add(btnCalcular);

        panelCentro.repaint();

        btnCalcular.addActionListener(e -> {
            for (RentItem item: items) {
                if (item.getCodigo().equals(codigo_texto.getText())) {
                    int dias = Integer.parseInt(dias_texto.getText());
                    double total = item.pagoRenta(dias);
                    JOptionPane.showMessageDialog(this,
                            "Total a pagar: Lps " + total);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Item No Existe");
        });
    }
    
    public void ejecutarSub(){
        limpiarCentro();
        JLabel labelCodigo = new JLabel("Código:");
        labelCodigo.setFont(fuente);
        labelCodigo.setBounds(50, 80, 150, 30);

        JTextField codigo_texto = new JTextField();
        codigo_texto.setFont(fuente);
        codigo_texto.setBounds(250, 80, 200, 30);
        
        panelCentro.add(labelCodigo);
        panelCentro.add(labelCodigo);
        panelCentro.repaint();
    }
    
    public void imprimirTodo(){
        limpiarCentro();
        int x= 50;
        int y= 40;

        for (RentItem item : items) {
            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(null);
            tarjeta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tarjeta.setBounds(x, y, 320, 180);

            JLabel imagenLabel= new JLabel();
            imagenLabel.setBounds(190, 20, 100, 100);
            
            if (item.getImagen()!= null) {
                imagenLabel.setIcon(item.getImagen());
            }
            tarjeta.add(imagenLabel);
            
            JLabel nombre = new JLabel("Nombre: "+item.getNombre());
            nombre.setBounds(10, 20, 180, 25);

            JLabel precio = new JLabel("Precio: Lps "+item.getPrecioBase());
            precio.setBounds(10, 50, 180, 25);

            tarjeta.add(nombre);
            tarjeta.add(precio);

            if (item instanceof Movie) {
                JLabel estado = new JLabel("Estado: "+((Movie) item).getEstado());
                estado.setBounds(10, 80, 230, 25);
                tarjeta.add(estado);
            }
          
            
            panelCentro.add(tarjeta);
            x += 270;
            if (x > 500) {
                x = 50;
                y+= 180;
            }
        }
        panelCentro.repaint();
    }
    
    private ImageIcon cargarImagen() {
        JFileChooser file= new JFileChooser();
        int opcion= file.showOpenDialog(this);

        if (opcion== JFileChooser.APPROVE_OPTION) {
            ImageIcon original= new ImageIcon(file.getSelectedFile().getAbsolutePath());
            Image imgEscalada= original.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(imgEscalada);
        }
        return null;
    }
}
