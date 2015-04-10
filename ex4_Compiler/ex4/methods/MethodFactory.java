package clids.ex4.methods;

import java.util.ArrayList;

import clids.ex4.variables.Variable;

public class MethodFactory {
	
	
	/**
	 * constructor
	 * 
	 */
	public MethodFactory(){}
	
	/**
	 * creates a new method
	 * 
	 * @param methodName
	 * @param methodVariables - the method variables
	 * @return
	 */
	public Method createMethod(String methodName, ArrayList<Variable> methodVariables, 
							  int beginBlock, int closeBlock){
		if(methodVariables!=null){
			for(Variable v: methodVariables){
				v.setInitialization();
			}
		}
		
		
		return new Method(methodName, methodVariables, beginBlock, closeBlock);
		
	}
	
}
