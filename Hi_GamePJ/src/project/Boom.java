package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Boom {
    private static final int EASY = 0;
    private static final int NORMAL = 1;
    private static final int HARD = 2;

    private int difficulty;
    private int[][] mineMap;
    private JButton[][] buttons;
    private int remainingMines;
    private int score;

    private JFrame frame;
    private JPanel minePanel;
    private JLabel mineRemainLabel;
    private JLabel scoreLabel;

    public Boom() {
        difficulty = -1;
        remainingMines = 0;
        score = 0;
    }

    private void createAndShowGUI() {
        frame = new JFrame("지뢰찾기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createDifficultySelectionPanel();

        frame.pack();
        frame.setVisible(true);
    }

    private void createDifficultySelectionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));

        JButton easyButton = createDifficultyButton("쉬움", EASY);
        JButton normalButton = createDifficultyButton("보통", NORMAL);
        JButton hardButton = createDifficultyButton("어려움", HARD);

        panel.add(easyButton);
        panel.add(normalButton);
        panel.add(hardButton);

        frame.getContentPane().add(panel, BorderLayout.NORTH);
    }

    private JButton createDifficultyButton(String text, int diff) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (difficulty != diff) {
                    if (JOptionPane.showConfirmDialog(frame, "해당 난이도로 게임을 구성하시겠습니까?") == JOptionPane.OK_OPTION) {
                        difficulty = diff;
                        initializeMineMap();
                        createMineField();
                        frame.pack();
                    }
                }
            }
        });
        return button;
    }

    private void initializeMineMap() {
        switch (difficulty) {
            case EASY:
                mineMap = new int[10][10];
                remainingMines = 10;
                break;
            case NORMAL:
                mineMap = new int[15][15];
                remainingMines = 30;
                break;
            case HARD:
                mineMap = new int[20][20];
                remainingMines = 60;
                break;
            default:
                return;
        }

        int numMines = 0;
        while (numMines < remainingMines) {
            int x = (int) (Math.random() * mineMap.length);
            int y = (int) (Math.random() * mineMap[0].length);
            if (mineMap[x][y] == 0) {
                mineMap[x][y] = 1;
                numMines++;
            }
        }
    }

    private void createMineField() {
        minePanel = new JPanel();
        minePanel.setLayout(new GridLayout(mineMap.length, mineMap[0].length));

        buttons = new JButton[mineMap.length][mineMap[0].length];

        for (int i = 0; i < mineMap.length; i++) {
            for (int j = 0; j < mineMap[0].length; j++) {
                buttons[i][j] = createMineButton(i, j);
                minePanel.add(buttons[i][j]);
            }
        }

        frame.getContentPane().add(minePanel, BorderLayout.CENTER);
        frame.getContentPane().add(createMineRemainPanel(), BorderLayout.SOUTH);
    }

    private JButton createMineButton(int x, int y) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(40, 40));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mineMap[x][y] == 1) {
                    JOptionPane.showMessageDialog(frame, "폭탄이 있습니다!");
                    button.setEnabled(false);
                    button.setText("");
                    score--;
                    scoreLabel.setText(Integer.toString(score));
                } else {
                    int numMines = countAdjacentMines(x, y);
                    button.setText(Integer.toString(numMines));
                    button.setEnabled(false);
                    if (numMines == 0) {
                        revealAdjacentZeros(x, y);
                    }
                    score++;
                    scoreLabel.setText(Integer.toString(score));
                }
            }
        });
        return button;
    }

    private int countAdjacentMines(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < mineMap.length && j >= 0 && j < mineMap[0].length && mineMap[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    private void revealAdjacentZeros(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < mineMap.length && j >= 0 && j < mineMap[0].length && buttons[i][j].isEnabled()) {
                    int numMines = countAdjacentMines(i, j);
                    buttons[i][j].setText(Integer.toString(numMines));
                    buttons[i][j].setEnabled(false);
                    if (numMines == 0) {
                        revealAdjacentZeros(i, j);
                    }
                    score++;
                    scoreLabel.setText(Integer.toString(score));
                }
            }
        }
    }

    private JPanel createMineRemainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel mineLabel = new JLabel("남은 폭탄 개수:");
        mineRemainLabel = new JLabel(Integer.toString(remainingMines));

        JLabel scoreTitleLabel = new JLabel("스코어:");
        scoreLabel = new JLabel(Integer.toString(score));

        panel.add(mineLabel);
        panel.add(mineRemainLabel);
        panel.add(scoreTitleLabel);
        panel.add(scoreLabel);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Boom().createAndShowGUI();
            }
        });
    }
}

