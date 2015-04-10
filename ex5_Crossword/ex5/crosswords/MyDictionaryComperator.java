package clids.ex5.crosswords;

import java.util.Comparator;
/**
 * Dictionary Comperator for the words in the dictionary
 * 
 * @author jjgold, mishelle
 *
 */
public class MyDictionaryComperator implements Comparator<String>{

	/**
	 * Compares its two arguments for order. Returns a negative integer, zero, or a positive integer 
	 * as the first argument is less than, equal to, or greater than the second.
	 * 
	 * entery1 - the first object to be compared.
	 * entery2 - the second object to be compared.
	 */
	public int compare(String o1, String o2) {
		int result = o2.length()-o1.length();
		
		return result!=0 ?  result :  o1.compareTo(o2);
	}
	
	

}
