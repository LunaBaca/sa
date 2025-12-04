package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Cliente;
import vallegrande.edu.pe.service.ClienteService;

import java.time.LocalDate;
import java.util.List;

public class ClienteController {
    private ClienteService service = new ClienteService();

    public void crearCliente(String nombre, String apellidos, String usuario, String dni, String correo, LocalDate fechaRegistro) {
        Cliente cliente = new Cliente(nombre, apellidos, usuario, dni, correo, fechaRegistro);
        service.crearCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return service.listarClientes();
    }

    public void actualizarCliente(int id, String nombre, String apellidos, String usuario, String dni, String correo, LocalDate fechaRegistro) {
        Cliente cliente = new Cliente(id, nombre, apellidos, usuario, dni, correo, fechaRegistro);
        service.actualizarCliente(cliente);
    }

    public void eliminarCliente(int id) {
        service.eliminarCliente(id);
    }
}