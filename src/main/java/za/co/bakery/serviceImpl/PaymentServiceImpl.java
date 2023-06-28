
package za.co.bakery.serviceImpl;

import za.co.bakery.dao.PaymentDao;
import za.co.bakery.dao.impl.PaymentDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Payment;
import za.co.bakery.service.PaymentService;


public class PaymentServiceImpl implements PaymentService{
    PaymentDao paymentDao;
    
    public PaymentServiceImpl() {
    }
    
    public PaymentServiceImpl(DBManager dbManager) {
        this.paymentDao = PaymentDaoImpl.getInstance(dbManager.getConnection());
    }
    
    
    @Override
    public Payment addPayment(Payment payment) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public boolean generatePayment(String paymentOption) {
        int randomVal = -1;
        boolean retVal = true;
        switch(paymentOption.toLowerCase()){
            case "debit":
                randomVal = (int)(Math.random() * (100 - 20 + 1) + 20);
                if(randomVal > 55 && randomVal < 101) {
                    retVal = true;
                }
                break;
            case "credit":
                randomVal = (int)(Math.random() * (100 - 45 + 1) + 45);
                if(randomVal > 55 && randomVal < 101) {
                    retVal = true;
                }
                break;
            default:
        }
        return retVal;
    }
    
}
