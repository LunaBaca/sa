package vallegrande.edu.pe.controller;

import vallegrande.edu.pe.model.Pedido;
import vallegrande.edu.pe.service.PedidoService;

import java.util.List;

public class PedidoController {
    private PedidoService service = new PedidoService();

    public void crearPedido(String nombreCliente, String telefono, String direccion, String detallePedido) {
        Pedido pedido = new Pedido(nombreCliente, telefono, direccion, detallePedido);
        service.crearPedido(pedido);
    }

    public List<Pedido> listarPedidos() {
        return service.listarPedidos();
    }

    public void actualizarPedido(int id, String nombreCliente, String telefono, String direccion, String detallePedido) {
        Pedido pedido = new Pedido(id, nombreCliente, telefono, direccion, detallePedido);
        service.actualizarPedido(pedido);
    }

    public void eliminarPedido(int id) {
        service.eliminarPedido(id);
    }
}