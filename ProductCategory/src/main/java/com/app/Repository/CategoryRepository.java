package com.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
