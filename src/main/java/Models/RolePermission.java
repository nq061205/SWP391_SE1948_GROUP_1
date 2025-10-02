/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author admin
 */
public class RolePermission {

    private int roleId;
    private Permission permission;

    public RolePermission() {
    }

    public RolePermission(int roleId, Permission permission) {
        this.roleId = roleId;
        this.permission = permission;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermissionId(Permission permission) {
        this.permission = permission;
    }
}
