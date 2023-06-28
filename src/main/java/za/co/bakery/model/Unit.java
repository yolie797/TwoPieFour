package za.co.bakery.model;

import java.util.Objects;

public class Unit {
    private int unitId;
    private String unitName;

    public Unit() {
    }

    public Unit(int unitId, String unitName) {
        this.unitId = unitId;
        this.unitName = unitName;
    }
    
    public int getUnitId() {
        return unitId;
    }
    
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.unitId;
        hash = 37 * hash + Objects.hashCode(this.unitName);
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
        final Unit other = (Unit) obj;
        if (this.unitId != other.unitId) {
            return false;
        }
        if (!Objects.equals(this.unitName, other.unitName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Unit{" + "unitId=" + unitId + ", unitName=" + unitName + '}';
    }
    
}
