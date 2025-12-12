package Taller4;

import java.util.ArrayList;

public class Repositorio {
    private static Repositorio instancia;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Estudiante> estudiantes;
    private ArrayList<Curso> cursos;
    private ArrayList<Certificacion> certificaciones;
    private ArrayList<Registro> registros;
    private ArrayList<Nota> notas;

    private Repositorio() {
        usuarios = new ArrayList<Usuario>();
        estudiantes = new ArrayList<Estudiante>();
        cursos = new ArrayList<Curso>();
        certificaciones = new ArrayList<Certificacion>();
        registros = new ArrayList<Registro>();
        notas = new ArrayList<Nota>();

        usuarios.add(new Administrador("admin","admin123"));
    }

    public static Repositorio getInstance() {
        if (instancia == null) instancia = new Repositorio();
        return instancia;
    }

    public ArrayList<Usuario> getUsuarios() { return usuarios; }
    public ArrayList<Estudiante> getEstudiantes() { return estudiantes; }
    public ArrayList<Curso> getCursos() { return cursos; }
    public ArrayList<Certificacion> getCertificaciones() { return certificaciones; }
    public ArrayList<Registro> getRegistros() { return registros; }
    public ArrayList<Nota> getNotas() { return notas; }

    public void addUsuario(Usuario u) {
        usuarios.add(u);
        if (u instanceof Estudiante) estudiantes.add((Estudiante)u);
    }
    public void removeUsuario(Usuario u) {
        usuarios.remove(u);
        if (u instanceof Estudiante) estudiantes.remove((Estudiante)u);
    }

    public Usuario findByUsername(String username) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(username)) return usuarios.get(i);
        }
        return null;
    }

    public Estudiante findEstudianteByRut(String rut) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getRut().equals(rut)) return estudiantes.get(i);
        }
        return null;
    }

    public Curso findCursoByNrc(String nrc) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getNrc().equals(nrc)) return cursos.get(i);
        }
        return null;
    }

    public Certificacion findCertById(String id) {
        for (int i = 0; i < certificaciones.size(); i++) {
            if (certificaciones.get(i).getId().equals(id)) return certificaciones.get(i);
        }
        return null;
    }
}
