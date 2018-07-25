package project4;

import javax.swing.*;

import project4.Game;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

/************************************************************************
 * This class is the dialog box where the user inputs the information 
 * regarding renting a game
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ************************************************************************/
public class RentGameDialog extends JDialog implements ActionListener {

	/** Input box for the title of the title of the game */
	private JTextField titleTxt;

	/** Input box for the name of the renter */
	private JTextField renterTxt;

	/** Input box for the date on which the game was rented */
	private JTextField rentedOnTxt;

	/** Input box for the date for when the game is due back */
	private JTextField dueBackTxt;

	/** Input box for the type of the game */
	//	private JTextField gameTypeTxt;
	private JComboBox jbox;

	/** This is the OK button */
	private JButton okButton;

	/** This is the Cancel button */
	private JButton cancelButton;

	/** This is the frame of the dialog box gui */
	private JFrame frame;

	/** Represents the type of gaming console */
	private PlayerType player;

	/** Represents if the gaming console is valid or not */
	private boolean validPlayerType;

	/** Represents if the due back date was correctly parsed or not */
	private boolean parseDueBack;

	/** Represents if the rented date was correctly parsed or not */
	private boolean parseRentedOn;

	/** Represents if the dialog box should close or nor */
	public static boolean disposed;

	/** Represents the date when a game was bought */
	private Date boughtDateDateForm;

	/** Represents the date when a game is due back */
	private Date dueBackDateDateForm;

	/** Represents the date in GregorianCalendar form */
	private GregorianCalendar gregorianCalendar;

	/** Represents the date in Calendar form */
	private Calendar c;

	/** Represents the game object */
	private Game game;


	/*****************************************************************
	 * Constructor instantiates the GUI components and displays the 
	 * GUI for the dialog box for renting a game
	 * 
	 * @param parent JFrame and Game object
	 ****************************************************************/
	public RentGameDialog(JFrame parent, Game g) {

		// call parent and create a 'modal' dialog
		super(parent, true);

		// set title on the frame
		setTitle("Rent a DVD:");

		// set size of the frame
		setSize(400, 200);

		game = g;

		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// instantiate and display text fields
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6, 2));

		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("T Haas", 30);
		textPanel.add(renterTxt);

		textPanel.add(new JLabel("Title of Game:"));
		titleTxt = new JTextField("Call of Duty", 30);
		textPanel.add(titleTxt);

		textPanel.add(new JLabel("Type of Game System: "));
		jbox = new JComboBox(PlayerType.values());
		textPanel.add(jbox);

		jbox.addActionListener(this);


		// creating a gregorian calendar
		c = new GregorianCalendar();

		// making the calendar appear in a simple format
		DateFormat dateFormat = 
				new SimpleDateFormat("MM/dd/yyyy");

		// date is getting the time of the calendar
		Date date = c.getTime();

		// this is what prints on the side
		textPanel.add(new JLabel("Rented on: "));

		// this is the box where they can input date
		rentedOnTxt = 
				new JTextField(dateFormat.format(date), 30);

		// this is what adds the box to the panel
		textPanel.add(rentedOnTxt);

		// adding one day to the calendar for due back
		c.add(c.DAY_OF_YEAR, 1);

		// getting the time after the addition
		date = c.getTime();

		// this is printed on the panel
		textPanel.add(new JLabel("Due back: "));

		// this is what shows up in the due back box
		dueBackTxt = 
				new JTextField(dateFormat.format(date), 30);

		// this puts the due back box on the panel
		textPanel.add(dueBackTxt);

		getContentPane().add(textPanel, BorderLayout.CENTER);

		// Instantiate and display two buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		setSize(300, 300);
		setVisible(true);
	}

	/*****************************************************************
	 * This method causes the frame to close upon clicking the ok 
	 * button or if cancel is clicked, the frame also closes
	 * 
	 * @param ActionEvent
	 * @return none
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		// If cancel button is clicked
		if (e.getSource() == cancelButton) {
			dispose();
			disposed = true;
		}


		// If OK clicked then fill the object
		else if (e.getSource() == okButton) {

			//if one input box is left empty
			if(renterTxt.getText() == null || titleTxt.getText()== null 
					|| rentedOnTxt.getText() == null
					|| dueBackTxt.getText() == null) {
				JOptionPane.showMessageDialog(null, 
						"You missed a box. "
								+ "Please input all values");
			}

			else {
				// save the information in the object
				game.setNameOfRenter(renterTxt.getText());
				game.setTitle(titleTxt.getText());

				//set the playertype by changing the jbox
				//item into a string
				setPlayerType(jbox.getSelectedItem().toString()); 
				

				// Show warnings if the gaming console type
				// is invalid 
				if (validPlayerType == false) {
					JOptionPane.showMessageDialog(frame, 
							"Please input a valid console from "
									+ "the options:\nXbox360, XBox1, PS4," 
									+ " WiiU, and NintendoSwitch");
				}

				checkRentedOnDate(rentedOnTxt.getText());
				checkDueBackDate(dueBackTxt.getText());

				// If date is not parsed correctly 
				if (parseRentedOn == false 
						|| parseDueBack == false) {
					JOptionPane.showMessageDialog(frame, 
							"Please input valid dates");
					parseRentedOn = false;
					parseDueBack = false;
				}

				// If everything is done correctly 
				if (parseRentedOn == true && parseDueBack == true 
						&& validPlayerType == true) {
					if (boughtDateDateForm.
							before(dueBackDateDateForm)) {
						// make the dialog disappear
						dispose();
						disposed = false;
					}

					// If return date is before the due date  
					else {
						JOptionPane.showMessageDialog(frame, 
								"The return date " + 
								"should be after bought date");
					}
				}
			}
		}
	}

	/*****************************************************************
	 * This method checks if the rented on date is valid and in the 
	 * right form 
	 * 
	 * @param input date 
	 * @return none
	 *****************************************************************/
	private void checkRentedOnDate(String date) {
		String[] dates = date.split("/");
		int monthAttempt = 0;
		int dayAttempt = 0;
		int yearAttempt = 0;

		try {
			//This parses the month to an Integer
			monthAttempt = Integer.parseInt(dates[0]);

			//This parses the day to an Integer
			dayAttempt = Integer.parseInt(dates[1]);

			//This parses the year to an Integer
			yearAttempt = Integer.parseInt(dates[2]);

			// this is checking to make sure that the rented on 
			// date uses positive numbers
			if (monthAttempt > 0 && dayAttempt > 0 
					&& yearAttempt > 0) {
				parseRentedOn();
			}
		} catch (Exception e1) {

		}
	}

	/*****************************************************************
	 * This method checks if the due back date is valid and in the 
	 * right form 
	 * 
	 * @param input date 
	 * @return none
	 *****************************************************************/
	private void checkDueBackDate(String date) {
		String[] dates = date.split("/");
		int monthAttempt = 0;
		int dayAttempt = 0;
		int yearAttempt = 0;

		try {
			//This parses the month to an Integer
			monthAttempt = Integer.parseInt(dates[0]);

			//This parses the day to an Integer
			dayAttempt = Integer.parseInt(dates[1]);

			//This parses the year to an Integer
			yearAttempt = Integer.parseInt(dates[2]);

			// this is checking to make sure that the due back 
			//date uses positive numbers
			if (monthAttempt > 0 && dayAttempt >
			0 && yearAttempt > 0) {
				parseDueBack();
			}

		} catch (Exception e1) {
		}

	}

	/*****************************************************************
	 * This method checks if the rented on date is valid and in the 
	 * right form 
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void parseRentedOn() {
		// this takes the string form of the due back input
		String boughtDate = rentedOnTxt.getText();

		// This checks for invalid dates 
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);

		// must surround with a try-catch block
		try {

			// this takes the string form and parses it into a 
			//date form
			boughtDateDateForm = 
					new SimpleDateFormat("MM/dd/yyyy").
					parse(boughtDate);

			// This checks for invalid dates 
			df.parse(boughtDate);

			// this takes the date form and turns it into a 
			//GregorianCalendar form
			gregorianCalendar = 
					(GregorianCalendar) GregorianCalendar.
					getInstance();
			gregorianCalendar.setTime(boughtDateDateForm);

			// this puts the gregorian calendar form and puts it 
			//into the unit
			game.setBought(gregorianCalendar);
			parseRentedOn = true;

		}

		// this happens if the user inputs something that isn't a 
		//date into the system
		catch (Exception e1) {
			parseRentedOn = false;
		}
	}

	/*****************************************************************
	 * This method parses the due back date from String to 
	 * GregorianCalendar
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void parseDueBack() {
		// this takes the string form of the due back input
		String dueBack = dueBackTxt.getText();

		// This checks for invalid dates 
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);

		// must surround with a try-catch block
		try {

			// this takes the string form and parses it into a 
			//date form
			dueBackDateDateForm = new SimpleDateFormat("MM/dd/yyyy").
					parse(dueBack);

			// This checks for invalid dates 
			df.parse(dueBack);

			// this takes the date form and turns it into a 
			//gregorian calendar form
			gregorianCalendar = (GregorianCalendar) 
					GregorianCalendar.getInstance();
			gregorianCalendar.setTime(dueBackDateDateForm);

			// this puts the gregorian calendar form and
			//puts it into the unit
			game.setDueBack(gregorianCalendar);
			parseDueBack = true;
		}

		// this happens if the user inputs something that 
		//isn't a date into the system
		catch (Exception e1) {
			parseDueBack = false;
		}
	}


	/*****************************************************************
	 * This method sets the type of gaming console used 
	 * 
	 * @param String input of type of gaming console 
	 * @return none
	 *****************************************************************/
	public void setPlayerType(String gameTypeTxt) {
		String temp = PlayerType.PS4.toString();
		String temp2 = PlayerType.NintendoSwitch.toString();
		String temp3 = PlayerType.XBox1.toString();
		String temp4 = PlayerType.Xbox360.toString();
		String temp5 = PlayerType.WiiU.toString();

		// if the gaming console is a PS4
		if (gameTypeTxt.equals(temp)) {
			PlayerType p = PlayerType.valueOf(temp);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;

			// if the gaming console is a NintendoSwitch
		} else if (gameTypeTxt.equals(temp2)) {
			PlayerType p = PlayerType.valueOf(temp2);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;

			// if the gaming console is a Xbox1
		} else if (gameTypeTxt.equals(temp3)) {
			PlayerType p = PlayerType.valueOf(temp3);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;

			// if the gaming console is a Xbox360
		} else if (gameTypeTxt.equals(temp4)) {
			PlayerType p = PlayerType.valueOf(temp4);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;

			// if the gaming console is a WiiU
		} else if (gameTypeTxt.equals(temp5)) {
			PlayerType p = PlayerType.valueOf(temp5);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;
		} else {
			validPlayerType = false;
		}
	}

}

