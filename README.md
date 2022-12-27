# CSC-172-Project-1---Infix-Calculator
Project 1 for CSC 172 Data Structures and Algorithms. Input a text file with math expressions, and this program will solve each expression in the file. 

Synopsis:
    After the URCalculator.java program takes in a text file with valid infix expressions, it converts all the expressions in the file into a String array using URArrayList that gets converted to a regular array. After it does that, it runs the ShuntingYard algorithm followed by the postfix evaluator algorithm on each expression, both of which are contained within the URCalculator file as functions. It saves each evaluated result to a double array, which is then iterated through to save to an output file.

    ShuntingYard:
        The algorithm begins by creating a stack and a queue using URStack and URQueue, which both in-turn use URNode to create Linked Lists. The algorithm begins by iterating through each character of the original infix String and divides the operators/operands into separate Strings, stored in a URArrayList. It ignores spaces, and stores operands (including decimal points), in addition to the operators. It then converts the ArrayList into a regular String array, called "infixSplit", after all operators/operands have been found. Then, the algorithm begins iterating through each operator/operand in infixSplit. If it's an operand, it is enqueued. If it is an operator, the program does one of two things: if it is not an open/close parenthesis, it will pop stack elements until the current operator's prority is smaller than the popped operator's priority, or until additional conditions are met. The priority of an operator is determined by a String and Integer array as a Characteristic Vector combination, using the index of the operator in the String as the index for the Integer array, which contains a numerical value representing the precedence. The higher the number, the greater the precedence. If the operator is an open parenthesis, it will simply push that operator to the stack. If it's a closed parenthesis, it will pop elements from the stack and enqueue them until it finds an open parenthesis in the stack. The open parenthesis is also popped, but not enqueued. Once that is completed, it pops any remaining elements in the stack and enqueues them, and finally returns the final queue.

    PostFixEval:
         The function takes a URQueue that has been formatted as a postfix expression from ShuntingYard as its input. It then creates a URStack and then enters a while loop dictated by a boolean that can be altered inside the loop if certain conditions are met. On each iteration, the front of the queue is peeked and acts upon what it is. If it's an operand, it is dequeued and pushed to the stack. If it's null, the while loop is ended. Otherwise, that means it is an operator. A switch operation diverts the program into three sections: Mathematical (+-*/^), Logical (=<>&|), and the NOT operator (!) (which has its own special case). The stack is popped twice, and on each pop, it checks if its empty. If it is, the popped value is pushed back into the stack and the loop is ended. The two popped values are saved to double variables, and then a smaller switch statements divide the program further into individual operator cases. Each case in the smaller statements performs the appropriate operations on the two variables, pushes the resulting number to the stack, and dequeues without saving. In the case of the NOT operator, only one stack value is popped since it only requires one value. After the while loop ends, the function returns the last remaining value in the stack as a Double.

You will notice numerous commented-out print statements from hours of debugging. I struggled a decent amount with the operator precedence until I followed the Shunting Yard Algorithm Wikipedia page's pseudocode, which slipped my mind for the longest time. 


EXTRA CREDIT ASPECTS: The ShuntingYard algorithm can read expressions without spaces. 

I asked for assistance and collaborated with Tan Kang, Krish Jain, and a few people ont he CSUG Tutoring Discord server for assistance, but all of the code written in this project is original. 

Files included: 
README                  this file.
OUTPUT                  an example of the programs output.
infix_expr_short.txt    A text file that can be used as input for URCalculator.java.
URCalculator.java       The Java file that contains the ShuntingYard 
                        and PostFixEval methods and evaluates the expressions in the input file.
URArrayList.java        A class that creates a custom implementation of the ArrayList ADT.
URNode.java             A class that creates a custom implementation of the Node ADT 
                        used in URStack and URQueue.
URStack.java            A class that creates a custom implementation of the Stack ADT.
URQueue.java            A class that creates a custom implementation of the Queue ADT.
