/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */
public class UsersForm extends javax.swing.JFrame {

    /**
     * Creates new form UsersForm
     */
    
    private UserAccount loggedInAccount;
    
    private PermissionCheckDAO permissionCheckDAO =
        new PermissionCheckDAO();
    
    public UsersForm(UserAccount account) {

    initComponents();

    this.loggedInAccount = account;

    loadUsers();

    findButton.addActionListener(e -> findUser());

    firstButton.addActionListener(e -> goToFirstUser());

    previousButton.addActionListener(e -> goToPreviousUser());

    nextButton.addActionListener(e -> goToNextUser());
}
    
    private void loadUsers() {

    UserDAO userDAO = new UserDAO();

    java.util.List<User> users = userDAO.getAllUsers();

    javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) usersTable.getModel();

    model.setRowCount(0);

    for (User user : users) {

        model.addRow(new Object[]{
            user.getUserId(),
            user.getFullName(),
            user.getEmail(),
            user.getStatus()
        });
    }
}
    
    private void findUser() {

    if (!permissionCheckDAO.hasPermission(
            loggedInAccount.getUserId(),
            "VIEW",
            "Users")) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Access denied. You do not have permission to view users.",
                "Permission Denied",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    String search =
            javax.swing.JOptionPane.showInputDialog(
                    this,
                    "Enter User ID, name, or email:",
                    "Find User",
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

    if (search == null || search.trim().isEmpty()) {
        return;
    }

    search = search.trim().toLowerCase();

    for (int row = 0; row < usersTable.getRowCount(); row++) {

        String userId =
                usersTable.getValueAt(row, 0).toString();

        String fullName =
                usersTable.getValueAt(row, 1).toString();

        String email =
                usersTable.getValueAt(row, 2).toString();

        if (userId.toLowerCase().contains(search)
                || fullName.toLowerCase().contains(search)
                || email.toLowerCase().contains(search)) {

            usersTable.setRowSelectionInterval(row, row);

            usersTable.scrollRectToVisible(
                    usersTable.getCellRect(row, 0, true)
            );

            return;
        }
    }

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "No user was found matching: " + search,
            "User Not Found",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
    );
}


private void goToFirstUser() {

    if (usersTable.getRowCount() == 0) {
        return;
    }

    usersTable.setRowSelectionInterval(0, 0);

    usersTable.scrollRectToVisible(
            usersTable.getCellRect(0, 0, true)
    );
}


private void goToPreviousUser() {

    int selectedRow =
            usersTable.getSelectedRow();

    if (selectedRow == -1) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Please select a user first.",
                "No User Selected",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        return;
    }

    if (selectedRow == 0) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "You are already at the first user.",
                "First User",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        return;
    }

    int previousRow =
            selectedRow - 1;

    usersTable.setRowSelectionInterval(
            previousRow,
            previousRow
    );

    usersTable.scrollRectToVisible(
            usersTable.getCellRect(previousRow, 0, true)
    );
}


private void goToNextUser() {

    int selectedRow =
            usersTable.getSelectedRow();

    if (selectedRow == -1) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Please select a user first.",
                "No User Selected",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        return;
    }

    int lastRow =
            usersTable.getRowCount() - 1;

    if (selectedRow == lastRow) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "You are already at the last user.",
                "Last User",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        return;
    }

    int nextRow =
            selectedRow + 1;

    usersTable.setRowSelectionInterval(
            nextRow,
            nextRow
    );

    usersTable.scrollRectToVisible(
            usersTable.getCellRect(nextRow, 0, true)
    );
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        backButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        addUserButton = new javax.swing.JButton();
        updateUserButton = new javax.swing.JButton();
        deleteUserButton = new javax.swing.JButton();
        findButton = new javax.swing.JButton();
        firstButton = new javax.swing.JButton();
        previousButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        lastButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("USER MANAGEMENT");
        jLabel1.setRequestFocusEnabled(false);

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Full Name", "Email", "Status"
            }
        ));
        jScrollPane1.setViewportView(usersTable);

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        addUserButton.setText("Add User");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        updateUserButton.setText("Update User");
        updateUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateUserButtonActionPerformed(evt);
            }
        });

        deleteUserButton.setText("Delete User");
        deleteUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserButtonActionPerformed(evt);
            }
        });

        findButton.setText("Find");

        firstButton.setText("First");

        previousButton.setText("Previous");

        nextButton.setText("Next");

        lastButton.setText("Last");
        lastButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(updateUserButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(findButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(firstButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(previousButton)
                                .addGap(12, 12, 12)
                                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lastButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(350, 350, 350)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(405, 405, 405)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167)
                .addComponent(refreshButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton)
                    .addComponent(backButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addUserButton)
                    .addComponent(updateUserButton)
                    .addComponent(deleteUserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findButton)
                    .addComponent(firstButton)
                    .addComponent(previousButton)
                    .addComponent(nextButton)
                    .addComponent(lastButton))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        
        DashboardForm dashboardForm =
        new DashboardForm(loggedInAccount);

        dashboardForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
        loadUsers();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        // TODO add your handling code here:
        
        if (!permissionCheckDAO.hasPermission(
            loggedInAccount.getUserId(),
            "CREATE",
            "Users")) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Access denied. You do not have permission to create users."
        );

        return;
    }
        
        AddUserForm addUserForm =
        new AddUserForm(loggedInAccount);

        addUserForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void updateUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateUserButtonActionPerformed
        // TODO add your handling code here:
        
        if (!permissionCheckDAO.hasPermission(
        loggedInAccount.getUserId(),
        "UPDATE",
        "Users")) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Access denied. You do not have permission to update users."
    );

    return;
}
        
            int selectedRow = usersTable.getSelectedRow();

if (selectedRow == -1) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Please select a user to update."
    );

    return;
}

int userId =
        Integer.parseInt(
                usersTable.getValueAt(selectedRow, 0).toString()
        );

String fullName =
        usersTable.getValueAt(selectedRow, 1).toString();

String email =
        usersTable.getValueAt(selectedRow, 2).toString();

String status =
        usersTable.getValueAt(selectedRow, 3).toString();

User user = new User(
        userId,
        fullName,
        email,
        status
);

EditUserForm editUserForm =
        new EditUserForm(loggedInAccount, user);

editUserForm.setVisible(true);

this.dispose();
    }//GEN-LAST:event_updateUserButtonActionPerformed

    private void deleteUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserButtonActionPerformed
        // TODO add your handling code here:
        
        if (!permissionCheckDAO.hasPermission(
        loggedInAccount.getUserId(),
        "DELETE",
        "Users")) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Access denied. You do not have permission to delete users."
    );

    return;
}
        
        int selectedRow = usersTable.getSelectedRow();

if (selectedRow == -1) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Please select a user to delete."
    );

    return;
}

int userId =
        Integer.parseInt(
                usersTable.getValueAt(selectedRow, 0).toString()
        );

String fullName =
        usersTable.getValueAt(selectedRow, 1).toString();

int confirmation =
        javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete "
                + fullName + "?",
                "Confirm Delete",
                javax.swing.JOptionPane.YES_NO_OPTION
        );

if (confirmation != javax.swing.JOptionPane.YES_OPTION) {

    return;
}

UserDAO userDAO = new UserDAO();

boolean success = userDAO.deleteUser(userId);

if (success) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "User deleted successfully."
    );

    loadUsers();

} else {

    int deactivateConfirmation =
            javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "This user cannot be permanently deleted "
                    + "because they have related records in the system.\n\n"
                    + "Would you like to deactivate "
                    + fullName + " instead?",
                    "Delete User",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

    if (deactivateConfirmation
        == javax.swing.JOptionPane.YES_OPTION) {

    User user = new User(
            userId,
            fullName,
            usersTable.getValueAt(selectedRow, 2).toString(),
            "INACTIVE"
    );

    boolean deactivated =
            userDAO.updateUser(user);

    if (deactivated) {

        UserAccountDAO userAccountDAO =
                new UserAccountDAO();

        boolean accountDisabled =
                userAccountDAO.disableAccount(userId);

        if (accountDisabled) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "User has been deactivated successfully.\n"
                    + "Their login account has also been disabled."
            );

        } else {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "User was deactivated, but the login account "
                    + "could not be disabled.",
                    "Warning",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
        }

        loadUsers();

    } else {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "The user could not be deleted or deactivated.",
                "Operation Failed",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}
}
    }//GEN-LAST:event_deleteUserButtonActionPerformed

    private void lastButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastButtonActionPerformed
        // TODO add your handling code here:
        if (usersTable.getRowCount() == 0) {
        return;
    }

    int lastRow =
            usersTable.getRowCount() - 1;

    usersTable.setRowSelectionInterval(
            lastRow,
            lastRow
    );

    usersTable.scrollRectToVisible(
            usersTable.getCellRect(lastRow, 0, true)
    );
    }//GEN-LAST:event_lastButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UsersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsersForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // UsersForm is opened from DashboardForm after login.
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUserButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton deleteUserButton;
    private javax.swing.JButton findButton;
    private javax.swing.JButton firstButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton lastButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton previousButton;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton updateUserButton;
    private javax.swing.JTable usersTable;
    // End of variables declaration//GEN-END:variables
}
