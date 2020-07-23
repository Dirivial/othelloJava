import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//TODO: Add end screen.

public class GameWindow implements ActionListener {
    JFrame f;
    JFrame optF;
    ArrayList<ArrayList<JButton>> gameBoard = new ArrayList<>();
    Board tileBoard;
    Color currentPlayer = Color.BLUE;

    JButton currentPlayerButton = new JButton();
    JButton exitButton = new JButton();
    JButton restartButton = new JButton();
    JLabel numOfRed = new JLabel();
    JLabel numOfBlue = new JLabel();

    String gameMode = "HUMAN";

    OthelloSuperAI computer;

    final int rows = 8;
    final int cols = 8;


    public GameWindow() {

        f = new JFrame("Othello!");
        optF = new JFrame("Information");
        f.setLayout(new GridLayout(rows, cols));

        generateButtonBoard();
        generateOptionals();

        this.tileBoard = new Board(cols, rows);
        this.tileBoard.updateBoardFromGameBoard(this.gameBoard);
        this.tileBoard.calculatePossibleMoves(this.currentPlayer);
        this.tileBoard.updateEnableStatus(this.gameBoard);

        startMenu();

        f.setSize(817, 839);
        optF.setSize(217,839);
    }

    private void startMenu() {
        JFrame frame = new JFrame("Who will you go against?");

        JButton buttonHuman = new JButton("Human");
        JButton buttonRandomAI = new JButton("AI - Random");
        JButton buttonMinMaxAI = new JButton("AI - MinMax");
        JButton buttonABPruningAI = new JButton("AI - ABPruning");


        buttonHuman.setBounds(0,0,100,100);
        buttonRandomAI.setBounds(100,0,100,100);
        buttonMinMaxAI.setBounds(200,0,100,100);
        buttonABPruningAI.setBounds(300,0,100,100);

        buttonHuman.addActionListener(e -> {
            f.setTitle("Othello vs Human!");
            frame.dispose();
            optF.setVisible(true);
            f.setVisible(true);
            gameMode = "HUMAN";
        });

        buttonRandomAI.addActionListener(e -> {
            f.setTitle("Othello vs RandomAI");
            frame.dispose();
            optF.setVisible(true);
            f.setVisible(true);
            gameMode = "AIRANDOM";
        });

        buttonMinMaxAI.addActionListener(e -> {
            f.setTitle("Othello vs MinMaxAI");
            frame.dispose();
            optF.setVisible(true);
            f.setVisible(true);
            gameMode = "AIMINMAX";
        });

        buttonABPruningAI.addActionListener(e -> {
            f.setTitle("Othello vs ABPruningAI");
            frame.dispose();
            optF.setVisible(true);
            f.setVisible(true);
            gameMode = "AIABPRUNING";
        });

        frame.add(buttonHuman);
        frame.add(buttonRandomAI);
        frame.add(buttonMinMaxAI);
        frame.add(buttonABPruningAI);

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(2,2));
        frame.setVisible(true);
    }

    private void endMenu() {
        updateOptionals();
        JFrame frame = new JFrame("Game ended");
        frame.setLayout(new GridLayout(2, 2));

        JLabel label = new JLabel();

        if ((Integer.parseInt(this.numOfRed.getText()) > Integer.parseInt(this.numOfBlue.getText()))) {
            label.setText("Computer won!");
        } else {
            label.setText("You won!");
        }

        frame.add(label);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void nextPlayer() {
        if (currentPlayer.equals(Color.BLUE)) {
            humanTurn();
        } else {
            switch (gameMode) {
                case "AIRANDOM":
                    aiRandomTurn();
                    break;
                case "AIMINMAX":
                    aiMinMaxTurn();
                    break;
                case "AIABPRUNING":
                    aiABPruningTurn();
                    break;
                default:
                    humanTurn();
            }

        }
    }

    private void humanTurn() {
        updateGameBoard();
        if (this.tileBoard.listOfAllEnabledPositions().size() == 0) {
            endTurn();
        }
    }

    private void aiRandomTurn() {
        updateGameBoard();
        if (this.tileBoard.listOfAllEnabledPositions().size() != 0) {
            Position pos;
            computer = new OthelloRandomAI(this.tileBoard, Color.RED);
            pos = computer.getMove();
            this.tileBoard.makeMoveFromPosition(pos, Color.RED);
            this.tileBoard.updateColorsOnGameBoard(this.gameBoard);
        }
        endTurn();
    }

    private void aiMinMaxTurn() {
        updateGameBoard();
        if (this.tileBoard.listOfAllEnabledPositions().size() != 0) {
            Position pos;
            computer = new OthelloMinMaxAI(this.tileBoard, Color.RED);
            pos = computer.getMove();
            this.tileBoard.makeMoveFromPosition(pos, Color.RED);
            this.tileBoard.updateColorsOnGameBoard(this.gameBoard);
        }
        endTurn();
    }

    private void aiABPruningTurn() {
        updateGameBoard();
        if (this.tileBoard.listOfAllEnabledPositions().size() != 0) {
            Position pos;
            computer = new OthelloABPruningAI(this.tileBoard, Color.RED);
            pos = computer.getMove();
            this.tileBoard.makeMoveFromPosition(pos, Color.RED);
            this.tileBoard.updateColorsOnGameBoard(this.gameBoard);
        }
        endTurn();
    }

    private void endTurn() {
        disableButtons();
        switchPlayer();
        updateOptionals();
        if (gameIsOver()) {

            endMenu();
            resetBoard();
        }
        nextPlayer();
    }

    private void updateGameBoard() {
        this.tileBoard.updateBoardFromGameBoard(this.gameBoard);
        this.tileBoard.updateColorsOnGameBoard(this.gameBoard);
        disableButtons();
        this.tileBoard.updateEnableStatus(this.gameBoard);
        this.tileBoard.calculatePossibleMoves(currentPlayer);
        this.tileBoard.updateEnableStatus(this.gameBoard);
    }

    private void generateOptionals() {
        exitButton.setText("EXIT");
        exitButton.setBackground(Color.MAGENTA);
        exitButton.setBounds(0, 700, 200, 100);
        exitButton.addActionListener(this);
        optF.add(exitButton);

        restartButton.setText("RESET");
        restartButton.setBackground(Color.GRAY);
        restartButton.setBounds(0, 600, 200, 100);
        restartButton.addActionListener(this);
        optF.add(restartButton);

        JLabel label = new JLabel("Current Player:");
        label.setBounds(0, 0, 200, 100);
        label.setSize(label.getPreferredSize());
        optF.add(label);

        currentPlayerButton = new JButton("");
        currentPlayerButton.setEnabled(false);
        currentPlayerButton.setBounds(50, 90, 100, 100);
        currentPlayerButton.setBackground(Color.BLUE);
        optF.add(currentPlayerButton);

        this.numOfRed.setBounds(0, 250, 100, 10);
        this.numOfRed.setText("<html>Number of red markers: <br><html/>" + "<p style=\"font-size:30px\">2");
        this.numOfRed.setSize(this.numOfRed.getPreferredSize());
        optF.add(this.numOfRed);


        this.numOfBlue.setBounds(0, 350, 100, 100);
        this.numOfBlue.setSize(this.numOfBlue.getPreferredSize());
        this.numOfBlue.setText("<html>Number of blue markers: <br><html/>" + "<p style=\"font-size:30px\">2");
        optF.add(this.numOfBlue);
    }
    private void updateOptionals() {
        this.currentPlayerButton.setBackground(this.currentPlayer);
        ArrayList<Integer> colors = this.tileBoard.calculateMarkers();
        this.numOfRed.setText("<html>Number of red markers: <br><html/><p style=\"font-size:30px\">" + colors.get(1).toString());
        this.numOfBlue.setText("<html>Number of blue markers: <br><html/><p style=\"font-size:30px\">" + colors.get(0).toString());
    }

    private void generateButtonBoard() {
        JButton button;

        for (int i = 0; i < this.cols; i++) {
            gameBoard.add(new ArrayList<>());
            for (int j = 0; j < this.rows; j++) {
                //button = new JButton("X: " + i + ", Y:" + j);
                button = new JButton();
                f.add(button);
                gameBoard.get(i).add(button);
                button.setBounds(i*100, j*100, 100, 100);
                button.addActionListener(this);
                setButtonColor(button, i, j);
            }
        }
    }

    private void setButtonColor(JButton button, int i, int j) {
        if (i == 3 && j == 3) {
            button.setBackground(Color.BLUE);
        } else if (i == 3 && j == 4) {
            button.setBackground(Color.RED);
        } else if (i == 4 && j == 3) {
            button.setBackground(Color.RED);
        } else if (i == 4 && j == 4){
            button.setBackground(Color.BLUE);
        } else {
            button.setBackground(Color.DARK_GRAY);
        }
        button.setEnabled(false);
    }

    /**
     * Resets the board to its original state
     */
    private void resetBoard() {
        JButton button;

        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                button = gameBoard.get(i).get(j);
                setButtonColor(button, i, j);
            }
        }
        this.tileBoard.updateBoardFromGameBoard(this.gameBoard);
        this.currentPlayer = Color.BLUE;
        this.tileBoard.calculatePossibleMoves(this.currentPlayer);
        this.tileBoard.updateEnableStatus(this.gameBoard);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JButton) {
            if (((JButton) e.getSource()).getText().equals("EXIT")) {
                f.dispose();
                optF.dispose();
            } else if (((JButton) e.getSource()).getText().equals("RESET")) {
                resetBoard();
            } else {
                this.tileBoard.makeMoveFromButton(this.currentPlayer, gameBoard, (JButton) e.getSource());
                this.tileBoard.updateColorsOnGameBoard(this.gameBoard);
                disableButtons();
            }
        }
        endTurn();
    }

    private void disableButtons() {
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                this.gameBoard.get(i).get(j).setEnabled(false);
            }
        }
    }

    private void switchPlayer() {
        if (this.currentPlayer == Color.BLUE) {
            this.currentPlayer = Color.RED;
        } else {
            this.currentPlayer = Color.BLUE;
        }
    }

    private boolean gameIsOver() {
        for (ArrayList<JButton> arrList: this.gameBoard) {
            for (JButton button: arrList) {
                if (button.getBackground() == Color.DARK_GRAY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}
