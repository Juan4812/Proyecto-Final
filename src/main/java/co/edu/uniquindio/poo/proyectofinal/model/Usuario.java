package co.edu.uniquindio.poo.proyectofinal.model;

public abstract class Usuario {

    //Atributos

    protected String nombre;
    protected String identificacion;
    protected String telefono;
    protected String correo;
    protected int puntosReputacion;
    protected String clasificacion;

    //Constructor
    public Usuario(String nombre, String identificacion, String telefono, String correo, int puntosReputacion, String clasificacion) {
        validarTexto(nombre, "El nombre es obligatorio");
        validarTexto(identificacion, "La identificacion es obligatoria");
        validarTexto(telefono, "El telefono es obligatorio");
        validarTexto(correo, "El correo es obligatorio");


        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.puntosReputacion = 0;
        this.clasificacion = clasificacion;
    }

    //Metodossssss

    //Get and Set
    public String getNombre() {
        return nombre; }
    public void setNombre(String nombre) {
        validarTexto(nombre, "El nombre es obligatorio");
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion; }

    public String getTelefono() {
        return telefono; }
    public void setTelefono(String telefono) {
        validarTexto(telefono, "El telefono es obligatorio");
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo; }
    public void setCorreo(String correo) {
        validarTexto(correo, "El correo es obligatorio");
        this.correo = correo;
    }

    public int getPuntosReputacion() {
        return puntosReputacion; }

    public String getClasificacion() {
        return clasificacion; }
    public void setClasificacion(String clasificacion){
        this.clasificacion = clasificacion;
    }

    public abstract String getTipoUsuario();

    // Metodo abstracto
    public abstract int calcularBeneficio();

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

    //ToString
    @Override
    public String toString() {
        return nombre + " - " + getTipoUsuario() + " - " + clasificacion + " (" + puntosReputacion + " pts)";
    }
}