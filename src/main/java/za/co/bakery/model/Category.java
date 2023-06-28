package za.co.bakery.model;

import java.util.Objects;

public class Category {
    private int categoryId;
    private String categoryName;
    private boolean active;

    public Category() {
    }
    
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Category(int categoryId, String categoryName, boolean active) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.active = active;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.categoryId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.categoryId != other.categoryId) {
            return false;
        }
        if (!Objects.equals(this.categoryName, other.categoryName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryName=" + categoryName + '}';
    }

}
