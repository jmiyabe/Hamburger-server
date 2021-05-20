package com.hamburger.server.service;

import com.hamburger.server.model.Ingredient;
import com.hamburger.server.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    public List<Ingredient> getIngredients() {
        return ingredientRepository.getIngredientList();
    }

    public void setIngredient(Ingredient ingredient){
        ingredientRepository.setIngredient(ingredient);
    }

    public Ingredient getIngredientByID(long id){
        return ingredientRepository.getIngredientById(id);
    }


}
