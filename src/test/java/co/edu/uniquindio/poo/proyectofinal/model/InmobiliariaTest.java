package co.edu.uniquindio.poo.proyectofinal.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InmobiliariaTest {

    private Inmobiliaria crearInmobiliaria() {
        return new Inmobiliaria("InmoSmart", "123456789");
    }

    private void imprimirPrueba(String nombrePrueba, Object resultadoDado, Object resultadoEsperado) {
        System.out.println("Prueba completa: " + nombrePrueba);
        System.out.println("Resultado dado: " + resultadoDado);
        System.out.println("Resultado esperado: " + resultadoEsperado);
        System.out.println("----------------------------------------");
    }

    @Test
    void registrarUsuariosGuardaEnListaGeneralYFiltraPorTipo() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();

        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");

        assertEquals(2, inmobiliaria.getListaUsuarios().size(),
                "La lista general debe guardar compradores y vendedores.");
        assertEquals(1, inmobiliaria.getListaCompradores().size(),
                "El filtro de compradores debe retornar solo compradores.");
        assertEquals(1, inmobiliaria.getListaVendedores().size(),
                "El filtro de vendedores debe retornar solo vendedores.");
        assertSame(comprador, inmobiliaria.getListaCompradores().get(0),
                "El comprador filtrado debe ser el comprador registrado.");
        assertSame(vendedor, inmobiliaria.getListaVendedores().get(0),
                "El vendedor filtrado debe ser el vendedor registrado.");
        assertEquals(1, comprador.getId(), "El primer usuario debe recibir id 1.");
        assertEquals(2, vendedor.getId(), "El segundo usuario debe recibir id 2.");

        imprimirPrueba(
                "registro de usuarios en lista general",
                "usuarios=" + inmobiliaria.getListaUsuarios().size()
                        + ", compradores=" + inmobiliaria.getListaCompradores().size()
                        + ", vendedores=" + inmobiliaria.getListaVendedores().size(),
                "usuarios=2, compradores=1, vendedores=1"
        );
    }

    @Test
    void noPermiteRegistrarUsuariosRepetidosAunqueSeanDeTipoDiferente() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        inmobiliaria.registrarComprador("Ana Perez", "1001", "3001111111", "ana@mail.com");

        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> inmobiliaria.registrarVendedor("Luis Gomez", "1001", "3002222222", "luis@mail.com"),
                "No se debe permitir la misma identificacion en comprador y vendedor.");

        imprimirPrueba(
                "validacion de usuario repetido",
                excepcion.getMessage(),
                "El usuario ya esta registrado"
        );
    }

    @Test
    void registrarInmuebleValidaVendedorRegistradoYDatosDelInmueble() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Vendedor vendedorNoRegistrado = new Vendedor(
                99, "Marta Ruiz", "2002", "3003333333", "marta@mail.com");

        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);

        assertEquals(1, inmobiliaria.getListaInmuebles().size(),
                "Debe registrarse un inmueble valido.");
        assertSame(inmueble, inmobiliaria.getListaInmuebles().get(0),
                "El inmueble guardado debe ser el mismo objeto retornado.");
        assertEquals(EstadoInmueble.DISPONIBLE, inmueble.getEstado(),
                "Todo inmueble nuevo debe quedar disponible.");
        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.registrarInmueble(
                2, "Carrera 5 # 10-20", "Armenia", 70, 220000000,
                TipoInmueble.CASA, vendedorNoRegistrado),
                "No se debe permitir registrar inmuebles con vendedores no registrados.");
        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.registrarInmueble(
                3, "Calle 12 # 8-15", "Pereira", 0, 220000000,
                TipoInmueble.CASA, vendedor),
                "No se debe permitir area menor o igual a cero.");

        imprimirPrueba(
                "registro de inmueble valido",
                "inmuebles=" + inmobiliaria.getListaInmuebles().size()
                        + ", estado=" + inmueble.getEstado(),
                "inmuebles=1, estado=DISPONIBLE"
        );
    }

    @Test
    void noPermiteRegistrarInmueblesConDatosExactamenteIguales() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);

        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> inmobiliaria.registrarInmueble(
                        2, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                        TipoInmueble.APARTAMENTO, vendedor),
                "No se debe permitir registrar dos inmuebles con los mismos datos exactos.");

        imprimirPrueba(
                "validacion de inmuebles duplicados",
                excepcion.getMessage(),
                "Ya existe un inmueble con los mismos datos"
        );
    }

    @Test
    void publicarInmuebleValidaExistenciaPertenenciaDisponibilidadYReputacion() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Vendedor otroVendedor = inmobiliaria.registrarVendedor(
                "Marta Ruiz", "2002", "3003333333", "marta@mail.com");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);

        Publicacion publicacion = inmobiliaria.publicarInmueble(
                vendedor, inmueble, "Apartamento cerca al centro", TipoOperacion.VENTA);

        assertEquals(1, inmobiliaria.getListaPublicaciones().size(),
                "Debe guardarse la publicacion.");
        assertSame(publicacion, inmobiliaria.getListaPublicaciones().get(0),
                "La publicacion guardada debe ser la misma retornada.");
        assertEquals(10, vendedor.getPuntosReputacion(),
                "Publicar inmueble debe sumar 10 puntos al vendedor.");
        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.publicarInmueble(
                otroVendedor, inmueble, "Publicacion invalida", TipoOperacion.VENTA),
                "No se debe publicar un inmueble que pertenece a otro vendedor.");

        inmueble.cambiarEstado(EstadoInmueble.VENDIDO);
        assertThrows(IllegalArgumentException.class, () -> inmobiliaria.publicarInmueble(
                vendedor, inmueble, "Ya no disponible", TipoOperacion.VENTA),
                "No se debe publicar un inmueble no disponible.");

        imprimirPrueba(
                "publicacion de inmueble",
                "publicaciones=" + inmobiliaria.getListaPublicaciones().size()
                        + ", reputacionVendedor=" + vendedor.getPuntosReputacion(),
                "publicaciones=1, reputacionVendedor=10"
        );
    }

    @Test
    void noPermitePublicarElMismoInmuebleDosVeces() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);
        inmobiliaria.publicarInmueble(vendedor, inmueble, "Primera publicacion", TipoOperacion.VENTA);

        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
                () -> inmobiliaria.publicarInmueble(vendedor, inmueble, "Segunda publicacion", TipoOperacion.VENTA),
                "No se debe permitir publicar dos veces el mismo inmueble disponible.");

        imprimirPrueba(
                "validacion de publicacion duplicada",
                excepcion.getMessage(),
                "El inmueble ya esta publicado"
        );
    }

    @Test
    void buscarInmueblesAplicaFiltrosDeCiudadTipoPrecioAreaYOperacion() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Inmueble armeniaApartamento = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);
        Inmueble pereiraCasa = inmobiliaria.registrarInmueble(
                2, "Carrera 7 # 5-40", "Pereira", 120, 410000000,
                TipoInmueble.CASA, vendedor);
        Inmueble armeniaApartamentoVendido = inmobiliaria.registrarInmueble(
                3, "Avenida 1 # 2-30", "Armenia", 50, 180000000,
                TipoInmueble.APARTAMENTO, vendedor);
        inmobiliaria.publicarInmueble(vendedor, armeniaApartamento, "Apartamento en venta", TipoOperacion.VENTA);
        inmobiliaria.publicarInmueble(vendedor, pereiraCasa, "Casa en arriendo", TipoOperacion.ARRIENDO);
        armeniaApartamentoVendido.cambiarEstado(EstadoInmueble.VENDIDO);

        ArrayList<Inmueble> resultado = inmobiliaria.buscarInmuebles(
                new FiltroBusqueda("Armenia", TipoInmueble.APARTAMENTO,
                        200000000, 300000000, 70, TipoOperacion.VENTA));

        assertEquals(1, resultado.size(),
                "Solo debe encontrarse el apartamento disponible que cumple todos los filtros.");
        assertSame(armeniaApartamento, resultado.get(0),
                "El inmueble encontrado debe ser el apartamento de Armenia publicado en venta.");

        imprimirPrueba(
                "busqueda con filtros completos",
                "resultados=" + resultado.size() + ", codigo=" + resultado.get(0).getCodigo(),
                "resultados=1, codigo=1"
        );
    }

    @Test
    void buscarInmueblesConCompradorRegistraHistorialDeBusqueda() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com");
        FiltroBusqueda filtro = new FiltroBusqueda(
                "Armenia", TipoInmueble.APARTAMENTO, 100000000, 300000000, 50, TipoOperacion.VENTA);

        inmobiliaria.buscarInmuebles(comprador, filtro);

        assertEquals(1, inmobiliaria.getListaHistorialBusquedas().size(),
                "Buscar con comprador debe registrar historial.");
        assertSame(comprador, inmobiliaria.getListaHistorialBusquedas().get(0).comprador(),
                "El historial debe pertenecer al comprador que busco.");
        assertSame(filtro, inmobiliaria.getListaHistorialBusquedas().get(0).filtroBusqueda(),
                "El historial debe guardar el filtro usado.");

        imprimirPrueba(
                "historial de busqueda",
                "historiales=" + inmobiliaria.getListaHistorialBusquedas().size(),
                "historiales=1"
        );
    }

    @Test
    void publicarInmuebleSimilarGeneraAlertaSegunHistorialDeBusqueda() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        FiltroBusqueda filtro = new FiltroBusqueda(
                "Armenia", TipoInmueble.APARTAMENTO, 100000000, 300000000, 50, TipoOperacion.VENTA);
        inmobiliaria.buscarInmuebles(comprador, filtro);
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);

        inmobiliaria.publicarInmueble(vendedor, inmueble, "Apartamento que coincide con la busqueda", TipoOperacion.VENTA);

        assertEquals(1, inmobiliaria.getListaAlertas().size(),
                "Publicar un inmueble similar al historial debe generar alerta.");
        assertSame(comprador, inmobiliaria.getListaAlertas().get(0).getUsuario(),
                "La alerta debe enviarse al comprador que hizo la busqueda.");

        imprimirPrueba(
                "alerta por inmueble similar",
                "alertas=" + inmobiliaria.getListaAlertas().size()
                        + ", usuario=" + inmobiliaria.getListaAlertas().get(0).getUsuario().getNombre(),
                "alertas=1, usuario=Ana Perez"
        );
    }

    @Test
    void realizarYRechazarOfertaActualizaEstadoReputacionYAlertas() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);

        Oferta oferta = inmobiliaria.realizarOferta(comprador, inmueble, 230000000);
        inmobiliaria.rechazarOferta(oferta);

        assertEquals(EstadoOferta.RECHAZADA, oferta.getEstado(),
                "La oferta rechazada debe quedar en estado RECHAZADA.");
        assertEquals(5, comprador.getPuntosReputacion(),
                "Realizar oferta debe sumar 5 puntos al comprador.");
        assertEquals(2, inmobiliaria.getListaAlertas().size(),
                "Realizar y rechazar una oferta debe generar dos alertas.");
        assertSame(vendedor, inmobiliaria.getListaAlertas().get(0).getUsuario(),
                "La primera alerta debe ser para el vendedor.");
        assertSame(comprador, inmobiliaria.getListaAlertas().get(1).getUsuario(),
                "La segunda alerta debe ser para el comprador.");

        imprimirPrueba(
                "oferta rechazada",
                "estado=" + oferta.getEstado()
                        + ", reputacionComprador=" + comprador.getPuntosReputacion()
                        + ", alertas=" + inmobiliaria.getListaAlertas().size(),
                "estado=RECHAZADA, reputacionComprador=5, alertas=2"
        );
    }

    @Test
    void aceptarOfertaCreaTransaccionCambiaEstadoReputacionYRechazaPendientes() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador compradorAceptado = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com");
        Comprador compradorRechazado = inmobiliaria.registrarComprador(
                "Carlos Rios", "1002", "3004444444", "carlos@mail.com");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);
        Oferta ofertaAceptada = inmobiliaria.realizarOferta(compradorAceptado, inmueble, 240000000);
        Oferta ofertaRechazada = inmobiliaria.realizarOferta(compradorRechazado, inmueble, 245000000);

        Transaccion transaccion = inmobiliaria.aceptarOferta(ofertaAceptada, TipoOperacion.VENTA);

        assertEquals(1, inmobiliaria.getListaTransacciones().size(),
                "Aceptar una oferta debe crear una transaccion.");
        assertSame(transaccion, inmobiliaria.getListaTransacciones().get(0),
                "La transaccion guardada debe ser la retornada.");
        assertEquals(EstadoOferta.ACEPTADA, ofertaAceptada.getEstado(),
                "La oferta aceptada debe cambiar a ACEPTADA.");
        assertEquals(EstadoOferta.RECHAZADA, ofertaRechazada.getEstado(),
                "Las otras ofertas pendientes del inmueble deben rechazarse.");
        assertEquals(EstadoInmueble.VENDIDO, inmueble.getEstado(),
                "Si la operacion es venta, el inmueble debe quedar vendido.");
        assertEquals(240000000, inmueble.getPrecio(),
                "El inmueble debe mostrar el valor final de la oferta aceptada.");
        assertEquals(155, compradorAceptado.getPuntosReputacion(),
                "Comprador aceptado debe tener 5 + 50 + 100 puntos.");
        assertEquals(100, vendedor.getPuntosReputacion(),
                "Vendedor debe recibir 100 puntos por completar transaccion.");
        assertTrue(inmobiliaria.getListaAlertas().stream()
                        .anyMatch(alerta -> alerta.getUsuario() == compradorRechazado),
                "El comprador rechazado automaticamente debe recibir una alerta.");

        imprimirPrueba(
                "oferta aceptada y transaccion",
                "transacciones=" + inmobiliaria.getListaTransacciones().size()
                        + ", estadoInmueble=" + inmueble.getEstado()
                        + ", precioInmueble=" + String.format(Locale.US, "%.1f", inmueble.getPrecio())
                        + ", reputacionComprador=" + compradorAceptado.getPuntosReputacion()
                        + ", reputacionVendedor=" + vendedor.getPuntosReputacion(),
                "transacciones=1, estadoInmueble=VENDIDO, precioInmueble=240000000.0, reputacionComprador=155, reputacionVendedor=100"
        );
    }

    @Test
    void aceptarOfertaDeArriendoCambiaEstadoAarrendado() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);
        Oferta oferta = inmobiliaria.realizarOferta(comprador, inmueble, 1800000);

        inmobiliaria.aceptarOferta(oferta, TipoOperacion.ARRIENDO);

        assertEquals(EstadoInmueble.ARRENDADO, inmueble.getEstado(),
                "Si la operacion es arriendo, el inmueble debe quedar arrendado.");
        assertEquals(1800000, inmueble.getPrecio(),
                "El inmueble arrendado debe mostrar el valor pactado en la oferta.");
        assertEquals(1, inmobiliaria.obtenerInmueblesArrendados().size(),
                "El reporte de arrendados debe incluir el inmueble.");

        imprimirPrueba(
                "oferta de arriendo aceptada",
                "estadoInmueble=" + inmueble.getEstado()
                        + ", precioInmueble=" + String.format(Locale.US, "%.1f", inmueble.getPrecio())
                        + ", arrendados=" + inmobiliaria.obtenerInmueblesArrendados().size(),
                "estadoInmueble=ARRENDADO, precioInmueble=1800000.0, arrendados=1"
        );
    }

    @Test
    void recomendarInmueblesUsaHistorialDeBusquedaYOfertas() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Inmueble referencia = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);
        Inmueble recomendadoPorOferta = inmobiliaria.registrarInmueble(
                2, "Carrera 15 # 7-20", "Armenia", 75, 260000000,
                TipoInmueble.APARTAMENTO, vendedor);
        Inmueble recomendadoPorHistorial = inmobiliaria.registrarInmueble(
                3, "Avenida 1 # 2-30", "Armenia", 90, 290000000,
                TipoInmueble.APARTAMENTO, vendedor);
        inmobiliaria.registrarInmueble(
                4, "Calle 50 # 11-10", "Pereira", 90, 270000000,
                TipoInmueble.APARTAMENTO, vendedor);
        inmobiliaria.publicarInmueble(vendedor, recomendadoPorHistorial, "Apartamento publicado", TipoOperacion.VENTA);
        inmobiliaria.buscarInmuebles(comprador, new FiltroBusqueda(
                "Armenia", TipoInmueble.APARTAMENTO, 200000000, 300000000, 70, TipoOperacion.VENTA));
        inmobiliaria.realizarOferta(comprador, referencia, 240000000);

        ArrayList<Inmueble> recomendaciones = inmobiliaria.recomendarInmuebles(comprador);

        assertTrue(recomendaciones.contains(recomendadoPorOferta),
                "Debe recomendar inmuebles similares por historial de ofertas.");
        assertTrue(recomendaciones.contains(recomendadoPorHistorial),
                "Debe recomendar inmuebles que coincidan con historial de busqueda.");

        imprimirPrueba(
                "recomendacion de inmuebles",
                "contieneOferta=" + recomendaciones.contains(recomendadoPorOferta)
                        + ", contieneHistorial=" + recomendaciones.contains(recomendadoPorHistorial),
                "contieneOferta=true, contieneHistorial=true"
        );
    }

    @Test
    void generarReporteGeneralYConsultasEstadisticasRetornanValoresEsperados() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Comprador comprador = inmobiliaria.registrarComprador(
                "Ana Perez", "1001", "3001111111", "ana@mail.com");
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);
        inmobiliaria.publicarInmueble(vendedor, inmueble, "Apartamento cerca al centro", TipoOperacion.VENTA);
        inmobiliaria.realizarOferta(comprador, inmueble, 230000000);
        inmobiliaria.aceptarOferta(inmobiliaria.getListaOfertas().get(0), TipoOperacion.VENTA);

        String reporte = inmobiliaria.generarReporteGeneral();

        assertTrue(reporte.contains("Total compradores: 1"),
                "El reporte debe contar compradores.");
        assertTrue(reporte.contains("Total vendedores: 1"),
                "El reporte debe contar vendedores.");
        assertTrue(reporte.contains("Total inmuebles: 1"),
                "El reporte debe contar inmuebles.");
        assertTrue(reporte.contains("Total publicaciones: 1"),
                "El reporte debe contar publicaciones.");
        assertTrue(reporte.contains("Total ofertas: 1"),
                "El reporte debe contar ofertas.");
        assertTrue(reporte.contains("Total transacciones: 1"),
                "El reporte debe contar transacciones.");
        assertSame(comprador, inmobiliaria.obtenerCompradorMasActivo(),
                "El comprador con mas reputacion debe ser Ana.");
        assertSame(vendedor, inmobiliaria.obtenerVendedorConMasPropiedades(),
                "El vendedor con mas propiedades debe ser Luis.");
        assertEquals("Armenia", inmobiliaria.obtenerCiudadConMayorDemanda(),
                "La ciudad con mayor demanda debe ser Armenia.");
        assertEquals(1, inmobiliaria.obtenerInmueblesVendidos().size(),
                "Debe existir un inmueble vendido.");

        imprimirPrueba(
                "reporte general y estadisticas",
                "transacciones=" + inmobiliaria.getListaTransacciones().size()
                        + ", vendidos=" + inmobiliaria.obtenerInmueblesVendidos().size()
                        + ", ciudadDemanda=" + inmobiliaria.obtenerCiudadConMayorDemanda(),
                "transacciones=1, vendidos=1, ciudadDemanda=Armenia"
        );
    }

    @Test
    void cambiarPrecioInmuebleActualizaPrecioYGeneraAlerta() {
        Inmobiliaria inmobiliaria = crearInmobiliaria();
        Vendedor vendedor = inmobiliaria.registrarVendedor(
                "Luis Gomez", "2001", "3002222222", "luis@mail.com");
        Inmueble inmueble = inmobiliaria.registrarInmueble(
                1, "Calle 10 # 20-30", "Armenia", 80, 250000000,
                TipoInmueble.APARTAMENTO, vendedor);

        inmobiliaria.cambiarPrecioInmueble(inmueble, 240000000);

        assertEquals(240000000, inmueble.getPrecio(),
                "El precio del inmueble debe actualizarse.");
        assertEquals(1, inmobiliaria.getListaAlertas().size(),
                "Cambiar precio debe generar una alerta.");
        assertSame(vendedor, inmobiliaria.getListaAlertas().get(0).getUsuario(),
                "La alerta de precio debe enviarse al vendedor.");

        imprimirPrueba(
                "cambio de precio",
                "precio=" + String.format(Locale.US, "%.1f", inmueble.getPrecio())
                        + ", alertas=" + inmobiliaria.getListaAlertas().size(),
                "precio=240000000.0, alertas=1"
        );
    }
}
