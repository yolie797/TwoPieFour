package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.OrderDao;
import za.co.bakery.model.Order;

public class OrderDaoImpl implements OrderDao {

    private static OrderDao orderDao;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<Order> lstOrder = null;
    private Order order = null;

    private OrderDaoImpl(Connection dbConnection) {
        this.con = dbConnection;
    }

    public static OrderDao getInstance(Connection dbCon) {
        if (orderDao == null) {
            orderDao = new OrderDaoImpl(dbCon);
        }
        return orderDao;
    }

    @Override
    public boolean cancelOrder(Order order) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE order_tbl SET deliveryStatus = ? WHERE orderId = ?");
                ps.setString(1, "cancelled");
                ps.setInt(2, order.getOrderId());

                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException ex) {
                System.out.println("Cancel order error: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statement error: " + exception.getMessage());
                    }
                }
            }
        }
        return retVal;

    }

    @Override
    public boolean updateOrder(Order order) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE order_tbl SET deliveryStatus=?, paymentStatus=? WHERE orderId = ?");
                ps.setString(1, order.getDeliveryStatus());
                ps.setString(2, order.getPaymentStatus());
                ps.setInt(3, order.getOrderId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException ex) {
                System.out.println("Update order ERROR: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statement error: " + exception.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public boolean addOrder(Order order) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO order_tbl (userEmailAddress, addressId, invoiceId, date, deliveryStatus, paymentStatus) values(?,?,?,?,?,?)");
                ps.setString(1, order.getEmail());
                ps.setInt(2, order.getAddressId());
                ps.setInt(3, order.getInvoiceId());
                ps.setDate(4, Date.valueOf(order.getDate()));
                ps.setString(5, order.getDeliveryStatus());
                ps.setString(6, order.getPaymentStatus());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException ex) {
                System.out.println("add order error" + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public Order getOrderByInvoiceId(int invoiceId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT orderId, userEmailAddress, addressId, invoiceId, date, deliveryStatus, paymentStatus, active FROM order_tbl WHERE invoiceId=?");
                ps.setInt(1, invoiceId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    order = new Order(rs.getInt("orderId"), rs.getString("userEmailAddress"), rs.getInt("addressId"), rs.getInt("invoiceId"), rs.getDate("date").toLocalDate(),
                            rs.getString("deliveryStatus"), rs.getString("paymentStatus"), rs.getBoolean("active"));
                }
            } catch (SQLException exception) {
                System.out.println("Get OrderByInvoiceId ERROR: " + exception.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return order;
    }

    @Override
    public Order getOrderById(int orderId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT orderId, userEmailAddress, addressId, invoiceId, date, deliveryStatus, paymentStatus, active FROM order_tbl WHERE orderId=?");
                ps.setInt(1, orderId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    order = new Order(rs.getInt("orderId"), rs.getString("userEmailAddress"), rs.getInt("addressId"), rs.getInt("invoiceId"), rs.getDate("date").toLocalDate(),
                            rs.getString("deliveryStatus"), rs.getString("paymentStatus"), rs.getBoolean("active"));
                }
            } catch (SQLException exception) {
                System.out.println("Get OrderByInvoiceId ERROR: " + exception.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        lstOrder = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT orderId, userEmailAddress, addressId, invoiceId, date, deliveryStatus, paymentStatus, active FROM order_tbl");
                rs = ps.executeQuery();
                while (rs.next()) {
                    lstOrder.add(new Order(rs.getInt("orderId"), rs.getString("userEmailAddress"), rs.getInt("addressId"), rs.getInt("invoiceId"), rs.getDate("date").toLocalDate(),
                            rs.getString("deliveryStatus"), rs.getString("paymentStatus"), rs.getBoolean("active")));
                }
            } catch (SQLException exception) {
                System.out.println("Get allOrder ERROR: " + exception.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return lstOrder;
    }

    @Override
    public List<Order> getCustomerOrders(String userId) {
        lstOrder = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT orderId, userEmailAddress, addressId, invoiceId, date, deliveryStatus, paymentStatus, active FROM order_tbl WHERE userEmailAddress=?");
                ps.setString(1, userId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    lstOrder.add(new Order(rs.getInt("orderId"), rs.getString("userEmailAddress"), rs.getInt("addressId"), rs.getInt("invoiceId"), rs.getDate("date").toLocalDate(),
                            rs.getString("deliveryStatus"), rs.getString("paymentStatus"), rs.getBoolean("active")));
                }
            } catch (SQLException exception) {
                System.out.println("Get allOrder ERROR: " + exception.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return lstOrder;
    }

    @Override
    public boolean deleteOrder(Order order) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE order_tbl SET active=? WHERE orderId=?");
                ps.setBoolean(1, order.isActive());
                ps.setInt(1, order.getOrderId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Delete order Error!!: " + exception.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException exception) {
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

}
