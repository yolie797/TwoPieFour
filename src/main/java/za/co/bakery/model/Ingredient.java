package za.co.bakery.model;

import java.util.Comparator;
import java.util.Objects;

public class Ingredient implements Comparator<Ingredient>{
    private int ingredientId;
    private int unitId;
    private String name;
    private double quantityInStock;
    private double minStockQuantity;
     private boolean active;

    public Ingredient() {
    }
    
    public Ingredient(int unitId, String name, double quantityInStock, double minStockQuantity) {
        this.unitId = unitId;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.minStockQuantity = minStockQuantity;
    }

    public Ingredient(int ingredientID, int unitId, String name, double quantityInStock, double minStockQuantity, boolean active) {
        this.ingredientId = ingredientID;
        this.unitId = unitId;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.minStockQuantity = minStockQuantity;
        this.active = active;
    }
    
    public int getIngredientId() {
        return ingredientId;
    }
    
     public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getQuantityInStock() {
        return quantityInStock;
    }
    
    public void setQuantityInStock(double quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
    
    public double getMinStockQuantity() {
        return minStockQuantity;
    }
    
    public void setMinStockQuantity(double minStockQuantity) {
        this.minStockQuantity = minStockQuantity;
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
        hash = 89 * hash + Objects.hashCode(this.name);
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
        final Ingredient other = (Ingredient) obj;
        if (!Objects.equals(this.ingredientId, other.ingredientId)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "ingredientId=" + ingredientId + ", name=" + name + ", quantityInStock=" + quantityInStock + ", minStockQuantity=" + minStockQuantity + '}';
    }

    @Override
    public int compare(Ingredient i1, Ingredient i2) {
        return i1.getName().compareTo(i2.getName());
    }

    

}
