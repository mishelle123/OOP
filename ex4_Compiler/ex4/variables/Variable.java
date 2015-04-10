package clids.ex4.variables;

public abstract class Variable {

	// checks if the variable has been initialized
	protected boolean initialize = false;
	// checks if the variable defined as final
	protected boolean finalize = false;
	// the variable name
	protected String name ;
	// saves the type of the string
	protected String type;
	

	
	/**
	 * 
	 * @return weather the variable has been initialized
	 */
	public boolean checkInitialize(){
		return this.initialize;
	}
	
	/**
	 * 
	 * @return weather the variable is final
	 */
	public boolean checkFinalize(){
		return this.finalize;
	}

	/**
	 * 
	 * @return the variable's type
	 */
	public String getType(){
		return this.type;
	}



	/**
	 * 
	 * @return the variable's name
	 */
	public String getName(){
		return this.name;
	}



	/**
	 * sets the variable value
	 * 
	 * @param newValue
	 */
	public void setInitialization(){
		this.initialize = true;
	}
	
	/**
	 * sets the variable value
	 * 
	 * @param newValue
	 */
	public void setFinalize(){
		this.finalize = true;
	}


}
