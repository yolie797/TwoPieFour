package za.co.bakery.service;

import java.util.List;
import za.co.bakery.model.Nutrition;

public interface NutritionService {
    List<Nutrition> getAllNutrition();
    List<Nutrition> getRecipeNutrition(int recipeID);
    Nutrition getNutritionByName(String nutritionName);
    boolean addRecipeNutrition(Nutrition nutrition);
    boolean editRecipeNutrition(Nutrition nutrition);
    boolean deleteNutrition(Nutrition nutrition);
}
