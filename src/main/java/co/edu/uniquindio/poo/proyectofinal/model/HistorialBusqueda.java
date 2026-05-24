package co.edu.uniquindio.poo.proyectofinal.model;

import java.time.LocalDate;

public record HistorialBusqueda(
        Comprador comprador,
        FiltroBusqueda filtroBusqueda,
        LocalDate fecha
) {
}