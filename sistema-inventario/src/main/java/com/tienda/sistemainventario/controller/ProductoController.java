package com.tienda.sistemainventario.controller;

import com.tienda.sistemainventario.entity.Producto;
import com.tienda.sistemainventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // --- PÁGINA PRINCIPAL DE PRODUCTOS ---
    @GetMapping("/productos")
    public String listar(Model model) {
        List<Producto> lista = productoService.listarTodos();

        // CORRECCIÓN AQUÍ: Usamos "misProductos" porque así lo llama tu HTML
        model.addAttribute("misProductos", lista);

        return "lista-productos"; // Busca el archivo lista-productos.html
    }

    // --- FORMULARIO PARA CREAR PRODUCTO ---
    @GetMapping("/productos/nuevo")
    public String mostrarFormulario(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        return "crear-producto"; // Busca crear-producto.html
    }

    // --- GUARDAR PRODUCTO ---
    @PostMapping("/productos")
    public String guardar(@ModelAttribute("producto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos"; // Al guardar, recargamos la listSCACZCCZCZXZa
    }

    // --- REDIRECCIÓN DE INICIO ---
    @GetMapping("/")
    public String home() {
        return "redirect:/productos"; // Si entran a la raíz, los mandamos a productos
    }
}