package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.UnitDao;
import za.co.bakery.model.Unit;

public class UnitDaoImpl implements UnitDao{
    private static UnitDaoImpl unitDaoImpl = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    List<Unit> lstUnit = null;
    Unit unit = null;

    private UnitDaoImpl(Connection dbConnection) {
        this.con = dbConnection;
    }
    
    public static UnitDaoImpl getInstance(Connection dbConnection){
        if(unitDaoImpl == null){
            unitDaoImpl = new UnitDaoImpl(dbConnection);
        }
        return unitDaoImpl;
    } 
    
    @Override
    public List<Unit> getAllUnit() {
        lstUnit = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT unitId, unitName FROM unit");
                rs = ps.executeQuery();
                while(rs.next()){
                    lstUnit.add(new Unit(rs.getInt("unitId"), rs.getString("unitName")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get allUnit ERROR: " + exception.getMessage());
            }
            finally{
                if(ps != null){
                    try{
                        ps.close();
                    }
                    catch(SQLException exception){
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return !lstUnit.isEmpty() ? lstUnit : null;
    }

    @Override
    public Unit getUnitById(int unitId) {
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT unitId, unitName FROM unit WHERE unitId=?");
                ps.setInt(1, unitId);
                rs = ps.executeQuery();
                while(rs.next()){
                    unit = new Unit(rs.getInt("unitId"), rs.getString("unitName"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get allUnit ERROR: " + exception.getMessage());
            }
            finally{
                if(ps != null){
                    try{
                        ps.close();
                    }
                    catch(SQLException exception){
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return unit;
    }

    @Override
    public Unit getUnitByName(String unitName) {
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT unitId, unitName FROM unit WHERE unitName=?");
                ps.setString(1, unitName);
                rs = ps.executeQuery();
                while(rs.next()){
                    unit = new Unit(rs.getInt("unitId"), rs.getString("unitName"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get allUnit ERROR: " + exception.getMessage());
            }
            finally{
                if(ps != null){
                    try{
                        ps.close();
                    }
                    catch(SQLException exception){
                        System.out.println("Close prepared statment ERROR: " + exception.getMessage());
                    }
                }
            }
        }
        return unit;
    }

    @Override
    public boolean addUnit(Unit unit) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO unit (unitName) values(?)");
                ps.setString(1, unit.getUnitName());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Add unit Error!!: " + exception.getMessage());
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
        return retVal;
    }

    @Override
    public boolean editUnit(Unit unit) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE unit SET unitName=? WHERE unitId=?");
                ps.setInt(1, unit.getUnitId());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException exception) {
                System.out.println("Edit unit Error!!: " + exception.getMessage());
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
        return retVal;
    }

    @Override
    public boolean deleteUnit(Unit unit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
