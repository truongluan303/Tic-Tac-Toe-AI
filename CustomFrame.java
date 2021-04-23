import javax.swing.*;

public class CustomFrame extends JFrame {
    public CustomFrame(String title, int x, int y, int width, int height, boolean bool) {
        super(title);
        setVisible(bool);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(x, y, width, height);
    }
}
