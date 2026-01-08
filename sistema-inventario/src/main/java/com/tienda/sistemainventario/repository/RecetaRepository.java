package com.tienda.sistemainventario.repository;


import com.tienda.sistemainventario.entity.Receta;
import com.tienda.sistemainventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    // Método vital: Buscar todos los ingredientes de un arreglo específico
    List<Receta> findByArreglo(Producto arreglo);
}