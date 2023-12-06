package SistemaAlquiler;

import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PasarelaPagoPayU implements IPasarelaDePago {

	@Override
	public boolean procesarPago(String numeroTarjeta, String nombreTitular, double monto, Date fechaVencimiento) {
		LocalDate fechaLocalDate = LocalDate.now();
        // Convierte la fecha de tipo LocalDate a Date
        Date fechaActuDate = java.sql.Date.valueOf(fechaLocalDate);
		int tamanioCadena = numeroTarjeta.length();
		if (tamanioCadena == 16 && fechaVencimiento.after(fechaActuDate)&& monto>0) {
			String archivoTrazas = "InventarioDatos/trazas_transacciones";

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTrazas, true))) {
	            // Obtener la fecha y hora actual
	            LocalDateTime fechaHoraActual = LocalDateTime.now();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            String fechaHoraFormateada = fechaHoraActual.format(formatter);
	            String traza = String.format("Fecha/Hora: %s - Opción de Pago: %s - Monto: %s - Titular: %s - Terminacion Tarjeta: %s Resultado: %s%n",
	                    fechaHoraFormateada, "PayPal", monto, nombreTitular, numeroTarjeta.substring(numeroTarjeta.length() - 4), "Aprobada");

	            // Escribir la traza en el archivo
	            writer.write(traza);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return true;
		}
		else {
			return false;
		}
	}
	@Override
	public boolean bloquearCupo(String numeroTarjeta, double monto) {
		int tamanioCadena = numeroTarjeta.length();
		if (tamanioCadena == 16&& monto>0) {
			return true;
		}
		else {
			return false;
		}
		
	}

}