package clids.ex4.variables;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clids.ex4.toolbox.toolbox;




public class variableFactory {
	
	private static final String BOOLEAN = "boolean";
	private static final String STRING = "String";
	private static final String CHAR = "char";
	private static final String INT = "int";
	private static final String DOUBLE = "double";
	private static final String VARIABLES_NAME = "[a-zA-Z]\\w*+|_\\w+";
	private static final String VARIABLES_NAME_RE = "\\s*+" + "(" + VARIABLES_NAME + ")" + "\\s*+";
	private static final Pattern VARIABLE_NAME_PATTERN = Pattern.compile(VARIABLES_NAME_RE);
	
	public variableFactory() {

	}
	
	/**
	 * 
	 * @param var
	 * @return a copy of the variable
	 */
	public Variable copyVariable(Variable var){
	    Variable newVar;
	    String type = var.getType();
	    boolean finalize = var.checkFinalize();
	    switch (type) {
        case BOOLEAN : 
            newVar = new BooleanVariable(var.getName(), var.checkInitialize());
            break;
        case STRING : 
            newVar = new StringVariable(var.getName(), var.checkInitialize());
            break;
        case INT : 
            newVar = new IntVariable(var.getName(), var.checkInitialize());
            break;
        case CHAR : 
            newVar = new CharVariable(var.getName(), var.checkInitialize());
            break;
        case DOUBLE :
            newVar = new DoubleVariable(var.getName(), var.checkInitialize());
            break;
        default:
            newVar = null;
	    }
	    if(finalize){
	        newVar.setFinalize();
	    }
	    return newVar;
	}
	/**
	 * create a variable according to the given params
	 * @param members - members to compare
	 * @param variables - variables to compare
	 * @param type 
	 * @param variableNameAndValue
	 * @return a variable according to the type 
	 * @throws illigalVariableNameException
	 * @throws IlligalVariableException
	 */
	public Variable createVariable(ArrayList<Variable> members, ArrayList<Variable> variables, String type, 
			String variableNameAndValue) throws illigalVariableNameException, IlligalVariableException{
	
		boolean initialize = false;
		String[] nameAndValue = variableNameAndValue.split("=");
		Matcher m = VARIABLE_NAME_PATTERN.matcher(nameAndValue[0]); 
		if(!m.matches()){
			throw new illigalVariableNameException();
		}
		else{
			nameAndValue[0] = m.group(1).trim();
			for(Variable v: variables){
				if(nameAndValue[0].equals(v.getName())){
					throw new illigalVariableNameException();
				}
			}
		
			// there is no initialization
			if(nameAndValue.length==1){
				initialize = false; 
			}
			// there is an initialization
			
			else if(toolbox.checkInitialization(members, variables, type, nameAndValue[1].trim())){
				initialize = true;
			}
			else{
				throw new IlligalVariableException(); 
			}
			Variable var;
			switch (type) {
			case BOOLEAN : 
				var = new BooleanVariable(nameAndValue[0], initialize);
				break;
			case STRING : 
				var = new StringVariable(nameAndValue[0], initialize);
				break;
			case INT : 
				var = new IntVariable(nameAndValue[0], initialize);
				break;
			case CHAR : 
				var = new CharVariable(nameAndValue[0], initialize);
				break;
			case DOUBLE :
				var = new DoubleVariable(nameAndValue[0], initialize);
				break;
			default:
				var = null;

			}
			return var;
		}
	}

	
}
