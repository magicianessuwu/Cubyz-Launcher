import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.ColorUIResource;

public class ThemeSetter {
    public static void setTheme() {
        UIManager.put("Label.foreground", new ColorUIResource(UIColors.TEXT));
        UIManager.put("Panel.background", new ColorUIResource(UIColors.SURFACE1));
        UIManager.put("Button.background", new ColorUIResource(UIColors.OVERLAY0));
        UIManager.put("Button.foreground", new ColorUIResource(UIColors.TEXT));
        UIManager.put("ProgressBar.foreground", new Color(254, 100, 11));
        UIManager.put("TextArea.background", new ColorUIResource(UIColors.SURFACE1));
        UIManager.put("TextArea.foreground", new ColorUIResource(UIColors.TEXT));
        UIManager.put("TextArea.selectionBackground", new ColorUIResource(UIColors.SURFACE0));
        UIManager.put("TextArea.selectionForeground", new ColorUIResource(UIColors.TEXT));
        UIManager.put("TextArea.caretForeground", new ColorUIResource(UIColors.TEXT));
        UIManager.put("nimbusBase", new ColorUIResource(UIColors.BASE));
        UIManager.put("nimbusBlueGrey", new ColorUIResource(UIColors.MANTLE));
        UIManager.put("control", new ColorUIResource(UIColors.BASE));
        UIManager.put("text", new ColorUIResource(UIColors.TEXT));
        UIManager.put("nimbusLightBackground", new ColorUIResource(UIColors.CRUST));
        UIManager.put("nimbusFocus", new Color(147, 153, 178));
        UIManager.put("nimbusOrange", new Color(64, 160, 43));

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
