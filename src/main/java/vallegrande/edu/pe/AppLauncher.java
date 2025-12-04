package vallegrande.edu.pe;

import vallegrande.edu.pe.view.MenuView; // Importar la nueva vista

public class AppLauncher {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Lanza la nueva vista de menú en lugar de ClienteView
            MenuView vista = new MenuView();
            vista.setVisible(true);
        });
    }
}