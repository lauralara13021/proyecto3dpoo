package Consola;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Inventario.Inventario;

public class VentanaInicial extends JFrame{
	final public Inventario inventario;
	
	public VentanaInicial() {
		
		inventario = new Inventario();
		if (inventario.equals(null)) {
		}
		
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes entre botones

        JButton botonCliente = new JButton("Soy Cliente");
        JButton botonAdminGeneral = new JButton("Soy Administrador General");
        JButton botonAdminLocal = new JButton("Soy Administrador Local");
        JButton botonEmpleado = new JButton("Soy Empleado");
        JButton botonSalir = new JButton("Salir");

        add(botonCliente, gbc);
        add(botonAdminGeneral, gbc);
        add(botonAdminLocal, gbc);
        add(botonEmpleado, gbc);
        add(botonSalir, gbc);
        
        botonCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazCliente sesionCliente = new InterfazCliente(inventario);
                sesionCliente.setLocationRelativeTo( null );
                sesionCliente.setVisible(true);
            }
        });
        botonAdminGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazMenuAdmi menuadmi= new InterfazMenuAdmi(inventario);
                menuadmi.setLocationRelativeTo( null );
                menuadmi.setVisible(true);
            }
         });
        botonAdminLocal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	Sedes sedes = new Sedes(inventario);
            	sedes.setLocationRelativeTo(null);
            	sedes.setVisible(true);
            }
         });
        botonEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	InterfazEmpleado interfazEmpleado = new InterfazEmpleado(inventario);
            	interfazEmpleado.setLocationRelativeTo(null);
            	interfazEmpleado.setVisible(true);
            }
         });
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
         });
	}
	public static void main(String[] args) {
        VentanaInicial ventana = new VentanaInicial();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
	
	
	/**
	 * Método que inicia la aplicación al crear una instancia de la clase 'Inventario' y ejecutar su método 'aplicacion'.
	 */
	
}