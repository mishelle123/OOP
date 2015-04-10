package clids.ex4.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Sjavac {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 ArrayList<String> input = new ArrayList<>();
	        Scanner sc;
	        try{
	            File file = new File(args[0]);
	            sc = new Scanner(file);
	            while(sc.hasNextLine()){
	            	input.add(sc.nextLine());
	            }
	        }
	        catch(IOException FileNotFoundException)
	        {
	            System.exit(2);

	        }
	        
	    Parser p = new Parser(input);
	    p.parse();
	        
	}

}
