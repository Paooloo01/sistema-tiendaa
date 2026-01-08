package com.tienda.sistemainventario.repository;


import com.tienda.sistemainventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí podemos agregar búsquedas especiales después, como "buscarPorCodigo"
}