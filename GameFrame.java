import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// THIS IS USED FOR THE MENU FRAME, GAME FRAME, AND THE GAME OVER FRAME
public class GameFrame extends JFrame {

    // EMPTY CONSTRUCTOR
    GameFrame() {
    }

    // THIS IS THE MENU METHOD 
    public void GameMenu() {
        // MAKES IT SO IT CAN SWITCH BETWEEN FRAMES
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // CREATION AND STYLE OF ROOT PANEL
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(Color.black);

        // CREATION OF THE TITLE TEXT
        JLabel menuText = new JLabel("SNEK & PAPPLE");
        menuText.setFont(new Font("Serif", Font.BOLD, 50));
        menuText.setForeground(Color.cyan);
        root.add(menuText);

        // CREATION OF THE START GAME BUTTON
        JButton startGame = new JButton("Start Game");
        startGame.setForeground(Color.black);
        startGame.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
        startGame.setBackground(Color.yellow);
        // this action listener calls the GameFrame constructor that will call the GamePanel class
        startGame.addActionListener(e -> {
            new GameFrame(this);
        });
        root.add(startGame);

        // CREATION OF THE INSTRUCTIONS TEXT
        JLabel instructions = new JLabel();
        // SUPER COOL WAY TO MAKE THE TEXT WRAP (THANKS ALEX!)
        instructions.setText("<html>"
            + "Welcome to Snek & Papple! " 
            + "How to play: use the arrow keys to control where the snek moves"
            + " and try and eat the pineapple. Have fun!" + "<html>");
        instructions.setForeground(Color.cyan);
        instructions.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 20));
        root.add(instructions);

        // STATEMENTS TO MAKE THE WINDOW LOOK NICE AND POP UP
        this.add(root);
        this.setPreferredSize(new Dimension(600, 600));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // CONSTRUCTOR, WILL CALL THE GAMEPANEL SO YOU CAN PLAY
    GameFrame(JFrame f) {
        this.add(new GamePanel(this));
        this.setTitle("Snek & Papple");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        // MAKES IT SO YOU CAN HOP FROM ONE FRAME TO ANOTHER WITHOUT IT LOOKING BAD
        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    }

    // WILL SHOW THE GAME OVER SCREEN WHEN THE SNEK HITS INTO ITSELF OR A BORDER 
    public void GameOver(JFrame f, int score, Graphics g) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // CREATION OF THE MAIN ROOT PANEL
        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(Color.black);

        // CREATION OF THE GAME OVER TEXT
        JLabel gameOver = new JLabel("GAME OVER");
        gameOver.setFont(new Font("Serif", Font.BOLD, 75));
        gameOver.setForeground(Color.cyan);
        root.add(gameOver);

        // CREATION OF THE SCORE TEXT SO YOU CAN SEE YOUR FINAL SCORE
        JLabel scoreText = new JLabel("Final Score: " + score);
        scoreText.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
        scoreText.setForeground(Color.cyan);
        root.add(scoreText);

        // THIS BUTTON WILL RESTART THE GAME IF YOU CLICK IT
        JButton restartGame = new JButton();
        restartGame.setText("Restart?");
        restartGame.setForeground(Color.black);
        restartGame.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
        restartGame.setBackground(Color.yellow);
        // THIS ACTION LISTENER CALLS THE GAMEFRAME CONSTRUCTOR SO YOU CAN KEEP PLAYING
        restartGame.addActionListener(e -> {
            new GameFrame(this);
        });
        root.add(restartGame);

        // I TRIED TO CREATE A BUTTON SO YOU CAN HEAD BACK TO THE MENU
        // BUT I COULDN'T GET IT TO NOT OVERLAP WITH THE GAME OVER SCREEN
        /*JButton returnMenu = new JButton();
        returnMenu.setText("Return to Menu?");
        returnMenu.setForeground(Color.black);
        returnMenu.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 30));
        returnMenu.setBackground(Color.yellow);
        returnMenu.addActionListener(e -> {
            callMenu(this);
        });
        root.add(returnMenu);*/

        // ALL THE IMPORTANT STATEMENTS SO YOU CAN SEE THE SCREEN
        this.add(root);
        this.setPreferredSize(new Dimension(600, 600));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    }
}
