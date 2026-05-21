package co.edu.uniquindio.poo.proyectofinal.model;

public class FiltroBusqueda {
    private String ciudad;
    private TipoInmueble tipoInmueble;
    private double precioMinimo;
    private double precioMaximo;
    private double areaMinima;

    public FiltroBusqueda(String ciudad, TipoInmueble tipoInmueble,
                          double precioMinimo, double precioMaximo, double areaMinima) {
        this.ciudad = ciudad;
        this.tipoInmueble = tipoInmueble;
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.areaMinima = areaMinima;
    }

    public String getCiudad() { return ciudad; }
    public TipoInmueble getTipoInmueble() { return tipoInmueble; }
    public double getPrecioMinimo() { return precioMinimo; }
    public double getPrecioMaximo() { return precioMaximo; }
    public double getAreaMinima() { return areaMinima; }
}