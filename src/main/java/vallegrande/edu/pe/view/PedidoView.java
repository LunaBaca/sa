package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.PedidoController;
import vallegrande.edu.pe.model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PedidoView extends JFrame {
    private PedidoController controller;
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private JTextField txtId, txtNombreCliente, txtTelefono, txtDireccion;
    private JTextArea txtDetallePedido;

    public PedidoView() {
        controller = new PedidoController();
        initUI();
        cargarPedidos();
    }

    private void initUI() {
        setTitle("📋 Gestión de Pedidos - Tienda UG");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // ===== Tabla =====
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre Cliente", "Teléfono", "Dirección", "Detalle Pedido"}, 0);
        tablaPedidos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaPedidos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Pedidos"));
        panel.add(scrollTabla, BorderLayout.CENTER);

        // Listener para cargar datos en los campos
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPedidos.getSelectedRow() != -1) {
                int selectedRow = tablaPedidos.getSelectedRow();
                txtId.setText(modeloTabla.getValueAt(selectedRow, 0).toString());
                txtNombreCliente.setText(modeloTabla.getValueAt(selectedRow, 1).toString());
                txtTelefono.setText(modeloTabla.getValueAt(selectedRow, 2).toString());
                txtDireccion.setText(modeloTabla.getValueAt(selectedRow, 3).toString());
                txtDetallePedido.setText(modeloTabla.getValueAt(selectedRow, 4).toString());
            }
        });

        // ===== Formulario =====
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));

        JPanel camposPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNombreCliente = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();

        camposPanel.add(new JLabel("ID (solo lectura):"));
        camposPanel.add(txtId);
        camposPanel.add(new JLabel("Nombre Cliente:"));
        camposPanel.add(txtNombreCliente);
        camposPanel.add(new JLabel("Teléfono:"));
        camposPanel.add(txtTelefono);
        camposPanel.add(new JLabel("Dirección:"));
        camposPanel.add(txtDireccion);

        formPanel.add(camposPanel, BorderLayout.NORTH);

        // Panel para detalle del pedido
        JPanel detallePanel = new JPanel(new BorderLayout());
        detallePanel.add(new JLabel("Detalle del Pedido:"), BorderLayout.NORTH);
        txtDetallePedido = new JTextArea(3, 20);
        txtDetallePedido.setLineWrap(true);
        txtDetallePedido.setWrapStyleWord(true);
        JScrollPane scrollDetalle = new JScrollPane(txtDetallePedido);
        detallePanel.add(scrollDetalle, BorderLayout.CENTER);

        formPanel.add(detallePanel, BorderLayout.CENTER);

        panel.add(formPanel, BorderLayout.NORTH);

        // ===== Botones =====
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

        // ===== Eventos =====
        btnAgregar.addActionListener(e -> {
            controller.crearPedido(
                    txtNombreCliente.getText(),
                    txtTelefono.getText(),
                    txtDireccion.getText(),
                    txtDetallePedido.getText()
            );
            limpiarCampos();
            cargarPedidos();
            JOptionPane.showMessageDialog(this, "Pedido agregado correctamente");
        });

        btnEditar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                controller.actualizarPedido(
                        Integer.parseInt(txtId.getText()),
                        txtNombreCliente.getText(),
                        txtTelefono.getText(),
                        txtDireccion.getText(),
                        txtDetallePedido.getText()
                );
                limpiarCampos();
                cargarPedidos();
                JOptionPane.showMessageDialog(this, "Pedido actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido para editar.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Está seguro de eliminar este pedido?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarPedido(Integer.parseInt(txtId.getText()));
                    limpiarCampos();
                    cargarPedidos();
                    JOptionPane.showMessageDialog(this, "Pedido eliminado correctamente");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido para eliminar.");
            }
        });

        btnActualizar.addActionListener(e -> cargarPedidos());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        add(panel);
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        List<Pedido> pedidos = controller.listarPedidos();
        for (Pedido p : pedidos) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombreCliente(),
                    p.getTelefono(),
                    p.getDireccion(),
                    p.getDetallePedido()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombreCliente.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtDetallePedido.setText("");
        if (tablaPedidos.getSelectedRow() != -1) {
            tablaPedidos.clearSelection();
        }
    }
}