
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setSize(300, 200);

        JPanel loginPanel = new JPanel(new BorderLayout(10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 25));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("rina") && password.equals("rinacutie")) {
                    dispose();
                    displayUserGuideline();
                    startGame();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password. Please try again.");
                }
            }
        });

        GroupLayout layout = new GroupLayout(loginPanel);
        loginPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(usernameLabel)
                        .addComponent(passwordLabel)
                        .addComponent(usernameField)
                        .addComponent(passwordField)
                        .addComponent(loginButton, GroupLayout.Alignment.CENTER)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(usernameLabel)
                        .addComponent(usernameField)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(loginButton)
        );

        add(loginPanel);
        setVisible(true);
    }



    private void displayUserGuideline() {
        JOptionPane.showMessageDialog(this, "How to play\n\n" +
                "Use the left and right arrow keys to move the paddle. " +
                "The ball will bounce off the paddle and the top and bottom walls of the screen. \n" +
                "When the ball hits a brick, the brick will break and the ball will bounce off in a different direction. The goal of the game is to break all of the bricks.\n\n" +
                "Tips\n\n" +
                "Try to keep the ball in the center of the paddle. This will give you the most control over where the ball goes.\n " +
                "If you're having trouble hitting a brick, try moving the paddle closer to the brick before you hit it.\n" +
                "If you're about to lose the ball, try to hit it with the side of the paddle. This will give you a better chance of keeping the ball in play.\n\n" +
                "Controls\n" +
                "Left arrow key: Move the paddle left. \n" +
                "Right arrow key: Move the paddle right.\n" +
                "Enter key: Restart the game.\n\n" );
    }

    private void startGame() {
        JFrame obj = new JFrame();
        Gameplay gamePlay = new Gameplay();

        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);

        obj.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}
