package za.co.bakery.model;

import java.util.Objects;

public class CartItem {
    private Product product;
    private int quantity;
    private double total;
    private double subTotal;

    public CartItem() {
    }

    public CartItem(Product product, int quantity, double total, double subTotal) {
        this.product = product;
        this.quantity = quantity;
        this.total = total;
        this.subTotal = subTotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.product);
        hash = 83 * hash + this.quantity;
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
        final CartItem other = (CartItem) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CartItem{" + "product=" + product + ", quantity=" + quantity + ", total=" + total + ", subTotal=" + subTotal + '}';
    }
    
}
