package za.co.bakery.model;

public class RecipeIngredient {
    private int recipeId;
    private int ingredientId;
    private int unitId;
    private double quantity;

    public RecipeIngredient() {
    }
    
    public RecipeIngredient(int ingredientId, int unitId, double quantity) {
        this.ingredientId = ingredientId;
        this.unitId = unitId;
        this.quantity = quantity;
    }

    public RecipeIngredient(int recipeId, int ingredientId, int unitId, double quantity) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.unitId = unitId;
        this.quantity = quantity;
    }
    
    public int getRecipeId() {
        return recipeId;
    }
    
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    
    public int getIngredientId() {
        return ingredientId;
    }
    
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
    
    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
    
    public double getQuantity() {
        return quantity;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.recipeId;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.quantity) ^ (Double.doubleToLongBits(this.quantity) >>> 32));
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
        final RecipeIngredient other = (RecipeIngredient) obj;
        if (this.recipeId != other.recipeId) {
            return false;
        }
        if (this.ingredientId != other.ingredientId) {
            return false;
        }
        if (this.unitId != other.unitId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" + "recipeId=" + recipeId + ", ingredientId=" + ingredientId + ", unitId=" + unitId + ", quantity=" + quantity + '}';
    }
    
}
