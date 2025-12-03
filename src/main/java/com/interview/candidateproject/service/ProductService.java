package com.interview.candidateproject.service;

import com.interview.candidateproject.entity.Product;
import com.interview.candidateproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findAvailableProducts();
    }

    public List<Product> getLowStockProducts(Integer threshold) {
        return productRepository.findLowStockProducts(threshold);
    }

    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    product.setPrice(productDetails.getPrice());
                    product.setStockQuantity(productDetails.getStockQuantity());
                    product.setCategory(productDetails.getCategory());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #4
     * 
     * This method should search for products by name.
     * It should be case-insensitive and support partial matches.
     * 
     * Hint: Use the existing repository method
     * 
     * @param name The product name to search for
     * @return List of products matching the search criteria
     */
    public List<Product> searchProductsByName(String name) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #5
     * 
     * This method should filter products by a price range.
     * 
     * Validation rules:
     * - minPrice must not be negative
     * - maxPrice must not be negative
     * - minPrice must be less than or equal to maxPrice
     * - If validation fails, throw IllegalArgumentException with appropriate
     * message
     * 
     * @param minPrice The minimum price
     * @param maxPrice The maximum price
     * @return List of products within the price range
     * @throws IllegalArgumentException if price range is invalid
     */
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        // TODO: Implement this method with proper validation
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }

    /**
     * TODO: Implement this method - CANDIDATE TASK #6
     * 
     * This method should update the stock quantity of a product.
     * 
     * Requirements:
     * - Find the product by ID (throw RuntimeException if not found)
     * - The new quantity cannot be negative (throw IllegalArgumentException)
     * - Save and return the updated product
     * 
     * @param productId   The product ID
     * @param newQuantity The new stock quantity
     * @return The updated product
     * @throws RuntimeException         if product not found
     * @throws IllegalArgumentException if quantity is negative
     */
    public Product updateStockQuantity(Long productId, Integer newQuantity) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("This method needs to be implemented by the candidate");
    }
}
