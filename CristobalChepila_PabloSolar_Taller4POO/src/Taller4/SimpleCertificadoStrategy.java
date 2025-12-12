package Taller4;

public class SimpleCertificadoStrategy implements CertificadoStrategy {
    @Override
    public boolean generar(Certificacion c, Registro r) {
        if (r.getProgreso() >= 100.0 || "Completada".equalsIgnoreCase(r.getEstado())) {
            return ExportManager.exportarCertificado(r);
        }
        return false;
    }
}
