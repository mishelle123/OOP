package clids.ex4.methods;

import java.util.ArrayList;

import clids.ex4.variables.Variable;

public class Method {

	private String methodName;
	private ArrayList<Variable> methodVariables;
	private int beginBlock, closeBlock;
	/**
	 * constructor
	 * 
	 * @param methodName
	 * @param methodVariables - the method variables
	 */
	public Method(String methodName, ArrayList<Variable> methodVariables, int beginBlock, int closeBlock){
		this.methodName = methodName;
		this.methodVariables = methodVariables;
		this.beginBlock = beginBlock;
		this.closeBlock = closeBlock;
	}
	
	public Method(String methodName){
		this.methodName = methodName;
	}

	/**
	 * 
	 * @return the method begin index
	 */
	public int getBeginIndex(){
		return this.beginBlock;
	}
	
	/**
	 * 
	 * @return the method close index
	 */
	public int getCloseIndex(){
		return this.closeBlock;
	}
	
	/**
	 * 
	 * @return the method variables
	 */
	public ArrayList<Variable> getMethodVariables(){
		return this.methodVariables;
	}
	
	/**
	 * 
	 * @return method name
	 */
	public String getMethodName(){
		return this.methodName;
	}
	
	
	
}
