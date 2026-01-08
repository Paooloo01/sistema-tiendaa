package com.tienda.sistemainventario;



import com.tienda.sistemainventario.entity.Producto;
import com.tienda.sistemainventario.entity.Receta;
import com.tienda.sistemainventario.repository.ProductoRepository;
import com.tienda.sistemainventario.repository.RecetaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaDeDatos {

    @Bean
    CommandLineRunner iniciarDatos(ProductoRepository productoRepo, RecetaRepository recetaRepo) {
        return args -> {
            // SOLO CREAR DATOS SI LA TABLA ESTÁ VACÍA (Para no duplicar cada vez que reinicias)
            if (productoRepo.count() == 0) {

                // 1. CREAR INSUMOS (Componentes sueltos)
                Producto oso = new Producto();
                oso.setNombre("Oso de Peluche 30cm");
                oso.setCodigo("PEL-001");
                oso.setPrecioCompra(15.0); // Te costó 15
                oso.setPrecioVenta(30.0);  // Lo vendes a 30
                oso.setStock(10);          // Tienes 10 en tienda
                oso.setEsArreglo(false);
                productoRepo.save(oso);

                Producto chocolate = new Producto();
                chocolate.setNombre("Chocolate Vizzio");
                chocolate.setCodigo("CHO-001");
                chocolate.setPrecioCompra(5.0);
                chocolate.setPrecioVenta(10.0);
                chocolate.setStock(20);
                chocolate.setEsArreglo(false);
                productoRepo.save(chocolate);

                // 2. CREAR UN ARREGLO (Producto Compuesto)
                Producto packAmor = new Producto();
                packAmor.setNombre("Pack Amor Eterno");
                packAmor.setCodigo("ARR-001");
                packAmor.setPrecioCompra(0.0); // El costo es la suma de insumos (se calcula luego)
                packAmor.setPrecioVenta(60.0); // Precio final del arreglo
                packAmor.setStock(0);          // El stock es virtual, depende de los insumos
                packAmor.setEsArreglo(true);   // ¡IMPORTANTE! Esto activa la magia
                productoRepo.save(packAmor);

                // 3. CREAR LA RECETA (Unir el arreglo con sus ingredientes)
                // Decimos: "El Pack Amor lleva 1 Oso"
                Receta receta1 = new Receta();
                receta1.setArreglo(packAmor);
                receta1.setInsumo(oso);
                receta1.setCantidad(1);
                recetaRepo.save(receta1);

                // Decimos: "El Pack Amor lleva 2 Chocolates"
                Receta receta2 = new Receta();
                receta2.setArreglo(packAmor);
                receta2.setInsumo(chocolate);
                receta2.setCantidad(2);
                recetaRepo.save(receta2);

                System.out.println("✅ DATOS DE PRUEBA CARGADOS EN AIVEN EXITOSAMENTE");
            }
        };
    }
}