package Taller4;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class UsuarioFormDialog extends JDialog {
    private JTextField txtUser = new JTextField(20);
    private JPasswordField txtPass = new JPasswordField(20);
    private JComboBox<String> cbRol = new JComboBox<>(new String[] {"ADMIN","COORD","EST"});
    private JTextField txtExtra = new JTextField(20);
    private boolean ok = false;
    private Usuario original = null;

    public UsuarioFormDialog(JFrame owner, Usuario u) {
        super(owner, true);
        this.original = u;
        setTitle(u==null? "Crear Usuario":"Modificar Usuario");
        setSize(480,280);
        setLocationRelativeTo(owner);
        init();
        if (u!=null) load(u);
    }

    private void init() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(4,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Usuario (username / RUT):")); form.add(txtUser);
        form.add(new JLabel("Contraseña:")); form.add(txtPass);
        form.add(new JLabel("Rol:")); form.add(cbRol);
        form.add(new JLabel("Área / Nombre:")); form.add(txtExtra);
        p.add(form, BorderLayout.CENTER);
        JPanel btns = new JPanel();
        JButton okb = new JButton("OK"); okb.addActionListener(e->onOk());
        JButton cancel = new JButton("Cancelar"); cancel.addActionListener(e->onCancel());
        btns.add(okb); btns.add(cancel);
        p.add(btns, BorderLayout.SOUTH);
        add(p);
    }

    private void load(Usuario u) {
        txtUser.setText(u.getUsername()); txtUser.setEnabled(false);
        txtPass.setText(u.getPassword());
        cbRol.setSelectedItem(u.getRol());
        if (u instanceof Coordinador) txtExtra.setText(((Coordinador)u).getArea());
        else if (u instanceof Estudiante) txtExtra.setText(((Estudiante)u).getNombreCompleto());
    }

    private void onOk() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());
        String rol = (String)cbRol.getSelectedItem();
        String extra = txtExtra.getText().trim();
        if (user.isEmpty() || pass.isEmpty()) { JOptionPane.showMessageDialog(this, "Usuario y contraseña obligatorios"); return; }
        if (original == null) {
            if ("ADMIN".equals(rol)) Repositorio.getInstance().getUsuarios().add(new Administrador(user, pass));
            else if ("COORD".equals(rol)) Repositorio.getInstance().getUsuarios().add(new Coordinador(user, pass, extra));
            else if ("EST".equals(rol)) {
                Estudiante e = new Estudiante(user, extra.isEmpty()?"Nombre":extra, "Carrera", 1, "email@ejemplo", pass);
                Repositorio.getInstance().getUsuarios().add(e);
                Repositorio.getInstance().getEstudiantes().add(e);
            }
        } else {
            original.setPassword(pass);
        }
        ok = true;
        dispose();
    }

    private void onCancel() { ok=false; dispose(); }
    public boolean isOk() { return ok; }
    public Usuario getUsuario() {
        if (!ok) return null;
        return Repositorio.getInstance().findByUsername(txtUser.getText().trim());
    }
}

