package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.co.bakery.dao.RoleDao;
import za.co.bakery.model.Role;

public class RoleDaoImpl implements RoleDao{
    private static RoleDao roleDao = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private List<Role> lstRoles;

    private RoleDaoImpl(Connection con) {
        this.con = con;
    }

    public static RoleDao getInstance(Connection dbCon) {
        if (roleDao == null) {
            roleDao = new RoleDaoImpl(dbCon);
        }
        return roleDao;
    }
    
    @Override
    public Role getByName(String roleName) {
        Role role = null;
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT roleId, roleName, active FROM role WHERE roleName=?");
                ps.setString(1, roleName);
                rs = ps.executeQuery();
                while(rs.next()){
                    role = new Role(rs.getInt("roleId"), rs.getString("roleName"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get roleByName ERROR: " + exception.getMessage());
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
        return role;
    }
    
    @Override
    public Role getById(int roleId) {
        Role role = null;
        if(con != null){
            try{
                ps = con.prepareStatement("SELECT roleId, roleName, active FROM role WHERE roleId=?");
                ps.setInt(1, roleId);
                rs = ps.executeQuery();
                while(rs.next()){
                    role = new Role(rs.getInt("roleId"), rs.getString("roleName"), rs.getBoolean("active"));
                }
            }
            catch(SQLException exception){
                System.out.println("Get roleById ERROR: " + exception.getMessage());
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
        return role;
    }
    
    @Override
    public List<Role> getAllRoles() {
        lstRoles = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("Select roleId, roleName, active FROM role");
                rs = ps.executeQuery();
                while(rs.next()){
                    lstRoles.add(new Role(rs.getInt("roleId"), rs.getString("roleName"), rs.getBoolean("active")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get allRole ERROR: " + exception.getMessage());
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
        return lstRoles;
    }

    @Override
    public boolean addRole(Role role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean editRole(Role role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteRole(Role role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
