package model;

/**
 *
 * @author Guerino
 */
public class User {
    private int id;//Para la db
    private int port; //Puerto asignado al cliente
    private String name; //Nombre del usuario;
    private String color;

    public User() {
        this.id = 0;
        this.port = 0;
        this.name = "";
    }

    public User(int port, String name) {
        this.port = port;
        this.name = name;
    }

    public User(int id, int port, String name) {
        this.id = id;
        this.port = port;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }    

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", port=" + port + ", name=" + name + '}';
    }    
    
}