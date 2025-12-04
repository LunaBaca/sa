package vallegrande.edu.pe.model;

public class Pedido {
    private int id;
    private String nombreCliente;
    private String telefono;
    private String direccion;
    private String detallePedido;

    // Constructores
    public Pedido() {}

    public Pedido(int id, String nombreCliente, String telefono, String direccion, String detallePedido) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.telefono = telefono;
        this.direccion = direccion;
        this.detallePedido = detallePedido;
    }

    public Pedido(String nombreCliente, String telefono, String direccion, String detallePedido) {
        this.nombreCliente = nombreCliente;
        this.telefono = telefono;
        this.direccion = direccion;
        this.detallePedido = detallePedido;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getDetallePedido() { return detallePedido; }
    public void setDetallePedido(String detallePedido) { this.detallePedido = detallePedido; }

    @Override
    public String toString() {
        return id + " | " + nombreCliente + " | " + telefono + " | " + direccion + " | " + detallePedido;
    }
}