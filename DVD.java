package project4;

import java.io.Serializable;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/************************************************************************
 * This class finds the information about the date on which a particular 
 * dvd was rented, when it is due back, who rented it and the cost of 
 * renting a dvd
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ************************************************************************/
public class DVD implements Serializable {

	/** This is a part of Serializable */
	private static final long serialVersionUID = 1L;

	/** Represents the date when a dvd was bought */
	protected GregorianCalendar bought;

	/** Represents when a dvd is due back */
	protected GregorianCalendar dueBack;

	/** Represents the title of a dvd */
	protected String title;

	/** Represents the name of the renter */
	protected String nameOfRenter;

	/** Represents the cost of a dvd */
	protected double cost;

	/*****************************************************************
	 * This method formats the date a dvd was bought 
	 * from a type Calendar to String
	 * 
	 * @param none
	 * @return formatted date
	 *****************************************************************/
	public String getBoughtString() {
		// creates the format we want to return the date in
		SimpleDateFormat myDateFormat = 
				new SimpleDateFormat("MM/dd/yyyy");

		// turns the date into a string
		String formattedDate = myDateFormat.
				format(bought.getTime());

		// changed from calendar to return a string
		return formattedDate;
	}

	/*****************************************************************
	 * This method returns the date a dvd was bought
	 * 
	 * @param none
	 * @return date a dvd was bought
	 *****************************************************************/
	public GregorianCalendar getBoughtDate() {
		return bought;
	}

	/*****************************************************************
	 * This method formats sets the date a dvd was bought
	 * 
	 * @param bought
	 *            date of dvd in GregorianCalendar form
	 * @return none
	 *****************************************************************/
	public void setBought(GregorianCalendar bought) {
		this.bought = bought;
	}

	/*****************************************************************
	 * This method formats the date a dvd is due
	 *  back from a type Calendar to String
	 * 
	 * @param none
	 * @return formatted due back date
	 *****************************************************************/
	public String getDueBack() {
		// creates the format we want to return the date in
		SimpleDateFormat myDateFormat = 
				new SimpleDateFormat("MM/dd/yyyy");

		// turns the date into a string
		String formattedDate = myDateFormat.
				format(dueBack.getTime());

		// changed this from calendar to string
		return formattedDate;
	}

	/*****************************************************************
	 * This method returns the due back date for a dvd
	 * 
	 * @param none
	 * @return date a dvd is due back
	 ****************************************************************/
	public GregorianCalendar getDueBackGregorianCalendar() {
		return dueBack;
	}

	/*****************************************************************
	 * This method a Date object representing 
	 * this Calendar's time value
	 * 
	 * @param none
	 * @return Date object
	 ****************************************************************/
	public Date getDueBackDate() {

		// finds the calendar's time value
		Date date = dueBack.getTime();
		return date;
	}

	/*****************************************************************
	 * This method sets the date for when a dvd is due back
	 * 
	 * @param dueback date in GregorianCalendar form
	 * @return none
	 *****************************************************************/
	public void setDueBack(GregorianCalendar dueBack) {
		this.dueBack = dueBack;
	}
	
	/*****************************************************************
	 * This method returns the title of the dvd
	 * 
	 * @param none
	 * @return title of dvd
	 *****************************************************************/
	public String getTitle() {
		return title;
	}

	/*****************************************************************
	 * This method sets the title of the dvd
	 * 
	 * @param title of the dvd in String form
	 * @return none
	 *****************************************************************/
	public void setTitle(String title) {
		this.title = title;
	}

	/*****************************************************************
	 * This method returns the name of the renter
	 * 
	 * @param none
	 * @return name of renter
	 ****************************************************************/
	public String getNameOfRenter() {
		return nameOfRenter;
	}
	
	/*****************************************************************
	 * This method sets the name of renter
	 * 
	 * @param name of renter
	 * @return none
	 ****************************************************************/
	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}

	/*****************************************************************
	 * This method calculates the cost of renting a dvd
	 * 
	 * @param date the dvd is being returned on
	 * @return cost of renting the dvd
	 ****************************************************************/
	public double getCost(GregorianCalendar date) {

		// checks if the dvd is being returned 
		//after the day it was due
		if (date.after(dueBack)) {

			// an extra $2 if the dvd was returned late
			cost = 1.2 + 2.0;
		} else {

			// the cost is $1.2 if returned on time
			cost = 1.2;
		}
		return cost;
	}
	
	public String toString() {
		String line =  "Name: " + " " + getNameOfRenter();
		line += "    Title: " + " " + getTitle();	
		line += "    Rented On: " + " " + getBoughtString();
		line += "    Due Back On: " + " " + getDueBack();
		
		return line;
	}
}

