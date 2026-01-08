package com.tienda.sistemainventario.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta; // A qué boleta pertenece

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto; // Qué se llevó

    private Integer cantidad;
    private Double precioUnitario; // Guardamos el precio del momento por si cambia mañana
}