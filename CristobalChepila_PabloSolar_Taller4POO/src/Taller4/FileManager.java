//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Taller4;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    private static final String DATA_DIR = "data";

    public static void cargarTodos() {
        cargarUsuarios();
        cargarEstudiantes();
        cargarCursos();
        cargarCertificaciones();
        cargarAsignaturasCert();
        cargarRegistros();
        cargarNotas();
    }

    public static void cargarUsuarios() {
        File f = new File(DATA_DIR + "/usuarios.txt");
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] s = line.split(";");
                String user = s[0];
                String pass = s.length>1? s[1] : "";
                String rol  = s.length>2? s[2] : "";
                if ("ADMIN".equalsIgnoreCase(rol)) {
                    Repositorio.getInstance().getUsuarios().add(new Administrador(user, pass));
                } else if ("COORD".equalsIgnoreCase(rol)) {
                    String area = s.length>3? s[3] : "";
                    Repositorio.getInstance().getUsuarios().add(new Coordinador(user, pass, area));
                } else if ("EST".equalsIgnoreCase(rol)) {
                    String rut = user;
                    String nombre = s.length>3? s[3] : "";
                    String carrera = s.length>4? s[4] : "";
                    int semestre = s.length>5? parseIntSafe(s[5],1) : 1;
                    String email = s.length>6? s[6] : "";
                    Estudiante e = new Estudiante(rut, nombre, carrera, semestre, email, pass);
                    Repositorio.getInstance().getUsuarios().add(e);
                    Repositorio.getInstance().getEstudiantes().add(e);
                }
            }
        } catch (Exception ex) { System.err.println("Error cargarUsuarios: "+ex.getMessage()); }
    }

    public static void guardarUsuarios() {
        File f = new File(DATA_DIR + "/usuarios.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i=0;i<Repositorio.getInstance().getUsuarios().size();i++) {
                Usuario u = Repositorio.getInstance().getUsuarios().get(i);
                if (u instanceof Administrador) pw.println(u.getUsername()+";"+u.getPassword()+";ADMIN");
                else if (u instanceof Coordinador) pw.println(u.getUsername()+";"+u.getPassword()+";COORD;"+((Coordinador)u).getArea());
                else if (u instanceof Estudiante) {
                    Estudiante e = (Estudiante)u;
                    pw.println(e.getRut()+";"+e.getPassword()+";EST;"+e.getNombreCompleto()+";"+e.getCarrera()+";"+e.getSemestre()+";"+e.getEmail());
                }
            }
        } catch (Exception ex) { System.err.println("Error guardarUsuarios: "+ex.getMessage()); }
    }

    public static void cargarEstudiantes() {
        File f = new File(DATA_DIR + "/estudiantes.txt");
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] s = line.split(";");
                String rut = s[0];
                String nombre = s.length>1? s[1] : "";
                String carrera = s.length>2? s[2] : "";
                int semestre = s.length>3? parseIntSafe(s[3],1) : 1;
                String email = s.length>4? s[4] : "";
                String pass = s.length>5? s[5] : "pass";
                if (Repositorio.getInstance().findEstudianteByRut(rut) == null) {
                    Estudiante e = new Estudiante(rut, nombre, carrera, semestre, email, pass);
                    Repositorio.getInstance().getEstudiantes().add(e);
                    Repositorio.getInstance().getUsuarios().add(e);
                }
            }
        } catch (Exception ex) { System.err.println("Error cargarEstudiantes: "+ex.getMessage()); }
    }

    public static void cargarCursos() {
        File f = new File(DATA_DIR + "/cursos.txt");
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] s = line.split(";");
                String nrc = s[0];
                String nombre = s.length>1? s[1] : "";
                int sem = s.length>2? parseIntSafe(s[2],1) : 1;
                int creds = s.length>3? parseIntSafe(s[3],0) : 0;
                String area = s.length>4? s[4] : "";
                List<String> prereq = new ArrayList<String>();
                if (s.length>5 && !s[5].isEmpty()) {
                    String[] parts = s[5].split(",");
                    for (int i=0;i<parts.length;i++) prereq.add(parts[i].trim());
                }
                Repositorio.getInstance().getCursos().add(new Curso(nrc, nombre, sem, creds, area, prereq));
            }
        } catch (Exception ex) { System.err.println("Error cargarCursos: "+ex.getMessage()); }
    }

    public static void cargarCertificaciones() {
        File f = new File(DATA_DIR + "/certificaciones.txt");
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] s = line.split(";");
                String id = s[0];
                String nombre = s.length>1? s[1] : "";
                String desc = s.length>2? s[2] : "";
                int req = s.length>3? parseIntSafe(s[3],0) : 0;
                int val = s.length>4? parseIntSafe(s[4],0) : 0;
                Repositorio.getInstance().getCertificaciones().add(new Certificacion(id, nombre, desc, req, val));
            }
        } catch (Exception ex) { System.err.println("Error cargarCertificaciones: "+ex.getMessage()); }
    }

    public static void cargarAsignaturasCert() {
        File f = new File(DATA_DIR + "/asignaturas_certificaciones.txt");
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] s = line.split(";");
                String id = s[0];
                String nrc = s.length>1? s[1] : "";
                Certificacion c = Repositorio.getInstance().findCertById(id);
                if (c != null && !nrc.isEmpty()) c.addCurso(nrc);
            }
        } catch (Exception ex) { System.err.println("Error cargarAsignaturasCert: "+ex.getMessage()); }
    }

    public static void cargarRegistros() {
        File f = new File(DATA_DIR + "/registros.txt");
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] s = line.split(";");
                String rut = s[0];
                String id = s.length>1? s[1] : "";
                String fecha = s.length>2? s[2] : "";
                String estado = s.length>3? s[3] : "";
                double prog = s.length>4? parseDoubleSafe(s[4],0.0) : 0.0;
                Repositorio.getInstance().getRegistros().add(new Registro(rut, id, fecha, estado, prog));
            }
        } catch (Exception ex) { System.err.println("Error cargarRegistros: "+ex.getMessage()); }
    }

    public static void guardarRegistros() {
        File f = new File(DATA_DIR + "/registros.txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
            for (int i=0;i<Repositorio.getInstance().getRegistros().size();i++) {
                Registro r = Repositorio.getInstance().getRegistros().get(i);
                pw.println(r.getRutEstudiante()+";"+r.getIdCertificacion()+";"+r.getFecha()+";"+r.getEstado()+";"+r.getProgreso());
            }
        } catch (Exception ex) { System.err.println("Error guardarRegistros: "+ex.getMessage()); }
    }

    public static void cargarNotas() {
        File f = new File(DATA_DIR + "/notas.txt");
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] s = line.split(";");
                String rut = s[0];
                String cod = s.length>1? s[1] : "";
                double cal = s.length>2? parseDoubleSafe(s[2],0.0) : 0.0;
                String estado = s.length>3? s[3] : "";
                String semestre = s.length>4? s[4] : "";
                Repositorio.getInstance().getNotas().add(new Nota(rut, cod, cal, estado, semestre));
            }
        } catch (Exception ex) { System.err.println("Error cargarNotas: "+ex.getMessage()); }
    }

    private static int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
    private static double parseDoubleSafe(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }
}
