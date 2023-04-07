import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProductTableGUI extends JFrame implements ActionListener {
   // Initialize components
   private JLabel userLabel, passLabel;
   private JTextField userField;
   private JPasswordField passField;
   private JButton loginButton, createTableButton;

   // Initialize database variables
   private Connection con;
   private Statement stmt;
   private ResultSet rs;

   // Constructor
   public ProductTableGUI() {
      // Set window properties
      setTitle("Product Table");
      setSize(400, 200);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      // Initialize components
      userLabel = new JLabel("Username:");
      passLabel = new JLabel("Password:");
      userField = new JTextField(20);
      passField = new JPasswordField(20);
      loginButton = new JButton("Login");
      createTableButton = new JButton("Create Product Table");

      // Add components to window
      JPanel panel = new JPanel(new GridLayout(3, 2));
      panel.add(userLabel);
      panel.add(userField);
      panel.add(passLabel);
      panel.add(passField);
      panel.add(loginButton);
      panel.add(createTableButton);
      add(panel);

      // Add action listeners
      loginButton.addActionListener(this);
      createTableButton.addActionListener(this);
   }

   // ActionListener implementation
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == loginButton) {
         // Check if login details are correct
         String username = userField.getText();
         String password = new String(passField.getPassword());

         if (username.equals("admin") && password.equals("password")) {
            // Show success message and enable create table button
            JOptionPane.showMessageDialog(this, "Login successful!");
            createTableButton.setEnabled(true);

            // Connect to database
            try {
               Class.forName("com.mysql.jdbc.Driver");
               con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "wasted9237");
               
               // Create "productdb" database if not exists
               stmt = con.createStatement();
               String sql = "CREATE DATABASE IF NOT EXISTS productdb";
               stmt.executeUpdate(sql);
               
               // Switch to "productdb" database
               sql = "USE productdb";
               stmt.executeUpdate(sql);
            } catch (Exception ex) {
               JOptionPane.showMessageDialog(this, "Error connecting to database.");
               ex.printStackTrace();
            }
         } else {
            // Show error message
            JOptionPane.showMessageDialog(this, "Incorrect username or password.");
         }
      } else if (e.getSource() == createTableButton) {
         // Create product table in database
         try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE products (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(50), price DOUBLE, PRIMARY KEY (id))";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Product table created successfully.");
         } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating product table.");
            ex.printStackTrace();
         }
      }
   }

   // Main method
   public static void main(String[] args) {
      ProductTableGUI gui = new ProductTableGUI();
      gui.setVisible(true);
      gui.createTableButton.setEnabled(false);
   }
}
