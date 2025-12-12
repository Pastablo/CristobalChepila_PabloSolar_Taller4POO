//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Logica;

import Dominio.Certificacion;

public class PrintCertVisitor implements CertificacionVisitor {
    @Override
    public void visit(Certificacion c) {
        System.out.println("Cert: " + c.getNombre() + " (req: " + c.getRequisitosCreditos() + ")");
    }
}
