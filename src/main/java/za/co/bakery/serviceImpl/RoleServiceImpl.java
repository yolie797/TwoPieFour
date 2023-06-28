package za.co.bakery.serviceImpl;

import java.util.List;
import za.co.bakery.dao.RoleDao;
import za.co.bakery.dao.impl.RoleDaoImpl;
import za.co.bakery.db.manager.DBManager;
import za.co.bakery.model.Role;
import za.co.bakery.service.RoleService;

public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    public RoleServiceImpl(DBManager dbManager) {
        this.roleDao = RoleDaoImpl.getInstance(dbManager.getConnection());
    }
    
    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getByName(String roleName) {
        roleName = roleName.toLowerCase();
        return roleName != null ? roleDao.getByName(roleName):null;
    }
    
    @Override
    public Role getById(int roleId) {
        return roleId > 0 ? roleDao.getById(roleId):null;
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
