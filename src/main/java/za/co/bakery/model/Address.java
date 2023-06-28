package za.co.bakery.model;

import java.util.Objects;

public class Address {

    private int addressId;
    private String addressLine1;
    private String addressLine2;
    private String suburb;
    private String city;
    private int postalCode;
    private String province;

    public Address() {
    }
    
    public Address(String addressLine1, String addressLine2, String suburb, String city, int postalCode, String province) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.suburb = suburb;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
    }

    public Address(int addressId, String addressLine1, String addressLine2, String suburb, String city, int postalCode, String province) {
        this.addressId = addressId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.suburb = suburb;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.addressId;
        hash = 23 * hash + Objects.hashCode(this.addressLine1);
        hash = 23 * hash + Objects.hashCode(this.addressLine2);
        hash = 23 * hash + Objects.hashCode(this.suburb);
        hash = 23 * hash + Objects.hashCode(this.city);
        hash = 23 * hash + this.postalCode;
        hash = 23 * hash + Objects.hashCode(this.province);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (this.addressId != other.addressId) {
            return false;
        }
        if (this.postalCode != other.postalCode) {
            return false;
        }
        if (!Objects.equals(this.addressLine1, other.addressLine1)) {
            return false;
        }
        if (!Objects.equals(this.addressLine2, other.addressLine2)) {
            return false;
        }
        if (!Objects.equals(this.suburb, other.suburb)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.province, other.province)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + "addressId=" + addressId + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", suburb=" + suburb + ", city=" + city + ", postalCode=" + postalCode + ", province=" + province + '}';
    }
    
    

}
