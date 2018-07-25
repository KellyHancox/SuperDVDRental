package project4;

import java.awt.GridLayout;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/********************************************************************
 * This class creates a dialog box that asks for a date to 
 * check past and looks through the list of DVDs to find which ones
 * will be late past that date. It will call on another class 
 * that will display that list
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ********************************************************************/
public class RentLateDialog extends JDialog implements 
ActionListener {

	/** this is version identifier for the serializable class*/
	private static final long serialVersionUID = 1L;

	/** creates the text field to input the date*/
	public static JTextField input;

	/** creates the ok button */
	private JButton okButton;
	
	/** creates the cancel button */
	private JButton cancelButton;

	/** creates dialog box */
	private JDialog dialog;
	
	/** creates the frame */
	private JFrame parentFrame;
	
	/** creates the panel */
	private JPanel panel;

	/** holds the number of days a DVD will be late */
	private double numDaysLate;

	/** this creates a calendar version of the date */
	private Calendar c;

	/** this creates a date version of the date */
	private Date lateDaysDateForm;
	
	/** this creates a gregorian calendar version of 
	 * the date */
	private GregorianCalendar gregorianCalendarLateDays;
	
	/** a boolean for whether the date is valid or not */
	private boolean validDate;

	/** the string form of the date */
	private String date;

	/** an arraylist of the DVDs that will be 
	 * late after the input date */
	public static ArrayList<String> lateListInput;

	/*****************************************************************
	 * Constructor instantiates the GUI components and displays 
	 * the GUI
	 * 
	 * @param none
	 ****************************************************************/
	public RentLateDialog(JFrame parent) {
		// sets the valid date to false because the date has not 
		// yet been created 
		validDate = false;

		//instantiates the arrayList to hold strings
		lateListInput = new ArrayList<String>();

		//instantiates the gregorian calendar
		c = new GregorianCalendar();
		
		// making the calendar appear in a simple format
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		// date is getting the time of the calendar
		Date date = c.getTime();

		// this is the box where they can input date
		input = new JTextField(dateFormat.format(date), 30);

		// instantiates buttons with their words and adds 
		//the actionlisteners
		okButton = new JButton("Check");
		cancelButton = new JButton("Cancel");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		// instantiates dialog box and panel
		dialog = new JDialog();
		panel = new JPanel();
		
		//adds the input box and buttons to the panel
		panel.add(input);
		panel.add(okButton);
		panel.add(cancelButton);

		//sets the grid layout 
		panel.setLayout(new GridLayout(3, 1));

		//adds the panel to the dialog box
		dialog.add(panel);

		//sets up the dialog box and makes it visible
		dialog.setLocationRelativeTo(parentFrame);
		dialog.setModal(true);
		dialog.setTitle("Check Late Items");
		dialog.setDefaultCloseOperation(JFrame.
				DISPOSE_ON_CLOSE);
		dialog.setSize(250, 150);
		dialog.setVisible(true);
	}

	/*****************************************************************
	 * This method causes the GUI to process the input upon clicking 
	 * the ok button and to close upon clicking the cancel button
	 * 
	 * @param ActionEvent
	 * @return none
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		//this happens when the ok button is clicked
		if (e.getSource() == okButton) {

			//the string date is the input 
			date = input.getText();

			//calls upon the method to check the date for negatives
			checkForNegatives(date);

			//this occurs if it passed through the method checks
			if(validDate == true) {
				
				//it parses through the DVDs that have
				//been checked out
				for (int i = 0; i < RentalStore.listDVDs.size(); 
						i++) {
					
					//occurs if the due back dates occur
					//before the input date 
					if (RentalStore.listDVDs.get(i).getDueBackDate().
							before(lateDaysDateForm)) {

						//this creates a string out of the DVD
						//information
						String line = "Name: " + " " + RentalStore.
								listDVDs.get(i).getNameOfRenter();
						line += "    Title: " + " " + RentalStore.
								listDVDs.get(i).getTitle();
						line += "    Rented On: " + " " + RentalStore.
								listDVDs.get(i).getBoughtString();
						line += "    Due Back On: " + " " + 
								RentalStore.
								listDVDs.get(i).getDueBack();

						//this calculates the number of days late
						//that the dvd would be on 
						double daysLate = getNumDaysLate(RentalStore.
								listDVDs.get(i).
								getDueBackGregorianCalendar());

						//combines the string and number
						String s = line + ", Days late: " + daysLate;
						
						//adds the final string to the arraylist
						lateListInput.add(s);

					}
				}	
				
				//opens the GUI if there are late DVDs
				if(lateListInput.size() > 0) {
					LateRentalsGUI lateRentals = new 
							LateRentalsGUI();
				}
				
				//tells the user that there are no late rentals
				//if the arraylist is empty
				else {
					JOptionPane.showMessageDialog(null, 
							"There are no late "
							+ "rentals after this date");
				}
			}
			
			//shows error message if not a valid date
			else {
				JOptionPane.showMessageDialog(null, "Please"
						+ " enter a valid date.");
			}
		}

		//if the cancel button is clicked, the dialog will close
		if (e.getSource() == cancelButton) {
			dialog.dispose();
		}
	}


	/*****************************************************************
	 * This takes the date form and checks for negative numbers
	 * 
	 * @param String input date
	 * @return none
	 *****************************************************************/
	private void checkForNegatives(String date) {
		
		//splits the string at the / character
		String[] dates = date.split("/");
		
		//creates temporary variables for the months, days, and years
		int monthAttempt = 0;
		int dayAttempt = 0;
		int yearAttempt = 0;

		//attempts to turn the dates into numbers 
		try {
			monthAttempt = Integer.parseInt(dates[0]);
			dayAttempt = Integer.parseInt(dates[1]);
			yearAttempt = Integer.parseInt(dates[2]);

			//this is checking to make sure that the due
			//back date uses positive numbers
			if(monthAttempt > 0 && dayAttempt > 0 &&
					yearAttempt > 0) {
				checkValidDate();
			}
			
			//this occurs when a negative occurs
			else {
				validDate = false;
			}

		}
		
		//catches any parsing problems and asks the user to 
		//input a valid date
		catch(Exception e1) {
			validDate = false;
		}
	}

	/*****************************************************************
	 * This checks the date and checks that it is a valid date
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void checkValidDate() {
		// this takes the string form of the due back input
		String lateDays = input.getText();
		
		//this checks for invalid dates
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);

		// must surround with a try-catch block
		try {

			// this takes the string form and parses it 
			//into a date form
			lateDaysDateForm = new SimpleDateFormat("MM/dd/yyyy").
					parse(lateDays);
			
			//this checks for invalid dates
			df.parse(lateDays);

			// this takes the date form and turns it into a 
			//gregorian calendar form
			//gregorianCalendarLateDays.setLenient(false);
			gregorianCalendarLateDays = (GregorianCalendar) 
					GregorianCalendar.getInstance();
			gregorianCalendarLateDays.setTime(lateDaysDateForm);

			//makes the boolean true
			validDate = true;
		}

		// this happens if the user inputs something that isn't 
		//a date into the system
		catch (Exception e1) {
			
			//changes the valid date status to false
			validDate = false;
		}
	}

	/*****************************************************************
	 * Counts the number of days between the due date and 
	 * the input late date
	 * 
	 * @param Calendar calendar version of the input date
	 * @return double the number of days between
	 *****************************************************************/
	public double getNumDaysLate(GregorianCalendar cal) {
		
		//counts the number of days late by subtracting the 
		// input date from the due date
		numDaysLate = gregorianCalendarLateDays.
				get(Calendar.DAY_OF_YEAR) - cal.get
				(Calendar.DAY_OF_YEAR);
		return numDaysLate;
	}

}

