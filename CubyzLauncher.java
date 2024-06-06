import javax.swing.SwingUtilities;

public class CubyzLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LauncherUI ui = new LauncherUI();
            ui.createAndShowGUI();
        });
    }
}
