package za.co.bakery.model;

public class OrderLineItem {
    private int itemId;
    private int orderId;
    private int invoiceId;
    private int productId;
    private int quantity;
    private double totalPrice;

    public OrderLineItem() {
    }

    public OrderLineItem(int orderId, int invoiceId, int productId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = unitPrice;
    }
    
    public OrderLineItem(int itemId, int orderId, int invoiceId, int productId, int quantity, double unitPrice) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = unitPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.itemId;
        hash = 29 * hash + this.invoiceId;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.totalPrice) ^ (Double.doubleToLongBits(this.totalPrice) >>> 32));
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
        final OrderLineItem other = (OrderLineItem) obj;
        if (this.itemId != other.itemId) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalPrice) != Double.doubleToLongBits(other.totalPrice)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderLineItem{" + "itemId=" + itemId + ", orderId=" + orderId + ", invoiceId=" + invoiceId + ", productId=" + productId + ", quantity=" + quantity + ", unitPrice=" + totalPrice + '}';
    }
}
