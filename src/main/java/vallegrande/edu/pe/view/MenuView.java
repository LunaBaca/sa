package vallegrande.edu.pe.view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    public MenuView() {
        initUI();
    }

    private void initUI() {
        setTitle("Restaurante - Sistema de Gestión");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Contenedor Principal con la Imagen de Fondo ---
        ImageIcon originalIcon = null;

        // Intentar cargar la imagen con diferentes rutas posibles
        try {
            // Opción 1: Si está en src/main/resources/
            originalIcon = new ImageIcon(getClass().getClassLoader().getResource("fondo.png"));

            // Si no se encuentra, probar otras rutas
            if (originalIcon == null) {
                // Opción 2: Si está en un paquete resources
                originalIcon = new ImageIcon(getClass().getResource("/resources/fondo.png"));
            }
            if (originalIcon == null) {
                // Opción 3: Ruta relativa al paquete
                originalIcon = new ImageIcon(getClass().getResource("fondo.png"));
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
        }

        JLabel backgroundLabel;

        if (originalIcon != null && originalIcon.getImage() != null) {
            // Escalar la imagen si se cargó correctamente
            Image scaledImage = originalIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            backgroundLabel = new JLabel(scaledIcon);
        } else {
            // Fallback: crear un fondo sólido si la imagen no se carga
            System.out.println("No se pudo cargar la imagen de fondo. Usando color de fondo sólido.");
            backgroundLabel = new JLabel();
            backgroundLabel.setBackground(new Color(30, 30, 30)); // Fondo oscuro elegante
            backgroundLabel.setOpaque(true);
        }

        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);

        // --- Panel para los Botones (Transparente) ---
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false); // Hacer el panel transparente
        // Usamos BoxLayout para apilar los botones verticalmente
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // --- TÍTULO MEJORADO ---
        JLabel titleLabel = new JLabel("RESTAURANTE ANGELLO", SwingConstants.CENTER);

        // Fuente más llamativa y elegante
        try {
            // Intentar usar una fuente más estilizada
            Font customFont = new Font("Segoe UI", Font.BOLD, 28);
            titleLabel.setFont(customFont);
        } catch (Exception e) {
            // Fallback a fuentes seguras
            titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        }

        // Efecto de gradiente en el texto
        titleLabel.setForeground(Color.WHITE);

        // Sombra para el texto para mejor legibilidad
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0, 100), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Fondo semitransparente para el título
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(139, 0, 0, 180)); // Rojo vino semitransparente

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espaciado y agregado al panel
        contentPanel.add(Box.createVerticalStrut(50)); // Espacio superior
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(40)); // Espacio después del título

        // --- Botones Mejorados ---
        JButton btnClientes = createStyledButton("Gestión de Clientes",
                new Color(46, 204, 113),    // Verde
                new Color(39, 174, 96));    // Verde oscuro para hover

        JButton btnPedidos = createStyledButton("Gestión de Pedidos",
                new Color(155, 89, 182),    // Púrpura
                new Color(142, 68, 173));   // Púrpura oscuro para hover

        JButton btnSalir = createStyledButton("Salir del Sistema",
                new Color(192, 57, 43),     // Rojo
                new Color(169, 50, 38));    // Rojo oscuro para hover

        // Agregar los botones con espacio entre ellos
        contentPanel.add(btnClientes);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(btnPedidos);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(btnSalir);

        // --- Eventos de Botones ---
        btnClientes.addActionListener(e -> {
            ClienteView clienteView = new ClienteView();
            clienteView.setVisible(true);
        });

        btnPedidos.addActionListener(e -> {
            PedidoView pedidoView = new PedidoView();
            pedidoView.setVisible(true);
        });

        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro que desea salir del sistema?",
                    "Confirmar Salida",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                System.exit(0);
            }
        });

        // Agregar el panel de contenido al JLabel (que es ahora el ContentPane)
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Mostrar la ventana
        setVisible(true);
    }

    // Método auxiliar mejorado para crear botones estilizados con efecto hover
    private JButton createStyledButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(baseColor);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Sin borde por defecto
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        // Configurar el tamaño máximo para que todos tengan el mismo ancho
        Dimension preferredSize = new Dimension(320, 50);
        button.setPreferredSize(preferredSize);
        button.setMaximumSize(preferredSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente

        return button;
    }

    // Método sobrecargado para compatibilidad con código existente
    private JButton createStyledButton(String text, Color color) {
        return createStyledButton(text, color, color.darker());
    }
}