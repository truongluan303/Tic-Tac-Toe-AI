import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class XO {
    private final int LIM = 3, AI_MODE = 0;
    private final char FIRST = 'X', SECOND = 'O', EMPTY = ' ';
    private int count = 0;              // keep track how many boxes have been marked
    private JButton[][] buttons;        // The buttons that make up the game board
    private char playerMark;
    private myGUI panel;
    private MinimaxAI AI;
    private int mode;

    // constructor
    public XO(myGUI panel) {
        buttons = new JButton[LIM][LIM];
        this.panel = panel;
        AI = new MinimaxAI();
    }

    public void initializeXOButtons() {
        int c = 0;
        for (int i = 0; i < LIM; i++) {
            for (int j = 0; j < LIM; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setVisible(true);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                buttons[i][j].addActionListener(myListener());
                buttons[i][j].setFont(new Font("Bradley Hand ITC", Font.BOLD | Font.ITALIC, 130));
                buttons[i][j].setName(Integer.toString(c));
                buttons[i][j].setText(Character.toString(EMPTY));
                buttons[i][j].setFocusPainted(false);
                panel.add(buttons[i][j]);
                c++;
            }
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
        playerMark = FIRST;
    }

    private void mark(int y, int x) {
        JButton buttonToMark = buttons[y][x];
        mark(buttonToMark);
    }


    private void mark(JButton buttonClicked) {
        char result;
        int location;
        int x, y;
        // we only make changes to the buttons that were not selected before.
        if (!buttonClicked.isSelected()) {
            char temp = playerMark;
            buttonClicked.setText(String.valueOf(temp));
            buttonClicked.setSelected(true);

            if (playerMark == FIRST) {
                buttonClicked.setBackground(Color.CYAN);
                playerMark = SECOND;
            } else {
                buttonClicked.setBackground(Color.ORANGE);
                playerMark = FIRST;
            }
            location = Integer.parseInt(buttonClicked.getName());
            y = location / LIM;
            x = location % LIM;
            count++;                                 // increase the number of moves made

            if (checkWinner(x, y, temp)) {
                result = temp;            // If a winner is found, stop the game
                disableButtons();
                panel.createResultBox(result + " wins!");
            } else if (count == LIM * LIM) {
                disableButtons();               // if all 9 boxes have been marked, stop the game
                panel.createResultBox("Draw!");
            }

            // if the mode is 1_player, the AI will make a move right after the player clicks a button
            if (mode == AI_MODE) {
                AI.updateBoard(buttons);        // update the board for the AI
                // If it is the AI's turn, then we find the best move
                if (playerMark == SECOND) {
                    int[] bestMove = AI.findBestMove(buttons);
                    mark(bestMove[0], bestMove[1]);
                }
            }
        }
    }


    private boolean checkWinner(int x, int y, char player) {
        int c;

        // check row.
        for (c = 0; c < LIM; c++)
            if (buttons[y][c].getText().charAt(0) != player)
                break;
        if (c == LIM) {
            grayOutButtons();
            for (int i = 0; i < LIM; i++)
                buttons[y][i].setBackground(Color.GREEN);
            return true;
        }
        // check column.
        for (c = 0; c < LIM; c++)
            if (buttons[c][x].getText().charAt(0) != player)
                break;
        if (c == LIM) {
            grayOutButtons();
            for (int i = 0; i < LIM; i++)
                buttons[i][x].setBackground(Color.GREEN);
            return true;
        }
        // Check upward diagonal.
        if (x == y) {
            for (c = 0; c < LIM; c++)
                if (buttons[c][c].getText().charAt(0) != player)
                    break;
            if (c == LIM) {
                grayOutButtons();
                for (int i = 0; i < LIM; i++) {
                    buttons[i][i].setBackground(Color.GREEN);
                }
                return true;
            }
        }
        // check downward diagonal
        for (c = 0; c < LIM; c++)
            if (buttons[c][LIM - c - 1].getText().charAt(0) != player)
                break;
        if (c == LIM) {
            grayOutButtons();
            for (int i = 0; i < LIM; i++)
                buttons[i][LIM - i - 1].setBackground(Color.GREEN);
        }
        return c == LIM;
    }


    private ActionListener myListener() {
        return e -> {
            JButton buttonClicked = (JButton) e.getSource(); // get the button that was clicked
            mark(buttonClicked);
        };
    }

    private void grayOutButtons() {
        for (int i = 0; i < LIM; i++)
            for (int j = 0; j < LIM; j++) 
                buttons[i][j].setBackground(Color.lightGray);
    }

    private void disableButtons() {
        for (int i = 0; i < LIM; i++)
            for (int j = 0; j < LIM; j++) 
                buttons[i][j].setEnabled(false);
    }
}