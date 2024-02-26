package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Leave;

public class LeaveData {

    // Read Leave data from a CSV file and return a list of leaves
    public static List<Leave> readLeaveCsv() throws IOException {
        List<Leave> leaves = new ArrayList<>();
        String filename = "src/data/Leave Balance.csv";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line; // Variable to store each line read from the CSV

        // Skip the header line
        reader.readLine();

        // Read each line from the CSV and create a Leave object
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String id = fields[0];
            String lastName = fields[1];
            String firstName = fields[2];
            int sickLeaveDays = Integer.parseInt(fields[3]);
            int vacationLeaveDays = Integer.parseInt(fields[4]);
            int emergencyLeaveDays = Integer.parseInt(fields[5]);

            // Create a Leave object and add it to the list
            Leave leave = new Leave(id, lastName, firstName, sickLeaveDays, emergencyLeaveDays, vacationLeaveDays);
            leaves.add(leave);
        }

        reader.close(); // Close the reader
        return leaves;
    }

    // Update the leave balance in the CSV file
    public static void updateLeaveCsv(List<Leave> leaves) throws IOException {
        String filename = "src/Data/Leave Balance Tally.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        // Write the header line
        writer.write("Employee ID,Last Name,First Name,Sick Leave Days,Vacation Leave Days,Emergency Leave Days\n");

        // Write leave data for each leave object
        for (Leave leave : leaves) {
            writer.write(leave.getId() + "," +
                    leave.getLastName() + "," +
                    leave.getFirstName() + "," +
                    leave.getSickLeaveDays() + "," +
                    leave.getVacationLeaveDays() + "," +
                    leave.getEmergencyLeaveDays() + "\n");
        }

        writer.close(); // Close the writer
    }

    // Write leave application data to a CSV file
    public static void writeLeaveApplicationData(String employeeId, String employeeName, String leaveType, String startDate, String endDate, String totalDays) throws IOException {
        String filePath = "src/Data/Leave Application Approved Dates.csv";

        // Create a BufferedWriter to write to the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

        // Write the leave application data to the file
        writer.write(employeeId + "," +
                employeeName + "," +
                leaveType + "," +
                startDate + "," +
                endDate + "," +
                totalDays + "\n");

        writer.close(); // Close the writer
    }
    
    // Retrieve leave data for a specific employee ID
    public static Leave getLeaveDataByEmployeeId(String employeeId) throws IOException {
        List<Leave> leaves = readLeaveCsv();
        for (Leave leave : leaves) {
            if (leave.getId().equals(employeeId)) {
                return leave;
            }
        }
        return null; // Employee ID not found
    }
}
