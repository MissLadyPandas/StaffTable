import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class StaffTableApp {
    // database connection details
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/user_database?useSSL=false"; 
	private static final String JDBC_USER = "root"; 
	private static final String JDBC_PASSWORD = "";


    // defines UI components
    private JTextField idField, lastNameField, firstNameField, miField, addressField, cityField, stateField, telephoneField;
    private JButton viewButton, insertButton, updateButton, clearButton;

    public StaffTableApp() {
        // initiates the ui components
        JFrame frame = new JFrame("Staff Information");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(10, 20, 80, 25);
        panel.add(idLabel);

        idField = new JTextField(20);
        idField.setBounds(150, 20, 165, 25);
        panel.add(idField);

        // other labels and text fields 
        addLabelTextField(panel, "Last Name:", 50, lastNameField = new JTextField(20));
        addLabelTextField(panel, "First Name:", 80, firstNameField = new JTextField(20));
        addLabelTextField(panel, "MI:", 110, miField = new JTextField(1));
        addLabelTextField(panel, "Address:", 140, addressField = new JTextField(20));
        addLabelTextField(panel, "City:", 170, cityField = new JTextField(20));
        addLabelTextField(panel, "State:", 200, stateField = new JTextField(2));
        addLabelTextField(panel, "Telephone:", 230, telephoneField = new JTextField(10));

        // buttons
        viewButton = new JButton("View");
        viewButton.setBounds(10, 260, 100, 25);
        panel.add(viewButton);

        insertButton = new JButton("Insert");
        insertButton.setBounds(120, 260, 100, 25);
        panel.add(insertButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(230, 260, 100, 25);
        panel.add(updateButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(340, 260, 100, 25);
        panel.add(clearButton);

        // add action listeners to the buttons
        viewButton.addActionListener(this::viewAction);
        insertButton.addActionListener(this::insertAction);
        updateButton.addActionListener(this::updateAction);
        clearButton.addActionListener(this::clearAction);
    }

    private void addLabelTextField(JPanel panel, String label, int y, JTextField textField) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(10, y, 120, 25);
        panel.add(jLabel);

        textField.setBounds(150, y, 165, 25);
        panel.add(textField);
    }

    private void viewAction(ActionEvent e) {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "SELECT * FROM Staff WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    lastNameField.setText(resultSet.getString("lastName"));
                    firstNameField.setText(resultSet.getString("firstName"));
                    miField.setText(resultSet.getString("mi"));
                    addressField.setText(resultSet.getString("address"));
                    cityField.setText(resultSet.getString("city"));
                    stateField.setText(resultSet.getString("state"));
                    telephoneField.setText(resultSet.getString("telephone"));
                } else {
                    JOptionPane.showMessageDialog(null, "No record found with ID: " + id, "Not Found", JOptionPane.INFORMATION_MESSAGE);
                    clearAction(e); // Clearing other fields if no record is found.
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while accessing the database!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void insertAction(ActionEvent e) {
        String id = idField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String mi = miField.getText().trim();
        String address = addressField.getText().trim();
        String city = cityField.getText().trim();
        String state = stateField.getText().trim();
        String telephone = telephoneField.getText().trim();

        if(id.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || 
            address.isEmpty() || city.isEmpty() || state.isEmpty() || 
            telephone.isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, mi);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, state);
            preparedStatement.setString(8, telephone);

            int rowsInserted = preparedStatement.executeUpdate();
            if(rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Record inserted successfully!");
                clearAction(e); // Clearing fields after successful insertion.
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while accessing the database!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateAction(ActionEvent e) {
        String id = idField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String mi = miField.getText().trim();
        String address = addressField.getText().trim();
        String city = cityField.getText().trim();
        String state = stateField.getText().trim();
        String telephone = telephoneField.getText().trim();

        if(id.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || 
            address.isEmpty() || city.isEmpty() || state.isEmpty() || 
            telephone.isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, mi);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, state);
            preparedStatement.setString(7, telephone);
            preparedStatement.setString(8, id);

            // success and error updates
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Record updated successfully!");
                clearAction(e); 
            } else {
                JOptionPane.showMessageDialog(null, "No record found with ID: " + id, "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while accessing the database!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void clearAction(ActionEvent e) {
        //clears all the input fields
        idField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        miField.setText("");
        addressField.setText("");
        cityField.setText("");
        stateField.setText("");
        telephoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StaffTableApp());
    }
}

