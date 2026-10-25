/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class OrganizationForm extends JFrame {

    private final UserAccount loggedInAccount;

    private final PermissionCheckDAO permissionCheckDAO =
            new PermissionCheckDAO();

    private final OrganizationDAO dao = new OrganizationDAO();

    private List<Role> roles = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    private final JTable departmentsTable = new JTable();
    private final JTable deptRolesTable = new JTable();
    private final JTable hierarchyTable = new JTable();
    private final JTable conflictsTable = new JTable();
    private final JTable employeesTable = new JTable();

    private final JTextField deptNameField = new JTextField(14);
    private final JTextField deptDescField = new JTextField(20);

    private final JComboBox<String> drDeptCombo = new JComboBox<>();
    private final JComboBox<String> drRoleCombo = new JComboBox<>();

    private final JComboBox<String> seniorCombo = new JComboBox<>();
    private final JComboBox<String> juniorCombo = new JComboBox<>();

    private final JComboBox<String> conflictCombo1 = new JComboBox<>();
    private final JComboBox<String> conflictCombo2 = new JComboBox<>();

    private final JComboBox<String> empUserCombo = new JComboBox<>();
    private final JComboBox<String> empDeptCombo = new JComboBox<>();
    private final JTextField empTitleField = new JTextField(12);
    private final JTextField empDateField =
            new JTextField(java.time.LocalDate.now().toString(), 8);
    private final JComboBox<String> empStatusCombo = new JComboBox<>();

    public OrganizationForm(UserAccount account) {

        this.loggedInAccount = account;

        setTitle("Organization Setup");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 560);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        for (String status : dao.getEnumValues(
                "employee_records", "employment_status")) {
            empStatusCombo.addItem(status);
        }

        if (empStatusCombo.getItemCount() == 0) {
            empStatusCombo.addItem("ACTIVE");
        }

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Departments", buildDepartmentsTab());
        tabs.addTab("Department Roles", buildDepartmentRolesTab());
        tabs.addTab("Role Hierarchy", buildHierarchyTab());
        tabs.addTab("Role Conflicts", buildConflictsTab());
        tabs.addTab("Employee Records", buildEmployeesTab());

        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            new DashboardForm(loggedInAccount).setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel(
                new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        bottom.add(backButton);

        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(tabs, java.awt.BorderLayout.CENTER);
        getContentPane().add(bottom, java.awt.BorderLayout.SOUTH);

        refreshAll();
    }

    // ---------- layout helpers ----------

        private JPanel flow(java.awt.Component... items) {

        JPanel fields = new JPanel(
                new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        JPanel buttons = new JPanel(
                new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        for (java.awt.Component item : items) {

            if (item instanceof JButton) {
                buttons.add(item);
            } else {
                fields.add(item);
            }
        }

        JPanel panel = new JPanel(new java.awt.GridLayout(2, 1));
        panel.add(fields);
        panel.add(buttons);

        return panel;
    }

    private JPanel buildTab(JPanel controls, JTable table) {

        JPanel panel = new JPanel(new java.awt.BorderLayout());
        panel.add(controls, java.awt.BorderLayout.NORTH);
        panel.add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildDepartmentsTab() {

        JButton add = new JButton("Add Department");
        JButton remove = new JButton("Delete Selected");

        add.addActionListener(e -> addDepartment());
        remove.addActionListener(e -> deleteDepartment());

        return buildTab(flow(
                new JLabel("Name"), deptNameField,
                new JLabel("Description"), deptDescField,
                add, remove), departmentsTable);
    }

    private JPanel buildDepartmentRolesTab() {

        JButton add = new JButton("Allow Role");
        JButton remove = new JButton("Remove Selected");

        add.addActionListener(e -> addDepartmentRole());
        remove.addActionListener(e -> removeDepartmentRole());

        return buildTab(flow(
                new JLabel("Department"), drDeptCombo,
                new JLabel("Role"), drRoleCombo,
                add, remove), deptRolesTable);
    }

    private JPanel buildHierarchyTab() {

        JButton add = new JButton("Add Relationship");
        JButton remove = new JButton("Remove Selected");

        add.addActionListener(e -> addHierarchy());
        remove.addActionListener(e -> removeHierarchy());

        return buildTab(flow(
                new JLabel("Senior role (inherits)"), seniorCombo,
                new JLabel("Junior role"), juniorCombo,
                add, remove), hierarchyTable);
    }

    private JPanel buildConflictsTab() {

        JButton add = new JButton("Add Conflict");
        JButton remove = new JButton("Remove Selected");

        add.addActionListener(e -> addConflict());
        remove.addActionListener(e -> removeConflict());

        return buildTab(flow(
                new JLabel("Role"), conflictCombo1,
                new JLabel("cannot be held with"), conflictCombo2,
                add, remove), conflictsTable);
    }

    private JPanel buildEmployeesTab() {

        JButton add = new JButton("Add Record");
        JButton remove = new JButton("Delete Selected");

        add.addActionListener(e -> addEmployee());
        remove.addActionListener(e -> deleteEmployee());

        return buildTab(flow(
                new JLabel("User"), empUserCombo,
                new JLabel("Department"), empDeptCombo,
                new JLabel("Job title"), empTitleField,
                new JLabel("Date (yyyy-mm-dd)"), empDateField,
                new JLabel("Status"), empStatusCombo,
                add, remove), employeesTable);
    }

    // ---------- shared helpers ----------

    private boolean allowed() {

        if (permissionCheckDAO.hasPermission(
                loggedInAccount.getUserId(), "UPDATE", "Roles")) {
            return true;
        }

        JOptionPane.showMessageDialog(this,
                "Access denied. You do not have permission to change "
              + "organization setup.");

        return false;
    }

    private void message(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    private boolean confirm(String text) {

        return JOptionPane.showConfirmDialog(this, text, "Confirm",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void finish(String error, String successMessage) {

        if (error != null) {
            message(error);
            return;
        }

        message(successMessage);
        refreshAll();
    }

    private int selectedId(JTable table, int column, String what) {

        int row = table.getSelectedRow();

        if (row == -1) {
            message("Please select a " + what + " first.");
            return -1;
        }

        return Integer.parseInt(table.getValueAt(row, column).toString());
    }

    private void fillCombo(JComboBox<String> combo, List<String> items) {

        combo.removeAllItems();

        for (String item : items) {
            combo.addItem(item);
        }
    }

    private List<String> roleNames() {

        List<String> names = new ArrayList<>();

        for (Role role : roles) {
            names.add(role.getRoleName());
        }

        return names;
    }

    private List<String> departmentNames() {

        List<String> names = new ArrayList<>();

        for (Department department : departments) {
            names.add(department.getDepartmentName());
        }

        return names;
    }

    private List<String> userNames() {

        List<String> names = new ArrayList<>();

        for (User user : users) {
            names.add(user.getFullName() + " (ID " + user.getUserId() + ")");
        }

        return names;
    }

    private void refreshAll() {

        roles = new RoleDAO().getAllRoles();
        users = new UserDAO().getAllUsers();
        departments = new DepartmentDAO().getAllDepartments();

        fillCombo(drDeptCombo, departmentNames());
        fillCombo(empDeptCombo, departmentNames());
        fillCombo(drRoleCombo, roleNames());
        fillCombo(seniorCombo, roleNames());
        fillCombo(juniorCombo, roleNames());
        fillCombo(conflictCombo1, roleNames());
        fillCombo(conflictCombo2, roleNames());
        fillCombo(empUserCombo, userNames());

        departmentsTable.setModel(dao.buildModel(
                "SELECT department_id, department_name, description "
              + "FROM Departments ORDER BY department_id",
                new String[]{"ID", "Department", "Description"}));

        deptRolesTable.setModel(dao.buildModel(
                "SELECT d.department_id, d.department_name, "
              + "r.role_id, r.role_name "
              + "FROM Department_Roles dr "
              + "JOIN Departments d ON d.department_id = dr.department_id "
              + "JOIN Roles r ON r.role_id = dr.role_id "
              + "ORDER BY d.department_name, r.role_name",
                new String[]{"Dept ID", "Department", "Role ID", "Allowed Role"}));

        hierarchyTable.setModel(dao.buildModel(
                "SELECT p.role_id, p.role_name, c.role_id, c.role_name "
              + "FROM Role_Hierarchy rh "
              + "JOIN Roles p ON p.role_id = rh.parent_role_id "
              + "JOIN Roles c ON c.role_id = rh.child_role_id "
              + "ORDER BY p.role_name, c.role_name",
                new String[]{"Senior ID", "Senior Role",
                    "Junior ID", "Junior Role (inherited)"}));

        conflictsTable.setModel(dao.buildModel(
                "SELECT r1.role_id, r1.role_name, r2.role_id, r2.role_name "
              + "FROM Role_Conflicts rc "
              + "JOIN Roles r1 ON r1.role_id = rc.role_id_1 "
              + "JOIN Roles r2 ON r2.role_id = rc.role_id_2 "
              + "ORDER BY r1.role_name, r2.role_name",
                new String[]{"Role ID", "Role", "Conflicts With ID",
                    "Conflicting Role"}));

        employeesTable.setModel(dao.buildModel(
                "SELECT e.employee_id, u.full_name, d.department_name, "
              + "e.job_title, e.employment_date, e.employment_status "
              + "FROM Employee_Records e "
              + "JOIN Users u ON u.user_id = e.user_id "
              + "JOIN Departments d ON d.department_id = e.department_id "
              + "ORDER BY u.full_name",
                new String[]{"ID", "Employee", "Department", "Job Title",
                    "Employment Date", "Status"}));
    }

    // ---------- actions ----------

    private void addDepartment() {

        if (!allowed()) {
            return;
        }

        String name = deptNameField.getText().trim();

        if (name.isEmpty()) {
            message("Please enter a department name.");
            return;
        }

        String error = dao.addDepartment(name, deptDescField.getText().trim());

        if (error == null) {
            deptNameField.setText("");
            deptDescField.setText("");
        }

        finish(error, "Department added.");
    }

    private void deleteDepartment() {

        if (!allowed()) {
            return;
        }

        int id = selectedId(departmentsTable, 0, "department");

        if (id == -1 || !confirm("Delete this department?")) {
            return;
        }

        finish(dao.deleteDepartment(id), "Department deleted.");
    }

    private void addDepartmentRole() {

        if (!allowed()) {
            return;
        }

        int d = drDeptCombo.getSelectedIndex();
        int r = drRoleCombo.getSelectedIndex();

        if (d == -1 || r == -1) {
            message("Please select a department and a role.");
            return;
        }

        finish(dao.addDepartmentRole(
                departments.get(d).getDepartmentId(),
                roles.get(r).getRoleId()),
                "Role allowed for the department.");
    }

    private void removeDepartmentRole() {

        if (!allowed()) {
            return;
        }

        int departmentId = selectedId(deptRolesTable, 0, "row");

        if (departmentId == -1) {
            return;
        }

        int roleId = selectedId(deptRolesTable, 2, "row");

        finish(dao.removeDepartmentRole(departmentId, roleId),
                "Department rule removed.");
    }

    private void addHierarchy() {

        if (!allowed()) {
            return;
        }

        int s = seniorCombo.getSelectedIndex();
        int j = juniorCombo.getSelectedIndex();

        if (s == -1 || j == -1) {
            message("Please select a senior role and a junior role.");
            return;
        }

        finish(dao.addRoleHierarchy(
                roles.get(s).getRoleId(),
                roles.get(j).getRoleId()),
                "Relationship added.");
    }

    private void removeHierarchy() {

        if (!allowed()) {
            return;
        }

        int seniorId = selectedId(hierarchyTable, 0, "row");

        if (seniorId == -1) {
            return;
        }

        int juniorId = selectedId(hierarchyTable, 2, "row");

        finish(dao.removeRoleHierarchy(seniorId, juniorId),
                "Relationship removed.");
    }

    private void addConflict() {

        if (!allowed()) {
            return;
        }

        int a = conflictCombo1.getSelectedIndex();
        int b = conflictCombo2.getSelectedIndex();

        if (a == -1 || b == -1) {
            message("Please select two roles.");
            return;
        }

        finish(dao.addRoleConflict(
                roles.get(a).getRoleId(),
                roles.get(b).getRoleId()),
                "Conflict added.");
    }

    private void removeConflict() {

        if (!allowed()) {
            return;
        }

        int roleA = selectedId(conflictsTable, 0, "row");

        if (roleA == -1) {
            return;
        }

        int roleB = selectedId(conflictsTable, 2, "row");

        finish(dao.removeRoleConflict(roleA, roleB), "Conflict removed.");
    }

    private void addEmployee() {

        if (!allowed()) {
            return;
        }

        int u = empUserCombo.getSelectedIndex();
        int d = empDeptCombo.getSelectedIndex();
        String title = empTitleField.getText().trim();
        Object status = empStatusCombo.getSelectedItem();

        if (u == -1 || d == -1 || title.isEmpty() || status == null) {
            message("Please fill in the user, department, job title and status.");
            return;
        }

        java.sql.Date date;

        try {
            date = java.sql.Date.valueOf(empDateField.getText().trim());
        } catch (IllegalArgumentException ex) {
            message("The date must look like 2026-10-14.");
            return;
        }

        String error = dao.addEmployeeRecord(
                users.get(u).getUserId(),
                departments.get(d).getDepartmentId(),
                title,
                date,
                status.toString());

        if (error == null) {
            empTitleField.setText("");
        }

        finish(error, "Employee record added.");
    }

    private void deleteEmployee() {

        if (!allowed()) {
            return;
        }

        int id = selectedId(employeesTable, 0, "record");

        if (id == -1 || !confirm("Delete this employee record?")) {
            return;
        }

        finish(dao.deleteEmployeeRecord(id), "Employee record deleted.");
    }
}
