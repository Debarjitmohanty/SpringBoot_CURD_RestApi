package com.app.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.Entity.Category;
import com.app.Entity.Product;
import com.app.Repository.CategoryRepository;
import com.app.Repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/categories")
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@PostMapping("/addcategory")
	public Category addCategory(@RequestBody Category category) {
		return categoryRepository.save(category);
	}
	
	@PostMapping("/addproduct")
	public Product addProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	@PutMapping("/addProductToCategory")
	public String addProductToCategory(@RequestParam long categoryId,@RequestParam long productId) {
		Category category = categoryRepository.findById(categoryId).get();
		Product product=productRepository.findById(productId).get();
		product.setCategory(category);
		productRepository.save(product);
		return "Category Update Successfully";
	}
	
	@PutMapping("/updateProduct/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable long productId, @RequestBody Product updatedProduct) {
	    Product existingProduct = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
	    existingProduct.setProductName(updatedProduct.getProductName());
	    existingProduct.setProductDescription(updatedProduct.getProductDescription());
	    existingProduct.setProductPrice(updatedProduct.getProductPrice());
	   
	    // You can update other fields as needed
	    Product savedProduct = productRepository.save(existingProduct);
	    return ResponseEntity.ok(savedProduct);
	}
	
	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<Category> updateCategory(@PathVariable long categoryId, @RequestBody Category updatedCategory) {
	    Category existingCategory = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
	    existingCategory.setName(updatedCategory.getName());
	    existingCategory.setDescription(updatedCategory.getDescription());
	    // You can update other fields as needed
	    Category savedCategory = categoryRepository.save(existingCategory);
	    return ResponseEntity.ok(savedCategory);
	}
	
	@DeleteMapping("/deleteProduct/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable long productId) {
	    productRepository.deleteById(productId);
	    return ResponseEntity.ok("Product deleted successfully");
	}



	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
	    Category category = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

	    Optional<Product> products = productRepository.findById(categoryId);
	    if (!products.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete category with associated products.");
	    }

	    categoryRepository.delete(category);
	    return ResponseEntity.ok("Category deleted successfully");
	}
}
