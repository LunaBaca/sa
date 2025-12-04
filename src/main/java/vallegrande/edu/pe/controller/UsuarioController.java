package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Pedido;
import vallegrande.edu.pe.service.PedidoService;

import java.util.List;

public class UsuarioController {
    private PedidoService service = new UsuarioService();

    public void crearUsuario(String nombreUsuario, String telefono, String direccion, String detalleUsuario) {
        Pedido pedido = new Usuario(nombreUsuario, telefono, direccion, detalleUsuario);
        service.crearUsuario(Usuario);
    }

    public List<Usuario> listarUsuario() {
        return service.listarUsuario();
    }

    public void actualizarUsuario(int id, String nombreUsuario, String telefono, String direccion, String detalleUsuario) {
        Usuario usuario = new Usuario(id, nombreUsuario, telefono, direccion, detalleUsuario);
        service.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(int id) {
        service.eliminarUsuario(id);
    }