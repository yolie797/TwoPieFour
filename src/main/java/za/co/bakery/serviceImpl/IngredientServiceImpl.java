package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.IngredientDao;
import za.co.bakery.dao.impl.IngredientDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Ingredient;
import za.co.bakery.service.IngredientService;


public class IngredientServiceImpl implements IngredientService{
    private IngredientDao ingredientDao;
    
    public IngredientServiceImpl(DBManager dbManager) {
        this.ingredientDao = IngredientDaoImpl.getInstance(dbManager.getConnection());
    }

    @Override
    public List<Ingredient> getAllIngredient() {
        return ingredientDao.getAllIngredient();
    }
    
    @Override
    public Ingredient getIngedientByName(String name) {
        name = name.toLowerCase();
        return name != null ? ingredientDao.getIngedientByName(name):null;
    }

    @Override
    public Ingredient getIngedientById(int ingredientId) {
        return ingredientId > 0 ? ingredientDao.getIngedientById(ingredientId):null;
    }
    
    @Override
    public boolean addIngredient(Ingredient ingredient) {
        return ingredient != null ? ingredientDao.addIngredient(ingredient):false;
    }

    @Override
    public boolean editIngredient(Ingredient ingredient) {
        return ingredient != null ? ingredientDao.editIngredient(ingredient):false;
    }

    @Override
    public boolean deleteIngredient(Ingredient ingredient) {
        return ingredient != null ? ingredientDao.deleteIngredient(ingredient):null;
    }
    
}
