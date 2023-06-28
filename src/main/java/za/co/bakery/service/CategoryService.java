package za.co.bakery.service;

import java.util.List;
import za.co.bakery.model.Category;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategoryByName(String categoryName);
    Category getCategoryById(int categoryId);
    boolean addCategory(Category category);
    boolean editCategory(Category category);
    boolean deleteCategory(Category category);
}
