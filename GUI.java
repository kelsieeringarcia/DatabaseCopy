package flights;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * 
 * @author Kelsie Garcia and Aiden Van Dyke
 *
 */
public class GUI extends JFrame {
    // SQL Connection
    private static final String databaseURL =
            "jdbc:derby:FlightsDB;create=true";

    // GUI Components
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> inputAirline;
    private JFormattedTextField inputNumber;
    private JComboBox<String> inputAirport;
    private JComboBox<String> inputStatus;
    private JComboBox<String> inputGate;
    private JTextField inputDate;
    private JTextField inputTime;
    private JFormattedTextField inputDuration;
    private JButton btnAddFlight;
    private JButton btnRemoveFlight;
    private JButton btnUpdateFlight;
    private Row row;
    private int currentRow;
    private int colNo;
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GUI frame = new GUI();
                frame.setVisible(true);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    GUI() {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:FlightsDB;create=true");
             Statement statement = connection.createStatement()) {

            // Reset data in between testing
            SqlGeneric.resetTables();


            // Window
            getContentPane().setLayout(new BorderLayout());
            JPanel topPanel = new JPanel(new BorderLayout());
            JPanel bottomPanel = new JPanel(new BorderLayout());

            // Top Panel
            FlowLayout flowLayout = topPanel(topPanel);

            // Table
            createJTable();
            JScrollPane pane = new JScrollPane(table);
            //Makes sure only one row can be selected at a time
            table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            getContentPane().add(pane, BorderLayout.CENTER);
           
            

            // Bottom
            bottomPanel(bottomPanel, flowLayout);
            this.pack();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

	private void bottomPanel(JPanel bottomPanel, FlowLayout flowLayout) {
		bottomPanel.setBackground(Color.CYAN);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(flowLayout);
		
		inputAirline = new JComboBox<String>();
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		inputNumber = new JFormattedTextField(numberFormat);
		inputNumber.setValue(0);
		inputNumber.setColumns(4);
		inputNumber.setPreferredSize(new Dimension(40,26));
		inputAirport = new JComboBox<String>();
		inputStatus = new JComboBox<String>();
		inputGate = new JComboBox<String>();
		inputDate = new JTextField("Date");
		inputDate.setPreferredSize(new Dimension(56,26));
		inputTime = new JTextField("Time");
		inputTime.setPreferredSize(new Dimension(56,26));
		inputDuration = new JFormattedTextField(numberFormat);
		inputNumber.setValue(0);
		inputDuration.setPreferredSize(new Dimension(56,26));
		
		btnAddFlight = new JButton("Add Flight");
		btnRemoveFlight = new JButton("Remove Flight");
		btnUpdateFlight = new JButton("Update Flight");

		bottomPanel.add(inputAirline);
		bottomPanel.add(inputNumber);
		bottomPanel.add(inputAirport);
		bottomPanel.add(inputStatus);
		bottomPanel.add(inputGate);
		bottomPanel.add(inputDate);
		bottomPanel.add(inputTime);
		bottomPanel.add(inputDuration);

		bottomPanel.add(btnAddFlight);
		btnAddFlight.addActionListener(e -> addFlight());
		bottomPanel.add(btnRemoveFlight);
		btnRemoveFlight.addActionListener(e -> removeFlight());
		bottomPanel.add(btnUpdateFlight);
		btnUpdateFlight.addActionListener(e -> updateFlight());
		fillBottomComboboxes();
	}

	private FlowLayout topPanel(JPanel topPanel) {
		JLabel lblSort = new JLabel("Sort: ");
		JComboBox inputSort = new JComboBox(SqlColumn.values());
		JButton btnSort = new JButton("Sort");
		JTextField searchbar = new JTextField();
		// TODO replace with table names
		JLabel lblSearch = new JLabel("Search: ");
		JComboBox comboColumn = new JComboBox(SqlColumn.values());
		JComboBox comboTable = new JComboBox(new String[]{"Flight Table"});
		searchbar.setPreferredSize(new Dimension(260,26));
		JButton btnSearch = new JButton("Search");
		getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setBackground(Color.CYAN);
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		topPanel.setLayout(flowLayout);
		//Sort portion
		topPanel.add(lblSort);
		topPanel.add(inputSort);
		topPanel.add(btnSort);
		//Search portion
		topPanel.add(lblSearch);
		topPanel.add(comboTable);
		topPanel.add(comboColumn);
		topPanel.add(searchbar);
		topPanel.add(btnSearch);
		return flowLayout;
	}
    
    /**
     * This method is meant to pull the data from the database and add to the JComboBox
     * Starting with airline ID 
     */
    private void fillBottomComboboxes() {
    	try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()) {
    		
    		//Retrieving column fields and adding to the combo boxes
            ResultSet rs = statement.executeQuery(SqlAirline.getAll());
            while(rs.next()) {
            	inputAirline.addItem(rs.getString("Id"));
            }
            rs = statement.executeQuery(SqlAirport.getAll());
            while(rs.next()) {
            	inputAirport.addItem(rs.getString("Id"));
            }
            rs = statement.executeQuery(SqlStatus.getAll());
            while(rs.next()) {
            	inputStatus.addItem(rs.getString("Description"));
            }
            rs = statement.executeQuery(SqlGate.getAll());
            while(rs.next()) {
            	inputGate.addItem(rs.getString("Id"));
            }
            
    	}catch (SQLException e) {
            System.err.println("There was a problem filling combo boxes.");
            e.printStackTrace();
        }
    }
    
    

    private void addFlight() {
        String airlineId = String.valueOf(inputAirline.getSelectedItem());
        int number = Integer.parseInt(inputNumber.getText());
        String airportId = String.valueOf(inputAirport.getSelectedItem());
        String statusString = String.valueOf(inputStatus.getSelectedItem());
        int status = 0;
        if (statusString.equals("Now Boarding"))
            status = 1;
        if (statusString.equals("Delayed"))
            status = 2;
        if (statusString.equals("Canceled"))
            status = 3;
        // Else status defaults to 0 which equals "On Time" status
        String gate = String.valueOf(inputGate.getSelectedItem());
        String date = String.valueOf(inputDate.getText());
        String time = String.valueOf(inputTime.getText());
        int duration = Integer.parseInt(inputDuration.getText());
        //Message so you do not double add
        JOptionPane.showMessageDialog(null, "Added the flight successfully");

        try (Connection connection = DriverManager.getConnection(databaseURL);
                    Statement statement = connection.createStatement()) {

            statement.execute(SqlFlight.insertValue(airlineId, number, airportId, status, gate, date, time, duration));
            updateJTable();
        }
        catch (SQLException e) {
            System.err.println("There was a problem adding a flight.");
            e.printStackTrace();
        }
    }
    /**
     * This takes what is selected from the JTable and deletes the flight selected
     * from the table and the database
     */
	private void removeFlight() {
		int number = Integer.parseInt(inputNumber.getText());
		// check for selected row first
		if (table.getSelectedRow() != -1) {
			// remove selected row from the model
			tableModel.removeRow(table.getSelectedRow());
			JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
		}
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			 statement.execute(SqlFlight.removeFlightWhere(number));
		} catch (SQLException e) {
			System.err.println("There was a problem deleting the flight.");
			e.printStackTrace();
		}
	}
	/**
	 * This method takes the current selected flight on the JTable and updates the fields
	 * and database when the button "update" is pressed
	 */
	private void updateFlight() {
		String airlineId = String.valueOf(inputAirline.getSelectedItem());
        int number = Integer.parseInt(inputNumber.getText());
        String airportId = String.valueOf(inputAirport.getSelectedItem());
        String statusString = String.valueOf(inputStatus.getSelectedItem());
        int status = 0;
        if (statusString.equals("Now Boarding"))
            status = 1;
        if (statusString.equals("Delayed"))
            status = 2;
        if (statusString.equals("Canceled"))
            status = 3;
        // Else status defaults to 0 which equals "On Time" status
        String gate = String.valueOf(inputGate.getSelectedItem());
        String date = String.valueOf(inputDate.getText());
        String time = String.valueOf(inputTime.getText());
        int duration = Integer.parseInt(inputDuration.getText());
        
        //Updates the values on the JTable
        int i = table.getSelectedRow();
        table.setValueAt(inputAirline.getSelectedItem(), i, 0);
        table.setValueAt(inputNumber.getText(), i, 1);
        table.setValueAt(inputAirport.getSelectedItem(), i, 2);
        table.setValueAt(inputStatus.getSelectedItem(), i, 3);
        table.setValueAt(inputGate.getSelectedItem(), i, 4);
        table.setValueAt(inputDate.getText(), i, 5);
        table.setValueAt(inputTime.getText(), i, 6);
        table.setValueAt(inputDuration.getText(), i, 7);
        
		//TODO Fix the query or the data being put in the query
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			 //statement.execute(SqlFlight.updateFlight(airlineId, number, airportId, status, gate, date, time, duration));
			System.out.println(airlineId + " " + number + " " + airportId + " " + status + " " 
			+ gate + " " + date + " " + time + " " + duration);
			//updateJTable();
		} catch (SQLException e) {
			System.err.println("There was a problem updating the flight.");
			e.printStackTrace();
		}
        
		
	}

    private void createJTable() {
        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(SqlFlight.getAllSortedWithNames(SqlColumn.AIRLINE_NAME));
            ResultSetMetaData rsmd = rs.getMetaData();

            Object[] columnLabels;
            int colNo = rsmd.getColumnCount();
            columnLabels = new Object[colNo];
            for (int i = 0; i < colNo; i++) {
                columnLabels[i] = rsmd.getColumnLabel(i + 1);
                
            }

            table = new JTable(new DefaultTableModel(columnLabels, 0));
            table.addMouseListener(new MouseAdapter() {
            	@Override
            	public void mouseClicked(MouseEvent e) {
            		 currentRow = table.getSelectedRow();
            		 
                     Object airlineIdBox = tableModel.getValueAt(currentRow, 0);
                     Object flightNumBox = tableModel.getValueAt(currentRow, 1);
                     Object destinationBox = tableModel.getValueAt(currentRow, 2);
                     Object statusBox = tableModel.getValueAt(currentRow, 3);
                     Object gateBox = tableModel.getValueAt(currentRow, 4);
                     Object dateBox = tableModel.getValueAt(currentRow, 5);
                     Object timeBox = tableModel.getValueAt(currentRow, 6);
                     Object durationBox = tableModel.getValueAt(currentRow, 7);
                     //Constructs a row of data together
                     row = new Row(airlineIdBox.toString(), flightNumBox.toString(), destinationBox.toString(), statusBox.toString(), 
                    		 gateBox.toString(), dateBox.toString(), timeBox.toString(), durationBox.toString());
                     //This updates the JComboBoxes with the data selected on the table
                     inputAirline.setSelectedItem(row.airlineToId());
                     inputNumber.setText(flightNumBox.toString());
                     inputAirport.setSelectedItem(row.destinationToId());
                     inputStatus.setSelectedItem(statusBox);
                     inputGate.setSelectedItem(gateBox);
                     inputDate.setText(dateBox.toString());
                     inputTime.setText(timeBox.toString());
                     inputDuration.setText(durationBox.toString());
                     
                     
                     //TODO showing the output in the console for testing
                     System.out.println(row.toString());
                     
            		
            	}
            });
            tableModel = (DefaultTableModel) table.getModel();

            while (rs.next()) {
                Object[] objects = new Object[colNo];
                for (int i = 0; i < colNo; i++) {
                    objects[i] = rs.getObject(i + 1); // SQL is indexes start at 1
                }
                tableModel.addRow(objects);
            }
            table.setModel(tableModel);
        }
        catch (SQLException e){
            System.err.println("There was a problem updating the JTable.");
            e.printStackTrace();
        }
    }
    

    private void updateJTable() {
        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(SqlFlight.getAllSortedWithNames(SqlColumn.AIRLINE_NAME));
            ResultSetMetaData rsmd = rs.getMetaData();

            Object[] columnLabels;
            colNo = rsmd.getColumnCount();
            columnLabels = new Object[colNo];
            for (int i = 0; i < colNo; i++) {
                columnLabels[i] = rsmd.getColumnLabel(i + 1);
            }

            if (tableModel.getRowCount() > 0) {
                for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                    tableModel.removeRow(i);
                }
            }

            while (rs.next()) {
                Object[] objects = new Object[colNo];
                for (int i = 0; i < colNo; i++) {
                    objects[i] = rs.getObject(i + 1); // SQL is indexes start at 1                    
                }
                tableModel.addRow(objects);
                
            }
            tableModel.fireTableDataChanged();
            table.setModel(tableModel);
        }
        catch (SQLException e){
            System.err.println("There was a problem updating the JTable.");
            e.printStackTrace();
        }
    }
}