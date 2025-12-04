package vallegrande.edu.pe.model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String username; // Cambiado de 'usuario' a 'username' para mayor claridad
    private String password; // Nuevo campo esencial para un usuario
    private String correo;
    private LocalDate fechaRegistro;

    // Constructores
    public Usuario() {}

    // Constructor completo
    public Usuario(int id, String nombre, String apellidos, String username, String password, String correo, String rol, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    // Constructor sin ID (útil para insertar nuevos registros)
    public Usuario(String nombre, String apellidos, String username, String password, String correo, String rol, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | " + apellidos + " | " + username + " | " + rol + " | " + correo + " | " + fechaRegistro;
        // NOTA: Se omite el campo 'password' en el toString() por seguridad.
    }
}