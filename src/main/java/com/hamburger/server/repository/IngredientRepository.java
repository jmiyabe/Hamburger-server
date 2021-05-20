package com.hamburger.server.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamburger.server.model.Ingredient;

import com.hamburger.server.utils.ProjectConstants;

import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class IngredientRepository {

    //In Memory database
    List<Ingredient> ingredientList = new ArrayList<>();

    public IngredientRepository(){
        if(ingredientList.size() == 0) {
            populateIngredientList();
        }
    }

    public Ingredient getIngredientById(long id){
        return ingredientList.stream().filter(ingredient -> id == ingredient.getId()).findAny().orElse(null);
    }

    public void setIngredient(Ingredient ingredient){
        ingredientList.add(ingredient);
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    //populate in-memory database
    private void populateIngredientList() {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader(ProjectConstants.fullPathToFile)) {
            Ingredient[] ingredient = mapper.readValue(reader,Ingredient[].class);
            ingredientList = Arrays.asList(ingredient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
