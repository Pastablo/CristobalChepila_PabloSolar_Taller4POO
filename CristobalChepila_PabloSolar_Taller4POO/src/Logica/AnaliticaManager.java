//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Logica;

import java.util.ArrayList;
import java.util.List;

import Dominio.Certificacion;
import Dominio.Registro;


public class AnaliticaManager {

    public static List<ConteoCert> inscripcionesPorCert() {
        List<ConteoCert> conteos = new ArrayList<ConteoCert>();
        List<Registro> regs = Repositorio.getInstance().getRegistros();

        for (int i=0;i<regs.size();i++) {
            String id = regs.get(i).getIdCertificacion();
            // buscar existente
            ConteoCert ex = null;
            for (int j=0;j<conteos.size();j++) {
                if (conteos.get(j).id.equals(id)) { ex = conteos.get(j); break; }
            }
            if (ex == null) {
                ConteoCert nc = new ConteoCert(id);
                nc.cantidad = 1;
                conteos.add(nc);
            } else {
                ex.cantidad = ex.cantidad + 1;
            }
        }
        return conteos;
    }

    public static List<Certificacion> certificacionesCriticas() {
        List<Certificacion> lista = new ArrayList<Certificacion>();
        for (int i=0;i<Repositorio.getInstance().getCertificaciones().size();i++) lista.add(Repositorio.getInstance().getCertificaciones().get(i));
        List<ConteoCert> conteo = inscripcionesPorCert();

        // orden burbuja por cantidad ascendente
        for (int i=0;i<lista.size()-1;i++) {
            for (int j=0;j<lista.size()-i-1;j++) {
                int c1 = getCount(lista.get(j).getId(), conteo);
                int c2 = getCount(lista.get(j+1).getId(), conteo);
                if (c1 > c2) {
                    Certificacion tmp = lista.get(j);
                    lista.set(j, lista.get(j+1));
                    lista.set(j+1, tmp);
                }
            }
        }
        return lista;
    }

    private static int getCount(String id, List<ConteoCert> conteo) {
        for (int i=0;i<conteo.size();i++) {
            if (conteo.get(i).id.equals(id)) return conteo.get(i).cantidad;
        }
        return 0;
    }
}

