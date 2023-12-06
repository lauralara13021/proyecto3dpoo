package Consola;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;





import Inventario.Inventario;
import SistemaAlquiler.Categoria;
import SistemaAlquiler.Cliente;
import SistemaAlquiler.Empleado;
import SistemaAlquiler.IPasarelaDePago;
import SistemaAlquiler.PasarelaPagoPayPal;
import SistemaAlquiler.PasarelaPagoPayU;
import SistemaAlquiler.PasarelaPagoSire;
import SistemaAlquiler.Reserva;
import SistemaAlquiler.Vehiculo;
import SistemaAlquiler.VehiculoRentalSystem;



public class InterfazEmpleado extends JFrame {


	Inventario inventario;
	VehiculoRentalSystem rentalSystem;
	//InterfazCliente interfazCliente;
	
	
	public InterfazEmpleado(Inventario inventario) {
		this.inventario = inventario;
		rentalSystem = inventario.getAplicacion();
		if (validacionEmpleado()) {
			setTitle("Interfaz Empleado");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(400, 300);
			setResizable(false);
			setLocationRelativeTo(null);
	      
			getContentPane().removeAll();

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(6, 1));

			JButton recibirCarroButton = new JButton("Recibir carro");
			JButton generarReservaButton = new JButton("Generar reserva");
			JButton entregarCarroButton = new JButton("Entregar carro");
			JButton reportarMantenimientoButton = new JButton("Reportar carro que necesita mantenimiento");
			JButton salirButton = new JButton("Volver al menu principal");
			JLabel texto = new JLabel("¿Qué desea hacer?");
			texto.setHorizontalAlignment(JLabel.CENTER);

			panel.add(texto);
			panel.add(recibirCarroButton);
			panel.add(generarReservaButton);
			panel.add(entregarCarroButton);
			panel.add(reportarMantenimientoButton);
			panel.add(salirButton);

			add(panel);

			recibirCarroButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					opcion1Empleado();
				}
			});
	      
			generarReservaButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
	            	opcion2Empleado();
				}
			});

			entregarCarroButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					opcion3Empleado();
				}
			});

			reportarMantenimientoButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					opcion4Empleado();
				}
			});

			salirButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					getContentPane().removeAll();
					System.exit(0);
				}
			});
			}
	  	}
	public void opcion1Empleado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JLabel nombreLabel = new JLabel("Ingrese el nombre del cliente que está retornando el vehículo:");    
        JTextField nombreTextField = new JTextField();
        JLabel fechaLabel = new JLabel("Ingrese la fecha en la que se está recibiendo el vehículo (dd/MM/yyyy):");
        JTextField fechaTextField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(nombreLabel);
        panel.add(nombreTextField);
        panel.add(fechaLabel);
        panel.add(fechaTextField);
        
        int result = JOptionPane.showConfirmDialog(null, panel,
                "Recepción de Vehículos", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
        	String nombre = nombreTextField.getText();
        	String fecha = fechaTextField.getText();
        	try {
            	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaHoy = dateFormat.parse(fecha);
                
                LocalDate localDate = fechaHoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaSumada = localDate.plusDays(2);
                Date fechaFinal = Date.from(fechaSumada.atStartOfDay(ZoneId.systemDefault()).toInstant());
                boolean recibido = false;
                for(Reserva reserva: rentalSystem.getReservas()) {
                	if(reserva.getCliente().equals(nombre) && reserva.getEstado().equals("vigente")) {
                		String carId = reserva.getIdCarro();
                		rentalSystem.returnVehiculo(carId, fechaHoy, fechaFinal);
                		
                		JOptionPane.showMessageDialog(null, "Vehículo retornado de manera exitosa", "A partir de este momento la tarjeta del cliente queda nuevamente activada", JOptionPane.INFORMATION_MESSAGE);
                		recibido = true;
                		break;
                	}
                }
                if(!recibido) {
                	JOptionPane.showMessageDialog(null, "El cliente no tiene ninguna reserva vigente");
                }
            }catch(ParseException e) {
            	JOptionPane.showMessageDialog(null, "La fecha no está en formato (dd/MM/yyyy)", "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	public void opcion2Empleado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JLabel nombreLabel = new JLabel("Ingrese el nombre del cliente:");
        JTextField nombreTextField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(nombreLabel);
        panel.add(nombreTextField);
		
		int result = JOptionPane.showConfirmDialog(null, panel,
                "Recepción de Vehículos", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
        	boolean registrado = true;
        	String nombreCliente = nombreTextField.getText();
        	for(String clienteStr: rentalSystem.getClientes().keySet()) {
    			if(clienteStr.equals(nombreCliente)) {
    				Cliente cliente = rentalSystem.getClientes().get(nombreCliente);
    				InterfazCliente interfazCliente = new InterfazCliente(inventario);
    				registrado = false;
    			}
        	}
        	if(registrado) {
        		JOptionPane.showMessageDialog(null, "El cliente no está registrado en el sistema, pidale que se registre");
        	}
		}
	}
	public void opcion3Empleado() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        
        String pasarela = elegirPasarela();
        
        JLabel nombreLabel = new JLabel("Ingrese el nombre del cliente para buscar su reserva:");    
        JTextField nombreTextField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(nombreLabel);
        panel.add(nombreTextField);
        
        int result = JOptionPane.showConfirmDialog(null, panel,
                "Entrega de Vehículos", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
        	boolean error = true;
        	Reserva reservaEvaluada = null;
        	for(Reserva reserva: rentalSystem.getReservas()) {
        		String nombreCliente = nombreTextField.getText();
        		if(reserva.getCliente().equals(nombreCliente)) {
        			reservaEvaluada = reserva;
        			rentalSystem.AgregarConductoresReserva(reservaEvaluada);
        		
        			Categoria categoria = rentalSystem.getCategorias().get(reservaEvaluada.getCategoria());
	            
        			double PrecioBase = reservaEvaluada.getPrecioBase();
        			double PrecioAbonado = reservaEvaluada.getPrecioAbonado();
        			double PrecioConductores = reservaEvaluada.getPrecioConductores(PrecioBase, categoria.getvalorAdicionalConductor());
        			double PrecioTodo = PrecioBase+PrecioConductores-PrecioAbonado;
        			error = false;
        			
        			
        			if(pasarela != null) {
        				realizarPago(pasarela,PrecioTodo);
        				JOptionPane.showMessageDialog(null, "El total que se le descontará de la tarjeta es de: " + PrecioTodo);
        				generarPDF(reservaEvaluada, PrecioTodo);
        			}
        			
        			for(Vehiculo car : rentalSystem.getVehiculos()) {
        				if(car.getVehiculoId().equals(reservaEvaluada.getIdCarro())) {
        					car.setUbicacion("Alquilado");
        				}
        			}
	            }
        	}
        	if(error) {
                JOptionPane.showMessageDialog(null, "No hay reservas a su nombre", "Cliente sin reservas", JOptionPane.ERROR_MESSAGE);
                }
        }
	}
    public void opcion4Empleado() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(500, 300);
        setLocationRelativeTo(null);
        
        JLabel carIdLabel = new JLabel("Ingrese el ID (placa) del vehículo que necesita mantenimiento:");    
        JTextField carIdTextField = new JTextField();
        JLabel fechaLabel = new JLabel("Ingrese la fecha en la que el vehículo estará en mantenimiento (dd/MM/yyyy):");    
        JTextField fechaTextField = new JTextField();
        JLabel cantDiasLabel = new JLabel("Ingrese la cantidad de días que el vehículo estará en mantenimiento:");    
        JTextField cantDiasTextField = new JTextField();
        
        
        JPanel panelMantenimiento = new JPanel(new GridLayout(6, 1));
        panelMantenimiento.add(carIdLabel);
        panelMantenimiento.add(carIdTextField);
        panelMantenimiento.add(fechaLabel);
        panelMantenimiento.add(fechaTextField);
        panelMantenimiento.add(cantDiasLabel);
        panelMantenimiento.add(cantDiasTextField);
        
        int result = JOptionPane.showConfirmDialog(null, panelMantenimiento,
                "Mantenimiento de Vehiculo", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
        String carId = carIdTextField.getText();
        String fechaHoyStr = fechaTextField.getText();
        String cantDiasStr = cantDiasTextField.getText();
        int cantDias = Integer.parseInt(cantDiasStr);
        	try {
        		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            	Date fechaHoy = dateFormat.parse(fechaHoyStr);
            
            	LocalDate localDate = fechaHoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            	LocalDate fechaSumada = localDate.plusDays(cantDias-1);
            	Date fechaFinal = Date.from(fechaSumada.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            	rentalSystem.carroEnMantenimiento(carId, fechaHoy, fechaFinal);
        	}catch(ParseException e) {
        		JOptionPane.showMessageDialog(null, "La fecha no está en formato (dd/MM/yyyy)", "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
        	}
        }
	}
	
    	/**
    	 * Realiza la validación de un empleado, solicitando su nombre de usuario y contraseña.
    	 * Si las credenciales son válidas, muestra el menú de opciones del empleado correspondiente.
    	 *
    	 * @param scanner El objeto Scanner para la entrada de usuario.
    	 * @throws ParseException Si ocurre un error de análisis de fecha.
    	 */
    public boolean validacionEmpleado() {
    	boolean validacion = false;
        while(!validacion) {
        	String empleadoUsername = JOptionPane.showInputDialog("Ingrese su usuario de empleado:");
            String empleadoPassword = JOptionPane.showInputDialog("Ingrese su contraseña:");
            
            for(Empleado empleado : rentalSystem.getEmpleados()) {
            	if(empleado.getLogin().equals(empleadoUsername) && empleado.getContrasena().equals(empleadoPassword)) {
            		validacion = true;
            	}
            }
            if(validacion == false) {
            	JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos", "Acceso denegado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
		return validacion;
	}
    
    public String elegirPasarela() {
        Object[] opciones = {"PayU", "PayPal", "PagoSire"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Seleccione la pasarela de banco:",
                "Selección de Pasarela",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion == -1) {
 
            return null;
        }

        return opciones[seleccion].toString();
    }
    
    public void realizarPago(String pasarela, double monto) {
       
        String numeroTarjeta = JOptionPane.showInputDialog("Ingrese el número de tarjeta:");


        if (numeroTarjeta == null || numeroTarjeta.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Número de tarjeta inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

      
     // Solicitar el nombre del titular de la tarjeta
        String nombreTitular = JOptionPane.showInputDialog("Ingrese el nombre del titular de la tarjeta:");


        if (nombreTitular == null || nombreTitular.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Nombre del titular inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }


        try {
         
            if (monto <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Monto inválido. Ingrese un número positivo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

   
        String fechaVencimientoStr = JOptionPane.showInputDialog("Ingrese la fecha de vencimiento de la tarjeta (MM/yyyy):");


        if (fechaVencimientoStr == null || fechaVencimientoStr.isEmpty() || !fechaVencimientoStr.matches("\\d{2}/\\d{4}")) {
            JOptionPane.showMessageDialog(
                    null,
                    "Formato de fecha de vencimiento inválido. Utilice el formato MM/yyyy.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

    
        Date fechaVencimiento;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
            fechaVencimiento = dateFormat.parse(fechaVencimientoStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al procesar la fecha de vencimiento.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        IPasarelaDePago pasarelaDePago = null;

        switch (pasarela) {
            case "PayPal":
                pasarelaDePago = new PasarelaPagoPayPal();
                break;
            case "PayU":
                pasarelaDePago = new PasarelaPagoPayU();
                break;
            case "PagoSire":
                pasarelaDePago = new PasarelaPagoSire();
                break;
            default:
                JOptionPane.showMessageDialog(
                        null,
                        "Error: Pasarela de pago no válida",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
        }

        // Llamada a procesarPago y bloquearCupo con el número de tarjeta ingresado
        boolean pagoExitoso = pasarelaDePago.procesarPago(numeroTarjeta, nombreTitular, monto, fechaVencimiento);

        if (pagoExitoso) {
            boolean cupoBloqueado = pasarelaDePago.bloquearCupo(numeroTarjeta, monto);

            if (cupoBloqueado) {
                JOptionPane.showMessageDialog(
                        null,
                        "Pago realizado con éxito a través de la pasarela " + pasarela + ". La tarjeta ha sido bloqueada.",
                        "Pago Exitoso",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Error al bloquear el cupo de la tarjeta",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al procesar el pago",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void generarPDF(Reserva reserva, double precioTotal) {

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

  
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("-------FACTURA VEHICULO RENTAL SYSTEM-----");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Información de la Reserva:");
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("- Cliente: " + reserva.getCliente());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("- Vehículo: " + reserva.getIdCarro());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("- Precio Total: " + precioTotal);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("- Fecha de retorno: " + reserva.getFechaRetorno());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("- ID sede donde devolver vehículo: " + reserva.getIdSedeDevolver());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("- ID sede donde recoger vehículo: " + reserva.getIdSedeRecoger());
            contentStream.newLineAtOffset(0, -20);
         
            contentStream.endText();

    
            File imageFile = new File("Entrega3/firmaAdmi.png");  
            PDImageXObject image = LosslessFactory.createFromImage(document, ImageIO.read(imageFile));
            float scale = 0.5f; 
            contentStream.drawImage(image, 50, 400, image.getWidth() * scale, image.getHeight() * scale);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 350);
            contentStream.showText("Firma administrador general");
            contentStream.endText();

 
            contentStream.close();

            // Guarda el PDF en un archivo
            String pdfFileName = "Reserva_" + reserva.getIdCarro() + ".pdf";
            document.save(pdfFileName);
            document.close();

            File pdfFile = new File(pdfFileName);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "No se puede abrir el visor de PDF predeterminado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Archivo PDF generado con éxito: " + pdfFileName,
                    "Generación de PDF",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Error al generar el archivo PDF",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    
    
	
}
