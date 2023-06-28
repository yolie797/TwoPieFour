package za.co.bakery.serviceImpl;

import za.co.bakery.model.RecipeIngredient;
import za.co.bakery.service.RecipeIngredientService;

public class RecipeIngredientServiceImpl implements RecipeIngredientService{

    @Override
    public RecipeIngredient addRecipeIngredient(int ingredientId, int unitId, double quantity) {
        return ingredientId > 0 && unitId > 0 && quantity > 0.0 ? new RecipeIngredient(ingredientId, unitId, quantity):null;
    }
    
}
