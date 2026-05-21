package co.edu.uniquindio.poo.proyectofinal.model;

public abstract class Usuario {
    private int id;
    private String nombre;
    private String identificacion;
    private String telefono;
    private String correo;
    private int puntosReputacion;
    private String clasificacion;

    public Usuario(int id, String nombre, String identificacion, String telefono, String correo) {
        validarTexto(nombre, "El nombre es obligatorio");
        validarTexto(identificacion, "La identificacion es obligatoria");
        validarTexto(telefono, "El telefono es obligatorio");
        validarTexto(correo, "El correo es obligatorio");

        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.puntosReputacion = 0;
        this.clasificacion = "Principiante";
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public int getPuntosReputacion() { return puntosReputacion; }
    public String getClasificacion() { return clasificacion; }

    public void setNombre(String nombre) {
        validarTexto(nombre, "El nombre es obligatorio");
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        validarTexto(telefono, "El telefono es obligatorio");
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        validarTexto(correo, "El correo es obligatorio");
        this.correo = correo;
    }

    public void sumarReputacion(int puntos) {
        if (puntos <= 0) {
            throw new IllegalArgumentException("Los puntos deben ser mayores a 0");
        }
        puntosReputacion += puntos;
        actualizarClasificacion();
    }

    private void actualizarClasificacion() {
        if (puntosReputacion <= 100) {
            clasificacion = "Principiante";
        } else if (puntosReputacion <= 500) {
            clasificacion = "Inversionista";
        } else if (puntosReputacion <= 2000) {
            clasificacion = "Experto Inmobiliario";
        } else {
            clasificacion = "Magnate Inmobiliario";
        }
    }

    protected void validarTexto(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    public abstract String getTipoUsuario();

    public abstract int calcularBeneficio();

    @Override
    public String toString() {
        return nombre + " - " + getTipoUsuario() + " - " + clasificacion + " (" + puntosReputacion + " pts)";
    }
}