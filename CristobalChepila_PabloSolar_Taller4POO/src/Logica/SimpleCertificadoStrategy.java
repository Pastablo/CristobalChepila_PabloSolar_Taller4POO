//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Logica;

import Dominio.Certificacion;
import Dominio.Registro;

public class SimpleCertificadoStrategy implements CertificadoStrategy {
    @Override
    public boolean generar(Certificacion c, Registro r) {
        if (r.getProgreso() >= 100.0 || "Completada".equalsIgnoreCase(r.getEstado())) {
            return ExportManager.exportarCertificado(r);
        }
        return false;
    }
}
