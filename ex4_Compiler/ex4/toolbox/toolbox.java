package clids.ex4.toolbox;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clids.ex4.variables.Variable;
import clids.ex4.variables.illigalVariableNameException;




public class toolbox {

	
	private static final String BOOLEAN = "boolean";
	private static final String STRING = "String";
	private static final String CHAR = "char";
	private static final String INT = "int";
	private static final String DOUBLE = "double";
	private static final String INT_RE = "\\s*+-?\\s*+\\d++\\s*+";	
	private static final String DOUBLE_RE = "\\s*+-?\\s*+([.]\\d++|\\d++[.]\\d*+|\\d++)\\s*+";
	private static final String STRING_RE = "\\s*+\"([^\"]*+)\"\\s*+";
	private static final String CHAR_RE = "\\s*+'([^']{1})'\\s*+";
	private static final String BOOLEAN_RE = "\\s*+(true|false|-?\\s*+[.]\\d++|\\d++[.]\\d*+|\\d++)\\s*+";
	
	private static final String OPEN_BLOCK = "[^{]*+\\{\\s*+";
    private static final String CLOSE_BLOCK = "\\s*+\\}\\s*+";
    
	private static final Pattern INT_PATTERN = Pattern.compile(INT_RE);
	private static final Pattern DOUBLE_PATTERN = Pattern.compile(DOUBLE_RE);
	private static final Pattern STRING_PATTERN = Pattern.compile(STRING_RE);
	private static final Pattern CHAR_PATTERN = Pattern.compile(CHAR_RE);
	private static final Pattern BOOLEAN_PATTERN = Pattern.compile(BOOLEAN_RE);
	private static final Pattern OPEN_BLOCK_PATTERN = Pattern.compile(OPEN_BLOCK);
    private static final Pattern CLOSE_BLOCK_PATTERN = Pattern.compile(CLOSE_BLOCK);

	public toolbox(){}

	/**
	 * checks if the variable has been initialize
	 * 
	 * @param members
	 * @param type
	 * @param value
	 * @param variables
	 * @return
	 * @throws illigalVariableNameException
	 */
	public static boolean checkInitialization(ArrayList<Variable> members , ArrayList<Variable> variables, 
	        String type, String value) 
	{
	
		Matcher m;

		// checks if the value matches one of the variables
		for(int i = 0 ; i < variables.size() ; i++){
			// two variables with the same name

			if(value.equals(variables.get(i).getName())){
				// checks if the variable's type matches  
				if(variables.get(i).checkInitialize() && type.equals(variables.get(i).getType())){	
					return true;
				}
				else{
					return false;
				}
			}
		}
		// checks if the value matches one of the  members
        for(int i = 0 ; i < members.size() ; i++){
            // two variables with the same name

            if(value.equals(members.get(i).getName())){
                // checks if the variable's type matches  
                if(members.get(i).checkInitialize() && type.equals(members.get(i).getType())){  
                    return true;
                }
                else{
                    return false;
                }
            }
        }
		// checks the type of the value
		switch (type) {
		case BOOLEAN :  	
			m = BOOLEAN_PATTERN.matcher(value);
			break;
		case STRING : 
			m = STRING_PATTERN.matcher(value);
			break;
		case INT :
			m = INT_PATTERN.matcher(value);
			break;
		case CHAR : 
			m = CHAR_PATTERN.matcher(value);
			break;
		case DOUBLE : 
			m = DOUBLE_PATTERN.matcher(value);
			break;

		default:
			return false;

		}
		if(m.matches()){
			return true;

		}
		else{
			
			return false;
		}
	}
	/**
	 * returns the matching bracket in the input
	 * @param index - index of the open bracket
	 * @param input
	 * @return - the index of the closing bracket
	 * @throws MissingBracketException
	 */
    public static int countBlockBracket(int index,ArrayList<String> input) throws MissingBracketException{
        int openBracketsCounter=0, closeBracketsCounter=0;
        int i = index+1;
        for(; openBracketsCounter>=closeBracketsCounter && i<input.size() ; i++){
            String line = input.get(i);
            Matcher openMatcher = OPEN_BLOCK_PATTERN.matcher(line);
            Matcher closeMatche = CLOSE_BLOCK_PATTERN.matcher(line);
            if(openMatcher.matches()){
                openBracketsCounter++;
            }
            else if(closeMatche.matches()){
                closeBracketsCounter++;
            }
        }
        if(closeBracketsCounter - openBracketsCounter== 1){
            return i-1;
        }
        else{
            throw new MissingBracketException();
        }
    }

	
	
	
}
