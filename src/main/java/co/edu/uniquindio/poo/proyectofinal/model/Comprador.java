package co.edu.uniquindio.poo.proyectofinal.model;

public class Comprador extends Usuario {

    public Comprador(int id, String nombre, String identificacion, String telefono, String correo) {
        super(id, nombre, identificacion, telefono, correo);
    }

    @Override
    public String getTipoUsuario() {
        return "Comprador";
    }

    // Ejemplo de polimorfismo (puede tener comportamiento diferente en el futuro)
    public void realizarOferta() {
        System.out.println("El comprador " + getNombre() + " está realizando una oferta...");
    }
}