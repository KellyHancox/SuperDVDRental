package project4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

/********************************************************************
 * This is the custom dialog box for renting a DVD
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ********************************************************************/
public class RentDVDDialog extends JDialog implements
ActionListener {

	/** input box for title */
	private JTextField titleTxt;

	/** input box for name of renter */
	private JTextField renterTxt;

	/** input box for date rented on */
	public JTextField rentedOnTxt;

	/** input box for due back date */
	public JTextField dueBackTxt;

	/** ok button */
	private JButton okButton;

	/** cancel button */
	private JButton cancelButton;

	/** boolean for whether the box is closed or not */
	private boolean closeStatus;

	/** boolean for whether the due back is valid */
	private boolean parseDueBack = false; 

	/** boolean for whether the rented on date is valid */
	private boolean parseRentedOn = false;

	/** date version for the bought date */
	private Date boughtDateDateForm;

	/** date version for the due back date */
	private Date dueBackDateDateForm;

	/** frame */
	private JFrame frame;

	/** boolean for whether or not the box is disposed */
	public static boolean disposed;

	/** calendar object for use in parsing */
	private Calendar c;

	/** gregorian calendars for the bought date */
	private GregorianCalendar gregorianCalendarBought;

	/** gregorian calendars for the due back date */
	private GregorianCalendar gregorianCalendarDue;

	/** dvd object */
	private DVD unit;


	/*****************************************************************
	 * Constructor instantiates the GUI components and displays 
	 * the GUI
	 * 
	 * @param parent the frame, d a DVD
	 ****************************************************************/
	public RentDVDDialog(JFrame parent, DVD d) {
		// call parent and create a 'modal' dialog
		super(parent, true);

		//sets title to "rent a dvd"
		setTitle("Rent a DVD:");

		//not closed
		closeStatus = false;

		//sets size
		setSize(400, 200);

		//calls DVD d
		unit = d;

		// allows user to close window
		setDefaultCloseOperation
		(WindowConstants.DO_NOTHING_ON_CLOSE);

		//sets panel
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6, 2));

		//adds the name label and input box to panel
		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("John Doe", 30);
		textPanel.add(renterTxt);

		//adds the title label and input box to panel
		textPanel.add(new JLabel("Title of DVD:"));
		titleTxt = new JTextField("Avengers", 30);
		textPanel.add(titleTxt);


		//creating a gregorian calendar
		c = new GregorianCalendar();

		//making the calendar appear in a simple format
		DateFormat dateFormat = new 
				SimpleDateFormat("MM/dd/yyyy");

		//date is getting the time of the calendar
		Date date = c.getTime();

		//this is what prints on the side
		textPanel.add(new JLabel("Rented on: "));

		//this is the box where they can input date
		rentedOnTxt = new 
				JTextField(dateFormat.format(date), 30);

		//this is what adds the box to the panel
		textPanel.add(rentedOnTxt);

		//adding one day to the calendar for due back
		c.add(c.DAY_OF_YEAR, 1);

		//getting the time after the addition
		date = c.getTime();

		//this is printed on the panel
		textPanel.add(new JLabel("Due back: "));

		//this is what shows up in the due back box
		dueBackTxt = new 
				JTextField(dateFormat.format(date), 30);

		//this puts the due back box on the panel
		textPanel.add(dueBackTxt);

		//this adds the entire boxes to the whole pane 
		getContentPane().
		add(textPanel, BorderLayout.CENTER);

		// Instantiate and display two buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		//instantiates and declares panel for buttons 
		JPanel buttonPanel = new JPanel();

		//adds the buttons to the button panel 
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		// adds the button panel to the panel
		getContentPane().
		add(buttonPanel, BorderLayout.SOUTH);

		//adds the actionlisteners to buttons
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		//sets overall size and for it to be visible 
		setSize(300, 300);
		setVisible(true);
	}

	/*****************************************************************
	 * This method gives creates commands for the ok and 
	 * cancel button
	 * 
	 * @param e the actionevent
	 * @return none
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		//creates name for if clicked
		JButton button = (JButton) e.getSource();

		//if the cancel button is pressed, it will close
		if(button == cancelButton) {
			dispose();
			disposed = true;
		}

		// if OK clicked 
		if(button == okButton) {

			//if a box is empty then a dialog box appears
			if(renterTxt.getText() == null || titleTxt.getText() 
					== null || rentedOnTxt.getText() == null || 
					dueBackTxt.getText() == null) {
				JOptionPane.showMessageDialog(null, "You missed "
						+ "a box. Please input all values");
			}

			// if all boxes are full
			else {

				//changes boolean status
				closeStatus = true;

				//checks for the name
				unit.setNameOfRenter(renterTxt.getText());

				//gets the naem of the title
				unit.setTitle(titleTxt.getText());

				//calls to check the dates for validity
				checkRentedOnDate(rentedOnTxt.getText());
				checkDueBackDate(dueBackTxt.getText());

				//if either do not pass the checks
				//error message appears
				if(parseRentedOn == false || parseDueBack == false){

					JOptionPane.showMessageDialog(frame, "Please "
							+ "input valid dates");
					parseRentedOn = false;
					parseDueBack = false;
				}

				//if both of them pass the checks then it will input
				else if(parseRentedOn == true &&
						parseDueBack == true) {

					//if the bought date is before the due back date 
					if(boughtDateDateForm.
							before(dueBackDateDateForm)) {

						// make the dialog disappear
						dispose();
						disposed = false;
					}

					//if the due back date is before the bought date 
					//then an error message appears
					else {	
						JOptionPane.showMessageDialog(frame, "The "
								+ "return date should be after bought "
								+ "date");
					}
				}

			}
		}
	}

	/*****************************************************************
	 * This method checks for negatives in the rented on date
	 * 
	 * @param date the string form of the rented on date
	 * @return none
	 *****************************************************************/
	private void checkRentedOnDate(String date) {

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

			//this is checking to make sure that the rented on date 
			//uses positive numbers
			if(monthAttempt > 0 && dayAttempt > 0 && 
					yearAttempt > 0) {
				parseRentedOn();
			}
		}

		//catches any parsing problems and asks the user to 
		//input a valid date
		catch(Exception e1) {

		}
	}

	/*****************************************************************
	 * This method checks for negatives in the due date
	 * 
	 * @param date the string form of the due date
	 * @return none
	 *****************************************************************/
	private void checkDueBackDate(String date) {

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

			//this is checking to make sure that the due back 
			//date uses positive numbers
			if(monthAttempt > 0 && dayAttempt > 0 && 
					yearAttempt > 0) {
				parseDueBack();
			}

		}

		//catches any parsing problems and asks the user to 
		//input a valid date
		catch(Exception e1) {
			e1.printStackTrace();
		}

	}

	/*****************************************************************
	 * This method checks that the rented on date is valid
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void parseRentedOn() {
		//this takes the string form of the due back input
		String boughtDate = rentedOnTxt.getText();

		//this checks for invalid dates
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);

		//must surround with a try-catch block
		try {

			//this takes the string form and parses it 
			//into a date form
			boughtDateDateForm = new SimpleDateFormat("MM/dd/yyyy").
					parse(boughtDate);

			//this checks for invalid dates
			df.parse(boughtDate);

			//this takes the date form and turns it into
			//a gregorian calendar form
			gregorianCalendarBought = (GregorianCalendar)
					GregorianCalendar.getInstance();
			gregorianCalendarBought.setTime(boughtDateDateForm);

			// this puts the gregorian calendar form and 
			//puts it into the unit
			unit.setBought(gregorianCalendarBought);

			parseRentedOn = true;

		} 

		//this happens if the user inputs something that 
		//isn't a date into the system 
		catch (Exception e1) {
			parseRentedOn = false;
		}
	}

	/*****************************************************************
	 * This method checks that the rented on date is valid
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void parseDueBack() {
		//this takes the string form of the due back input
		String dueBack = dueBackTxt.getText();

		//this checks for invalid dates
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);

		//must surround with a try-catch block
		try {

			//this takes the string form and parses 
			//it into a date form
			dueBackDateDateForm = new SimpleDateFormat("MM/dd/yyyy").
					parse(dueBack);

			//this checks for invalid dates
			df.parse(dueBack);

			//this takes the date form and turns 
			//it into a gregorian calendar form
			gregorianCalendarDue = (GregorianCalendar)
					GregorianCalendar.getInstance();
			gregorianCalendarDue.setTime(dueBackDateDateForm);

			// this puts the gregorian calendar form and 
			//puts it into the unit
			unit.setDueBack(gregorianCalendarDue);
			parseDueBack = true;
		}

		//this happens if the user inputs something that 
		//isn't a date into the system 
		catch (Exception e1) {	
			parseDueBack = false;
		}
	}

	/*****************************************************************
	 * This method returns the status of the dialog box
	 * 
	 * @param none
	 * @return closeStatus the status of the dialog box
	 *****************************************************************/
	public boolean closeOK() {
		return closeStatus;
	}

}
