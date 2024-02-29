import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import model.Employee;
import model.User;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import service.LoginService;
import util.EmployeeData;
import util.SessionManager;
import util.UserRepository;
import view.Dashboard;
import java.awt.Cursor;

public class GUIlogin {

    JFrame loginScreen1;
    private JTextField usernameTextField1;
    private LoginService loginService;
    private SessionManager sessionManager;
    private UserRepository userRepository;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUIlogin window = new GUIlogin();
                    window.loginScreen1.setVisible(true);
                    window.loginScreen1.setLocationRelativeTo(null); // this method displays the login screen at the center of the screen. slay!
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUIlogin() {
        initialize();
        // Initialize the LoginService, UserRepository, and SessionManager
        UserRepository userRepository = new UserRepository();
        EmployeeData employeeData = new EmployeeData();
        sessionManager = new SessionManager(userRepository, employeeData);
    }

    //Initialize the contents of the frame. GUI starts here.

    private <usernameTextField1> void initialize() {
        loginScreen1 = new JFrame();
        loginScreen1.setBackground(new Color(255, 255, 255));
        loginScreen1.setTitle("MotorPH Payroll System");
        loginScreen1.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
        loginScreen1.getContentPane().setBackground(new Color(255, 255, 255));
        loginScreen1.setBounds(100, 100, 1315, 770);
        loginScreen1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginScreen1.getContentPane().setLayout(null);
        
        JLabel motorphLabel = new JLabel("MotorPH");
        motorphLabel.setForeground(new Color(30, 55, 101));
        motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
        motorphLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 60));
        motorphLabel.setBounds(524, 138, 250, 54);
        loginScreen1.getContentPane().add(motorphLabel);
        
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 28));
        welcomeLabel.setBounds(534, 202, 240, 33);
        loginScreen1.getContentPane().add(welcomeLabel);
        
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
        usernameLabel.setBounds(452, 281, 107, 33);
        loginScreen1.getContentPane().add(usernameLabel);
        
        usernameTextField1 = new JTextField();
        usernameTextField1.setForeground(new Color(30, 55, 101));
        usernameTextField1.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
        usernameTextField1.setBounds(452, 313, 397, 42);
        loginScreen1.getContentPane().add(usernameTextField1);
        usernameTextField1.setColumns(10);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
        passwordLabel.setBounds(452, 385, 107, 33);
        loginScreen1.getContentPane().add(passwordLabel);
        
        JButton loginButton = new JButton("Log In");
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setForeground(new Color(30, 55, 101));
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
        loginButton.setBounds(452, 562, 397, 51);
        loginScreen1.getContentPane().add(loginButton);
        
        JLabel blobLeft = new JLabel("");
        blobLeft.setIcon(new ImageIcon("E:\\Downloads\\Documents\\shaneabrasaldo-IT110-OOP-MotorPH-Payroll\\Icons\\Login_blobLeft.png"));
        blobLeft.setBounds(-231, -17, 602, 446);
        loginScreen1.getContentPane().add(blobLeft);
        
        JLabel blobRight = new JLabel("");
        blobRight.setIcon(new ImageIcon("E:\\Downloads\\Documents\\shaneabrasaldo-IT110-OOP-MotorPH-Payroll\\Icons\\Login_blobRight.png"));
        blobRight.setBounds(1018, 289, 397, 516);
        loginScreen1.getContentPane().add(blobRight);
        
        JLabel motorphIcon = new JLabel("");
        motorphIcon.setHorizontalAlignment(SwingConstants.CENTER);
        motorphIcon.setIcon(new ImageIcon("E:\\Downloads\\Documents\\shaneabrasaldo-IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Logo.png"));
        motorphIcon.setBounds(526, 42, 232, 95);
        loginScreen1.getContentPane().add(motorphIcon);
        

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
        passwordField.setBounds(452, 416, 397, 42);
        loginScreen1.getContentPane().add(passwordField);

     // ActionListener for Log In button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField1.getText();
                String password = new String(passwordField.getPassword()); 

                // Check login credentials
                boolean loginSuccess = false;
        		try {
        			loginSuccess = sessionManager.login(username, password);
        		} catch (IOException e1) {
        			// Handle IOException
        			e1.printStackTrace();
        		}
                
                if (loginSuccess) {
                    // Log successful login attempt
                    sessionManager.logLoginAttempt(username, true);
                    
                    // Retrieve the logged-in employee information
                    User loggedInEmployee = sessionManager.getLoggedInUser(); // Assuming such method exists
                    
                    // Open the Dashboard with the logged-in employee
                    openDashboard(loggedInEmployee);
                } else {
                    // Log unsuccessful login attempt
                    sessionManager.logLoginAttempt(username, false);
                    // Show error message
                    JOptionPane.showMessageDialog(loginScreen1,
                            "Invalid username or password. Please try again.");
                }
            }
        });
    }

    
    private void openDashboard(User loggedInEmployee) {
        // Create an instance of GUIDashboard with the logged-in employee
        GUIDashboard dashboard = new GUIDashboard(loggedInEmployee);

        // Make the dashboard window visible
        dashboard.getDashboardScreen().setVisible(true);

        // Close the Login window
        loginScreen1.dispose();
    }

    

}
