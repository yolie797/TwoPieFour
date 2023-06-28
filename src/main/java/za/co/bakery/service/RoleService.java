package za.co.bakery.service;

import java.util.List;
import za.co.bakery.model.Role;

public interface RoleService {
    List<Role> getAllRoles();
    Role getByName(String roleName);
    Role getById(int roleId);
    boolean addRole(Role role);
    boolean editRole(Role role);
    boolean deleteRole(Role role);
}
