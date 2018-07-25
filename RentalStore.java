package project4;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import java.io.*;
import java.util.*;

/********************************************************************
 * This class creates the rules for the rental store
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ********************************************************************/
public class RentalStore extends AbstractTableModel {
	
	/** holds the DVDs that are checked out*/
	public static MyDoubleLinkedList<DVD> listDVDs;
	public  MyDoubleLinkedList<UndoAction> undoList;
	
	
	public String[] columnNames;
	 JTable table;
	 DefaultTableModel tableModel;

	/*****************************************************************
	 * Constructor creates instantiates the arraylist of DVDs
	 * 
	 * @param none
	 * @return none
	 ****************************************************************/
	public RentalStore() {
		super();
		listDVDs = new  MyDoubleLinkedList<>();
		undoList = new MyDoubleLinkedList<UndoAction>();
		
		columnNames = new String[]{"First Name",
	            "Title",
	            "Rented on",
	            "Due Back"
	            ,"Player Type"};
		
		tableModel=new DefaultTableModel(columnNames,5);
		
		table = new JTable(0, 5);
	}

		@Override
	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    @Override
	    public String getColumnName(int column) {
	        return columnNames[column];
	    }

	    @Override
	    public int getRowCount() {
	        return listDVDs.size();
	    }

	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex) {
	       DVD a = listDVDs.get(rowIndex);
	       
	        if (a == null) {
	            return null;
	        }
	        
	        switch (columnIndex) {
	            case 0:
	                return a.getNameOfRenter();
	            case 1:
	                return a.getTitle();
	            case 2:
	                return a.getBoughtString();
	            case 3:
	                return a.getDueBack();
	            case 4:
	                if(a instanceof Game) {
	                return ((Game)a).getGamePlayerType();
	                }
	                else {
	                	return null;
	                }
	    
	            default:
	                return null;
	        }
	    }

	

	/*****************************************************************
	 * Adds a DVD to the arraylist
	 * 
	 * @param DVD a DVD with DVD information
	 * @return none
	 ****************************************************************/
	public void add (DVD a) {
		
		//adds the dvd to the arraylist
		listDVDs.add(a);
		
		//continuously updates the arraylist
		//fireIntervalAdded(this, 0, listDVDs.size());
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
		
		undoList.add(new UndoAction("add", a));
		System.out.println("after adding to the list " + undoList.size());

	}

	/*****************************************************************
	 * Deletes a DVD from the linkedList
	 * 
	 * @param a DVD with DVD information
	 * @return none
	 ****************************************************************/
	public void delete (DVD a) {
		//sorts through the list of DVDs and deletes the correct DVD
		for(int i = 0; i < listDVDs.size(); i++) {
			if(a ==  listDVDs.get(i)) {
				listDVDs.remove(i);
				fireTableRowsDeleted(i, i);
				
				undoList.add(new UndoAction("remove", a));

				System.out.println("after returning an item " + undoList.size());
			}
		}
	}

	/*****************************************************************
	 * Deletes a DVD from the arraylist
	 * 
	 * @param i integer to get from the arraylist
	 * @return DVD at the selected index
	 ****************************************************************/
	public DVD get (int i) {
		return listDVDs.get(i);
	}

	/*****************************************************************
	 * This method gathers the information for a DVD into a string
	 * to print out
	 * 
	 * @param arg0 item that was selected
	 * @return line the string with the information 
	 ****************************************************************/
	public static Object getElementAt(int arg0) {	

		//creates a local DVD unit 
		DVD unit = listDVDs.get(arg0);

		//uses the unit to get the information for a string
		String line = "Name: " + " " + listDVDs.get(arg0).getNameOfRenter();
		line += "    Title: " + " " + listDVDs.get(arg0).getTitle();	
		line += "    Rented On: " + " " + listDVDs.get(arg0).getBoughtString();
		line += "    Due Back On: " + " " + listDVDs.get(arg0).getDueBack();

		//gets the gamesystem of games
		if (unit instanceof Game) {
			line += "    Console: " + ((Game)unit).getGamePlayerType();
		}

		return line;
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
	 * @param filename name of file
	 * @return none
	 ****************************************************************/
	public void saveAsSerializable(String filename) {
		
		//attempts to save the linkedList
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(listDVDs);
			os.close();
			
//			  ObjectIOExample objectIO = new ObjectIOExample();
//
////			  for(int i = 0; i < listDVDs.size(); i++) {
////		        DVD d = new DVD();
////		       // d = listDVDs.get(i);
//		        objectIO.WriteObjectToFile(filename, listDVDs);
//			  }
//
//		        DVD dvd = (DVD) objectIO.ReadObjectFromFile(filename);
		        
		}
		
		//displays error message if the file does not work
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error in saving db");
		}
	}
	
	
//    public void WriteObjectToFile(String filepath,Object serObj) {
//    	
//        try {
//            FileOutputStream fileOut = new FileOutputStream(filepath);
//
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//
//            objectOut.writeObject(serObj);
//
//            objectOut.close();
//            System.out.println("The Object  was succesfully written to a file");
//        	
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//
//        }
//
//    }

	/*****************************************************************
	 * This method loads the list of DVDs from a saved file
	 * 
	 * @param filename name of file
	 * @return none
	 ****************************************************************/
	public void loadFromSerializable(String filename) {
		
//		//attempts to read the file and display it
//		try {
////			FileInputStream fis = new FileInputStream(filename);
////			ObjectInputStream is = new ObjectInputStream(fis);
////
////			listDVDs = (MyDoubleLinkedList<DVD>) is.readObject();
////			fireTableDataChanged();
////			is.close();
//			
//		}
//		
//		//displays error message if loading does not work 
//		catch (Exception ex) {
//			JOptionPane.showMessageDialog(null,"Error in loading db");
//		}
		
		
		 try {

	            FileInputStream fileIn = new FileInputStream(filename);
	            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	
	            listDVDs = (MyDoubleLinkedList<DVD>) objectIn.readObject();
	            undoList = new MyDoubleLinkedList<UndoAction>();
	            objectIn.close();
	            

	
	        } catch (Exception ex) {
	
	          ex.printStackTrace();
	
	            //return null;
	
	        }
	}
	
	/*****************************************************************
	 * This method saves the list of DVDs
	 * 
	 * @param filename name of file
	 * @return none
	 ****************************************************************/
	public void saveAsTextFile(String filename) {
		try {
	//	PrintWriter writer = new PrintWriter(filename);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));

		//iterate over list 
		for(int i = 0; i < listDVDs.size(); i++) {
		    out.writeObject(listDVDs.get(i));
		}
		
		out.close();
		}
		
		catch(Exception e) {
			System.out.println("Error saving into db");
		}
	}
	
	public void addFromUndo(DVD a) {

		// adds the dvd to the arraylist
		listDVDs.add(a);
	
		  // continuously updates the arraylist
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}

	public void deleteFromUndo(DVD a) {
		for (int i = 0; i < listDVDs.size(); i++) {
			if (a == listDVDs.get(i)) {
				listDVDs.remove(i);
				fireTableRowsDeleted(i, i);	
			}
		}
	}
	
public void undoAdd() {
		
		if(undoList.size() <= 0) {
			throw new NullPointerException();
		}
		
		else {
		UndoAction lastAction = undoList.get(undoList.size() - 1);
		
		if (lastAction.getAction() == "add") {
			deleteFromUndo(lastAction.getDvd());
			undoList.remove(undoList.size()-1);
			System.out.println("after removing from undo list " + undoList.size());
		}

		else if (lastAction.getAction() == "remove") {
			addFromUndo(lastAction.getDvd());
			undoList.remove(undoList.size()-1);
			System.out.println("after adding from undo list " + undoList.size());
			}
		}
	}

	
	/*****************************************************************
	 * This method loads the list of DVDs from a saved file
	 * 
	 * @param filename name of file
	 * @return none
	 ****************************************************************/
	public void loadFromTextFile(String filename) {
		
		//attempts to read the file and display it
		try {
			
			  BufferedReader br = null;
		      br = new BufferedReader(new FileReader(filename));
		            
			ArrayList<String>list =new ArrayList<String> ();
			String currentLine;
			
			while ((currentLine = br.readLine()) != null) {
			    list.add(currentLine);
			    System.out.println(currentLine);
			} 
			
			MyDoubleLinkedList<DVD> listDVDs = new MyDoubleLinkedList<DVD>();
			
			for(int i = 0; i < list.size(); i++) {
				//listDVDs.add(list.get(i));
				
			}
			
			br.close() ;
			
		}
		
		//displays error message if loading does not work 
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error in loading db");
		}
	}

	public Object getTableHeader() {
		// TODO Auto-generated method stub
		columnNames = new String[]{"First Name",
	            "Title",
	            "Rented on",
	            "Due Back"
	            ,"Player Type"};
		return columnNames;
	}
}

