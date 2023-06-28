
package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.InvoiceDao;
import za.co.bakery.model.Invoice;


public class InvoiceDaoImpl implements InvoiceDao{
    private static InvoiceDao invoiceDao = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Invoice invoice = null;
    private List lstIdentifier;

    private InvoiceDaoImpl(Connection con) {
        this.con = con;
    }
    
     public static InvoiceDao getInstance(Connection dbCon) {
        if (invoiceDao == null) {
            invoiceDao = new InvoiceDaoImpl(dbCon);
        }
        return invoiceDao;
    }

    @Override
    public boolean createInvoice(Invoice invoice) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO invoice (identifier, totalPayment, amountDue) values(?,?,?)");
                ps.setInt(1, invoice.getIdentifier());
                ps.setDouble(2, invoice.getTotalPayment());
                ps.setDouble(3, invoice.getAmountDue());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException ex) {
                System.out.println("create invoice error" + ex.getMessage());
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

    public boolean updateInvoice(Invoice invoice) {
         boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE invoice SET orderId=? WHERE invoiceId=?");
                ps.setInt(1, invoice.getOrderId());
                ps.setInt(2, invoice.getInvoiceId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Update invoice Error!!: " + exception.getMessage());
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
    public Invoice getOrderInvoice(int orderId) {
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT invoiceId, orderId, identifier, totalPayment, amountDue FROM invoice WHERE orderId=?");
                ps.setInt(1, orderId);
                rs = ps.executeQuery();
                while(rs.next()){
                    invoice = new Invoice(rs.getInt("invoiceId"), rs.getInt("orderId"), rs.getInt("identifier"), rs.getDouble("totalPayment"), rs.getDouble("amountDue"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get OrderInvoice ERROR: " + exception.getMessage());
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
        return invoice;
    }

    @Override
    public List<Integer> getInvoiceIdentifier() {
        lstIdentifier = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT identifier FROM invoice");
                rs = ps.executeQuery();
                while(rs.next()){
                    lstIdentifier.add(rs.getInt("identifier"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get InvoiceIdentifier ERROR: " + exception.getMessage());
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
        return lstIdentifier;
    }

    @Override
    public Invoice getInvoiceWithIdentifier(int identifier) {
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT invoiceId, orderId, identifier, totalPayment, amountDue FROM invoice WHERE identifier=?");
                ps.setInt(1, identifier);
                rs = ps.executeQuery();
                while(rs.next()){
                    invoice = new Invoice(rs.getInt("invoiceId"), rs.getInt("orderId"), rs.getInt("identifier"), rs.getDouble("totalPayment"), rs.getDouble("amountDue"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get InvoiceWithIdentidier ERROR: " + exception.getMessage());
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
        return invoice;
    }
    
}
