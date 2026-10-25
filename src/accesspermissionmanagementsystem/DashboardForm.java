/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */
public class DashboardForm extends javax.swing.JFrame {

    /**
     * Creates new form DashboardForm
     */
    private UserAccount loggedInAccount;
    private final PermissionCheckDAO permissionCheckDAO =
        new PermissionCheckDAO();

    public DashboardForm(UserAccount account) {

    initComponents();

    this.loggedInAccount = account;

    loadUserInformation();
    loadUserPermissions();
    applyModernLayout();
}
    
     private void applyModernLayout() {

        java.awt.Color pageColor = new java.awt.Color(0xF1F5F9);
        java.awt.Color navy = new java.awt.Color(0x0F172A);
        java.awt.Color muted = new java.awt.Color(0x64748B);
        int userId = loggedInAccount.getUserId();

        javax.swing.JLabel appName = new javax.swing.JLabel(
                "ACCESS PERMISSION MANAGEMENT SYSTEM");
        appName.setFont(appName.getFont().deriveFont(java.awt.Font.BOLD, 12f));
        appName.setForeground(new java.awt.Color(0x94A3B8));
        appName.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JLabel welcome = new javax.swing.JLabel(
                "Welcome back, " + loggedInAccount.getUsername());
        welcome.setFont(welcome.getFont().deriveFont(java.awt.Font.BOLD, 26f));
        welcome.setForeground(java.awt.Color.WHITE);
        welcome.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JPanel chips = new javax.swing.JPanel();
        chips.setLayout(new javax.swing.BoxLayout(
                chips, javax.swing.BoxLayout.X_AXIS));
        chips.setOpaque(false);
        chips.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        for (Role role : new RoleCheckDAO().getUserRoles(userId)) {

            javax.swing.JLabel chip = new javax.swing.JLabel(role.getRoleName());
            chip.setOpaque(true);
            chip.setBackground(new java.awt.Color(0x1E40AF));
            chip.setForeground(java.awt.Color.WHITE);
            chip.setFont(chip.getFont().deriveFont(java.awt.Font.BOLD, 12f));
            chip.setBorder(javax.swing.BorderFactory.createEmptyBorder(
                    5, 12, 5, 12));

            chips.add(chip);
            chips.add(javax.swing.Box.createHorizontalStrut(8));
        }

        chips.add(javax.swing.Box.createHorizontalGlue());

        javax.swing.JPanel header = new javax.swing.JPanel();
        header.setLayout(new javax.swing.BoxLayout(
                header, javax.swing.BoxLayout.Y_AXIS));
        header.setBackground(navy);
        header.setBorder(javax.swing.BorderFactory.createEmptyBorder(
                24, 32, 24, 32));
        header.add(appName);
        header.add(javax.swing.Box.createVerticalStrut(6));
        header.add(welcome);
        header.add(javax.swing.Box.createVerticalStrut(14));
        header.add(chips);

        javax.swing.JPanel body = new javax.swing.JPanel(
                new java.awt.BorderLayout(0, 20));
        body.setBackground(pageColor);
        body.setBorder(javax.swing.BorderFactory.createEmptyBorder(
                24, 32, 24, 32));

        if (permissionCheckDAO.hasPermission(userId, "VIEW", "Reports")) {

            DashboardDAO stats = new DashboardDAO();

            javax.swing.JPanel cards = new javax.swing.JPanel(
                    new java.awt.GridLayout(1, 5, 16, 0));
            cards.setOpaque(false);

            cards.add(statCard("Users", stats.getTotalUsers(),
                    new java.awt.Color(0x2563EB)));
            cards.add(statCard("Roles", stats.getTotalRoles(),
                    new java.awt.Color(0x7C3AED)));
            cards.add(statCard("Logins today", stats.getLoginsToday(),
                    new java.awt.Color(0x059669)));
            cards.add(statCard("Denied (24h)", stats.getDeniedLast24Hours(),
                    new java.awt.Color(0xDC2626)));
            cards.add(statCard("Role clashes", stats.getUsersWithRoleClashes(),
                    new java.awt.Color(0xD97706)));

            body.add(cards, java.awt.BorderLayout.NORTH);
            body.add(buildActivityPanel(), java.awt.BorderLayout.CENTER);

        } else {

            javax.swing.JLabel note = new javax.swing.JLabel(
                    "Use the menu bar above to open the screens "
                  + "you have access to.");
            note.setFont(note.getFont().deriveFont(15f));
            note.setForeground(muted);

            body.add(note, java.awt.BorderLayout.NORTH);
        }

        javax.swing.JPanel root = new javax.swing.JPanel(
                new java.awt.BorderLayout());
        root.setBackground(pageColor);
        root.add(header, java.awt.BorderLayout.NORTH);
        root.add(body, java.awt.BorderLayout.CENTER);

        setContentPane(root);
        setTitle("Dashboard - Access Permission Management System");
        setSize(1150, 700);
        setLocationRelativeTo(null);
    }

    private javax.swing.JPanel statCard(String label, int value,
                                        java.awt.Color accent) {

        javax.swing.JLabel number = new javax.swing.JLabel(
                String.valueOf(value));
        number.setFont(number.getFont().deriveFont(java.awt.Font.BOLD, 30f));
        number.setForeground(new java.awt.Color(0x0F172A));

        javax.swing.JLabel caption = new javax.swing.JLabel(label);
        caption.setFont(caption.getFont().deriveFont(13f));
        caption.setForeground(new java.awt.Color(0x64748B));

        javax.swing.JPanel card = new javax.swing.JPanel();
        card.setLayout(new javax.swing.BoxLayout(
                card, javax.swing.BoxLayout.Y_AXIS));
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(0xD9E0EA)),
                javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createMatteBorder(
                                0, 4, 0, 0, accent),
                        javax.swing.BorderFactory.createEmptyBorder(
                                14, 16, 14, 16))));

        card.add(number);
        card.add(caption);

        return card;
    }

    private javax.swing.JPanel buildActivityPanel() {

        javax.swing.JLabel title = new javax.swing.JLabel("Recent activity");
        title.setFont(title.getFont().deriveFont(java.awt.Font.BOLD, 16f));
        title.setForeground(new java.awt.Color(0x0F172A));

        javax.swing.JTable table = new javax.swing.JTable(
                new OrganizationDAO().buildModel(
                        "SELECT l.action_time, u.full_name, "
                      + "CONCAT(o.operation_name, ' on ', r.resource_name), "
                      + "l.result "
                      + "FROM Access_Logs l "
                      + "JOIN Users u ON u.user_id = l.user_id "
                      + "JOIN Operations o ON o.operation_id = l.operation_id "
                      + "JOIN Resources r ON r.resource_id = l.resource_id "
                      + "ORDER BY l.action_time DESC LIMIT 12",
                        new String[]{"Time", "User", "Action", "Result"}));

        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        table.getColumnModel().getColumn(3).setCellRenderer(
                new javax.swing.table.DefaultTableCellRenderer() {

            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable t, Object value, boolean selected,
                    boolean focus, int row, int col) {

                java.awt.Component component =
                        super.getTableCellRendererComponent(
                                t, value, selected, focus, row, col);

                if (!selected) {
                    component.setForeground(
                            "DENIED".equals(String.valueOf(value))
                                    ? new java.awt.Color(0xDC2626)
                                    : new java.awt.Color(0x059669));
                }

                return component;
            }
        });

        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);
        scroll.setBorder(javax.swing.BorderFactory.createLineBorder(
                new java.awt.Color(0xD9E0EA)));

        javax.swing.JPanel panel = new javax.swing.JPanel(
                new java.awt.BorderLayout(0, 10));
        panel.setOpaque(false);
        panel.add(title, java.awt.BorderLayout.NORTH);
        panel.add(scroll, java.awt.BorderLayout.CENTER);

        return panel;
    }

    private void loadUserInformation() {

        usernameLabel.setText(
                "Username: " + loggedInAccount.getUsername()
        );

        RoleCheckDAO roleCheckDAO = new RoleCheckDAO();

        java.util.List<Role> roles
                = roleCheckDAO.getUserRoles(
                        loggedInAccount.getUserId()
                );

        StringBuilder roleText
                = new StringBuilder("Roles: ");

        for (Role role : roles) {

            roleText.append(role.getRoleName());
            roleText.append("  ");
        }

        rolesLabel.setText(roleText.toString());
    }
    
    private void loadUserPermissions() {

    int userId = loggedInAccount.getUserId();
    
    organizationMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "UPDATE",
                    "Roles"
            )
    );
    
    assignPermissionMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "UPDATE",
                    "Roles"
            )
    );
    
    assignRoleMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "UPDATE",
                    "Users"
            )
    );

    usersMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "VIEW",
                    "Users"
            )
    );

    rolesMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "VIEW",
                    "Roles"
            )
    );

    permissionsMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "VIEW",
                    "Permissions"
            )
    );

    resourcesMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "VIEW",
                    "Resources"
            )
    );

    operationsMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "VIEW",
                    "Operations"
            )
    );

    sessionsMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "VIEW",
                    "Sessions"
            )
    );

    accessLogsMenuItem.setVisible(
            permissionCheckDAO.hasPermission(
                    userId,
                    "VIEW",
                    "Access Logs"
            )
    );
    
    boolean canViewReports = permissionCheckDAO.hasPermission(
        userId,
        "VIEW",
        "Reports"
    );

    userAccountsReportMenuItem.setVisible(canViewReports);
    roleAssignmentsReportMenuItem.setVisible(canViewReports);
    rolePermissionsReportMenuItem.setVisible(canViewReports);
    loginSessionsReportMenuItem.setVisible(canViewReports);
    accessAuditReportMenuItem.setVisible(canViewReports);
    
    accessReviewMenuItem.setVisible(
        permissionCheckDAO.hasPermission(
                userId,
                "VIEW",
                "Access Reviews"
        )
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

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        rolesLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        dashboardMenu = new javax.swing.JMenu();
        managementMenu = new javax.swing.JMenu();
        usersMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        rolesMenuItem = new javax.swing.JMenuItem();
        assignRoleMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        permissionsMenuItem = new javax.swing.JMenuItem();
        assignPermissionMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        resourcesMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        operationsMenuItem = new javax.swing.JMenuItem();
        organizationMenuItem = new javax.swing.JMenuItem();
        securityMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        userAccountsReportMenuItem = new javax.swing.JMenuItem();
        roleAssignmentsReportMenuItem = new javax.swing.JMenuItem();
        rolePermissionsReportMenuItem = new javax.swing.JMenuItem();
        loginSessionsReportMenuItem = new javax.swing.JMenuItem();
        accessAuditReportMenuItem = new javax.swing.JMenuItem();
        sessionsMenuItem = new javax.swing.JMenuItem();
        activityMenu = new javax.swing.JMenu();
        accessLogsMenuItem = new javax.swing.JMenuItem();
        accessReviewMenuItem = new javax.swing.JMenuItem();
        accountMenu = new javax.swing.JMenu();
        logoutMenuItem = new javax.swing.JMenuItem();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ACCESS PERMISSION MANAGEMENT SYSTEM");

        jLabel2.setText("DASHBOARD");

        usernameLabel.setText("Username:");

        rolesLabel.setText("Roles:");

        dashboardMenu.setText("DASHBOARD");
        jMenuBar1.add(dashboardMenu);

        managementMenu.setText("Management");

        usersMenuItem.setText("Users");
        usersMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersMenuItemActionPerformed(evt);
            }
        });
        managementMenu.add(usersMenuItem);
        managementMenu.add(jSeparator1);

        rolesMenuItem.setText("Roles");
        rolesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rolesMenuItemActionPerformed(evt);
            }
        });
        managementMenu.add(rolesMenuItem);

        assignRoleMenuItem.setText("Assign Roles");
        assignRoleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignRoleMenuItemActionPerformed(evt);
            }
        });
        managementMenu.add(assignRoleMenuItem);
        managementMenu.add(jSeparator2);

        permissionsMenuItem.setText("Permissions");
        permissionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                permissionsMenuItemActionPerformed(evt);
            }
        });
        managementMenu.add(permissionsMenuItem);

        assignPermissionMenuItem.setText("Assign Permissions");
        assignPermissionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignPermissionMenuItemActionPerformed(evt);
            }
        });
        managementMenu.add(assignPermissionMenuItem);
        managementMenu.add(jSeparator3);

        resourcesMenuItem.setText("Resources");
        resourcesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resourcesMenuItemActionPerformed(evt);
            }
        });
        managementMenu.add(resourcesMenuItem);
        managementMenu.add(jSeparator4);

        operationsMenuItem.setText("Operations");
        operationsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operationsMenuItemActionPerformed(evt);
            }
        });
        managementMenu.add(operationsMenuItem);

        organizationMenuItem.setText("Organization Setup");
        organizationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organizationMenuItemActionPerformed(evt);
            }
        });
        managementMenu.add(organizationMenuItem);

        jMenuBar1.add(managementMenu);

        securityMenu.setText("Security");

        jMenu1.setText("Activity Reports");

        userAccountsReportMenuItem.setText("User Accounts Report");
        userAccountsReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userAccountsReportMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(userAccountsReportMenuItem);

        roleAssignmentsReportMenuItem.setText("Role Assignments Report");
        roleAssignmentsReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleAssignmentsReportMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(roleAssignmentsReportMenuItem);

        rolePermissionsReportMenuItem.setText("Role Permissions Report");
        rolePermissionsReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rolePermissionsReportMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(rolePermissionsReportMenuItem);

        loginSessionsReportMenuItem.setText("Login and Sessions Report");
        loginSessionsReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginSessionsReportMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(loginSessionsReportMenuItem);

        accessAuditReportMenuItem.setText("Access Activity and Security Audit Report");
        accessAuditReportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accessAuditReportMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(accessAuditReportMenuItem);

        securityMenu.add(jMenu1);

        sessionsMenuItem.setText("Sessions");
        sessionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionsMenuItemActionPerformed(evt);
            }
        });
        securityMenu.add(sessionsMenuItem);

        jMenuBar1.add(securityMenu);

        activityMenu.setText("Activity");

        accessLogsMenuItem.setText("Access Logs");
        accessLogsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accessLogsMenuItemActionPerformed(evt);
            }
        });
        activityMenu.add(accessLogsMenuItem);

        accessReviewMenuItem.setText("Access Review");
        accessReviewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accessReviewMenuItemActionPerformed(evt);
            }
        });
        activityMenu.add(accessReviewMenuItem);

        jMenuBar1.add(activityMenu);

        accountMenu.setText("Account");

        logoutMenuItem.setText("Logout");
        logoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuItemActionPerformed(evt);
            }
        });
        accountMenu.add(logoutMenuItem);

        jMenuBar1.add(accountMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rolesLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(70, 70, 70)
                .addComponent(usernameLabel)
                .addGap(34, 34, 34)
                .addComponent(rolesLabel)
                .addContainerGap(165, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usersMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersMenuItemActionPerformed
        // TODO add your handling code here:
        UsersForm usersForm =
        new UsersForm(loggedInAccount);

        usersForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_usersMenuItemActionPerformed

    private void rolesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rolesMenuItemActionPerformed
        // TODO add your handling code here:
        RolesForm rolesForm =
        new RolesForm(loggedInAccount);

        rolesForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_rolesMenuItemActionPerformed

    private void permissionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_permissionsMenuItemActionPerformed
        // TODO add your handling code here:
        PermissionsForm permissionsForm =
        new PermissionsForm(loggedInAccount);

        permissionsForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_permissionsMenuItemActionPerformed

    private void resourcesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resourcesMenuItemActionPerformed
        // TODO add your handling code here:
        ResourcesForm resourcesForm =
        new ResourcesForm(loggedInAccount);

        resourcesForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_resourcesMenuItemActionPerformed

    private void operationsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operationsMenuItemActionPerformed
        // TODO add your handling code here:
        OperationsForm operationsForm =
        new OperationsForm(loggedInAccount);

        operationsForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_operationsMenuItemActionPerformed

    private void sessionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionsMenuItemActionPerformed
        // TODO add your handling code here:
        SessionsForm sessionsForm =
        new SessionsForm(loggedInAccount);

        sessionsForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_sessionsMenuItemActionPerformed

    private void accessLogsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accessLogsMenuItemActionPerformed
        // TODO add your handling code here:
        AccessLogsForm accessLogsForm =
        new AccessLogsForm(loggedInAccount);

        accessLogsForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_accessLogsMenuItemActionPerformed

    private void logoutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMenuItemActionPerformed
        // TODO add your handling code here:
        SessionDAO sessionDAO = new SessionDAO();

int sessionId =
        sessionDAO.getActiveSessionId(
                loggedInAccount.getUserId()
        );

if (sessionId != 0) {

    boolean sessionClosed =
            sessionDAO.closeSession(sessionId);

    if (!sessionClosed) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Warning: The session could not be closed.",
                "Logout Warning",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
    }
}

LoginForm loginForm = new LoginForm();

loginForm.setVisible(true);

this.dispose();
    }//GEN-LAST:event_logoutMenuItemActionPerformed

    private void accessReviewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accessReviewMenuItemActionPerformed
        // TODO add your handling code here:
        AccessReviewForm accessReviewForm =
            new AccessReviewForm(loggedInAccount);

        accessReviewForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_accessReviewMenuItemActionPerformed

    private void userAccountsReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userAccountsReportMenuItemActionPerformed
        // TODO add your handling code here:
        int userId = loggedInAccount.getUserId();

        if (!permissionCheckDAO.hasPermission(
            userId, "VIEW", "Reports")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "You do not have permission to view reports.",
                "Access Denied",
                javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    new UserAccountsReportForm(loggedInAccount).setVisible(true);
    }//GEN-LAST:event_userAccountsReportMenuItemActionPerformed

    private void roleAssignmentsReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleAssignmentsReportMenuItemActionPerformed
        // TODO add your handling code here:
        int userId = loggedInAccount.getUserId();

    if (!permissionCheckDAO.hasPermission(
            userId, "VIEW", "Reports")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "You do not have permission to view reports.",
                "Access Denied",
                javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    new RoleAssignmentsReportForm(loggedInAccount).setVisible(true);
    }//GEN-LAST:event_roleAssignmentsReportMenuItemActionPerformed

    private void rolePermissionsReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rolePermissionsReportMenuItemActionPerformed
        // TODO add your handling code here:
        int userId = loggedInAccount.getUserId();

    if (!permissionCheckDAO.hasPermission(
            userId, "VIEW", "Reports")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "You do not have permission to view reports.",
                "Access Denied",
                javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    new RolePermissionsReportForm(loggedInAccount).setVisible(true);
    }//GEN-LAST:event_rolePermissionsReportMenuItemActionPerformed

    private void loginSessionsReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginSessionsReportMenuItemActionPerformed
        // TODO add your handling code here:
        int userId = loggedInAccount.getUserId();

    if (!permissionCheckDAO.hasPermission(
            userId, "VIEW", "Reports")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "You do not have permission to view reports.",
                "Access Denied",
                javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    new LoginSessionsReportForm(loggedInAccount).setVisible(true);
    }//GEN-LAST:event_loginSessionsReportMenuItemActionPerformed

    private void accessAuditReportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accessAuditReportMenuItemActionPerformed
        // TODO add your handling code here:
        int userId = loggedInAccount.getUserId();

    if (!permissionCheckDAO.hasPermission(
            userId, "VIEW", "Reports")) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "You do not have permission to view reports.",
                "Access Denied",
                javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    new AccessAuditReportForm(loggedInAccount).setVisible(true);
    }//GEN-LAST:event_accessAuditReportMenuItemActionPerformed

    private void assignRoleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignRoleMenuItemActionPerformed
        // TODO add your handling code here:
        AssignRoleForm assignRoleForm = new AssignRoleForm(loggedInAccount);

        assignRoleForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_assignRoleMenuItemActionPerformed

    private void assignPermissionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignPermissionMenuItemActionPerformed
        // TODO add your handling code here:
        AssignPermissionForm assignPermissionForm = new AssignPermissionForm(loggedInAccount);

        assignPermissionForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_assignPermissionMenuItemActionPerformed

    private void organizationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organizationMenuItemActionPerformed
        // TODO add your handling code here:
        OrganizationForm organizationForm = new OrganizationForm(loggedInAccount);

        organizationForm.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_organizationMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(
                            info.getClassName()
                    );
                    break;
                }
            }

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(
                    DashboardForm.class.getName()
            ).log(
                    java.util.logging.Level.SEVERE,
                    null,
                    ex
            );
        }

        // Dashboard is opened from LoginForm after successful login.
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem accessAuditReportMenuItem;
    private javax.swing.JMenuItem accessLogsMenuItem;
    private javax.swing.JMenuItem accessReviewMenuItem;
    private javax.swing.JMenu accountMenu;
    private javax.swing.JMenu activityMenu;
    private javax.swing.JMenuItem assignPermissionMenuItem;
    private javax.swing.JMenuItem assignRoleMenuItem;
    private javax.swing.JMenu dashboardMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenuItem loginSessionsReportMenuItem;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JMenu managementMenu;
    private javax.swing.JMenuItem operationsMenuItem;
    private javax.swing.JMenuItem organizationMenuItem;
    private javax.swing.JMenuItem permissionsMenuItem;
    private javax.swing.JMenuItem resourcesMenuItem;
    private javax.swing.JMenuItem roleAssignmentsReportMenuItem;
    private javax.swing.JMenuItem rolePermissionsReportMenuItem;
    private javax.swing.JLabel rolesLabel;
    private javax.swing.JMenuItem rolesMenuItem;
    private javax.swing.JMenu securityMenu;
    private javax.swing.JMenuItem sessionsMenuItem;
    private javax.swing.JMenuItem userAccountsReportMenuItem;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JMenuItem usersMenuItem;
    // End of variables declaration//GEN-END:variables
}
