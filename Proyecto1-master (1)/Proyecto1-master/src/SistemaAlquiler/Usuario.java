package SistemaAlquiler;

class Usuario {
    private String login;
    private String contrasena;

    /**
     * Constructor para crear un nuevo usuario con un nombre de usuario (login) y contraseña.
     *
     * @param login       El nombre de usuario (login).
     * @param contrasena  La contraseña del usuario.
     */
    public Usuario(String login, String contrasena) {
        this.login = login;
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el nombre de usuario (login) del usuario.
     *
     * @return El nombre de usuario (login) del usuario.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contraseña) {
    	this.contrasena = contraseña;
    }
    
    public void setLogin(String login) {
    	this.login = login;
    }
    
}	