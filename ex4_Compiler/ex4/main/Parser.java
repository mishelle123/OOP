package clids.ex4.main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import clids.ex4.methods.Method;
import clids.ex4.methods.MethodFactory;
import clids.ex4.toolbox.MissingBracketException;
import clids.ex4.toolbox.toolbox;
import clids.ex4.variables.IlligalVariableException;
import clids.ex4.variables.Variable;
import clids.ex4.variables.illigalVariableNameException;
import clids.ex4.variables.variableFactory;

public class Parser {

	private static final String BOOLEAN = "boolean";
	private ArrayList<String> input;
	private ArrayList<Method> methods;
	private ArrayList<Variable> members;
	private int exitValue = 0;


	private static final String FINAL = "final";
	private static final String COMMENT_RE = "^\\s*//";
	private static final String VOID_RE = "void";
	private static final String METHOD_NAME_RE = "[a-zA-Z]\\w*+";
	private static final String OPEN_BRACKET_RE = "[(]";
	private static final String CLOSE_BRACKET_RE = "[)]";
	private static final String OPEN_BLOCK_BRACKET_RE = "[{]";
	private static final String INSIDE_BRACKET_RE = "[^)]*";
	private static final String METHOD_BEGIN_RE = "^\\s*+" + VOID_RE + "\\s++" + "(" + METHOD_NAME_RE + ")" + "\\s*+" 
			+ OPEN_BRACKET_RE + "\\s*+" + "(" + INSIDE_BRACKET_RE + ")" 
			+ CLOSE_BRACKET_RE + "\\s*+" + OPEN_BLOCK_BRACKET_RE + "\\s*+";
	private static final String SPACE = "\\s";
	private static final String VARIABLES_TYPE = "String|char|int|double|boolean";
	private static final String FINAL_VARIABLES_TYPE = "final\\s++String|final\\s++char|final\\s++int|final" +
			"\\s++double|final\\s++boolean";
	private static final String VARIABLES_NAME = "[a-zA-Z]\\w*+|_\\w+";
	private static final String METHOD_CALL_RE = "^\\s*+" + "(" + METHOD_NAME_RE + ")" + "\\s*+" + OPEN_BRACKET_RE 
			+ "\\s*+" + "(" + INSIDE_BRACKET_RE + ")" + CLOSE_BRACKET_RE + "\\s*+" + ";" + "\\s*+";
	private static final String STATEMENT_END = ";" + SPACE + "*" + "$";
	private static final String VARIABLES_DECLERATION = "^" + SPACE + "*+" + "(" + FINAL_VARIABLES_TYPE + "|" 
			+ VARIABLES_TYPE + ")"+ SPACE + "++" + "(" + "[^;]*+" + ")" + ";" + SPACE+ "*+";

	private static final String METHOD_VARIABLE_RE = SPACE + "*+" + "(" + FINAL_VARIABLES_TYPE + "|"+ VARIABLES_TYPE 
			+ ")" + SPACE + "++" + "(" + VARIABLES_NAME + ")" + SPACE + "*+";

	private static final String RETURN_RE = "\\s*+return\\s*+;\\s*+";
	private static final String BOOLEAN_RE = "\\s*+(true|false|-?\\s*+[.]\\d++|\\d++[.]\\d*+|\\d++)\\s*+";	
	private static final String IF_WHILE_RE = "^\\s*+(if|while)\\s*+[(]{1}([^)]*+)[)]\\s*+[{]\\s*+";	
	private static final String ASSIGNMENT_RE = "\\s*+" +"((" + VARIABLES_NAME + ")\\s*+" + "=" + "\\s*+" + "[^;]++"
			+ ")" + ";" + SPACE+ "*+";

	private static final Pattern METHOD_CALL_PATTERN = Pattern.compile(METHOD_CALL_RE);
	private static final Pattern IF_WHILE_PATTERN = Pattern.compile(IF_WHILE_RE);
	private static final Pattern COMMENT_PATTERN = Pattern.compile(COMMENT_RE);
	private static final Pattern METHOD_VARIABLE_PATTERN = Pattern.compile(METHOD_VARIABLE_RE);
	private static final Pattern EMPTY_STRING_PATTERN = Pattern.compile(SPACE+"*+");
	private static final Pattern VARIABLES_DECLERATION_PATTERN = Pattern.compile(VARIABLES_DECLERATION);
	private static final Pattern STATEMENT_END_PATTERN = Pattern.compile(STATEMENT_END);
	private static final Pattern METHOD_BEGIN_PATTERN = Pattern.compile(METHOD_BEGIN_RE);

	private static final Pattern RETURN_PATTERN = Pattern.compile(RETURN_RE);
	private static final Pattern BOOLEAN_PATTERN = Pattern.compile(BOOLEAN_RE);
	private static final Pattern ASIIGNMENT_PATTERN = Pattern.compile(ASSIGNMENT_RE);

	private variableFactory variableFactory;
	private MethodFactory methodFactory;

	/**
	 * constructor
	 * 
	 * @param input
	 */
	public Parser(ArrayList<String> input){

		this.input = input;
		this.members = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.variableFactory = new variableFactory();
		this.methodFactory = new MethodFactory();

	}
	/**
	 * parse the input and preforms the system exit according to the definition.
	 */
	public void parse(){
		// calls to initialParsing
		try {
			initialParsing();
		} catch (InvalidAssignmentException | illigalVariableNameException
				| IlligalVariableException | IlligalFinalException
				| MissingBracketException | IlligalMethodNameException
				| NoReturnStatementException | InvalidLineException e1) {
			this.exitValue = 1;
		}

		// calls to methodParsing
		try {
			methodParsing();
		} catch (illigalVariableNameException | IlligalVariableException
				| IlligalFinalException | InvalidAssignmentException
				| MissingBooleanException | MissingBracketException
				| IlligalMethodNameException | InvalidLineException e) {
			this.exitValue = 1;
		}
		System.exit(exitValue);

	}


	/*
	 * runs over the input array and checks the lines.
	 */
	private void initialParsing() throws InvalidAssignmentException, illigalVariableNameException, 
	IlligalVariableException, IlligalFinalException, MissingBracketException, IlligalMethodNameException, 
	NoReturnStatementException, InvalidLineException{
		
		
		for(int i=0;i<input.size();i++){
			String line = input.get(i);
			if(checkEmptyLine(line)){
				continue;
			}
			if(checkComment(line)){
				continue;
			}
			if(checkVariable(line, this.members, this.members)){
				continue;
			}
			if(checkAssignmentLine(line, this.members, this.members)){
				continue;
			}
			if(checkMethod(line, i)){
				i = toolbox.countBlockBracket(i,this.input);
				continue;
			}
			throw new InvalidLineException();
		}
	}

	/*
	 * a method that checks each method using the recursive parsing
	 */
	private void methodParsing() throws illigalVariableNameException, 
	IlligalVariableException, IlligalFinalException, InvalidAssignmentException, MissingBooleanException, 
	MissingBracketException, IlligalMethodNameException, InvalidLineException {


		for(Method m : this.methods){
			//copy the members
			ArrayList<Variable> membersCopy = new ArrayList<>(); 
			for(Variable v:this.members){
				Variable var = this.variableFactory.copyVariable(v);
				membersCopy.add(var);
			}
			recursiveParsing(m.getBeginIndex(), m.getCloseIndex(), m.getMethodVariables(), membersCopy);
		}
	}


	/*
	 * checks the each block recursively
	 */
	private void recursiveParsing(int begin, int end, ArrayList<Variable> variables, ArrayList<Variable>members)  
			throws illigalVariableNameException, IlligalVariableException, IlligalFinalException, 
			InvalidAssignmentException, MissingBooleanException, MissingBracketException, 
			IlligalMethodNameException, InvalidLineException {


		for(int i = begin+1 ; i<end; i++){
			String line = this.input.get(i);
			// checks if the line is empty
			if(checkEmptyLine(line)){
				continue;
			}
			// checks if the line is a comment
			if(checkComment(line)){
				continue;
			}
			// checks if the line contains variables deceleration
			if(checkVariable(line, variables, members)){
				continue;
			}
			// checks if the line contains variables intialization
			if(checkAssignmentLine(line, variables, members)){
				continue;
			}
			// checks if the line is 'return'
			if(checkReturn(line)){
				continue;
			}
			// checks if the line is if or while block
			if(checkIfWhileLine(line, variables, members)){
				ArrayList<Variable> allVariables =  new ArrayList<>();
				for(int k=0; k< variables.size(); k++){
					allVariables.add(variables.get(k));
				}
				//find where the block end and call recursively on the block
				int endBlock = toolbox.countBlockBracket(i,this.input);
				recursiveParsing(i, endBlock, allVariables, members);
				i = endBlock;
				continue;
			}
			if(checkMethodCall(line, variables, members)){
				continue;
			}
			throw new InvalidLineException();

		}

	}

	/*
	 * checks if a line is a method call
	 */
	private boolean checkMethodCall(String line, ArrayList<Variable> variables, ArrayList<Variable> members) 
			throws IlligalMethodNameException, illigalVariableNameException{


		Matcher m = METHOD_CALL_PATTERN.matcher(line);
		if(m.matches()){
			String methodName = m.group(1);
			String methodSignature = m.group(2);
			//find if a method exists
			Method method = null;
			for(Method meth : this.methods){
				if(methodName.equals(meth.getMethodName())){
					method = meth;
				}
			}
			if(method==null){
				throw new IlligalMethodNameException();
			}
			//check if the variable used to call the method are matching the method variables.
			ArrayList<Variable> methodVariable = method.getMethodVariables();
			m = EMPTY_STRING_PATTERN.matcher(methodSignature);
			if(m.matches() && methodVariable.size()==0){
				return true;
			}
			String[] signatureVariables = methodSignature.split(",");

			if(signatureVariables.length!=methodVariable.size()){
				throw new IlligalMethodNameException();
			}

			for(int i =0 ; i<signatureVariables.length; i++){
				if(!toolbox.checkInitialization(members, variables, 
						methodVariable.get(i).getType(), signatureVariables[i].trim())){
					throw new illigalVariableNameException();
				}
			}
			return true;
		}
		return false;
	}




	/*
	 * checks if the line is 'return'
	 */
	private boolean checkReturn(String line){
		Matcher m = RETURN_PATTERN.matcher(line);
		return m.matches();
	}


	/*
	 * checks if the line is empty
	 */
	private boolean checkEmptyLine(String line) {
		Matcher m = EMPTY_STRING_PATTERN.matcher(line);
		return m.matches();

	}

	/*
	 * checks if the line is if condition/ while loop
	 */
	private boolean checkIfWhileLine(String line, ArrayList<Variable> variables, ArrayList<Variable> members) 
			throws MissingBooleanException{
		
		Matcher m = IF_WHILE_PATTERN.matcher(line);
		if(m.matches()){
			String[] booleanExpression = m.group(2).split("[&]{2}|[|]{2}");
			for(String s : booleanExpression){
				String condition = s.trim();
				m = BOOLEAN_PATTERN.matcher(condition);
				if(m. matches()){
					continue;
				}
				//check if the boolean matches a variable 
				else{
					for(Variable v: variables){
						if(condition.equals(v.getName())){
							if(!v.getType().equals(BOOLEAN) || !v.checkInitialize()){
								throw new MissingBooleanException();
							}
							return true;
						}
					}
					//check if the boolean matches a member
					for(Variable v : members){
						if(condition.equals(v.getName())){
							if(!v.getType().equals(BOOLEAN) || !v.checkInitialize()){
								throw new MissingBooleanException();
							}
							return true;
						}
					}
					throw new MissingBooleanException();
				}
			}
			return true;
		}
		else{
			return false;
		}
	}

	/*
	 * checks if the line is an assignment line
	 */
	private boolean checkAssignmentLine(String line, ArrayList<Variable> variables, ArrayList<Variable> members)
			throws InvalidAssignmentException, illigalVariableNameException{

		Matcher m = STATEMENT_END_PATTERN.matcher(line);
		if(m.find()){
			m = ASIIGNMENT_PATTERN.matcher(line);
			if(m.matches()){
				String[] assignment = m.group(1).split("=");
				assignment[0] = assignment[0].trim();
				//find the relevant variable.
				for(Variable v : variables){
					if(assignment[0].equals(v.getName())){
						if(v.checkFinalize()){
							throw new InvalidAssignmentException();
						}
						//check if the assingment is legal.
						else{
							if(toolbox.checkInitialization(members, variables, v.getType(), assignment[1].trim())){

								v.setInitialization();
								return true;
							}
							else{
								throw new InvalidAssignmentException();
							}
						}
					}
				}
				//find if the variable is a member.
				for(Variable v : members){
					if(assignment[0].equals(v.getName())){
						if(v.checkInitialize() && v.checkFinalize()){
							throw new InvalidAssignmentException();
						}
						else{
							if(toolbox.checkInitialization(members, variables, v.getType(), assignment[1].trim())){

								v.setInitialization();
								return true;
							}
							else{
								throw new InvalidAssignmentException();
							}
						}
					}
				}
			}
		}
		return false;

	}


	/*
	 * checks if the line is a method
	 */
	private boolean checkMethod(String line, int index) throws IlligalMethodNameException, MissingBracketException,
	illigalVariableNameException, IlligalVariableException, NoReturnStatementException{

		Matcher m = METHOD_BEGIN_PATTERN.matcher(line);
		if(m.matches()){
			String methodName =m.group(1);
			String signature = m.group(2);
			//check if a method already exists.
			for(Method meth : this.methods){
				if(methodName.equals(meth.getMethodName())){
					throw new IlligalMethodNameException();
				}
			}
			m = EMPTY_STRING_PATTERN.matcher(signature); 
			ArrayList<Variable> methodVariables = new ArrayList<>();
			//check the method signature
			if(!m.matches()){
				String[] signatureVariables = signature.split(",");
				//create the method variables.
				for(String s : signatureVariables){
					m= METHOD_VARIABLE_PATTERN.matcher(s);
					if(m.matches()){
						String type = m.group(1);
						String[] characteristic = type.split("\\s++");
						boolean finalize = false;
						if(characteristic[0].equals(FINAL)){
							finalize = true;
							type = characteristic[1];
						}
						else{
							type = characteristic[0];
						}
						String variableName = m.group(2);

						Variable var = variableFactory.createVariable(this.members, methodVariables, type, variableName);
						if(finalize){
							var.setFinalize();
						}
						methodVariables.add(var);

					}
					else{
						throw new illigalVariableNameException();
					}
				}
			}
			int closeMethodIndex = toolbox.countBlockBracket(index,this.input);
			if(!checkReturn(input.get(closeMethodIndex-1))){
				throw new NoReturnStatementException();
			}
			this.methods.add(methodFactory.createMethod(methodName, methodVariables, index, closeMethodIndex));
			return true;
		}
		return false;
	}


	/*
	 * checks if the line is a comment
	 */
	private boolean checkComment(String line){
		Matcher commentMatcher = COMMENT_PATTERN.matcher(line);
		return commentMatcher.find();

	}

	/*
	 * checks if the line is variable declaration
	 */
	private boolean checkVariable(String line, ArrayList<Variable> variables, ArrayList<Variable> members) 
			throws illigalVariableNameException, IlligalVariableException, IlligalFinalException {
		Matcher m = STATEMENT_END_PATTERN.matcher(line);
		if(m.find()){
			m = VARIABLES_DECLERATION_PATTERN.matcher(line);
			if(m.matches()){
				String type = m.group(1);
				String declatarions = m.group(2);
				// checks if the variable defined as 'final' and check the variable type
				boolean finalize = false;
				String[] characteristic = type.split("\\s++");

				if(characteristic[0].equals(FINAL)){
					finalize = true;
					type = characteristic[1];
				}
				else{
					type = characteristic[0];
				}
				String[] variablesName = declatarions.split(",");
				for(String s:variablesName){

					Variable var = variableFactory.createVariable(members, variables, type, s);

					if(finalize){
						if(!var.checkInitialize()){
							throw new IlligalFinalException();
						}
						var.setFinalize();
					}
					variables.add(var);
				}
				return true;
			}
		}
		return false;
	}
}
