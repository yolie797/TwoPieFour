package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.CategoryDao;
import za.co.bakery.dao.impl.CategoryDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Category;
import za.co.bakery.service.CategoryService;

public class CategoryServiceImpl implements CategoryService{
    private CategoryDao categoryDao;

    public CategoryServiceImpl(DBManager dbManager) {
        this.categoryDao = CategoryDaoImpl.getInstance(dbManager.getConnection());
    }
    
    @Override
    public List<Category> getAllCategory() {
        return categoryDao != null ? categoryDao.getAllCategory():null;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        categoryName = categoryName.toLowerCase();
        return categoryName != null ? categoryDao.getCategoryByName(categoryName):null;
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return categoryId > 0 ? categoryDao.getCategoryById(categoryId):null;
    }

    @Override
    public boolean addCategory(Category category) {
        return category != null ? categoryDao.addCategory(category):false;
    }

    @Override
    public boolean editCategory(Category category) {
        return category != null ? categoryDao.editCategory(category):false;
    }

    @Override
    public boolean deleteCategory(Category category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
