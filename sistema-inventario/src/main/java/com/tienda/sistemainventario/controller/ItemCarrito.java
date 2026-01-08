package com.tienda.sistemainventario.controller;


import com.tienda.sistemainventario.entity.Producto;
import lombok.Data;

@Data
public class ItemCarrito {
    private Producto producto;
    private Integer cantidad;
    private Double subtotal; // Precio x Cantidad

    public ItemCarrito(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecioVenta() * cantidad;
    }
}