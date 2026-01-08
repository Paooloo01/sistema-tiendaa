package com.tienda.sistemainventario.controller;


import com.tienda.sistemainventario.entity.Producto;
import com.tienda.sistemainventario.entity.Receta;
import com.tienda.sistemainventario.repository.ProductoRepository;
import com.tienda.sistemainventario.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/receta")
public class RecetaController {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private RecetaRepository recetaRepo;

    // 1. Ver la pantalla de ingredientes de un arreglo específico
    @GetMapping("/{idArreglo}")
    public String verIngredientes(@PathVariable Long idArreglo, Model model) {
        Producto arreglo = productoRepo.findById(idArreglo).orElse(null);

        // Buscamos qué ingredientes ya tiene asignados
        List<Receta> ingredientesActuales = recetaRepo.findByArreglo(arreglo);

        // Buscamos TODOS los productos para poder elegir (menos él mismo)
        List<Producto> todosLosProductos = productoRepo.findAll();
        todosLosProductos.remove(arreglo); // No puedes ponerte a ti mismo como ingrediente

        model.addAttribute("arreglo", arreglo);
        model.addAttribute("ingredientesActuales", ingredientesActuales);
        model.addAttribute("todosLosProductos", todosLosProductos);

        return "receta"; // Vamos al HTML receta.html
    }

    // 2. Agregar un ingrediente
    @PostMapping("/agregar")
    public String agregarIngrediente(@RequestParam Long idArreglo,
                                     @RequestParam Long idInsumo,
                                     @RequestParam Integer cantidad) {

        Producto arreglo = productoRepo.findById(idArreglo).orElse(null);
        Producto insumo = productoRepo.findById(idInsumo).orElse(null);

        if (arreglo != null && insumo != null) {
            Receta nuevaReceta = new Receta();
            nuevaReceta.setArreglo(arreglo);
            nuevaReceta.setInsumo(insumo);
            nuevaReceta.setCantidad(cantidad);
            recetaRepo.save(nuevaReceta);
        }
        return "redirect:/receta/" + idArreglo; // Recargamos la misma página
    }

    // 3. Borrar un ingrediente de la lista
    @GetMapping("/eliminar/{idReceta}")
    public String eliminarIngrediente(@PathVariable Long idReceta) {
        Receta r = recetaRepo.findById(idReceta).orElse(null);
        Long idArreglo = r.getArreglo().getId();
        recetaRepo.delete(r);
        return "redirect:/receta/" + idArreglo;
    }
}