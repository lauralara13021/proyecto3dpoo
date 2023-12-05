package SistemaAlquiler;

import java.util.Date;

public interface IPasarelaDePago {
	/**
     * Procesa un pago simulado con la tarjeta de crédito.
     *
     * @param numeroTarjeta Número de la tarjeta de crédito.
     * @param nombreTitular Nombre del titular de la tarjeta.
     * @param monto Monto del pago.
     * @return true si el pago fue exitoso, false en caso contrario.
     */
    boolean procesarPago(String numeroTarjeta, String nombreTitular, double monto, Date fechaVencimiento);

    /**
     * Bloquea el cupo en la tarjeta de crédito simuladamente.
     *
     * @param numeroTarjeta Número de la tarjeta de crédito.
     * @param monto Monto a bloquear.
     * @return true si el bloqueo fue exitoso, false en caso contrario.
     */
    boolean bloquearCupo(String numeroTarjeta, double monto);
}
