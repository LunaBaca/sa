package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.ClienteController;
import vallegrande.edu.pe.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ClienteView extends JFrame {
    private ClienteController controller;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    private JTextField txtNombre, txtApellidos, txtUsuario, txtDni, txtCorreo, txtId;

    public ClienteView() {
        controller = new ClienteController();
        initUI();
        cargarClientes();
    }

    private void initUI() {
        setTitle("Gestión de Registros");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // ===== Tabla =====
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellidos", "Usuario", "DNI", "Correo", "Fecha Registro"}, 0);
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaClientes);
        panel.add(scroll, BorderLayout.CENTER);

        // Agrega el Listener para cargar los datos en los campos al hacer clic en la tabla
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaClientes.getSelectedRow() != -1) {
                int selectedRow = tablaClientes.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(selectedRow, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(selectedRow, 1).toString());
                txtApellidos.setText(modeloTabla.getValueAt(selectedRow, 2).toString());
                txtUsuario.setText(modeloTabla.getValueAt(selectedRow, 3).toString());
                txtDni.setText(modeloTabla.getValueAt(selectedRow, 4).toString());
                txtCorreo.setText(modeloTabla.getValueAt(selectedRow, 5).toString());
            }
        });

        // ===== Formulario =====
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellidos = new JTextField();
        txtUsuario = new JTextField();
        txtDni = new JTextField();
        txtCorreo = new JTextField();

        formPanel.add(new JLabel("ID (solo para editar/eliminar):"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Apellidos:"));
        formPanel.add(txtApellidos);
        formPanel.add(new JLabel("Usuario:"));
        formPanel.add(txtUsuario);
        formPanel.add(new JLabel("DNI:"));
        formPanel.add(txtDni);
        formPanel.add(new JLabel("Correo:"));
        formPanel.add(txtCorreo);

        panel.add(formPanel, BorderLayout.EAST);

        // ===== Botones =====
        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Refrescar");

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        panel.add(botones, BorderLayout.SOUTH);

        // ===== Eventos =====
        btnAgregar.addActionListener(e -> {
            controller.crearCliente(
                    txtNombre.getText(),
                    txtApellidos.getText(),
                    txtUsuario.getText(),
                    txtDni.getText(),
                    txtCorreo.getText(),
                    LocalDate.now() // Fecha automática
            );
            limpiarCampos();
            cargarClientes();
            JOptionPane.showMessageDialog(this, "Registro agregado correctamente");
        });

        btnEditar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                controller.actualizarCliente(
                        Integer.parseInt(txtId.getText()),
                        txtNombre.getText(),
                        txtApellidos.getText(),
                        txtUsuario.getText(),
                        txtDni.getText(),
                        txtCorreo.getText(),
                        LocalDate.now() // Fecha actual para la actualización
                );
                limpiarCampos();
                cargarClientes();
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Debe ingresar un ID para editar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar este registro?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarCliente(Integer.parseInt(txtId.getText()));
                    limpiarCampos();
                    cargarClientes();
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe ingresar un ID para eliminar.");
            }
        });

        btnActualizar.addActionListener(e -> cargarClientes());

        // Añadir panel
        add(panel);
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0); // limpiar tabla
        List<Cliente> clientes = controller.listarClientes();
        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{
                    c.getId(), c.getNombre(), c.getApellidos(), c.getUsuario(),
                    c.getDni(), c.getCorreo(), c.getFechaRegistro()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtUsuario.setText("");
        txtDni.setText("");
        txtCorreo.setText("");
    }
}