package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.co.bakery.dao.PaymentDao;
import za.co.bakery.model.Payment;


public class PaymentDaoImpl implements PaymentDao{

    private static PaymentDao paymentDao = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public PaymentDaoImpl(Connection con) {
        this.con = con;
    }

    
    public static PaymentDao getInstance(Connection dbCon){
        if (paymentDao == null) {
            paymentDao = new PaymentDaoImpl(dbCon);
        }
        return paymentDao;
    }
    
    
    
    @Override
    public Payment addPayment(Payment payment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//         boolean retVal = false;
//         if (con != null) {
//            try{
//                ps = con.prepareStatement("INSERT INTO payment(invoiceId, paymentDate, totalPayment, type) values(?,?,?,?)");
//                ps.setInt(1, payment.getInvoiceId());
//                ps.setDate(2, Date.valueOf(payment.getPaymentDate()));
//                ps.setDouble(3, payment.getTotalPayment());
//                ps.setString(4, payment.getType());
//                
//                if (ps.executeUpdate() > 0) {
//                    retVal = true;
//                }
//                
//            } catch (SQLException ex) {
//                 System.out.println("Add payment Error" +ex.getMessage());
//             }finally{
//                if (ps != null) {
//                    try {
//                        ps.close();
//                    } catch (SQLException exception) {
//                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
//                    }
//                }
//            }
//        }
//        return retVal;
       
    }

  

    @Override
    public Payment getPaymentsByUser(String userEmailAddress) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Payment getPaymentsByStatus(Payment status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Payment getTotalPaymentAmount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
