package za.co.bakery.model;

import java.time.LocalDate;
import java.util.Objects;

public class Product {
    private int productId;
    private int recipeId;
    private int categoryId;
    private String title; 
    private String description; 
    private String warnings; 
    private double price; 
    private String picture;
    private boolean active;
    private int discount;
    private LocalDate date;
    private boolean outOfStock;

    public Product() {
    }
    
    public Product(int recipeId, int categoryId, String title, String description, String warnings, double price, String picture) {
        this.recipeId = recipeId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.warnings = warnings;
        this.price = price;
        this.picture = picture;
    }
    
    public Product(int recipeId, int categoryId, String title, String description, String warnings, double price, String picture, LocalDate date) {
        this.recipeId = recipeId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.warnings = warnings;
        this.price = price;
        this.date = date;
        this.outOfStock = outOfStock;
    }

    public Product(int productId, int recipeId, int categoryId, String title, String description, String warnings, double price, String picture, int discount, boolean active, LocalDate date, boolean outOfStock) {
        this.productId = productId;
        this.recipeId = recipeId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.warnings = warnings;
        this.price = price;
        this.picture = picture;
        this.discount = discount;
        this.active = active;
        this.date = date;
        this.outOfStock = outOfStock;
    }
    
    public Product(int productId, int recipeId, int categoryId, String title, String description, String warnings, double price, String picture, int discount, boolean active) {
        this.productId = productId;
        this.recipeId = recipeId;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.warnings = warnings;
        this.price = price;
        this.picture = picture;
        this.discount = discount;
        this.active = active;
    }
    
    public int getProductId() {
        return productId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int recipeId) {
        this.categoryId = recipeId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getWarnings() {
        return warnings;
    }
    
    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getPicture() {
        return picture;
    }
    
    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.categoryId;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }

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
        final Product other = (Product) obj;
        if (this.productId != other.productId) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.picture, other.picture)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", recipeId=" + recipeId + ", categoryId=" + categoryId + ", title=" + title + ", description=" + description + ", warnings=" + warnings + ", price=" + price + ", picture=" + picture + ", active=" + active + ", discount=" + discount + ", date=" + date + ", outOfStock=" + outOfStock + '}';
    }
    
    
}
