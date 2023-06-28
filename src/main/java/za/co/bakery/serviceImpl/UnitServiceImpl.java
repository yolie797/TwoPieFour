package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.UnitDao;
import za.co.bakery.dao.impl.UnitDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Unit;
import za.co.bakery.service.UnitService;

public class UnitServiceImpl implements UnitService{
    private UnitDao unitDao;

    public UnitServiceImpl(DBManager dbManager) {
        this.unitDao = UnitDaoImpl.getInstance(dbManager.getConnection());
    }

    @Override
    public List<Unit> getAllUnit() {
        return unitDao.getAllUnit();
    }

    @Override
    public Unit getUnitById(int unitId) {
        return unitId > 0 ? unitDao.getUnitById(unitId):null;
    }

    @Override
    public Unit getUnitByName(String unitName) {
        unitName = unitName.toLowerCase();
        return unitName != null ? unitDao.getUnitByName(unitName):null;
    }

    @Override
    public boolean addUnit(Unit unit) {
        return unit != null ? unitDao.addUnit(unit):false;
    }

    @Override
    public boolean editUnit(Unit unit) {
        return unit != null ? unitDao.editUnit(unit):false;
    }

    @Override
    public boolean deleteUnit(Unit unit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
