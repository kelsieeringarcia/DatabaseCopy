package flights;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.NumberFormat;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private int columnCount;
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

            // TODO split sections into separate methods

            // Window
            getContentPane().setLayout(new BorderLayout());
            JPanel topPanel = new JPanel(new BorderLayout());
            JPanel bottomPanel = new JPanel(new BorderLayout());

            // Top Panel
            JTextField searchbar = new JTextField();
            // TODO replace with table names
            JComboBox comboColumn = new JComboBox(SqlColumn.values());
            JComboBox comboTable = new JComboBox(new String[]{"Flight Table"});
            searchbar.setPreferredSize(new Dimension(260,26));
            JButton btnSearch = new JButton("Search");
            getContentPane().add(topPanel, BorderLayout.NORTH);
            topPanel.setBackground(Color.BLUE);
            FlowLayout flowLayout = new FlowLayout();
            flowLayout.setAlignment(FlowLayout.RIGHT);
            topPanel.setLayout(flowLayout);

            topPanel.add(comboTable);
            topPanel.add(comboColumn);
            topPanel.add(searchbar);
            topPanel.add(btnSearch);

            // Table
            createJTable();
            JScrollPane pane = new JScrollPane(table);
            //Makes sure only one row can be selected at a time
            table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            
            getContentPane().add(pane, BorderLayout.CENTER);
           
            

            // Bottom
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
            fillBottomComboboxes();
            this.pack();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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

	private void removeFlight() {
		// check for selected row first
		if (table.getSelectedRow() != -1) {
			// remove selected row from the model
			tableModel.removeRow(table.getSelectedRow());
			JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
		}
		try (Connection connection = DriverManager.getConnection(databaseURL);
                Statement statement = connection.createStatement()) {
			//TODO connect the selected data with bottom panel to ensure its being removed from the database
        //statement.execute(SqlFlight.removeFlightWhere(airlineId, number, airportId, status, gate, date, time, duration));
			//TODO also make updates to updateJTable method to delete
        //updateJTable();
    }
    catch (SQLException e) {
        System.err.println("There was a problem adding a flight.");
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
            		 //columnCount = table.getSelectedColumns();
            		 System.out.println(colNo);
            		 
                     Object temp1 = tableModel.getValueAt(currentRow, 0);
                     Object temp2 = tableModel.getValueAt(currentRow, 1);
                     Object temp3 = tableModel.getValueAt(currentRow, 2);
                     Object temp4 = tableModel.getValueAt(currentRow, 3);
                     Object temp5 = tableModel.getValueAt(currentRow, 4);
                     Object temp6 = tableModel.getValueAt(currentRow, 5);
                     Object temp7 = tableModel.getValueAt(currentRow, 6);
                     Object temp8 = tableModel.getValueAt(currentRow, 7);
                     row = new Row(temp1.toString(), temp2.toString(), temp3.toString(), temp4.toString(), 
                    		 temp5.toString(), temp6.toString(), temp7.toString(), temp8.toString());
                     inputAirline.setSelectedItem(temp1);
                     System.out.println(row.toString());
                     //TODO now that the information prints assign it to the bottom row to show those fields.
            		
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