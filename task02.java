import java.util.List;
import java.util.ArrayList;


public class Lexer {
	
    public static enum TokenType  {
    	// Literals.
    	NUM, ATOM, //ATOM == anything else

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
    

    public static List<Token> lex(String input) {
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
        String test = "10 6 9 3 + -11 * / * 17 + 5 +";
        List<Token> tokens = lex(test);
        for(Token t : tokens) {
            System.out.println(t);
        }
    }
}