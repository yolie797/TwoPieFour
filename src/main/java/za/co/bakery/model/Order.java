package za.co.bakery.model;

import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private int orderId;
    private String email;
    private int addressId;
    private int invoiceId;
    private LocalDate date;
    private String deliveryStatus;
    private String paymentStatus;
    private boolean active;

    public Order() {
    }
    
    public Order(String email, int addressId, int invoiceId, LocalDate date, String deliveryStatus, String paymentStatus) {
        this.email = email;
        this.addressId = addressId;
        this.invoiceId = invoiceId;
        this.deliveryStatus = deliveryStatus;
        this.paymentStatus = paymentStatus;
        this.date = date;
    }

    public Order(int orderId, String email, int addressId, int invoiceId, LocalDate date,  String deliveryStatus, String paymentStatus, boolean active) {
        this.orderId = orderId;
        this.email = email;
        this.addressId = addressId;
        this.invoiceId = invoiceId;
        this.deliveryStatus = deliveryStatus;
        this.paymentStatus = paymentStatus;
        this.date = date;
        this.active = active;
    }
    
      public Order(String email, String deliveryStatus, String paymentStatus) {
        this.email = email;
        this.deliveryStatus = deliveryStatus;
        this.paymentStatus = paymentStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.orderId;
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + Objects.hashCode(this.addressId);
        hash = 37 * hash + Objects.hashCode(this.invoiceId);
        hash = 37 * hash + Objects.hashCode(this.deliveryStatus);
        hash = 37 * hash + Objects.hashCode(this.paymentStatus);
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
        final Order other = (Order) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (!Objects.equals(this.deliveryStatus, other.deliveryStatus)) {
            return false;
        }
        if (!Objects.equals(this.paymentStatus, other.paymentStatus)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.addressId, other.addressId)) {
            return false;
        }
        if (!Objects.equals(this.invoiceId, other.invoiceId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", email=" + email + ", addressId=" + addressId + ", invoiceId=" + invoiceId + ", date=" + date + ", deliveryStatus=" + deliveryStatus + ", paymentStatus=" + paymentStatus + '}';
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
