//Cristobal Chepilla 21.873.055-8 I.T.I
//Pablo Solar 21.590.002-9 I.T.I

package Taller4;

public abstract class Usuario {
    protected String username;
    protected String password;
    protected String rol;

    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRol() { return rol; }
    public void setPassword(String p) { this.password = p; }
    public boolean autenticar(String p) { return this.password.equals(p); }
}
