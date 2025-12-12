//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Taller4;

import java.io.FileWriter;
import java.io.PrintWriter;

public class ExportManager {
    public static boolean exportarCertificado(Registro r) {
        try {
            Certificacion c = Repositorio.getInstance().findCertById(r.getIdCertificacion());
            if (c == null) return false;
            String fname = "certificado_" + r.getRutEstudiante() + "_" + c.getId() + ".txt";
            try (PrintWriter pw = new PrintWriter(new FileWriter(fname))) {
                pw.println("CERTIFICADO");
                pw.println("Estudiante: " + r.getRutEstudiante());
                pw.println("Certificacion: " + c.getNombre());
                pw.println("Fecha: " + r.getFecha());
                pw.println("Validez años: " + c.getValidezAnios());
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error exportar: " + e.getMessage());
            return false;
        }
    }
}
