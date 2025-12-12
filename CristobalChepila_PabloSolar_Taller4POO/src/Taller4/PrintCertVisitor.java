package Taller4;


public class PrintCertVisitor implements CertificacionVisitor {
    @Override
    public void visit(Certificacion c) {
        System.out.println("Cert: " + c.getNombre() + " (req: " + c.getRequisitosCreditos() + ")");
    }
}
