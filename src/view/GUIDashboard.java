package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import model.Employee;
import model.User;
import util.UserRole;
import service.EmployeeDAO;
import service.EmployeeService;
import util.SessionManager;
import util.UserRepository;

public class GUIDashboard {

	private JFrame dashboardScreen;
    private static User loggedInEmployee;
    private JLabel employeeNameLabel;
    private JLabel empIDLabel;
    private JLabel empPositionLabel;
    private JLabel immediateSupervisorLabel;
    private JLabel empStatusLabel;
    private JLabel empBdayLabel;
    private JLabel empAddressLabel;
    private JLabel empTINLabel;
    private JLabel empSSSLabel;
    private JLabel empPhilhealthLabel;
    private JLabel empPagibigLabel;
    private JLabel employeeFirstNameLabel;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Retrieve the logged-in employee from the session manager
                    User loggedInEmployee = SessionManager.getLoggedInUser();

                    // Before initializing GUIDashboard, make sure loggedInEmployee is not null
                    if (loggedInEmployee != null) {
                        System.out.println("Logged-in Employee: " + loggedInEmployee);

                        // Initialize GUIDashboard with the logged-in employee
                        GUIDashboard window = new GUIDashboard(loggedInEmployee);
                        window.dashboardScreen.setVisible(true);
                        window.dashboardScreen.setLocationRelativeTo(null);

                        // After initializing GUIDashboard
                        System.out.println("GUIDashboard Initialized");

                        // Call displayEmployeeInformation() to display the logged-in employee's information
                        window.displayEmployeeInformation();
                    } else {
                        System.out.println("No user logged in.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



	/**
	 * Create the application.
	 */
    // Constructor
    public GUIDashboard(User loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
        initialize();
    }

    // Static getter for loggedInEmployee
    public static User getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public JFrame getDashboardScreen() {
        return dashboardScreen;
    }


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dashboardScreen = new JFrame();
		dashboardScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\shane\\eclipse-workspace\\IT110-OOP-MotorPH-Payroll\\Icons\\MotorPH Icon.png"));
		dashboardScreen.setBackground(new Color(255, 255, 255));
		dashboardScreen.setTitle("MotorPH Payroll System");
		dashboardScreen.setBounds(100, 100, 1315, 770);
		dashboardScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dashboardScreen.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 0, 1301, 733);
		dashboardScreen.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setBackground(new Color(255, 255, 255));
		sidebarPanel.setBounds(0, 0, 299, 733);
		mainPanel.add(sidebarPanel);
		sidebarPanel.setLayout(null);
		
		JLabel motorphLabel = new JLabel("MotorPH");
		motorphLabel.setHorizontalAlignment(SwingConstants.CENTER);
		motorphLabel.setForeground(new Color(30, 55, 101));
		motorphLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 28));
		motorphLabel.setBounds(10, 30, 279, 45);
		sidebarPanel.add(motorphLabel);
		
		
		//Sidebar buttons
		JButton dashboardButton = new JButton("Dashboard");
		dashboardButton.setEnabled(false);
		dashboardButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		dashboardButton.setBackground(new Color(255, 255, 255));
		dashboardButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		dashboardButton.setBounds(37, 95, 227, 31);
		sidebarPanel.add(dashboardButton);
		
		JButton timeInOutButton = new JButton("Time In/Out");
		timeInOutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		timeInOutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		timeInOutButton.setBackground(Color.WHITE);
		timeInOutButton.setBounds(37, 154, 227, 31);
		sidebarPanel.add(timeInOutButton);
		
		// Define action listener for the timeInOutButton
		timeInOutButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Open GUITimeInOut with the logged-in employee
		        GUITimeInOut timeInOut = new GUITimeInOut(loggedInEmployee);
		        timeInOut.openWindow();

		        // Close the current dashboard window after
		        if (dashboardScreen != null) {
		            dashboardScreen.dispose();
		        }
		    }
		});
			
		JButton payslipButton = new JButton("Payslip");
		payslipButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        openPayslip(loggedInEmployee);
		        dashboardScreen.dispose(); // Optionally dispose the current window
		    }

		    // Define the openPayslip method here within the ActionListener class
		    private void openPayslip(User loggedInEmployee) {
		        // Create an instance of GUIPayslip with the loggedInEmployee
		        GUIPayslip payslip = new GUIPayslip(loggedInEmployee);

		        // Make the payslip window visible
		        payslip.openWindow();
		    }
		});

		payslipButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		payslipButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		payslipButton.setBackground(Color.WHITE);
		payslipButton.setBounds(37, 216, 227, 31);
		sidebarPanel.add(payslipButton);
		
		JButton leaverequestButton = new JButton("Leave Request");
		leaverequestButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leaverequestButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		leaverequestButton.setBackground(Color.WHITE);
		leaverequestButton.setBounds(37, 277, 227, 31);
		sidebarPanel.add(leaverequestButton);

		leaverequestButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        openLeaveRequest(loggedInEmployee);
		        dashboardScreen.dispose(); // Optionally dispose the current window
		    }

		    // Define the openLeaveRequest method here within the ActionListener class
		    private void openLeaveRequest(User loggedInEmployee) {
		        // Create an instance of GUILeaveRequest with the loggedInEmployee
		        GUILeaveRequest leaveRequest = null;
				try {
					leaveRequest = new GUILeaveRequest(loggedInEmployee);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        // Make the leave request window visible
		        leaveRequest.openWindow();
		    }
		});
						
		JButton helpButton = new JButton("Help & Support");
		helpButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 23));
		helpButton.setBackground(Color.WHITE);
		helpButton.setBounds(37, 669, 227, 31);
		sidebarPanel.add(helpButton);
		
		JButton HR_EmpMngmntButton = new JButton("Employee management");
		HR_EmpMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_EmpMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_EmpMngmntButton.setBackground(Color.WHITE);
		HR_EmpMngmntButton.setBounds(37, 383, 227, 31);
		sidebarPanel.add(HR_EmpMngmntButton);

		// Add action listener to HR_EmpMngmntButton
		HR_EmpMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
					openEmployeeManagement();
					dashboardScreen.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }

		    // Define the openEmployeeManagement method
		    private void openEmployeeManagement() throws IOException {
		        // Create an instance of GUI_HREmployeeManagement
		        GUI_HREmployeeManagement employeeManagement = new GUI_HREmployeeManagement(null);

		        // Make the employee management window visible
		        employeeManagement.setVisible(true);
		    }
		});
		
		//hrattendancemngmnt
		JButton HR_AttendanceMngmntButton = new JButton("Attendance management");
		HR_AttendanceMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_AttendanceMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_AttendanceMngmntButton.setBackground(Color.WHITE);
		HR_AttendanceMngmntButton.setBounds(37, 438, 227, 31);
		sidebarPanel.add(HR_AttendanceMngmntButton);
		HR_AttendanceMngmntButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	GUI_HRAttendanceManagement window = new GUI_HRAttendanceManagement();
				window.hrattendancemngmnt.setVisible(true);
		    }
		});
		
		
		
		JButton HR_LeaveMngmntButton = new JButton("LeaveBalance management");
		HR_LeaveMngmntButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		HR_LeaveMngmntButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		HR_LeaveMngmntButton.setBackground(Color.WHITE);
		HR_LeaveMngmntButton.setBounds(37, 491, 227, 31);
		sidebarPanel.add(HR_LeaveMngmntButton);
		
		JButton Payroll_SalaryCalculationButton = new JButton("Salary Calculation");
		Payroll_SalaryCalculationButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 19));
		Payroll_SalaryCalculationButton.setBackground(Color.WHITE);
		Payroll_SalaryCalculationButton.setBounds(37, 383, 227, 31);
		sidebarPanel.add(Payroll_SalaryCalculationButton);
		
		JButton Payroll_MonthlyReportsButton = new JButton("Monthly Summary Reports");
		Payroll_MonthlyReportsButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 16));
		Payroll_MonthlyReportsButton.setBackground(Color.WHITE);
		Payroll_MonthlyReportsButton.setBounds(37, 438, 227, 31);
		sidebarPanel.add(Payroll_MonthlyReportsButton);
		
		// Check the user role
		UserRole userRole = loggedInEmployee != null ? loggedInEmployee.getRole() : null;

		// Check the user role and set button visibility accordingly
		if (userRole == UserRole.HR) {
		    // If the user is HR, make the HR management buttons visible
		    HR_EmpMngmntButton.setVisible(true);
		    HR_AttendanceMngmntButton.setVisible(true);
		    HR_LeaveMngmntButton.setVisible(true);
		    // Hide payroll buttons
		    Payroll_SalaryCalculationButton.setVisible(false);
		    Payroll_MonthlyReportsButton.setVisible(false);
		} else if (userRole == UserRole.PAYROLL) {
		    // If the user is in the payroll department, make the payroll buttons visible
		    Payroll_SalaryCalculationButton.setVisible(true);
		    Payroll_MonthlyReportsButton.setVisible(true);
		    // Hide HR management buttons
		    HR_EmpMngmntButton.setVisible(false);
		    HR_AttendanceMngmntButton.setVisible(false);
		    HR_LeaveMngmntButton.setVisible(false);
		} else {
		    // If the user is not in HR or payroll, hide all management buttons
		    HR_EmpMngmntButton.setVisible(false);
		    HR_AttendanceMngmntButton.setVisible(false);
		    HR_LeaveMngmntButton.setVisible(false);
		    Payroll_SalaryCalculationButton.setVisible(false);
		    Payroll_MonthlyReportsButton.setVisible(false);
		}

		JLabel dashboardLabel = new JLabel("Dashboard");
		dashboardLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 32));
		dashboardLabel.setBounds(340, 36, 205, 33);
		mainPanel.add(dashboardLabel);
		
		JLabel welcomeLabel = new JLabel("Welcome, ");
		welcomeLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		welcomeLabel.setBounds(340, 103, 177, 33);
		mainPanel.add(welcomeLabel);
		
		JPanel employeeinfoPanel = new JPanel();
		employeeinfoPanel.setBackground(new Color(255, 255, 255));
		employeeinfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		employeeinfoPanel.setBounds(340, 146, 923, 242);
		mainPanel.add(employeeinfoPanel);
		employeeinfoPanel.setLayout(null);
		
		JLabel employeeIcon = new JLabel("");
		employeeIcon.setIcon(new ImageIcon("E:\\Downloads\\Documents\\shaneabrasaldo-IT110-OOP-MotorPH-Payroll\\Icons\\Employee Icon.png"));
		employeeIcon.setHorizontalAlignment(SwingConstants.CENTER);
		employeeIcon.setBounds(-21, 10, 204, 222);
		employeeinfoPanel.add(employeeIcon);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		nameLabel.setBounds(158, 32, 45, 13);
		employeeinfoPanel.add(nameLabel);
		
		JLabel idLabel = new JLabel("Employee ID");
		idLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		idLabel.setBounds(158, 77, 98, 13);
		employeeinfoPanel.add(idLabel);
		
		JLabel positionLabel = new JLabel("Position");
		positionLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		positionLabel.setBounds(158, 122, 69, 13);
		employeeinfoPanel.add(positionLabel);

		JLabel supervisorLabel = new JLabel("Immediate Supervisor");
		supervisorLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		supervisorLabel.setBounds(158, 168, 171, 13);
		employeeinfoPanel.add(supervisorLabel);
		
		JLabel statusLabel = new JLabel("Status");
		statusLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		statusLabel.setBounds(407, 32, 98, 13);
		employeeinfoPanel.add(statusLabel);
		
		JLabel bdayLabel = new JLabel("Birthday");
		bdayLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		bdayLabel.setBounds(407, 77, 98, 13);
		employeeinfoPanel.add(bdayLabel);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		addressLabel.setBounds(407, 168, 98, 13);
		employeeinfoPanel.add(addressLabel);
		
		JLabel tinLabel = new JLabel("TIN");
		tinLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		tinLabel.setBounds(407, 122, 98, 13);
		employeeinfoPanel.add(tinLabel);
		
		JLabel sssLabel = new JLabel("SSS");
		sssLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		sssLabel.setBounds(615, 32, 98, 13);
		employeeinfoPanel.add(sssLabel);
		
		JLabel philhealthLabel = new JLabel("Philhealth");
		philhealthLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		philhealthLabel.setBounds(615, 77, 98, 13);
		employeeinfoPanel.add(philhealthLabel);
		
		JLabel pagibigLabel = new JLabel("Pag-ibig");
		pagibigLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 15));
		pagibigLabel.setBounds(615, 122, 98, 13);
		employeeinfoPanel.add(pagibigLabel);
		
		JPanel announcementsPanel = new JPanel();
		announcementsPanel.setBackground(new Color(30, 55, 101));
		announcementsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		announcementsPanel.setBounds(340, 419, 923, 281);
		mainPanel.add(announcementsPanel);
		announcementsPanel.setLayout(null);
		
		JLabel announcementsLabel = new JLabel("Company Announcements");
		announcementsLabel.setForeground(new Color(255, 255, 255));
		announcementsLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 45));
		announcementsLabel.setBounds(25, 26, 531, 40);
		announcementsPanel.add(announcementsLabel);
		
		JPanel mismongAnnouncementPanel = new JPanel();
		mismongAnnouncementPanel.setBounds(25, 86, 874, 169);
		announcementsPanel.add(mismongAnnouncementPanel);
		mismongAnnouncementPanel.setLayout(null);
		
		JLabel announcementText = new JLabel("Company-wide meeting tomorrow at 10 AM in the conference room. See you there! ");
		announcementText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		announcementText.setHorizontalAlignment(SwingConstants.CENTER);
		announcementText.setBounds(21, 71, 843, 47);
		mismongAnnouncementPanel.add(announcementText);
		
		JLabel reminderText = new JLabel("Reminder:");
		reminderText.setHorizontalAlignment(SwingConstants.CENTER);
		reminderText.setFont(new Font("Tahoma", Font.BOLD, 25));
		reminderText.setBounds(10, 40, 854, 47);
		mismongAnnouncementPanel.add(reminderText);
		
		JLabel teamheadText = new JLabel("- HR Team Head");
		teamheadText.setFont(new Font("Tahoma", Font.BOLD, 15));
		teamheadText.setBounds(692, 128, 125, 13);
		mismongAnnouncementPanel.add(teamheadText);
		
		JButton signoutButton = new JButton("Sign Out");
		signoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIlogin login = new GUIlogin();
				login.loginScreen1.setVisible(true);
				dashboardScreen.dispose();
			}
		});
		signoutButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 18));
		signoutButton.setBackground(Color.WHITE);
		signoutButton.setBounds(1160, 36, 103, 31);
		mainPanel.add(signoutButton);
		
		// Initialize JLabels with empty strings
	    employeeFirstNameLabel = new JLabel("");
	    employeeFirstNameLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
	    employeeFirstNameLabel.setBounds(512, 103, 323, 33);
	    mainPanel.add(employeeFirstNameLabel);

	    employeeNameLabel = new JLabel("");
		employeeNameLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		employeeNameLabel.setBounds(158, 44, 228, 23);
		employeeinfoPanel.add(employeeNameLabel);

		empIDLabel = new JLabel("");
		empIDLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empIDLabel.setBounds(158, 89, 119, 23);
		employeeinfoPanel.add(empIDLabel);

		empPositionLabel = new JLabel("");
		empPositionLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empPositionLabel.setBounds(158, 134, 228, 23);
		employeeinfoPanel.add(empPositionLabel);

		immediateSupervisorLabel = new JLabel("");
		immediateSupervisorLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		immediateSupervisorLabel.setBounds(158, 180, 228, 23);
		employeeinfoPanel.add(immediateSupervisorLabel);

		empStatusLabel = new JLabel("");
		empStatusLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empStatusLabel.setBounds(407, 44, 113, 23);
		employeeinfoPanel.add(empStatusLabel);

		empBdayLabel = new JLabel("");
		empBdayLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empBdayLabel.setBounds(407, 89, 160, 23);
		employeeinfoPanel.add(empBdayLabel);

		empAddressLabel = new JLabel("");
		empAddressLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empAddressLabel.setBounds(407, 180, 506, 23);
		employeeinfoPanel.add(empAddressLabel);

		empTINLabel = new JLabel("");
		empTINLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empTINLabel.setBounds(615, 44, 171, 23);
		employeeinfoPanel.add(empTINLabel);

		empSSSLabel = new JLabel("");
		empSSSLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empSSSLabel.setBounds(407, 134, 171, 23);
		employeeinfoPanel.add(empSSSLabel);

		empPhilhealthLabel = new JLabel("");
		empPhilhealthLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empPhilhealthLabel.setBounds(615, 89, 171, 23);
		employeeinfoPanel.add(empPhilhealthLabel);

		empPagibigLabel = new JLabel("");
		empPagibigLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		empPagibigLabel.setBounds(615, 134, 171, 23);
		employeeinfoPanel.add(empPagibigLabel);

		// Set employee info
		if (loggedInEmployee != null) {
		    // Assuming you have an instance of EmployeeService and EmployeeDAO
			UserRepository userRepository = new UserRepository();
			EmployeeService employeeService = new EmployeeService(userRepository, loggedInEmployee);

		    // Retrieve the employee by ID using the service
		    Employee employee = employeeService.getEmployeeById(loggedInEmployee.getId());

		    // Convert the int value to String before setting the text
		    empIDLabel.setText(String.valueOf(employee.getEmpId()));
		}
		
		// Call displayEmployeeInformation after initializing the labels
	    displayEmployeeInformation();


	}
	
	public void displayEmployeeInformation() {
	    // Check if a user is logged in
	    if (loggedInEmployee != null) {
	        // Create an instance of EmployeeService using EmployeeDAO
	        UserRepository userRepository = new UserRepository();
	        EmployeeService employeeService = new EmployeeService(userRepository, loggedInEmployee);

	        // Retrieve the employee's information from the database using their ID
	        Employee employee = employeeService.getLoggedInEmployee();

	        // Check if employee is found
	        if (employee != null) {
	            // Display employee information in the GUI
	            employeeFirstNameLabel.setText(employee.getFirstName());
	            employeeNameLabel.setText(employee.getFirstName() + " " + employee.getLastName());
	            empIDLabel.setText(String.valueOf(employee.getEmpId()));
	            empPositionLabel.setText(employee.getPosition());
	            immediateSupervisorLabel.setText(employee.getImmediateSupervisor());
	            empStatusLabel.setText(employee.getStatus());
	            empBdayLabel.setText(employee.getBirthday());
	            empAddressLabel.setText(employee.getAddress());
	            empTINLabel.setText(employee.getTinNumber());
	            empSSSLabel.setText(employee.getSssNumber());
	            empPhilhealthLabel.setText(employee.getPhilhealthNumber());
	            empPagibigLabel.setText(employee.getPagibigNumber());

	            // No need for pop-up message if employee information is successfully displayed
	        } else {
	            // Handle case where employee is not found
	            JOptionPane.showMessageDialog(dashboardScreen, "Employee not found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        // Handle case where no user is logged in
	        JOptionPane.showMessageDialog(dashboardScreen, "No user logged in.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    
	}