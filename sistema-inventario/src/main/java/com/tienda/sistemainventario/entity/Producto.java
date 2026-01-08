package com.tienda.sistemainventario.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo; // Ej: PEL-001
    private String nombre;
    private Double precioVenta;  // A cuánto lo vendes
    private Double precioCompra; // A cuánto lo compraste (Para calcular ganancia)
    private Integer stock;

    private boolean esArreglo; // TRUE = Es un pack (descuenta de otros), FALSE = Producto simple
}