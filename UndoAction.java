package project4;

/************************************************************************
 * This class keeps track of the undo actions
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 25, 2018
 ************************************************************************/
public class UndoAction {

	/** holds the String value of the action */
	public String action;

	/** holds the dvd info */
	public DVD dvd;

	/*****************************************************************
	 * Constructor sets the instance variables to initial values
	 * 
	 * @param String of the action that was done and the dvd info
	 ****************************************************************/
	public UndoAction(String action, DVD dvd) {

		this.action = action;
		this.dvd = dvd;
	}

	/*****************************************************************
	 * This method retrieves the action that was performed
	 * 
	 * @param none
	 * @return String value of the action
	 ****************************************************************/
	public String getAction() {
		return action;
	}

	/*****************************************************************
	 * This method sets the action that was performed
	 * 
	 * @param String value of the action
	 * @return none
	 ****************************************************************/
	public void setAction(String action) {
		this.action = action;
	}

	/*****************************************************************
	 * This method retrieves the dvd info
	 * 
	 * @param none
	 * @return dvd info
	 ****************************************************************/
	public DVD getDvd() {
		return dvd;
	}

	/*****************************************************************
	 * This method sets the dvd info
	 * 
	 * @param dvd info
	 * @return none
	 ****************************************************************/
	public void setDvd(DVD dvd) {
		this.dvd = dvd;
	}

}

