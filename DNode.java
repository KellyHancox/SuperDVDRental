package project4;

/**********************************************************************
 * This is the node class that keeps track of the index of nodes in 
 * the linked list
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 25, 2018
 *********************************************************************/
public class DNode<T> {

	/** this is the contained information in a position */
	private T data;

	/** this holds the next node from a position */
	private DNode<T> next;

	/** this holds the previous node from a position */
	private DNode<T> previous;

	/*****************************************************************
	 * Constructor sets the instance variables to initial values
	 * 
	 * @param the data that's in the node, the next node and the 
	 * previous node
	 * @return none
	 ****************************************************************/
	public DNode(T data, DNode<T> next, DNode<T> previous) {
		this.data = data;
		this.next = next;
		this.previous = previous;

	}

	/*****************************************************************
	 * This is an empty constructor
	 * 
	 * @param none
	 * @return none
	 ****************************************************************/
	public DNode() {

	}

	/*****************************************************************
	 * This method retrieves the data from a node
	 * 
	 * @param none
	 * @return data contained in the node
	 ****************************************************************/
	public T getData() {
		return data;
	}

	/*****************************************************************
	 * This method sets the data in a node
	 * 
	 * @param the data that's in the node
	 * @return none
	 ****************************************************************/
	public void setData(T data) {
		this.data = data;
	}

	/*****************************************************************
	 * This method sets the next node from a node at a particular 
	 * position
	 * 
	 * @param the next node
	 * @return none
	 ****************************************************************/
	public void setNext(DNode<T> next) {
		this.next = next;
	}

	/*****************************************************************
	 * This method retrieves the next node from a node at a 
	 * particular position
	 * 
	 * @param none
	 * @return none
	 ****************************************************************/
	public DNode<T> getNext() {
		return next;
	}

	/*****************************************************************
	 * This method sets the previous node from a node at a 
	 * particular position
	 * 
	 * @param the previous node
	 * @return none
	 ****************************************************************/
	public void setPrevious(DNode<T> previous) {
		this.previous = previous;
	}

	/*****************************************************************
	 * This method retrieves the previous node from a node at a 
	 * particular position
	 * 
	 * @param none
	 * @return none
	 ****************************************************************/
	public DNode<T> getPrevious() {
		return previous;
	}

}

