import java.util.Stack;
import java.util.Vector;

class RPN_Stacker {
    public int RPN_Stack(String[] tokens, Vector<String> variablesNames, Vector<Integer> variablesValues) {
        
        Stack<Integer> stack = new Stack<Integer>();
        
        for (String token : tokens) {
            
            if ("+-*/".contains(token)) {
                stack.push(calculate(stack.pop(), stack.pop(), token));
            } else {
            	if (variablesNames.contains(token)) {
            		int tempPos = variablesNames.indexOf(token);
            		stack.push(variablesValues.elementAt(tempPos));
            	} else {
            		stack.push(Integer.parseInt(token));
            	}
            }
        }
        
        return stack.pop();
        
    }
    
    public int calculate(int first, int second, String operator){
        
        if ("+".equals(operator)) {
            return first + second;
        } else if ("*".equals(operator)) {
            return first * second;
        } else if ("/".equals(operator)) {
            return  second / first;
        } else {
            return second - first;
        }
    }
}