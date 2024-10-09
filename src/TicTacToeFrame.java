import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private JButton[][] buttons; // 3x3 grid of buttons
    private char currentPlayer;  // Current player 'X' or 'O'
    private boolean gameEnded;

    public TicTacToeFrame() {

        setTitle("Tic-Tac-Toe Game");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameEnded = false;


        JPanel boardPanel = createBoardPanel();
        add(boardPanel, BorderLayout.CENTER);


        JPanel quitPanel = createQuitPanel();
        add(quitPanel, BorderLayout.SOUTH);

        setVisible(true);
    }


    private JPanel createBoardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3)); // 3x3 grid layout


        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusable(false);
                panel.add(buttons[row][col]);


                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
            }
        }
        return panel;
    }


    private JPanel createQuitPanel() {
        JPanel panel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.PLAIN, 20));

        quitButton.addActionListener(e -> {
            int confirmed = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to quit?", "Quit Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        panel.add(quitButton);
        return panel;
    }


    private void checkGameStatus() {
        if (checkWinner()) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            gameEnded = true;
            askPlayAgain();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a tie!");
            gameEnded = true;
            askPlayAgain();
        }
    }


    private boolean checkWinner() {

        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }

            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }

        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }


    private void askPlayAgain() {
        int option = JOptionPane.showConfirmDialog(this,
                "Do you want to play again?", "Play Again",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = 'X';
        gameEnded = false;
    }


    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (buttons[row][col].getText().isEmpty() && !gameEnded) {
                buttons[row][col].setText(String.valueOf(currentPlayer));

                checkGameStatus();

                if (!gameEnded) {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move, try again!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
