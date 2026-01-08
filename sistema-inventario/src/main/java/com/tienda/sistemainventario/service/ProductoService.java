package com.tienda.sistemainventario.service;


import com.tienda.sistemainventario.entity.Producto;
import com.tienda.sistemainventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // ¡Importante! Esto le dice a Spring que aquí hay lógica de negocio
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Método para borrar por ID
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}