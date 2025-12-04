package vallegrande.edu.pe.service;

import vallegrande.edu.pe.database.Database;
import vallegrande.edu.pe.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    // Insertar
    public void crearPedido(Pedido pedido) {
        String sql = "INSERT INTO Pedidos (nombre_cliente, telefono, direccion, detalle_pedido) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pedido.getNombreCliente());
            stmt.setString(2, pedido.getTelefono());
            stmt.setString(3, pedido.getDireccion());
            stmt.setString(4, pedido.getDetallePedido());
            stmt.executeUpdate();
            System.out.println("✅ Pedido agregado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar
    public List<Pedido> listarPedidos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pedido p = new Pedido(
                        rs.getInt("id"),
                        rs.getString("nombre_cliente"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("detalle_pedido")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar
    public void actualizarPedido(Pedido pedido) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE Pedidos SET ");
        List<Object> parametros = new ArrayList<>();
        boolean primerCampo = true;

        if (pedido.getNombreCliente() != null && !pedido.getNombreCliente().trim().isEmpty()) {
            sqlBuilder.append("nombre_cliente=?");
            parametros.add(pedido.getNombreCliente());
            primerCampo = false;
        }

        if (pedido.getTelefono() != null && !pedido.getTelefono().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("telefono=?");
            parametros.add(pedido.getTelefono());
            primerCampo = false;
        }

        if (pedido.getDireccion() != null && !pedido.getDireccion().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("direccion=?");
            parametros.add(pedido.getDireccion());
            primerCampo = false;
        }

        if (pedido.getDetallePedido() != null && !pedido.getDetallePedido().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("detalle_pedido=?");
            parametros.add(pedido.getDetallePedido());
            primerCampo = false;
        }

        if (primerCampo) {
            System.out.println("⚠️ No hay campos para actualizar. No se realizó ninguna operación.");
            return;
        }

        sqlBuilder.append(" WHERE id=?");
        parametros.add(pedido.getId());

        String sql = sqlBuilder.toString();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            int filasAfectadas = stmt.executeUpdate();
            if(filasAfectadas > 0){
                System.out.println("✅ Pedido actualizado (campos modificados: " + (parametros.size() - 1) + ")");
            } else {
                System.out.println("⚠️ No se encontró pedido con ID: " + pedido.getId() + " o no hubo cambios.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar
    public void eliminarPedido(int id) {
        String sqlDelete = "DELETE FROM Pedidos WHERE id=?";
        String sqlCheckCount = "SELECT COUNT(*) AS count FROM Pedidos";
        String sqlResetAutoIncrement = "ALTER TABLE Pedidos AUTO_INCREMENT = 1";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {

            stmtDelete.setInt(1, id);
            int rowsAffected = stmtDelete.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Pedido eliminado");

                try (Statement stmtCheck = conn.createStatement();
                     ResultSet rs = stmtCheck.executeQuery(sqlCheckCount)) {

                    if (rs.next() && rs.getInt("count") == 0) {
                        try (Statement stmtReset = conn.createStatement()) {
                            stmtReset.executeUpdate(sqlResetAutoIncrement);
                            System.out.println("✅ AUTO_INCREMENT reseteado a 1");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}