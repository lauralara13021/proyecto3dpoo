package SistemaAlquiler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.lang.Math;
import java.lang.reflect.InvocationTargetException;





public class VehiculoRentalSystem extends JFrame{
	private static final long serialVersionUID = 1L;
	private List<Vehiculo> cars;
    private List<Reserva> reservas;
    private HashMap<String, Categoria> categorias;
    private HashMap<String,Cliente> clientes;
    private List<Seguro> segurosDisponibles;
    private Map<String, String> usuariosYContraseñas;
    private List<Conductor> conductores;
    private Map<String, Sede> sedes;
    private List<Empleado> empleados;
    private Map<String, List<AgendaCarro>> agendasCarros;
    private Map<String, List<String>> segurosReserva;
    private static VehiculoRentalSystem instance;
    private List<Categoria>precioCategoria;
    private List<IPasarelaDePago> pasarelas;
    
    /**
     * Constructor de la clase VehiculoRentalSystem. Inicializa las listas y mapas necesarios.
     */
    public VehiculoRentalSystem() {
        cars = new ArrayList<>();
        reservas = new ArrayList<>();
        categorias = new HashMap<>();
        clientes = new HashMap<>();
        segurosDisponibles = new ArrayList<>();
        usuariosYContraseñas =  new HashMap<>();
        conductores = new ArrayList<>();
        sedes = new HashMap<>();
        empleados = new ArrayList<>();
        agendasCarros = new HashMap<>();
        segurosReserva = new HashMap<>();
        precioCategoria = new ArrayList<>();
        pasarelas = new ArrayList<>();
    }
    
    public List<Empleado> getEmpleados() {
    	return empleados;
    }
    
    public List<Vehiculo> getVehiculos() {
    	return cars;
    }
    
    public List<Seguro> getSeguros() {
    	return segurosDisponibles;
    }
    
    public List<Reserva> getReservas() {
    	return reservas;
    }
    
    public Map<String, Categoria> getCategorias() {
    	return categorias;
    }
    
    public Map<String, Sede> getSedes(){
    	return sedes;
    }
    
    public Map<String, Cliente> getClientes(){
    	return clientes;
    }
    
    public Map<String, String> getContraseñasUsuarios(){
    	return usuariosYContraseñas;
    }
    
    public static VehiculoRentalSystem getInstance() {
        if (instance == null) {
            instance = new VehiculoRentalSystem();
        }
        return instance;
    }
    
    //Vehiculo
    /**
     * Agrega un vehículo al sistema.
     *
     * @param car El vehículo a agregar.
     */
    public void addVehiculo(Vehiculo car) {
        cars.add(car);
    }
    
    /**
     * Escribe la información de un vehículo en el archivo de inventario.
     *
     * @param car El vehículo que se va a escribir en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirVehiculo(Vehiculo car) {
    	String rutaArchivo = "InventarioDatos/carrosDatos";
        String contenido = "\n" + car.getVehiculoId() + "," + car.getmarca() + "," + car.getmodelo() + "," 
    	+ car.getCategoria( )+ "," + car.getColor() + "," + car.getTipoTransmision() + "," + car.getCapacidad() 
    	+ "," + car.getUbicacion();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
        	writer.write(contenido);  
        } catch (IOException e) {
        	System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
    
    /**
     * Elimina un vehículo al sistema.
     *
     * @param car El vehículo a eliminar.
     */
    public void removeVehiculo(Vehiculo vehiculo) {
    	cars.remove(vehiculo);
    }
    
    /**
     * Elimina la información de un vehículo del archivo de inventario.
     *
     * @param vehiculo El vehículo que se va a eliminar del archivo.
     * @throws IOException Si ocurre un error al eliminar la información del vehículo.
     */
    public void eliminarVehiculo(Vehiculo vehiculo) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/carrosDatos"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] carInfo = line.split(",");
                if (vehiculo.getVehiculoId().equals(carInfo[0])) {
                }else {
                	input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/carrosDatos");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Cliente
    /**
     * Agrega un cliente al sistema.
     *
     * @param cliente El cliente a agregar.
     */
    public void addCliente(Cliente cliente) {
        clientes.put(cliente.getName(), cliente);
    }
    
    /**
     * Escribe la información de un cliente en el archivo de clientes.
     *
     * @param cliente El cliente que se va a escribir en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirCliente(Cliente cliente) {
    	
    	String rutaArchivo = "InventarioDatos/Clientes";
    	SimpleDateFormat fechaVencimiento = new SimpleDateFormat("dd/MM/yyyy");
    	String vencimientoLicencia = fechaVencimiento.format(cliente.getlicencia().getFechaVencimineto());
    	String vencimientoTarjeta = fechaVencimiento.format(cliente.getMedioPago().getFechaVencimiento());
    	
        String contenido = cliente.getTelefono() + "," + cliente.getName()+ "," + cliente.getCorreo() 
        + "," + cliente.getlicencia().getNumero()+ "," + cliente.getlicencia().getPaisExpedicion() + "," 
        + vencimientoLicencia + "," + cliente.getMedioPago().getNumero() + "," 
        + cliente.getMedioPago().getTipo() + "," + vencimientoTarjeta + "," 
        + cliente.getLogin() + "," + cliente.getContrasena() + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
        	writer.write(contenido);  
        } catch (IOException e) {
        	System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }

    //categorias
    /**
     * Agrega una categoria al sistema.
     *
     * @param categoria La categoría a agregar.
     */
    public void addCategorias(Categoria categoria) {
    	categorias.put(categoria.getNombre(), categoria);
    }
    
    
    //Seguro
    /**
     * Agrega un seguro al sistema.
     *
     * @param seguro El seguro a agregar.
     */
    public void addSeguro(Seguro seguro) {
    	segurosDisponibles.add(seguro);
    }
    
    /**
     * Modifica el precio de un seguro en el archivo de seguros.
     *
     * @param seguro      El seguro que se va a modificar.
     * @param nuevoPrecio El nuevo precio del seguro.
     * @throws IOException Si ocurre un error al modificar el seguro en el archivo.
     */
    public void modificarSeguro(Seguro seguro, double nuevoPrecio) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Seguros"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] seguroInfo = line.split(",");
                if (seguro.getNombre().equals(seguroInfo[0])) {
                	input += line.replaceAll(seguroInfo[1], String.valueOf(nuevoPrecio))+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Seguros");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    public void configuracionPasarelas(){
    	try (BufferedReader br = new BufferedReader(new FileReader("InventarioDatos/configuracion_pasarelas"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Cargar dinámicamente la clase de la pasarela
                Class<?> pasarelaClass = Class.forName(linea);

                // Crear una instancia de la clase de la pasarela
                IPasarelaDePago pasarela = (IPasarelaDePago) pasarelaClass.getDeclaredConstructor().newInstance();

                pasarelas.add(pasarela);
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    public void modificarPrecioCategoria(Categoria categoria, double nuevoPrecio) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Categorias"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] seguroInfo = line.split(",");
                if (categoria.getNombre().equals(seguroInfo[0])) {
                	input += line.replaceAll(seguroInfo[1], String.valueOf(nuevoPrecio))+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Categorias");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    //Sede
    /**
     * Agrega una sede al sistema.
     *
     * @param sede La sede a agregar.
     */
    public void addSede(Sede sede) {
    	sedes.put(sede.getNombre(), sede);
    }
    
    /**
     * Modifica el horario de atención de una sede en el archivo de sedes.
     *
     * @param sede         La sede cuyo horario se va a modificar.
     * @param hora         La nueva hora de inicio o final de atención.
     * @param finalOinicial Indica si se va a modificar el horario final o inicial.
     * @throws IOException Si ocurre un error al modificar el horario de la sede en el archivo.
     */
    public void modificarHorarioSede(Sede sede, LocalTime hora, String finalOinicial) {
    	int posInfo;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Sedes"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] sedeInfo = line.split(",");
                if (finalOinicial.equals("final")) {
                	posInfo = 3;
    
                }else {
                	posInfo = 2;
                }
                
                if (sede.getNombre().equals(sedeInfo[0])) {
                	String horaStr = formatter.format(hora);
                	input += line.replaceAll(sedeInfo[posInfo], horaStr )+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Sedes");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void modificarPreciosCategoria(String nombreCategoria, int nuevaTarifaTempAlta, int nuevaTarifaTempBaja, int nuevaTarifaConductor,int nuevoPrecioSede) {
        List<Categoria> categorias = obtenerCategoriasDesdeArchivo();

        // Buscar la categoría por nombre
        for (Categoria categoria : categorias) {
            if (categoria.getNombre().equals(nombreCategoria)) {
                // Actualizar los precios
                categoria.setTarifaTempAlta(nuevaTarifaTempAlta);
                categoria.setTarifaTempBaja(nuevaTarifaTempBaja);
                categoria.setValorAdicionalConductor(nuevaTarifaConductor);
                categoria.setValorSedeDiferente(nuevoPrecioSede);

                // Guardar los cambios en el archivo de texto
                guardarCategoriasEnArchivo(categorias);
                return;
            }
        }
     // Si llegamos aquí, la categoría no se encontró
        System.out.println("Categoría no encontrada.");
    }
    
    private void guardarCategoriasEnArchivo(List<Categoria> categorias) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("InventarioDatos/Categorias"))) {
            for (Categoria categoria : categorias) {
                writer.println(categoria.toStringParaArchivo());
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }
    
    private List<Categoria> obtenerCategoriasDesdeArchivo() {
        List<Categoria> categorias = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Categorias"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    String nombre = partes[0];
                    int tarifaTempAlta = Integer.parseInt(partes[1]);
                    int tarifaTempBaja = Integer.parseInt(partes[2]);
                    int valorAdicionalConductor = Integer.parseInt(partes[3]);
                    int valorSedeDiferente = Integer.parseInt(partes[4]);

                    Categoria categoria = new Categoria(nombre, tarifaTempAlta, tarifaTempBaja, valorAdicionalConductor, valorSedeDiferente);
                    categorias.add(categoria);
                } else {
                    System.out.println("Formato incorrecto en línea: " + linea);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }

        return categorias;
    }

    /**
     * Modifica la dirección de una sede en el archivo de sedes.
     *
     * @param sede      La sede cuya dirección se va a modificar.
     * @param direccion La nueva dirección de la sede.
     * @throws IOException Si ocurre un error al modificar la dirección de la sede en el archivo.
     */
    public void modificarDireccionSede(Sede sede, String direccion) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Sedes"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] sedeInfo = line.split(",");
                if (sede.getNombre().equals(sedeInfo[0])) {
                	input += line.replaceAll(sedeInfo[1], direccion)+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Sedes");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Empleado
    /**
     * Agrega un empleado al sistema.
     *
     * @param empleado El empleado a agregar.
     */
    public void addEmpleado(Empleado empleado) {
    	empleados.add(empleado);
    }
    
    public void removeEmpleado(Empleado empleado) {
    	empleados.remove(empleado);
    }
    
    /**
     * Escribe la información de un empleado en el archivo de empleados.
     *
     * @param empleado El empleado que se va a escribir en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirEmpleado(Empleado empleado) {
    	String rutaArchivo = "InventarioDatos/Empleados";
        String contenido = empleado.getSede() + "," + empleado.getLogin()+ "," + empleado.getContrasena() + "," + empleado.getNombre() +"\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
        	writer.write(contenido); 
        } catch (IOException e) {
        	System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
    
    public void eliminarEmpleado(Empleado empleado) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Empleados"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] empleadoInfo = line.split(",");
                if (empleado.getNombre().equals(empleadoInfo[3])) {
                }else {
                	input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Empleados");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void modificarLoginEmpleado(Empleado empleado, String login) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Empleados"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] empleadoInfo = line.split(",");
                if (empleado.getLogin().equals(empleadoInfo[1])) {
                	input += line.replaceAll(empleadoInfo[1], login)+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Empleados");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void modificarContraseñaEmpleado(Empleado empleado, String contraseña) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Empleados"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] empleadoInfo = line.split(",");
                if (empleado.getContrasena().equals(empleadoInfo[2])) {
                	input += line.replaceAll(empleadoInfo[2], contraseña)+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Empleados");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //usuarioYcontraseña
    /**
     * Agrega un par de usuario y contraseña al mapa de usuarios y contraseñas.
     *
     * @param login      El nombre de usuario.
     * @param contrasena La contraseña asociada al usuario.
     */
    public  void addUsuarioYContraseña(String login, String contrasena) {
    	usuariosYContraseñas.put(login, contrasena);
	}
    
    /**
     * Escribe un par de usuario y contraseña en el archivo de usuarios y contraseñas.
     *
     * @param login      El nombre de usuario.
     * @param contrasena La contraseña asociada al usuario.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirUsuarioContrasena(String login, String contrasena) {
    	String rutaArchivo = "InventarioDatos/usuariosYcontraseñas";
        String contenido = login + "," + contrasena + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
        	writer.write(contenido);  // Agrega una nueva línea al final
        } catch (IOException e) {
        	System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
    
    //Reserva
    /**
     * Agrega una reserva al sistema.
     *
     * @param reserva La reseva a agregar.
     */
    public void addReserva(Reserva reserva) {
    	reservas.add(reserva);
    }
    
    /**
     * Escribe la información de una reserva en el archivo de reservas.
     *
     * @param reserva La reserva que se va a escribir en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirReserva(Reserva reserva) {
    	String rutaArchivo = "InventarioDatos/Reservas";
    	
    	SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
    	String fechaEntrega = fecha.format(reserva.getFechaEntrega());
    	String fechaRetorno = fecha.format(reserva.getFechaRetorno());
    	
        String contenido = reserva.getCategoria() + "," + reserva.getIdSedeRecoger()+ "," 
    	+ reserva.getIdSedeDevolver() + "," + fechaEntrega + "," + fechaRetorno 
    	+ "," + reserva.getCliente() + "," + reserva.getIdCarro() + "," + reserva.getPrecioBase() + "," 
    	+ reserva.getPrecioAbonado() + "," + reserva.getEstado() + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
        	writer.write(contenido);  
        } catch (IOException e) {
        	System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
    
    
    /**
     * Modifica la sede de entrega o retorno en el archivo de reservas.
     *
     * @param reserva       La reserva cuya sede se va a modificar.
     * @param sede          La nueva sede de entrega o retorno.
     * @param entregaOretorno Indica si se va a modificar la sede de entrega o retorno.
     * @throws IOException Si ocurre un error al modificar la sede en el archivo.
     */
    public void modificarSedeReserva(Reserva reserva, String sede, String entregaOretorno, String cliente) {
    	int posInfo;
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Reservas"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] infoReserva = line.split(",");
                if (entregaOretorno.equals("entrega")) {
                	posInfo = 1;
                }else {
                	posInfo = 2;
                }
                
                
                if (reserva.getCliente().equals(cliente)) {
                	
                	input += line.replaceFirst(infoReserva[posInfo], sede) + "\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Reservas");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Modifica la fecha de entrega o retorno en el archivo de reservas.
     *
     * @param reserva       La reserva cuya fecha se va a modificar.
     * @param fecha         La nueva fecha de entrega o retorno.
     * @param entregaOretorno Indica si se va a modificar la fecha de entrega o retorno.
     * @throws IOException Si ocurre un error al modificar la fecha en el archivo.
     */
    public void modificarFechaReserva(Reserva reserva, Date fecha, String entregaOretorno) {
    	int posInfo;
    	String infoFecha;
    	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Reservas"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] reservaInfo = line.split(",");
                if (entregaOretorno.equals("entrega")) {
                	posInfo = 3;
                	infoFecha = date.format(reserva.getFechaEntrega());
    
                }else {
                	posInfo = 4;
                	infoFecha = date.format(reserva.getFechaRetorno());
                }
                
                if (infoFecha.equals(reservaInfo[posInfo])) {
                	String fechaStr = date.format(fecha);
                	input += line.replaceAll(reservaInfo[posInfo], fechaStr ) + "\n";
                	System.out.print(line);
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Reservas");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Modifica el precio de la reserva en el archivo, en caso de que se hayan generado cambios.
     *
     * @param reserva       La reserva cuyo precio se va a modificar.
     * @param nuevoPrecio   El nuevo precio de la reserva.
     * @param cliente       Indica el nombre del cliente dueño de la reserva.
     * @throws IOException  Si ocurre un error al modificar el precio en el archivo.
     */
    public void modificarPrecioReserva(Reserva reserva, double nuevoPrecio, String cliente) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Reservas"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] infoReserva = line.split(",");
                
                if (reserva.getCliente().equals(cliente)) {
                	input += line.replaceAll(infoReserva[7], String.valueOf(nuevoPrecio))+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Reservas");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void modificarEstadoReserva(Reserva reserva, String nuevoEstado, String cliente) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Reservas"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] infoReserva = line.split(",");
                
                if (reserva.getCliente().equals(cliente)) {
                	input += line.replaceAll(infoReserva[9],nuevoEstado)+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/Reservas");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Conductor
    /**
     * Agrega un conductor a la lista de conductores.
     *
     * @param conductor El conductor que se va a agregar.
     */
    public void addConductor(Conductor conductor) {
    	conductores.add(conductor);
    }    
    
    /**
     * Escribe la información de un conductor en el archivo de conductores.
     *
     * @param conductor El conductor que se va a escribir en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirConductor(Conductor conductor) {
    	String rutaArchivo = "InventarioDatos/Conductores";
    	
    	SimpleDateFormat fechaVencimiento = new SimpleDateFormat("dd/MM/yyyy");
    	String vencimientoLicencia = fechaVencimiento.format(conductor.getLicencia().getFechaVencimineto());
    	
        String contenido = conductor.getNombre() + "," +  conductor.getTelefono() + "," 
    	+ conductor.getCorreo() + "," + conductor.getLicencia().getNumero() + ","
        + conductor.getLicencia().getPaisExpedicion() + "," + vencimientoLicencia + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
        	writer.write(contenido);  
        } catch (IOException e) {
        	System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
    
    //AgendaCarros
    /**
     * Agrega una agenda de carro a la lista de agendas de un carro identificado por su ID.
     *
     * @param IdCarro    El ID del carro al que se va a agregar la agenda.
     * @param agendaCarro La agenda de carro que se va a agregar.
     */
    public void addAgendasCarros(String IdCarro, AgendaCarro agendaCarro) {
    	if(agendasCarros.containsKey(IdCarro) == true) {
    		agendasCarros.get(IdCarro).add(agendaCarro);
    	}else {
    		ArrayList<AgendaCarro> listaAgendas = new ArrayList<>();
    		agendasCarros.put(IdCarro, listaAgendas);
    	}
    } 
    
    /**
     * Escribe la información de una agenda de carro en el archivo de agendas de carros.
     *
     * @param IdCarro    El ID del carro al que se relaciona la agenda.
     * @param agendaCarro La agenda de carro que se va a escribir en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirAgendasCarros(String IdCarro, AgendaCarro agendaCarro) {
    	String rutaArchivo = "InventarioDatos/AgendasCarros";
    	
    	SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
    	String fechaInicio = fecha.format(agendaCarro.getFechaInicio());
    	String fechaFinal = fecha.format(agendaCarro.getFechaFinal());
    	
        String contenido = IdCarro + "," +  fechaInicio + "," 
    	+ fechaFinal + "," +agendaCarro.getEstadoCarro() + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
        	writer.write(contenido);  
        } catch (IOException e) {
        	System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
    
    /**
     * Modifica la fecha de inicio o final de una agenda de carro en el archivo de agendas de carros.
     *
     * @param agendaCarro     La agenda de carro cuya fecha se va a modificar.
     * @param fecha           La nueva fecha de inicio o final.
     * @param finalOinicial   Indica si se va a modificar la fecha de inicio o final.
     * @throws IOException Si ocurre un error al modificar la fecha en el archivo.
     */
    public void modificarAgendasCarros(AgendaCarro agendaCarro, Date fecha, String finalOinicial) {
    	int posInfo;
    	String infoFecha;
    	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
    	try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/AgendasCarros"))) {
    		String line;
    		String input = "";
    		while ((line = reader.readLine()) != null) {
                String[] agendaInfo = line.split(",");
                if (finalOinicial.equals("inicial")) {
                	posInfo = 0;
                	infoFecha = date.format(agendaCarro.getFechaInicio());
    
                }else {
                	posInfo = 1;
                	infoFecha = date.format(agendaCarro.getFechaFinal());
                }
                
                if (infoFecha.equals(agendaInfo[posInfo])) {
                	String fechaStr = date.format(fecha);
                	input += line.replaceAll(agendaInfo[posInfo], fechaStr)+"\n";
                }else {
                    input += line+"\n";
                }
            }
    		 FileOutputStream fileOut = new FileOutputStream("InventarioDatos/AgendasCarros");
    		    fileOut.write(input.getBytes());
    		    fileOut.close();
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //SegurosReserva
    /**
     * Agrega un seguro a la lista de seguros de una reserva de un cliente.
     *
     * @param nombreCliente El nombre del cliente al que se va a agregar el seguro.
     * @param nombreSeguro  El nombre del seguro que se va a agregar.
     */
    public void addSegurosReserva(String nombreCliente, String nombreSeguro) {
    	if(segurosReserva.containsKey(nombreCliente) == true) {
    		segurosReserva.get(nombreCliente).add(nombreSeguro);
    	}else {
    		ArrayList<AgendaCarro> listaAgendas = new ArrayList<>();
    		agendasCarros.put(nombreCliente, listaAgendas);
    	}
    }
    
    /**
     * Escribe la información de un seguro de reserva en el archivo de seguros de reserva.
     *
     * @param nombreCliente El nombre del cliente al que se relaciona el seguro.
     * @param nombreSeguro  El nombre del seguro que se va a escribir en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void escribirSegurosReserva(String nombreCliente, String nombreSeguro) {
    	String rutaArchivo = "InventarioDatos/SegurosReserva";
        String contenido = nombreCliente + "," +  nombreSeguro + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
        	writer.write(contenido);  
        } catch (IOException e) {
        	System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
    
    
    
    //Extra
    /**
     * Consulta la información de un vehículo por su ID, mostrando sus agendas y ubicación.
     *
     * @param vehiculoID El ID del vehículo que se va a consultar.
     */
    public void consultarVehiculo(String vehiculoID) {
        Vehiculo vehiculoConsultado = null;

        for (Vehiculo vehiculo : cars) {
            if (vehiculo.getVehiculoId().equals(vehiculoID)) {
                vehiculoConsultado = vehiculo;
                break;
            }
        }

        if (vehiculoConsultado != null) {
            for(AgendaCarro indisponibilidad : vehiculoConsultado.getAgendaVehiculo()){
            	System.out.println("El vehículo no estará disponible desde " + indisponibilidad.getFechaInicio());
            	System.out.println("hasta " + indisponibilidad.getFechaFinal());
            }
            System.out.println("Se encuentra en la sede: " + vehiculoConsultado.getUbicacion());
            System.out.println("Reservas:");
            int cantReservas = 0;
            for(Reserva reserva: reservas) {
            	if (reserva.getIdCarro().equals(vehiculoID)) {
            		cantReservas += 1;
            		System.out.println("Reservado desde: " + reserva.getFechaEntrega());
            		System.out.println("Reservado hasta: " + reserva.getFechaRetorno());
            		System.out.println("Reservado por: " + reserva.getCliente());
            		System.out.println("Estado actual de la reserva: " + reserva.getEstado());
            		System.out.println("-------------------------------------------------------");
            		
            	}
            if(cantReservas == 0) {
            	System.out.println("El vehículo no tiene historial de reservas");
            }
            }
            
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    
    }
    
    /**
     * Registra un nuevo cliente a través de entrada desde el escáner y lo almacena en el sistema.
     *
     * @param scanner    El escáner para la entrada del cliente.
     * @param login      El nombre de usuario del cliente.
     * @param contrasena La contraseña del cliente.
     * @return El nuevo cliente registrado.
     */
    public  Cliente RegistarCliente(String login, String contrasena) {
    	Cliente newCliente = null;
    	dispose();
    	
		getContentPane().removeAll();
		
		setTitle("Registrarse ");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JPanel myPanel = new JPanel(new GridLayout(3, 2));
		
		getContentPane().add(myPanel);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        JTextField clienteName = new JTextField();
		myPanel.add(new JLabel("Ingrese su nombre completo"));
		myPanel.add(clienteName);
        JTextField clienteTelefono = new JTextField();
    	myPanel.add(new JLabel("Digite su telefono: "));
    	myPanel.add(clienteTelefono);
    	JTextField clienteCorreo = new JTextField();
     	myPanel.add(new JLabel("Digite su correo: "));
     	myPanel.add(clienteCorreo);
    	
     	JTextField clienteLicencia = new JTextField();
     	myPanel.add(new JLabel("Digite el numero de su licencia de conducción: "));
     	myPanel.add(clienteLicencia);
     	JTextField clientePaisLicencia = new JTextField();
     	myPanel.add(new JLabel("Digite el país de expedición de su licencia de conducción: "));
     	myPanel.add(clientePaisLicencia);
     	JTextField clienteVenciLicencia = new JTextField();
     	myPanel.add(new JLabel("Digite la fecha de vencimiento de su licencia (dd/MM/yyyy): "));
     	myPanel.add(clienteVenciLicencia);
     	JTextField clienteNumTarjeta = new JTextField();
      	myPanel.add(new JLabel("Digite el número de su tarjeta: "));
      	myPanel.add(clienteNumTarjeta);
      	JTextField clienteTipoTarjeta = new JTextField();
      	myPanel.add(new JLabel("Digite el tipo de tarjeta que tiene (VISA, Mastercard, ...): "));
      	myPanel.add(clienteTipoTarjeta);
      	JTextField venciTarjeta = new JTextField();
      	myPanel.add(new JLabel("Digite la fecha de vencimiento de su tarjeta (dd/MM/yyyy): "));
      	myPanel.add(venciTarjeta);
		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"llene los campos para el registro", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {

    	try {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date VencimientoLicencia = dateFormat.parse(clienteVenciLicencia.getText());
            
            Licencia licencia = new Licencia(clienteLicencia.getText(), clientePaisLicencia.getText(), VencimientoLicencia);
            
           
            Date VencimientoTarjeta = dateFormat.parse(venciTarjeta.getText());
            
            MedioPago medioPago = new MedioPago(clienteNumTarjeta.getText(), clienteTipoTarjeta.getText(), VencimientoTarjeta);
        	
        	newCliente = new Cliente(clienteTelefono.getText(), clienteName.getText(), clienteCorreo.getText(), licencia, medioPago, login, contrasena);
        	Conductor conductor = new Conductor(clienteName.getText(), clienteTelefono.getText(), clienteCorreo.getText(), licencia);
        	
        	addConductor(conductor);
        	escribirConductor(conductor);
        	addCliente(newCliente);
        	escribirCliente(newCliente);
        	addUsuarioYContraseña(login, contrasena);
        	escribirUsuarioContrasena(login,contrasena);
    		JOptionPane.showMessageDialog(null, "cliente registrado con exito");

        	
    	} catch (ParseException e){
    		JOptionPane.showMessageDialog(null, "La fecha no está en formato (dd/MM/yyyy)");
    	}
    	setVisible(true);
    }
		return newCliente;
    }
    
    /**
     * Registra un nuevo conductor a través de entrada desde el escáner y lo almacena en el sistema.
     *
     * @param scanner El escáner para la entrada del conductor.
     * @return El nuevo conductor registrado.
     */
    
    public  Conductor RegistrarConductor() {
    	Conductor nuevoConductor = null;
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JLabel nombreLabel = new JLabel("Ingrese el nombre conductor:");    
        JTextField nombreTextField = new JTextField();
        JLabel telefonoLabel = new JLabel("Ingrese el telefono del conductor:");
        JTextField telefonoTextField = new JTextField();
        JLabel correoLabel = new JLabel("Ingrese el correo del conductor:");    
        JTextField correoTextField = new JTextField();
        JLabel num_licenciaLabel = new JLabel("Ingrese el numero licencia de conducción:");
        JTextField num_licenciaTextField = new JTextField();
        JLabel paisLabel = new JLabel("Ingrese el país de expedición de licencia de conducción:");    
        JTextField paisTextField = new JTextField();
        JLabel fechaLabel = new JLabel("Ingrese la fecha de vencimiento de licencia (dd/MM/yyyy):");
        JTextField fechaTextField = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(nombreLabel);
        panel.add(nombreTextField);
        panel.add(telefonoLabel);
        panel.add(telefonoTextField);
        panel.add(correoLabel);
        panel.add(correoTextField);
        panel.add(num_licenciaLabel);
        panel.add(num_licenciaTextField);
        panel.add(paisLabel);
        panel.add(paisTextField);
        panel.add(fechaLabel);
        panel.add(fechaTextField);
        
    	
    	String conductorName = nombreTextField.getText();
    	String telefono = telefonoTextField.getText();
    	String correo = correoTextField.getText();
    	String numeroLicencia = num_licenciaTextField.getText();
    	String PaisLicencia = paisTextField.getText();
    	String VencimientoLicenciaStr = fechaTextField.getText();
    	
    	int result = JOptionPane.showConfirmDialog(null, panel,
                "Registrar Conductor", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
        	try {
        		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        		Date VencimientoLicencia = dateFormat.parse(VencimientoLicenciaStr);
            
        		Licencia licencia = new Licencia(numeroLicencia, PaisLicencia, VencimientoLicencia);
            
        		nuevoConductor = new Conductor(conductorName, telefono, correo, licencia);
        		addConductor(nuevoConductor);
        		escribirConductor(nuevoConductor);
        		JOptionPane.showMessageDialog(null, "Se añadio el conductor exitosamente");
            } catch(ParseException e) {
    		JOptionPane.showMessageDialog(null, "La fecha no está en formato (dd/MM/yyyy)", "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
            }
        }
		return nuevoConductor;
    }
    
    /**
     * Determina la tarifa de alquiler para un vehículo basada en la fecha de inicio y la categoría.
     *
     * @param startDate      La fecha de inicio del alquiler.
     * @param categoriaInput La categoría del vehículo seleccionado.
     * @return La tarifa calculada.
     */
    public  int DeterminarTarifa(Date startDate, String categoriaInput) {
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int month = calendar.get(Calendar.MONTH);
        boolean temporadaAlta = (month >= Calendar.JULY && month <= Calendar.DECEMBER);
        
        
        int tarifa = 0;
        Categoria categoria = categorias.get(categoriaInput);
        if (temporadaAlta == true) {
        	tarifa = categoria.getTarifaTempAlta();
        } else {
        	tarifa = categoria.getTarifaTempBaja();
        }
        return tarifa;
    }
    
    /**
     * Devuelve un vehículo, realiza las actualizaciones correspondientes y muestra la información.
     *
     * @param carId     El ID del vehículo que se va a devolver.
     * @param fechaHoy  La fecha actual.
     * @param fechaFinal La fecha de devolución del vehículo.
     */
    public void returnVehiculo(String carId, Date fechaHoy, Date fechaFinal) {
    	Vehiculo carToReturn = null;
        for (Vehiculo car : cars) {
            if (car.getVehiculoId().equals(carId)) {
                carToReturn = car;
                break;
            }
        }

        if (carToReturn != null) {
            Cliente cliente = null;
            for (Reserva reserva : reservas) {
            	System.out.print(reserva.getIdCarro());
                if (reserva.getIdCarro().equals(carId)) {
                	cliente = clientes.get(reserva.getCliente());
                    carToReturn.setUbicacion(reserva.getIdSedeDevolver());
                    if (reserva.getIdSedeDevolver() != reserva.getIdSedeRecoger()) {
                    	Sede sedeDevolver = sedes.get(reserva.getIdSedeDevolver());
                    	sedeDevolver.agregarVehiculo(carToReturn);
                    	Sede sedeRecoger = sedes.get(reserva.getIdSedeRecoger());
                    	sedeRecoger.eliminarVehiculo(carToReturn);
                    	reserva.setEstado("terminada");
                    	modificarEstadoReserva(reserva, "terminada", cliente.getName());
                    }
                    break;
                }
            }

            if (cliente != null) {
            	AgendaCarro agenda = carToReturn.entregar(fechaHoy, fechaFinal);
                addAgendasCarros(carToReturn.getVehiculoId(), agenda);
                escribirAgendasCarros(carToReturn.getVehiculoId(), agenda);
                
                System.out.println("Vehículo retornado de manera exitosa por el cliente: " + cliente.getName());
                System.out.println("A partir de este momento la tarjeta del cliente queda nuevamente activada");
            } else {
                System.out.println("El vehículo no fue rentado o falta información");
            }
        } else {
            System.out.println("ID inválido o vehículo no retornado");
        }
    }
    
    /**
     * Marca un vehículo como en mantenimiento y registra la agenda de mantenimiento.
     *
     * @param carId       El ID del vehículo que se va a marcar como en mantenimiento.
     * @param fechaInicio La fecha de inicio del mantenimiento.
     * @param fechaFinal  La fecha de finalización del mantenimiento.
     */
    public  void carroEnMantenimiento(String carId, Date fechaInicio, Date fechaFinal) {
    	Vehiculo carroMantenimiento = null;
    	for (Vehiculo car : cars) {
            if (car.getVehiculoId().equals(carId)) {
                carroMantenimiento = car;
                break;
            }
    	}
        if (carroMantenimiento != null) {
            AgendaCarro agenda = carroMantenimiento.mantenimiento(fechaInicio, fechaFinal);
           	addAgendasCarros(carroMantenimiento.getVehiculoId(), agenda);
           	escribirAgendasCarros(carroMantenimiento.getVehiculoId(), agenda);
           	System.out.println("El carro con placa " + carId + "estará en mantenimiento desde " + fechaInicio);
           	System.out.println("hasta " + fechaFinal);
        }else {
           	System.out.println("ID inválido o vehículo no retornado");         
        }
    }
    
    /**
     * Permite al cliente seleccionar seguros para su reserva y actualiza el precio total.
     *
     * @param scanner       El escáner para la entrada del cliente.
     * @param reservaActual La reserva actual del cliente.
     * @param precio        El precio base del alquiler.
     * @param dias          El número de días de alquiler.
     * @return El nuevo precio total con los seguros seleccionados.
     */
    public double seleccionarSeguros(Reserva actualReserva, double precio, int dias) {
    	final Reserva reservaActual = actualReserva;
        double totalPrice = precio;
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        setTitle("Seleccion de Seguros");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] seguros = new String[segurosDisponibles.size()];
        for (int i = 0; i < segurosDisponibles.size(); i++) {
            seguros[i] = segurosDisponibles.get(i).getNombre();
        }
        final JComboBox<String> seguroComboBox = new JComboBox<>(seguros);
        myPanel.add(new JLabel("Seleccione el seguro:"));
        myPanel.add(seguroComboBox);
        seguroComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otras acciones...
            	String seguroOpcion = (String) seguroComboBox.getSelectedItem();
            	for(Seguro seguro: segurosDisponibles ) {
            		if(seguro.getNombre().equals(seguroOpcion)) {
            			escribirSegurosReserva(reservaActual.getCliente(), seguroOpcion);
            			addSegurosReserva(reservaActual.getCliente(), seguroOpcion);
            			
            			
            		}
            		
            	}
                
                
                JOptionPane.showMessageDialog(null, "Alquiler realizado exitosamente");
                // Otras acciones...
            }
        });

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Seleccione el seguro", JOptionPane.OK_CANCEL_OPTION);
        

        if (result == JOptionPane.OK_OPTION) {
            // Otras acciones...
        }

        // Actualizar el precio total con el costo de los seguros seleccionados
        totalPrice = reservaActual.getPrecioConSeguros(precio, dias);
        JOptionPane.showMessageDialog(null, "Nuevo precio total con seguros: " + totalPrice);
        
        // No llamamos a setVisible(true) aquí para que no se muestre la ventana de selección de seguros antes de tiempo
        
        return totalPrice;
    }
    
    /**
     * Permite al cliente agregar conductores adicionales a su reserva.
     *
     * @param scanner        El escáner para la entrada del cliente.
     * @param reservaEvaluada La reserva en la que se agregarán conductores adicionales.
     */
    public  void AgregarConductoresReserva(Reserva reservaEvaluada) {
    	boolean continuar = true;
    	while(continuar) {
    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JLabel pregunta1Label = new JLabel("¿Desea agregar más conductores a su renta? (Y/N):");    
            JTextField respuesta1TextField = new JTextField();
            JPanel panel1 = new JPanel(new GridLayout(2, 1));
            panel1.add(pregunta1Label);
            panel1.add(respuesta1TextField);
        	
        	int result1 = JOptionPane.showConfirmDialog(null, panel1,
                    "Agregar Conductores", JOptionPane.OK_CANCEL_OPTION);
            
            if (result1 == JOptionPane.OK_OPTION) {
            	String AgregarConductores = respuesta1TextField.getText();
            	if (AgregarConductores.equalsIgnoreCase("Y")) {
            		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            		setLocationRelativeTo(null);
            		JLabel pregunta2Label = new JLabel("¿El conductor ya está registrado? (Y/N):");    
            		JTextField respuesta2TextField = new JTextField();
            		JPanel panel2 = new JPanel(new GridLayout(2, 1));
            		panel2.add(pregunta2Label);
            		panel2.add(respuesta2TextField);
            	
            		int result2 = JOptionPane.showConfirmDialog(null, panel2,
                            "Agregar Conductores", JOptionPane.OK_CANCEL_OPTION);
                    
                    if (result2 == JOptionPane.OK_OPTION) {
        		String registrar = respuesta2TextField.getText();
        		
        		if (registrar.equalsIgnoreCase("Y")) {
        			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setLocationRelativeTo(null);
                    JLabel pregunta3Label = new JLabel("Escriba el correo del conductor:");    
                    JTextField respuesta3TextField = new JTextField();
                    JPanel panel3 = new JPanel(new GridLayout(2, 1));
                    panel3.add(pregunta3Label);
                    panel3.add(respuesta3TextField);
                    
                    int result3 = JOptionPane.showConfirmDialog(null, panel3,
                            "Agregar Conductores", JOptionPane.OK_CANCEL_OPTION);
                    
                    if (result3 == JOptionPane.OK_OPTION) {
            		String correoConductor = respuesta3TextField.getText();
            		for(Conductor conductor: conductores) {
            	   		if (conductor.getCorreo().equals(correoConductor)) {
            	    		reservaEvaluada.AgregarConductor(conductor);
            	    		JOptionPane.showMessageDialog(null, "El conductor fue agregado exitosamente");
            	    		continuar = false;
            	    		break;
            	    	}
            		}
                    }
                    
                    
        		}else {
        			Conductor nuevoConductor = RegistrarConductor();
        			reservaEvaluada.AgregarConductor(nuevoConductor);
        		}
            	}
            }else {
        		continuar = false;
        	}
        	}
    	}
    	}
    
    
    /**
     * Permite saber cuantos días dura una reserva creada por el cliente.
     *
     * @param startDate la fecha en la que va a iniciar la reserva.
     * @param endDate   La fecha en la que se terminará la reserva.
     */
    public int cantidadDiasRenta(Date startDate, Date endDate) {
    	long tiempoMilisegundos = endDate.getTime() - startDate.getTime();
        double diasReserva = (double) (tiempoMilisegundos / (1000 * 60 * 60 * 24));
        int diasRenta = (int) diasReserva;
        if (diasReserva != Math.floor(diasReserva)){
        	diasRenta += 1;
        }
        return diasRenta;
    }
    
    public int[] calcularDisponibilidadMensual(Sede sede) {
    	
        int[] disponibilidadMensual = new int[12];
        
        for (Vehiculo carro : sede.getVehiculos()) {
            ArrayList<AgendaCarro> agenda = carro.getAgendaVehiculo();

            for (AgendaCarro rango : agenda) {
            	if(rango.getEstadoCarro().equals("Reservado")) {
	            	Calendar fechaInicio = Calendar.getInstance();
	            	fechaInicio.setTime(rango.getFechaInicio());
	            	
	            	Calendar fechaFin = Calendar.getInstance();
	            	fechaFin.setTime(rango.getFechaFinal());
	            	
	            	int mesInicio = fechaInicio.get(Calendar.MONTH) - 1;
	            	int mesFinal = fechaInicio.get(Calendar.MONTH) - 1;
	                
	                if(mesInicio == mesFinal) {
	                	disponibilidadMensual[mesInicio]++;
	                }else {
	                	disponibilidadMensual[mesInicio]++;
	                	disponibilidadMensual[mesFinal]++;
	                }
            	}
            }
        }
        for(int i:disponibilidadMensual) {
        	System.out.print(i);
        }
        
        // Calcula la disponibilidad restando el total de carros que inicialmente están disponibles en el mes
        //al número de carros que estuvieron indisponibles en el mes
        //para saber cuantos carros disponibles hubieron en el mes
        for (int i = 0; i < disponibilidadMensual.length; i++) {
        	
            disponibilidadMensual[i] = (30* sede.getVehiculos().size())- disponibilidadMensual[i];
        }

        return disponibilidadMensual;
    }



    
    /**
     * Carga un empleado a todas las sedes disponibles.
     *
     * @param empleado El empleado que se va a cargar en las sedes.
     */
    public void CargaEmpleadosASede(Empleado empleado) {
    	for(Sede sede : sedes.values()) {
    		if(empleado.getSede().equals(sede.getNombre())) {
    			sede.agregarEmpleado(empleado);
    		}
    	}
    }
    
    /**
     * Carga un vehículo a todas las sedes disponibles.
     *
     * @param vehiculo El vehículo que se va a cargar en las sedes.
     */
    public void CargaCarrosASede(Vehiculo vehiculo) {
    	for(Sede sede : sedes.values()) {
    		if (vehiculo.getUbicacion().equals(sede.getNombre())){
    			sede.agregarVehiculo(vehiculo);;
    		}
    	}
    }
    
    /**
     * Carga una agenda de carro a un vehículo específico.
     *
     * @param IdCarro     El ID del vehículo al que se va a agregar la agenda.
     * @param agendaCarro La agenda de carro que se va a cargar en el vehículo.
     */
    public void CargaAgendaACarros(String IdCarro, AgendaCarro agendaCarro) {
    	for (Vehiculo carro: cars) {
    		if (carro.getVehiculoId().equals(IdCarro)){
    			carro.AñadirIndisponibilidad(agendaCarro);
    		}
    	}
    }
    
    /**
     * Carga un seguro a una reserva específica para un cliente.
     *
     * @param nombreCliente El nombre del cliente al que se va a cargar el seguro.
     * @param nombreSeguro  El nombre del seguro que se va a cargar en la reserva.
     */
    public void CargarSegurosAReserva(String nombreCliente, String nombreSeguro) {
    	for (Reserva reserva: reservas) {
    		if (reserva.getCliente().equals(nombreCliente)){
    			for(Seguro seguro: segurosDisponibles) {
    				if (seguro.getNombre().equals(nombreSeguro)) {
    					reserva.AgregarSeguro(seguro);
    				}
    			}
    		}
    	}
    }
    
    
    
    //TODO//
    /**
     * Muestra el menú principal del sistema y maneja las interacciones iniciales.
     */
   
	
	

    
    
    //CLIENTE//
    /**
     * Muestra el menú principal del cliente y maneja las interacciones del cliente.
     *
     * @param scanner    El escáner para la entrada del cliente.
     * @param newCliente El cliente que ha iniciado sesión o se ha registrado.
     */
	
    
    /**
     * Opción 1 para que un cliente alquile un vehículo.
     *<b>Pre:</b> Scanner y newCliente no deben ser nulos.
     * <b>Post:</b> El cliente puede alquilar un vehículo y gestionar la reserva si están disponibles y en el formato correcto.
     * @param scanner     Objeto Scanner para la entrada del usuario.
     * @param newCliente  Objeto Cliente representando al cliente actual.
     * @throws ParseException si hay un error en el formato de la fecha.
     */
	 
    
    /**
     * Opción 2 para que un cliente modifique una reserva existente.
     *<b>Pre:</b> Scanner y newCliente no deben ser nulos.
     *<b>Post:</b> El cliente puede modificar una reserva existente si la reserva pertenece a ese cliente.
     * @param scanner     Objeto Scanner para la entrada del usuario.
     * @param newCliente  Objeto Cliente representando al cliente actual.
     * @throws ParseException si hay un error en el formato de la fecha.
     */
	 
	
	//EMPLEADO//
    /**
	 * Muestra el menú de opciones para un empleado y gestiona las operaciones seleccionadas.
	 * <b>Pre:</b> El parámetro scanner no debe ser nulo.
     * <b>Post:</b> Muestra un menú de opciones para un empleado y gestiona las opciones ingresadas.
	 * @param scanner El objeto Scanner para la entrada de usuario.
	 * @throws ParseException Si ocurre un error de análisis de fecha.
	 */
	public void mostrarMenuEmpleado() {
		Scanner scanner = new Scanner(System.in);
		boolean continuar = true;
		while (continuar) {
            System.out.println("===== Vehiculo Rental System =====");
            System.out.println("1. Recibir Carro");
            System.out.println("2. Generar reserva");
            System.out.println("3. Entregar Carro");
            System.out.println("4. Reportar que un carro necesita mantenimiento");
            System.out.println("5. Salir");
            System.out.print("Ingrese una opción numérica: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 1) {
            	opcion1Empleado(scanner);
            }
            else if (choice == 2){
            	opcion2Empleado(scanner);
            }
            else if (choice == 3){
            	opcion3Empleado(scanner);
            }
            else if (choice == 4){
            	opcion4Empleado(scanner);
            }
            else if (choice == 5) {
            	continuar = false;
            }
            else {
            	System.out.print("Ingrese una opción válida: ");
            }
		}
	}
	
	/**
	 * Permite al empleado registrar la devolución de un vehículo y realiza las operaciones asociadas.
	 * <b>Pre:</b> El parámetro scanner no debe ser nulo.
     * <b>Post:</b> Permite al empleado registrar la devolución de un vehículo y realiza las operaciones asociadas.
	 * @param scanner     El objeto Scanner para la entrada de usuario.
	 * @throws ParseException Si ocurre un error de análisis de fecha.
	 */
	public void opcion1Empleado(Scanner scanner) {
		System.out.println("\n======= Retornar vehículo ======= \n");
        System.out.println("Ingrese el nombre del cliente que está retornando el vehículo: ");
        System.out.println("(El cliente debe tener una reserva vigente para poder retornar el vehículo)");
        String clienteNombre = scanner.nextLine();
        System.out.print("Ingrese la fecha de hoy (dd/MM/yyyy): ");
        String fechaHoyStr = scanner.nextLine();
        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaHoy = dateFormat.parse(fechaHoyStr);
            
            LocalDate localDate = fechaHoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaSumada = localDate.plusDays(2);
            Date fechaFinal = Date.from(fechaSumada.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            for(Reserva reserva: reservas) {
            	if(reserva.getCliente().equals(clienteNombre) && reserva.getEstado().equals("vigente")) {
            		String carId = reserva.getIdCarro();
            		returnVehiculo(carId, fechaHoy, fechaFinal);
            	}else {
            		System.out.println("El cliente no tiene ninguna reserva vigente");
            	}
            }
            
           
           
        }catch(ParseException e) {
        	System.err.println("La fecha no está en formato (dd/MM/yyyy");
        }
	}
	
	/**
	 * Permite al empleado generar una reserva para un cliente existente.
	 * <b>Pre:</b> El parámetro scanner no debe ser nulo. El cliente debe estar registrado en el sistema.
     * <b>Post:</b> Permite al empleado generar una reserva para un cliente existente.
	 * @param scanner El objeto Scanner para la entrada de usuario.
	 */
	public void opcion2Empleado(Scanner scanner) {
		
		System.out.println("Nombre del cliente: ");
		String NombreCliente = scanner.nextLine();
		for(String clienteStr: clientes.keySet()) {
			if(clienteStr.equals(NombreCliente)) {
				Cliente cliente = clientes.get(NombreCliente);
				//opcion1Cliente(cliente);
			}else {
				System.out.println("El cliente no está registrado en el sistema, pidale que se registre");
			}
		}
	}
	
	/**
	 * Permite al empleado registrar la entrega de un vehículo y realizar cálculos relacionados con la reserva.
	 * <b>Pre:</b> El parámetro scanner no debe ser nulo.
     * <b>Post:</b> Permite al empleado registrar la entrega de un vehículo y realizar cálculos relacionados con la reserva.
	 * @param scanner El objeto Scanner para la entrada de usuario.
	 * @throws ParseException Si ocurre un error de análisis de fecha.
	 */
	public void opcion3Empleado(Scanner scanner) {
		System.out.println("\n======= Entregar vehículo ======= \n");
        System.out.print("Ingrese el nombre del cliente para buscar su reserva: ");
        String NombreCliente = scanner.nextLine();
        
        Reserva reservaEvaluada = null;
        for(Reserva reserva: reservas) {
        	if(reserva.getCliente().equals(NombreCliente)) {
        		reservaEvaluada = reserva;
        		AgregarConductoresReserva(reservaEvaluada);
        		
	            Categoria categoria = categorias.get(reservaEvaluada.getCategoria());
	            
	            double PrecioBase = reservaEvaluada.getPrecioBase();
	            double PrecioAbonado = reservaEvaluada.getPrecioAbonado();
	            double PrecioConductores = reservaEvaluada.getPrecioConductores(PrecioBase, categoria.getvalorAdicionalConductor());
	            double PrecioTodo = PrecioConductores - PrecioAbonado;
	            
	            System.out.println("La entrega del vehículo fue exitosa ");
	            System.out.println("El total que se le descontará de la tarjeta es de: " + PrecioTodo);
	            System.out.println("A partir de este momento la tarjeta del cliente queda bloqueda");
	            
	            for(Vehiculo car : cars) {
	            	if(car.getVehiculoId().equals(reservaEvaluada.getIdCarro())) {
	            		car.setUbicacion("Alquilado");
	            	}
	            }
	    
            
        	} else {
            	System.out.println("No hay reservas a su nombre");
            }
        }
	}
	
	/**
	 * Permite al empleado registrar que un vehículo necesita mantenimiento y programar su fecha de regreso.
	 * <b>Pre:</b> El parámetro scanner no debe ser nulo.
     * <b>Post:</b> Permite al empleado registrar que un vehículo necesita mantenimiento y programar su fecha de regreso.
	 * @param scanner El objeto Scanner para la entrada de usuario.
	 * @throws ParseException Si ocurre un error de análisis de fecha.
	 */
	public void opcion4Empleado(Scanner scanner) {
		System.out.print("Ingrese el ID(placa) del vehículo que necesita mantenimiento: ");
        String carId = scanner.nextLine();
        System.out.print("Ingrese la fecha en la que el vehículo estará en mantenimiento (dd/MM/yyyy): ");
        String fechaHoyStr = scanner.nextLine();
        System.out.print("Ingrese la cantidad de días que el vehículo estará en mantenimiento: ");
        String cantDiasStr = scanner.nextLine();
        int cantDias = Integer.parseInt(cantDiasStr);
        
        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaHoy = dateFormat.parse(fechaHoyStr);
            
            LocalDate localDate = fechaHoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaSumada = localDate.plusDays(cantDias-1);
            Date fechaFinal = Date.from(fechaSumada.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            carroEnMantenimiento(carId, fechaHoy, fechaFinal);
        }catch(ParseException e) {
        	System.err.println("La fecha no está en formato (dd/MM/yyyy");
        }
	}
	
	/**
	 * Realiza la validación de un empleado, solicitando su nombre de usuario y contraseña.
	 * Si las credenciales son válidas, muestra el menú de opciones del empleado correspondiente.
	 *
	 * @param scanner El objeto Scanner para la entrada de usuario.
	 * @throws ParseException Si ocurre un error de análisis de fecha.
	 */
	public void validacionEmpleado(Scanner scanner) {
        boolean empleadoAutenticado = true;
        
        while(empleadoAutenticado) {
        	System.out.print("Ingrese su nombre de usuario: ");
            String empleadoUsername = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String empleadoPassword = scanner.nextLine();
            
            for(Empleado empleado : empleados) {
            	
            	if(empleado.getLogin().equals(empleadoUsername) && empleado.getContrasena().equals(empleadoPassword)) {
            		empleadoAutenticado = false;
            		mostrarMenuEmpleado();
            		break;
            	}
            }
            		
            if(empleadoAutenticado == true) {
            	System.out.println("Nombre de usuario o contraseña incorrectos. Acceso denegado.");
            }
            	
        }
		
	}
	
	//ADMINISTRADOR//
	/**
     * Muestra el menú principal de los administradores para que puedan especificar sin son el 
	 * administrador local de alguna sede o el general.
     */
	
	
	//Administrador Local
	/**
	 * Muestra el menú del Administrador Local para una sede específica
	 * y permite al administrador local realizar diversas operaciones.
	 * usuario y contraseña para admin sede central: admin_central
	 * usuario y contraseña para admin sede norte: admin_norte
	 * usuario y contraseña para admin sede sur: admin_sur
	 * 
	 * @param scanner El objeto Scanner para la entrada de usuario.
	 * @param sede    La sede en la que el administrador local está trabajando.
	 */
	
	
	/**
	 * Permite al administrador local modificar la información de una sede,
	 * incluyendo la dirección y el horario de atención.
	 *
	 * @param scanner  El objeto Scanner para la entrada de usuario.
	 * @param sedeStr  El nombre de la sede que se va a modificar.
	 */
	public void opcion1AdminLocal(Scanner scanner, String sedeStr) {
		String sucursal = "Sucursal " + sedeStr;
		
		Sede sede = sedes.get(sucursal);
		
		System.out.println("1. Modificar direccion de la sede");
	    System.out.println("2. Modificar inicio de horario de atención");
	    System.out.println("2. Modificar fin de horario de atencion");
	    System.out.println("4. Salir al menu");
	    System.out.print("Ingrese una opción numérica: ");
	    int opcion = scanner.nextInt();
	    scanner.nextLine();
	    
	    if(opcion == 1) {
	    	System.out.println("Escribe la nueva dirección: ");
	    	String ubicacion = scanner.nextLine();
		    sede.setUbicacion(ubicacion);
		    modificarDireccionSede(sede, ubicacion);
	    } else if(opcion == 2) {
	    	System.out.println("Escribe la nueva hora (HH:mm:ss): ");
	    	String hora = scanner.nextLine();
	    	LocalTime horaInicial = LocalTime.parse(hora);
		    sede.setInicioHorarioAtencion(horaInicial);
		    modificarHorarioSede(sede, horaInicial, "incial");
	    }else if(opcion == 3) {
	    	System.out.println("Escribe la nueva hora (HH:mm:ss): ");
	    	String hora = scanner.nextLine();
	    	LocalTime horaFinal = LocalTime.parse(hora);
		    sede.setFinalHorarioAtencion(horaFinal);
		    modificarHorarioSede(sede, horaFinal, "final");
	    }else {
	    	System.out.println("Escriba una opción válida");
	    }
	}  
	
	/**
	 * Permite al administrador local agregar un nuevo empleado a la sede actual.
	 *
	 * @param scanner El objeto Scanner para la entrada de usuario.
	 * @param sedeStr El nombre de la sede a la que se agrega el empleado.
	 */
	public void opcion2AdminLocal(Scanner scanner, String sedeStr) {
		String sucursal = "Sucursal " + sedeStr;
		
		Sede sede = sedes.get(sucursal);
		
		System.out.print("Ingrese el nombre de usuario del nuevo empleado: ");
		String empleadoUsername = scanner.nextLine();
		System.out.print("Ingrese la contraseña del nuevo empleado: ");
		String empleadoPassword = scanner.nextLine();
		String nombre = "JOSE";
		Empleado nuevoEmpleado = new Empleado(sucursal, empleadoUsername, empleadoPassword, nombre);
		sede.agregarEmpleado(nuevoEmpleado);
		empleados.add(nuevoEmpleado);
		escribirEmpleado(nuevoEmpleado);
	}
	
	/**
	 * Realiza la autenticación y validación del administrador local antes de permitir
	 * el acceso al menú del administrador local.
	 *
	 * @param scanner El objeto Scanner para la entrada de usuario.
	 * @param sede    El nombre de la sede en la que se encuentra el administrador local.
	 * @throws IllegalArgumentException Si la sede no coincide con "Central", "Norte" o "Sur".
	 */
	

	



}