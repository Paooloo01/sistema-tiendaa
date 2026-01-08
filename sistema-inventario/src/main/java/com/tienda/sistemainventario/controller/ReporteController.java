package com.tienda.sistemainventario.controller;

import com.tienda.sistemainventario.entity.DetalleVenta;
import com.tienda.sistemainventario.entity.Venta;
import com.tienda.sistemainventario.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping
    public String verReporte(Model model, @RequestParam(defaultValue = "mes") String periodo) {

        LocalDateTime inicio;
        LocalDateTime fin = LocalDateTime.now();

        // 1. LÓGICA DE FILTROS DE TIEMPO
        switch (periodo) {
            case "hoy":
                inicio = LocalDate.now().atStartOfDay(); // Desde las 00:00 de hoy
                break;
            case "semana":
                inicio = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1).atStartOfDay(); // Lunes de esta semana
                break;
            case "mes":
                inicio = LocalDate.now().withDayOfMonth(1).atStartOfDay(); // Día 1 de este mes
                break;
            default: // "total"
                inicio = LocalDateTime.of(2000, 1, 1, 0, 0); // Desde siempre
                break;
        }

        // Buscamos las ventas en ese rango
        List<Venta> ventas = ventaRepository.findByFechaBetween(inicio, fin);

        // 2. CÁLCULO DE TOTALES
        double totalVendido = 0;
        double totalGanancia = 0;

        // Mapa para contar productos: "Oso" -> 5 vendidos, "Chocolate" -> 10 vendidos
        Map<String, Integer> rankingProductos = new HashMap<>();

        for (Venta v : ventas) {
            totalVendido += v.getTotalVenta();
            totalGanancia += v.getGananciaTotal();

            // Recorremos los detalles para ver qué productos se vendieron
            for (DetalleVenta item : v.getItems()) {
                String nombreProd = item.getProducto().getNombre();
                int cantidad = item.getCantidad();

                // Sumamos al contador del ranking
                rankingProductos.put(nombreProd, rankingProductos.getOrDefault(nombreProd, 0) + cantidad);
            }
        }

        // 3. ORDENAR EL RANKING (Top 5 más vendidos)
        List<Map.Entry<String, Integer>> topProductos = rankingProductos.entrySet().stream()
                .sorted((k1, k2) -> k2.getValue().compareTo(k1.getValue())) // Ordenar de mayor a menor
                .limit(5) // Solo los top 5
                .collect(Collectors.toList());

        // 4. ENVIAR TODO A LA PANTALLA
        model.addAttribute("listaVentas", ventas);
        model.addAttribute("totalVendido", totalVendido);
        model.addAttribute("totalGanancia", totalGanancia);
        model.addAttribute("topProductos", topProductos);
        model.addAttribute("periodoActual", periodo); // Para pintar el botón activo

        return "reportes";
    }
}