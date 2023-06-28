package za.co.bakery.dao;

import java.util.List;
import za.co.bakery.model.OrderLineItem;

public interface OrderLineItemDao {
    List<OrderLineItem> getItemsByOrderId(int orderId);
    List<OrderLineItem> getItemsByInvoiceId(int invoiceId);
    boolean addOrderItem(OrderLineItem orderLineItem);
}
