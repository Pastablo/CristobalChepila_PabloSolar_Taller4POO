package Taller4;

public class UserFactory {
    public static Usuario create(String tipo, String... params) {
        switch (tipo.toUpperCase()) {
            case "ADMIN": return new Administrador(params[0], params[1]);
            case "COORD": return new Coordinador(params[0], params[1], params.length>2?params[2]:"");
            case "EST": return new Estudiante(params[0], params[1], params[2], Integer.parseInt(params[3]), params[4], params[5]);
            default: throw new IllegalArgumentException("Tipo desconocido");
        }
    }
}
