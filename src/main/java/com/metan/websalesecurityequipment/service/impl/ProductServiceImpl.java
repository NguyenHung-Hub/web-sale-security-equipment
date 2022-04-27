package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.repository.ProductRepository;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Product findProductById(String theId) {
        Optional<Product> result = productRepository.findById(theId);
        Product product;

        if (result.isPresent()) {
            product = result.get();
        } else {
            throw new RuntimeException("Did not find product by id - " + theId);
        }
        return product;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteProductById(String theId) {
        productRepository.deleteById(theId);
    }

    @Override
    public List<Product> findTopProduct() {
        return productRepository.findTopProduct();
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }


}
