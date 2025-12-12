//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Presentación;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Dominio.Administrador;
import Dominio.Coordinador;
import Dominio.Estudiante;
import Dominio.Usuario;
import Logica.FileManager;
import Logica.Repositorio;



public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Sistema - Login");
        setSize(420,230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        FileManager.cargarTodos();
    }

    private void init() {
        JPanel main = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new GridLayout(2,2,6,6));
        center.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        center.add(new JLabel("Usuario:")); txtUser = new JTextField(); center.add(txtUser);
        center.add(new JLabel("Contraseña:")); txtPass = new JPasswordField(); center.add(txtPass);
        main.add(center, BorderLayout.CENTER);

        JPanel boton = new JPanel();
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e->doLogin());
        boton.add(btnLogin);
        main.add(boton, BorderLayout.SOUTH);

        add(main);
    }

    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword());
        Usuario usr = Repositorio.getInstance().findByUsername(u);
        if (usr != null && usr.autenticar(p)) {
            if (usr instanceof Administrador) new AdminFrame().setVisible(true);
            else if (usr instanceof Coordinador) new CoordinatorFrame().setVisible(true);
            else if (usr instanceof Estudiante) new StudentFrame((Estudiante)usr).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
