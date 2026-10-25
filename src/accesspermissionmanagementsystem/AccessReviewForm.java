/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */
public class AccessReviewForm extends javax.swing.JFrame {

    /**
     * Creates new form AccessReviewForm
     */
    private UserAccount loggedInAccount;

    private AccessReviewDAO accessReviewDAO =
        new AccessReviewDAO();

    private PermissionCheckDAO permissionCheckDAO =
        new PermissionCheckDAO();

    private java.util.List<String[]> reviewRecords =
        new java.util.ArrayList<>(); 
    private UserRoleDAO userRoleDAO =
        new UserRoleDAO();
    public AccessReviewForm(UserAccount account) {

    initComponents();

    this.loggedInAccount = account;

    if (this.loggedInAccount != null) {

        int userId =
                this.loggedInAccount.getUserId();

        boolean allowed =
                permissionCheckDAO.hasPermission(
                        userId,
                        "VIEW",
                        "Access Reviews"
                );

        if (!allowed) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Access denied. You do not have permission to view Access Reviews.",
                    "Permission Denied",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            this.dispose();

            return;
        }
        
        boolean canManageAccessReviews =
        permissionCheckDAO.hasPermission(
                userId,
                "MANAGE",
                "Access Reviews"
        );

       removeAccessButton.setEnabled(
        canManageAccessReviews
        );
    }

    loadReviewRecords();
}
    
    private void loadReviewRecords() {

    reviewRecords =
            accessReviewDAO.getPermissionUsageReview();

    displayRecords(reviewRecords);
}
    
    private void displayRecords(
        java.util.List<String[]> records) {

    javax.swing.table.DefaultTableModel model =
            new javax.swing.table.DefaultTableModel(
                    new Object[]{
                        "User ID",
                        "Full Name",
                        "Role",
                        "Permission ID",
                        "Operation",
                        "Resource",
                        "Description",
                        "Last Used",
                        "Status"
                    },
                    0
            ) {

        @Override
        public boolean isCellEditable(
                int row,
                int column) {

            return false;
        }
    };

    for (String[] record : records) {

        model.addRow(
                new Object[]{
                    record[0],
                    record[1],
                    record[3],
                    record[4],
                    record[5],
                    record[6],
                    record[7],
                    record[8] == null
                            ? "Never used"
                            : record[8],
                    record[9]
                }
        );
    }

    accessReviewTable.setModel(model);
}
    
    private void displayUnusedRecords() {

    java.util.List<String[]> unusedRecords =
            new java.util.ArrayList<>();

    for (String[] record : reviewRecords) {

        if ("UNUSED".equals(record[9])) {

            unusedRecords.add(record);
        }
    }

    displayRecords(unusedRecords);
}
    
    private String[] findSelectedReviewRecord(
        int modelRow) {

    if (modelRow < 0
            || modelRow >= reviewRecords.size()) {

        return null;
    }

    String userId =
            String.valueOf(
                    accessReviewTable
                            .getModel()
                            .getValueAt(modelRow, 0)
            );

    String fullName =
            String.valueOf(
                    accessReviewTable
                            .getModel()
                            .getValueAt(modelRow, 1)
            );

    String roleName =
            String.valueOf(
                    accessReviewTable
                            .getModel()
                            .getValueAt(modelRow, 2)
            );

    String permissionId =
            String.valueOf(
                    accessReviewTable
                            .getModel()
                            .getValueAt(modelRow, 3)
            );

    for (String[] record : reviewRecords) {

        if (record[0].equals(userId)
                && record[1].equals(fullName)
                && record[3].equals(roleName)
                && record[4].equals(permissionId)) {

            return record;
        }
    }

    return null;
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
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        accessReviewTable = new javax.swing.JTable();
        showUnusedButton = new javax.swing.JButton();
        showAllButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        removeAccessButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ACCESS REVIEW");

        jLabel2.setText("Permissions are reviewed against successful access during the last 6 months.");

        accessReviewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "User ID", "Full Name", "Role", "Permission ID", "Operation", "Resource", "Description", "Last Used", "Status"
            }
        ));
        jScrollPane1.setViewportView(accessReviewTable);

        showUnusedButton.setText("Show Unused Only");
        showUnusedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showUnusedButtonActionPerformed(evt);
            }
        });

        showAllButton.setText("Show All");
        showAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        removeAccessButton.setText("Remove Role Access");
        removeAccessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAccessButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(742, 742, 742)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backButton)
                .addGap(136, 136, 136))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(610, 610, 610)
                        .addComponent(showUnusedButton)
                        .addGap(27, 27, 27)
                        .addComponent(showAllButton)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton)
                        .addGap(29, 29, 29)
                        .addComponent(removeAccessButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(581, 581, 581)
                        .addComponent(jLabel2)))
                .addContainerGap(701, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(backButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showUnusedButton)
                    .addComponent(showAllButton)
                    .addComponent(refreshButton)
                    .addComponent(removeAccessButton)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showUnusedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showUnusedButtonActionPerformed
        // TODO add your handling code here:
        displayUnusedRecords();
    }//GEN-LAST:event_showUnusedButtonActionPerformed

    private void showAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllButtonActionPerformed
        // TODO add your handling code here:
        displayRecords(reviewRecords);
    }//GEN-LAST:event_showAllButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
        loadReviewRecords();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        DashboardForm dashboardForm =
        new DashboardForm(loggedInAccount);

        dashboardForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void removeAccessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAccessButtonActionPerformed
        // TODO add your handling code here:
        if (loggedInAccount == null) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "No logged-in account was found.",
                "Access Error",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    int reviewerUserId =
            loggedInAccount.getUserId();

    boolean allowed =
            permissionCheckDAO.hasPermission(
                    reviewerUserId,
                    "MANAGE",
                    "Access Reviews"
            );

    if (!allowed) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Access denied. You do not have permission to manage Access Reviews.",
                "Permission Denied",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    int selectedRow =
            accessReviewTable.getSelectedRow();

    if (selectedRow == -1) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Please select an access review record first.",
                "No Selection",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    int modelRow =
            accessReviewTable.convertRowIndexToModel(
                    selectedRow
            );

    String status =
            String.valueOf(
                    accessReviewTable
                            .getModel()
                            .getValueAt(modelRow, 8)
            );

    if (!"UNUSED".equals(status)) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "This permission is marked as USED.\n"
                + "Only UNUSED access should be considered for removal.",
                "Access Already Used",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    String[] selectedRecord =
            findSelectedReviewRecord(modelRow);

    if (selectedRecord == null) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "The selected review record could not be found.",
                "Review Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );

        return;
    }

    int userId =
            Integer.parseInt(selectedRecord[0]);

    String fullName =
            selectedRecord[1];

    int roleId =
            Integer.parseInt(selectedRecord[2]);

    String roleName =
            selectedRecord[3];

    String operationName =
            selectedRecord[5];

    String resourceName =
            selectedRecord[6];

    int confirmation =
            javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "You are about to remove the role assignment.\n\n"
                    + "User: " + fullName + "\n"
                    + "Role: " + roleName + "\n"
                    + "Unused permission: "
                    + operationName + " "
                    + resourceName + "\n\n"
                    + "WARNING:\n"
                    + "Removing this role will remove ALL permissions "
                    + "that this user inherits from this role.\n\n"
                    + "Do you want to continue?",
                    "Confirm Access Removal",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

    if (confirmation != javax.swing.JOptionPane.YES_OPTION) {

        return;
    }

    boolean removed =
            userRoleDAO.removeUserRole(
                    userId,
                    roleId
            );

    if (removed) {

    int sessionId =
            new SessionDAO().getActiveSessionId(
                    reviewerUserId
            );

    String targetRecord =
            "Removed role '" + roleName
            + "' from user '" + fullName
            + "' after access review";

    boolean logged =
            accessReviewDAO.logAccessRemoval(
                    reviewerUserId,
                    sessionId,
                    targetRecord
            );

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Role access removed successfully.\n\n"
            + "User: " + fullName + "\n"
            + "Role: " + roleName
            + (logged
                    ? "\n\nAccess-removal action recorded."
                    : "\n\nWarning: removal succeeded, "
                    + "but the audit entry could not be recorded."),
            "Access Removed",
            logged
                    ? javax.swing.JOptionPane.INFORMATION_MESSAGE
                    : javax.swing.JOptionPane.WARNING_MESSAGE
    );

    loadReviewRecords();

    } else {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "The role assignment could not be removed.",
                "Removal Failed",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_removeAccessButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AccessReviewForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccessReviewForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccessReviewForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccessReviewForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AccessReviewForm(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable accessReviewTable;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton removeAccessButton;
    private javax.swing.JButton showAllButton;
    private javax.swing.JButton showUnusedButton;
    // End of variables declaration//GEN-END:variables
}
