//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Taller4;

public class Estudiante extends Usuario {
    private String rut;
    private String nombreCompleto;
    private String carrera;
    private int semestre;
    private String email;

    public Estudiante(String rut, String nombreCompleto, String carrera, int semestre, String email, String password) {
        super(rut, password, "EST");
        this.rut = rut;
        this.nombreCompleto = nombreCompleto;
        this.carrera = carrera;
        this.semestre = semestre;
        this.email = email;
    }

    public String getRut() { return rut; }
    public String getNombreCompleto() { return nombreCompleto; }
    // alias para compatibilidad con llamadas a getNombre()
    public String getNombre() { return nombreCompleto; }
    public String getCarrera() { return carrera; }
    public int getSemestre() { return semestre; }
    public String getEmail() { return email; }
}
