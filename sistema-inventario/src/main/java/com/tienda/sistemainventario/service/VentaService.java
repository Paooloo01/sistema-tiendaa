package com.tienda.sistemainventario.service;


import com.tienda.sistemainventario.entity.*;
import com.tienda.sistemainventario.repository.*;
import jakarta.transaction.Transactional; // Importante para que si falla algo, no se guarde nada a medias
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    // ESTE ES EL MÉTODO MÁGICO QUE HACE TODO
    @Transactional // Significa: "O se guarda todo bien, o no se guarda nada"
    public Venta registrarVenta(List<Producto> productosVendidos) {

        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());

        double totalVenta = 0.0;
        double costoTotal = 0.0;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (Producto prodVendido : productosVendidos) {
            // 1. Buscamos el producto real en la BD para tener datos frescos
            Producto productoBD = productoRepository.findById(prodVendido.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // 2. Lógica de Descuento de Stock
            if (productoBD.isEsArreglo()) {
                // SI ES ARREGLO: Buscamos su receta y descontamos los insumos
                List<Receta> ingredientes = recetaRepository.findByArreglo(productoBD);

                for (Receta receta : ingredientes) {
                    Producto insumo = receta.getInsumo();
                    int cantidadNecesaria = receta.getCantidad();

                    // Descontamos del insumo (Ej. Restamos el Oso)
                    insumo.setStock(insumo.getStock() - cantidadNecesaria);
                    productoRepository.save(insumo); // Guardamos el nuevo stock del insumo

                    // Sumamos al costo (para saber cuánto ganamos)
                    costoTotal += (insumo.getPrecioCompra() * cantidadNecesaria);
                }
            } else {
                // SI ES PRODUCTO SIMPLE: Solo restamos 1 al stock de ese producto
                productoBD.setStock(productoBD.getStock() - 1);
                productoRepository.save(productoBD);
                costoTotal += productoBD.getPrecioCompra();
            }

            // 3. Crear el detalle para la boleta
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(productoBD);
            detalle.setCantidad(1); // Por ahora asumimos venta de 1 en 1 para simplificar
            detalle.setPrecioUnitario(productoBD.getPrecioVenta());

            detalles.add(detalle);
            totalVenta += productoBD.getPrecioVenta();
        }

        // 4. Finalizar la Venta
        venta.setItems(detalles);
        venta.setTotalVenta(totalVenta);
        venta.setGananciaTotal(totalVenta - costoTotal); // ¡Aquí calculamos tu ganancia real!

        return ventaRepository.save(venta);
    }
}