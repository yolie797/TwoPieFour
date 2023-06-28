package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.InvoiceDao;
import za.co.bakery.dao.impl.InvoiceDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Invoice;
import za.co.bakery.service.InvoiceService;

public class InvoiceServiceImpl implements InvoiceService{
    private InvoiceDao invoiceDao;

    public InvoiceServiceImpl(DBManager dbManager) {
        this.invoiceDao = InvoiceDaoImpl.getInstance(dbManager.getConnection());
    }
    
    @Override
    public boolean createInvoice(Invoice invoice) {
        return invoice != null ? invoiceDao.createInvoice(invoice):null;
    }
    
    @Override
    public boolean updateInvoice(Invoice invoice) {
        return invoice != null ? invoiceDao.updateInvoice(invoice):null;
    }

    @Override
    public Invoice getOrderInvoice(int orderId) {
        return orderId > 0 ? invoiceDao.getOrderInvoice(orderId):null;
    }

    @Override
    public List<Integer> getInvoiceIdentifier() {
        return invoiceDao.getInvoiceIdentifier();
    }

    @Override
    public Invoice getInvoiceWithIdentifier(int identifier) {
        return identifier > 99 ? invoiceDao.getInvoiceWithIdentifier(identifier):null;
    }
    
}
