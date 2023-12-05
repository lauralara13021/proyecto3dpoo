package Consola;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Inventario.Inventario;
import SistemaAlquiler.AgendaCarro;
import SistemaAlquiler.Categoria;
import SistemaAlquiler.Cliente;
import SistemaAlquiler.Reserva;
import SistemaAlquiler.Sede;
import SistemaAlquiler.Vehiculo;
import SistemaAlquiler.VehiculoRentalSystem;

public class InterfazCliente extends JFrame{
	JTextField usuario = new JTextField();
	JTextField contrasenia = new JTextField();
	private static final long serialVersionUID = 1L;
	Inventario inventario;
	VehiculoRentalSystem rentalSystem;
    
	public InterfazCliente(Inventario inventario) {
		this.inventario = inventario;
		rentalSystem = inventario.getAplicacion();
		setTitle("Iniciar Sesion ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel myPanel = new JPanel(new GridLayout(3, 2));

        getContentPane().add(myPanel);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        JTextField usuario = new JTextField();
        JTextField contrasenia = new JTextField();
        myPanel.add(new JLabel("Ingrese su nombre de usuario"));
        myPanel.add(usuario);
        myPanel.add(new JLabel("Ingrese su contraseña"));
        myPanel.add(contrasenia);
        JButton registrarse = new JButton("¿No tienes cuenta? Registrate aquí");
        myPanel.add(registrarse);
        
        registrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                setTitle("Registrarse");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                JPanel myPanel = new JPanel(new GridLayout(5, 2));

                getContentPane().add(myPanel);
                myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
                JTextField usuario = new JTextField();
                JTextField contrasenia = new JTextField();
                myPanel.add(new JLabel("Ingrese su nombre de usuario"));
                myPanel.add(usuario);
                myPanel.add(new JLabel("Ingrese su contraseña"));
                myPanel.add(contrasenia);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Ingrese los datos de registro", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String usuarioTexto = usuario.getText();
                    String contraseniaTexto = contrasenia.getText();
                    Cliente newCliente = rentalSystem.RegistarCliente(usuarioTexto, contraseniaTexto);
                    dispose();
                }
            }
        });
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Ingrese los datos de inicio de sesion", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String usuarioTexto = usuario.getText();
            String contraseniaTexto = contrasenia.getText();
            Cliente newCliente = validacionCliente(usuarioTexto, contraseniaTexto);
            mostrarMenuCliente(newCliente);
        }
        
	}
        public void mostrarMenuCliente(Cliente newCliente) {
            // Crear una variable final efectiva
            final Cliente clienteFinal = newCliente;

            dispose();
            setTitle("Vehiculo Rental System");
            setSize(300, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            getContentPane().removeAll();

            JPanel menuCliente = new JPanel();
            menuCliente.setLayout(new BoxLayout(menuCliente, BoxLayout.Y_AXIS));

            JButton botonAlquiler = new JButton("Alquilar un Vehiculo");
            JButton botonModEntrega = new JButton("Modificar información entrega en reserva");
            JButton botonSalir = new JButton("Salir");

            menuCliente.add(botonAlquiler);
            menuCliente.add(botonModEntrega);
            menuCliente.add(botonSalir);

            botonAlquiler.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    opcion1Cliente(clienteFinal);
                }
            });
            botonModEntrega.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    opcion2Cliente(clienteFinal);
                }
            });
            botonSalir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            getContentPane().add(menuCliente); // Agrega el nuevo panel al contenedor existente
            setVisible(true);
        }
        public void opcion1Cliente( Cliente newCliente) {
			
    		JPanel myPanel = new JPanel(new GridLayout(8, 2));
    			
    		getContentPane().add(myPanel);
            String[] categoriasOpciones = {"SUV", "pequeño", "van", "lujoso"};	
            JComboBox<String> categoriasComboBox = new JComboBox<>(categoriasOpciones);
    		myPanel.add(new JLabel("Seleccione la categoría que desea alquilar "));
    		myPanel.add(categoriasComboBox);
    			
    		JTextField fechaInicio = new JTextField();
    		myPanel.add(new JLabel("Ingrese la fecha de inicio de renta (dd/MM/yyyy): "));
    		myPanel.add(fechaInicio);			
    		JTextField horaInicio = new JTextField();
    		myPanel.add(new JLabel("Ingrese la hora de inicio de renta (HH/mm): "));
    		myPanel.add(horaInicio);
    		JTextField fechaFinal = new JTextField();
    		myPanel.add(new JLabel("Ingrese la fecha final de renta (dd/MM/yyyy): "));
    		myPanel.add(fechaFinal);
    		JTextField horaFinal = new JTextField();
    		myPanel.add(new JLabel("Ingrese la hora de fin de renta (HH/mm): "));
    		myPanel.add(horaFinal);
    		String[] sedesOpciones = {"Sucursal Central", "Sucursal Norte", "Sucursal Sur"};
    		JComboBox<String> sedesRecoComboBox = new JComboBox<>(sedesOpciones);
    	    JComboBox<String> sedesDevoComboBox = new JComboBox<>(sedesOpciones);
    	    myPanel.add(new JLabel("Seleccione la sede en la que va a recoger el carro:"));
    	    myPanel.add(sedesRecoComboBox);
    	    myPanel.add(new JLabel("Seleccione la sede en la que va a devolver el carro:"));
    	    myPanel.add(sedesDevoComboBox);
    	        
    	    int result = JOptionPane.showConfirmDialog(null, myPanel,
    	                "Seleccione el seguro", JOptionPane.OK_CANCEL_OPTION);
    	        
    	    if (result == JOptionPane.OK_OPTION) {
    	    	
    		    try {
    		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
    		        Date startDate = dateFormat.parse(fechaInicio.getText() + "/" + horaInicio.getText());
    		        Date endDate = dateFormat.parse(fechaFinal.getText() + "/" + horaFinal.getText());
    		        
    		        List<Vehiculo> availableCarsIncategoria = new ArrayList<>();
    		        for (Sede sede : rentalSystem.getSedes().values()) {
    		        	if (sede.getNombre().equals((String)sedesRecoComboBox.getSelectedItem()));{
    		        		for (Vehiculo car : sede.getVehiculos()) {
    		        			String categoria = car.getCategoria();
    		        			if (car.ValidarDisponibilidad(startDate, endDate) && categoria.equals((String)categoriasComboBox.getSelectedItem())) {
    		    	            		availableCarsIncategoria.add(car);
    		    	            }
    		    	                
    		    	        }
    		    		}
    		    	}
    	            if (!availableCarsIncategoria.isEmpty()) {
    	                // Seleccionar un carro aleatorio de la categoría
    	                Random random = new Random();
    	                Vehiculo selectedCar = availableCarsIncategoria.get(random.nextInt(availableCarsIncategoria.size()));
    	                
    	                // Calcular la diferencia de días entre las fechas
    	                int diasRenta = rentalSystem.cantidadDiasRenta(startDate, endDate);

    	                // Determinar si estamos en temporada alta (por ejemplo, de julio a diciembre)
    	                int tarifa = rentalSystem.DeterminarTarifa(startDate, (String)categoriasComboBox.getSelectedItem());
    	                

    	                // Resto del código para alquilar el coche
    	                // Utiliza el método calculatePrice con diasRenta y temporadaAlta
    	                // para calcular el precio y mostrarlo al usuario.
    	                boolean reservaVigente = false;
    	                if(rentalSystem.getReservas().size() != 0) {
    	                	for(Reserva res: rentalSystem.getReservas()) {
    	                    	if(res.getCliente().equals(newCliente.getName())&& res.getEstado().equals("vigente")) {
    	                    		reservaVigente = true;
    	                    	}
    	                	}
    	                    if(!reservaVigente) {
    	                    	generarReserva((String)categoriasComboBox.getSelectedItem(), startDate, endDate, newCliente, selectedCar, 
    	                    			(String)sedesRecoComboBox.getSelectedItem(), (String)sedesDevoComboBox.getSelectedItem(), diasRenta, tarifa);
    	                    }else {
    	                    	JOptionPane.showMessageDialog(null, "Ya tiene una reserva vigente, no puede generar otra reserva");
    	                    	dispose();
    	                    }
    	                }else {
    	                	generarReserva((String)categoriasComboBox.getSelectedItem(), startDate, endDate, newCliente, selectedCar, 
    	                			(String)sedesRecoComboBox.getSelectedItem(), (String)sedesDevoComboBox.getSelectedItem(), diasRenta, tarifa);
    	                }
    	                
    	                
    	                
    	                
    	            } else {
    	            	JOptionPane.showMessageDialog(null, "No hay carros disponibles en la categoría seleccionada, disculpas.");
    	            	dispose();
    	            }
    	            
    	            } catch (ParseException e) {
    	            	JOptionPane.showMessageDialog(null, "Formato de la Fecha invalido");
    	            	dispose();
    	            }
    	        }
    	        }
    	 
    	 private void generarReserva(String categoriaInput, Date inicioDate, Date finDate, Cliente newCliente,
    	            Vehiculo selectedcarro, String NombreSedeRecoger, String NombreSedeDevolver, int diasRenta, int tarifa) {
    		 	final Date startDate = inicioDate;
    		 	final Date endDate = finDate;
    		 	final Vehiculo selectedCar = selectedcarro;
    	        Reserva reserva = new Reserva(categoriaInput, startDate, endDate, newCliente.getName(),
    	                selectedCar.getVehiculoId(), NombreSedeRecoger, NombreSedeDevolver, "vigente");
    	        Categoria categoria = rentalSystem.getCategorias().get(categoriaInput);
    	        final double[] totalPrice = {reserva.getPrecio(diasRenta, tarifa, categoria.getvalorSedeDiferente())};

    	        // Crear variables finales efectivas
    	        final Reserva reservaFinal = reserva;
    	        final int diasRentaFinal = diasRenta;

    	        dispose();
    	        setTitle("Aquí informacion Alquiler");
    	        setSize(500, 500);
    	        setDefaultCloseOperation(EXIT_ON_CLOSE);
    	        setLocationRelativeTo(null);

    	        getContentPane().removeAll();
    	        JPanel ElPanel = new JPanel(new GridLayout(8, 2));
    	        getContentPane().add(ElPanel);
    	        ElPanel.add(new JLabel("Nombre del Cliente: " + newCliente.getName()));
    	        ElPanel.add(new JLabel("Vehículo: " + selectedCar.getmarca() + " " + selectedCar.getmodelo()));
    	        ElPanel.add(new JLabel("Dias del Alquiler: " + diasRenta));
    	        ElPanel.add(new JLabel("Precio total: " + totalPrice[0]));

    	        JButton botonConfirmar = new JButton("Confirmar el alquiler");
    	        JButton botonCancelar = new JButton("Cancelar alquiler");
    	        ElPanel.add(botonConfirmar);
    	        ElPanel.add(botonCancelar);

    	        // Mostrar la ventana principal después de configurar completamente la interfaz
    	        setVisible(true);

    	        botonConfirmar.addActionListener(new ActionListener() {
    	            @Override
    	            public void actionPerformed(ActionEvent e) {
    	                // Mostrar cuadro de diálogo con opciones de pago
    	                String[] opcionesPago = {"Tarjeta de Crédito", "Transferencia Bancaria", "Efectivo"};
    	                String opcionSeleccionada = (String) JOptionPane.showInputDialog(null,
    	                        "Seleccione una opción de pago:",
    	                        "Opciones de Pago",
    	                        JOptionPane.QUESTION_MESSAGE,
    	                        null,
    	                        opcionesPago,
    	                        opcionesPago[0]);

    	                if (opcionSeleccionada != null) {
    	                    // Usuario seleccionó una opción, ahora puedes continuar con el resto de las acciones
    	                    totalPrice[0] = rentalSystem.seleccionarSeguros(reservaFinal, totalPrice[0], diasRentaFinal);
    	                    reservaFinal.setPrecio(totalPrice[0]);
    	                    rentalSystem.addReserva(reservaFinal);
    	                    rentalSystem.escribirReserva(reservaFinal);
    	                    AgendaCarro indisponibilidad = new AgendaCarro(startDate, endDate, "Reservado");
    	                    rentalSystem.addAgendasCarros(selectedCar.getVehiculoId(), indisponibilidad);
    	                    rentalSystem.escribirAgendasCarros(selectedCar.getVehiculoId(), indisponibilidad);
    	                    JOptionPane.showMessageDialog(null, "Alquiler realizado exitosamente");
    	                    // Otras acciones...
    	                } else {
    	                    // Usuario cerró el cuadro de diálogo o no seleccionó ninguna opción
    	                    JOptionPane.showMessageDialog(null, "Operación cancelada");
    	                }
    	            }
    	        });

    	        botonCancelar.addActionListener(new ActionListener() {
    	            @Override
    	            public void actionPerformed(ActionEvent e) {
    	                JOptionPane.showMessageDialog(null, "Alquiler cancelado exitosamente");
    	                System.exit(0);
    	            }
    	        });
    	    }
        
        /**
         * Opción 2 para que un cliente modifique una reserva existente.
         *<b>Pre:</b> Scanner y newCliente no deben ser nulos.
         *<b>Post:</b> El cliente puede modificar una reserva existente si la reserva pertenece a ese cliente.
         * @param scanner     Objeto Scanner para la entrada del usuario.
         * @param newCliente  Objeto Cliente representando al cliente actual.
         * @throws ParseException si hay un error en el formato de la fecha.
         */
    	 public void opcion2Cliente(Cliente newCliente) {
    			setTitle("Modificar Reserva");
    	        setSize(300, 300);
    	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	       
    	        getContentPane().removeAll();

    	        JPanel menuPanel = new JPanel();
    	        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
    			JPanel myPanel = new JPanel(new GridLayout(8, 2));
    			setTitle("Modificar Reserva");
    	        JButton botonCambiarFecha = new JButton("Cambiar fecha en la que se devuelve el carro");
    	        JButton botonCambiarLugar = new JButton("Cambiar lugar de devuelta del carro");
    			myPanel.add(botonCambiarFecha);
    			myPanel.add(botonCambiarLugar);
    			for(Reserva reservaa: rentalSystem.getReservas()) {
    				if (reservaa.getCliente().equals(newCliente.getName())&& reservaa.getEstado().equals("vigente")) {
    					final Reserva reservaFinal = reservaa;
    					botonCambiarFecha.addActionListener(new ActionListener() {
    			            @Override
    			            public void actionPerformed(ActionEvent e) {
    			            	dispose();
    			        		setTitle("Alquilar Vehiculo ");
    			        		setSize(500, 500);
    			        		setDefaultCloseOperation(EXIT_ON_CLOSE);
    			        		setLocationRelativeTo(null);
    			        		
    			        		getContentPane().removeAll();
    			        		
    			        		JPanel myPanel = new JPanel(new GridLayout(8, 2));
    			        		
    			        		getContentPane().add(myPanel);
    			                myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
    			            	JTextField fechaDevolverStr = new JTextField();
    			            	myPanel.add(new JLabel("Escribe la nueva fecha (dd/MM/yyyy/HH/mm): "));
    			            	myPanel.add(fechaDevolverStr);
    			            	
    			            	 int result = JOptionPane.showConfirmDialog(null, myPanel,
    				                        "Modifique la Sede", JOptionPane.OK_CANCEL_OPTION);

    				                if (result == JOptionPane.OK_OPTION) {
    				                	try {
    					                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
    					                    Date fechaDevolver = dateFormat.parse(fechaDevolverStr.getText());
    					                    for(Vehiculo carro : rentalSystem.getVehiculos()) {
    					                    	if(carro.getVehiculoId().equals(reservaFinal.getIdCarro())) {
    					                    		for(AgendaCarro agenda : carro.getAgendaVehiculo()) {
    					                    			if(reservaFinal.getFechaRetorno().equals(agenda.getFechaFinal())) {
    					                    				rentalSystem.modificarAgendasCarros(agenda, fechaDevolver, "Final");
    					                    				agenda.setFechaFinal(fechaDevolver);
    					                    				rentalSystem.modificarFechaReserva(reservaFinal, fechaDevolver, "retorno");
    					                    				reservaFinal.setFechaRetorno(fechaDevolver);
    					                    			}
    					                    		}
    					                    	}
    					                    }
    					                    double precio = reservaFinal.getPrecio(rentalSystem.cantidadDiasRenta(reservaFinal.getFechaEntrega(), reservaFinal.getFechaRetorno()), rentalSystem.DeterminarTarifa(reservaFinal.getFechaEntrega(),  reservaFinal.getCategoria()), rentalSystem.getCategorias().get(reservaFinal.getCategoria()).getvalorSedeDiferente());
    					                    double precioTotal = reservaFinal.getPrecioConSeguros(precio, rentalSystem.cantidadDiasRenta(reservaFinal.getFechaEntrega(), reservaFinal.getFechaRetorno()));
    					                    reservaFinal.setPrecio(precioTotal);
    					                    rentalSystem.modificarPrecioReserva(reservaFinal, precioTotal, reservaFinal.getCliente());
    					                    JOptionPane.showMessageDialog(null, "Fecha actualizada con éxito");
    					                    
    					            	}catch(ParseException e1) {
    					            		JOptionPane.showMessageDialog(null, "Formato de Fecha invalido");
    					            	}	
    				                }
    			            	
    			            }
    			        });
    					botonCambiarLugar.addActionListener(new ActionListener() {
    			            @Override
    			            public void actionPerformed(ActionEvent e) {
    			            	dispose();
    			        		setTitle("Alquilar Vehiculo ");
    			        		setSize(500, 500);
    			        		setDefaultCloseOperation(EXIT_ON_CLOSE);
    			        		setLocationRelativeTo(null);
    			        		
    			        		getContentPane().removeAll();
    			        		
    			        		JPanel myPanel = new JPanel(new GridLayout(8, 2));
    			        		
    			        		getContentPane().add(myPanel);
    			                myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
    			            	String[] sedesOpciones = {"Sucursal Central", "Sucursal Norte", "Sucursal Sur"};
    			                JComboBox<String> sedesComboBox = new JComboBox<>(sedesOpciones);
    			                myPanel.add(new JLabel("Seleccione la sede en la que va a recoger el carro:"));
    			                myPanel.add(sedesComboBox);
    			                
    			                int result = JOptionPane.showConfirmDialog(null, myPanel,
    			                        "Modifique la Sede", JOptionPane.OK_CANCEL_OPTION);

    			                if (result == JOptionPane.OK_OPTION) {
    			                	rentalSystem.modificarSedeReserva(reservaFinal, (String) sedesComboBox.getSelectedItem(), "devuelta", reservaFinal.getCliente());
    			                	reservaFinal.setIdSedeDevolver((String) sedesComboBox.getSelectedItem());
    				                double precio = reservaFinal.getPrecio(rentalSystem.cantidadDiasRenta(reservaFinal.getFechaEntrega(), reservaFinal.getFechaRetorno()), rentalSystem.DeterminarTarifa(reservaFinal.getFechaEntrega(),  reservaFinal.getCategoria()), rentalSystem.getCategorias().get(reservaFinal.getCategoria()).getvalorSedeDiferente());
    			                    double precioTotal = reservaFinal.getPrecioConSeguros(precio, rentalSystem.cantidadDiasRenta(reservaFinal.getFechaEntrega(), reservaFinal.getFechaRetorno()));
    			                    rentalSystem.modificarPrecioReserva(reservaFinal, precioTotal, reservaFinal.getCliente());
    			                    reservaFinal.setPrecio(precioTotal);
    			                    JOptionPane.showMessageDialog(null, "Sede actualizada con éxito");

    			                }

    			            }
    			        });
    		                
    		            }
    				}
    			add(myPanel);

    	        // Validar y refrescar
    	        revalidate();
    	        repaint();
    	        
    			setLocationRelativeTo(null);
    			setVisible(true);
    			}
    	 public  Cliente validacionCliente(String clienteUsername, String clientePassword) {
    			// Menú para el cliente
    			Cliente newCliente = null;
    	        boolean clienteAutenticado = false;
    	        
    	        while (!clienteAutenticado) {
    	            // Verificar si el usuario ya existe en el mapa
    	            if (rentalSystem.getContraseñasUsuarios().containsKey(clienteUsername)) {
    	                // Verificar si la contraseña coincide
    	                if (rentalSystem.getContraseñasUsuarios().get(clienteUsername).equals(clientePassword)) {
    	                	for (Cliente cliente : rentalSystem.getClientes().values()) {
    	                		if (cliente.getLogin().equals(clienteUsername)) {
    	                			newCliente = cliente;
    	                		}
    	                	}
    	                    clienteAutenticado = true;
    	                } else {
    	                	JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
    	                }
    	            } else {
    	                // El usuario no existe en el mapa, permitir registro
    	            	dispose();
    	        		setTitle("Registrarse ");
    	        		setDefaultCloseOperation(EXIT_ON_CLOSE);
    	        		setLocationRelativeTo(null);
    	        		
    	        		JPanel myPanel = new JPanel(new GridLayout(3, 2));
    	        		
    	        		getContentPane().add(myPanel);
    	                myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
    	                JTextField usuario = new JTextField();
    	            	JTextField contrasenia = new JTextField();
    	        		 myPanel.add(new JLabel("Ingrese su nombre de usuario"));
    	        		 myPanel.add(usuario);
    	        		 myPanel.add(new JLabel("Ingrese su contraseña"));
    	        		 myPanel.add(contrasenia);
    	        		 int result = JOptionPane.showConfirmDialog(null, myPanel,
    	        	                "Ingrese los datos de ", JOptionPane.OK_CANCEL_OPTION);
    	        		 if (result == JOptionPane.OK_OPTION) {
    	     	            String usuarioTexto = usuario.getText();
    	     	            String contraseniaTexto = contrasenia.getText();
    	     	            newCliente = rentalSystem.RegistarCliente(usuarioTexto,contraseniaTexto);
    	     		 }  
    	        		 setVisible(true);
    	            }
    	        }
    	        return newCliente;
    		}
}