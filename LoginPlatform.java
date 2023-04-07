import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPlatform extends JFrame {
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField, passwordField;
    private JButton loginButton, clearButton, exitButton;
    
    public LoginPlatform() {
        // set up the frame
        setTitle("Login Platform");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // create the components
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        
        loginButton = new JButton("Login");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");
        
        // add anonymous class action listeners to the buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                
                // check if the username and password are correct
                if (username.equals("admin") && password.equals("password")) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password.");
                }
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        // add the components to the frame
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(clearButton);
        panel.add(exitButton);
        add(panel);
        
        // show the frame
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new LoginPlatform();
    }
}
