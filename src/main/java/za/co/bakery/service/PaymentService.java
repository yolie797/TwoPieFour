
package za.co.bakery.service;

import za.co.bakery.model.Payment;


public interface PaymentService {
    public Payment addPayment(Payment payment);       
    Payment getPaymentsByUser(String userEmailAddress);        // a list of payments made by a specific user.
    Payment getPaymentsByStatus(Payment status);       // payments with a specific status (e.g., "completed," "pending," "cancelled").
    Payment getTotalPaymentAmount();
    boolean generatePayment(String paymentOption);
}
