package Pruebas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import SistemaAlquiler.Reserva;
import SistemaAlquiler.Seguro;
import SistemaAlquiler.VehiculoRentalSystem;


@DisplayName("Pruebas para la creación de reservas")
public class ReservaTest {
    private Reserva reserva;

    @BeforeEach
    public void setUp() throws ParseException {
        // Configuración común para las pruebas
        String categoria = "Económico";
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
    @DisplayName("Pruebas método getCategoria()")
    public void testGetCategoria() {
        assertEquals("Económico", reserva.getCategoria());
    }

    @Test
    @DisplayName("Pruebas método getFechaEntrega()")
    public void testGetFechaEntrega() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        String expectedDate = "05/12/2023/12/00";
        assertEquals(expectedDate, dateFormat.format(reserva.getFechaEntrega()));
    }

    // Agrega más pruebas para los demás métodos de la clase Reserva
}