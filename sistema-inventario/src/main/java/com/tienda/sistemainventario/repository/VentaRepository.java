package com.tienda.sistemainventario.repository;


import com.tienda.sistemainventario.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tienda.sistemainventario.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;



@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    // NUEVO MÉTODO MÁGICO: Busca ventas en un rango de fechas
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}