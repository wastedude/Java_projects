import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class PharmacyManagementSystem extends JFrame implements ActionListener {

    // GUI components
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton, manageAccountsButton, updateStockButton, dispenseMedicineButton;
    private JTextArea outputArea;

    // Database connection variables
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public PharmacyManagementSystem() {

        // Set up the main window
        super("Pharmacy Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create the login form
        usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField(20);
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);

        // Create the admin features
        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(this);
        add(createAccountButton);

        manageAccountsButton = new JButton("Manage Accounts");
        manageAccountsButton.addActionListener(this);
        add(manageAccountsButton);

        updateStockButton = new JButton("Update Stock");
        updateStockButton.addActionListener(this);
        add(updateStockButton);

        // Create the user features
        dispenseMedicineButton = new JButton("Dispense Medicine");
        dispenseMedicineButton.addActionListener(this);
        add(dispenseMedicineButton);

        outputArea = new JTextArea(10, 40);
        add(outputArea);

        // Initialize the database connection
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy_management_system", "root",
                    "wasted9237");
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find database driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + e.getMessage());
        }

    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == loginButton) {
            // Check the login credentials
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                resultSet = statement.executeQuery(
                        "SELECT * FROM accounts WHERE username='" + username + "' AND password='" + password + "'");
                if (resultSet.next()) {
                    // Show the appropriate features based on the account type
                    String accountType = resultSet.getString("account_type");
                    if (accountType.equals("admin")) {
                        createAccountButton.setVisible(true);
                        manageAccountsButton.setVisible(true);
                        updateStockButton.setVisible(true);
                        dispenseMedicineButton.setVisible(false);
                    } else if (accountType.equals("user")) {
                        createAccountButton.setVisible(false);
                        manageAccountsButton.setVisible(false);
                        updateStockButton.setVisible(false);
                        dispenseMedicineButton.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login credentials");
                }
            } catch (SQLException e) {
                System.out.println("Could not execute login query: " + e.getMessage());
            }
        } else if (event.getSource() == createAccountButton) {
            // Create a new user account
            String newUsername = JOptionPane.showInputDialog(this, "Enter the new username:");
            String newPassword = JOptionPane.showInputDialog(this, "Enter the new password:");
            String newAccountType = JOptionPane.showInputDialog(this, "Enter the new account type (admin or user):");
            try {
                statement.executeUpdate("INSERT INTO accounts (username, password, account_type) VALUES ('"
                        + newUsername + "', '" + newPassword + "', '" + newAccountType + "')");
                JOptionPane.showMessageDialog(this, "New account created successfully");
            } catch (SQLException e) {
                System.out.println("Could not execute create account query: " + e.getMessage());
            }
        } else if (event.getSource() == manageAccountsButton) {
            // Manage existing user accounts
            try {
                resultSet = statement.executeQuery("SELECT * FROM accounts");
                outputArea.setText("Username\tPassword\tAccount Type\n");
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String accountType = resultSet.getString("account_type");
                    outputArea.append(username + "\t" + password + "\t" + accountType + "\n");
                }
            } catch (SQLException e) {
                System.out.println("Could not execute manage accounts query: " + e.getMessage());
            }
        } else if (event.getSource() == updateStockButton) {
            // Update the stock levels of a medicine
            String medicineName = JOptionPane.showInputDialog(this, "Enter the name of the medicine:");
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the new quantity:"));
            try {
                statement.executeUpdate(
                        "UPDATE medicines SET quantity=" + quantity + " WHERE name='" + medicineName + "'");
                JOptionPane.showMessageDialog(this, "Stock levels updated successfully");
            } catch (SQLException e) {
                System.out.println("Could not execute update stock query: " + e.getMessage());
            }
        } else if (event.getSource() == dispenseMedicineButton) {
            // Dispense a medicine to a patient and generate an invoice
            String patientName = JOptionPane.showInputDialog(this, "Enter the name of the patient:");
            String medicineName = JOptionPane.showInputDialog(this, "Enter the name of the medicine:");
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the quantity dispensed:"));
            try {
                resultSet = statement.executeQuery("SELECT * FROM medicines WHERE name='" + medicineName + "'");
                if (resultSet.next()) {
                    int stockLevel = resultSet.getInt("quantity");
                    if (stockLevel >= quantity) {
                        // Update the stock levels
                        int newStockLevel = stockLevel - quantity;
                        statement.executeUpdate("UPDATE medicines SET quantity=" + newStockLevel + " WHERE name='"
                                + medicineName + "'");
                        // Generate an invoice
                        double price = resultSet.getDouble("price");
                        double totalPrice = price * quantity;
                        outputArea.setText(
                                "Patient Name: " + patientName + "\nMedicine Name: " + medicineName + "\nQuantity: "
                                        + quantity + "\nPrice per unit: " + price + "\nTotal Price: " + totalPrice);
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient stock for " + medicineName);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Medicine not found: " + medicineName);
                }
            } catch (SQLException e) {
                System.out.println("Could not execute dispense medicine query: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        PharmacyManagementSystem system = new PharmacyManagementSystem();
        system.setVisible(true);
    }
}