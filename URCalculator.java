import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class URCalculator {

    public static boolean isNumber(String input){
        try{    // try to get a number from the currentOper
            Double dummy = Double.parseDouble(input);
            return true;
        }catch (Exception E){ return false; } // returns false if it can't be a number
    }
    public static boolean isOperator(String input){
        String operators = "()^*/+-<>=!&|";
        if(operators.contains(input))
            return true;
        else
            return false;
    }


    public static URQueue<String> shuntingYard(String infix){
        URStack<String> stack = new URStack<String>();
        URQueue<String> queue = new URQueue<String>();

        URArrayList<String> infixSplitList = new URArrayList<String>(infix.length());
        String curr = "", operands = "1234567890.", operators = "()^*/+-<>=!&|";
        for(int i = 0; i < infix.length(); i++){
            
            char currentChar = infix.charAt(i);
            // System.out.println(currentChar == ')');
            if(currentChar == ' ')
                continue;
            else if(operands.indexOf(currentChar) != -1){
                curr = curr + currentChar;
                continue;
            } else if(operators.indexOf(currentChar) != -1){
                if(curr != "")
                    infixSplitList.append(curr);
                curr = "";
                String temp = "" + currentChar;
                infixSplitList.append(temp);
            }
        }
        if(curr != "")
            infixSplitList.append(curr);


        String[] infixSplit = new String[infixSplitList.length()];
        for(int i = 0; i < infixSplitList.length(); i++){
            infixSplit[i] = (String) infixSplitList.get(i);
        }
        // System.out.println(Arrays.toString(infixSplit));

        /** */
        for(String currentOper : infixSplit){
            // System.out.println();
            // System.out.println("ITERATION " + i + "\nSTACK: " + stack.toString() + "\nQUEUE: " + queue.toString() + "\n");
            // the boolean will be false if no operand can be pulled.
            // if false, the currentOper is an Operator.
            if(isNumber(currentOper)){
                // If the token is an operand, enqueue it.
                // System.out.println("NUMBER FOUND (" + currentOper + ")   ENQUEUING...");
                queue.enqueue(currentOper);
                // System.out.println("QUEUE IS NOW " + queue.toString());
            } else {
                // System.out.println("OPERATOR FOUND (" + currentOper + ")");
                //this will run if token is an operator.
                
                // CHARACTERISTIC VECTOR
                // the index of the current operator will be used to find its precedence level.
                // the earlier the operator, the greater the precedent
                String precedenceVectorSTR = "()^*/+-<>=!&|";
                int[] precedenceVals = new int[]{8,8,7,6,6,5,5,4,4,4,3,2,2};
                switch(currentOper){
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "^":
                    case "=":
                    case "<":
                    case ">":
                    case "&":
                    case "|":
                    case "!":
                        // If the token is an operator, 
                        // pop every token on the stack and enqueue them one by one until you reach either
                        // an operator of lower precedence, or a right-associative operator of equal precedence 
                        // (e.g. the logical NOT and the exponential are right-associative operators). 
                        // Enqueue the last operator found, and push the original operator onto the stack.

                        String rightAssociative = "|^";
                        String peekOper = stack.peek();
                        // System.out.println("PEEK OPERATOR: " + peekOper);
                        if(peekOper != null) {
                            int currPriority = precedenceVals[precedenceVectorSTR.indexOf(currentOper)],
                            peekPriority = precedenceVals[precedenceVectorSTR.indexOf(peekOper)];
                            // System.out.println("Peek priority (" + peekPriority + ") > Current priority (" + currPriority + ")?  " + (peekPriority > currPriority));
    
                            while( /** operator other than "(" at the top of the stack */
                            (isOperator(peekOper) && !peekOper.equals("("))
                            && (
                            // peek operator is greater precedence than the current operator 
                            peekPriority > currPriority
                            //OR
                            || (
                            // precedence is equal and current operator is left-associative
                            peekPriority == currPriority && !rightAssociative.contains(currentOper))
                            )
                            ){
                                // System.out.println("Popping and enqueuing...");
                                queue.enqueue(stack.pop());
                                peekOper = stack.peek();
                                if(peekOper == null){
                                    // System.out.println("Stack is empty, exiting.");
                                    break;
                                }
                            }

                            
                        }
                        stack.push(currentOper);
                        break;
                    case "(":
                        stack.push(currentOper);
                        break;
                    case ")":
                        // System.out.println("END PARENTHESIS FOUND. BEGINNING LOOP...\n");
                        // If the token is a close-parenthesis [‘)’], 
                        // pop all the stack elements and enqueue them one by one 
                        // until an open-parenthesis [‘(‘] is found.
                        // System.out.println(stack.peek() + "   " + stack.peek().equals("("));
                        while(!stack.peek().equals("(") && !stack.isEmpty()){
                            // System.out.println("top of stack: " + stack.peek().toString() + "\npopping...");
                            queue.enqueue(stack.pop());
                        }
                        // System.out.println("loop over. current top of stack: " + stack.peek() + "\npopping and removing...");
                        stack.pop();
                        break;

                }
            }

        }

        // At the end of the input, pop every token that remains on the stack 
        // and add them to the queue one by one.
        while(!stack.isEmpty())
            queue.enqueue(stack.pop());
        return queue;
    }

    public static double postfixEval(URQueue<String> queue){
        URStack<String> stack = new URStack<String>();
        boolean nothingLeft = false;
        // System.out.println("BEGIN EVALUATION!!\nSTACK: " + stack.toString() + "\nQUEUE: " + queue.toString());
        // System.out.println("USING THIS QUEUE:    " + queue.toString());
        while(!nothingLeft){
            String oper = queue.peek();
            // System.out.println("CURRENT PEEK:   " + oper);
            // System.out.println("current stack:   " + stack.toString() +"\ncurrent queue:   " + queue.toString() + "\n");
            // System.out.println(oper);
            if(isNumber(oper)){
                stack.push(queue.dequeue());
            } else if(oper == null){
                break;
            } else {
                switch(oper){
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "^":
                        //ARITHMETIC TWO POP
                        // System.out.println(stack.peek());
                        String temp = stack.pop();
                        if(temp == null){
                            nothingLeft = true;
                            stack.push(temp);
                            break;
                        }
                        double op1 = Double.parseDouble(temp);
                        // System.out.println(stack.peek());
                        temp = stack.pop();
                        if(temp == null){
                            nothingLeft = true;
                            stack.push(temp);
                            break;
                        }
                        double op2 = Double.parseDouble(temp);
                        Double result1 = 0.0;
                        switch(oper){
                            case "+":
                                result1 = op2 + op1;
                                break;
                            case "-":
                                result1 = op2 - op1;
                                break;
                            case "*":
                                result1 = op2 * op1;
                                break;
                            case "/":
                                result1 = op2 / op1;
                                break;
                            case "^":
                                result1 = 1.0;
                                for(int i = 0; i < op1; i++){
                                    result1 *= (double) op2;
                                }
                                break;
                        }
                        stack.push(result1.toString());
                        queue.dequeue();
                        break;
                    case "=":                       
                    case "<":
                    case ">":
                    case "&":
                    case "|":
                        // LOGICAL TWO POP
                        temp = stack.pop();
                        if(temp == null){
                            nothingLeft = true;
                            stack.push(temp);
                            break;
                        }
                        op1 = Double.parseDouble(temp);
                        temp = stack.pop();
                        if(temp == null){
                            nothingLeft = true;
                            stack.push(temp);
                            break;
                        }
                        op2 = Double.parseDouble(temp);
                        Double result2 = 0.0;
                        switch(oper){
                            case "=":    
                                if(op2 == op1)
                                    result2 = 1.0;
                                break;
                            case "<":
                                if(op2 < op1)
                                    result2 = 1.0;
                                break;
                            case ">":
                                if(op2 > op1)
                                    result2 = 1.0;
                                break;
                            case "&":
                                if(op2 == 1.0 && op1 == 1.0)
                                    result2 = 1.0;
                                break;
                            case "|":
                                if(op2 == 1.0 || op1 == 1.0)
                                    result2 = 1.0;
                                break;
                        }
                        stack.push(result2.toString());
                        queue.dequeue();
                        break;
                    case "!":
                        // LOGICAL ONE POP
                        temp = stack.pop();
                        if(temp == null){
                            nothingLeft = true;
                            stack.push(temp);
                            break;
                        }
                        double op = Double.parseDouble(temp);
                        if(op == 1.0)
                            stack.push("0.0");
                        else if(op == 0.0)
                            stack.push("1.0"); 
                        queue.dequeue();
                        break;
                }
                // System.out.println("current stack:   " + stack.toString());
            }
            // System.out.println("STACK: " + stack.toString() + "\nQUEUE: " + queue.toString());
        }
        // System.out.println("ABOUT TO RETURN: " + stack.peek() + "\n(full stack): " + stack.toString());
        return Double.parseDouble(stack.pop());
    }

    public static void main(String[] args){
        String inFileName = args[0], outFileName = args[1];
        // String inFileName = ".\\infix_expr_short.txt", outFileName = "Evaluated Output.txt";
        // String inFileName = ".\\SINGLETESTING.txt", outFileName = "singletestoutput.txt";
        
        File inFile;
        Scanner inReader;
        URArrayList<String> gatherExpressions;
        Object[] expressionsGathered;
        String[] expressions = null;
        try{
            inFile = new File(inFileName);
            inReader = new Scanner(inFile);
            
            gatherExpressions = new URArrayList<String>(5);
            while(inReader.hasNextLine())
                gatherExpressions.append(inReader.nextLine());
            expressionsGathered = gatherExpressions.toArray();
            expressions = new String[expressionsGathered.length];
            for(int i = 0; i < expressionsGathered.length; i++)
                expressions[i] = (String) expressionsGathered[i];
            inReader.close();            
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }

        /** 
        //TESTING ARRAYLIST
        URArrayList<Integer> arrTest = new URArrayList<Integer>(3);
        arrTest.append(7);
        arrTest.append(2);
        arrTest.append(9);
        arrTest.append(4);
        System.out.println(Arrays.toString(arrTest.toArray()));
        arrTest.insert(5, 2);
        System.out.println(Arrays.toString(arrTest.toArray()));

        //TESTING THE STACK
        URStack<Integer> stack = new URStack<Integer>();
        System.out.println("FULL 1: " + stack.toString());
        stack.push(2);
        System.out.println("PEEK 1: " + stack.peek());
        stack.push(7);
        stack.push(8);
        System.out.println("PEEK 2: " + stack.peek());
        System.out.println("FULL 2: " + stack.toString());
        System.out.println("POP 1: " + stack.pop());
        System.out.println("POP 2: " + stack.pop());
        System.out.println("FULL 3: " + stack.toString());

        // TESTING THE QUEUE
        URQueue<Integer> queue = new URQueue<Integer>();
        System.out.println("QUEUE INIT: " + queue.toString());
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("QUEUE 1: " + queue.toString());
        System.out.println("DEQUEUE: " + queue.dequeue());
        System.out.println("PEEK: " + queue.peek());
        System.out.println("QUEUE 2: " + queue.toString());
        System.out.println("DEQUEUE: " + queue.dequeue());
*/

        double[] evaluated = new double[expressions.length];
        int i = 0;
        for(String expression : expressions){
            // System.out.println("EXPRESSION: " + expression);
            // System.out.println("POSTFIX VERSION: " + postfix.toString());
            double result = postfixEval(shuntingYard(expression));
            System.out.printf("%.2f\n", result);
            evaluated[i] = result;
            i++;
            // break;
        }

        // save results to file
        try{
            FileWriter outFile = new FileWriter(outFileName);
            for(double d : evaluated){
                outFile.write(String.format("%.2f\n", d));
            }
            outFile.close();
            System.out.println("Evaluated values successfully saved.");
        } catch(Exception e){
            System.out.println("Error writing file.");
        }
        
        
        
        
    }


}