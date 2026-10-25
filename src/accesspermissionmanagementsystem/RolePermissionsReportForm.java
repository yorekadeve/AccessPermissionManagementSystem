/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class RolePermissionsReportForm extends ReportWindow {

    public RolePermissionsReportForm(UserAccount account) {
        super(
            "Role Permissions Report",
            "Review which operations each role may perform on resources.",
            "SELECT "
            + "r.role_name AS Role, "
            + "res.resource_name AS Resource, "
            + "o.operation_name AS Operation, "
            + "p.description AS 'Permission Description' "
            + "FROM Roles r "
            + "JOIN Role_Permissions rp "
            + "ON rp.role_id = r.role_id "
            + "JOIN Permissions p "
            + "ON p.permission_id = rp.permission_id "
            + "JOIN Operations o "
            + "ON o.operation_id = p.operation_id "
            + "JOIN Resources res "
            + "ON res.resource_id = p.resource_id "
            + "ORDER BY r.role_name, res.resource_name, "
            + "o.operation_name"
        );
    }
}

