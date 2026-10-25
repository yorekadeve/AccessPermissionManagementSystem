/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class RoleConflict {

    private int roleId;
    private int conflictingRoleId;

    public RoleConflict() {
    }

    public RoleConflict(int roleId, int conflictingRoleId) {
        this.roleId = roleId;
        this.conflictingRoleId = conflictingRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getConflictingRoleId() {
        return conflictingRoleId;
    }

    public void setConflictingRoleId(int conflictingRoleId) {
        this.conflictingRoleId = conflictingRoleId;
    }
}
