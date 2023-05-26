package gameTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TetrisGame extends JPanel {

    // 게임 상수
    private static final int ROWS = 30;
    private static final int COLS = 15;
    private static final int BLOCK_SIZE = 30;
    private static final int FPS = 60;
	private static final int BOARD_HEIGHT = 0;
	private static final int BOARD_WIDTH = 0;
	private static final int[][] BOARD = null;

    // 게임 상태
    private boolean isGameOver;
    private boolean isPaused;

    // 현재 블록
    private int currentBlockX;
    private int currentBlockY;
    private int currentBlockType;
    private int currentBlockRotation;

    // 게임 보드
    private boolean[][] board;
    
    // 키 입력
    private boolean[] keys;

    // 게임 루프
    private Timer gameLoop;
    
    private void gameLoop1() {
        if (!isGameOver && !isPaused) {
            if (canMoveDown()) {
                moveDown();
            } else {
                updateBoard();
                spawnNewBlock();
                if (!isValidMove(currentBlockX, currentBlockY, currentBlockType, currentBlockRotation)) {
                    isGameOver = true;
                    gameLoop.stop();
                }
            }
        }
        repaint();

        // 블록이 내려오는 간격 조절 (500 밀리초 = 0.5초)
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public TetrisGame() {
        // 게임 초기화
        isGameOver = false;
        isPaused = false;
        currentBlockX = COLS / 2;
        currentBlockY = 0;
        currentBlockType = 0;
        currentBlockRotation = 0;
        board = new boolean[ROWS][COLS];
        keys = new boolean[256];
        gameLoop = new Timer(1000 / FPS, new GameLoopListener());
        gameLoop.start();

        // 키 이벤트 리스너 등록
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;

                // 키 입력 처리
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    moveRight();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    rotateBlock();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    dropBlock();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }
        });
    }

    private void moveLeft() {
        if (canMoveLeft()) {
            currentBlockX--;
        }
    }

    private void moveRight() {
        if (canMoveRight()) {
            currentBlockX++;
        }
    }

    private void moveDown() {
        if (canMoveDown()) {
            currentBlockY++;
        }
    }

    private void rotateBlock() {
        int newRotation = (currentBlockRotation + 1) % 4;
        if (isValidMove(currentBlockX, currentBlockY, currentBlockType, newRotation)) {
            currentBlockRotation = newRotation;
        }
    }

    private void dropBlock() {
        while (canMoveDown()) {
            currentBlockY++;
        }
    }

    private boolean canMoveLeft() {
        // 왼쪽으로 이동 가능한지 확인
        int[][] block = getBlock(currentBlockType, currentBlockRotation);
        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[row].length; col++) {
                if (block[row][col] != 0) {
                    int newX = currentBlockX + col - 1;
                    int newY = currentBlockY + row;
                    if (newX < 0 || newY < 0 || newX >= COLS || newY >= ROWS || board[newY][newX]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean canMoveRight() {
        // 오른쪽으로 이동 가능한지 확인
        int[][] block = getBlock(currentBlockType, currentBlockRotation);
        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[row].length; col++) {
                if (block[row][col] != 0) {
                    int newX = currentBlockX + col + 1;
                    int newY = currentBlockY + row;
                    if (newX < 0 || newY < 0 || newX >= COLS || newY >= ROWS || board[newY][newX]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean canMoveDown() {
        // 아래로 이동 가능한지 확인
        int[][] block = getBlock(currentBlockType, currentBlockRotation);
        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[row].length; col++) {
                if (block[row][col] != 0) {
                    int newX = currentBlockX + col;
                    int newY = currentBlockY + row + 1;
                    if (newX < 0 || newY < 0 || newX >= COLS || newY >= ROWS || board[newY][newX]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isValidMove(int x, int y, int type, int rotation) {
        // 이동 또는 회전이 가능한지 확인
        int[][] block = getBlock(type, rotation);
        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[row].length; col++) {
                if (block[row][col] != 0) {
                    int newX = x + col;
                    int newY = y + row;
                    if (newX < 0 || newY < 0 || newX >= COLS || newY >= ROWS || board[newY][newX]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void updateBoard() {
        // 현재 블록을 게임 보드에 추가
        int[][] block = getBlock(currentBlockType, currentBlockRotation);
        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[row].length; col++) {
                if (block[row][col] != 0) {
                    int x = currentBlockX + col;
                    int y = currentBlockY + row;
                    board[y][x] = true;
                }
            }
        }

        // 줄이 꽉 차있는지 확인하고 제거
        for (int row = ROWS - 1; row >= 0; row--) {
            boolean isFull = true;
            for (int col = 0; col < COLS; col++) {
                if (!board[row][col]) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                // 현재 줄을 제거하고 위의 줄을 아래로 이동
                for (int r = row; r > 0; r--) {
                    for (int c = 0; c < COLS; c++) {
                        board[r][c] = board[r - 1][c];
                    }
                }
            }
        }
    }

    private void gameLoop() {
        if (!isGameOver && !isPaused) {
            if (canMoveDown()) {
                moveDown();
            } else {
                updateBoard();
                spawnNewBlock();
                if (!isValidMove(currentBlockX, currentBlockY, currentBlockType, currentBlockRotation)) {
                    isGameOver = true;
                    gameLoop.stop();
                }
            }
        }
        repaint();
    }

    private class GameLoopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameLoop1();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 게임 보드 그리기
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col]) {
                    g.setColor(Color.CYAN);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        // 현재 블록 그리기
        int[][] block = getBlock(currentBlockType, currentBlockRotation);
        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[row].length; col++) {
                if (block[row][col] != 0) {
                    int x = currentBlockX + col;
                    int y = currentBlockY + row;
                    g.setColor(Color.CYAN);
                    g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        // 게임 오버 메시지 그리기
        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 2);
        }

        // 일시 정지 메시지 그리기
        if (isPaused && !isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("PAUSED", getWidth() / 2 - 80, getHeight() / 2);
        }
    }

    private void spawnNewBlock() {
        currentBlockX = COLS / 2;
        currentBlockY = 0;
        currentBlockType = (int) (Math.random() * 7);
        currentBlockRotation = 0;
    }

    private int[][] getBlock(int type, int rotation) {
        // 블록 타입 및 회전에 따라 해당 블록 반환
        int[][] block;
        switch (type) {
            case 0: // I 블록
                block = new int[][]{
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                };
                break;
            case 1: // J 블록
                block = new int[][]{
                        {1, 0, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                };
                break;
            case 2: // L 블록
                block = new int[][]{
                        {0, 0, 1},
                        {1, 1, 1},
                        {0, 0, 0}
                };
                break;
            case 3: // O 블록
                block = new int[][]{
                        {1, 1},
                        {1, 1}
                };
                break;
            case 4: // S 블록
                block = new int[][]{
                        {0, 1, 1},
                        {1, 1, 0},
                        {0, 0, 0}
                };
                break;
            case 5: // T 블록
                block = new int[][]{
                        {0, 1, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                };
                break;
            case 6: // Z 블록
                block = new int[][]{
                        {1, 1, 0},
                        {0, 1, 1},
                        {0, 0, 0}
                };
                break;
            case 7: // 세로 블록
                block = new int[][]{
                    {0, 1, 0},
                    {0, 1, 0},
                    {0, 1, 0}
                };
                break;    
            default:
                block = new int[0][0];
        }

        // 회전 적용
        for (int r = 0; r < rotation; r++) {
            int[][] rotatedBlock = new int[block.length][block[0].length];
            for (int row = 0; row < block.length; row++) {
                for (int col = 0; col < block[row].length; col++) {
                    rotatedBlock[col][block.length - 1 - row] = block[row][col];
                }
            }
            block = rotatedBlock;
        }

        return block;
    }
    public void checkLines() {
        int linesCleared = 0;
        for (int row = BOARD_HEIGHT - 1; row >= 0; row--) {
            boolean lineIsFull = true;
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (BOARD[row][col] == 0) {
                    lineIsFull = false;
                    break;
                }
            }
            if (lineIsFull) {
                clearLine(row);
                linesCleared++;
            }
        }

        // 점수 계산
        if (linesCleared > 0) {
            int score = linesCleared * 10; // 한 줄에 10점씩 추가
            int totalScore = score;
            System.out.println("Score: " + totalScore);
        }
    }

    private void clearLine(int row) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
    	  JFrame frame = new JFrame("Tetris");
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.setSize(COLS * BLOCK_SIZE + 16, ROWS * BLOCK_SIZE + 39); // 창의 크기를 BLOCK_SIZE로 조정
    	    frame.setResizable(false);
    	    frame.add(new TetrisGame());
    	    frame.setVisible(true);
    	}
}