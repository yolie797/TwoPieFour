package za.co.bakery.model;

import java.util.Objects;

public class Recipe {
    private int recipeId;
    private String description;
    private String recipeName;
    private boolean active;

    public Recipe() {
    }
    
    public Recipe(String description, String recipeName) {
        this.description = description;
        this.recipeName = recipeName;
    }

    public Recipe(int recipeId, String description, String recipeName, boolean active) {
        this.recipeId = recipeId;
        this.description = description;
        this.recipeName = recipeName;
        this.active = active;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getRecipeName() {
        return recipeName;
    }
    
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.recipeId;
        hash = 97 * hash + Objects.hashCode(this.description);
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
        final Recipe other = (Recipe) obj;
        if (this.recipeId != other.recipeId) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Recipe{" + "recipeId=" + recipeId + ", description=" + description + ", recipeName=" + recipeName + ", active=" + active + '}';
    }
    
}
