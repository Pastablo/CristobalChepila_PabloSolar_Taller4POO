package Taller4;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;



public class AdminFrame extends JFrame {
    private JTable table;
    private UsuariosTableModel model;

    public AdminFrame() {
        setTitle("Panel Administrador");
        setSize(1000,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
    }

    private void init() {
        getContentPane().setLayout(new BorderLayout());
        JLabel title = new JLabel("Administración de Usuarios", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        getContentPane().add(title, BorderLayout.NORTH);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton btnCrear = new JButton("Crear Usuario"); btnCrear.setAlignmentX(Component.CENTER_ALIGNMENT); btnCrear.addActionListener(e->crearUsuario());
        JButton btnModificar = new JButton("Modificar Usuario"); btnModificar.setAlignmentX(Component.CENTER_ALIGNMENT); btnModificar.addActionListener(e->modificarUsuario());
        JButton btnEliminar = new JButton("Eliminar Usuario"); btnEliminar.setAlignmentX(Component.CENTER_ALIGNMENT); btnEliminar.addActionListener(e->eliminarUsuario());
        JButton btnReset = new JButton("Restablecer Contraseña"); btnReset.setAlignmentX(Component.CENTER_ALIGNMENT); btnReset.addActionListener(e->resetPass());
        left.add(btnCrear); left.add(Box.createVerticalStrut(10));
        left.add(btnModificar); left.add(Box.createVerticalStrut(10));
        left.add(btnEliminar); left.add(Box.createVerticalStrut(10));
        left.add(btnReset);
        getContentPane().add(left, BorderLayout.WEST);

        model = new UsuariosTableModel(new ArrayList<Usuario>(Repositorio.getInstance().getUsuarios()));
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton btnRef = new JButton("Refrescar"); btnRef.addActionListener(e->refrescar());
        JButton btnSave = new JButton("Guardar en archivo"); btnSave.addActionListener(e->{ FileManager.guardarUsuarios(); JOptionPane.showMessageDialog(this, "Usuarios guardados en data/usuarios.txt"); });
        bottom.add(btnRef); bottom.add(btnSave);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private void crearUsuario() {
        UsuarioFormDialog dlg = new UsuarioFormDialog(this, null);
        dlg.setVisible(true);
        if (dlg.isOk()) {
            Usuario u = dlg.getUsuario();
            if (u!=null) {
                Repositorio.getInstance().addUsuario(u);
                refrescar();
            }
        }
    }

    private void modificarUsuario() {
        int sel = table.getSelectedRow();
        if (sel < 0) { UtilsGui.showError("Seleccione un usuario"); return; }
        Usuario u = model.getUsuarioAt(sel);
        UsuarioFormDialog dlg = new UsuarioFormDialog(this, u);
        dlg.setVisible(true);
        if (dlg.isOk()) refrescar();
    }

    private void eliminarUsuario() {
        int sel = table.getSelectedRow();
        if (sel < 0) { UtilsGui.showError("Seleccione un usuario"); return; }
        Usuario u = model.getUsuarioAt(sel);
        int r = JOptionPane.showConfirmDialog(this, "Eliminar usuario " + u.getUsername() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            if (u instanceof Estudiante) {
                Estudiante e = (Estudiante)u;
                // eliminar registros y notas relacionados
                for (int i=Repositorio.getInstance().getRegistros().size()-1;i>=0;i--) {
                    if (Repositorio.getInstance().getRegistros().get(i).getRutEstudiante().equals(e.getRut())) {
                        Repositorio.getInstance().getRegistros().remove(i);
                    }
                }
                for (int i=Repositorio.getInstance().getNotas().size()-1;i>=0;i--) {
                    if (Repositorio.getInstance().getNotas().get(i).getRut().equals(e.getRut())) {
                        Repositorio.getInstance().getNotas().remove(i);
                    }
                }
                Repositorio.getInstance().getEstudiantes().remove(e);
            }
            Repositorio.getInstance().getUsuarios().remove(u);
            refrescar();
        }
    }

    private void resetPass() {
        int sel = table.getSelectedRow();
        if (sel < 0) { UtilsGui.showError("Seleccione usuario"); return; }
        Usuario u = model.getUsuarioAt(sel);
        String np = JOptionPane.showInputDialog(this, "Ingrese nueva contraseña para " + u.getUsername() + ":");
        if (np!=null && !np.trim().isEmpty()) { u.setPassword(np.trim()); JOptionPane.showMessageDialog(this, "Contraseña actualizada"); }
    }

    private void refrescar() {
        model.setUsuarios(new ArrayList<Usuario>(Repositorio.getInstance().getUsuarios()));
        model.fireTableDataChanged();
    }

    static class UsuariosTableModel extends AbstractTableModel {
        private String[] cols = {"Usuario","Rol","Extra"};
        private java.util.ArrayList<Usuario> usuarios;
        public UsuariosTableModel(java.util.ArrayList<Usuario> usuarios) { this.usuarios = usuarios; }
        public void setUsuarios(java.util.ArrayList<Usuario> u) { this.usuarios = u; }
        public Usuario getUsuarioAt(int row) { return usuarios.get(row); }
        public int getRowCount() { return usuarios.size(); }
        public int getColumnCount() { return cols.length; }
        public String getColumnName(int c) { return cols[c]; }
        public Object getValueAt(int r, int c) {
            Usuario u = usuarios.get(r);
            switch(c) {
                case 0: return u.getUsername();
                case 1: return u.getRol();
                case 2:
                    if (u instanceof Coordinador) return ((Coordinador)u).getArea();
                    if (u instanceof Estudiante) return ((Estudiante)u).getNombreCompleto();
                    return "";
            }
            return null;
        }
    }
}

