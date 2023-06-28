package za.co.bakery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import za.co.bakery.dao.UserDao;
import za.co.bakery.model.User;

public class UserDaoImpl implements UserDao {
    private List<User> lstUsers;
    private static UserDao userDao = null;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private UserDaoImpl(Connection con) {
        this.con = con;
    }

    public static UserDao getInstance(Connection dbCon) {
        if (userDao == null) {
            userDao = new UserDaoImpl(dbCon);
        }
        return userDao;
    }

    @Override
    public boolean addUser(User user) {
        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO user(userEmailAddress, roleId, password, fName, lName, title, phoneNo, active) values(?,?,?,?,?,?,?,?)");
                ps.setString(1, user.getEmail().toLowerCase());
                ps.setInt(2, user.getRoleId());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getfName());
                ps.setString(5, user.getLname());
                ps.setString(6, user.getTitle());
                ps.setString(7, user.getPhoneNo());
                ps.setBoolean(8, user.isActive());
                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Could not close: " + ex.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public boolean editUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User userLogin(User user) {
        User aUser = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("Select userEmailAddress, roleId, fName,lname,title,phoneNo,active from user WHERE userEmailAddress=? AND PASSWORD=?");
                ps.setString(1, user.getEmail().toLowerCase());
                ps.setString(2, user.getPassword());
                rs = ps.executeQuery();
                if (rs.next()){
                    aUser = new User(rs.getString("userEmailAddress"), rs.getInt("roleId"), rs.getString("fName"), rs.getString("lname"), rs.getString("title"), "", 
                            rs.getString("phoneNo"), rs.getBoolean("active"));
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Could not close: " + ex.getMessage());
                    }
                }
            }
        }
        return aUser;
    }

    @Override
    public List<User> getAllUsers() {
        lstUsers = new ArrayList<>();
        if(con != null){
            try{
                ps = con.prepareStatement("Select userEmailAddress, roleId, fName,lname,title,phoneNo,active from user");
                rs = ps.executeQuery();
                while(rs.next()){
                    lstUsers.add(new User(rs.getString("userEmailAddress"), rs.getInt("roleId"), rs.getString("fName"), rs.getString("lname"), rs.getString("title"), "", 
                            rs.getString("phoneNo"), rs.getBoolean("active")));
                }
            }
            catch(SQLException exception){
                System.out.println("Get product ERROR: " + exception.getMessage());
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
        return lstUsers;
    }

    @Override
    public List<User> getActiveUsers() {
        if(lstUsers == null){
            getAllUsers();
        }
        return lstUsers.stream().filter(user -> user.isActive()==true)
                                  .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersByRole(int roleId) {
        if(lstUsers == null){
            getAllUsers();
        }
        return lstUsers.stream().filter(user -> user.getRoleId() == roleId)
                                  .collect(Collectors.toList());
    }

    @Override
    public User getUser(String username) {
        User aUser = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("Select userEmailAddress, roleId, password, fName, lname, title, phoneNo, active from user WHERE userEmailAddress=? AND active=1");
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next()){
                    aUser = new User(rs.getString("userEmailAddress"), rs.getInt("roleId"), rs.getString("fName"), rs.getString("lname"), rs.getString("title"), rs.getString("password"), 
                            rs.getString("phoneNo"), rs.getBoolean("active"));
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Could not close: " + ex.getMessage());
                    }
                }
            }
        }
        return aUser;
    }
}
