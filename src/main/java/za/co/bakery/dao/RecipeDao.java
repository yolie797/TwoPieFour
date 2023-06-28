package za.co.bakery.dao;

import java.util.List;
import za.co.bakery.model.Recipe;
import za.co.bakery.model.RecipeIngredient;

public interface RecipeDao {
    List<Recipe> getAllRecipe();
    Recipe getRecipeByName(String recipeName);
    Recipe getRecipeById(int recipeId);
    List<RecipeIngredient> getRecipeIngredients(int recipeId);
    boolean addRecipe(Recipe recipe, List<RecipeIngredient> lstRecipeIngredient);
    boolean editRecipe(Recipe recipe, List<RecipeIngredient> lstRecipeIngredient);
    boolean deleteRecipe(Recipe recipe);
}
