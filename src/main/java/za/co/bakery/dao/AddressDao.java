package za.co.bakery.dao;

import java.util.List;
import za.co.bakery.model.Address;

public interface AddressDao {
    boolean addAddress(Address address, String userEmailAddress, boolean isDefaultDelAdd, boolean isResiAdd); 
    boolean editAddress(Address address, boolean isDefaultDelAdd, boolean isResiAdd);
    boolean deleteAddress(Address address);
    Address getAddressByName(String addressLine1);
    Address getAddressById(int addressId);
    Address getDefaultDeliveryAddress(String userId);
    Address getResidentAddress(String userId);
    List<Address> getAllAddresses();
    List<Address> getAddressesByUser(String userId); 
}
