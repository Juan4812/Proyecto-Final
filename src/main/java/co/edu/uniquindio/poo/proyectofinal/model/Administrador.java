package co.edu.uniquindio.poo.proyectofinal.model;

public class Administrador {

    private String nombre;
    private String usuario;
    private String contrasena;

    public Administrador(String nombre, String usuario, String contrasena) {
        validarTexto(nombre, "El nombre es obligatorio");
        validarTexto(usuario, "El usuario es obligatorio");
        validarContrasenaInicial(contrasena);

        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public boolean tieneCredenciales(String usuario, String contrasena) {
        return this.usuario.equals(usuario) && this.contrasena.equals(contrasena);
    }

    public void cambiarContrasena(String nuevaContrasena, String confirmacionContrasena) {
        validarTexto(nuevaContrasena, "La nueva contrasena es obligatoria");
        validarTexto(confirmacionContrasena, "La confirmacion de contrasena es obligatoria");

        if (!nuevaContrasena.equals(confirmacionContrasena)) {
            throw new IllegalArgumentException("Las contrasenas no coinciden");
        }
        if (nuevaContrasena.equals(contrasena)) {
            throw new IllegalArgumentException("La nueva contrasena no puede ser igual a la anterior");
        }
        if (nuevaContrasena.length() <= 4) {
            throw new IllegalArgumentException("La contrasena debe tener mas de 4 caracteres");
        }

        contrasena = nuevaContrasena;
    }

    private void validarContrasenaInicial(String contrasena) {
        validarTexto(contrasena, "La contrasena es obligatoria");
    }

    private void validarTexto(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    @Override
    public String toString() {
        return nombre + " - Administrador";
    }
}