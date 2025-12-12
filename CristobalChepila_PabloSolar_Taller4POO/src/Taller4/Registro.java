//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Taller4;

public class Registro {
    private String rutEstudiante;
    private String idCertificacion;
    private String fecha; // formato texto "YYYY-MM-DD"
    private String estado; // Activa, Completada, Suspendida
    private double progreso;

    // constructor mínimo de 3 campos
    public Registro(String rutEstudiante, String idCertificacion, String fecha) {
        this.rutEstudiante = rutEstudiante;
        this.idCertificacion = idCertificacion;
        this.fecha = fecha;
        this.estado = "Activa";
        this.progreso = 0.0;
    }

    // constructor completo
    public Registro(String rutEstudiante, String idCertificacion, String fecha, String estado, double progreso) {
        this.rutEstudiante = rutEstudiante;
        this.idCertificacion = idCertificacion;
        this.fecha = fecha;
        this.estado = estado;
        this.progreso = progreso;
    }

    public String getRutEstudiante() { return rutEstudiante; }
    public String getIdCertificacion() { return idCertificacion; }
    public String getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public double getProgreso() { return progreso; }
    public void setEstado(String e) { this.estado = e; }
    public void setProgreso(double p) { this.progreso = p; }
}
