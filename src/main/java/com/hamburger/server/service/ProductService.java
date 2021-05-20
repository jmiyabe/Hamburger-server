package com.hamburger.server.service;

import com.hamburger.server.model.Product;
import com.hamburger.server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProductList() {
        return productRepository.getProductList();
    }

    public void setProduct(Product product){
        productRepository.setProduct(product);
    }

    public Product getProductByID(long id){
        return productRepository.getProductByID(id);
    }
}
