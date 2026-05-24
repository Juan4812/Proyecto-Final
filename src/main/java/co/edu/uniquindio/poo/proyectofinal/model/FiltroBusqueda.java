package co.edu.uniquindio.poo.proyectofinal.model;

public record FiltroBusqueda(
        String ciudad,
        TipoInmueble tipoInmueble,
        double precioMinimo,
        double precioMaximo,
        double areaMinima,
        TipoOperacion tipoOperacion
) {
}