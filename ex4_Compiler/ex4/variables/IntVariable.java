package clids.ex4.variables;



public class IntVariable extends Variable {
	
	private static final String INT = "int";
	


	/**
	 * constructor 
	 * 
	 * @param name- variable's name, finalize- the variable is final, initialize
	 */
	public IntVariable(String name, boolean initialize){
		super.name = name;
		super.initialize = initialize;
		super.type = INT;
	}
	
	



}
