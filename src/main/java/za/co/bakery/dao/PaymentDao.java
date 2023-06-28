package za.co.bakery.dao;

import za.co.bakery.model.Payment;

public interface PaymentDao {
   public Payment addPayment(Payment payment);                 
   Payment getPaymentsByUser(String userEmailAddress);        // a list of payments made by a specific user.
   Payment getPaymentsByStatus(Payment status);               // payments with a specific status (e.g., "completed," "pending," "cancelled").
   Payment getTotalPaymentAmount();                          //Calculates and returns the total amount of all payments made.
}
