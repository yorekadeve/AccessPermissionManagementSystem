/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class LoginSessionsReportForm extends ReportWindow {

    public LoginSessionsReportForm(UserAccount account) {
        super(
            "Login and Sessions Report",
            "Review login history and sessions that remain open.",
            "SELECT "
            + "s.session_id AS 'Session ID', "
            + "u.full_name AS 'Full Name', "
            + "a.username AS Username, "
            + "s.login_time AS 'Login Time', "
            + "s.logout_time AS 'Logout Time', "
            + "CASE WHEN s.logout_time IS NULL "
            + "THEN 'OPEN' ELSE 'CLOSED' END AS 'Session Status' "
            + "FROM Sessions s "
            + "JOIN Users u ON u.user_id = s.user_id "
            + "LEFT JOIN User_Accounts a "
            + "ON a.user_id = u.user_id "
            + "ORDER BY s.login_time DESC"
        );
    }
}

