//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Taller4;

public class Coordinador extends Usuario {
    private String area;
    public Coordinador(String username, String password, String area) {
        super(username, password, "COORD");
        this.area = area;
    }
    public String getArea() { return area; }
}
