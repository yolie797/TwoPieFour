package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.OrderDao;
import za.co.bakery.dao.impl.OrderDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Order;
import za.co.bakery.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    public OrderServiceImpl(DBManager dbManager) {
        this.orderDao = OrderDaoImpl.getInstance(dbManager.getConnection());
    }
    
    @Override
    public boolean cancelOrder(Order order) {
        return order != null ? orderDao.cancelOrder(order):false;
    }

    @Override
    public boolean updateOrder(Order order) {
        return order != null ? orderDao.updateOrder(order):false;
    }
    
    @Override
    public boolean addOrder(Order order) {
        return order != null ? orderDao.addOrder(order):false;
    }

    @Override
    public Order getOrderByInvoiceId(int invoiceId) {
        return invoiceId > 0 ? orderDao.getOrderByInvoiceId(invoiceId):null;
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderId > 0 ? orderDao.getOrderById(orderId):null;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }
    
    @Override
    public List<Order> getCustomerOrders(String userId) {
        userId = userId.toLowerCase();
        return !userId.isEmpty() ? orderDao.getCustomerOrders(userId):null;
    }

    @Override
    public boolean deleteOrder(Order order) {
        return order != null ? orderDao.deleteOrder(order):false;
    }

}
