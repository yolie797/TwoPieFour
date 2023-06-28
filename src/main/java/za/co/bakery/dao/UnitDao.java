package za.co.bakery.dao;

import java.util.List;
import za.co.bakery.model.Unit;

public interface UnitDao {
    List<Unit> getAllUnit();
    Unit getUnitById(int unitId);
    Unit getUnitByName(String unitName);
    boolean addUnit(Unit unit);
    boolean editUnit(Unit unit);
    boolean deleteUnit(Unit unit);
}
