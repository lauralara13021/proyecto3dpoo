package Consola;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.*;
import SistemaAlquiler.*;
import Inventario.Inventario;

class Sedes extends JFrame implements ActionListener{
	JComboBox<String> listaDesplegable;
	Inventario inventario;
	VehiculoRentalSystem rentalSystem;
		
	public Sedes(Inventario inventario){
	this.inventario = inventario;
	rentalSystem = inventario.getAplicacion();
	
	setTitle("Lights out");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(500,500);	
	
	String[] opciones = {"Sede", "Sucursal Norte", "Sucursal Central", "Sucursal Sur"};
	listaDesplegable = new JComboBox<>(opciones);
	listaDesplegable.setPreferredSize(new Dimension(200, 50));
	
	JLabel etiqueta = new JLabel("Seleccione la sede que administra");
	Font fuentePersonalizada = new Font("Georgia", Font.BOLD, 20); // Cambiar a la fuente y tamaño deseados
    etiqueta.setFont(fuentePersonalizada);
	
	JButton boton = new JButton("Confirmar");
	boton.addActionListener(this);
	boton.setActionCommand("Confirmar");
	
	
	Font fuenteBoton = new Font("Verdana", Font.PLAIN, 14);
    boton.setFont(fuenteBoton);

    // Aplicar fuente personalizada al JComboBox
    Font fuenteComboBox = new Font("Verdana", Font.PLAIN, 14);
    listaDesplegable.setFont(fuenteComboBox);
	
	 // Crear un panel con GridBagLayout
    JPanel panel = new JPanel(new GridBagLayout());

    // Configurar las restricciones del GridBagConstraints para centrar el JComboBox
    GridBagConstraints gbc = new GridBagConstraints();
    
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
    panel.add(etiqueta, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre el JComboBox y el botón
    panel.add(listaDesplegable, gbc);

    // Configurar las restricciones para el botón
    gbc.gridy = 2;
    panel.add(boton, gbc);
    
    //panel.add(new JLabel("Seleccione una opción", JLabel.CENTER));
    // Configurar el diseño del marco y agregar el panel
    setLayout(new BorderLayout());
    add(panel, BorderLayout.CENTER);

    // Centrar la ventana en la pantalla
    setLocationRelativeTo(null);
    
 // Configurar el diseño del marco y agregar el panel
 	add(panel);
 	setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Obtener la opción seleccionada de la lista desplegable
		String sedeOpcion = (String) listaDesplegable.getSelectedItem();
		System.out.print(sedeOpcion);
		
		if(sedeOpcion.equals("Sede")) {
			JOptionPane.showMessageDialog(null, "Elija una opción válida");
		}else {
			Sede sede = rentalSystem.getSedes().get(sedeOpcion);
			
			InterfazAdminLocal menuLocal= new InterfazAdminLocal(inventario, sede);
	        
	        menuLocal.setLocationRelativeTo( null );
	        menuLocal.setVisible(true);
		}
			
	}

}
