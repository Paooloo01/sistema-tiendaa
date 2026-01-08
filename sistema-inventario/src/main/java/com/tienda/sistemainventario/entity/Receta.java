package com.tienda.sistemainventario.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_arreglo")
    private Producto arreglo; // El padre (Ej. Pack Cumpleaños)

    @ManyToOne
    @JoinColumn(name = "id_insumo")
    private Producto insumo; // El hijo (Ej. Oso de Peluche)

    private Integer cantidad; // ¿Cuántos osos lleva este pack?
}