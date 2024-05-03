package com.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
