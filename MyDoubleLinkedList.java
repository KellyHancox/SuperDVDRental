package project4;

import java.io.Serializable;
import java.util.ArrayList;

import project4.DNode;
import project4.DVD;

/************************************************************************
 * This class keeps track of items in a linked list and performs various
 * functions on them.
 * 
 * @author Kelly Hancox and Isfar Baset
 * @version July 25, 2018
 ************************************************************************/
public class MyDoubleLinkedList<T> implements Serializable {

	/** This is a compononent of the Serializable class */
	private static final long serialVersionUID = 1L;

	/** holds the first node in the list */
	private DNode<T> top;

	/** holds the ast node in the list */
	private DNode<T> tail;

	/** holds the current index of a node */
	private DNode<T> currentIndex;

	/*****************************************************************
	 * Constructor sets the top and tail instance variables to 
	 * initial values
	 * 
	 * @param none
	 ****************************************************************/
	public MyDoubleLinkedList() {
		// initially the first and last node contain nothing 
		top = null;
		tail = null;
	}

	/*****************************************************************
	 * This methods finds the size of the lisnked list
	 * 
	 * @param none
	 * @return none
	 ****************************************************************/
	public int size() {
		// a variable to keep count of the nodes 
		int count = 0;
		// a new variable is set to be the first node 
		DNode<T> temp = top;
		while (temp != null) {
		    // count increments every time the first node isn't null 
			count++;
			// then the temp variable is set to the next node 
			temp = temp.getNext();
		}
        // the size of the list is returned 
		return count;
	}

	/*****************************************************************
	 * This method clears out the linked list
	 * 
	 * @param none
	 * @return none
	 ****************************************************************/
	public void clear() {
	   // remove all items
		top = null;
		tail = null;
	}

	/*****************************************************************
	 * This method adds an item after a node in the linked list
	 * 
	 * @param the data t of type T
	 * @return none
	 ****************************************************************/
	public void add(T t) {
		// add after
		// if first node is null 
		if (top == null) {
			// a new node is made 
			DNode<T> temp = new DNode<T>(t, null, null);
			// the first node is set to temp 
			top = temp;
			// the last node is set to temp 
			tail = temp;
		}
        // if the first node isn't null
		else {
			// a new node is made 
			DNode<T> extra = new DNode<T>(t, null, tail);
			// the new node is set to be the next node from the 
			// tail node 
			tail.setNext(extra);
			// this new node becomes the last node in the list 
			tail = extra;
		}
	}

	/*****************************************************************
	 * This method adds a node at the top of the list in a linked list
	 * 
	 * @param the data t of type T
	 * @return none
	 ****************************************************************/
	public void addFirst(T t) {
		// add at the top.
		// a new node is made 
		DNode<T> temp = new DNode<T>(t, null, null);
		
		// temp node is added to the top of the list 
		top = temp;
		tail = temp;
		return;
	}

	/*****************************************************************
	 * This methods gets rids of the first occurrence of a 
	 * particular node
	 * 
	 * @param index of the node
	 * @return none
	 * @throws IllegalArgumentException 
	 ****************************************************************/
	public void remove(int index) {
		// remove first occurrence.

		// if index is at the beginning
		if (index == 0) {
			top = top.getNext();
		}

		// if index is at the end
		else if (index == (size()) - 1) {

			DNode<T> newTail = new DNode<T>();
			newTail = tail.getPrevious();
			newTail.setPrevious(tail.getPrevious().getPrevious());

			tail = newTail;
			tail.setNext(null);

		}
        
		// if index is in the middle
		else if (index > 0 && index < size()) {

			int count = 0;
			DNode<T> currNode = top;
			DNode<T> prevNode = top;

			while (index != count) {
				prevNode = currNode;
				currNode = currNode.getNext();

				count++;
			}
            
			// setting previous node 
			prevNode.setNext(currNode.getNext());
			// setting current node 
			currNode.getNext().setPrevious(prevNode);

		}

		else {
			throw new IllegalArgumentException();
		}

	}

	/*****************************************************************
	 * This methods gets rid of all the occurrences of the node that
	 * matches the data
	 * 
	 * @param data t of type T
	 * @return true if all was removed, false otherwise 
	 ****************************************************************/
	public boolean removeAll(T t) {
		// remove all the ones that match
        // boolean returns true if you were able to 
		// successfully remove it
		boolean removedOne = false;

		DNode<T> currentNode = top;
		
		//loops thorugh the linked list 
		for (int i = 0; i < size(); i++) {
			// if current node holds the wanted data
			if (currentNode.getData() == t) {
				remove(i);
				removedOne = true;
			}

			currentNode = currentNode.getNext();
		}

		return removedOne;
	}

	/*****************************************************************
	 * This method retrieves the data from a particular index
	 * 
	 * @param index of the node
	 * @return the data in the node
	 ****************************************************************/
	public T get(int index) {
        
		// if index is out of bounds 
		if (index < 0 || index >= size()) {
			return null;
		}
        
		// if index is within bounds 
		else {
			DNode<T> temp = top;
			for (int i = 0; i < index; i++) {
				temp = temp.getNext();
			}
			return temp.getData();
		}
	}

	/*****************************************************************
	 * This method finds the index from the given data in a node
	 * 
	 * @param data in the node
	 * @return index of the node
	 ****************************************************************/
	public int find(T t) {
	   
		int returnedIndex = -1;
		
		// return index if found, -1 otherwise
		DNode<T> temp = top;
		// loops thorugh the whole list 
		for (int i = 0; i < size(); i++) {
			// if the required data is found 
			if (temp.getData() == t) {
				returnedIndex = i;
				return returnedIndex;
			}
			temp = temp.getNext();
		}
		return returnedIndex;
	}

	/*****************************************************************
	 * This method returns the String form of the data contained 
	 * in a node
	 * 
	 * @param none
	 * @return String form of the data
	 ****************************************************************/
	public String toString() {
		String str = "Forward: ";
		// loops though the whole list 
		for (int i = 0; i < size(); i++) {
			// returns the String form of the forward nodes 
			str += get(i).toString();
		}

		str += "\nBackward: ";
		// loops thorugh the whole list 
		for (int i = size() - 1; i >= 0; i--) {
			// returns the String form of the backward nodes
			str += get(i).toString();
		}

		return str;
	}
}


