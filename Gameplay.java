import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 48;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;
    private Graphics2D g;

    public Gameplay() {
        map = new MapGenerator(4, 12);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background
        Graphics2D g2d= (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(1, 1, 692, 592);

        // drawing map
        map.draw((Graphics2D) g);

        // borders
        g2d.setColor(Color.yellow);
        g2d.fillRect(0, 0, 3, 592);
        g2d.fillRect(0, 0, 692, 3);
        g2d.fillRect(691, 0, 3, 592);

        // the scores
        g2d.setColor(Color.white);
        g2d.setFont(new Font("serif", Font.BOLD, 25));
        g2d.drawString("" + score, 590, 30);

        // the paddle
        g2d.setColor(Color.green);
        g2d.fillRect(playerX, 550, 100, 8);

        // the ball
        g2d.setColor(Color.yellow);
        g2d.fillOval(ballposX, ballposY, 20, 20);


        // when you won the game
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("serif", Font.BOLD, 30));
            g2d.drawString("You Won", 260, 300);

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("serif", Font.BOLD, 20));
            g2d.drawString("Press (Enter) to Restart", 230, 350);
        }

        // when you lose the game
        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("serif", Font.BOLD, 30));
            g2d.drawString("Game Over, Scores: " + score, 190, 300);

            g2d.setColor(Color.RED);
            g2d.setFont(new Font("serif", Font.BOLD, 20));
            g2d.drawString("Press (Enter) to Restart", 230, 350);
        }

//        g.dispose();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void moveRight() {
        play = true;
        if (playerX <600) {
            playerX += 20;
        }
    }

    public void moveLeft() {
        play = true;
        if (playerX>10) {
            playerX -= 20;
        }
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) {
                ballYdir = -ballYdir;
                ballXdir = -2;
            } else if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;
            } else if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                ballYdir = -ballYdir;
            }

            // check map collision with the ball
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        //scores++;
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            score += 5;
                            totalBricks--;

                            // when ball hit right or left of brick
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            }
                            // when ball hits top or bottom of brick
                            else {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
            if (ballposY>570){
                Graphics2D g= (Graphics2D) getGraphics();
                play=false;
                ballXdir=0;
                ballYdir=0;
                g.setColor(Color.RED);
                g.setFont(new Font("serif", Font.BOLD, 30));
                g.drawString("Game over, scores: " + score, 190,300);

                g.setColor(Color.RED);
                g.setFont(new Font("serif", Font.BOLD, 20));
                g.drawString("press (enter) to restart", 230,350);
            }

            repaint();
        }
    }
}
