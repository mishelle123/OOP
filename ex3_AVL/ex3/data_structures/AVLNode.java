package clids.ex3.data_structures;

/**
 * Constructs a single node in the tree
 * 
 * @author mishelle
 *
 */
public class AVLNode {

	private AVLNode left, right, parent;
	private int data;

	/**
	 * constructs new node
	 * @param data
	 */
	public AVLNode(int data){
		this.data = data;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	/**
	 * default constructor
	 */
	public AVLNode(){

	}

	/**
	 * 
	 * @sets the data
	 */
	public void setData(int data){
		this.data = data;
	}
	/**
	 * 
	 * @return the left son of the node
	 */
	public AVLNode getLeft(){
		return this.left;
	}
	
	/**
	 * 
	 * @return the right son of the node
	 */
	public AVLNode getRight(){
		return this.right;
	}

	/**
	 * 
	 * @return the parent of the node
	 */
	public AVLNode getParent(){
		return this.parent;
	}
	
	/**
	 * 
	 * @sets the parent of the node
	 */
	public void setParent(AVLNode parent){
		this.parent = parent;
	}
	
	/**
	 * sets the left son of the node
	 * @param left - the new node
	 */
	public void setLeft(AVLNode left){
		this.left = left;
	}
	/**
	 * sets the right son of the node
	 * @param right - the new node
	 */
	public void setRight(AVLNode right){
		this.right = right;
	}
	/**
	 * 
	 * @return the node's data
	 */
	public int getData(){
		return this.data;
	}

}

