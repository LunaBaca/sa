package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

// CLASE RENOMBRADA
public class UsuarioView extends JFrame {
    private UsuarioController controller;
    private JTable tablaUsuarios; // Variable renombrada
    private DefaultTableModel modeloTabla;

    private JTextField txtId, txtNombre, txtApellidos, txtUsuario, txtDni, txtCorreo, txtFechaRegistro;

    // Formato para la fecha (ej: YYYY-MM-DD)
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public UsuarioView() {
        controller = new UsuarioController(); // Controlador renombrado
        initUI();
        cargarUsuarios(); // Método renombrado
    }

    private void initUI() {
        setTitle("🧑 Gestión de Usuarios - Tienda UG"); // Título actualizado
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // 1. ===== Inicialización de Campos de Entrada =====
        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombre = new JTextField();
        txtApellidos = new JTextField();
        txtUsuario = new JTextField();
        txtDni = new JTextField();
        txtCorreo = new JTextField();
        txtFechaRegistro = new JTextField(LocalDate.now().format(DATE_FORMATTER));

        // 2. ===== Tabla =====
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellidos", "Usuario", "DNI", "Correo", "F. Registro"}, 0);
        tablaUsuarios = new JTable(modeloTabla); // Variable renombrada
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Usuarios")); // Título actualizado
        panel.add(scrollTabla, BorderLayout.CENTER);

        // Listener para cargar datos en los campos al seleccionar una fila
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> { // Referencia a tablaUsuarios
            if (!e.getValueIsAdjusting() && tablaUsuarios.getSelectedRow() != -1) {
                int selectedRow = tablaUsuarios.getSelectedRow();

                txtId.setText(modeloTabla.getValueAt(selectedRow, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(selectedRow, 1).toString());
                txtApellidos.setText(modeloTabla.getValueAt(selectedRow, 2).toString());
                txtUsuario.setText(modeloTabla.getValueAt(selectedRow, 3).toString());
                txtDni.setText(modeloTabla.getValueAt(selectedRow, 4).toString());
                txtCorreo.setText(modeloTabla.getValueAt(selectedRow, 5).toString());

                // Mostrar la fecha de registro
                Object fecha = modeloTabla.getValueAt(selectedRow, 6);
                if (fecha instanceof LocalDate) {
                    txtFechaRegistro.setText(((LocalDate) fecha).format(DATE_FORMATTER));
                } else if (fecha != null) {
                    txtFechaRegistro.setText(fecha.toString());
                } else {
                    txtFechaRegistro.setText("");
                }
            }
        });

        // 3. ===== Formulario (Construcción del Panel) =====
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Usuario")); // Título actualizado

        JPanel camposPanel = new JPanel(new GridLayout(4, 4, 10, 5));

        camposPanel.add(new JLabel("ID (solo lectura):"));
        camposPanel.add(txtId);
        camposPanel.add(new JLabel("Nombre:"));
        camposPanel.add(txtNombre);

        camposPanel.add(new JLabel("Apellidos:"));
        camposPanel.add(txtApellidos);
        camposPanel.add(new JLabel("Usuario:"));
        camposPanel.add(txtUsuario);

        camposPanel.add(new JLabel("DNI:"));
        camposPanel.add(txtDni);
        camposPanel.add(new JLabel("Correo:"));
        camposPanel.add(txtCorreo);

        camposPanel.add(new JLabel("F. Registro (YYYY-MM-DD):"));
        camposPanel.add(txtFechaRegistro);
        camposPanel.add(new JLabel(""));
        camposPanel.add(new JLabel(""));

        formPanel.add(camposPanel, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.NORTH);

        // 4. ===== Botones =====
        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("➕ Agregar");
        JButton btnEditar = new JButton("📝 Editar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnActualizar = new JButton("🔄 Refrescar");
        JButton btnLimpiar = new JButton("🧹 Limpiar");

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);
        botones.add(btnLimpiar);

        panel.add(botones, BorderLayout.SOUTH);

        // 5. ===== Eventos (Lógica) =====

        // Función auxiliar para parsear la fecha
        java.util.function.Supplier<LocalDate> parseFecha = () -> {
            try {
                return LocalDate.parse(txtFechaRegistro.getText().trim(), DATE_FORMATTER);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de Fecha de Registro inválido. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        };


        btnAgregar.addActionListener(e -> {
            LocalDate fechaRegistro = parseFecha.get();
            if (fechaRegistro == null) return;

            // Crea un objeto Usuario
            Usuario nuevoUsuario = new Usuario( // Clase renombrada
                    txtNombre.getText(),
                    txtApellidos.getText(),
                    txtUsuario.getText(),
                    txtDni.getText(),
                    txtCorreo.getText(),
                    fechaRegistro
            );

            controller.crearUsuario(nuevoUsuario); // Método renombrado
            limpiarCampos();
            cargarUsuarios(); // Método renombrado
            JOptionPane.showMessageDialog(this, "Usuario agregado correctamente"); // Texto actualizado
        });

        btnEditar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                LocalDate fechaRegistro = parseFecha.get();
                if (fechaRegistro == null) return;

                // Crea un objeto Usuario completo para actualización
                Usuario usuarioEditado = new Usuario( // Clase renombrada
                        Integer.parseInt(txtId.getText()),
                        txtNombre.getText(),
                        txtApellidos.getText(),
                        txtUsuario.getText(),
                        txtDni.getText(),
                        txtCorreo.getText(),
                        fechaRegistro
                );

                controller.actualizarUsuario(usuarioEditado); // Método renombrado
                limpiarCampos();
                cargarUsuarios(); // Método renombrado
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente"); // Texto actualizado
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario para editar."); // Texto actualizado
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar este usuario?", // Texto actualizado
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarUsuario(Integer.parseInt(txtId.getText())); // Método renombrado
                    limpiarCampos();
                    cargarUsuarios(); // Método renombrado
                    JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente"); // Texto actualizado
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario para eliminar."); // Texto actualizado
            }
        });

        btnActualizar.addActionListener(e -> cargarUsuarios()); // Método renombrado
        btnLimpiar.addActionListener(e -> limpiarCampos());

        add(panel);
    }

    private void cargarUsuarios() { // Método renombrado
        if (modeloTabla == null) return;

        modeloTabla.setRowCount(0);
        List<Usuario> usuarios = controller.listarUsuarios(); // Variables y métodos renombrados
        for (Usuario u : usuarios) { // Objeto renombrado
            modeloTabla.addRow(new Object[]{
                    u.getId(),
                    u.get