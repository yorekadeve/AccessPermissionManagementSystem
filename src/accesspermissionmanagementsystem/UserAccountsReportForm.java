/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class UserAccountsReportForm extends ReportWindow {

    public UserAccountsReportForm(UserAccount account) {
        super(
            "User Accounts Report",
            "Review system users, login accounts and account status.",
            "SELECT "
            + "u.user_id AS 'User ID', "
            + "u.full_name AS 'Full Name', "
            + "u.email AS Email, "
            + "u.status AS 'User Status', "
            + "a.username AS Username, "
            + "a.account_status AS 'Account Status', "
            + "a.last_login AS 'Last Login' "
            + "FROM Users u "
            + "LEFT JOIN User_Accounts a "
            + "ON a.user_id = u.user_id "
            + "ORDER BY u.full_name"
        );
    }
}

