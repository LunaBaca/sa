package vallegrande.edu.pe.service;

import vallegrande.edu.pe.database.Database;
import vallegrande.edu.pe.model.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    // Insertar
    public void crearCliente(Cliente cliente) {
        String sql = "INSERT INTO Registro (nombre, apellidos, usuario, dni, correo, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getUsuario());
            stmt.setString(4, cliente.getDni());
            stmt.setString(5, cliente.getCorreo());
            stmt.setDate(6, Date.valueOf(cliente.getFechaRegistro()));
            stmt.executeUpdate();
            System.out.println("✅ Cliente agregado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Registro";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("usuario"),
                        rs.getString("dni"),
                        rs.getString("correo"),
                        rs.getDate("fecha_registro").toLocalDate()
                );
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar
    public void actualizarCliente(Cliente cliente) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE Registro SET ");
        List<Object> parametros = new ArrayList<>();
        boolean primerCampo = true;

        if (cliente.getNombre() != null && !cliente.getNombre().trim().isEmpty()) {
            sqlBuilder.append("nombre=?");
            parametros.add(cliente.getNombre());
            primerCampo = false;
        }

        if (cliente.getApellidos() != null && !cliente.getApellidos().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("apellidos=?");
            parametros.add(cliente.getApellidos());
            primerCampo = false;
        }

        if (cliente.getUsuario() != null && !cliente.getUsuario().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("usuario=?");
            parametros.add(cliente.getUsuario());
            primerCampo = false;
        }

        if (cliente.getDni() != null && !cliente.getDni().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("dni=?");
            parametros.add(cliente.getDni());
            primerCampo = false;
        }

        if (cliente.getCorreo() != null && !cliente.getCorreo().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("correo=?");
            parametros.add(cliente.getCorreo());
            primerCampo = false;
        }

        if (cliente.getFechaRegistro() != null) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("fecha_registro=?");
            parametros.add(cliente.getFechaRegistro());
            primerCampo = false;
        }

        if (primerCampo) {
            System.out.println("⚠️ No hay campos para actualizar. No se realizó ninguna operación.");
            return;
        }

        sqlBuilder.append(" WHERE id=?");
        parametros.add(cliente.getId());

        String sql = sqlBuilder.toString();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            int filasAfectadas = stmt.executeUpdate();
            if(filasAfectadas > 0){
                System.out.println("✅ Cliente actualizado (campos modificados: " + (parametros.size() - 1) + ")");
            } else {
                System.out.println("⚠️ No se encontró cliente con ID: " + cliente.getId() + " o no hubo cambios.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar
    public void eliminarCliente(int id) {
        String sqlDelete = "DELETE FROM Registro WHERE id=?";
        String sqlCheckCount = "SELECT COUNT(*) AS count FROM Registro";
        String sqlResetAutoIncrement = "ALTER TABLE Registro AUTO_INCREMENT = 1";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {

            stmtDelete.setInt(1, id);
            int rowsAffected = stmtDelete.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Cliente eliminado");

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