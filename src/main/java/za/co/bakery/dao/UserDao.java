package za.co.bakery.dao;

import java.util.List;
import za.co.bakery.model.User;

public interface UserDao {
    boolean addUser(User user);
    boolean editUser(User user);
    User userLogin(User user);
    User getUser(String username);
    List<User> getAllUsers();
    List<User> getActiveUsers();
    List<User> getUsersByRole(int roleId);
}
