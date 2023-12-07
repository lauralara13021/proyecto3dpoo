package Pruebas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import SistemaAlquiler.Conductor;
import SistemaAlquiler.Licencia;
import SistemaAlquiler.Reserva;
import SistemaAlquiler.Seguro;
import SistemaAlquiler.VehiculoRentalSystem;

@DisplayName("Pruebas para la creación de reservas")
public class ReservaTest {
    private Reserva reserva;
    Seguro seguro2;
    Conductor conductorPrueba2;
    VehiculoRentalSystem vehiculo1;

    @BeforeEach
    public void setUp() throws ParseException {
        // Configuración común para las pruebas
        String categoria = "pequeño";
        String idSedeRecoger = "Sede1";
        String idSedeDevolver = "Sede2";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        Date fechaEntrega = (Date) dateFormat.parse("05/12/2023/12/00");
        Date fechaRetorno = (Date) dateFormat.parse("10/12/2023/12/00");
        String cliente = "Laura";
        String idCarro = "ABC123";
        String estado = "Reservado";

        reserva = new Reserva(categoria, fechaEntrega, fechaRetorno, cliente, idCarro, idSedeRecoger, idSedeDevolver, estado);
    }

    

    @Test
    @DisplayName("Prueba para getPrecio")
    public void testGetPrecio() {

        int diasRenta = 5;
        int tarifa = 100;
        int valorSedeDiferente = 50;


        double precio = reserva.getPrecio(diasRenta, tarifa, valorSedeDiferente);

        // Verificación de resultados
        assertEquals(550, precio); 
    }

    @Test
    @DisplayName("Prueba para setPrecio")
    public void testSetPrecio() {
        // Configuración de la prueba
    	 int diasRenta = 5;
         int tarifa = 100;
         int valorSedeDiferente = 50;
        double nuevoPrecio = 550;

        // Ejecución del método a probar
        reserva.setPrecio(nuevoPrecio);

        // Verificación de resultados
        assertEquals(nuevoPrecio, reserva.getPrecio(diasRenta, tarifa, valorSedeDiferente)); 
        }
    @Test
    @DisplayName("Pruebas método agregarSeguro()")
    public void testAgregarSeguro() {
    	seguro2 = new Seguro("Seguro2", 1000);
    	reserva.AgregarSeguro(seguro2);
    	assertTrue(reserva.getSeguros().contains(seguro2));    }
    @Test
    @DisplayName("Pruebas método agregarConductor()")
    public void testAgregarConductor() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
    	Date fechaLicencia2 = (Date) dateFormat.parse("06/12/2010/12/00");
        Licencia licencia2 = new Licencia("321", "Colombia", fechaLicencia2);
        conductorPrueba2 = new Conductor("Isabella", "123456789", "isa@gmail.com", licencia2);
    	reserva.AgregarConductor(conductorPrueba2);
    	assertTrue(reserva.getConductores().contains(conductorPrueba2.getNombre()));
    }
    @Test
    @DisplayName("Pruebas método getPrecioConSeguros()")
    public void testGetPrecioConSeguros() {
    	seguro2 = new Seguro("Seguro2", 1000);
    	reserva.AgregarSeguro(seguro2);
    	int dias = 3;
    	assertEquals(reserva.getPrecioConSeguros(reserva.getPrecioBase(),dias), ((reserva.getPrecioBase())+(seguro2.getCostoPorDia()*dias)));
    	}


}
