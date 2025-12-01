package com.ecostyle.EcoStyle.repository;

import com.ecostyle.EcoStyle.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
