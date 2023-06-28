package za.co.bakery.service;

import za.co.bakery.model.RecipeIngredient;

public interface RecipeIngredientService {
    RecipeIngredient addRecipeIngredient(int ingredientId, int unitId, double quantity);
}
