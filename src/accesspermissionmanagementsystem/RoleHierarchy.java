/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class RoleHierarchy {

    private int parentRoleId;
    private int childRoleId;

    public RoleHierarchy() {
    }

    public RoleHierarchy(int parentRoleId, int childRoleId) {
        this.parentRoleId = parentRoleId;
        this.childRoleId = childRoleId;
    }

    public int getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(int parentRoleId) {
        this.parentRoleId = parentRoleId;
    }

    public int getChildRoleId() {
        return childRoleId;
    }

    public void setChildRoleId(int childRoleId) {
        this.childRoleId = childRoleId;
    }
}
