package Consola;

import java.sql.Date;

public interface IpasarelaDePago {
    boolean procesarPago(String numeroTarjeta, String nombreTitular, double monto, Date fechaVencimiento);

    boolean bloquearCupo(String numeroTarjeta, double monto);
}
