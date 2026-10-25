/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */

public class EmployeeRecord {

    private int employeeId;
    private int userId;
    private int departmentId;
    private String jobTitle;
    private String employmentStatus;

    public EmployeeRecord() {
    }

    public EmployeeRecord(int employeeId, int userId, int departmentId,
                          String jobTitle, String employmentStatus) {
        this.employeeId = employeeId;
        this.userId = userId;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.employmentStatus = employmentStatus;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
}
