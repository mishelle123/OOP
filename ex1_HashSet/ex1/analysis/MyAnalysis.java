package clids.ex1.analysis;
import java.util.LinkedList; 
import java.util.TreeSet;
import clids.ex1.data_structures.MyHashSet;



public class MyAnalysis {

	private static final String NUM = "-13170890158";
	private static final String NUM2 = "23";
	private static final String HI = "hi";
	private static final String SIZE_LARGER_ADD = "analyzing  MyHashSet 'add' complexity when " +
												  "the size is larger than the capacity ";
	private static final String CAPACITY_LARGER_ADD = "analyzing  MyHashSet 'add' complexity when " +
												  	  "the capacity is larger than the size ";
	private static final String SIZE_LARGER_DELETE = "analyzing  MyHashSet 'delete' complexity when " +
														"the size is larger than the capacity ";
	private static final String CAPACITY_LARGER_DELETE = "analyzing  MyHashSet 'delete' complexity when " + 
														 "the capacity is larger than the size ";
	private static final String SIZE_LARGER_CONTAINS = "analyzing  MyHashSet 'contains' complexity when " +
													   "the size is larger than the capacity ";
	private static final String CAPACITY_LARGER_CONTAINS = "analyzing  MyHashSet 'contains' complexity when " +
													   	   "the capacity is larger than the size ";
	private static final String DATA1 = "data1 :";
	private static final String DATA2 = "data2 :";
	private static final String EXPLORE_ADD = "explores the add method :";
	private static final String EXPLORE_CONTAINS = "explores the contains method :";
	public static void main(String[] args) {

		// data structures for part b  
		LinkedList<String> linkedList1 = new LinkedList<>();
		LinkedList<String> linkedList2 = new LinkedList<>();
		MyHashSet set1 = new MyHashSet();
		MyHashSet set2 = new MyHashSet();
		TreeSet<String> tree1 = new TreeSet<>();
		TreeSet<String> tree2 = new TreeSet<>();
		MyHashSet set1LargeCapacity = new MyHashSet(500000 , 0.25, 0.75);
		MyHashSet set2LargeCapacity = new MyHashSet(500000 , 0.25, 0.75);
		MyHashSet set1LargeSize = new MyHashSet();
		MyHashSet set2LargeSize = new MyHashSet();
		/* contains a list of 99,999 words such that after adding all of them into
		 * a MyHashSet object, they are all mapped to the same cell. */
		String[] data1 = Ex1Utils.file2array("C:/clids/data1.txt");
		/* contains a mixture of different 99,999 words that should be mapped ~uniformly 
		 * across a hash table after adding all of the words. */
		String[] data2 = Ex1Utils.file2array("C:/clids/data2.txt");


		HelperMethods help = new HelperMethods(); 

		/* 
		 * MyHashSet : size > capacity
		 */
		// analyzing  MyHashSet 'add' complexity when the size is larger than the capacity
		System.out.println(SIZE_LARGER_ADD);
		help.addMyHashSet(data1, set1LargeSize);
		help.addMyHashSet(data2, set2LargeSize);
		// analyzing  MyHashSet 'contains' complexity when the size is larger than the capacity
		System.out.println(SIZE_LARGER_CONTAINS);
		help.containsMyHashSet(NUM2, set1LargeSize);
		help.containsMyHashSet(NUM2, set2LargeSize);
		// analyzing  MyHashSet 'delete' complexity when the size is larger than the capacity
		System.out.println(SIZE_LARGER_DELETE);
		help.deleteMyHashSet(NUM2, set1LargeSize);
		help.deleteMyHashSet(NUM2, set2LargeSize);
		
		
		/* 
		 * MyHashSet : size < capacity
		 */
		// analyzing  MyHashSet 'add' complexity when the capacity is larger than the size
		System.out.println(CAPACITY_LARGER_ADD);
		help.addMyHashSet(data1, set1LargeCapacity);
		help.addMyHashSet(data2, set2LargeCapacity);
		// analyzing  MyHashSet 'contains' complexity when the capacity is larger than the size
		System.out.println(CAPACITY_LARGER_CONTAINS);
		help.containsMyHashSet(NUM2, set1LargeCapacity);
		help.containsMyHashSet(NUM2, set2LargeCapacity);
		// analyzing  MyHashSet 'delete' complexity when the capacity is larger than the size
		System.out.println(CAPACITY_LARGER_DELETE);
		help.deleteMyHashSet(NUM2, set1LargeCapacity);
		help.deleteMyHashSet(NUM2, set2LargeCapacity);
	


		/*
		 * explores the add method
		 */
		System.out.println(EXPLORE_ADD);
		help.addLinkedList(data1, linkedList1);
		help.addLinkedList(data2, linkedList2);	
		help.addTreeSet(data1, tree1);
		help.addTreeSet(data2, tree2);
		help.addMyHashSet(data1, set1);
		help.addMyHashSet(data2, set2);
		/*
		 * explores the contains method
		 */
		System.out.println(EXPLORE_CONTAINS);
		System.out.println(DATA1);
		help.containsLinkedList(NUM, linkedList1);
		help.containsTreeSet(NUM, tree1);
		help.containsMyHashSet(NUM, set1);
		help.containsLinkedList(HI, linkedList1);
		help.containsTreeSet(HI, tree1);
		help.containsMyHashSet(HI, set1);
		System.out.println(DATA2);
		help.containsLinkedList(NUM2, linkedList2);
		help.containsTreeSet(NUM2, tree2);
		help.containsMyHashSet(NUM2, set2);
		help.containsLinkedList(HI, linkedList2);
		help.containsTreeSet(HI, tree2);
		help.containsMyHashSet(HI, set2);




	}






}
