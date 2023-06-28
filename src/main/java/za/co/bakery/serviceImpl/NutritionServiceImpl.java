
package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.NutritionDao;
import za.co.bakery.dao.impl.NutritionDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Nutrition;
import za.co.bakery.service.NutritionService;

public class NutritionServiceImpl implements NutritionService{
    private NutritionDao nutritionDao;

    public NutritionServiceImpl(DBManager dbManager) {
        this.nutritionDao = NutritionDaoImpl.getInstance(dbManager.getConnection());
    }
    
    @Override
    public List<Nutrition> getAllNutrition() {
        return nutritionDao.getAllNutrition();
    }
    
    @Override
    public List<Nutrition> getRecipeNutrition(int recipeID) {
        return recipeID > 0 ? nutritionDao.getRecipeNutrition(recipeID):null;
    }
    
    @Override
    public Nutrition getNutritionByName(String nutritionName) {
        nutritionName = nutritionName.toLowerCase();
        return nutritionName != null ? nutritionDao.getNutritionByName(nutritionName):null;
    }

    @Override
    public boolean addRecipeNutrition(Nutrition nutrition) {
        return nutrition != null ? nutritionDao.addRecipeNutrition(nutrition):false;
    }

    @Override
    public boolean editRecipeNutrition(Nutrition nutrition) {
        return nutrition != null ? nutritionDao.editRecipeNutrition(nutrition):false;
    }

    @Override
    public boolean deleteNutrition(Nutrition nutrition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
