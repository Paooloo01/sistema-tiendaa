package com.tienda.sistemainventario.controller;


import com.tienda.sistemainventario.entity.Producto;
import com.tienda.sistemainventario.entity.Venta;
import com.tienda.sistemainventario.service.ProductoService;
import com.tienda.sistemainventario.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    // --- PRODUCTOS ---

    @GetMapping("/productos")
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    @PostMapping("/productos")
    public Producto guardar(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    // --- VENTAS ---

    // Este es el botón "COBRAR" de tu tienda
    @PostMapping("/ventas")
    public Venta realizarVenta(@RequestBody List<Producto> productos) {
        return ventaService.registrarVenta(productos);
    }


}