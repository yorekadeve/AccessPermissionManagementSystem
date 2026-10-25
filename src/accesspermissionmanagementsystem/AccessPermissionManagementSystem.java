/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package accesspermissionmanagementsystem;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public class AccessPermissionManagementSystem {

    public static void main(String[] args) {

        FlatLightLaf.setup();

        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Table.rowHeight", 26);

        java.awt.EventQueue.invokeLater(
                () -> new LoginForm().setVisible(true)
        );
    }
}
