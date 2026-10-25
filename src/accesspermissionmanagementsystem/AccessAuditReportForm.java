/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class AccessAuditReportForm extends ReportWindow {

    public AccessAuditReportForm(UserAccount account) {
        super(
            "Access Activity and Security Audit Report",
            "Review access attempts, affected resources and outcomes.",
            "SELECT "
            + "l.log_id AS 'Log ID', "
            + "u.full_name AS 'User', "
            + "o.operation_name AS Operation, "
            + "r.resource_name AS Resource, "
            + "l.target_record AS 'Target Record', "
            + "l.action_time AS 'Action Time', "
            + "l.result AS Result "
            + "FROM Access_Logs l "
            + "JOIN Users u ON u.user_id = l.user_id "
            + "JOIN Operations o "
            + "ON o.operation_id = l.operation_id "
            + "JOIN Resources r "
            + "ON r.resource_id = l.resource_id "
            + "ORDER BY l.action_time DESC"
        );
    }
}

