/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package accesspermissionmanagementsystem;

/**
 *
 * @author yorekadev
 */
public class LoginForm extends javax.swing.JFrame {

    /**
     * Creates new form LoginForm
     */
        public LoginForm() {
        initComponents();
        applyModernLayout();
    }

    private void applyModernLayout() {

        java.awt.Color pageColor = new java.awt.Color(0xEEF2F7);
        java.awt.Color borderColor = new java.awt.Color(0xD9E0EA);
        java.awt.Color titleColor = new java.awt.Color(0x0F172A);
        java.awt.Color mutedColor = new java.awt.Color(0x64748B);

        jLabel1.setText("<html>Access Permission<br>Management System</html>");
        jLabel1.setFont(jLabel1.getFont().deriveFont(java.awt.Font.BOLD, 24f));
        jLabel1.setForeground(titleColor);
        jLabel1.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JLabel subtitle = new javax.swing.JLabel("Sign in to continue");
        subtitle.setFont(subtitle.getFont().deriveFont(14f));
        subtitle.setForeground(mutedColor);
        subtitle.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        jLabel2.setText("Username");
        jLabel2.setFont(jLabel2.getFont().deriveFont(java.awt.Font.BOLD, 13f));
        jLabel2.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        jLabel3.setText("Password");
        jLabel3.setFont(jLabel3.getFont().deriveFont(java.awt.Font.BOLD, 13f));
        jLabel3.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        usernameField.putClientProperty(
                "JTextField.placeholderText", "Enter your username");
        passwordField.putClientProperty(
                "JTextField.placeholderText", "Enter your password");
        passwordField.putClientProperty(
                "JPasswordField.showRevealButton", true);

        usernameField.setFont(usernameField.getFont().deriveFont(14f));
        passwordField.setFont(passwordField.getFont().deriveFont(14f));

        for (javax.swing.JComponent field
                : new javax.swing.JComponent[]{usernameField, passwordField}) {
            field.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
            field.setPreferredSize(new java.awt.Dimension(300, 40));
            field.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 40));
        }

        loginButton.setText("Sign In");
        loginButton.setFont(loginButton.getFont().deriveFont(java.awt.Font.BOLD, 15f));
        loginButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        loginButton.setPreferredSize(new java.awt.Dimension(300, 44));
        loginButton.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 44));
        loginButton.setFocusable(false);
        loginButton.setBackground(new java.awt.Color(0x2563EB));
        loginButton.setForeground(java.awt.Color.WHITE);
        loginButton.putClientProperty("FlatLaf.style",
                "borderWidth: 0; focusWidth: 0");

        javax.swing.JLabel footer = new javax.swing.JLabel(
                "Authorized users only. All activity is logged.");
        footer.setFont(footer.getFont().deriveFont(12f));
        footer.setForeground(mutedColor);
        footer.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        javax.swing.JPanel card = new javax.swing.JPanel();
        card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(borderColor),
                javax.swing.BorderFactory.createEmptyBorder(34, 38, 30, 38)));

        card.add(jLabel1);
        card.add(javax.swing.Box.createVerticalStrut(6));
        card.add(subtitle);
        card.add(javax.swing.Box.createVerticalStrut(28));
        card.add(jLabel2);
        card.add(javax.swing.Box.createVerticalStrut(6));
        card.add(usernameField);
        card.add(javax.swing.Box.createVerticalStrut(18));
        card.add(jLabel3);
        card.add(javax.swing.Box.createVerticalStrut(6));
        card.add(passwordField);
        card.add(javax.swing.Box.createVerticalStrut(26));
        card.add(loginButton);
        card.add(javax.swing.Box.createVerticalStrut(18));
        card.add(footer);

        card.setPreferredSize(new java.awt.Dimension(
                420, card.getPreferredSize().height));

        javax.swing.JPanel page = new javax.swing.JPanel(
                new java.awt.GridBagLayout());
        page.setBackground(pageColor);
        page.add(card);

        setContentPane(page);
        setTitle("Access Permission Management System");
        setSize(620, 600);
        setLocationRelativeTo(null);

        getRootPane().setDefaultButton(loginButton);
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
        usernameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ACCESS PERMISSION MANAGEMENT SYSTEM");

        jLabel2.setText("Username:");

        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Password:");

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(usernameField))
                .addGap(69, 69, 69))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(loginButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(loginButton)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        String username = usernameField.getText();
        String password =
        new String(
                passwordField.getPassword()
        );

if (username.isEmpty()
        || password.isEmpty()) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Please enter both username and password.",
            "Login",
            javax.swing.JOptionPane.WARNING_MESSAGE
    );

    return;
}

LoginDAO loginDAO =
        new LoginDAO();

UserAccount account =
        loginDAO.login(
                username,
                password
        );

if (account != null) {

    if (!"ACTIVE".equalsIgnoreCase(
            account.getAccountStatus())) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Your account is disabled. "
                + "Please contact the administrator.",
                "Login Rejected",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    SessionDAO sessionDAO =
            new SessionDAO();

    sessionDAO.createSession(
            account.getUserId()
    );

    javax.swing.JOptionPane.showMessageDialog(
        this,
        "Welcome, " + account.getUsername() + "!",
        "Login Successful",
        javax.swing.JOptionPane.INFORMATION_MESSAGE
    );

    DashboardForm dashboardForm =
        new DashboardForm(account);

    dashboardForm.setVisible(true);

    this.dispose();

    } else {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            "Invalid username or password.",
            "Login Failed",
            javax.swing.JOptionPane.ERROR_MESSAGE
    );
}
    }//GEN-LAST:event_loginButtonActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
