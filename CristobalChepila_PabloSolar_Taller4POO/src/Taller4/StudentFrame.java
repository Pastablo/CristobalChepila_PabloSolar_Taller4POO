package Taller4;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class StudentFrame extends JFrame {
    private Estudiante est;
    private JTable table;
    private DefaultTableModel model;

    public StudentFrame(Estudiante est) {
        super("Panel Estudiante - " + est.getNombreCompleto());
        this.est = est;
        setSize(1000,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Descripcion");
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel top = new JPanel();
        top.add(new JLabel("Certificaciones disponibles"));
        add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        JButton btnInscribir = new JButton("Inscribirse");
        JButton btnMetrics = new JButton("Ver métricas");
        JButton btnSalir = new JButton("Cerrar sesión");
        bottom.add(btnInscribir);
        bottom.add(btnMetrics);
        bottom.add(btnSalir);
        add(bottom, BorderLayout.SOUTH);

        cargarTabla();

        btnInscribir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { inscribir(); }
        });
        btnMetrics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { mostrarMetricas(); }
        });
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { dispose(); }
        });
    }

    private void cargarTabla() {
        model.setRowCount(0);
        for (int i=0;i<Repositorio.getInstance().getCertificaciones().size();i++) {
            Certificacion c = Repositorio.getInstance().getCertificaciones().get(i);
            model.addRow(new Object[]{c.getId(), c.getNombre(), c.getDescripcion()});
        }
    }

    private void inscribir() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Seleccione una certificación."); return; }
        String idCert = (String) model.getValueAt(row, 0);

        // fecha básica: yyyy-MM-dd usando Date
        String fecha = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

        Registro r = new Registro(est.getRut(), idCert, fecha, "Activa", 0.0);
        Repositorio.getInstance().getRegistros().add(r);
        FileManager.guardarRegistros();
        JOptionPane.showMessageDialog(this, "Inscripción realizada.");
    }

    private void mostrarMetricas() {
        List<ConteoCert> lista = AnaliticaManager.inscripcionesPorCert();
        if (lista == null || lista.size() == 0) {
            JOptionPane.showMessageDialog(this, "No hay inscripciones registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<lista.size();i++) {
            ConteoCert c = lista.get(i);
            sb.append(c.id).append(": ").append(c.cantidad).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }
}
