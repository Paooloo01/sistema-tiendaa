package com.tienda.sistemainventario.controller;



import com.tienda.sistemainventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // <--- OJO: Este es @Controller (sin Rest)
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller // Esto le dice a Spring: "Aquí vamos a entregar PANTALLAS HTML"
public class WebController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/mi-tienda") // Esta será la nueva dirección web
    public String verInventario(Model model) {
        // 1. Buscamos los productos
        var lista = productoService.listarTodos();

        // 2. Se los pasamos al HTML
        model.addAttribute("misProductos", lista);

        // 3. Spring buscará el archivo "lista-productos.html" en templates
        return "lista-productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return "redirect:/mi-tienda?exito=true"; // Si borra bien
        } catch (Exception e) {
            // Si falla (porque tiene ventas o es parte de un arreglo), volvemos con error
            return "redirect:/mi-tienda?error=true";
        }
    }

    // 1. Botón "Crear Nuevo" (Muestra formulario vacío)
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new com.tienda.sistemainventario.entity.Producto());
        return "formulario-producto";
    }

    // 2. Botón "Editar" (Busca el producto y rellena el formulario)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.buscarPorId(id));
        return "formulario-producto";
    }

    // 3. Botón "Guardar" del formulario (Recibe los datos y guarda en BD)
    @org.springframework.web.bind.annotation.PostMapping("/guardar")
    public String guardarProducto(com.tienda.sistemainventario.entity.Producto producto) {
        productoService.guardar(producto);
        return "redirect:/mi-tienda"; // Nos devuelve a la lista
    }
}