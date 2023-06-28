package za.co.bakery.model;

import java.util.Objects;

public class Nutrition {
    private int nutritionId;
    private int recipeId;
    private String nutritionName;
    private double nutritionValue;

    public Nutrition() {
    }
    
    public Nutrition(int recipeId, String nutritionName, double nutritionValue) {
        this.recipeId = recipeId;
        this.nutritionName = nutritionName;
        this.nutritionValue = nutritionValue;
    }

    public Nutrition(int nutritionId, int recipeId, String nutritionName, double nutritionValue) {
        this.nutritionId = nutritionId;
        this.recipeId = recipeId;
        this.nutritionName = nutritionName;
        this.nutritionValue = nutritionValue;
    }

    public int getNutritionId() {
        return nutritionId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getNutritionName() {
        return nutritionName;
    }

    public void setNutritionName(String nutritionName) {
        this.nutritionName = nutritionName;
    }

    public double getNutritionValue() {
        return nutritionValue;
    }

    public void setNutritionValue(double nutritionValue) {
        this.nutritionValue = nutritionValue;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.nutritionId;
        hash = 79 * hash + Objects.hashCode(this.recipeId);
        hash = 79 * hash + Objects.hashCode(this.nutritionName);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.nutritionValue) ^ (Double.doubleToLongBits(this.nutritionValue) >>> 32));
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
        final Nutrition other = (Nutrition) obj;
        if (this.nutritionId != other.nutritionId) {
            return false;
        }
        if (Double.doubleToLongBits(this.nutritionValue) != Double.doubleToLongBits(other.nutritionValue)) {
            return false;
        }
        if (!Objects.equals(this.nutritionName, other.nutritionName)) {
            return false;
        }
        if (!Objects.equals(this.recipeId, other.recipeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nutrition{" + "nutritionId=" + nutritionId + ", recipeId=" + recipeId + ", nutritionName=" + nutritionName + ", nutritionValue=" + nutritionValue + '}';
    }

    
}
