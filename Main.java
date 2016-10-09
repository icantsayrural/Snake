import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    private final static String KEY_DESCRIPTION = "Movement: Arrow keys, Restart: R, Pause: P, Start: S, Quit: Q";

    private static JFrame frame = new JFrame("Snakes");
    private static JLayeredPane layeredPane = new JLayeredPane();
    private static JPanel mainPanel = new JPanel();
    private static JPanel titlePanel = new JPanel();
    private static JPanel notifPanel = new JPanel();

    private static Snake snake;
    private static Food food;
    private static PowerUp powerUp;
    private static Sound sound;
    private static Level level;
    private static int score = 0;

    private static JLabel lifeLabel;
    private static JLabel scoreLabel;
    private static JLabel levelLabel;

    private static Timer snakeTimer;
    private static Timer paintTimer;

    private static boolean restarted = false;

    private static int snakeSpeed = 50;
    private static int paintSpeed = 33;

    private static boolean paused = false;

    static void runProgram() {
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(layeredPane);

        layeredPane.add(mainPanel, new Integer(0), 0);
        layeredPane.add(titlePanel, new Integer(1), 1);
        layeredPane.add(notifPanel, new Integer(2), 2);

        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        // -2 and -16 is a temp hack
        // JLayeredPane doesn't inherit the size of frame
        mainPanel.setBounds(0, 0, WIDTH-2, HEIGHT-16);
        mainPanel.setOpaque(true);

        // -2 here is also a hack
        titlePanel.setBounds(0, 3, WIDTH-2, 100);
        titlePanel.setOpaque(false);

        notifPanel.setBounds(0, 20, WIDTH-2, 100);
        notifPanel.setOpaque(false);

        JLabel descLabel = new JLabel(
                "<html>" +
                        "This is a classic game of Snakes with a twist.<br>" +
                        "The snake dies if it bumps into itself or the wall.<br>" +
                        "The snake gains score if it eats the red foods.<br>" +
                        "The snake gains life if it eats the golden nuggets.<br>" +
                        "There are 5 levels in this game.<br>" +
                        "To defeat it, you need to gain 200 scores on each level without dying.<br>" +
                        KEY_DESCRIPTION + "<br>" +
                        "<br>" +
                        "Author: Joanne Hong<br>" +
                        "Alias: j46hong<br>" +
                "</html>"
        );

        JButton startButton = new JButton("Start");
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                runGame();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }
        });

        mainPanel.add(descLabel);
        mainPanel.add(startButton);
        mainPanel.add(exitButton);

        frame.setVisible(true);
    }

    static void runGame() {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(Board.ROW, Board.COL, 0, 0));

        for (int y=0; y<Board.ROW; y++) {
            for (int x=0; x<Board.COL; x++) {
                mainPanel.add(Board.setSquare(new Square(x, y)));

                if (x == 0 || x == (Board.COL - 1) || y == 0 || y == (Board.ROW - 1)) {
                    Board.toWall(new Point(x, y));
                }
            }
        }

        // score board
        scoreLabel = new JLabel(scoreText());
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setLocation(0, 0);
        titlePanel.add(scoreLabel);

        // items & etc
        snake = new Snake();
        food = new Food(snake);
        powerUp = new PowerUp(snake);
        level = new Level(snake);
        sound = new Sound();

        // life
        lifeLabel = new JLabel(lifeText());
        lifeLabel.setForeground(Color.YELLOW);
        lifeLabel.setLocation(0, 1);
        titlePanel.add(lifeLabel);

        // level
        levelLabel = new JLabel(levelText());
        levelLabel.setForeground(Color.CYAN);
        levelLabel.setLocation(0, 2);
        titlePanel.add(levelLabel);

        snakeTimer = new Timer(snakeSpeed, null);
        paintTimer = new Timer(paintSpeed, null);

        mainPanel.revalidate();
        mainPanel.repaint();

        // listener for changing snake's directions
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                switch(key) {
                    case KeyEvent.VK_LEFT:
                        snake.changeDirection(Snake.WEST);
                        break;
                    case KeyEvent.VK_RIGHT:
                        snake.changeDirection(Snake.EAST);
                        break;
                    case KeyEvent.VK_UP:
                        snake.changeDirection(Snake.NORTH);
                        break;
                    case KeyEvent.VK_DOWN:
                        snake.changeDirection(Snake.SOUTH);
                        break;
                    case KeyEvent.VK_R:
                        paused = false;

                        generalRestart();

                        snake.resetLife();

                        // reset label
                        level.eraseAll();
                        level.reset();

                        // title bar info
                        lifeLabel.setText(lifeText());
                        score = 0;
                        scoreLabel.setText(scoreText());
                        levelLabel.setText(levelText());

                        notifPanel.removeAll();

                        snakeTimer.restart();
                        break;
                    case KeyEvent.VK_P:
                        paused = true;

                        JLabel explanationLabel = new JLabel(KEY_DESCRIPTION);
                        explanationLabel.setForeground(Color.RED);
                        explanationLabel.setLocation(0, 1);
                        notifPanel.add(explanationLabel);

                        break;
                    case KeyEvent.VK_S:
                        paused = false;

                        notifPanel.removeAll();
                        break;
                    case KeyEvent.VK_Q:
                        System.exit(1);
                        break;
                }
            }
        });

        // create a task for snake's constant movement
        ActionListener snakeTask = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!paused) {
                    if (restarted) {
                        rest();
                        restarted = false;
                    }

                    snake.move();

                    if (food.isEaten()) {
                        sound.playFoodSound();

                        snake.grow();

                        food.setRandomLocation();

                        // update score
                        score = score + snake.getSize() - 1;
                        scoreLabel.setText(scoreText());

                        // create power up
                        if (!powerUp.isActive() && powerUp.thresholdReached(score)) {
                            powerUp.setRandomLocation();
                            powerUp.setActive(true);
                        }
                    } else if (powerUp.isActive() && powerUp.isEaten()) {
                        sound.playPowerUpSound();
                        
                        snake.lifeUp();

                        lifeLabel.setText(lifeText());

                        powerUp.setActive(false);
                        powerUp.increaseThreshold(score);
                    }
                }
            }
        };

        // create a task for repainting
        ActionListener paintTask = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!paused) {
                    if (snake.isDead() && snake.hasLife()) {
                        sound.playDeathSound();

                        snakeTimer.stop();

                        snake.lifeDown();
                        lifeLabel.setText(lifeText());

                        if (snake.hasLife()) {
                            generalRestart();

                            rest();
                            snakeTimer.restart();
                        } else {
                            JLabel deadLabel = new JLabel("DEAD!");
                            deadLabel.setForeground(Color.RED);
                            deadLabel.setLocation(0, 1);
                            notifPanel.add(deadLabel);
                        }
                    } else if (level.up(score)) {
                        sound.playLevelUpSound();

                        snakeTimer.stop();

                        levelLabel.setText(levelText());
                        score = 0;

                        if (level.finishedGame()) {
                            JLabel finishedLabel = new JLabel("DONE!");
                            finishedLabel.setForeground(Color.RED);
                            finishedLabel.setLocation(0, 1);
                            notifPanel.add(finishedLabel);

                            paused = true;
                        } else {
                            generalRestart();
                            level.drawWallsAndHoles();
                            scoreLabel.setText(scoreText());

                            rest();
                            snakeTimer.restart();
                        }
                    }
                }

                mainPanel.revalidate();
                mainPanel.repaint();
            }
        };

        snakeTimer.addActionListener(snakeTask);
        snakeTimer.start();

        paintTimer.addActionListener(paintTask);
        paintTimer.start();
    }

    private static String scoreText() {
        return "Score: " + String.valueOf(score);
    }

    private static String lifeText() {
        return "Life: " + String.valueOf(snake.getLife());
    }

    private static String levelText() {
        return "Level: " + String.valueOf(level.getLevel());
    }

    private static void generalRestart() {
        snake.resetPosition();

        food.erase();
        food.setRandomLocation();

        powerUp.erase();
        powerUp.setActive(false);
        powerUp.setDefaultThreshold();

        restarted = true;
    }

    private static void rest() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                // from fps to milliseconds
                paintSpeed = (int) (1000/Double.parseDouble(args[0]));

                snakeSpeed = (int) ((1/Double.parseDouble(args[1])) * 250);
            } catch (NumberFormatException e) {
                System.out.println("Error: arguments are not integers");
                System.exit(1);
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runProgram();
            }
        });
    }
}