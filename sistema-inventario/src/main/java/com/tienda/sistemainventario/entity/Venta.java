package com.tienda.sistemainventario.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private Double totalVenta;
    private Double gananciaTotal; // Tu requisito clave: saber cuánto ganaste

    // Esto permite que al guardar una Venta, se guarden sus detalles automáticamente
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> items;
}