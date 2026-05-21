package co.edu.uniquindio.poo.proyectofinal.model;

public class Inmueble {
    private int codigo;
    private TipoInmueble tipo;
    private String direccion;
    private String ciudad;
    private double area;
    private double precio;
    private EstadoInmueble estado;
    private Vendedor vendedor;

    public Inmueble(int codigo, TipoInmueble tipo, String direccion, String ciudad,
                    double area, double precio, Vendedor vendedor) {
        if (tipo == null) throw new IllegalArgumentException("El tipo de inmueble es obligatorio");
        if (direccion == null || direccion.trim().isEmpty()) throw new IllegalArgumentException("La direccion es obligatoria");
        if (ciudad == null || ciudad.trim().isEmpty()) throw new IllegalArgumentException("La ciudad es obligatoria");
        if (area <= 0) throw new IllegalArgumentException("El area debe ser mayor a 0");
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser mayor a 0");
        if (vendedor == null) throw new IllegalArgumentException("El vendedor es obligatorio");

        this.codigo = codigo;
        this.tipo = tipo;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.area = area;
        this.precio = precio;
        this.estado = EstadoInmueble.DISPONIBLE;
        this.vendedor = vendedor;
    }

    public int getCodigo() { return codigo; }
    public TipoInmueble getTipo() { return tipo; }
    public String getDireccion() { return direccion; }
    public String getCiudad() { return ciudad; }
    public double getArea() { return area; }
    public double getPrecio() { return precio; }
    public EstadoInmueble getEstado() { return estado; }
    public Vendedor getVendedor() { return vendedor; }

    public void setPrecio(double precio) {
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser mayor a 0");
        this.precio = precio;
    }

    public void cambiarEstado(EstadoInmueble estado) {
        if (estado == null) throw new IllegalArgumentException("El estado es obligatorio");
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return estado == EstadoInmueble.DISPONIBLE;
    }

    @Override
    public String toString() {
        return codigo + " - " + tipo + " en " + ciudad + " - $" + precio + " - " + estado;
    }
}