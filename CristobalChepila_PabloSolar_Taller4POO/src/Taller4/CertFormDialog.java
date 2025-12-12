//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Taller4;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class CertFormDialog extends JDialog {
    private JTextField txtId = new JTextField(20);
    private JTextField txtNombre = new JTextField(20);
    private JTextField txtReq = new JTextField(5);
    private JTextField txtVal = new JTextField(5);
    private boolean ok = false;
    private Certificacion original = null;

    public CertFormDialog(JFrame owner, Certificacion c) {
        super(owner, true);
        this.original = c;
        setTitle(c==null?"Crear Certificación":"Modificar Certificación");
        setSize(480,240);
        setLocationRelativeTo(owner);
        init();
        if (c!=null) load(c);
    }

    private void init() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel f = new JPanel(new GridLayout(4,2,6,6));
        f.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        f.add(new JLabel("ID:")); f.add(txtId);
        f.add(new JLabel("Nombre:")); f.add(txtNombre);
        f.add(new JLabel("Req créditos:")); f.add(txtReq);
        f.add(new JLabel("Validez (años):")); f.add(txtVal);
        p.add(f, BorderLayout.CENTER);
        JPanel btn = new JPanel();
        JButton okb = new JButton("OK"); okb.addActionListener(e->onOk());
        JButton ccl = new JButton("Cancelar"); ccl.addActionListener(e->onCancel());
        btn.add(okb); btn.add(ccl);
        p.add(btn, BorderLayout.SOUTH);
        add(p);
    }

    private void load(Certificacion c) {
        txtId.setText(c.getId()); txtId.setEnabled(false);
        txtNombre.setText(c.getNombre());
        txtReq.setText(String.valueOf(c.getRequisitosCreditos()));
        txtVal.setText(String.valueOf(c.getValidezAnios()));
    }

    private void onOk() {
        String id = txtId.getText().trim();
        String nom = txtNombre.getText().trim();
        if (id.isEmpty() || nom.isEmpty()) { JOptionPane.showMessageDialog(this, "ID y Nombre obligatorios"); return; }
        int req = Integer.parseInt(txtReq.getText().trim());
        int val = Integer.parseInt(txtVal.getText().trim());
        if (original == null) {
            Certificacion c = new Certificacion(id, nom, "", req, val);
            Repositorio.getInstance().getCertificaciones().add(c);
        } else {
            // simplificado: no edita cursos asociados
        }
        ok = true;
        dispose();
    }

    private void onCancel() { ok=false; dispose(); }
    public boolean isOk() { return ok; }
}

