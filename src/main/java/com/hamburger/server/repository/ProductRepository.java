package com.hamburger.server.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamburger.server.model.Ingredient;
import com.hamburger.server.model.Product;
import com.hamburger.server.utils.ProjectConstants;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepository {
    //In memory database
    List<Product> productList = new ArrayList<>();

    public ProductRepository() {
        if(productList.size() == 0) {
            populateProductList();
        }
    }
    public void setProduct(Product product){
        productList.add(product);
    }

    public Product getProductByID(long id){
        return productList.stream().filter(lunch -> id == lunch.getId()).findAny().orElse(null);
    }

    public List<Product> getProductList(){
        return productList;
    }

    //populate in-memory database
    private void populateProductList(){
        long id = 0;
        ObjectMapper mapper = new ObjectMapper();
        List<Ingredient> ingredientList = new ArrayList<>();
        try (FileReader reader = new FileReader(ProjectConstants.fullPathToFile)) {
            Ingredient[] ingredient = mapper.readValue(reader,Ingredient[].class);
            ingredientList = Arrays.asList(ingredient);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!ingredientList.isEmpty()) {
            productList.add(new Product(id, "X-Bacon", Arrays.asList(new Ingredient[]{ingredientList.get(1), ingredientList.get(2), ingredientList.get(4)}), Product.Sale.LIGHT, 1));
            productList.add(new Product(++id, "X-Burger", Arrays.asList(new Ingredient[]{ingredientList.get(2), ingredientList.get(4)}), Product.Sale.MUITA_CARNE,1));
            productList.add(new Product(++id, "X-Egg", Arrays.asList(new Ingredient[]{ingredientList.get(2), ingredientList.get(3), ingredientList.get(4)}), Product.Sale.MUITO_QUEIJO, 1));
            productList.add(new Product(++id, "X-Egg-Bacon", Arrays.asList(new Ingredient[]{ingredientList.get(1), ingredientList.get(2), ingredientList.get(3), ingredientList.get(4)})));
        }
    }



}
