/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class RoleAssignmentsReportForm extends ReportWindow {

    public RoleAssignmentsReportForm(UserAccount account) {
        super(
            "Role Assignments Report",
            "Review user-to-role assignments and users without roles.",
            "SELECT "
            + "u.user_id AS 'User ID', "
            + "u.full_name AS 'Full Name', "
            + "u.status AS 'User Status', "
            + "r.role_id AS 'Role ID', "
            + "r.role_name AS 'Role Name' "
            + "FROM Users u "
            + "LEFT JOIN User_Roles ur "
            + "ON ur.user_id = u.user_id "
            + "LEFT JOIN Roles r "
            + "ON r.role_id = ur.role_id "
            + "ORDER BY u.full_name, r.role_name"
        );
    }
}

