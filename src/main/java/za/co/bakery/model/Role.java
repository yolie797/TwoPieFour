package za.co.bakery.model;

import java.util.Objects;


public class Role {
  private int roleId;
  private String roleName;
  private boolean active;

    public Role() {
    }

    public Role(int roleId) {
        this.roleId = roleId;
    }
    
    public Role(String roleName, boolean active) {
        this.roleName = roleName;
        this.active = active;
    }
    
    public Role(int roleId, String roleName, boolean active) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.active = active;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.roleId;
        hash = 37 * hash + Objects.hashCode(this.roleName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (this.roleId != other.roleId) {
            return false;
        }
        if (!Objects.equals(this.roleName, other.roleName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Role{" + "roleId=" + roleId + ", roleName=" + roleName + '}';
    }
    
}
