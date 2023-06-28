package za.co.bakery.service;

import java.util.List;
import za.co.bakery.model.Order;

public interface OrderService {
    boolean addOrder(Order order);
    boolean cancelOrder(Order order);
    boolean updateOrder(Order order);
    Order getOrderByInvoiceId(int invoiceId);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
    List<Order> getCustomerOrders(String userId);
    boolean deleteOrder(Order order);
    
}
