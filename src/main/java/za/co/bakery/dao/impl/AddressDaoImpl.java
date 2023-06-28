package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.AddressDao;
import za.co.bakery.model.Address;

public class AddressDaoImpl implements AddressDao {

    private static AddressDao addressDao;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Address address = null;

    public AddressDaoImpl(Connection con) {
        this.con = con;
    }

    public static AddressDao getInstance(Connection dbCon) {
        if (addressDao == null) {
            addressDao = new AddressDaoImpl(dbCon);
        }
        return addressDao;
    }

    @Override
    public boolean addAddress(Address address, String userEmailAddress, boolean isDefaultDelAdd, boolean isResiAdd) {
        boolean retVal = false;
        int addressId = 0;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO address(addressLine1,addressLine2,suburb,city,postalCode,province) values(?,?,?,?,?,?)");
                ps.setString(1, address.getAddressLine1());
                ps.setString(2, address.getAddressLine2());
                ps.setString(3, address.getSuburb());
                ps.setString(4, address.getCity());
                ps.setInt(5, address.getPostalCode());
                ps.setString(6, address.getProvince());
                ps.execute();

            } catch (SQLException ex) {
                System.out.println("add address Error!!: " + ex.getMessage());
            }
            try {
                ps = con.prepareStatement("SELECT addressId FROM address WHERE addressLine1=?");
                ps.setString(1, address.getAddressLine1());
                rs = ps.executeQuery();
                while (rs.next()) {
                    addressId = rs.getInt("addressId");
                }
                ps = con.prepareStatement("INSERT INTO user_address(userEmailAddress, addressId, defaultDeliveryAddress, residentAddress) values(?,?,?,?)");
                ps.setString(1, userEmailAddress);
                ps.setBoolean(3, isDefaultDelAdd);
                ps.setBoolean(4, isResiAdd);
                if (addressId > 0) {
                    ps.setInt(2, addressId);
                    if (ps.executeUpdate() > 0) {
                        retVal = true;
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Link user & address ERROR: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Close prepared statment ERROR: " + ex.getMessage());
                    }
                }
            }

        }
        return retVal;
    }

    @Override
    public boolean editAddress(Address address, boolean isDefaultDelAdd, boolean isResiAdd) {
        boolean retVal = false;
        if (con != null) {
            try {

                ps = con.prepareStatement("UPDATE address SET addressLine1=?, addressLine2=?, suburb=?, city=?, postalCode=?, province=? WHERE addressId=?");
                ps.setString(1, address.getAddressLine1());
                ps.setString(2, address.getAddressLine2());
                ps.setString(3, address.getSuburb());
                ps.setString(4, address.getCity());
                ps.setInt(5, address.getPostalCode());
                ps.setString(6, address.getProvince());
                ps.setInt(7, address.getAddressId());
                ps.execute();
                ps = con.prepareStatement("UPDATE user_address SET defaultDeliveryAddress=?, residentAddress=? WHERE addressId=?");
                ps.setBoolean(1, isDefaultDelAdd);
                ps.setBoolean(2, isResiAdd);
                ps.setInt(3, address.getAddressId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException ex) {
                System.out.println("Edit address ERROR: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Close prepared statment ERROR: " + ex.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public boolean deleteAddress(Address address) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("DELETE FROM address WHERE addressId=?");
                //ps.setInt(1, addressId);

                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException ex) {
                System.out.println("Delete address ERROR: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Close prepared statment ERROR: " + ex.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public List<Address> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT * FROM address");
                rs = ps.executeQuery();
                while (rs.next()) {
                    addresses.add(new Address(rs.getInt("addressId"), rs.getString("addressLine1"), rs.getString("addressLine2"),
                             rs.getString("suburb"), rs.getString("city"), rs.getInt("postalCode"), rs.getString("province")));
                }
            } catch (SQLException ex) {
                System.out.println("Get allAddress ERROR: " + ex.getMessage());
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
        return addresses;
    }

    @Override
    public Address getAddressById(int addressId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT * FROM address WHERE addressId=?");
                ps.setInt(1, addressId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    address = new Address(rs.getInt("addressId"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("suburb"), rs.getString("city"),
                            rs.getInt("postalCode"), rs.getString("province"));
                }
            } catch (SQLException exception) {
                System.out.println("Get addressByName ERROR: " + exception.getMessage());
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
        return address;
    }

    @Override
    public List<Address> getAddressesByUser(String email) {
        List<Address> addresses = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT a.* FROM address a, user_address ua WHERE ua.userEmailAddress=? AND ua.addressId = a.addressId");
                ps.setString(1, email);
                rs = ps.executeQuery();
                while (rs.next()) {
                    addresses.add(new Address(rs.getInt("addressId"), rs.getString("addressLine1"), rs.getString("addressLine2"),
                             rs.getString("suburb"), rs.getString("city"), rs.getInt("postalCode"), rs.getString("province")));
                }
            } catch (SQLException ex) {
                System.out.println("Get AddressByUser ERROR: " + ex.getMessage());
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
        return addresses;
    }

    @Override
    public Address getDefaultDeliveryAddress(String userId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT a.* FROM address a, user_address ua WHERE ua.userEmailAddress=? AND ua.defaultDeliveryAddress=1 AND ua.addressId=a.addressId");
                ps.setString(1, userId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    address = new Address(rs.getInt("addressId"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("suburb"), rs.getString("city"),
                            rs.getInt("postalCode"), rs.getString("province"));
                }
            } catch (SQLException exception) {
                System.out.println("Get DefaultDeliveryAddress ERROR: " + exception.getMessage());
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
        return address;
    }

    @Override
    public Address getResidentAddress(String userId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT a.* FROM address a, user_address ua WHERE ua.userEmailAddress=? AND ua.residentAddress=1 AND ua.addressId=a.addressId");
                ps.setString(1, userId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    address = new Address(rs.getInt("addressId"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("suburb"), rs.getString("city"),
                            rs.getInt("postalCode"), rs.getString("province"));
                }
            } catch (SQLException exception) {
                System.out.println("Get ResidentAddress ERROR: " + exception.getMessage());
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
        return address;
    }

    @Override
    public Address getAddressByName(String addressLine1) {
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT * FROM address WHERE addressLine1=?");
                ps.setString(1, addressLine1);
                rs = ps.executeQuery();
                while (rs.next()) {
                    address = new Address(rs.getInt("addressId"), rs.getString("addressLine1"), rs.getString("addressLine2"), rs.getString("suburb"), rs.getString("city"),
                            rs.getInt("postalCode"), rs.getString("province"));
                }
            } catch (SQLException exception) {
                System.out.println("Get addressByName ERROR: " + exception.getMessage());
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
        return address;
    }

}
