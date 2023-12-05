package SistemaAlquiler;

public class Empleado extends Usuario{
	
	private String sede;
	private String nombre;
	
	 /**
     * Constructor de la clase Empleado.
     *
     * @param sede      La sede a la que está asignado el empleado.
     * @param login     El nombre de usuario del empleado.
     * @param contrasena La contraseña del empleado.
     */
	public Empleado(String sede, String login, String contrasena, String nombre) {
		super(login, contrasena);
		this.sede = sede;
		this.nombre = nombre;
	}
	
    /**
     * Obtiene la sede a la que está asignado el empleado.
     *
     * @return La sede del empleado.
    */
	public String getSede() {
		return sede;
	}
	
	 /**
     * Establece la sede a la que está asignado el empleado.
     *
     * @param sede La sede del empleado.
     */
	public void setSede(String sede) {
		this.sede = sede;
	}
	
	public String getNombre() {
		return nombre;
	}
}
