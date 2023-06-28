package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.UserDao;
import za.co.bakery.dao.impl.UserDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.User;
import za.co.bakery.service.UserService;

public class UserServiceImpl implements UserService {

    UserDao userDao;

    public UserServiceImpl(DBManager dBManager) {
        this.userDao = UserDaoImpl.getInstance(dBManager.getConnection());
    }

    @Override
    public boolean addUser(User user) {
        return user != null ? userDao.addUser(user):false;
    }

    @Override
    public boolean editUser(User user) {
        return user != null ? userDao.editUser(user):false;
    }

    @Override
    public User userLogin(User user) {
        return user != null ? userDao.userLogin(user):null;
    }

    @Override
    public User getUser(String username) {
        username = username.toLowerCase();
        return !username.isEmpty() ? userDao.getUser(username):null;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

}
