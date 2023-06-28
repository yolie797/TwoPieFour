package za.co.bakery.service;

import java.util.List;
import za.co.bakery.model.OrderLineItem;

public interface OrderLineItemService {
    List<OrderLineItem> getItemsByOrderId(int orderId);
    List<OrderLineItem> getItemsByInvoiceId(int invoiceId);
    boolean addOrderItem(OrderLineItem orderLineItem);
}
