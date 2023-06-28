package za.co.bakery.dao;

import java.util.List;
import za.co.bakery.model.Role;

public interface RoleDao {
    List<Role> getAllRoles();
    Role getByName(String roleName);
    Role getById(int roleId);
    boolean addRole(Role role);
    boolean editRole(Role role);
    boolean deleteRole(Role role);
}
