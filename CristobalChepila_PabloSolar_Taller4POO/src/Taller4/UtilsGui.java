package Taller4;

import javax.swing.JOptionPane;

public class UtilsGui {
    public static void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
