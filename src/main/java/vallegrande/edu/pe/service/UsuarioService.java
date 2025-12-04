package vallegrande.edu.pe.service;

import vallegrande.edu.pe.database.Database;
import vallegrande.edu.pe.model.Usuario; // ¡Importante: Usar la clase Usuario!

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    // Insertar
    public void crearUsuario(Usuario usuario) {
        // SQL adaptado a la tabla de Usuario (sin el campo 'rol')
        String sql = "INSERT INTO Usuario (nombre, apellidos, username, password, correo, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getCorreo());
            stmt.setDate(6, Date.valueOf(usuario.getFechaRegistro()));
            stmt.executeUpdate();
            System.out.println("✅ Usuario agregado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("correo"),
                        rs.getDate("fecha_registro").toLocalDate()
                );
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar
    public void actualizarUsuario(Usuario usuario) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE Usuario SET ");
        List<Object> parametros = new ArrayList<>();
        boolean primerCampo = true;

        if (usuario.getNombre() != null && !usuario.getNombre().trim().isEmpty()) {
            sqlBuilder.append("nombre=?");
            parametros.add(usuario.getNombre());
            primerCampo = false;
        }

        if (usuario.getApellidos() != null && !usuario.getApellidos().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("apellidos=?");
            parametros.add(usuario.getApellidos());
            primerCampo = false;
        }

        // --- Campos de Usuario (sin rol) ---
        if (usuario.getUsername() != null && !usuario.getUsername().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("username=?");
            parametros.add(usuario.getUsername());
            primerCampo = false;
        }

        if (usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("password=?");
            parametros.add(usuario.getPassword());
            primerCampo = false;
        }

        if (usuario.getCorreo() != null && !usuario.getCorreo().trim().isEmpty()) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("correo=?");
            parametros.add(usuario.getCorreo());
            primerCampo = false;
        }
        // -------------------------

        if (usuario.getFechaRegistro() != null) {
            if (!primerCampo) sqlBuilder.append(", ");
            sqlBuilder.append("fecha_registro=?");
            parametros.add(usuario.getFechaRegistro());
            primerCampo = false;
        }

        if (primerCampo) {
            System.out.println("⚠️ No hay campos para actualizar. No se realizó ninguna operación.");
            return;
        }

        sqlBuilder.append(" WHERE id=?");
        parametros.add(usuario.getId());

        String sql = sqlBuilder.toString();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < parametros.size(); i++) {
                // Hay que manejar correctamente el tipo Date para LocalDate
                Object param = parametros.get(i);
                if (param instanceof LocalDate) {
                    stmt.setDate(i + 1, Date.valueOf((LocalDate) param));
                } else {
                    stmt.setObject(i + 1, param);
                }
            }

            int filasAfectadas = stmt.executeUpdate();
            if(filasAfectadas > 0){
                System.out.println("✅ Usuario actualizado (campos modificados: " + (parametros.size() - 1) + ")");
            } else {
                System.out.println("⚠️ No se encontró usuario con ID: " + usuario.getId() + " o no hubo cambios.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar
    public void eliminarUsuario(int id) {
        String sqlDelete = "DELETE FROM Usuario WHERE id=?";
        String sqlCheckCount = "SELECT COUNT(*) AS count FROM Usuario";
        String sqlResetAutoIncrement = "ALTER TABLE Usuario AUTO_INCREMENT = 1";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {

            stmtDelete.setInt(1, id);
            int rowsAffected = stmtDelete.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Usuario eliminado");

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