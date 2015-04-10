package clids.ex5.crosswords;

import java.util.Comparator;

public class MyEntryComperator implements Comparator<MyCrosswordEntry>{

	/**
	 * Compares its two arguments for order. Returns a negative integer, zero, or a positive integer 
	 * as the first argument is less than, equal to, or greater than the second.
	 * 
	 * entery1 - the first object to be compared.
	 * entery2 - the second object to be compared.
	 */
	public int compare(MyCrosswordEntry entry1, MyCrosswordEntry entry2) {
		int result = entry2.getSpaceLength() - entry1.getSpaceLength();
		if(result!=0){
			return result;
		}
		result = entry1.getPosition().getX() - entry2.getPosition().getX();
		if(result!=0){
			return result;
		}
		result = entry1.getPosition().getY() - entry2.getPosition().getY();
		if(result!=0){
			return result;
		}
		
		if(entry2.getPosition().isVertical()==entry1.getPosition().isVertical()){
			return 0;
		}
		if(entry1.getPosition().isVertical()){ 
			return -1;
		}
		else{
			return 1;
		}
	}

}
