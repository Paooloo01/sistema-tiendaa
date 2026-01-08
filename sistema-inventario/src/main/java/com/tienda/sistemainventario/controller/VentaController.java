package com.tienda.sistemainventario.controller;


import com.tienda.sistemainventario.entity.Producto;
import com.tienda.sistemainventario.service.ProductoService;
import com.tienda.sistemainventario.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vender")
@SessionAttributes("carrito") // ¡TRUCO! Esto mantiene el carrito vivo mientras navegas
public class VentaController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    // Inicializamos el carrito vacío si no existe
    @ModelAttribute("carrito")
    public List<ItemCarrito> inicializarCarrito() {
        return new ArrayList<>();
    }

    // 1. PANTALLA DE VENTAS
    @GetMapping
    public String interfazVenta(Model model, @ModelAttribute("carrito") List<ItemCarrito> carrito) {
        model.addAttribute("productos", productoService.listarTodos());

        // Calcular total
        double total = carrito.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
        model.addAttribute("total", total);

        return "vender"; // Buscaremos vender.html
    }

    // 2. AGREGAR PRODUCTO AL CARRITO
    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam Long id, @RequestParam Integer cantidad, @ModelAttribute("carrito") List<ItemCarrito> carrito) {
        Producto producto = productoService.buscarPorId(id);

        if (producto != null) {
            carrito.add(new ItemCarrito(producto, cantidad));
        }
        return "redirect:/vender";
    }

    // 3. LIMPIAR CARRITO
    @GetMapping("/limpiar")
    public String limpiarCarrito(@ModelAttribute("carrito") List<ItemCarrito> carrito) {
        carrito.clear();
        return "redirect:/vender";
    }

    // 4. FINALIZAR VENTA (EL MOMENTO DE LA VERDAD)
    @GetMapping("/finalizar")
    public String finalizarVenta(@ModelAttribute("carrito") List<ItemCarrito> carrito) {
        // Convertimos los items del carrito a una lista de productos simple para el servicio
        List<Producto> productosParaVenta = new ArrayList<>();

        for (ItemCarrito item : carrito) {
            for (int i = 0; i < item.getCantidad(); i++) {
                productosParaVenta.add(item.getProducto());
            }
        }

        // Llamamos a tu servicio inteligente que descuenta stock
        ventaService.registrarVenta(productosParaVenta);

        // Limpiamos carrito y volvemos
        carrito.clear();
        return "redirect:/vender?exito=true";
    }
}