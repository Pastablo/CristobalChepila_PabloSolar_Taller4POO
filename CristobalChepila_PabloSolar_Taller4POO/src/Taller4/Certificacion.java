package Taller4;

import java.util.ArrayList;
import java.util.List;

public class Certificacion {
    private String id;
    private String nombre;
    private String descripcion;
    private int requisitosCreditos;
    private int validezAnios;
    private List<String> cursosNRC;

    public Certificacion(String id, String nombre, String descripcion, int requisitosCreditos, int validezAnios) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requisitosCreditos = requisitosCreditos;
        this.validezAnios = validezAnios;
        this.cursosNRC = new ArrayList<String>();
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getRequisitosCreditos() { return requisitosCreditos; }
    public int getValidezAnios() { return validezAnios; }
    public List<String> getCursosNRC() { return cursosNRC; }
    public void addCurso(String nrc) { this.cursosNRC.add(nrc); }
}
