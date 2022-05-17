package com.metan.websalesecurityequipment.service.impl;

import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.model.request.ProductRequestPageable;
import com.metan.websalesecurityequipment.repository.ProductRepository;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Product> findProductsNew() {
        return productRepository.findProductsNew();
    }

    @Override
    public List<Product> findProductByNameParentCategory(String name, String otherName, int limit) {
        return productRepository.findProductByNameParentCategory(name, otherName, limit);
    }

    @Override
    public List<Product> findProductsByCategory(long id) {
        return productRepository.findProductsByCategory(id);
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
    public String findMaxProductId() {
        return productRepository.findMaxProductId();
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }


    @Override
    public Page<Product> searchByNameCateBrand(ProductRequestPageable req, String name, Pageable pageable) {

        Page<Product> page;
        if (req.getBrandIds().size() == 0 && req.getCategoryIds().size() == 0 && req.getRating() == 0 && req.getMinPrice() == 0) {
            page = findByNameContaining(name, pageable);
        } else {
            if (req.getBrandIds().size() == 0 && req.getCategoryIds().size() == 0 && req.getRating() == 0) {
                page = productRepository.searchByPrice(req.getMinPrice(), req.getMaxPrice(), name, pageable);
            } else if (req.getBrandIds().size() == 0 && req.getCategoryIds().size() == 0 && req.getRating() != 0) {
                page = productRepository.searchByNameRating(req.getRating(), req.getMinPrice(), req.getMaxPrice(), name, pageable);
            } else if (req.getRating() == 0) {
                page = productRepository.searchByNameCateBrand(req.getCategoryIds(), req.getBrandIds(), req.getMinPrice(), req.getMaxPrice(), name, pageable);
            } else {
                page = productRepository.searchByNameCateBrandRating(req.getCategoryIds(), req.getBrandIds(), req.getRating(), req.getMinPrice(), req.getMaxPrice(), name, pageable);
            }
        }
        return page;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Product findBySlug(String slug) {
        return productRepository.findBySlug(slug);
    }

    @Override
    public List<Product> findTopNumberRandom(int top) {
        return productRepository.findTopNumberRandom(top);
    }

    @Override
    public boolean checkBuyCompletedProductByUser(String productId, long userId) {
        Product product= productRepository.checkBuyCompletedProductByUser(productId,userId);
        return product!=null;
    }

}
