package clids.ex4.variables;

public class DoubleVariable extends Variable {

	private static final String DOUBLE = "double";

	
	/**
	 * constructor 
	 * 
	 * @param name- variable's name, finalize- the variable is final, initialize
	 */
	public DoubleVariable(String name, boolean initialize){
		super.name = name;
		super.initialize = initialize;
		super.type = DOUBLE;
	}
	
	

	

}
