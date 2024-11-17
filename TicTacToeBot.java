package TicTacToeApp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TicTacToeBot {
    int boardWidth = 600;
    int boardHeight = 650; // 50px for the text panel on top (aka who's turn is it)

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JLabel roundCount = new JLabel();
    JLabel score = new JLabel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;
    int round = 0;
    int playerXScore = 0;
    int playerOScore = 0;
    Set<Map.Entry<Integer, Integer>> emptyTiles = new HashSet<>();

    TicTacToeBot() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.gray);
        frame.add(boardPanel);

        roundCount.setBackground(Color.darkGray);
        roundCount.setForeground(Color.white);
        roundCount.setText("Rounds: " + round);
        roundCount.setFont(new Font("Arial", Font.ITALIC, 20));
        roundCount.setOpaque(true);
        roundCount.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        textPanel.add(roundCount, BorderLayout.EAST);

        score.setForeground(Color.white);
        score.setText("X " + playerXScore + " : " + playerOScore + " Y");
        score.setFont(new Font("Arial",Font.PLAIN, 20));
        score.setOpaque(true);
        score.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        score.setBackground(Color.darkGray);
        textPanel.add(score, BorderLayout.WEST);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
                emptyTiles.add(new AbstractMap.SimpleEntry<>(r, c));

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JButton tile = (JButton) e.getSource();

                        if (tile.getText() == "") {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();

                            if (!gameOver) {
                                currentPlayer = playerO;
                                playPlayerO();
                                checkWinner();
                                turns++;
                                currentPlayer = playerX;
                                textLabel.setText(currentPlayer + "'s turn");

                            }

                        }

                    }
                });
            }
        }
    }

    void checkWinner() {
        // horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue;

            if (board[r][0].getText() == board[r][1].getText() &&
                    board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                increaseScore();
                gameOver = true;
                resetGame();
                return;
            }
        }
        // vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue;

            if (board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                increaseScore();
                gameOver = true;
                resetGame();
                return;
            }
        }
        // diagonal
        if (board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            increaseScore();
            gameOver = true;
            resetGame();
            return;
        }
        // anti-diagonal
        if (board[2][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[0][2].getText() &&
                board[2][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[0][2]);
                setWinner(board[1][1]);
                setWinner(board[2][0]);
            }
            increaseScore();
            gameOver = true;
            resetGame();
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
            resetGame();
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("It's a tie!");
        resetGame();
    }

    void increaseScore() {
        // Increase score of the winner
        if (currentPlayer == playerX) {
            playerXScore++;
        } else playerOScore++;
        score.setText("X " + playerXScore + " : " + playerOScore + " Y");
    }

    void resetGame() {
        // Restart game button
        JButton restartButton = new JButton();
        restartButton.setBackground(Color.red);
        restartButton.setForeground(Color.white);
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setFocusable(false);
        restartButton.setText("Play Again");
        frame.add(restartButton, BorderLayout.SOUTH);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton restartButton = (JButton) e.getSource();
                // Reset num of turns
                turns = 0;

                // Reset board
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        board[r][c].setText("");
                        board[r][c].setBackground(Color.darkGray);
                        board[r][c].setForeground(Color.white);

                    }
                }
                // Reset textLabel and remove restart button
                textLabel.setText(currentPlayer + "'s turn");
                frame.remove(restartButton);

                // Increase num of rounds
                round++;
                roundCount.setText("Rounds: " + round);

                // Repopulate set of index pairs
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        emptyTiles.add(new AbstractMap.SimpleEntry<>(r, c));

                        gameOver = false;
                    }
                }
            }
        });
    }

    void playPlayerO() {
        // Remove non-empty pair of indices from the set == a set of empty spots on the board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText() != "") {
                    emptyTiles.remove(new AbstractMap.SimpleEntry<>(r, c));
                }
            }
        }
        // Iterate through the set
        Iterator<Map.Entry<Integer, Integer>> iterator = emptyTiles.iterator();
        if (iterator.hasNext()) {
            Map.Entry<Integer, Integer> anyItem = iterator.next(); // Get the first tuple (== pair of indexes, e.g. (0,0) )
            int key = anyItem.getKey(); // e.g. 0
            int value = anyItem.getValue(); // e.g. 0
            board[key][value].setText(playerO); // Place "O" on an empty tile from the list
        }
    }
}
