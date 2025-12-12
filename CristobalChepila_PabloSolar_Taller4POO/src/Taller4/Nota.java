//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Taller4;

public class Nota {
    private String rut;
    private String codigoAsignatura;
    private double calificacion;
    private String estado;
    private String semestre;

    public Nota(String rut, String codigoAsignatura, double calificacion, String estado, String semestre) {
        this.rut = rut;
        this.codigoAsignatura = codigoAsignatura;
        this.calificacion = calificacion;
        this.estado = estado;
        this.semestre = semestre;
    }

    public String getRut() { return rut; }
    public String getCodigoAsignatura() { return codigoAsignatura; }
    public double getCalificacion() { return calificacion; }
    public String getEstado() { return estado; }
    public String getSemestre() { return semestre; }
}
