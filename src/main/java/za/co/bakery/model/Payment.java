package za.co.bakery.model;

import java.time.LocalDate;
import java.util.Objects;

public class Payment {
    private int paymentId;
    private Invoice invoiceId;
    private LocalDate paymentDate;
    private double totalPayment;
    private String type;

    public Payment() {
    }

    public Payment(int paymentId, Invoice invoiceId, LocalDate paymentDate, double totalPayment, String type) {
        this.paymentId = paymentId;
        this.invoiceId = invoiceId;
        this.paymentDate = paymentDate;
        this.totalPayment = totalPayment;
        this.type = type;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.paymentId;
        hash = 59 * hash + Objects.hashCode(this.invoiceId);
        hash = 59 * hash + Objects.hashCode(this.paymentDate);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.totalPayment) ^ (Double.doubleToLongBits(this.totalPayment) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.type);
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
        final Payment other = (Payment) obj;
        if (this.paymentId != other.paymentId) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalPayment) != Double.doubleToLongBits(other.totalPayment)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.invoiceId, other.invoiceId)) {
            return false;
        }
        if (!Objects.equals(this.paymentDate, other.paymentDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "payment{" + "paymentId=" + paymentId + ", invoiceId=" + invoiceId + ", paymentDate=" + paymentDate + ", totalPayment=" + totalPayment + ", type=" + type + '}';
    }
    
    
}
