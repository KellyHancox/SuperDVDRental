package project4;

import java.util.*;
import javax.swing.*;

import project4.RentLateDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/********************************************************************
 * This class creates the interface for the late rentals
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ********************************************************************/
public class LateRentalsGUI extends JFrame implements 
ActionListener{
	
	/** creates the panel for the GUI */
	private  JPanel panel;
	
	/** creates the frame for the GUI */
	private  JFrame frame;
	
	/** creates the label asking for what is being displayed */
	private  JLabel label;
	
	/** a button that closes the application */
	private  JButton okButton;
	
	/** the local list of the late DVDs */
	private ArrayList<String> lateList;
	
	/** the JList of late DVDs */
	private JList<String> displayList;
	
	/** the scrollpane the JList is placed on */
	private JScrollPane scrollPane;
	
	/*****************************************************************
	 * Constructor displays the GUI
	 * 
	 * @param none
	 *****************************************************************/
	public LateRentalsGUI() {
		
		//instantiating the arraylist
		lateList = new ArrayList<String>();
		
		//this for loop parses through the list of late 
		//DVDs in the RentLateDialog and copies them 
		for(int i = 0; i < RentLateDialog.lateListInput.size(); 
				i++) {
			lateList.add(RentLateDialog.lateListInput.get(i));
		}
		
		//this takes the arraylist and turns it into a JList
		displayList = new JList<>(lateList.toArray(new String[0]));
		
		//instantiates the scrollpane with the JList
		scrollPane = new JScrollPane(displayList);
		
		//instantiates the panel and frame
		panel = new JPanel();
		frame = new JFrame();
		
		//makes the label print the date
		label = new JLabel("These items are late after " + 
		RentLateDialog.input.getText() + ":");
		
		//makes the button say ok and adds actionlistener
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		
		//makes it close automatically
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//adds the label, scrollpane, button to the panel
		panel.add(label);
		panel.add(scrollPane);
		panel.add(okButton);
		
		//adds the panel to the frame
		frame.add(panel);
		
		//packs and sets it visible
		frame.pack();
		frame.setVisible(true);
		
		//after the GUI displays, clear the arraylist 
		//this is so they don't all add 
		RentLateDialog.lateListInput.clear();
	}

	/*****************************************************************
	 * This method causes the frame to close upon clicking the ok 
	 * button 
	 * 
	 * @param ActionEvent
	 * @return none
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {
		
		//when the button is pressed, the frame will close
		if (e.getSource() == okButton) {
			frame.dispose();
		}
	}
	
}
