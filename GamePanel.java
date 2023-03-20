import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

// THIS ENTIRE CLASS IS USED TO RUN THE GAME PANEL
public class GamePanel extends JPanel implements ActionListener {

    // ALL OF THE VARIABLES USED FOR THE GAME 
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE*UNIT_SIZE;
    static int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int snekParts = 6;
    static int papplesEaten;
    int pappleX;
    int pappleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    JFrame frame;

    // THREE GETTERS TO PASS INFORMATION NEEDED IN GAMEFRAME 
    public int getWidth() {
        return SCREEN_WIDTH;
    }

    public int getHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getPapplesEaten() {
        return papplesEaten;
    }
    
    // THE GAMEPANEL CONSTRUCTOR 
    GamePanel(JFrame f) {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.frame = f;
        startGame();
    }

    // THIS STARTS THE GAME, ADDS A NEW PINEAPPLE ON THE SCREEN, STARTS THE TIMER 
    public void startGame() {
        newPapple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    // THIS IMPLEMENTS THE PAINT COMPONENT SO THAT GRAPHICS CAN BE IMPLEMENTED
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // THE DRAW METHOD FOR THE SNEK, THE PINEAPPLE, AND THE SCORE 
    public void draw(Graphics g) {
        // WHILE RUNNING IS TRUE
        if(running) {
            // CREATE A GRID
            for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            // CREATE THE PINEAPPLE
            g.setColor(Color.yellow);
            g.fillOval(pappleX, pappleY, UNIT_SIZE, UNIT_SIZE);

            // CREATE THE SNEK
            for(int i = 0; i < snekParts; i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // CREATE THE TEXT FOR THE SCORE 
            g.setColor(Color.cyan);
            g.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE:" + papplesEaten, (SCREEN_WIDTH - metrics.stringWidth("SCORE:" + papplesEaten))/2, g.getFont().getSize());
        } 
        // WHEN RUNNING IS FALSE 
        else {
            // SENDS THE ITEMS TO THE GAMEFRAME FOR THE GAMEOVER SCREEN 
            new GameFrame().GameOver(frame, papplesEaten, g);
            // SETS THE SCORE TO ZERO 
            papplesEaten = 0;
        }
    }

    // THIS METHOD CREATES A NEW PINEAPPLE RANDOMLY AROUND THE SCREEN WEHN THE GAME STARTS AND WHEN IT'S CALLED 
    public void newPapple() {
        pappleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        pappleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    // THIS METHOD MOVES THE SNEK PARTS AROUND 
    public void move() {
        for(int i = snekParts; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        // THIS CHECKS THE DIRECTION OF WHERE THE SNEK IS GOING 
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    // THIS CHECKS IF THE SNEK HAS EATEN A PINEAPPLE AND IF SO, CREATES A NEW ONE 
    // THIS ALSO COUNTS THE SCORE AND ADDS ONTO THE SNEK 
    public void checkPapple() {
        if((x[0] == pappleX) && (y[0] == pappleY)) {
            snekParts++;
            papplesEaten++;
            playSound("nomnom.wav");
            newPapple();
        }
    }

    // THIS CODE IS FOR THE SOUNDS FILES
    // FOUND HERE: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
        public void run() {
            try {
                Clip clip = AudioSystem.getClip();
                File audioFile = new File(url);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);
                clip.open(inputStream);
                clip.start(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }).start();
    }

    // THIS METHOD CHECKS IF THE SNEK HAS HIT INTO ITSELF OR A BORDER 
    public void checkCollide() {
        // this checks when the snek hits into itself
        for(int i = snekParts; i > 0; i--) {
            if((x[0] == x[i] && y[0] == y[i])) {
                playSound("ded.wav");
                running = false;
            }
        }

        // CHECKS IF THE SNEK COLLIDES WITH THE LEFT BORDER 
        if(x[0] < 0) {
            playSound("ded.wav");
            running = false;
        }
        // CHECKS IF THE SNEK COLLIDES WITH THE RIGHT BORDER 
        if(x[0] > SCREEN_WIDTH - UNIT_SIZE) {
            playSound("ded.wav");
            running = false;
        }
        // CHECKS IF THE SNEK COLLIDES WITH THE TOP BORDER 
        if(y[0] < 0) {
            playSound("ded.wav");
            running = false;
        }
        // CHECKS IF THE SNEK COLLIDES WITH THE BOTTOM BORDER 
        if(y[0] > SCREEN_HEIGHT - UNIT_SIZE) {
            playSound("ded.wav");
            running = false;
        }

        // WILL STOP THE GAME IF FALSE 
        if(!running) {
            timer.stop();
        }
    }

    // THIS CALLED THE THREE MOST IMPORTANT METHODS AND BASICALLY KEEPS THE GAME RUNNING 
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkPapple();
            checkCollide();
        }
        repaint();
    }

    // THIS CLASS AND METHOD MAKES IT SO YOU CAN USE THE ARROW KEYS TO MOVE THE SNEK
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                // THE FOLLOWING SWITCH STATEMENTS NOT ONLY MAKE IT SO
                // YOU CAN MOVE THE SNEK BY USING THE ARROW KEYS, BUT THEY
                // ALSO LIMIT THE PLAYER TO ONLY 90 DEGREE TURNS, SO AS TO NOT
                // HIT INTO THEMSELVES BY GOING BACK
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
