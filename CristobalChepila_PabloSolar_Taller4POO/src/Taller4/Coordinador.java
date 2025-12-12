package Taller4;

public class Coordinador extends Usuario {
    private String area;
    public Coordinador(String username, String password, String area) {
        super(username, password, "COORD");
        this.area = area;
    }
    public String getArea() { return area; }
}
