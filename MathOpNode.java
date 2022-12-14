public class MathOpNode extends Node
{
	//ENUM STORING POSSIBLE OPERATIONS
	//USED INSTEAD OF A STRING LITERAL
	public enum possibleOps
	{
		PLUS,
		MINUS,
		MULTIPLIED,
		DIVIDED,
		MOD
	}
	
	private possibleOps operator;
	private Node left;
	private Node right;
	
	//WE WILL ONLY CREATE A MATH NODE WHEN WE KNOW WHAT THE TWO OPERANDS ARE AND THE OPERATION
	//THE FUNCTIONS IN PARSER (TERM AND EXPRESSION) IMPLEMENT THE CREATIONS OF THESE NODES
	//BECAUSE THE LEXERS SUCCESSFULLY LEXED OUT TOKENS, AN OPERATOR WILL BE IN BETWEEN EITHER TWO NUMBER NODES
	//AN EXPRESSION SUBTREES ROOT NODE AND A NUMBER NODE
	//OR TWO EXPRESSION SUBTREES ROOT NODES
	public MathOpNode(Node l, Node r, String op)
	{
		left = l;
		right = r;
		setOperaterEnum(op);
	}
	//RETURNS THE LEFT LEAF NODE
	public Node getLeft()
	{
		return left;
	}
	//RETURNS THE RIGHT LEAF NODE
	//USED FOR PRINTING AND SOLVING THE EXPRESSION
	public Node getRight()
	{
		return right;
	}
	
	public possibleOps getOp()
	{
		return operator;
	}
	//SETS THE OPERATOR ENUM BASED ON THE STRING LITERAL STORED IN A TOKEN
	private void setOperaterEnum(String op)
	{
		switch(op)
		{
			case "+": operator = possibleOps.PLUS; break;
			case "-": operator = possibleOps.MINUS; break;
			case "*": operator = possibleOps.MULTIPLIED; break;
			case "/": operator = possibleOps.DIVIDED; break;
			case "mod": operator = possibleOps.MOD; break;
			default: System.out.println("THIS IS NOT A VALID OPERATOR TOKEN"); break;
		}
	}
	//RETURNS A STRING LITERAL BASED ON ITS ENUM
	public String toString()
	{
		return left.toString() + " " + operator + " " + right.toString();
	}
	
	
	
	
	














}