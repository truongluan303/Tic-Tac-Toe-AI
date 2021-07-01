import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class myGUI extends JPanel {
    private final int LIM = 3;
    private XO myXO = new XO(this);
    private static CustomFrame f;
    private static ImageIcon icon = new ImageIcon("src/icon.png");

    public myGUI() {
        setLayout(new GridLayout(LIM, LIM));
        myXO.initializeXOButtons();
        chooseMode();
        f.setVisible(true);
    }

    public static void main(String[] args) {
        runGame();
    }

    public void createResultBox(String message) {
        String display = message + "\nDo you want to replay?";
        int chosen = JOptionPane.showConfirmDialog(f, display,
                message, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        // Decide if the player wants to replay or not
        if (chosen == 0) 
            f.reset();
        else
            System.exit(0);
    }

    private static void runGame() {
        // Our game frame
        f = new CustomFrame("Tic Tac Toe Game",
                500, 500, 500, 500, false, icon.getImage());
        f.getContentPane().add(new myGUI());     
    }

    private void chooseMode() {
        String message = "Please choose your mode";
        String[] options = {"1 Player mode", "2 Players mode"};
        int mode = JOptionPane.showOptionDialog(f, message, null, JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        myXO.setMode(mode);
    }

    /////////////////////////
    ////   Custom Frame  ////
    private static class CustomFrame extends JFrame {
        private CustomFrame(String title, int x, int y, int width, int height, boolean bool, Image icon) {
            super(title);
            setVisible(bool);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setBounds(x, y, width, height);
            setLocationRelativeTo(null);
            setIconImage(icon);
        }
    
        private void reset() {
            setVisible(false);
            getContentPane().removeAll();
            add(new myGUI());
            SwingUtilities.updateComponentTreeUI(this);
            setVisible(true);
        }
    }
}