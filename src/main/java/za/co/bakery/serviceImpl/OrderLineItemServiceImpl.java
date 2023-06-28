package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.OrderLineItemDao;
import za.co.bakery.dao.impl.OrderLineItemDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.OrderLineItem;
import za.co.bakery.service.OrderLineItemService;

public class OrderLineItemServiceImpl implements OrderLineItemService{
    private OrderLineItemDao orderLineItemDao;

    public OrderLineItemServiceImpl(DBManager dbManager) {
        this.orderLineItemDao = OrderLineItemDaoImpl.getInstance(dbManager.getConnection());
    }

    @Override
    public List<OrderLineItem> getItemsByOrderId(int orderId) {
        return orderId > 0 ? orderLineItemDao.getItemsByOrderId(orderId):null;
    }

    @Override
    public List<OrderLineItem> getItemsByInvoiceId(int invoiceId) {
        return invoiceId > 0 ? orderLineItemDao.getItemsByInvoiceId(invoiceId):null;
    }

    @Override
    public boolean addOrderItem(OrderLineItem orderLineItem) {
        return orderLineItem != null ? orderLineItemDao.addOrderItem(orderLineItem):null;
    }
}
