package clids.ex4.variables;

public class BooleanVariable extends Variable {

	private static final String BOOLEAN = "boolean";



	/**
	 * constructor 
	 * 
	 * @param name- variable's name, finalize- the variable is final , initialize
	 */
	public BooleanVariable(String name, boolean initialize){
		super.name = name;
		super.initialize = initialize;
		super.type = BOOLEAN;
	}



}
