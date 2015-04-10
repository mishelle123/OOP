package clids.ex4.variables;

public class StringVariable extends Variable {

	private static final String STRING = "String";
	
	
	
	/**
	 * constructor 
	 * 
	 * @param name- variable's name, finalize- the variable is final , initialize
	 */
	public StringVariable(String name, boolean initialize){
		super.name = name;
		super.initialize = initialize;
		super.type = STRING;
	}

	

	
}
