package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.AddressDao;
import za.co.bakery.dao.impl.AddressDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Address;
import za.co.bakery.service.AddressService;

public class AddressServiceImpl implements AddressService{
    private AddressDao addressDao;

    public AddressServiceImpl(DBManager dbManager) {
        this.addressDao = AddressDaoImpl.getInstance(dbManager.getConnection());
    }
    
    @Override
    public boolean addAddress(Address address, String userEmailAddress, boolean isDefaultDelAdd, boolean isResiAdd) {
        userEmailAddress = userEmailAddress.toLowerCase();
        return address != null && !userEmailAddress.isEmpty() ? addressDao.addAddress(address, userEmailAddress, isDefaultDelAdd, isResiAdd):false;
    }

    @Override
    public boolean editAddress(Address address, boolean isDefaultDelAdd, boolean isResiAdd) {
        return address != null ? addressDao.editAddress(address, isDefaultDelAdd, isResiAdd):false;
    }

    @Override
    public boolean deleteAddress(Address address) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Address getAddressByName(String addressLine1) {
        addressLine1 = addressLine1.toLowerCase();
        return !addressLine1.isEmpty() ? addressDao.getAddressByName(addressLine1):null;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressDao.getAllAddresses();
    }

    @Override
    public List<Address> getAddressesByUser(String userId) {
        userId = userId.toLowerCase();
        return !userId.isEmpty() ? addressDao.getAddressesByUser(userId):null;
    }

    @Override
    public Address getDefaultDeliveryAddress(String userId) {
        userId = userId.toLowerCase();
        return !userId.isEmpty() ? addressDao.getDefaultDeliveryAddress(userId):null;
    }

    @Override
    public Address getResidentAddress(String userId) {
        userId = userId.toLowerCase();
        return !userId.isEmpty() ? addressDao.getResidentAddress(userId):null;
    }

    @Override
    public Address getAddressById(int addressId) {
        return addressId > -1 ? addressDao.getAddressById(addressId):null;
    }
    
}
