package za.co.bakery.dao;

import java.util.List;
import za.co.bakery.model.Ingredient;

public interface IngredientDao {
    List<Ingredient> getAllIngredient();
    Ingredient getIngedientByName(String name);
    Ingredient getIngedientById(int ingredientId);
    boolean addIngredient(Ingredient ingredient);
    boolean editIngredient(Ingredient ingredient);
    boolean deleteIngredient(Ingredient ingredient);
}
