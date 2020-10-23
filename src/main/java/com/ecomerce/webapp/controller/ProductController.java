package com.ecomerce.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.webapp.entity.Product;
import com.ecomerce.webapp.exception.ProductNotFound;
import com.ecomerce.webapp.repository.ProductRepository;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

	// inject dependency
	@Autowired
	private ProductRepository productRepository;

	// GET all Products
	@GetMapping("/products")
	public List<Product> getAllProduct() {
		return this.productRepository.findAll();
	}

	// Get product by id
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable(value = "id") long productId) {
		return this.productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFound("Product not found with id" + productId));
	}

	// create product
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		return this.productRepository.save(product);
	}

	// update a product
	// find a product
	@PutMapping("/products/{id}")
	public Product UpdateProduct(@RequestBody Product product, @PathVariable(value = "id") long productId) {
		Product fetchedProduct = this.productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFound("Product not found with id" + productId));
		// set new values
		fetchedProduct.setName(product.getName());
		fetchedProduct.setDescription(product.getDescription());
		// save the product
		return this.productRepository.save(fetchedProduct);
	}

	
	// Delete a product
		@DeleteMapping("/products/{id}")
		public void DeleteProduct(@PathVariable(value = "id") long productId) {
			Product fetchedProduct = this.productRepository.findById(productId)
					.orElseThrow(() -> new ProductNotFound("Product not found with id" + productId));
			this.productRepository.delete(fetchedProduct);
		}

}
