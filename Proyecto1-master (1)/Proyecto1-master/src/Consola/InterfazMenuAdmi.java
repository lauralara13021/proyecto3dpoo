package Consola;
import javax.imageio.ImageIO;
import javax.swing.*;

import SistemaAlquiler.AgendaCarro;
import SistemaAlquiler.Categoria;
import SistemaAlquiler.Reserva;
import SistemaAlquiler.Seguro;
import SistemaAlquiler.Vehiculo;
import SistemaAlquiler.VehiculoRentalSystem;
import Inventario.Inventario;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class InterfazMenuAdmi extends JFrame {
    private static final long serialVersionUID = 1L;
    Inventario inventario;
	VehiculoRentalSystem rentalSystem;

    public InterfazMenuAdmi(Inventario inventario) {
    	this.inventario = inventario;
    	rentalSystem = inventario.getAplicacion();
    
         if (validacionAdminGeneral()) {
 			setTitle("Interfaz Administrador General");
 		    setSize(300, 300);
 		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 		       
 		    getContentPane().removeAll();

 		    JPanel menuPanel = new JPanel();
 		    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

 	        JButton agregarCarroButton = new JButton("Agregar Carro");
 	        JButton eliminarCarroButton = new JButton("Eliminar Carro");
 	        JButton verificarInfoVehiculoButton = new JButton("Verificar Información de Vehículo");
 	        JButton configurarSegurosButton = new JButton("Configurar Seguros");
 	        JButton configurarPreciosButton = new JButton("Configurar Precios");
 		    JButton salirButton = new JButton("Salir al Menú Principal");
 		    menuPanel.add(agregarCarroButton);
 	        menuPanel.add(eliminarCarroButton);
 	        menuPanel.add(verificarInfoVehiculoButton);
 	        menuPanel.add(configurarSegurosButton);
 	        menuPanel.add(configurarPreciosButton);
 	        menuPanel.add(salirButton);
 	        agregarCarroButton.addActionListener(new ActionListener() {
 	        	@Override
 		        public void actionPerformed(ActionEvent e) {
 	        		opcion1AdminGeneral();
 	        	}
 	        });

 		    eliminarCarroButton.addActionListener(new ActionListener() {
 		        @Override
 		        public void actionPerformed(ActionEvent e) {
 		        	opcion2AdminGeneral();
 		        }
 		    });

 		    verificarInfoVehiculoButton.addActionListener(new ActionListener() {
 		        @Override
 		        public void actionPerformed(ActionEvent e) {
 		        	String vehiculoID = JOptionPane.showInputDialog("Ingrese el ID (placa) del vehículo que desea consultar:");
 		            if (vehiculoID != null) {
 		            	mostrarInformacionVehiculoEnVentana(vehiculoID);
 		            }
 		         }
 		     });

 		    configurarSegurosButton.addActionListener(new ActionListener() {
 		    	@Override
 		        public void actionPerformed(ActionEvent e) {
 		    		String[] seguros = {"Seguro Bajo", "Seguro Medio", "Seguro Alto"};
 		            JComboBox<String> seguroComboBox = new JComboBox<>(seguros);

 		            JPanel myPanel = new JPanel(new GridLayout(2, 2));
 		            myPanel.add(new JLabel("Seleccione el seguro:"));
 		            myPanel.add(seguroComboBox);

 	                int result = JOptionPane.showConfirmDialog(null, myPanel,
 		                        "Seleccione el seguro", JOptionPane.OK_CANCEL_OPTION);

 		            if (result == JOptionPane.OK_OPTION) {
 		            	String selectedSeguro = (String) seguroComboBox.getSelectedItem();
 		                opcion5AdminGeneral(selectedSeguro);
 		            }
 		        }
 		    });
 		        
 		    configurarPreciosButton.addActionListener(new ActionListener() {
 		    	@Override
 		        public void actionPerformed(ActionEvent e) {
 		    		String[] categorias = {"SUV", "pequeño", "lujoso", "van"};
 		            JComboBox<String> categoriaComboBox = new JComboBox<>(categorias);

 		            JTextField tempAltaField = new JTextField();
 		            JTextField tempBajaField = new JTextField();
                     JTextField conductorField = new JTextField();
                     JTextField sedeField = new JTextField();

                     JPanel myPanel = new JPanel(new GridLayout(6, 4));
 		            myPanel.add(new JLabel("Seleccione la categoría:"));
 		            myPanel.add(categoriaComboBox);
 		            myPanel.add(new JLabel("Nueva tarifa temporada alta:"));
 		            myPanel.add(tempAltaField);
 		            myPanel.add(new JLabel("Nueva tarifa temporada baja:"));
                     myPanel.add(tempBajaField);
 		            myPanel.add(new JLabel("Nueva tarifa conductor adicional:"));
 		            myPanel.add(conductorField);
 		            myPanel.add(new JLabel("Nueva tarifa recoger esta categoría en otra sede:"));
 		            myPanel.add(sedeField);

 		            int preciosResult = JOptionPane.showConfirmDialog(null, myPanel,
 		                        "Configurar Precios", JOptionPane.OK_CANCEL_OPTION);

 		            if (preciosResult == JOptionPane.OK_OPTION) {
 		            	String selectedCategoria = (String) categoriaComboBox.getSelectedItem();
 		                try {
 		                	int nuevaTarifaTempAlta = Integer.parseInt(tempAltaField.getText());
 		                    int nuevaTarifaTempBaja = Integer.parseInt(tempBajaField.getText());
 		                    int nuevaTarifaConductor = Integer.parseInt(conductorField.getText());
 		                    int nuevaTarifaSede = Integer.parseInt(sedeField.getText());

 		                    // Llama al método para modificar los precios
 		                    rentalSystem.modificarPreciosCategoria(selectedCategoria, nuevaTarifaTempAlta, nuevaTarifaTempBaja,nuevaTarifaConductor,nuevaTarifaSede);

 		                    JOptionPane.showMessageDialog(null, "Precios actualizados con éxito.");
 		                } catch (NumberFormatException ex) {
 		                    JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos.");
 		                }
 		            }
 		    	}
 		    });


 		        salirButton.addActionListener(new ActionListener() {
 		            @Override
 		            public void actionPerformed(ActionEvent e) {
 		                System.exit(0);
 		            }
 		        });

 		        // Agregar directamente el panel de menú al JFrame
 		        add(menuPanel);

 		        // Validar y refrescar
 		        revalidate();
 		        repaint();

 		        setLocationRelativeTo(null);
 		        setVisible(true);
 		    }
    }

    private boolean validacionAdminGeneral() {
        final String ADMIN_USERNAME = "admiG";
        final String ADMIN_PASSWORD = "admiG";

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
    
    private void consultarVehiculoEnInterfaz(String vehiculoID) {
        // Crear un nuevo OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // Guardar la salida estándar actual
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;

        // Redirigir la salida estándar y de error al nuevo OutputStream
        System.setOut(ps);
        System.setErr(ps);

        // Llamar a la función en el modelo
        rentalSystem.consultarVehiculo(vehiculoID);

        // Restaurar la salida estándar y de error originales
        System.setOut(oldOut);
        System.setErr(oldErr);

        // Obtener la información capturada
        String infoVehiculo = baos.toString();

        // Mostrar la información en un cuadro de diálogo
        JOptionPane.showMessageDialog(null, infoVehiculo, "Información del Vehículo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void opcion1AdminGeneral() {
        JTextField vehiculoIDField = new JTextField();
        JTextField marcaField = new JTextField();
        JTextField modeloField = new JTextField();
        String[] categorias = {"SUV", "Pequeño", "Lujoso", "Van"};
        JComboBox<String> categoriaComboBox = new JComboBox<>(categorias);
        JTextField capacidadField = new JTextField();
        JTextField colorField = new JTextField();
        JTextField transmisionField = new JTextField();
        String[] sedes = {"Sucursal Central", "Sucursal Norte", "Sucursal Sur"};
        JComboBox<String> sedeComboBox = new JComboBox<>(sedes);

        JPanel myPanel = new JPanel(new GridLayout(8, 2));
        myPanel.add(new JLabel("Placa del carro:"));
        myPanel.add(vehiculoIDField);
        myPanel.add(new JLabel("Marca:"));
        myPanel.add(marcaField);
        myPanel.add(new JLabel("Modelo:"));
        myPanel.add(modeloField);
        myPanel.add(new JLabel("Categoría:"));
        myPanel.add(categoriaComboBox);
        myPanel.add(new JLabel("Capacidad:"));
        myPanel.add(capacidadField);
        myPanel.add(new JLabel("Color:"));
        myPanel.add(colorField);
        myPanel.add(new JLabel("Transmisión:"));
        myPanel.add(transmisionField);
        myPanel.add(new JLabel("Sede:"));
        myPanel.add(sedeComboBox);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Ingrese los datos del nuevo carro", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String vehiculoID = vehiculoIDField.getText();
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            String categoria = (String) categoriaComboBox.getSelectedItem();
            int capacidad = Integer.parseInt(capacidadField.getText());
            String color = colorField.getText();
            String transmision = transmisionField.getText();
            String sede = (String) sedeComboBox.getSelectedItem();

            Vehiculo newCar = new Vehiculo(vehiculoID, marca, modelo, categoria, color, transmision, capacidad, sede);
            rentalSystem.addVehiculo(newCar);
            rentalSystem.escribirVehiculo(newCar);
            JOptionPane.showMessageDialog(null, "Carro agregado con éxito a la sede " + sede);
        }
    }
    public void opcion2AdminGeneral() {
        // Eliminar carro
        String carId = JOptionPane.showInputDialog("Ingrese el ID (Placa) del carro que desea eliminar:");

        Vehiculo carToDelete = null;
        for (Vehiculo car : rentalSystem.getVehiculos()) {
            if (car.getVehiculoId().equals(carId)) {
                carToDelete = car;
                break;
            }
        }

        if (carToDelete != null) {
            rentalSystem.removeVehiculo(carToDelete);
            rentalSystem.eliminarVehiculo(carToDelete);
            JOptionPane.showMessageDialog(null, "Carro eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "ID inválido o carro no encontrado.");
        }
    }
   
    private Image getCustomImage() {
        // Cargar tu imagen personalizada (ajusta la ruta según la ubicación de tu imagen)
        try {
            return ImageIO.read(new File("InventarioDatos/image.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private Image getCustomIcon() {
        // Cargar tu imagen personalizada (ajusta la ruta según la ubicación de tu imagen)
        try {
       
            BufferedImage image = ImageIO.read(new File("InventarioDatos/image.png"));
            return image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private Image getScaledImage(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    
    private void opcion5AdminGeneral(String nombreSeguro) {
        String nuevoPrecioStr = JOptionPane.showInputDialog("Escriba el nuevo precio del seguro " + nombreSeguro + ":");

        try {
            double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
            for (Seguro seg : rentalSystem.getSeguros()) {
                if (seg.getNombre().equals(nombreSeguro)) {
                    seg.SetCostoPorDia(nuevoPrecio);
                    rentalSystem.modificarSeguro(seg, nuevoPrecio);
                    JOptionPane.showMessageDialog(null, "Precio del seguro actualizado con éxito.");
                    System.out.println("Precio del seguro actualizado con éxito.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Seguro no encontrado.");
            System.out.println("Seguro no encontrado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido para el nuevo precio.");
            System.out.println("Error: Ingrese un valor numérico válido para el nuevo precio.");
        }
    }
    
    private void mostrarInformacionVehiculoEnVentana(String vehiculoID) {
	    Vehiculo vehiculoConsultado = null;

	    // Buscar el vehículo en la lista de carros
	    for (Vehiculo vehiculo : rentalSystem.getVehiculos()) {
	        if (vehiculo.getVehiculoId().equals(vehiculoID)) {
	            vehiculoConsultado = vehiculo;
	            break;
	        }
	    }

	    if (vehiculoConsultado != null) {
	        StringBuilder infoVehiculo = new StringBuilder();
	        for (AgendaCarro indisponibilidad : vehiculoConsultado.getAgendaVehiculo()) {
	            infoVehiculo.append("El vehículo no estará disponible desde ").append(indisponibilidad.getFechaInicio()).append("\n");
	            infoVehiculo.append("hasta ").append(indisponibilidad.getFechaFinal()).append("\n");
	            infoVehiculo.append("-------------------------------------------------------\n");
	            infoVehiculo.append("-------------------------------------------------------\n");
	        }
	        infoVehiculo.append("* Se encuentra en la sede: ").append(vehiculoConsultado.getUbicacion()).append("\n");
	        infoVehiculo.append("* Color: ").append(vehiculoConsultado.getColor()).append("\n");
	        infoVehiculo.append("* Marca: ").append(vehiculoConsultado.getmarca()).append("\n");
	        infoVehiculo.append("* Tipo de transmisión: ").append(vehiculoConsultado.getTipoTransmision()).append("\n");
	        infoVehiculo.append("* Modelo: ").append(vehiculoConsultado.getmodelo()).append("\n");
	        infoVehiculo.append("* Categoría: ").append(vehiculoConsultado.getCategoria()).append("\n");
	        infoVehiculo.append("-------------------------------------------------------\n");
	        infoVehiculo.append("-------------------------------------------------------\n");
	        infoVehiculo.append("Reservas:\n");
	        for (Reserva reserva : rentalSystem.getReservas()) {
	            if (reserva.getIdCarro().equals(vehiculoID)) {
	                infoVehiculo.append("Reservado desde: ").append(reserva.getFechaEntrega()).append("\n");
	                infoVehiculo.append("Reservado hasta: ").append(reserva.getFechaRetorno()).append("\n");
	                infoVehiculo.append("Reservado por: ").append(reserva.getCliente()).append("\n");
	                infoVehiculo.append("Estado actual de la reserva: ").append(reserva.getEstado()).append("\n");
	                infoVehiculo.append("-------------------------------------------------------\n");
	                infoVehiculo.append("-------------------------------------------------------\n");
	            }
	            else {
	            	infoVehiculo.append("Este vehículo no está reservado");
	            	
	            }
	        }

	        // Mostrar la información en una ventana de diálogo
	        JOptionPane.showMessageDialog(null, infoVehiculo.toString(), "Información del Vehículo", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(null, "Vehículo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    
}