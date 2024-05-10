package model;

public class Bruger {
    private String username;
    private String password;


    public Bruger(String username, String password) {
        this.username = username;
        this.password = password;

    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

}
