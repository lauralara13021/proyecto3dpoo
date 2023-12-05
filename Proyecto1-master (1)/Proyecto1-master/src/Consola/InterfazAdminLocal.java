package Consola;

import javax.swing.*;
import javax.swing.border.LineBorder;


import Inventario.Inventario;
import SistemaAlquiler.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;


public class InterfazAdminLocal extends JFrame{
	
    Inventario inventario;
	VehiculoRentalSystem rentalSystem;
	private static final long serialVersionUID = 1L;
	Sede sede;
	
	public InterfazAdminLocal(Inventario inventario, Sede sede){
		
		this.inventario = inventario;
		this.sede = sede;
		rentalSystem = inventario.getAplicacion();
		if(validacionAdminLocal(this.sede.getNombre())) {
			
			setTitle("Ventana con Botones");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(600, 600); // Tamaño de la ventana
	        
	        JButton boton1 = new JButton("Gestionar información empleados");
	        JButton boton2 = new JButton("Gestionar información sede");
	        JButton boton3 = new JButton("Salir");
	        
	        Dimension dimension = new Dimension(320, 50);
	        boton1.setPreferredSize(dimension);
	        boton2.setPreferredSize(dimension);
	        boton3.setPreferredSize(dimension);
	        
	        Font fuenteBoton = new Font("Georgia", Font.BOLD, 16);
	        boton1.setFont(fuenteBoton);
	        boton2.setFont(fuenteBoton);
	        boton3.setFont(fuenteBoton);
	        
	        boton1.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		gestionarEmpleados();
	        		setVisible(false);
	        	}
	        });
	        
	        boton2.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		GestionarSede();
	        		setVisible(false);
	        	}
	        });
	        
	        boton3.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		System.exit(0);
	        	}
	        });
			
			
	        
	        JLabel etiqueta = new JLabel("Administrador Local");
	        JLabel nombreSede = new JLabel(this.sede.getNombre());
	    	Font fuentePersonalizada = new Font("Georgia", Font.BOLD, 20); // Cambiar a la fuente y tamaño deseados
	        etiqueta.setFont(fuentePersonalizada);
	        nombreSede.setFont(fuentePersonalizada);
	
	        // Crear un panel y establecer su diseño como BoxLayout
	        JPanel panel = new JPanel(new GridBagLayout());
	
	        // Configurar las restricciones del GridBagConstraints para centrar el JComboBox
	        GridBagConstraints gbc = new GridBagConstraints();
	        
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	        panel.add(etiqueta, gbc);
	        
	        
	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	        panel.add(nombreSede, gbc);
	        
	        
	        gbc.gridx = 1;
	        gbc.gridy = 2;
	        gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	        panel.add(boton1, gbc);
	        
	        gbc.gridx = 1;
	        gbc.gridy = 3;
	        gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre el JComboBox y el botón
	        panel.add(boton2, gbc);
	
	        // Configurar las restricciones para el botón
	        gbc.gridy = 4;
	        panel.add(boton3, gbc);
	        
	        //panel.add(new JLabel("Seleccione una opción", JLabel.CENTER));
	        // Configurar el diseño del marco y agregar el panel
	        setLayout(new BorderLayout());
	        add(panel, BorderLayout.CENTER);
	        
	        // Hacer visible la ventana
	        setVisible(true);
		}
	}
	
	private boolean validacionAdminLocal(String sede) {
		
		String ADMIN_USERNAME = "";
		String ADMIN_PASSWORD = "";
		
		if (sede.equals("Sucursal Central")) {
			ADMIN_USERNAME = "admin_central";
	        ADMIN_PASSWORD = "admin_central";
		}else if(sede.equals("Sucursal Norte")) {
			ADMIN_USERNAME = "admin_norte";
	        ADMIN_PASSWORD = "admin_norte";
		}else {
			ADMIN_USERNAME = "admin_sur";
	        ADMIN_PASSWORD = "admin_sur";
		}
		
		System.out.print(ADMIN_USERNAME);
		System.out.print(ADMIN_PASSWORD);
       
        // Autenticación del administrador
        while (true) {
            String adminUsername = JOptionPane.showInputDialog("Ingrese el nombre de usuario del administrador:");
            String adminPassword = JOptionPane.showInputDialog("Ingrese la contraseña del administrador:");

            if (adminUsername != null && adminPassword != null &&
                    adminUsername.equals(ADMIN_USERNAME) && adminPassword.equals(ADMIN_PASSWORD)) {
                // Si las credenciales son correctas, retorna true para mostrar el menú
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos. Acceso denegado.");
            }
        }
    }
	
	public void gestionarEmpleados() {
		
		final JFrame gestionarEmpleados = new JFrame();
		
		gestionarEmpleados.setTitle("Ventana con RadioButtons");
		gestionarEmpleados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gestionarEmpleados.setSize(600, 300);
	    
	    Font fuenteBoton = new Font("Georgia", Font.BOLD, 10);
	    
	    JPanel panelEmpleados = new JPanel();
	    panelEmpleados.setLayout(new BoxLayout(panelEmpleados, BoxLayout.Y_AXIS));
	    
	 // Crear un panel principal
	    JButton botonAceptar = new JButton("Aceptar");
	    JButton botonSalir = new JButton("Salir");
	    JButton botonRegistrar = new JButton("Nuevo empleado");
	    
	    Dimension dimension = new Dimension(150, 40);
	    botonAceptar.setPreferredSize(dimension);
	    botonAceptar.setFont(fuenteBoton);
	    botonSalir.setPreferredSize(dimension);
	    botonSalir.setFont(fuenteBoton);
	    botonRegistrar.setFont(fuenteBoton);
	    botonRegistrar.setPreferredSize(dimension);
	    
	    JLabel empleados = new JLabel("Empleados:");
	    Font fuentePersonalizada = new Font("Georgia", Font.BOLD, 20);
	    empleados.setFont(fuentePersonalizada);
	    
	    JLabel mensaje = new JLabel("<html>Seleccione un empleado para <br>modificar su información o eliminarlo</html>");
	    Font fuenteMensaje = new Font("Georgia", Font.PLAIN, 15);
	    mensaje.setFont(fuenteMensaje);
	
	    panelEmpleados.add(empleados, BorderLayout.WEST);
	    panelEmpleados.add(mensaje, BorderLayout.WEST);
	    gestionarEmpleados.add(panelEmpleados, BorderLayout.PAGE_START);
	    
	    
	    JPanel panelBotones = new JPanel();
	    
	    panelBotones.add(botonAceptar, BorderLayout.CENTER);
	    panelBotones.add(botonSalir, BorderLayout.CENTER);
	    panelBotones.add(botonRegistrar, BorderLayout.CENTER);
	
	    // Hacer visible la ventana
	    
	    gestionarEmpleados.add(panelBotones, BorderLayout.SOUTH);

	    List<String> opcionesList = new ArrayList<>();
	    
	    
	    
	    for(String empleado : sede.getEmpleados()) {
	    	opcionesList.add(empleado);
	    }
	    String[] opciones = opcionesList.toArray(new String[0]);
		final JComboBox<String> listaDesplegable = new JComboBox<>(opciones);
		//listaDesplegable.setPreferredSize(new Dimension(200, 50));
		
		gestionarEmpleados.add(listaDesplegable);
		gestionarEmpleados.setVisible(true);
	    
	    botonAceptar.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String nombreEmpleado = (String) listaDesplegable.getSelectedItem();
                for(Empleado empleado : rentalSystem.getEmpleados()) {
                	if(nombreEmpleado.equals(empleado.getNombre())) {
                		infoEmpleados(empleado, gestionarEmpleados);
                		gestionarEmpleados.setVisible(false);
                	}
                }	
        	}
        });
	    
	    botonSalir.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        		
        		
        	}
        });
	    
	    botonRegistrar.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		RegistrarEmpleado(gestionarEmpleados);
        		gestionarEmpleados.setVisible(false);        	}
        });
	    
	}

    public void GestionarSede() {
    	
    	final JFrame GestionSede = new JFrame();
    	GestionSede.setTitle("Ventana con Cuadrado y Texto");
    	GestionSede.setSize(600, 600);
    	GestionSede.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	    String horaInicio = formatter.format(sede.getInicioHorarioAtencion());
	    String horaFin = formatter.format(sede.getFinalHorarioAtencion());
	    
	    
	    String info = "<html>"+ "Nombre: " + sede.getNombre()+"<br>"+"Dirección: " + sede.getUbicacion()+"<br>"+"Horario de atencion: " + horaInicio + " - " + horaFin+"</html>";
	    
	    JLabel infoSede = new JLabel(info);
	    LineBorder border = new LineBorder(Color.GRAY, 2);
	    Font fuenteMensaje = new Font("Georgia", Font.PLAIN, 15);
	    infoSede.setBorder(border);
	    infoSede.setFont(fuenteMensaje);
	    
	    JLabel labelDireccion = new JLabel("Ingrese la nueva dirección");
	    labelDireccion.setFont(fuenteMensaje);
	    
	    final JTextField direccionNueva = new JTextField();
	    
	    final JTextField inicioHorarioNuevo = new JTextField();
	    
	    final JTextField finHorarioNuevo = new JTextField();
	    
	    JLabel labelInicioHorario = new JLabel("<html>Ingrese la nueva hora de<br>inicio del horario de atención</html>");
	    labelInicioHorario.setFont(fuenteMensaje);
	    
	    JLabel labelFinHorario = new JLabel("<html>Ingrese la nueva hora de<br>fin del horario de atención</html>");
	    labelFinHorario.setFont(fuenteMensaje);
	    
	    JLabel lugar = new JLabel("Información Sede");
	    Font fuenteTitulo = new Font("Georgia", Font.BOLD, 20);
	    lugar.setFont(fuenteTitulo);
	    
	    JLabel carros = new JLabel("Información Carros");
	    carros.setFont(fuenteTitulo);
	    
	    Dimension textFieldSize = new Dimension(100, 25); // Tamaño específico
	    direccionNueva.setPreferredSize(textFieldSize);
	    inicioHorarioNuevo.setPreferredSize(textFieldSize);
	    finHorarioNuevo.setPreferredSize(textFieldSize);
	    
	    JPanel panel = new JPanel(new GridBagLayout());
	
	    // Configurar las restricciones del GridBagConstraints para centrar el JComboBox
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(lugar, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.insets = new Insets(0, 0, 5, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(infoSede, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.insets = new Insets(0, 0, 5, 0); // Espacio entre el JComboBox y el botón
	    panel.add(carros, gbc);
	
	    
	    int y = 3;
	    for(Vehiculo carro: sede.getVehiculos()) {
	        
	    	String infoStr = carro.getVehiculoId() + ": " + carro.getCategoria() + " -- " + carro.getmarca();
	    	JLabel infoCarro = new JLabel(infoStr);
	        infoCarro.setBorder(border);
	        infoCarro.setFont(fuenteMensaje);
	        
	        gbc.gridx = 1;
	        gbc.gridy = y;
	        gbc.insets = new Insets(0, 0, 10, 0);
	        panel.add(infoCarro, gbc);
	        
	        y += 1;
	    }
	
	    
	    JPanel panel2 = new JPanel(new GridBagLayout());
	
	    // Configurar las restricciones del GridBagConstraints para centrar el JComboBox
	    GridBagConstraints gridbc = new GridBagConstraints();
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 0;
	    gridbc.insets = new Insets(10, 0, 10, 0);
	    panel2.add(labelDireccion, gridbc);
	    
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 2;
	    gridbc.insets = new Insets(0, 0, 10, 0);
	    panel2.add(direccionNueva, gridbc);
	    
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 3;
	    gridbc.insets = new Insets(10, 0, 10, 0);
	    panel2.add(labelInicioHorario, gridbc);
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 4;
	    gridbc.insets = new Insets(0, 0, 0, 0);
	    panel2.add(inicioHorarioNuevo, gridbc);
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 5;
	    gridbc.insets = new Insets(10, 0, 10, 0);
	    panel2.add(labelFinHorario, gridbc);
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 6;
	    gridbc.insets = new Insets(0, 0, 10, 0);
	    panel2.add(finHorarioNuevo, gridbc);
	    
	    
	    
	    //panel.add(new JLabel("Seleccione una opción", JLabel.CENTER));
	    // Configurar el diseño del marco y agregar el panel
	    setLayout(new BorderLayout());
	    
	    Font fuenteBoton = new Font("Georgia", Font.BOLD, 12);
	    
	    JButton confirmarCambio = new JButton("Confirmar Cambio");
	    confirmarCambio.setFont(fuenteBoton);
	    confirmarCambio.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String direccion = direccionNueva.getText();
    	        String inicioHorario = inicioHorarioNuevo.getText();
    	        String finHorario = finHorarioNuevo.getText();
    	        
    	        if(!direccion.isEmpty()) {
    	        	sede.setUbicacion(direccion);
    			    rentalSystem.modificarDireccionSede(sede, direccion);
    	        }
    	        String mensajeError = "El formato de fecha es incorrecto" + "\n" + "debe ser (HH:mm:ss)";
    		    if(!inicioHorario.isEmpty()) {
    		    	try {
    		    		LocalTime horaInicial = LocalTime.parse(inicioHorario);
    		    		sede.setInicioHorarioAtencion(horaInicial);
    				    rentalSystem.modificarHorarioSede(sede, horaInicial, "incial");
    		    	}catch(DateTimeParseException i){
    		    		
    		    		JOptionPane.showMessageDialog(null, mensajeError , "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
    		    	}
    		    }
    		    
    		    if(!finHorario.isEmpty()) {
    		    	
    		    	try {
    		    	LocalTime horaFinal = LocalTime.parse(finHorario);
    			    sede.setFinalHorarioAtencion(horaFinal);
    			    rentalSystem.modificarHorarioSede(sede, horaFinal, "final");
    		    	} catch(DateTimeParseException i) {
    		    		JOptionPane.showMessageDialog(null, mensajeError , "Formato incorrecto", JOptionPane.ERROR_MESSAGE);
    		    	}
    		    }
        	}
        });
	    
	    
	    
	    JButton salir = new JButton("Salir");
	    salir.setFont(fuenteBoton);
	    salir.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
	    
	    JButton disponibilidadMensual = new JButton("Disponibilidad mensual de carros");
	    disponibilidadMensual.setFont(fuenteBoton);
	    disponibilidadMensual.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		DisponibilidadCarros();
        	}
        });
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 7;
	    gridbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel2.add(confirmarCambio, gridbc);
	    
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 8;
	    gridbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel2.add(disponibilidadMensual, gridbc);
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 9;
	    gridbc.insets = new Insets(0, 0, 10, 0); // Espacio entre el JComboBox y el botón
	    panel2.add(salir, gridbc);
	    
	    
	    
	    GestionSede.add(panel, BorderLayout.WEST);
	    GestionSede.add(panel2, BorderLayout.CENTER);
	    GestionSede.setVisible(true);
	}
	
	public void infoEmpleados(Empleado empleado, JFrame gestionarEmpleados) {
		final JFrame ventanaAnterior = gestionarEmpleados;
		
		final Empleado empleadoFinal = empleado;
		
		final JFrame ventanaInfoEmpleado = new JFrame();
		ventanaInfoEmpleado.setTitle("Ventana con Cuadrado y Texto");
		ventanaInfoEmpleado.setSize(600, 600);
		ventanaInfoEmpleado.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    JLabel labelNombre = new JLabel("Nombre: " + empleado.getNombre());
	    JLabel labelLogin = new JLabel("Login: " + empleado.getLogin());
	    JLabel labelContraseña = new JLabel("Contraseña: " + empleado.getContrasena());
	    LineBorder border = new LineBorder(Color.GRAY, 2);
	    Font fuenteMensaje = new Font("Georgia", Font.PLAIN, 15);
	    labelNombre.setBorder(border);
	    labelLogin.setBorder(border);
	    labelContraseña.setBorder(border);
	    
	    labelNombre.setFont(fuenteMensaje);
	    labelLogin.setFont(fuenteMensaje);
	    labelContraseña.setFont(fuenteMensaje);
	    
	    JLabel campoUsuario = new JLabel("<html>Ingrese el nuevo usuario <br>si desea cambiar el actual</html>");
	    campoUsuario.setFont(fuenteMensaje);
	    
	    final JTextField usuarioNuevo = new JTextField();
	    
	    final JTextField contraseñaNueva = new JTextField();
	    
	    JLabel campoContraseña = new JLabel("<html>Ingrese la nueva contraseña <br>si desea cambiar la actual</html>");
	    campoContraseña.setFont(fuenteMensaje);
	    
	    JLabel info = new JLabel("Información empleado");
	    Font fuenteTitulo = new Font("Georgia", Font.BOLD, 20);
	    info.setFont(fuenteTitulo);
	    
	    Dimension textFieldSize = new Dimension(100, 25); // Tamaño específico
	    usuarioNuevo.setPreferredSize(textFieldSize);
	    contraseñaNueva.setPreferredSize(textFieldSize);
	    
	    
	    JPanel panel = new JPanel(new GridBagLayout());
	
	    // Configurar las restricciones del GridBagConstraints para centrar el JComboBox
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(info, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.insets = new Insets(0, 0, 5, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(labelNombre, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.insets = new Insets(0, 0, 5, 0); // Espacio entre el JComboBox y el botón
	    panel.add(labelLogin, gbc);
	
	    //Configurar las restricciones para el botón
	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    gbc.insets = new Insets(0, 0, 10, 0);
	    panel.add(labelContraseña, gbc);
	    
	    JPanel panel2 = new JPanel(new GridBagLayout());
	
	    // Configurar las restricciones del GridBagConstraints para centrar el JComboBox
	    GridBagConstraints gridbc = new GridBagConstraints();
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 0;
	    gridbc.insets = new Insets(10, 0, 10, 0);
	    panel2.add(campoUsuario, gridbc);
	    
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 2;
	    gridbc.insets = new Insets(0, 0, 10, 0);
	    panel2.add(usuarioNuevo, gridbc);
	    
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 3;
	    gridbc.insets = new Insets(10, 0, 10, 0);
	    panel2.add(campoContraseña, gridbc);
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 4;
	    gridbc.insets = new Insets(0, 0, 0, 0);
	    panel2.add(contraseñaNueva, gridbc);
	    
	    //panel.add(new JLabel("Seleccione una opción", JLabel.CENTER));
	    // Configurar el diseño del marco y agregar el panel
	    setLayout(new BorderLayout());
	    
	    Font fuenteBoton = new Font("Georgia", Font.BOLD, 12);
	    
	    JButton confirmarCambio = new JButton("Confirmar Cambio");
	    confirmarCambio.setFont(fuenteBoton);
	    confirmarCambio.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String usuario = usuarioNuevo.getText();
    	        String contraseña = contraseñaNueva.getText();
    	        if(!usuario.isEmpty()) {
    	        	
    	        	rentalSystem.modificarLoginEmpleado(empleadoFinal, usuario);
    	        	empleadoFinal.setLogin(usuario);
    	        }
    	        if(!contraseña.isEmpty()) {
    	        	System.out.print(contraseña);
    	        	empleadoFinal.setContrasena(contraseña);
    	        	rentalSystem.modificarContraseñaEmpleado(empleadoFinal, contraseña);
    	        }
        	}
        });
	    //confirmarCambio.setPreferredSize(dimension);
	    
	    JButton eliminarEmpleado = new JButton("Eliminar empleado");
	    eliminarEmpleado.setFont(fuenteBoton);
	    eliminarEmpleado.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
    			rentalSystem.eliminarEmpleado(empleadoFinal);
    			rentalSystem.removeEmpleado(empleadoFinal);
    			ventanaInfoEmpleado.dispose();
        		ventanaAnterior.setVisible(true);
        	}
        });
	    //eliminarEmpleado.setPreferredSize(dimension);
	    
	    JButton salir = new JButton("Salir");
	    salir.setFont(fuenteBoton);
	    salir.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ventanaInfoEmpleado.dispose();
        		ventanaAnterior.setVisible(true);
        	}
        });
	    //salir.setPreferredSize(dimension);
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 30;
	    gridbc.insets = new Insets(10, 0, 0, 0); // Espacio entre la etiqueta y el JComboBox
	    panel2.add(confirmarCambio, gridbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 30;
	    gbc.insets = new Insets(10, 0, 0, 10); // Espacio entre la etiqueta y el JComboBox
	    panel.add(eliminarEmpleado, gbc);
	    
	    gridbc.gridx = 1;
	    gridbc.gridy = 50;
	    gridbc.insets = new Insets(10, 0, 0, 0); // Espacio entre el JComboBox y el botón
	    panel2.add(salir, gridbc);
	    
	    
	    
	    ventanaInfoEmpleado.add(panel, BorderLayout.WEST);
	    ventanaInfoEmpleado.add(panel2, BorderLayout.CENTER);
	    ventanaInfoEmpleado.setVisible(true);
	}
	
	public void RegistrarEmpleado(JFrame gestionarEmpleado) {
		
		final JFrame ventanaAnterior = gestionarEmpleado;
		final JFrame RegistrarEmpleado = new JFrame();
		RegistrarEmpleado.setTitle("Ventana de Login");
		RegistrarEmpleado.setSize(600, 600);
		RegistrarEmpleado.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RegistrarEmpleado.setLocationRelativeTo(null);
	    
	    
	    // Creación de componentes
	    Font fuenteMensaje = new Font("Georgia", Font.PLAIN, 15);
	    JLabel labelUsuario = new JLabel("Usuario:");
	    JLabel labelContraseña = new JLabel("Contraseña:");
	    JLabel labelNombre = new JLabel("Nombre:");
	    labelUsuario.setFont(fuenteMensaje);
	    labelContraseña.setFont(fuenteMensaje);
	    labelNombre.setFont(fuenteMensaje);
	    
	    Dimension textFieldSize = new Dimension(100, 25);
	    final JTextField campoUsuario = new JTextField();
	    final JPasswordField campoContraseña = new JPasswordField();
	    final JTextField campoNombre = new JTextField();
	    campoUsuario.setPreferredSize(textFieldSize);
	    campoContraseña.setPreferredSize(textFieldSize);
	    campoNombre.setPreferredSize(textFieldSize);
	    
	    JButton botonLogin = new JButton("Registrar");
	    JButton botonSalir = new JButton("Salir");
	    Font fuenteBoton = new Font("Georgia", Font.BOLD, 12);
	    Dimension dimension = new Dimension(150, 30);
	    
	    botonLogin.setFont(fuenteBoton);
	    botonLogin.setPreferredSize(dimension);
	    
	   
	    
	    botonSalir.setFont(fuenteBoton);
	    botonSalir.setPreferredSize(dimension);
	    
	    botonLogin.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String usuario = campoUsuario.getText();
    	        String contraseña = new String(campoContraseña.getPassword());
    	        String nombre = campoNombre.getText();
    	        
    	        if(usuario.isEmpty()|| contraseña.isEmpty() || nombre.isEmpty()) {
    	        	JOptionPane.showMessageDialog(null, "el cuadro de texto está vacio, esciba algo" , "Cuadro de texto", JOptionPane.ERROR_MESSAGE);
    	         
    	        }else {
    	        	Empleado nuevoEmpleado = new Empleado(sede.getNombre(), usuario, contraseña, nombre);
    	    		sede.agregarEmpleado(nuevoEmpleado);
    	    		rentalSystem.getEmpleados().add(nuevoEmpleado);
    	    		rentalSystem.escribirEmpleado(nuevoEmpleado);
    	    		JOptionPane.showMessageDialog(null, "Empleado Registrado");
    	    		RegistrarEmpleado.dispose();
            		ventanaAnterior.setVisible(true);
    	        }
        	}
        });
	    botonSalir.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		RegistrarEmpleado.dispose();
        		ventanaAnterior.setVisible(true);
        	}
        });
	
	
	    // Configuración del layout
	    JPanel panel = new JPanel(new GridBagLayout());
	
	    // Configurar las restricciones del GridBagConstraints para centrar el JComboBox
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(labelNombre, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(campoNombre, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(labelUsuario, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(campoUsuario, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 4;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(labelContraseña, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 5;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(campoContraseña, gbc);
	    
	    
	    
	    gbc.gridx = 1;
	    gbc.gridy = 6;
	    gbc.insets = new Insets(0, 0, 10, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(botonLogin, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 7;
	    gbc.insets = new Insets(0, 0, 0, 0); // Espacio entre la etiqueta y el JComboBox
	    panel.add(botonSalir, gbc);
	    
	
	
	    RegistrarEmpleado.add(panel, BorderLayout.CENTER);
	    RegistrarEmpleado.setVisible(true);
	}

	public void DisponibilidadCarros() {
		JFrame frame = new JFrame("Disponibilidad de Carros");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int[] disponibilidadMensual = rentalSystem.calcularDisponibilidadMensual(sede);
		
		
		String[] labels = {
	            "Jan", "Feb", "March", "Aprl", "May", "June",
	            "July", "Aug", "Sept", "Oct", "Nov", "Dic"
	        };
		
		boolean todosSon30 = false;

		for (int i = 0; i < disponibilidadMensual.length; i++) {
		    if (disponibilidadMensual[i] == (30*sede.getVehiculos().size())) {
		        todosSon30 = true;
		        break;  // No es necesario seguir verificando si ya encontramos un elemento diferente de 10
		    }
		}
		DisponibilidadCarros panelDisponibilidad = new DisponibilidadCarros(disponibilidadMensual, labels);
		frame.add(panelDisponibilidad);
        frame.setVisible(true);
		frame.setLocationRelativeTo( null );
		if (todosSon30) {
			JOptionPane.showMessageDialog(null, "No han habido reservas, todos los carros han tenido disponibilidad completa");
			panelDisponibilidad.setVisible(false);
		} 
		
		
		
	}
}
