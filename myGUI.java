import javax.swing.*;
import java.awt.*;


public class myGUI extends JPanel {
    private final int LIM = 3;
    private XO myXO;
    private static FileIO fileIO = new FileIO();
    private static JFrame f;


    public myGUI() {
        setLayout(new GridLayout(LIM, LIM));
        myXO = new XO(createResultWindow(), fileIO);
        addXOButtons();
    }



    public static void main(String[] args) {
        runGame();
    }



    public static void runGame() {
        // Our game frame
        f = new CustomFrame("Tic Tac Toe Game", 500, 500, 500, 500, false);

        // Opening window
        JFrame welcomeFrame = new CustomFrame("Tic Tac Toe Game", 100, 100, 500, 270, true);
        welcomeFrame.getContentPane().setLayout(new GridLayout(7, 3));

        JButton[] empty = new JButton[18];
        for (int i = 0; i < 18; i++) {
            empty[i] = new JButton();
            empty[i].setVisible(false);
        }
        JButton startButton = new JButton("START GAME");
        startButton.setPreferredSize(new Dimension(20, 20));
        startButton.setFocusPainted(false);
        JButton showMatchHistory = new JButton("MATCH HISTORY");
        showMatchHistory.setPreferredSize(new Dimension(20, 20));
        showMatchHistory.setFocusPainted(false);
        JButton exit = new JButton("EXIT");
        exit.setPreferredSize(new Dimension(20, 20));
        exit.setFocusPainted(false);

        for (int i = 0; i < 4; i++)
            welcomeFrame.add(empty[i]);
        welcomeFrame.getContentPane().add(startButton);
        for(int i = 4; i < 9; i++)
            welcomeFrame.add(empty[i]);
        welcomeFrame.getContentPane().add(showMatchHistory);
        for(int i = 9; i < 14; i++)
            welcomeFrame.add(empty[i]);
        welcomeFrame.add(exit);
        for(int i = 14; i < 18; i++)
            welcomeFrame.add(empty[i]);

        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            showMatchHistory.setVisible(false);
            welcomeFrame.setVisible(false);
            f.setVisible(true);
            f.getContentPane().add(new myGUI());
        });
        showMatchHistory.addActionListener(e -> {
            welcomeFrame.setVisible(false);

            JScrollPane myText = fileIO.read();
            CustomFrame frame = new CustomFrame("Match History", 500, 500, 500, 500, true);

            JButton button = new JButton();
            button.setFont(new Font("Courier", Font.BOLD, 13));
            button.setText("OK");
            button.setFocusPainted(false);
            button.addActionListener(a -> {
                frame.setVisible(false);
                reset();
                runGame();
            });
            frame.add(myText);
            frame.add(BorderLayout.SOUTH, button);
        });
        exit.addActionListener(e -> System.exit(0));
    }





    public void addXOButtons() {
        JButton[][] temp = myXO.initializeXOButton();
        for (int i = 0; i < LIM; i++)
            for (int j = 0; j < LIM; j++)
                add(temp[i][j]);
    }





    public JFrame createResultWindow() {
        JFrame frame = new JFrame();
        frame.setSize(300, 150);

        JButton button = new JButton();
        button.setFont(new Font("Courier", Font.BOLD, 13));
        button.setText("OK");
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            frame.setVisible(false);
            reset();
            runGame();
        });
        frame.add(BorderLayout.SOUTH, button);
        frame.setVisible(false);

        return frame;
    }





    public static void reset() {
        f.setVisible(false);
        f.getContentPane().invalidate();
        f.getContentPane().validate();
        f.getContentPane().repaint();
    }
}
