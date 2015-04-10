package clids.ex4.variables;

public class CharVariable extends Variable {

	private static final String CHAR = "char";
	

	
	/**
	 * constructor 
	 * 
	 * @param name- variable's name, finalize- the variable is final , initialize
	 */
	public CharVariable(String name, boolean initialize){
		super.name = name;
		super.initialize = initialize;
		super.type = CHAR;
	}

	


}
