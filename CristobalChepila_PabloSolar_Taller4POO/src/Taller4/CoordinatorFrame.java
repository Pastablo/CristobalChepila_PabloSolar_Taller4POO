//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

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



public class CoordinatorFrame extends JFrame {
    private JPanel center;
    public CoordinatorFrame() {
        setTitle("Panel Coordinador");
        setSize(1000,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
    }
    private void init() {
        getContentPane().setLayout(new BorderLayout());
        JLabel title = new JLabel("Panel Coordinador", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        getContentPane().add(title, BorderLayout.NORTH);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JButton btnGestion = new JButton("Gestionar Certificaciones"); btnGestion.setAlignmentX(Component.CENTER_ALIGNMENT); btnGestion.addActionListener(e->manageCert());
        JButton btnMetr = new JButton("Ver Métricas"); btnMetr.setAlignmentX(Component.CENTER_ALIGNMENT); btnMetr.addActionListener(e->showMetrics());
        left.add(btnGestion); left.add(Box.createVerticalStrut(10)); left.add(btnMetr);
        getContentPane().add(left, BorderLayout.WEST);

        center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.PAGE_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        center.add(new JLabel("Seleccione una acción a la izquierda"));
        getContentPane().add(new JScrollPane(center), BorderLayout.CENTER);
    }

    private void manageCert() {
        center.removeAll();
        center.add(new JLabel("Gestión de certificaciones"));
        String[] cols = {"ID","Nombre","ReqCred","Validez","#Cursos"};
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        for (int i=0;i<Repositorio.getInstance().getCertificaciones().size();i++) {
            Certificacion c = Repositorio.getInstance().getCertificaciones().get(i);
            data.add(new Object[]{c.getId(), c.getNombre(), c.getRequisitosCreditos(), c.getValidezAnios(), c.getCursosNRC().size()});
        }
        Object[][] arr = new Object[data.size()][];
        for (int i=0;i<data.size();i++) arr[i]=data.get(i);
        JTable tabla = new JTable(arr, cols);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        center.add(new JScrollPane(tabla));
        JButton btnMod = new JButton("Modificar Seleccion");
        btnMod.addActionListener(e->{
            int s = tabla.getSelectedRow();
            if (s<0) { UtilsGui.showError("Seleccione una certificación"); return; }
            Object id = tabla.getValueAt(s,0);
            Certificacion c = Repositorio.getInstance().findCertById(id.toString());
            CertFormDialog dlg = new CertFormDialog(this, c);
            dlg.setVisible(true);
            manageCert();
        });
        JButton btnGen = new JButton("Generar certificados completados");
        btnGen.addActionListener(e->{
            int successes = 0;
            for (int i=0;i<Repositorio.getInstance().getRegistros().size();i++) {
                Registro r = Repositorio.getInstance().getRegistros().get(i);
                Certificacion c = Repositorio.getInstance().findCertById(r.getIdCertificacion());
                if (c!=null) {
                    SimpleCertificadoStrategy strat = new SimpleCertificadoStrategy();
                    if (strat.generar(c, r)) successes++;
                }
            }
            JOptionPane.showMessageDialog(this, "Certificados generados: " + successes);
        });
        center.add(Box.createVerticalStrut(10));
        center.add(btnMod);
        center.add(Box.createVerticalStrut(10));
        center.add(btnGen);
        center.revalidate();
        center.repaint();
    }

    private void showMetrics() {
        java.util.List<ConteoCert> lista = AnaliticaManager.inscripcionesPorCert();
        if (lista == null || lista.size() == 0) {
            JOptionPane.showMessageDialog(this, "No hay inscripciones");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<lista.size();i++) {
            ConteoCert c = lista.get(i);
            sb.append(c.id + ": " + c.cantidad + "\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }
}
