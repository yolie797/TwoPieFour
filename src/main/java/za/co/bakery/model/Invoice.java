
package za.co.bakery.model;

import java.util.Objects;


public class Invoice {
    private int invoiceId;
    private int orderId;
    private int identifier;
    private double totalPayment;
    private double amountDue;

    public Invoice() {
    }
    
    public Invoice(int identifier, double totalPayment, double amountDue) {
        this.identifier = identifier;
        this.totalPayment = totalPayment;
        this.amountDue = amountDue;
    }
    
    public Invoice(int orderId, int identifier, double totalPayment, double amountDue) {
        this.orderId = orderId;
        this.identifier = identifier;
        this.totalPayment = totalPayment;
        this.amountDue = amountDue;
    }

    public Invoice(int invoiceId, int orderId, int identifier, double totalPayment, double amountDue) {
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.identifier = identifier;
        this.totalPayment = totalPayment;
        this.amountDue = amountDue;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public int getIdentifier() {
        return identifier;
    }
    
    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.invoiceId;
        hash = 67 * hash + Objects.hashCode(this.orderId);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.amountDue) ^ (Double.doubleToLongBits(this.amountDue) >>> 32));
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
        final Invoice other = (Invoice) obj;
        if (this.invoiceId != other.invoiceId) {
            return false;
        }
        if (Double.doubleToLongBits(this.amountDue) != Double.doubleToLongBits(other.amountDue)) {
            return false;
        }
        if (!Objects.equals(this.orderId, other.orderId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Invoice{" + "invoiceId=" + invoiceId + ", orderId=" + orderId + ", identifier=" + identifier + ", totalPayment=" + totalPayment + ", amountDue=" + amountDue + '}';
    }
    
}
