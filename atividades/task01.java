import java.util.Stack;

class RPNStacker {
    public int RPN_Stack(String[] tokens) {
        
        Stack<Integer> stack = new Stack<Integer>();
        
        for (String token : tokens) {
            
            if ("+-*/".contains(token)) {
                stack.push(calculate(stack.pop(), stack.pop(), token));
            } else {
                stack.push(Integer.parseInt(token));
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

public class Main {
    
    public static void main(String[] args) {
        
        String[] test = { "10", "6", "9",  "3", "+", "-11", "*", "/",  "*", "17", "+", "5", "+" };
        
        RPNStacker str = new RPNStacker();
        int result = str.RPN_Stack(test);
        System.out.println(result);
        
    }
}
