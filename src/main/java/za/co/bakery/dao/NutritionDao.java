package za.co.bakery.dao;

import java.util.List;
import za.co.bakery.model.Nutrition;

public interface NutritionDao {
    List<Nutrition> getAllNutrition();
    List<Nutrition> getRecipeNutrition(int recipeID);
    Nutrition getNutritionByName(String nutritionName);
    boolean addRecipeNutrition(Nutrition nutrition);
    boolean editRecipeNutrition(Nutrition nutrition);
    boolean deleteNutrition(Nutrition nutrition);
}
