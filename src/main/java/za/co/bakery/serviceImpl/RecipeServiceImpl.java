package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.RecipeDao;
import za.co.bakery.dao.impl.RecipeDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Recipe;
import za.co.bakery.model.RecipeIngredient;
import za.co.bakery.service.RecipeService;


public class RecipeServiceImpl implements RecipeService{
    private RecipeDao recipeDao;

    public RecipeServiceImpl(DBManager dbManager) {
        this.recipeDao = RecipeDaoImpl.getInstance(dbManager.getConnection());
    }
    
    @Override
    public List<Recipe> getAllRecipe() {
        return recipeDao.getAllRecipe();
    }

    @Override
    public Recipe getRecipeByName(String recipeName) {
        recipeName = recipeName.toLowerCase();
        return recipeName != null ? recipeDao.getRecipeByName(recipeName):null;
    }
    
    @Override
    public Recipe getRecipeById(int recipeId) {
        return recipeId > 0 ? recipeDao.getRecipeById(recipeId):null;
    }

    @Override
    public List<RecipeIngredient> getRecipeIngredients(int recipeId) {
        return recipeId > 0 ? recipeDao.getRecipeIngredients(recipeId):null;
    }

    @Override
    public boolean addRecipe(Recipe recipe, List<RecipeIngredient> lstRecipeIngredient) {
        return recipe != null && lstRecipeIngredient != null ? recipeDao.addRecipe(recipe, lstRecipeIngredient):false;
    }

    @Override
    public boolean editRecipe(Recipe recipe, List<RecipeIngredient> lstRecipeIngredient) {
        return recipe != null && !lstRecipeIngredient.isEmpty() ? recipeDao.editRecipe(recipe, lstRecipeIngredient):false;
    }

    @Override
    public boolean deleteRecipe(Recipe recipe) {
        return recipe != null ? recipeDao.deleteRecipe(recipe):null;
    }
    
}
