// How to use this program:
// write down your variables ex: this_is_a_variable = 12
// then write your RPN expression ex:  this_is_a_variable 12 +
// repeat until satisfied
// exit program with "stop"

import java.util.List;
import java.util.ArrayList;
//import java.util.Collection; it's saying this is useless, not sure
import java.util.Scanner;
import java.util.Vector;


public class Lexer {
	
    private static Scanner sc;

	public static enum TokenType  {
    	// Literals.
    	NUM, VAR, ATOM, //ATOM == anything else

    	// Single-character tokens for operations.
    	MINUS, PLUS, SLASH, STAR,
    	
    	EOF
    }
    
    public static class Token {
    	
        public final TokenType  type;
        public final String lexeme; 
        
        public Token(TokenType  type, String value) {
            this.type = type;
            this.lexeme = value;
        }
        
        @Override
        public String toString() {
        	return "Token [type=" + this.type + ", lexeme=" + this.lexeme + "]";
        }
    }

    public static String getLexeme(String string, int index) {
    	
        int j = index;
        
        for( ; j < string.length(); ) {
            if(Character.isLetter(string.charAt(j))) {
                j++;
            } else {
                return string.substring(index, j);
            }
        }
        return string.substring(index, j);
    }
    

    public static List<Token> lex(String input, Boolean badEntry, Vector<String> variablesNames) {
    	
        List<Token> result = new ArrayList<Token>();
        
        for(int i = 0; i < input.length(); ) {
        	
            switch(input.charAt(i)) {
            
            case '-':
                result.add(new Token(TokenType.MINUS, "-"));
                i++;
                break;
            case '+':
                result.add(new Token(TokenType.PLUS, "+"));
                i++;
                break;
            case '/':
                result.add(new Token(TokenType.SLASH, "/"));
                i++;
                break;
            case '*':
                result.add(new Token(TokenType.STAR, "*"));
                i++;
                break;
            case '\u0000':
                result.add(new Token(TokenType.EOF, "\u0000"));
                i++;
                break;
                
            default:
            	
                if(Character.isWhitespace(input.charAt(i))) {
                    i++;
                } else if((int)input.charAt(i) > 47 && (int)input.charAt(i) < 58) { // if this is a number
                	
                	String number = String.valueOf(input.charAt(i));
                	
                	while((int)input.charAt(i+1) > 47 && (int)input.charAt(i+1) < 58) { // if the next char is also a number
                		number = number.concat(String.valueOf(input.charAt(i+1)));
                		i++;
                	}
                	
                    result.add(new Token(TokenType.NUM, number));
                    i++;
                    break;
                } else if(variablesNames.contains(Character.toString(input.charAt(i))) || // if this variable was declared
                		!Character.isWhitespace(input.charAt(i+1))) { // or if next char is spacebar
                	
                	String variable = Character.toString(input.charAt(i));
                	
                	while(!Character.isWhitespace(input.charAt(i+1))) { // see if variable is a "word"
                		variable = variable.concat(String.valueOf(input.charAt(i+1)));
                		i++;
                	}
                	
                	result.add(new Token(TokenType.VAR, variable)); // then add it as a token
                    i++;
                    break;
                } else {
                    String atom = getLexeme(input, i);
                    i += atom.length();
                    result.add(new Token(TokenType.ATOM, atom));
                }
                
                break;
                
            }
            
        }
        
        return result;
        
    }
    
    public static void main(String[] args) {

        String test = "11 6 2 3 + -11 * / * 17 + 5 +";
        String[] testCharString = test.split(" ");
        
        sc = new Scanner(System.in);
        
        String entry = "";
        
        Vector<String> variablesNames = new Vector<String>();
    	Vector<Integer> variablesValues = new Vector<Integer>();
        
        while(!(entry.equals("stop"))) {							// to exit program write "stop" as input
        	
        	entry = sc.nextLine();
        	String[] entryCharString = entry.split(" ");
        	        	
        	Boolean badEntry = false;
        	Vector<Token> badEntries = new Vector<Token>();
        	
        	List<Token> tokens = null;
        	
        	if (entry.contains("=")) {								// if it contains "=" this is a variable
        		String[] splitTemp = entry.split("=");
        		variablesNames.add(splitTemp[0].replaceAll(" ", ""));
        		variablesValues.add(Integer.parseInt(splitTemp[1].replaceAll(" ", "")));
        		
        	} else if (entry.equals("test")) {						// write "test" as input to use test string
        															// for developing purpose only
        		tokens = lex(test, badEntry, variablesNames);
        		
            	RPN_Stacker str = new RPN_Stacker();
            	int result = str.RPN_Stack(testCharString, variablesNames, variablesValues);
            	System.out.println("No bad entries, here is your result:");
            	System.out.println(result);
        		
        	} else {
        		
        		tokens = lex(entry, badEntry, variablesNames);
        		
        		for(Token t : tokens) {								// search for bad entries
                    System.out.println(t);
                    if (t.type == Lexer.TokenType.ATOM) {
                    	badEntries.add(t);
                    	badEntry = true;
                    }
                }
        		
            	if(badEntry == false){ 								// if there is no bad entry
                	RPN_Stacker str = new RPN_Stacker();
                    int result = str.RPN_Stack(entryCharString, variablesNames, variablesValues);
                    System.out.println();
                    System.out.println("No bad entries, here is your result:");
                    System.out.println(result); 					// then give result
                } else {
                	System.out.println();
                	System.out.println("You typed this/these bad entry/entries:");
                	for (Token b: badEntries) {
                		System.out.println(b.lexeme); 				// else print bad entries
                	}
                	
                }
        		
        	}
        	
        }
        
    }

}