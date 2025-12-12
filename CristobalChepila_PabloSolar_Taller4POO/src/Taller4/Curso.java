package Taller4;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nrc;
    private String nombre;
    private int semestre;
    private int creditos;
    private String area;
    private List<String> prerrequisitos;

    public Curso(String nrc, String nombre, int semestre, int creditos, String area, List<String> prerrequisitos) {
        this.nrc = nrc;
        this.nombre = nombre;
        this.semestre = semestre;
        this.creditos = creditos;
        this.area = area;
        this.prerrequisitos = prerrequisitos == null ? new ArrayList<String>() : prerrequisitos;
    }

    public String getNrc() { return nrc; }
    public String getNombre() { return nombre; }
    public int getSemestre() { return semestre; }
    public int getCreditos() { return creditos; }
    public String getArea() { return area; }
    public List<String> getPrerrequisitos() { return prerrequisitos; }
}
