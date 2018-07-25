package project4;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;

import project4.RentDVDDialog;
import project4.RentGameDialog;
import project4.RentLateDialog;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*********************************************************************
 * This class is the GUI witch which the user interacts with to rent 
 * a game or a dvd
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 25, 2018
 *********************************************************************/
public class RentalStoreGUI extends JFrame implements ActionListener {

	/** This is a part of Serializable */
	private static final long serialVersionUID = 1L;

	/** Holds the menu bar */
	private JMenuBar menus;

	/** Menu bar items for files */
	private JMenu fileMenu;
	
	/** Menu bar items for performing actions */
	private JMenu actionMenu;

	/** Menu bar item for opening serializable files */
	private JMenuItem openSerItem;
	
	/** Menu bar items exiting */
	private JMenuItem exitItem;
	
	/** Menu bar items for saving serializable files */
	private JMenuItem saveSerItem;
	
	/** Menu bar item for opening text file */
	private JMenuItem openTextItem;
	
	/** Menu bar item for saving text file */
	private JMenuItem saveTextItem;
	
	/** Menu bar item for renting a DVD */
	private JMenuItem rentDVD;
	
	/** Menu bar item for renting a game */
	private JMenuItem rentGame;
	
	/** Menu bar item for returning a game */
	private JMenuItem returnItem;
	
	/** Menu bar item for checking late rentals */
	private JMenuItem lateRental;
	
	/** Menu bar item for undoing an action */
	private JMenuItem undoAction;

	/** Holds the input date */
	private String inputDate;

	/** Holds the table engine */
	private RentalStore table;

	/** Holds the JTable area */
	private JTable JTableArea;


	protected String[] columnNames;

	/*****************************************************************
	 * Constructor instantiates the GUI components and displays the 
	 * GUI
	 ****************************************************************/
	public RentalStoreGUI() {

		// adding menu bar and menu items
		undoAction = new JMenuItem("Undo");
		menus = new JMenuBar();
		fileMenu = new JMenu("File");
		actionMenu = new JMenu("Action");
		openSerItem = new JMenuItem("Load Serial");
		exitItem = new JMenuItem("Exit");
		saveSerItem = new JMenuItem("Save Serial");
		openTextItem = new JMenuItem("Open Text");
		saveTextItem = new JMenuItem("Save Text");
		rentDVD = new JMenuItem("Rent DVD");
		rentGame = new JMenuItem("Rent Game");
		returnItem = new JMenuItem("Return");
		lateRental = new JMenuItem("Late Rental Check");

		// adding items to bar
		fileMenu.add(openSerItem);
		fileMenu.add(saveSerItem);
		fileMenu.add(openTextItem);
		fileMenu.add(saveTextItem);
		fileMenu.add(exitItem);
		actionMenu.add(rentDVD);
		actionMenu.add(rentGame);
		actionMenu.add(returnItem);
		actionMenu.add(lateRental);
		actionMenu.add(undoAction);

		menus.add(fileMenu);
		menus.add(actionMenu);

		// adding actionListener
        undoAction.addActionListener(this);
		openTextItem.addActionListener(this);
		saveTextItem.addActionListener(this);
		openSerItem.addActionListener(this);
		saveSerItem.addActionListener(this);
		exitItem.addActionListener(this);
		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		returnItem.addActionListener(this);
		lateRental.addActionListener(this);

		setJMenuBar(menus);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		columnNames = new String[] { "Name of Renter", 
			"Title", "Rented on", "Due Back", "Player Type" };

		// adding table to the GUI
		table = new RentalStore();
        JTableArea = new JTable(table);
		add(new JScrollPane(JTableArea));
		JTableArea.setVisible(true);

		setVisible(true);
		setSize(800, 800);
	}

	/*****************************************************************
	 * This method causes the selected menu items to function as 
	 * appropriate
	 * 
	 * @param ActionEvent
	 * @return none
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		Object clicked = e.getSource();

		// If load is clicked
		if (openSerItem == clicked || openTextItem == clicked) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = 
						chooser.getSelectedFile().getAbsolutePath();
				if (openSerItem == clicked) {
					table.loadFromSerializable(filename);
				}
				if (openTextItem == clicked) {
					table.loadFromTextFile(filename);
				}
			}

		}

		// If save is clicked
		if (saveSerItem == clicked || saveTextItem == clicked) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = 
						chooser.getSelectedFile().
						getAbsolutePath();
				if (saveSerItem == e.getSource()) {
					table.saveAsSerializable(filename);
				}
				if (saveTextItem == e.getSource()) {
					table.saveAsTextFile(filename);
				}
			}
		}

		// if undo is clicked 
		if (e.getSource() == undoAction) {

			int index = JTableArea.getSelectedRow();
			DVD unit = table.get(index);

			try {
				table.undoAdd();
			} catch (Exception ex) {
				JOptionPane.
				showMessageDialog(null, 
						"No actions left to undo");
			}

		}
        
		// MenuBar options
		// If exit is clicked
		if (e.getSource() == exitItem) {
			System.exit(1);
		}

		// If rent dvd is clicked
		if (e.getSource() == rentDVD) {
			DVD dvd = new DVD();
			RentDVDDialog dialog = 
					new RentDVDDialog(this, dvd);

			if (RentDVDDialog.disposed == false) {
				table.add(dvd);
			}
		}

		// If rent game is clicked
		if (e.getSource() == rentGame) {
			Game game = new Game();
			RentGameDialog dialog = 
					new RentGameDialog(this, game);

			if (RentGameDialog.disposed == false) {
				table.add(game);
			}

		}

		// If check late rentals is clicked
		if (e.getSource() == lateRental) {
			RentLateDialog lateRent = new RentLateDialog(this);
		}

		// If return items is clicked
		if (returnItem == e.getSource()) {

			int index = (int) JTableArea.getSelectedRow();
			// int index = JTableArea.getSelectedRow();

			boolean validReturnDate = false;

			GregorianCalendar date = new GregorianCalendar();
			inputDate = JOptionPane.showInputDialog(""
					+ "Enter return date: ");
			// Makes it into a date form
			SimpleDateFormat df = new SimpleDateFormat
					("MM/dd/yyyy");

			// CHeck if the input date is negative
			if (checkNegativeReturnDate(inputDate) && 
					parseReturnDate()) {

				try {

					Date newDate = df.parse(inputDate);
					date.setTime(newDate);

					validReturnDate = true;

				} catch (Exception ex) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,
							"You did not input a valid return" + 
					" date.\nPlease try again.");
				}

				// This executes if the return date is valid
				if (validReturnDate) {
					DVD unit = table.get(index);

					// If return date is before rented date
					if (date.before(unit.getBoughtDate())) {
						JOptionPane.showMessageDialog(null, 
					"You cannot return an item before "
					+ "it is bought.");
					}

					else {
						JOptionPane.showMessageDialog(null, 
								"Thanks, " + 
					unit.getNameOfRenter() + " for returning "
								+ unit.getTitle() + ", you owe: " 
					+ unit.getCost(date) + " dollars");

						// deletes item from list after returned
						table.delete(unit);
					}
				}

			}

			// this executes if something is wrong
			else if (inputDate == null || inputDate != null) {
				JOptionPane.showMessageDialog(null, 
						"Please input a valid date");
			}
		}

	}

	/*****************************************************************
	 * This method checks if the return date is negative
	 * 
	 * @param return date
	 * @return true if it is positive and false if negative
	 *****************************************************************/
	private boolean checkNegativeReturnDate(String date) {

		try {
			String[] dates = date.split("/");
			int monthAttempt = 0;
			int dayAttempt = 0;
			int yearAttempt = 0;
			monthAttempt = Integer.parseInt(dates[0]);
			dayAttempt = Integer.parseInt(dates[1]);
			yearAttempt = Integer.parseInt(dates[2]);

			// this is checking to make sure that the
			// rented on date uses positive numbers
			if (monthAttempt > 0 && dayAttempt >
			0 && yearAttempt > 0) {
				return true;
			} else {
				return false;
			}
		}
		// this happens if the user
		// inputs something that isn't a date into the system
		catch (Exception ex) {

			return false;
		}
	}

	/*****************************************************************
	 * This method checks if the return date is parsed into a 
	 * GregorianCalendar
	 * 
	 * @param none
	 * @return true if parsed, false if not
	 *****************************************************************/
	private boolean parseReturnDate() {
		// this takes the string form of the due back input
		String returnedOnDate = inputDate;

		// must surround with a try-catch block
		try {

			// this takes the string form and parses
			// it into a date form
			Date returnedOn = new SimpleDateFormat("MM/dd/yyyy").
					parse(returnedOnDate);

			// this takes the date form and turns it
			// into a gregorian calendar form
			GregorianCalendar gregorianCalendar = 
					(GregorianCalendar) GregorianCalendar.
					getInstance();
			gregorianCalendar.setTime(returnedOn);

			return true;

		}

		// this happens if the user
		// inputs something that isn't a date into the system
		catch (Exception e1) {
			return false;
		}
	}

	/*****************************************************************
	 * This is the main method that runs the GUI
	 *****************************************************************/
	public static void main(String[] args) {
		new RentalStoreGUI();
	}

}


