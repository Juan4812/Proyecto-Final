package co.edu.uniquindio.poo.proyectofinal.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InmobiliariaTest {

    private Inmobiliaria crearInmobiliaria() {
        return new Inmobiliaria("InmoSmart", "123456789");
    }

    @Test
    void registrarUsuariosGuardaEnListaGeneralYFiltraPorTipo() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();

        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com", 0, "Principiante");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com", 0, "Principiante");

        assertEquals(2, inmobiliaria.getListaUsuarios().size());
        assertTrue(inmobiliaria.getListaUsuarios().contains(comprador));
        assertTrue(inmobiliaria.getListaUsuarios().contains(vendedor));
        assertEquals(1, inmobiliaria.getListaCompradores().size());
        assertEquals(1, inmobiliaria.getListaVendedores().size());
        assertSame(comprador, inmobiliaria.getListaCompradores().get(0));
        assertSame(vendedor, inmobiliaria.getListaVendedores().get(0));
    }

    @Test
    void noPermiteRegistrarUsuariosRepetidosAunqueSeanDeTipoDiferente() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        inmobiliaria.registrarComprador("Ana Perez", "1001", "3001111111", "ana@mail.com", 0, "Principiante");

        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.registrarVendedor(
                "Luis Gomez", "1001", "3002222222", "luis@mail.com", 0, "Principiante"));
    }

    @Test
    void registrarInmuebleValidaVendedorRegistradoYDatosDelInmueble() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com", 0, "Principiante");
        Vendedor vendedorNoRegistrado = new Vendedor(
                "Marta Ruiz", "2002", "3003333333", "marta@mail.com", 0, "Principiante");

        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);

        assertEquals(1, inmobiliaria.getListaInmuebles().size());
        assertSame(inmueble, inmobiliaria.getListaInmuebles().get(0));
        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.registrarInmueble(
                2, "Carrera 5 # 10-20", "Armenia", 70, 220000000,
                TipoInmueble.CASA, EstadoInmueble.DISPONIBLE, vendedorNoRegistrado));
        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.registrarInmueble(
                3, "Calle 12 # 8-15", "Pereira", 0, 220000000,
                TipoInmueble.CASA, EstadoInmueble.DISPONIBLE, vendedor));
    }

    @Test
    void publicarInmuebleValidaExistenciaPertenenciaYDisponibilidad() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com", 0, "Principiante");
        Vendedor otroVendedor = inmobiliaria.registrarVendedor(
                "Marta Ruiz", "2002", "3003333333", "marta@mail.com", 0, "Principiante");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);

        Publicacion publicacion = inmobiliaria.publicarInmueble(
                vendedor, inmueble, "Apartamento cerca al centro", TipoOperacion.VENTA);

        assertEquals(1, inmobiliaria.getListaPublicaciones().size());
        assertSame(publicacion, inmobiliaria.getListaPublicaciones().get(0));
        assertEquals(10, vendedor.getPuntosReputacion());
        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.publicarInmueble(
                otroVendedor, inmueble, "Publicacion invalida", TipoOperacion.VENTA));

        inmueble.cambiarEstado(EstadoInmueble.VENDIDO);
        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.publicarInmueble(
                vendedor, inmueble, "Ya no disponible", TipoOperacion.VENTA));
    }

    @Test
    void buscarInmueblesAplicaFiltrosDeCiudadTipoPrecioYArea() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com", 0, "Principiante");
        Inmueble armeniaApartamento = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);
        inmobiliaria.registrarInmueble(
                2, "Carrera 7 # 5-40", "Pereira", 120, 410000000,
                TipoInmueble.CASA, EstadoInmueble.DISPONIBLE, vendedor);
        inmobiliaria.registrarInmueble(
                3, "Avenida 1 # 2-30", "Armenia", 50, 180000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.VENDIDO, vendedor);

        ArrayList<Inmueble> resultado = inmobiliaria.buscarInmuebles(
                new FiltroBusqueda("Armenia", TipoInmueble.APARTAMENTO, 200000000, 300000000, 70));

        assertEquals(1, resultado.size());
        assertSame(armeniaApartamento, resultado.get(0));
    }

    @Test
    void realizarYRechazarOfertaActualizaEstadoReputacionYAlertas() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com", 0, "Principiante");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com", 0, "Principiante");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);

        Oferta oferta = inmobiliaria.realizarOferta(comprador, inmueble, 230000000);
        inmobiliaria.rechazarOferta(oferta);

        assertEquals(EstadoOferta.RECHAZADA, oferta.getEstado());
        assertEquals(5, comprador.getPuntosReputacion());
        assertEquals(2, inmobiliaria.getListaAlertas().size());
        assertSame(vendedor, inmobiliaria.getListaAlertas().get(0).getUsuario());
        assertSame(comprador, inmobiliaria.getListaAlertas().get(1).getUsuario());
    }

    @Test
    void aceptarOfertaCreaTransaccionCambiaEstadoYRechazaOtrasOfertasPendientes() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador compradorAceptado = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com", 0, "Principiante");
        Comprador compradorRechazado = inmobiliaria.registrarComprador(
                "Carlos Rios", "1002", "3004444444", "carlos@mail.com", 0, "Principiante");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com", 0, "Principiante");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);
        Oferta ofertaAceptada = inmobiliaria.realizarOferta(compradorAceptado, inmueble, 240000000);
        Oferta ofertaRechazada = inmobiliaria.realizarOferta(compradorRechazado, inmueble, 245000000);

        Transaccion transaccion = inmobiliaria.aceptarOferta(ofertaAceptada, TipoOperacion.VENTA);

        assertEquals(1, inmobiliaria.getListaTransacciones().size());
        assertSame(transaccion, inmobiliaria.getListaTransacciones().get(0));
        assertEquals(EstadoOferta.ACEPTADA, ofertaAceptada.getEstado());
        assertEquals(EstadoOferta.RECHAZADA, ofertaRechazada.getEstado());
        assertEquals(EstadoInmueble.VENDIDO, inmueble.getEstado());
        assertEquals(155, compradorAceptado.getPuntosReputacion());
        assertEquals(100, vendedor.getPuntosReputacion());
    }

    @Test
    void recomendarInmueblesUsaHistorialDelComprador() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com", 0, "Principiante");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com", 0, "Principiante");
        Inmueble referencia = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);
        Inmueble recomendado = inmobiliaria.registrarInmueble(
                2, "Carrera 15 # 7-20", "Armenia", 75, 260000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);
        inmobiliaria.registrarInmueble(
                3, "Calle 50 # 11-10", "Pereira", 90, 270000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);
        inmobiliaria.realizarOferta(comprador, referencia, 240000000);

        ArrayList<Inmueble> recomendaciones = inmobiliaria.recomendarInmuebles(comprador);

        assertEquals(1, recomendaciones.size());
        assertSame(recomendado, recomendaciones.get(0));
    }

    @Test
    void generarReporteGeneralIncluyeEstadisticasPrincipales() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com", 0, "Principiante");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com", 0, "Principiante");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, EstadoInmueble.DISPONIBLE, vendedor);
        inmobiliaria.publicarInmueble(vendedor, inmueble, "Apartamento cerca al centro", TipoOperacion.VENTA);
        inmobiliaria.realizarOferta(comprador, inmueble, 230000000);

        String reporte = inmobiliaria.generarReporteGeneral();

        assertTrue(reporte.contains("Total compradores: 1"));
        assertTrue(reporte.contains("Total vendedores: 1"));
        assertTrue(reporte.contains("Total inmuebles: 1"));
        assertTrue(reporte.contains("Total publicaciones: 1"));
        assertTrue(reporte.contains("Total ofertas: 1"));
        assertTrue(reporte.contains("Comprador mas activo: Ana Perez"));
        assertTrue(reporte.contains("Vendedor con mas propiedades: Luis Gomez"));
        assertTrue(reporte.contains("Ciudad con mayor demanda: Armenia"));
    }
}
