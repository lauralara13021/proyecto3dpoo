package Pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import SistemaAlquiler.Reserva;
import SistemaAlquiler.VehiculoRentalSystem;

@DisplayName("Pruebas para VehiculoRentalSystem")
public class VehiculoRentalSystemTest {

    private VehiculoRentalSystem vehiculoRentalSystem;
    private Reserva reserva;
    private static String rutaArchivoReservas = "InventarioDatos/Reservas";
    private static String rutaBackup = "InventarioDatos/ReservaTemporal";

    @BeforeAll
    public static void setUpClass() throws IOException {
        // Realiza una copia de respaldo del archivo original
        copiarArchivo(rutaArchivoReservas, rutaBackup);
    }

    @AfterAll
    public static void tearDownClass() throws IOException {
        // Restaura el contenido desde la copia de respaldo
        copiarArchivo(rutaBackup, rutaArchivoReservas);
        // Elimina la copia de respaldo
        new File(rutaBackup).delete();
    }

    @BeforeEach
    public void setUp() throws ParseException {
        vehiculoRentalSystem = new VehiculoRentalSystem();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        Date fechaEntrega = dateFormat.parse("11/12/2023/12/30");
        Date fechaRetorno = dateFormat.parse("13/12/2023/12/30");
        reserva = new Reserva("pequeño", fechaEntrega, fechaRetorno, "Laura", "ABC123", "Sede1", "Sede2", "Reservado");
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Restaura el contenido desde la copia de respaldo después de cada prueba
        copiarArchivo(rutaBackup, rutaArchivoReservas);
    }

   
    
    @Test
    @DisplayName("Prueba para escribirReserva")
    public void testEscribirReserva() throws IOException, ParseException {
        // Configuración de la prueba
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        Date fechaEntrega = dateFormat.parse("11/12/2023/12/30");
        Date fechaRetorno = dateFormat.parse("13/12/2023/12/30");
        reserva = new Reserva("pequeño", fechaEntrega, fechaRetorno, "Laura", "PQR987", "Sucursal Sur", "Sucursal Norte", "Reservado");

        // Llama al método que estás probando
        vehiculoRentalSystem.escribirReserva(reserva);

        // Verifica que la reserva se haya escrito correctamente al final del archivo
        try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Reservas"))) {
            String lastLine = null;
            String line;

            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }

            assertNotNull(lastLine, "El archivo está vacío"); 

            // Verifica que la última línea del archivo coincida con la nueva reserva
            String[] reservaInfo = lastLine.split(",");
            assertEquals(reserva.getCategoria(), reservaInfo[0].trim());
            assertEquals(reserva.getIdSedeRecoger(), reservaInfo[1].trim());
            assertEquals(reserva.getIdSedeDevolver(), reservaInfo[2].trim());
            assertEquals(dateFormat.format(reserva.getFechaEntrega()), reservaInfo[3].trim());
            assertEquals(dateFormat.format(reserva.getFechaRetorno()), reservaInfo[4].trim());
            assertEquals(reserva.getCliente(), reservaInfo[5].trim());
            assertEquals(reserva.getIdCarro(), reservaInfo[6].trim());
            assertEquals(String.valueOf(reserva.getPrecioBase()), reservaInfo[7].trim());
            assertEquals(String.valueOf(reserva.getPrecioAbonado()), reservaInfo[8].trim());
            assertEquals(reserva.getEstado().trim(), reservaInfo[9].trim());
        }
    }

    
    @Test
    @DisplayName("Prueba para modificarFechaReserva")
    public void testModificarFechaReserva() throws IOException, ParseException {
        // Configuración de la prueba
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        Date fechaEntrega = dateFormat.parse("11/12/2023/12/30");
        Date fechaRetorno = dateFormat.parse("14/12/2023/12/30");
        Date nuevaFecha = dateFormat.parse("07/12/2023/13/20");
        reserva = new Reserva("pequeño", fechaEntrega, fechaRetorno, "Laura", "PQR987", "Sucursal Sur", "Sucursal Norte", "Reservado");
        // Guarda la reserva original en el archivo
        vehiculoRentalSystem.escribirReserva(reserva);

        // Llama al método que estás probando
        vehiculoRentalSystem.modificarFechaReserva(reserva, nuevaFecha, "entrega");

        // Verifica que la fecha de entrega se haya modificado correctamente en el archivo
        try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Reservas"))) {
            boolean fechaModificada = false;
            String line;

            while ((line = reader.readLine()) != null) {
                String[] reservaInfo = line.split(",");
                if (reserva.getCliente().equals(reservaInfo[5].trim())) {
                    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
                    String fechaModificadaStr = date.format(new Date()).trim();

                    if ("entrega".equals("entrega")) {
                        assertEquals(fechaModificadaStr, reservaInfo[3].trim(), fechaModificadaStr);
                        fechaModificada = true;
                        break;
                    }
                }
            }

            assertTrue(fechaModificada, "Fecha no encontrada o no modificada en las reservas");
        }
    }

 
 
    @Test
    @DisplayName("Prueba para modificarPrecioeserva")
    public void testModificarPrecioReserva() throws IOException, ParseException {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        Date fechaEntrega = dateFormat.parse("11/12/2023/12/30");
        Date fechaRetorno = dateFormat.parse("14/12/2023/12/30");
        Date nuevaFecha = dateFormat.parse("07/12/2023/13/17");
        reserva = new Reserva("pequeño", fechaEntrega, fechaRetorno, "Laura", "PQR987", "Sucursal Sur", "Sucursal Norte", "Reservado");
        // Guarda la reserva original en el archivo
        vehiculoRentalSystem.escribirReserva(reserva);
 
        String cliente = "Laura";


        vehiculoRentalSystem.modificarPrecioReserva(reserva, 150.0, cliente);


        try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Reservas"))) {
            String line;
            boolean clienteEncontrado = false;

            while ((line = reader.readLine()) != null) {
                String[] infoReserva = line.split(",");

                if (reserva.getCliente().equals(cliente)) {
               
                    assertEquals(String.valueOf(150.0), infoReserva[7]);
                    clienteEncontrado = true;
                    break;
                }
            }


            assertTrue(clienteEncontrado, "Cliente no encontrado en las reservas");
        }
    }
   
    
    @Test
    @DisplayName("Prueba para modificarSedeReserva")
    public void testModificarSedeReserva() throws IOException, ParseException {
     
        String sedeNueva = "Sucursal Central";
        String entregaOretorno = "entrega";
        String cliente = "Laura";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        Date fechaEntrega = dateFormat.parse("11/12/2023/12/30");
        Date fechaRetorno = dateFormat.parse("14/12/2023/12/30");
        Date nuevaFecha = dateFormat.parse("07/12/2023/13/17");
        reserva = new Reserva("pequeño", fechaEntrega, fechaRetorno, "Laura", "PQR987", "Sucursal Sur", "Sucursal Norte", "Reservado");
        // Guarda la reserva original en el archivo
        vehiculoRentalSystem.escribirReserva(reserva);


        vehiculoRentalSystem.modificarSedeReserva(reserva, sedeNueva, entregaOretorno, cliente);


        try (BufferedReader reader = new BufferedReader(new FileReader("InventarioDatos/Reservas"))) {
            String line;
            boolean clienteEncontrado = false;

            while ((line = reader.readLine()) != null) {
                String[] infoReserva = line.split(",");

                if (reserva.getCliente().equals(cliente)) {
         
                    if (entregaOretorno.equals("entrega")) {
                        assertEquals(sedeNueva, infoReserva[1]); // Si es entrega
                    } else {
                        assertEquals(sedeNueva, infoReserva[6]); // Si es retorno
                    }
                    clienteEncontrado = true;
                    break;
                }
            }

            // Verifica que se haya encontrado el cliente en las reservas
            assertTrue(clienteEncontrado, "Cliente no encontrado en las reservas");
        }
    }
  
  

    private static void copiarArchivo(String origen, String destino) throws IOException {
        // Copia el contenido de un archivo a otro
        try (BufferedReader reader = new BufferedReader(new FileReader(origen));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destino))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

}