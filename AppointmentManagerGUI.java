package project;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManagerGUI {
    private JFrame frame;
    private DefaultListModel<Appointment> appointmentListModel;
    private JList<Appointment> appointmentList;
    private List<Appointment> appointments;

    public AppointmentManagerGUI() {
        appointments = new ArrayList<>();
        FlatLightLaf.setup(new FlatLightLaf()); // Apply modern theme
        initializeUI();
    }

    private void initializeUI() {
        // Main Frame Setup
        frame = new JFrame("🗓️ Appointment Manager");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Split Panel for single-page design
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(5); // Thin divider
        splitPane.setDividerLocation(450); // Divide into form and list sections

        // Left Panel: Add/Edit Appointment Form
        JPanel formPanel = createAddEditPanel();
        splitPane.setLeftComponent(formPanel);

        // Right Panel: View Appointments
        JPanel viewPanel = createViewPanel();
        splitPane.setRightComponent(viewPanel);

        frame.add(splitPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createAddEditPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(250, 250, 255)); // Light background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Section Title
        JLabel sectionTitle = new JLabel("Add or Edit Appointment 📅");
        sectionTitle.setFont(new Font("San Francisco", Font.BOLD, 18));
        sectionTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(sectionTitle, gbc);

        // Appointment Type
        JLabel typeLabel = new JLabel("Appointment Type: 📝");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(typeLabel, gbc);

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton oneTimeButton = new JRadioButton("One-time");
        JRadioButton dailyButton = new JRadioButton("Daily");
        JRadioButton monthlyButton = new JRadioButton("Monthly");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(oneTimeButton);
        typeGroup.add(dailyButton);
        typeGroup.add(monthlyButton);
        typePanel.add(oneTimeButton);
        typePanel.add(dailyButton);
        typePanel.add(monthlyButton);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(typePanel, gbc);

        // Description
        JLabel descLabel = new JLabel("Description: 🖊");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(descLabel, gbc);

        JTextField descField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(descField, gbc);

        // Start Date with Calendar Emoji
        JLabel startDateLabel = new JLabel("Start Date: 📅");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(startDateLabel, gbc);

        DatePicker startDatePicker = createDatePicker();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(startDatePicker, gbc);

        // End Date (optional) with Calendar Emoji
        JLabel endDateLabel = new JLabel("End Date (optional): 📅");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(endDateLabel, gbc);

        DatePicker endDatePicker = createDatePicker();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(endDatePicker, gbc);

        // Save Button with Save Emoji
        JButton saveButton = new JButton("Save Appointment 💾");
        saveButton.setFont(new Font("San Francisco", Font.BOLD, 14));
        saveButton.setBackground(new Color(34, 193, 195)); // Greenish color for button
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            String type = oneTimeButton.isSelected() ? "One-time" :
                    dailyButton.isSelected() ? "Daily" : "Monthly";
            String description = descField.getText();
            LocalDate startDate = startDatePicker.getDate();
            LocalDate endDate = endDatePicker.getDate();  // This can be null

            // Validation
            if (description.isEmpty() || startDate == null) {
                JOptionPane.showMessageDialog(frame, "Description and Start Date are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (type.equals("One-time") && (endDate != null && !startDate.equals(endDate))) {
                JOptionPane.showMessageDialog(frame, "Start and End Date must be the same for One-time appointments.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (startDate.isAfter(endDate != null ? endDate : startDate)) {
                JOptionPane.showMessageDialog(frame, "Start Date cannot be after End Date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Appointment appointment = createAppointment(type, description, startDate, endDate);
            appointments.add(appointment);
            appointmentListModel.addElement(appointment);

            // Reset the form fields after saving
            descField.setText("");
            startDatePicker.setDate(null);
            endDatePicker.setDate(null);

            JOptionPane.showMessageDialog(frame, "Appointment added successfully!");
        });
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(saveButton, gbc);

        return panel;
    }

    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // List Title
        JLabel listTitle = new JLabel("View Appointments 🎉");
        listTitle.setFont(new Font("San Francisco", Font.BOLD, 18));
        listTitle.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(listTitle, BorderLayout.NORTH);

        // Appointment List
        appointmentListModel = new DefaultListModel<>();
        appointmentList = new JList<>(appointmentListModel);
        appointmentList.setFont(new Font("San Francisco", Font.PLAIN, 14));
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Custom cell renderer for colorful widgets
        appointmentList.setCellRenderer(new AppointmentWidgetRenderer());

        JScrollPane scrollPane = new JScrollPane(appointmentList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");
        JButton viewAllButton = new JButton("View All Appointments");
        JButton findButton = new JButton("Find Appointment");
        JButton sortButton = new JButton("Sort");

        deleteButton.addActionListener(e -> deleteSelectedAppointment());
        updateButton.addActionListener(e -> openUpdateDialog());
        viewAllButton.addActionListener(e -> viewAllAppointments());
        findButton.addActionListener(e -> findAppointments());
        sortButton.addActionListener(e -> sortAppointments());

        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(findButton);
        buttonPanel.add(sortButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private DatePicker createDatePicker() {
        DatePickerSettings settings = new DatePickerSettings();
        settings.setAllowEmptyDates(true); // End date is optional, can be left empty
        return new DatePicker(settings);
    }

    private Appointment createAppointment(String type, String description, LocalDate startDate, LocalDate endDate) {
        Appointment appointment = null;
        if (type.equals("One-time")) {
            appointment = new OneTimeAppointment(description, startDate);  // Adjust constructor for One-time
        } else if (type.equals("Daily")) {
            appointment = new DailyAppointment(description, startDate, endDate);
        } else if (type.equals("Monthly")) {
            appointment = new MonthlyAppointment(description, startDate, endDate);
        }
        return appointment;
    }

    private void deleteSelectedAppointment() {
        Appointment selected = appointmentList.getSelectedValue();
        if (selected != null) {
            appointments.remove(selected);
            appointmentListModel.removeElement(selected);
            JOptionPane.showMessageDialog(frame, "Appointment deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an appointment to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openUpdateDialog() {
        Appointment selected = appointmentList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(frame, "Please select an appointment to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a dialog for updating appointment details
        JDialog updateDialog = new JDialog(frame, "Update Appointment", true);
        updateDialog.setSize(400, 300);
        updateDialog.setLayout(new GridLayout(5, 2));

        // Pre-fill the fields with current appointment details
        JTextField descField = new JTextField(selected.getDescription());
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setDate(selected.getStartDate());
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setDate(selected.getEndDate());

        // Add fields and labels to the dialog
        updateDialog.add(new JLabel("Description:"));
        updateDialog.add(descField);
        updateDialog.add(new JLabel("Start Date:"));
        updateDialog.add(startDatePicker);
        updateDialog.add(new JLabel("End Date:"));
        updateDialog.add(endDatePicker);

        // Save button to update the appointment
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            String newDescription = descField.getText();
            LocalDate newStartDate = startDatePicker.getDate();
            LocalDate newEndDate = endDatePicker.getDate();

            if (newDescription.isEmpty() || newStartDate == null || newEndDate == null) {
                JOptionPane.showMessageDialog(updateDialog, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (newStartDate.isAfter(newEndDate)) {
                JOptionPane.showMessageDialog(updateDialog, "Start Date cannot be after End Date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the appointment in the list
            selected.setDescription(newDescription);
            selected.setStartDate(newStartDate);
            selected.setEndDate(newEndDate);

            appointmentList.repaint();
            JOptionPane.showMessageDialog(updateDialog, "Appointment updated successfully!");
            updateDialog.dispose();
        });
        updateDialog.add(saveButton);

        updateDialog.setVisible(true);
    }

    private void viewAllAppointments() {
        // View all appointments without filtering
        appointmentListModel.clear();
        for (Appointment appointment : appointments) {
            appointmentListModel.addElement(appointment);
        }
    }

    private void findAppointments() {
        String[] options = {"By Description", "By Date"};
        String choice = (String) JOptionPane.showInputDialog(frame, "Choose search option:", "Find Appointment",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == null) return;

        List<Appointment> filteredAppointments = new ArrayList<>();
        if ("By Description".equals(choice)) {
            String description = JOptionPane.showInputDialog(frame, "Enter description to search for:");
            for (Appointment appointment : appointments) {
                if (appointment.getDescription().toLowerCase().contains(description.toLowerCase())) {
                    filteredAppointments.add(appointment);
                }
            }
        } else if ("By Date".equals(choice)) {
            String dateStr = JOptionPane.showInputDialog(frame, "Enter date (YYYY-MM-DD):");
            LocalDate date = LocalDate.parse(dateStr);
            for (Appointment appointment : appointments) {
                if (appointment.getStartDate().equals(date)) {
                    filteredAppointments.add(appointment);
                }
            }
        }

        if (filteredAppointments.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No appointments match the given criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Update the appointment list to show only filtered results
            appointmentListModel.clear();
            for (Appointment appointment : filteredAppointments) {
                appointmentListModel.addElement(appointment);
            }
        }
    }

    private void sortAppointments() {
        String[] options = {"Sort by Date", "Sort by Description"};
        String choice = (String) JOptionPane.showInputDialog(frame, "Choose sort option:", "Sort Appointments",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == null) return;

        if ("Sort by Date".equals(choice)) {
            appointments.sort((a, b) -> a.getStartDate().compareTo(b.getStartDate()));
        } else if ("Sort by Description".equals(choice)) {
            appointments.sort((a, b) -> a.getDescription().compareToIgnoreCase(b.getDescription()));
        }

        // Refresh the appointment list with the sorted list
        appointmentListModel.clear();
        for (Appointment appointment : appointments) {
            appointmentListModel.addElement(appointment);
        }
    }
    // Custom renderer for colorful appointment widgets
    static class AppointmentWidgetRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Appointment appointment = (Appointment) value;

            // Format the start and end dates in a more readable format
            String formattedStartDate = appointment.getStartDate().format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
            String formattedEndDate = (appointment.getEndDate() != null) ? appointment.getEndDate().format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy")) : "";

            // Display the appointment details with dates formatted
            String endDateText = (formattedEndDate.isEmpty()) ? "" : " - " + formattedEndDate;

            label.setText("📅 " + appointment.getDescription() + " | " + formattedStartDate + endDateText); // Showing the dates with description
            label.setFont(new Font("San Francisco", Font.PLAIN, 14));
            label.setOpaque(true);

            // Change background and text color depending on selection
            if (isSelected) {
                label.setBackground(new Color(34, 193, 195));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(index % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                label.setForeground(Color.BLACK);
            }

            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppointmentManagerGUI::new);
    }
}
