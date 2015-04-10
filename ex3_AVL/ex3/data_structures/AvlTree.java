package clids.ex3.data_structures;


/**
 * Creates an AVL tree: self-balancing binary search tree.
 * 
 * @author mishelle
 */
public class AvlTree {


	private static final int NOT_CONTAINS = -1;
	private static final int LEFT_SUBTREE_BALANCE = -2;
	private static final int RIGHT_SUBTREE_BALANCE = 2;
	private static final int NODE_IS_NULL = -1;
	private static final int NODE_HAS_NO_SONS = 0;
	private AVLNode root;
	private int size;

	/**
	 * A default constructor.
	 */
	public AvlTree(){
		this.root = null ;
		this.size = 0;
	}

	/**
	 * A data constructor
	 * a constructor that builds the tree by adding the elements in the input array one-by-one.
	 * If the same value appears twice (or more) in the list, it is ignored.
	 *
	 * @param data values to add to tree
	 */
	public AvlTree(int[] data){
		if(data==null){
			this.root = null;
		}
		// runs over the array and insert the different elements
		for(int i=0 ; i< data.length ; i++){
			add(data[i]);
		}
	}

	/**
	 * @return number of nodes in the tree
	 */
	public int size(){
		return this.size;
	}

	/**
	 * Add a new node with key newValue into the tree.
	 *
	 * @param newValue new value to add to the tree.
	 * @return false if newValue already exist in the tree
	 *
	 */
	public boolean add(int newValue){
		// return false if newValue already exist in the tree
		if(contains(newValue)!=(NOT_CONTAINS)){
			return false;
		}
		// insert the node if the value is not in the tree
		AVLNode toInsert = new AVLNode(newValue);
		simpleInsert(this.root, toInsert);
		this.size++;
		return true;
	}


	/**
	 * Does tree contain a given input value.
	 *
	 * @param val value to search for.
	 * @return if val is found in the tree, return the depth of its node (where 0 is the root).
	 * Otherwise  return -1.
	 */
	public int contains(int searchVal) {
		// calls to contains help method
		return recursiveContains(this.root, searchVal ,0);
	}


	/**
	 * Remove a node from the tree, if it exists.
	 *
	 * @param toDelete value to delete
	 * @return true if toDelete is found and deleted
	 */
	public boolean delete(int toDelete){

		if(contains(toDelete)!=(NOT_CONTAINS)){
			// call delete help method
			findToDelete(toDelete, this.root);
			this.size--;
			return true;
		}
		// return false if newValue already exist in the tree
		return false;
	}
	
	
	/*
	 * contains helper method- search for the value
	 */
	private int recursiveContains(AVLNode node, int searchVal, int depth){
		// returns -1 if the value is not in the tree
		if(node==null){
			return NOT_CONTAINS;
		}
		// if the searchVal smaller than the node's data- calls the method with the left son
		if(searchVal< node.getData()){
			return recursiveContains(node.getLeft(), searchVal, depth+1);
		}
		// if the searchVal larger than the node's data- calls the method with the right son
		else if(searchVal> node.getData()){
			return recursiveContains(node.getRight(), searchVal, depth+1);
		} 
		return depth;
	}

	/*
	 * add helper method- insert new node to the tree and check the balance
	 */
	private void simpleInsert(AVLNode node, AVLNode toInsert){
		if(node==null){
			this.root = toInsert;
		}
		// checks if 'toInsert' value is smaller than the given node  
		else if (toInsert.getData()<node.getData()){
			// insert new node
			if(node.getLeft()==null){
				node.setLeft(toInsert);
				toInsert.setParent(node);
				// checks the balance of the tree
				checkBalance(toInsert);

			}

			else{
				// call to simpleInsert with the node's left son
				simpleInsert(node.getLeft() ,toInsert);
			}
		}
		// checks if 'toInsert' value is larger than the given node
		else if(toInsert.getData()>node.getData()){
			// insert new node
			if (node.getRight()==null){
				node.setRight(toInsert);
				toInsert.setParent(node);
				// checks the balance of the tree
				checkBalance(toInsert);

			}

			else{
				// call to simpleInsert with the node's right son
				simpleInsert(node.getRight() ,toInsert);
			}
		}
	}

	/*
	 * checks the tree balance recursively
	 */
	private void checkBalance(AVLNode node){
		// the difference between right sub-tree to the left sub-tree
		int balance = setNodeHeight(node.getRight()) - setNodeHeight(node.getLeft());
		// check the balance of the left sub-tree
		if(balance==LEFT_SUBTREE_BALANCE){
			// LL case
			if(setNodeHeight(node.getLeft().getLeft()) >= setNodeHeight(node.getLeft().getRight())){
				node = singleLeftRotate(node);
			}
			else{
				// LR case
				node = doubleLeftRotate(node);
			}
		}
		// check the balance of the right sub-tree
		else if(balance==RIGHT_SUBTREE_BALANCE){
			if(setNodeHeight(node.getRight().getRight())>=setNodeHeight(node.getRight().getLeft())){
				// RR case
				node = singleRightRotate(node);
			}
			else {
				// RL case
				node = doubleRightRotate(node);
			}
		}
		// calls check balance with node's parent
		if(node.getParent()!=null){
			checkBalance(node.getParent());
		}
		else{
			this.root = node;
		}
	}


	/*
	 * Calculating the height of a node.
	 */
	private int setNodeHeight(AVLNode node) {
		if(node==null) {
			return NODE_IS_NULL;
		}
		// the node has no sons
		if(node.getLeft()==null && node.getRight()==null) {
			return NODE_HAS_NO_SONS;
		} 
		// checks the height of the right sub-tree
		else if(node.getLeft()==null) {
			return 1+setNodeHeight(node.getRight());
		} 
		// checks the height of the left sub-tree
		else if(node.getRight()==null) {
			return 1+setNodeHeight(node.getLeft());
		} 
		else {
			// returns the max between the left sub-tree and the right sub-tree
			return 1+max(setNodeHeight(node.getLeft()),setNodeHeight(node.getRight()));
		}
	}

	/*
	 * finds the node should be deleted
	 */
	private void findToDelete(int toDelete, AVLNode node){
		if(node==null){
			return;
		}
		// checks if the value is larger/ smaller equal to the given node
		else{ 
			if(toDelete<node.getData()){
				findToDelete(toDelete, node.getLeft());
			}
			else if(toDelete> node.getData()){
				findToDelete(toDelete, node.getRight());
			}
			else if(toDelete==node.getData()){
				// calls to removeNode method
				removeNode(node);
			}
		}
	}

	/*
	 * remove the node from the tree 
	 * consider the different cases: the node has 2/1/0 children
	 */
	private AVLNode removeNode(AVLNode toDelete){
		AVLNode y = new AVLNode();
		AVLNode x = new AVLNode();
		// the node has at least one son
		if(toDelete.getLeft()==null || toDelete.getRight()==null){
			y = toDelete;
		}
		// the node has two sons
		else {
			y = succesor(toDelete);
		}
		// sets x to be y left son
		if(y.getLeft()!=null){
			x = y.getLeft();
		}
		// sets x to be y right son
		else{
			x = y.getRight();
		}
		// sets x parent and check the balance
		if(x!=null){
			x.setParent(y.getParent());
			checkBalance(y);
		}
		// the root is the node to be deleted
		if(y.getParent()==null){
			this.root = x;
		}
		// set y parent
		else if(y ==y.getParent().getLeft()){
			y.getParent().setLeft(x);
		}
		else{
			y.getParent().setRight(x);
		}
		// replace toDelete data with y data
		if(y!=toDelete){
			toDelete.setData(y.getData());
		}
		// checks parent balance
		if (y.getParent()!=null){
			checkBalance(y.getParent());
		}

		return y;
	}


	/*
	 * finds the node's successor
	 */
	public AVLNode succesor(AVLNode node){
		// case 1: the successor is the minimum of the right sub-tree
		if(node.getRight()!=null){
			AVLNode tmp = node.getRight();
			while(tmp.getLeft()!=null){
				tmp=tmp.getLeft();
			}
			return tmp;
		}
		else{
			// case 2: either the successor is the maximal node in the tree, or the successor 
			// is the lowest ancesor of x that has x in his left sub-tree
			AVLNode parent = node.getParent();
			while(parent!= null && node== parent.getRight()){
				node = parent;
				parent = node.getParent();
			}
			return parent;
		}

	}



	/*
	 * find the max between two sub-trees
	 */
	private int max(int leftHeight, int rightHeight){
		return leftHeight > rightHeight ? leftHeight : rightHeight;
	}



	/*
	 * RR rotate - rotate to the left
	 */
	private AVLNode singleRightRotate(AVLNode node){
		// sets the node right son to be tmp
		AVLNode tmp = node.getRight();
		//sets tmp parent to be node's parent
		tmp.setParent(node.getParent());
		// sets node right son to be tmp left son
		node.setRight(tmp.getLeft());
		// sets the parent of the node right son
		if(node.getRight()!=null) {
			node.getRight().setParent(node);
		}
		// sets tmp left son to be node
		tmp.setLeft(node);
		// sets node parent to be tmp
		node.setParent(tmp);
		// sets tmp parent
		if(tmp.getParent()!=null) {
			if(tmp.getParent().getRight()==node) {
				tmp.getParent().setRight( tmp);
			} else if(tmp.getParent().getLeft()==node) {
				tmp.getParent().setLeft(tmp);
			}
		}

		return tmp;
	}

	/*
	 * LL rotate -rotate to the right
	 */
	private AVLNode singleLeftRotate(AVLNode node){
		// sets the node left son to be tmp
		AVLNode tmp = node.getLeft();
		//sets tmp parent to be node's parent
		tmp.setParent(node.getParent());
		// sets node left son to be tmp right son
		node.setLeft(tmp.getRight());
		// sets the parent of the node left son
		if(node.getRight()!=null) {
			node.getLeft().setParent(node);
		}
		// sets tmp right son to be node
		tmp.setRight(node);
		// sets node parent to be tmp
		node.setParent(tmp);
		// sets tmp parent
		if(tmp.getParent()!=null) {
			if(tmp.getParent().getRight()==node) {
				tmp.getParent().setRight( tmp);
			} else if(tmp.getParent().getLeft()==node) {
				tmp.getParent().setLeft(tmp);
			}
		}
		return tmp;
	}


	/*
	 * LR rotate - rotate to the left and than to the right
	 */
	private AVLNode doubleLeftRotate(AVLNode node){
		// rotates to the left the left son of the vertex violation
		node.setLeft(singleRightRotate(node.getLeft()));
		// rotates to the right the violation vertex
		return singleLeftRotate(node);
	}

	/*
	 * RL rotate - rotate to the right and than to the left
	 */
	private AVLNode doubleRightRotate(AVLNode node){
		// rotates to the right the right son of the vertex violation
		node.setRight(singleLeftRotate(node.getRight()));
		// rotates to the left the violation vertex
		return singleRightRotate(node);

	}

}
