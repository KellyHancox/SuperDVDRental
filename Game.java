package project4;

import java.util.GregorianCalendar;
import java.io.Serializable;

/********************************************************************
 * This class finds the information about the type of console 
 * used to play the game and the cost of renting a game.
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 *******************************************************************/
public class Game extends DVD implements Serializable {

	/** Represents the type of player */
	private PlayerType player; 

	/** This is a part of Serializable */
	private static final long serialVersionUID = 1L;

	/****************************************************************
	 * This method sets the name of the gaming console
	 * 
	 * @param type of gaming console
	 * @return none
	 ****************************************************************/
	protected void setPlayerType(PlayerType gameSystem) {
		// put it in the dialog
		this.player = gameSystem;
	}

	/****************************************************************
	 * This method returns the name of the gaming console
	 * 
	 * @param none
	 * @return type of gaming console
	 ****************************************************************/
	public PlayerType getGamePlayerType() {
		return player;
	}

	/****************************************************************
	 * This method returns the cost of renting a game
	 * 
	 * @param date that is being returned on
	 * @return cost of renting game
	 ****************************************************************/
	public double getCost(GregorianCalendar date) {

		// condition if the game is returned late
		if (date.after(dueBack)) {
			cost = 10.0 + 5.0;
		}
		// if the game is returned on time
		else {
			cost = 5.0;
		}

		return cost;
	}
	
	public String toString() {
		String line =  "Name: " + " " + getNameOfRenter();
		line += "    Title: " + " " + getTitle();	
		line += "    Rented On: " + " " + getBoughtString();
		line += "    Due Back On: " + " " + getDueBack();
		line += "    Console: " + getGamePlayerType();
		
		return line;
	}

}


