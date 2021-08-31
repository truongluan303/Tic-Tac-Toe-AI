import javax.swing.JButton;


// An AI that uses minimax algorithm to play Tic Tac Toe game optimally
public class MinimaxAI {
    private final int LIM = 3;
    // AI moves after human. Therefore, AI's mark is O and human's mark is X
    private final char AI_MARK = 'O', HUMAN_MARK = 'X', EMPTY = ' ';
    private final int AI_WINS = 1, HUMAN_WINS = -1;

    // update the status of the game board
    public char[][] updateBoard(JButton[][] buttons) {
        char[][] board = new char[LIM][LIM];
        for (int i = 0; i < LIM; i++) {
            for (int j = 0; j < LIM; j++) {
                board[i][j] = buttons[i][j].getText().charAt(0);
            }
        }
        return board;
    }


    // The AI will make its move
    public int[] findBestMove(JButton[][] buttons) {
        char[][] myBoard = updateBoard(buttons);
        int bestScore = HUMAN_WINS - 1;
        int[] bestMove = new int[2];

        // Look at all the spots and find the available ones
        for (int i = 0; i < LIM; i++) {
            for (int j = 0; j < LIM; j++) {
                if (myBoard[i][j] == EMPTY) {
                    // Try to test an available spot by making a move there
                    myBoard[i][j] = AI_MARK;
                    // get the score out of each using the minimax algorithm
                    // since the AI just tried a move, it will be the human's turn, therefore it is not maximizing
                    int score = performMinimax(0, false, myBoard);
                    myBoard[i][j] = EMPTY;        // Since we are only testing out, we don't want to change the board
                    if (score > bestScore) {
                        // if there's a better score found, we set it to be the best score and save the location
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }

    
    // The minimax algorithm will consider all the possible ways the game can have and determine the best move
    // to be made to benefit the AI
    private int performMinimax(int treeDepth, boolean isMaximizing, char[][] board) {
        char winner = checkWinner(board);
        if (winner == AI_MARK)
            return AI_WINS;
        else if (winner == HUMAN_MARK)
            return HUMAN_WINS;
        else if (isFull(board))
            return 0;

        int bestScore = AI_WINS + 1;
        char player = HUMAN_MARK;
        if (isMaximizing) {
            bestScore = HUMAN_WINS - 1;
            player = AI_MARK;
        }
        // look for empty spot
        for (int i = 0; i < LIM; i++) {
            for (int j = 0; j < LIM; j++) {
                if (board[i][j] == EMPTY) {
                    // Make the move
                    board[i][j] = player;
                    // check all the cases after the move is made
                    int score = performMinimax(treeDepth + 1, !isMaximizing, board);
                    // if the new score is better, make it the bestScore
                    if (isMaximizing)
                        bestScore = Math.max(score, bestScore);
                    else
                        bestScore = Math.min(score, bestScore);
                    // undo the move so that we don't change the game board
                    board[i][j] = EMPTY;
                }
            }
        }
        return bestScore;
    }


    // check if there will be a winner after a test move is performed
    // If a winner is found, return that winner. Else, return EMPTY
    private char checkWinner(char[][] myBoard) {
        // check columns and rows
        for (int i = 0; i < LIM; i++)
            if (equal(myBoard[i][0], myBoard[i][1], myBoard[i][2]))
                return myBoard[i][0];
        for (int i = 0; i < LIM; i++)
            if (equal(myBoard[0][i], myBoard[1][i], myBoard[2][i]))
                return myBoard[0][i];
        // check diagonals
        if (equal(myBoard[0][0], myBoard[1][1], myBoard[2][2]))
            return myBoard[0][0];
        if (equal(myBoard[2][0], myBoard[1][1], myBoard[0][2]))
            return myBoard[2][0];
        return EMPTY;
    }


    private boolean equal(char a, char b, char c) {
        return a == b && b == c && a != EMPTY;
    }


    private boolean isFull(char[][] myBoard) {
        for (int i = 0; i < LIM; i++)
            for (int j = 0; j < LIM; j++)
                if (myBoard[i][j] == EMPTY)
                    return false;
        return true;
    }
}