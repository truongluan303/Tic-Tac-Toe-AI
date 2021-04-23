import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class XO {
    private final int LIM = 3;
    private final char FIRST = 'X';
    private final char SECOND = 'O';
    private int count = 0;
    private JButton[][] buttons;
    private static FileIO fileIO;
    private char playerMark = FIRST;
    private static JLabel resultMessage;
    private static JFrame resultWindow;



    public XO(JFrame resultWindow, FileIO fileIO) {
        buttons = new JButton[LIM][LIM];
        XO.resultWindow = resultWindow;
        XO.fileIO = fileIO;
    }



    public JButton[][] initializeXOButton() {
        int count = 0;
        for (int i = 0; i < LIM; i++) {
            for (int j = 0; j < LIM; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setVisible(true);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                buttons[i][j].addActionListener(myListener());
                buttons[i][j].setFont(new Font("Courier", Font.BOLD, 80));
                buttons[i][j].setName(Integer.toString(count));
                buttons[i][j].setText(" ");
                buttons[i][j].setFocusPainted(false);
                count++;
            }
        }
        return buttons;
    }



    public char mark(char player, int location) {
        int x, y;

        y = location / LIM;
        x = location % LIM;

        buttons[y][x].setText(String.valueOf(playerMark));
        count++;


        if (checkWinner(x, y, player))
            return player;
        else if (count == LIM * LIM)
            return 'D';
        else
            return '0';
    }




    private boolean checkWinner(int x, int y, char player) {
        int c;

        // check row.
        for (c = 0; c < LIM; c++)
            if (buttons[y][c].getText().charAt(0) != player)
                break;
        if (c == LIM)
            return true;


        // check column.
        for (c = 0; c < LIM; c++)
            if (buttons[c][x].getText().charAt(0) != player)
                break;
        if (c == LIM)
            return true;


        // Check upward diagonal.
        if (x == y) {
            for (c = 0; c < LIM; c++)
                if (buttons[c][c].getText().charAt(0) != player)
                    break;
            if (c == LIM)
                return true;
        }


        // check downward diagonal
        for (c = 0; c < LIM; c++)
            if (buttons[c][LIM - c - 1].getText().charAt(0) != player)
                break;
        return c == LIM;
    }




    public ActionListener myListener() {
        return e -> {
            JButton buttonClicked = (JButton) e.getSource(); // get the button that was clicked

            if (!buttonClicked.isSelected()) {
                buttonClicked.setText(String.valueOf(playerMark));
                buttonClicked.setSelected(true);

                char result = mark(playerMark, Integer.parseInt(buttonClicked.getName()));

                if (playerMark == FIRST) {
                    buttonClicked.setBackground(Color.CYAN);
                    playerMark = SECOND;
                } else {
                    buttonClicked.setBackground(Color.ORANGE);
                    playerMark = FIRST;
                }

                if (result == FIRST || result == SECOND) {
                    resultMessage = createResultMessage();
                    resultMessage.setText("The winner is " + result);
                    disableButtons();
                    resultWindow.setVisible(true);

                    fileIO.write(result);
                } else if (result == 'D') {
                    resultMessage = createResultMessage();
                    resultMessage.setText("Draw!!! Well done players!");
                    disableButtons();
                    resultWindow.setVisible(true);
                    fileIO.write(' ');
                }
            }
        };
    }




    public void disableButtons() {
        for (int i = 0; i < LIM; i++)
            for (int j = 0; j < LIM; j++)
                buttons[i][j].setEnabled(false);
    }




    public JLabel createResultMessage() {
        JLabel resultMessage = new JLabel("", CENTER);
        resultMessage.setFont(new Font("Courier", Font.BOLD, 20));
        resultMessage.setHorizontalAlignment(JLabel.CENTER);
        resultWindow.add(resultMessage);
        resultMessage.setLocation(CENTER, CENTER);

        return resultMessage;
    }
}