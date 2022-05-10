package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.model.request.ProductRequestPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public List<Product> findAll();

    public Product findProductById(String theId);

    public Product saveProduct(Product product);

    public void deleteProduct(Product product);

    public void deleteProductById(String theId);

    public List<Product> findTopProduct();

    //Hao
    List<Product> findByNameContaining(String name);

    public Page<Product> searchByNameCateBrand(String name,Pageable pageable);
    public Page<Product> searchByNameCateBrand(ProductRequestPageable req,String name, Pageable pageable);
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    public Product findBySlug(String slug);
    public List<Product> findTopNumberRandom(int top);
    public Page<Product> sortByOrderDetail(ProductRequestPageable req, String name, Pageable pageable);

}
