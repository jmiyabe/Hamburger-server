package com.hamburger.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Product {

    public static enum Sale {
        LIGHT, MUITA_CARNE, MUITO_QUEIJO, INFLACAO
    }

    private Sale sale;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;

    private int count;

    @ElementCollection
    @JsonProperty("ingredientList")
    public List<Ingredient> ingredientList;

    public Product() {

    }
    public Product(long id, String name, List<Ingredient> ingredientList) {
        this.id = id;
        this.name = name;
        this.ingredientList = ingredientList;
    }

    public Product(long id, String name, List<Ingredient> ingredientList, Sale sale, int count) {
        this.id = id;
        this.name = name;
        this.ingredientList = ingredientList;
        this.sale = sale;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
