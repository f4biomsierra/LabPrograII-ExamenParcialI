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
import java.util.*;

/**
 *
 * @author Fabio Sierra
 */
public class MainApp extends JFrame{
    private ArrayList <RentItem> items= new ArrayList<>();
    private JPanel panelTarjetas;
    private JPanel panelCentro;  
    private Font fuente = new Font ("Arial", Font.PLAIN,30);
    private JSpinner dateSpinner;
    private JLabel labelFechaDinamica;
    
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
    
    private void agregarItem() {
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

    JLabel labelNombre = new JLabel("Nombre:");
    labelNombre.setFont(fuente);
    labelNombre.setBounds(50, 150, 200, 30);

    JTextField nombre_texto = new JTextField();
    nombre_texto.setFont(fuente);
    nombre_texto.setBounds(350, 150, 200, 30);

    JButton btnImagen = new JButton("Cargar Imagen");
    btnImagen.setFont(fuente);
    btnImagen.setBounds(50, 260, 250, 40);

    JLabel preview = new JLabel();
    preview.setBounds(350, 260, 100, 100);

    labelFechaDinamica = new JLabel("Fecha de Estreno:");
    labelFechaDinamica.setFont(fuente);
    labelFechaDinamica.setBounds(50, 200, 320, 30);

    SpinnerDateModel model = new SpinnerDateModel();
    dateSpinner = new JSpinner(model);
    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
    dateSpinner.setEditor(editor);
    dateSpinner.setFont(fuente);
    dateSpinner.setBounds(380, 200, 200, 30);

    comboTipo.addActionListener(e -> {
        String seleccion = (String) comboTipo.getSelectedItem();
        labelFechaDinamica.setText(seleccion.equals("Movie") ? "Fecha de Estreno:" : "Fecha de Publicación:");
    });

    JButton btnGuardar = new JButton("Guardar Ítem");
    btnGuardar.setFont(fuente);
    btnGuardar.setBounds(350, 350, 300, 40);

    panelCentro.add(labelTipo);
    panelCentro.add(comboTipo);
    panelCentro.add(labelCodigo);
    panelCentro.add(codigo_texto);
    panelCentro.add(labelNombre);
    panelCentro.add(nombre_texto);
    panelCentro.add(btnImagen);
    panelCentro.add(labelFechaDinamica);
    panelCentro.add(dateSpinner);
    panelCentro.add(preview);
    panelCentro.add(btnGuardar);

    btnImagen.addActionListener(e -> {
        ImageIcon img = cargarImagen();
        if (img != null) {
            preview.setIcon(img);
        }
    });

    btnGuardar.addActionListener(e -> {
        String tipo = comboTipo.getSelectedItem().toString();
        String codigo = codigo_texto.getText().trim();
        String nombre = nombre_texto.getText().trim();

        // --- VALIDACIÓN DE CAMPOS VACÍOS ---
        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- VALIDACIÓN DE CÓDIGO ÚNICO ---
        for (RentItem item : items) {
            if (item.getCodigo().equals(codigo)) {
                JOptionPane.showMessageDialog(this, "El código del ítem es inválido o ya existe", "Código Duplicado", JOptionPane.ERROR_MESSAGE);
                return; // Sale del listener y no guarda nada
            }
        }

        ImageIcon imagenSeleccionada = (ImageIcon) preview.getIcon();
        Date fecha = (Date) dateSpinner.getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        if (tipo.equals("Movie")) {
            Movie movie = new Movie(codigo, nombre);
            movie.setImagen(imagenSeleccionada);
            movie.setFechaEstreno(cal);
            items.add(movie);
        } else {
            Game game = new Game(codigo, nombre);
            game.setImagen(imagenSeleccionada);
            game.setFechaPublicacion(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            items.add(game);
            game.submenu();
        }

        JOptionPane.showMessageDialog(this, "Ítem agregado correctamente");
        limpiarCentro();
    });
}

    public void rentar() {
        limpiarCentro();

        JLabel labelCodigo = new JLabel("Código:");
        labelCodigo.setFont(fuente);
        labelCodigo.setBounds(50, 30, 150, 30);

        JTextField codigo_texto = new JTextField();
        codigo_texto.setFont(fuente);
        codigo_texto.setBounds(250, 30, 200, 30);

        JButton btnVerificar = new JButton("Verificar Ítem");
        btnVerificar.setFont(new Font("Arial", Font.BOLD, 18));
        btnVerificar.setBounds(50, 70, 200, 30);

        JPanel datosPanel = new JPanel(null);
        datosPanel.setBounds(50, 80, 700, 400);
        datosPanel.setOpaque(false);

        panelCentro.add(labelCodigo);
        panelCentro.add(codigo_texto);
        panelCentro.add(btnVerificar);
        panelCentro.add(datosPanel);

        btnVerificar.addActionListener(e -> {
            datosPanel.removeAll();
            RentItem encontrado = null;

            try {
                String codBuscado =codigo_texto.getText();
                for (RentItem item : items) {
                    if (item.getCodigo().equals(codBuscado)) {
                        encontrado = item;
                        break;
                    }
                }
            } catch (NumberFormatException ex) {
                JLabel error = new JLabel("Error: Ingrese un código numérico.");
                error.setForeground(Color.RED);
                error.setBounds(0, 0, 400, 30);
                datosPanel.add(error);
            }

            if (encontrado != null) {
                JLabel info = new JLabel("Datos del Item");
                info.setFont(fuente);
                info.setBounds(0, 30, 400, 30);
                
                JLabel labelNombre= new JLabel ("Nombre: "+ encontrado.getNombre());
                labelNombre.setFont(fuente);
                labelNombre.setBounds(0, 75, 400, 30);
                
                JLabel labelPrecio= new JLabel ("Precio Base: Lps. " + encontrado.getPrecioBase());
                labelPrecio.setFont(fuente);
                labelPrecio.setBounds(0, 115, 400, 30);

                JLabel imgView = new JLabel();
                imgView.setHorizontalAlignment(JLabel.CENTER);
                if(encontrado.getImagen() != null) 
                    imgView.setIcon(encontrado.getImagen());
                imgView.setBounds(480, 30, 200, 200);
                imgView.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                JLabel labelDias = new JLabel("Cantidad de Días:");
                labelDias.setFont(fuente);
                labelDias.setBounds(0, 170, 250, 30);

                JTextField dias_texto = new JTextField();
                dias_texto.setFont(fuente);
                dias_texto.setBounds(260, 170, 200, 30);

                JButton btnCalcular = new JButton("Calcular Renta");
                btnCalcular.setBounds(0, 220, 260, 45);
                btnCalcular.setFont(fuente);

                JLabel lblTotal = new JLabel("Total a Pagar: ---");
                lblTotal.setFont(new Font("Arial", Font.BOLD, 26));
                lblTotal.setBounds(0, 280, 500, 40);

                final RentItem itemFinal = encontrado;
                btnCalcular.addActionListener(ev -> {
                    try {
                        int d = Integer.parseInt(dias_texto.getText());
                        double total = itemFinal.pagoRenta(d);
                        lblTotal.setText("Total a Pagar: Lps. " + total);
                    } catch (Exception ex) {
                        lblTotal.setText("Error: Ingrese días válidos.");
                    }
                });

                datosPanel.add(info);
                datosPanel.add(imgView);
                datosPanel.add(labelDias);
                datosPanel.add(dias_texto);
                datosPanel.add(btnCalcular);
                datosPanel.add(lblTotal);
                datosPanel.add(labelNombre);
                datosPanel.add(labelPrecio);

            } else if (!codigo_texto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Item No Existe." );
               
            }

            datosPanel.revalidate();
            datosPanel.repaint();
        });

        panelCentro.revalidate();
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
            
            JLabel codigo = new JLabel("Codigo: "+item.getCodigo());
            codigo.setBounds(10, 50, 180, 25);

            JLabel precio = new JLabel("Precio: Lps "+item.getPrecioBase());
            precio.setBounds(10, 80, 180, 25);

            tarjeta.add(nombre);
            tarjeta.add(codigo);
            tarjeta.add(precio);

            if (item instanceof Movie) {
                JLabel estado = new JLabel("Estado: "+((Movie) item).getEstado());
                estado.setBounds(10, 110, 230, 25);
                tarjeta.add(estado);
            }
          
            
            panelCentro.add(tarjeta);
            x += 340;
            if (x > 700) {
                x = 50;
                y+= 200;
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
