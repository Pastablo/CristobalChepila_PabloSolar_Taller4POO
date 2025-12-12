//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Logica;

import Dominio.Administrador;
import Dominio.Coordinador;
import Dominio.Estudiante;
import Dominio.Usuario;

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
