package project4;

import javax.swing.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import java.awt.Component;
import java.io.*;
import java.util.*;

/********************************************************************
 * This class creates the rules for the rental store
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ********************************************************************/
public class RentalStore extends AbstractTableModel {

	/** holds the DVDs that are checked out */
	public static MyDoubleLinkedList<DVD> listDVDs;

	/** This is a compononent of the Serializable class */
	private static final long serialVersionUID = 1L;

	/** holds the undo actions */
	public MyDoubleLinkedList<UndoAction> undoList;

	/** holds the column names */
	public String[] columnNames;

	/** holds the table */
	public JTable table;

	/** holds the table model */
	public DefaultTableModel tableModel;

	/*****************************************************************
	 * Constructor creates instantiates the linked list of DVDs 
	 * and undo actions. It also makes the column names and the table
	 * 
	 * @param none
	 ****************************************************************/
	public RentalStore() {
		super();
		// list of dvds
		listDVDs = new MyDoubleLinkedList<>();
		// list of undo actions
		undoList = new MyDoubleLinkedList<UndoAction>();

		columnNames = new String[] 
				{ "First Name", "Title", 
						"Rented on", "Due Back", "Player Type" };

		tableModel = new DefaultTableModel(columnNames, 5);

		table = new JTable(0, 5);
	}

	/*****************************************************************
	 * This method finds the number of columns
	 * 
	 * @param none
	 * @return number of columns
	 ****************************************************************/
	public int getColumnCount() {
		return columnNames.length;
	}

	/*****************************************************************
	 * This method finds the name of the columns
	 * 
	 * @param number of column
	 * @return name of column
	 ****************************************************************/
	public String getColumnName(int column) {
		return columnNames[column];
	}

	/*****************************************************************
	 * This method finds the number of rows
	 * 
	 * @param none
	 * @return number of rows
	 ****************************************************************/
	public int getRowCount() {
		return listDVDs.size();
	}

	/*****************************************************************
	 * This method finds the information in a particular row and 
	 * column
	 * 
	 * @param number of row and column index
	 * @return information in that row and column
	 ****************************************************************/
	public Object getValueAt(int rowIndex, int columnIndex) {
		DVD a = listDVDs.get(rowIndex);

		if (a == null) {
			return null;
		}

		switch (columnIndex) {
		case 0:
			// first row and column
			return a.getNameOfRenter();
		case 1:
			// second row and column
			return a.getTitle();
		case 2:
			// third row and column
			return a.getBoughtString();
		case 3:
			// fourth row and column
			return a.getDueBack();
		case 4:
			// fifth row and column
			if (a instanceof Game) {
				return ((Game) a).getGamePlayerType();
			} else {
				return null;
			}

		default:
			return null;
		}
	}

	/*****************************************************************
	 * Adds a DVD to the arraylist
	 * 
	 * @param DVD
	 *            a DVD with DVD information
	 * @return none
	 ****************************************************************/
	public void add(DVD a) {

		// adds the dvd to the arraylist
		listDVDs.add(a);

		// continuously updates the arraylist
		// fireIntervalAdded(this, 0, listDVDs.size());
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);

		undoList.add(new UndoAction("add", a));

	}

	/*****************************************************************
	 * Deletes a DVD from the linkedList
	 * 
	 * @param a DVD with DVD information
	 * @return none
	 ****************************************************************/
	public void delete(DVD a) {
		// sorts through the list of DVDs and deletes the correct DVD
		for (int i = 0; i < listDVDs.size(); i++) {
			if (a == listDVDs.get(i)) {
				listDVDs.remove(i);
				fireTableRowsDeleted(i, i);

				undoList.add(new UndoAction("remove", a));

			}
		}
	}

	/*****************************************************************
	 * Deletes a DVD from the arraylist
	 * 
	 * @param i integer to get from the arraylist
	 * @return DVD at the selected index
	 ****************************************************************/
	public DVD get(int i) {
		return listDVDs.get(i);
	}

	/*****************************************************************
	 * This method returns how many DVDs there are
	 * 
	 * @param none
	 * @return the size of the arraylist of DVDs
	 ****************************************************************/
	public int getSize() {
		return listDVDs.size();
	}

	/*****************************************************************
	 * This method saves the list of DVDs
	 * 
	 * @param filename
	 *            name of file
	 * @return none
	 ****************************************************************/
	public void saveAsSerializable(String filename) {

		// attempts to save the file
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(listDVDs);
			os.close();
		}

		// displays error message if saving does not work
		catch (IOException ex) {
			JOptionPane.showMessageDialog(null, 
					"Error in saving db");
			ex.printStackTrace();

		}
	}

	/*****************************************************************
	 * This method loads the list of DVDs from a saved file
	 * 
	 * @param filename
	 *            name of file
	 * @return none
	 ****************************************************************/
	public void loadFromSerializable(String filename) {

		// attempts to read the file and display it
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			listDVDs = (MyDoubleLinkedList<DVD>) is.readObject();
			fireTableDataChanged();
			is.close();
		}

		// displays error message if loading does not work
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, 
					"Error in loading db");
		}
	}

	/*****************************************************************
	 * This method saves the list of DVDs in a text file
	 * 
	 * @param filename name of file
	 * @return none
	 ****************************************************************/
	public void saveAsTextFile(String filename) {
		try {
			PrintWriter writer = new PrintWriter(filename);
			ObjectOutputStream out = 
					new ObjectOutputStream
					(new FileOutputStream(filename));

			// iterate over list
			for (int i = 0; i < listDVDs.size(); i++) {

				DVD d = listDVDs.get(i);

				String dateBoughtOn = d.getBoughtString();
				
				String dateDueOn = d.getDueBack();

				if (d instanceof Game) {
					String player;
					PlayerType type = 
							((Game) d).getGamePlayerType();

					if (type == PlayerType.PS4) {
						player = "PS4";
					} else if (type == PlayerType.Xbox360) {
						player = "XBOX360";
					} else {
						player = "XBOX720";
					}

					writer.println(d.getNameOfRenter() + "!!!" + 
					d.getTitle() + "!!!" + 
							dateBoughtOn + "!!!" + dateDueOn
							+ "!!!" + player);
				} else {
					writer.println(
							d.getNameOfRenter() 
							+ "!!!" + d.getTitle() + 
							"!!!" + dateBoughtOn + "!!!" + dateDueOn);
				}
			}
			// writer.println();
			out.close();
			writer.close();
		}

		catch (Exception e) {
			JOptionPane.
			showMessageDialog(null, "Error in saving db");
			e.printStackTrace();
		}

	}

	/*****************************************************************
	 * This method adds elements back into the list of dvds
	 * 
	 * @param dvd info
	 * @return none
	 ****************************************************************/
	public void addFromUndo(DVD a) {

		// adds the dvd to the arraylist
		listDVDs.add(a);

		// continuously updates the arraylist
		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}

	/*****************************************************************
	 * This method removes elements from the list of dvds
	 * 
	 * @param dvd info
	 * @return none
	 ****************************************************************/
	public void deleteFromUndo(DVD a) {
		for (int i = 0; i < listDVDs.size(); i++) {
			if (a == listDVDs.get(i)) {
				listDVDs.remove(i);
				fireTableRowsDeleted(i, i);
			}
		}
	}

	/*****************************************************************
	 * This method undoes adding an item in the list of items
	 * 
	 * @param dvd info
	 * @return none
	 ****************************************************************/
	public void undoAdd() {

		if (undoList.size() <= 0) {
			throw new NullPointerException();
		}

		else {
			UndoAction lastAction = undoList.get(undoList.size() - 1);

			if (lastAction.getAction() == "add") {
				deleteFromUndo(lastAction.getDvd());
				undoList.remove(undoList.size() - 1);
			}

			else if (lastAction.getAction() == "remove") {
				addFromUndo(lastAction.getDvd());
				undoList.remove(undoList.size() - 1);
			}
		}
	}

	/*****************************************************************
	 * This method loads the list of DVDs from a saved text file
	 * 
	 * @param filename name of file
	 * @return none
	 ****************************************************************/
	public void loadFromTextFile(String filename) 
			throws ParseException {
		Scanner fileReader;

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Load");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setCurrentDirectory(new 
				File(System.getProperty("user.home")));

		try {
			fileReader = new Scanner(new File(filename));

			while (fileReader.hasNext()) {
				String[] loaded = fileReader.next().split("!!!");
				SimpleDateFormat df = 
						new SimpleDateFormat("MM/dd/yyyy");

				String dateBought = loaded[2];
                String dueDate = loaded[3];

				Date boughtDateDateForm = 
						new SimpleDateFormat("MM/dd/yyyy")
						.parse(dateBought);
				// This checks for invalid dates
				df.parse(dateBought);

				// this takes the date form and turns it into a
				// gregorian calendar form
				GregorianCalendar gregorianCalendar = 
						(GregorianCalendar) 
						GregorianCalendar.getInstance();
				gregorianCalendar.setTime(boughtDateDateForm);

				Date dueDateDateForm = 
						new SimpleDateFormat
						("MM/dd/yyyy").parse(dueDate);

				df.parse(dueDate);

				GregorianCalendar gregorianCalendar1 = 
						(GregorianCalendar) 
						GregorianCalendar.getInstance();
				gregorianCalendar1.setTime(dueDateDateForm);

				if (loaded.length == 9) {
					Game d = new Game();

					PlayerType type;

					if (loaded[8].equals("PS4")) {
						type = PlayerType.PS4;
					} else if (loaded[8].equals("XBOX360")) {
						type = PlayerType.Xbox360;
					} else {
						type = PlayerType.XBox1;
					}

					((Game) d).setPlayerType(type);
					d.setNameOfRenter(loaded[0]);
					d.setTitle(loaded[1]);

					d.setBought(new GregorianCalendar());
					d.setDueBack(new GregorianCalendar());
					add(d);
				} else {
					DVD d = new DVD();
					d.setNameOfRenter(loaded[0]);
					d.setTitle(loaded[1]);

					d.setBought(new GregorianCalendar());
					d.setDueBack(new GregorianCalendar());
					add(d);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*****************************************************************
	 * This method sets the table header
	 * 
	 * @param none
	 * @return headings of the columns
	 ****************************************************************/
	public Object getTableHeader() {
		columnNames = new String[] { 
				"First Name", "Title", "Rented on", 
				"Due Back", "Player Type" };
		return columnNames;
	}

}

