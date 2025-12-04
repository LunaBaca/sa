package vallegrande.edu.pe.model;

import java.time.LocalDate;

public class Cliente {
    private int id;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String dni;
    private String correo;
    private LocalDate fechaRegistro;

    // Constructores
    public Cliente() {}

    public Cliente(int id, String nombre, String apellidos, String usuario, String dni, String correo, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.dni = dni;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }

    public Cliente(String nombre, String apellidos, String usuario, String dni, String correo, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.dni = dni;
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

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | " + apellidos + " | " + usuario + " | " + dni + " | " + correo + " | " + fechaRegistro;
    }
}