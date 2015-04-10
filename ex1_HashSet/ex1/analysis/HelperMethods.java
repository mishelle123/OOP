package clids.ex1.analysis;
import clids.ex1.data_structures.MyHashSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeSet;
/*
 * This class contains methods that compute the run time that is needed for each 
 * operation. There are different methods for every data structure. 
 * This in order to avoid code repetition. 
 */
public class HelperMethods {
	private static final String ADD = " adds to ";
	private static final String CONTAINS = " contains ";
	private static final String DELETE = " delete ";
	private static final String LINKEDLIST = " LinkedList ";
	private static final String TREESET = " TreeSet ";
	private static final String MYHASHSET = " MyHashSet ";
	private static final String MS = " ms ";


	public void addLinkedList(String[] data, LinkedList<String> list){
		long timeBefore = new Date().getTime(); 
		for(String word : data){
			list.add(word);
		}
		long timeAfter = new Date().getTime();
		long difference = timeAfter - timeBefore;
		System.out.println(ADD + LINKEDLIST + difference + MS);
	}

	public void addTreeSet(String[] data, TreeSet<String> tree){
		long timeBefore = new Date().getTime(); 
		for(String word : data){
			tree.add(word);
		}
		long timeAfter = new Date().getTime();
		long difference = timeAfter - timeBefore;
		System.out.println(ADD + TREESET + difference + MS);
	}

	public void addMyHashSet(String[] data, MyHashSet set){
		long timeBefore = new Date().getTime(); 
		for(String word : data){
			set.add(word);
		}
		long timeAfter = new Date().getTime();
		long difference = timeAfter - timeBefore;
		System.out.println(ADD + MYHASHSET + difference + MS);
	}

	public void containsLinkedList(String data, LinkedList<String> list){
		long timeBefore = new Date().getTime(); 
		list.contains(data);
		long timeAfter = new Date().getTime();
		long difference = timeAfter - timeBefore;
		System.out.println( CONTAINS + data + LINKEDLIST + difference + MS);
	}

	public void containsTreeSet(String data, TreeSet<String> tree){
		long timeBefore = new Date().getTime(); 
		tree.contains(data);
		long timeAfter = new Date().getTime();
		long difference = timeAfter - timeBefore;
		System.out.println( CONTAINS + data + TREESET + difference + MS);
	}
	
	public void containsMyHashSet(String data, MyHashSet set){
		long timeBefore = new Date().getTime(); 
		set.contains(data);
		long timeAfter = new Date().getTime();
		long difference = timeAfter - timeBefore;
		System.out.println( CONTAINS + data + MYHASHSET + difference + MS);
	}

	public void deleteMyHashSet(String data, MyHashSet set){
		long timeBefore = new Date().getTime(); 
		set.delete(data);
		long timeAfter = new Date().getTime();
		long difference = timeAfter - timeBefore;
		System.out.println( DELETE + data + MYHASHSET + difference + MS);
	}



}
