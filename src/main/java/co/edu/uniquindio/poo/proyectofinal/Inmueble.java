package co.edu.uniquindio.poo.proyectofinal;

public class Inmueble {
    private int codigo;
    private TipoInmueble tipo;
    private String direccion;
    private String ciudad;
    private double area;
    private double precio;
    private EstadoInmueble estado;
    private Vendedor vendedor;

    public Inmueble(int codigo, TipoInmueble tipo, String direccion, String ciudad, double area, double precio, Vendedor vendedor) {
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

    public void setPrecio(double precio) { this.precio = precio; }
    public void setEstado(EstadoInmueble estado) { this.estado = estado; }

    public void cambiarEstado(EstadoInmueble nuevoEstado) {
        this.estado = nuevoEstado;
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + tipo + " en " + ciudad + " - " + direccion +
               " | Área: " + area + "m² | Precio: $" + precio + " | Estado: " + estado +
               " | Vendedor: " + vendedor.getNombre();
    }
}