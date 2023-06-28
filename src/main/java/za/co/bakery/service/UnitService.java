package za.co.bakery.service;

import java.util.List;
import za.co.bakery.model.Unit;

public interface UnitService {
    List<Unit> getAllUnit();
    Unit getUnitById(int unitId);
    Unit getUnitByName(String unitName);
    boolean addUnit(Unit unit);
    boolean editUnit(Unit unit);
    boolean deleteUnit(Unit unit);
}
