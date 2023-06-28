package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.OrderLineItemDao;
import za.co.bakery.model.OrderLineItem;

public class OrderLineItemDaoImpl implements OrderLineItemDao{
    private static OrderLineItemDaoImpl orderLineItemDaoImpl = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<OrderLineItem> lstLineItems = null;
    
    private OrderLineItemDaoImpl(Connection dbConnection) {
        this.con = dbConnection;
    }
    
    public static OrderLineItemDaoImpl getInstance(Connection dbConnection){
        if(orderLineItemDaoImpl == null){
            orderLineItemDaoImpl = new OrderLineItemDaoImpl(dbConnection);
        }
        return orderLineItemDaoImpl;
    }

    @Override
    public List<OrderLineItem> getItemsByOrderId(int orderId) {
        lstLineItems = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT itemId, orderId, invoiceId, productId, quantity, totalPrice FROM order_line_items WHERE orderId=?");
                ps.setInt(1, orderId);
                rs = ps.executeQuery();
                while(rs.next()){
                lstLineItems.add(new OrderLineItem(rs.getInt("itemId"), rs.getInt("orderId"), rs.getInt("invoiceId"), rs.getInt("productId"),
                        rs.getInt("quantity"), rs.getDouble("totalPrice")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get itemsByOrderId ERROR: " + exception.getMessage());
            }
            finally{
                if(ps != null){
                    try{
                        ps.close();
                    }
                    catch(SQLException exception){
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return lstLineItems;
    }

    @Override
    public List<OrderLineItem> getItemsByInvoiceId(int invoiceId) {
        lstLineItems = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT itemId, orderId, invoiceId, productId, quantity, totalPrice FROM order_line_items WHERE invoiceId=?");
                ps.setInt(1, invoiceId);
                rs = ps.executeQuery();
                while(rs.next()){
                lstLineItems.add(new OrderLineItem(rs.getInt("itemId"), rs.getInt("orderId"), rs.getInt("invoiceId"), rs.getInt("productId"),
                        rs.getInt("quantity"), rs.getDouble("totalPrice")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get itemsByInvoiceId ERROR: " + exception.getMessage());
            }
            finally{
                if(ps != null){
                    try{
                        ps.close();
                    }
                    catch(SQLException exception){
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return lstLineItems;
    }
        
    @Override
    public boolean addOrderItem(OrderLineItem orderLineItem) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO order_line_items (orderId, invoiceId, productId, quantity, totalPrice) values(?,?,?,?,?)");
                ps.setInt(1, orderLineItem.getOrderId());
                ps.setInt(2, orderLineItem.getInvoiceId());
                ps.setInt(3, orderLineItem.getProductId());
                ps.setInt(4, orderLineItem.getQuantity());
                ps.setDouble(5, orderLineItem.getTotalPrice());
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
    
}
