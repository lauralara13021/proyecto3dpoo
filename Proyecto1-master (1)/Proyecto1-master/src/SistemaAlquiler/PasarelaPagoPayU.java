package SistemaAlquiler;

import java.util.Date;
import java.time.LocalDate;

public class PasarelaPagoPayU implements IPasarelaDePago {

	@Override
	public boolean procesarPago(String numeroTarjeta, String nombreTitular, double monto, Date fechaVencimiento) {
		LocalDate fechaLocalDate = LocalDate.now();
        // Convierte la fecha de tipo LocalDate a Date
        Date fechaActuDate = java.sql.Date.valueOf(fechaLocalDate);
		int tamanioCadena = numeroTarjeta.length();
		if (tamanioCadena == 16 && fechaVencimiento.after(fechaActuDate)&& monto>0) {
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
