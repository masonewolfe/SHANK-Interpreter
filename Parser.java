import java.util.*;
import java.util.HashMap;
public class Parser
{	
	private List<Token> tokenList;
	List<FuncNode> functions = new ArrayList<FuncNode>();
	
	//CONSTRUCTOR WILL TAKE A LIST OF TOKENS AND ASSIGN IT TO PARSERS LOCAL LIST OF TOKENS
	//WILL ALSO CALL EXPRESSION TO PARSE THE EXPRESSION
	public Parser()
	{
		tokenList = new ArrayList<Token>();
	}
	
	public void getTokens(List<Token> list)
	{
		tokenList.addAll(list);
	}
	
	//RETURNS ROOT NODE OF THE PARSED TREE
	public void Parse()
	{
		do
		{
			FuncNode temp = BuildFunctionTree();
			if(temp!=null){functions.add(temp);}
			else{throw new RuntimeException();}
			popEOLS();
		}
		while(!tokenList.isEmpty());
	}
	
	public List<FuncNode> getFunctions()
	{
		return functions;
	}
	
	public void printFunctions()
	{
		for( FuncNode f : functions )
		{
			f.printFunction();
		}
	}
	public void printTokens()
	{
		for(Token t : tokenList)
		{
			t.printToken();
		}
	}
	
	//WILL CALL TERM WHICH WILL EITHER RETURN A NUMBER, A SERIES OF MULTIPLICATION AND DIVIDES, OR A PARANTHESED EXPRESSION BUILT INTO A SUBTREE
	//WILL THEN ITERATE OVER THE TOKENS LOOKING FOR + OR - ALL WHILE CALLING TERM TO BUILD THE MATH OP NODE
	//TERM WILL AGAIN RETURN A SUBTREE BUILT FROM EITHER A NUMBER, A SERIES OF / AND *, OR AN EXPRESSION IN PARANTHESES
	//WILL ALSO POP A POSSIBLE ) OFF THE TOKEN LIST IN CASE WE ARE BUILDING AN EXPRESSION WITHIN AN EXPRESSION
	//IF NOT IT WILL DO NOTHING
	private Node Expression() 
	{
		Node left = Term();
		Node output = null;
		Token op;
		while(left!=null)
		{
			op = matchAndRemove(new Token("+"));
			if(op==null)
			{
				op = matchAndRemove(new Token("-"));
				if(op==null)
				{
					break;
				}
				else
				{
					output = new MathOpNode(left, Term(), op.toString());
				}
			}
			else
			{
				output = new MathOpNode(left, Term(), op.toString());
			}
			
			left = output;
		}
		matchAndRemove(new Token(")"));
		///////////////////MERGING////////////////////
		op = matchAndRemove(new Token(">"));
		if(op!=null)
		{
			return new CompareNode(left, op, Expression());
		}
		op = matchAndRemove(new Token("<"));
		if(op!=null)
		{
			return new CompareNode(left, op, Expression());
		}
		op = matchAndRemove(new Token(">="));
		if(op!=null)
		{
			return new CompareNode(left, op, Expression());
		}
		op = matchAndRemove(new Token("<="));
		if(op!=null)
		{
			return new CompareNode(left, op, Expression());
		}
		op = matchAndRemove(new Token("="));
		if(op!=null)
		{
			return new CompareNode(left, op, Expression());
		}
		op = matchAndRemove(new Token("<>"));
		if(op!=null)
		{
			return new CompareNode(left, op, Expression());
		}
		return left;
	}
	
	
	//WILL IMMEDIATLEY CALL FACTOR WHICH WILL RETURN OR A NUMBER OR NULL AND STORE IT IN A LOCAL NODE CALLED LEFT
	//WILL THEN TRY READ IN THE NEXT VALUE TO SEE IF IT IS A * OR /
	//IF SO, BUILD A LITTLE SUB TREE WITH THE NEXT NUMBER BY CALLING FACTOR AGAIN AND USING MATHOPNODE
	//ASSIGN THAT SUB TREE TO LOCAL NODE LEFT AND REPEAT
	//IF FACTOR RETURNS NULL, TERM WILL ALSO RETURN NULL
	//IF THERE IS NO * OR / IT WILL RETURN THE NUMBER NODE IT RECIEVED FROM FACTOR
	private Node Term()
	{
		Node left = Factor();
		Node output = null;
		while(left!=null)
		{
			Token op = matchAndRemove(new Token("*"));
			if(op==null)
			{
				op = matchAndRemove(new Token("/"));
				if(op==null)
				{
					op = matchAndRemove(new Token("mod"));
					if(op==null)
					{
						break;
					}
					else
					{
						output = new MathOpNode(left, Factor(), op.toString());
					}
				}
				else
				{
					output = new MathOpNode(left, Factor(), op.toString());
				}
			}
			else
			{
				output = new MathOpNode(left, Factor(), op.toString());
			}
			
			left = output;
		}
		return left;
	}
	
	
	
	//WILL CHECK IF THE CURRENT TOKEN IN THE BEGINNING OF THE LIST IS A NUMBER OR A LEFT HAND PARANTHESE
	//IF IT IS A RIGHT HAND PARANTHESE IT WILL DELETE IT FROM TOKEN LIST AND CALL EXPRESSION TO PARSE WHAT IS IN BETWEEN THE PARANTHESES
    //DOESNT NEED TO WORRY ABOUT A RIGHT HAND PARANTHESE BECAUSE EXPRESSION WILL AUTOMATICALLY TRY AND DELETE ONE WHEN IT IS DONE
	//IF IT IS A #: WILL OUTPUT A FLOAT NODE OR INT NODE, WILL THEN DELETE TOKEN IN BEGINNING OF LIST
	//IF IT IS NEITHER A NUMBER OR A ( THEN WE ARE EITHER AT END OF LINE OR THE PARANTHESES WITHIN THE EXPRESSION ARE NOT PROPERLY APPLIED
	private Node Factor()
	{
		Token temp = matchAndRemove(new Token("1"));
		if(temp!=null)
		{
			if(isFloat(temp.toString()))
			{
				return new FloatNode(Float.parseFloat(temp.toString()));  
			}
			else
			{
				return new IntegerNode(Integer.parseInt(temp.toString()));
			}
		}
		temp = matchAndRemove(new Token("ID"));
		if(temp!=null)
		{
			return new VariableReferenceNode(temp.toString());
		}
		temp = matchAndRemove(new Token("true"));
		if(temp!=null)
		{
			return new BoolNode(true);
		}
		temp = matchAndRemove(new Token("false"));
		if(temp!=null)
		{
			return new BoolNode(false);
		}
		temp = matchAndRemove(new Token("'c'"));
		if(temp!=null)
		{
			return new CharNode(temp.toString().charAt(1));
		}
		temp = matchAndRemove(new Token("\"string\""));
		if(temp!=null)
		{
			return new StringNode(temp.toString().replace("\"",""));
		}
		temp = matchAndRemove(new Token("("));
		if(temp!=null)
		{
			return Expression();
		}
		return null;
	}
	
	
	//GETS PASSED A TOKEN REPRESENTING WHAT WE ARE LOOKING FOR, 
	//WILL COMPARE TO TOKEN AT BEGINNING OF LIST. 
	//IF MATCH: OUTPUT TOKEN AT BEGINNING OF LIST AND DELETE IT FROM LIST. 
	//IF NOT A MATCH: RETURN NULL
	//ERROR CHECKING: IS LIST OF TOKENS EMPTY
	private Token matchAndRemove(Token tok) 
	{
		if(!tokenList.isEmpty())
		{
			Token temp = tokenList.get(0);
			if(temp.getEnum() == tok.getEnum())
			{
				tokenList.remove(0);
				return temp;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	//GETS PASSED A STRING VALUE THAT IS MOST DEFINITLY A CORRECT NUMBER
	//WILL LOOK FOR A DECIMAL POINT IN THE STRING LITERAL
	//IF IT FINDS ONE, THE NUMBER IS A FLOAT, RETURN TRUE
	//IF IT DOESNT FIND ONE IT WILL RETURN FALSE
	private boolean isFloat(String val)
	{
		boolean isfloat = false;
		for(int i =0; i<val.length(); i++)
		{
			if(val.charAt(i)=='.')
			{
				isfloat = true;
			}
		}
		return isfloat;
	}
	
	//PROLLY NEEDS TO PRINT CLEARER
	private void printExpression(Node root, String prefix)
	{
		if(root == null){return;}

		System.out.println(prefix + root.toString());
		printExpression(root.getLeft() , prefix + "  ");
		printExpression(root.getRight() , prefix + "  ");
	}
	
	///////////////////////////////////////////FUNCTION PARSING////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//BUILD FUNCTION TREE SHOULD ONLY BE CALLED WHEN A DEFINE TOKEN IS FOUND
	//WILL PARSE THE FUNCTION INTO A FUNCTION NODE
	//WILL CONTAIN A LIST OF PARAMETERS AND A LIST OF VARIABLES STORED AS VARIABLENODES
	//IF FUNCTION FORMAT IS INVALID, WILL THROW EXCEPTION
	public FuncNode BuildFunctionTree()
	{
		FuncNode functionNode;
		
		
		List<VariableNode> tempList = new ArrayList<VariableNode>();
		List<VariableNode> functionParameters = new ArrayList<VariableNode>();
		List<VariableNode> functionVariables = new ArrayList<VariableNode>();
		if(matchAndRemove(new Token("define"))!=null)
		{
			try
			{
				functionNode = new FuncNode(matchAndRemove(new Token("ID")).toString());
			}
			catch(Exception e)
			{
				System.out.println("FUNCTION NAME NOT FOUND");
				throw new RuntimeException();
			}
			if(matchAndRemove(new Token("("))!=null)
			{
				while(matchAndRemove(new Token(")"))==null)
				{
					tempList = processParameters(); //collects a list of parameters of same datatype in format v1,v2,...,v4 : datatype
					if(tempList==null)
					{   
						System.out.println("FUNCTION PARAMETERS INVALID");
						throw new RuntimeException();
					}
					else
					{
						functionParameters.addAll(tempList);
						matchAndRemove(new Token(";"));
					}
				}
				if(popEOLS()==false)
				{
					System.out.println("TRAILING COMMANDS FOLLOWING FUNCTION DECLARATION");
					throw new RuntimeException();
				}
				for(int i=0;i<2;i++)
				{
					switch(i)
					{
						case 0: tempList = Constants(); if(tempList!=null){functionVariables.addAll(tempList);}
						case 1: tempList = Variables(); if(tempList!=null){functionVariables.addAll(tempList);}
					}
				}
				functionNode.addParameters(functionParameters);
				functionNode.addLocals(functionVariables);
				functionNode.addStatements(ParseBody());
				return functionNode;
			}
			else
			{
				System.out.println("MISSING FUNCTION PARENTHESES");
				throw new RuntimeException();
			}
		}
		else
		{
			throw new RuntimeException();
		}
	}

	//USED TO PARSE VARIABLE DECLARATIONS AFTER A FUNCTION DECLARATION
	//SHOULD BE CALLED IN PARSEFUNCTION FUNCTION AFTER CALLING THE CONSTANT FUNCTION
	//WILL LOOK FOR VARIABLES KEY WORD AND A NEWLINE
	//THEN WILL CALL PROCESSVARIABLES REPEATEDLY UNTIL FORMAT FOR PROCESSVARIABLES IS INVALID
	//IF DOESNT FIND VARIABLES KEYWORD WILL RETURN NULL
	private List<VariableNode> Variables()
	{
		List<VariableNode> output = new ArrayList<VariableNode>();
		List<VariableNode> temporaryList = new ArrayList<VariableNode>();
		if(matchAndRemove(new Token("variables"))!=null)
		{
			if(popEOLS()==true)
			{
				
				temporaryList = processVariables();
				while(temporaryList!=null)
				{
					output.addAll(temporaryList);
					popEOLS();
					temporaryList = processVariables();
				}
			
				if(output.isEmpty())
				{
					System.out.println("SOMETHING WRONG IN VARIABLE DECLARATIONS");
					throw new RuntimeException();
				}
				else
				{
					return output;
				}
			}
			else
			{
				System.out.println("TRAILING COMMANDS AFTER VARIABLES KEYWORD");
				throw new RuntimeException();
			}
			
			
		}
		else
		{
			return null;
		}
	}
	
	
	//ALL FUCNTIONS SHOULD TAKE AT LEAST 1 PARAMETER FOR NOW 
	public List<VariableNode> processParameters()
	{
		List<VariableNode> varNodes = new ArrayList<VariableNode>();
		boolean isvar;
		Token temp;
		do
		{
			temp = matchAndRemove(new Token("var"));
			if(temp!=null)
			{
				isvar=true;
			}
			else
			{
				isvar=false;
			}
			temp = matchAndRemove(new Token("id"));
			if(temp!=null)
			{
				VariableNode v = new VariableNode(temp.toString());
				v.setConst((!isvar));
				varNodes.add(v);
			}
			else
			{
				throw new RuntimeException("PARAMETERS INVALID");
			}
			matchAndRemove(new Token(","));
		}while(matchAndRemove(new Token(":"))==null);
		
		for(int i=0; i<5 ;i++)
		{
			switch(i)
			{
			case 0: temp = matchAndRemove(new Token("integer")); break;
			case 1: temp = matchAndRemove(new Token("real")); break;
			case 2: temp = matchAndRemove(new Token("string")); break;
			case 3: temp = matchAndRemove(new Token("character")); break;
			case 4: temp = matchAndRemove(new Token("boolean")); break;
			}
			if(temp!=null)
			{
				break;
			}
		}
		
		if(temp==null) 
		{
			throw new RuntimeException("PARAMETERS INVALID");
		}
	
		for(VariableNode n:varNodes)
		{
			n.SetDataType(temp);
		}
		
		return varNodes;
	}
	//WILL PRODUCE A LIST OF VARIABLES
	//FORMAT OF VARIABLES SHOULD BE IN THE FORMAT V2, V2, V3, ... V5 : DATATYPE
	//ONLY BE CALLED IN VALID POSITIONS THROUGHOUT PARSER.JAVA
	//SO IF TOKEN LIST NOT IN FORMAT ID,ID,... : DATATYPE WE WILL THROW EXCEPTION
	private List<VariableNode> processVariables()
	{
		List<VariableNode> output = new ArrayList<VariableNode>();
		
		Token temporaryToken = matchAndRemove(new Token("ID"));
		while(temporaryToken!=null)
		{
			output.add(new VariableNode(temporaryToken.toString()));
			if(matchAndRemove(new Token(","))!=null)
			{
				temporaryToken = matchAndRemove(new Token("ID"));
			}
			else
			{
				break;
			}
		}
		if(output.isEmpty())
		{
			return null;
		}
		if(matchAndRemove(new Token(":"))!=null)
		{
			for(int i=0;i<5;i++)
			{
				switch(i)
				{
					case 0: temporaryToken = matchAndRemove(new Token("integer")); break;
					case 1: temporaryToken = matchAndRemove(new Token("real")); break;
					case 2: temporaryToken = matchAndRemove(new Token("string")); break;
					case 3: temporaryToken = matchAndRemove(new Token("character")); break;
					case 4: temporaryToken = matchAndRemove(new Token("boolean")); break;
				}
				if(temporaryToken!=null)
				{
					for(VariableNode n : output)
					{
						n.SetDataType(temporaryToken);
					}
					return output;
				}
			}
			return null;
		}
		else
		{
			return null;
		}
	}
	
	//WILL LOOKS TO POP AN IDENTIFIER, = , NUM, EOL OFF OF THE LIST IN THAT ORDER
	//IF IT DOESNT FIT THAT FORMAT THEN THROW AN EXCEPTION
	//WILL BE CALLED BY CONSTANTS FUNCTION AFTER FINDING A CONSTANTS TOKEN
	private VariableNode parseAConst()
	{
		Token temp = null;
		VariableNode var = null;
		temp = matchAndRemove(new Token("ID"));
		if(temp!=null)
		{
			var = new VariableNode(temp.toString());
		}
		else
		{
			return null;
		}	
		temp = matchAndRemove(new Token("="));
		if(temp == null)
		{
			return null;
		}
		Node tempNode = Factor();
		if(tempNode instanceof IntegerNode) 
		{
			var.SetDataType(new Token("integer"));
			var.setNode(tempNode);
		}
		else if(tempNode instanceof FloatNode)
		{
			var.SetDataType(new Token("real"));
			var.setNode(tempNode);
		}
		else if(tempNode instanceof StringNode)
		{
			var.SetDataType(new Token("string"));
			var.setNode(tempNode);
		}
		else if(tempNode instanceof CharNode)
		{
			var.SetDataType(new Token("character"));
			var.setNode(tempNode);
		}
		else if(tempNode instanceof BoolNode)
		{
			var.SetDataType(new Token("boolean"));
			var.setNode(tempNode);
		}
		else
		{
			return null;
		}
		if(popEOLS()==false)
		{
			return null;
		}
		var.setConst(true);
		return var;
	}
	
	//WILL TRY TO POP A CONSTANTS TOKEN OFF OF THE LIST
	//IF SUCCESSFUL THEN THERE SHOULD BE CONSTANTS STATED
	//WILL CALL PARSEACONST UNTIL ALL CONSTS ARE PARSED
	private List<VariableNode> Constants()
	{
		List<VariableNode> output = new ArrayList<VariableNode>();
		VariableNode temporaryNode;
		if(matchAndRemove(new Token("constants"))!= null)
		{
			if(popEOLS()==true)
			{
				temporaryNode = parseAConst();
				while(temporaryNode!=null)
				{
					output.add(temporaryNode);
					temporaryNode = parseAConst();
				}
				
				if(output.isEmpty())
				{
					System.out.println("CONSTANT DECLARATIONS ARE INVALID");
					throw new RuntimeException();
				}
				else
				{
					return output;
				}
			}
			else
			{
				System.out.println("TRAILING COMMANDS AFTER CONSTANTS KEYWORD");
				throw new RuntimeException();
			}
		}
		else
		{
			return null;
		}
	}
	
	//WILL POP BEGIN EOL END EOL FROM TOKEN LIST IN THAT ORDER
	//FOR NOW THAT IS HOW WE ARE PARSING A BODY
	//WILL CHANGE LATER ON
	public List<StatementNode> ParseBody()
	{
		Token temp = matchAndRemove(new Token("begin"));
		if(temp == null)
		{
			throw new RuntimeException();
		}
		if(popEOLS()==false)
		{
			throw new RuntimeException();
		}
		List<StatementNode> output = Statements();
		temp = matchAndRemove(new Token("end"));
		if(temp==null)
		{
			throw new RuntimeException();
		}
		if(popEOLS()==false)
		{
			throw new RuntimeException();
		}
		return output;
	}
	
	///////////////////////////////////////////////////////////////////////////////STATEMENTS PART OF PARSER//////////////////////////////////////////////////////////////////
	//WILL GET PASSED A STRING ID NAME FROM PARSEASTATEMENT FUNCTION BECAUSE PARSEAFUNCTION AND PARSEASTATEMENT BOTH START WITH MATCHANDREMOVE ID TOKEN
	//THEREFORE IF PARSEASSIGNMENT FAILS THAT SAME TOKEN WILL GET PASSED TO PARSEAFUNCTION SO NOT TO LOSE THE ID TOKEN
	//PARSEASSIGNMENT WILL PARSE IN THIS ORDER MATCHANDREMOVE := TOKEN, THEN CALL EXPRESSION, THEN POP EOLS
	//WILL RETURN NULL IF IT FAILS AT ANYPOINT
	public AssignmentNode parseAssignment(String name)
	{
		VariableReferenceNode varRef = new VariableReferenceNode(name);
		Token temp = matchAndRemove(new Token(":="));
		if(temp == null)
		{
			return null;
		}
		Node expr = Expression();
		if(expr == null)
		{
			return null;
		}
		if(popEOLS()==false)
		{
			return null;
		}
		return new AssignmentNode(varRef, expr);
	}
	
	//WILL TRY AND MATCHANDREMOVE CERTAIN KEYWORDS THAT WOULLD IN TURN MEAN CERTAIN LINES OR BLOCKS NEED BE PARSED
	//THOSE KEYWORDS ARE if, for, while, repeat (BASED ON THESE KEYWORD WE WOULD TRY TO PARSE ACCORDINGLY)
	//IF IT WE CANT SUCCESSFULLY MATCHANDREMOVE THOSE THEN WE TRY TO PARSE AN ASSIGNMENT AND IF THATS UNSUCCESSFUL THEN WE WILL TRY TO PARSE A FUNCTION CALL
	//IF ALL PARSE ATTEMPTS ARE UNSUCCESSFUL THEN WE WILL RETURN A NULL
	//IF IT PARSES ONE OF THESE SUCCESSFULLY THEN WE RETURN CORRECT STATEMENTNODE TYPE AFTER PARSING
	//THIS FUNCTION GETS CALLED REPEATEDLY BY STATEMENTS UNTIL IT IS UNSUCCESSFUL
	public StatementNode parseAStatement()
	{
		StatementNode output = null;
		if(matchAndRemove(new Token("if"))!=null)
		{
			output = parseIfStatement();
		}
		else if(matchAndRemove(new Token("for"))!=null)
		{
			output = parseForStatement();
		}
		else if(matchAndRemove(new Token("while"))!=null)
		{
			output = parseWhileStatement();
		}
		else if(matchAndRemove(new Token("repeat"))!=null)
		{
			output = parseRepeatStatement();
		}
		else //better be an assignment or a function call
		{
			Token temp = matchAndRemove(new Token("ID"));
			if(temp!=null)
			{
				output=parseAssignment(temp.toString());
				if(output==null)
				{
					output=parseFunctionCall(temp.toString());
				}
			}
			else
			{
				output=null;
			}
		}
		return output;
	}
	
	//WILL CALL PARSEASTATEMENT UNTIL IT RETURNS NULL
	//FOR EVERY SUCCESSFUL STATEMENT PARSED THE CORRESPONDING STATEMENTNODE WILL GET STORED IN THE LIST OF STATEMENTS TO BE RETURNED
	public List<StatementNode> Statements()
	{
		List<StatementNode> statements = new ArrayList<StatementNode>();
		StatementNode temp = parseAStatement();
		while(temp!=null)
		{
			statements.add(temp);
			temp=parseAStatement();
		}
		return statements;
	}
	
	//USED TO PARSE A COMPARISON
	//IF IT FAILS AT ANY POINT IT WILL RETURN NULL
	//COMPARISON HAPPEN IN FORMAT LEFTNODE COMPARISON RIGHTNODE
	//LEFT NODE AND RIGHT NODE CAN BE A VARIABLENAME, A NUMBER, OR AN EXPRESSION... SO EXPRESSION()	IS CALLED WHEN PARSING
	//FIRST CALL EXPRESSION WHICH WILL PARSE AN EXPRESSION AND RETURN A ROOT MATHOPNODE, A NUMBERNODE, OR A VARREF NODE AN ASSIGN IT TO LEFT NODE
	//THEN TRY AND MATCHANDREMOVE EVERY POSSIBLE TYPE OF COMPARISON OPERATOR, IF IT FINDS ONE IT WILL BE STORED IN A TEMPORARY TOKEN
	//THEN CALL EXPRESSION FOR RIGHT NODE
	//THEN RETURN A NEW COMPARE NODE THAT HAS LEFT NODE, COMPARE OP, AND RIGHTNODE STORED IN IT
	public CompareNode parseComparison()
	{
		Node left = Expression();
		Token temp;
		if(left == null)
		{
			System.out.println("EXPRESSION ON LEFT SIDE OF COMPARISON INVALID");
			return null;
		}
		if(matchAndRemove(new Token(">"))!=null)
		{
			temp = new Token(">");
		}
		else if(matchAndRemove(new Token("<"))!=null)
		{
			temp = new Token("<");
		}
		else if(matchAndRemove(new Token(">="))!=null)
		{
			temp = new Token(">=");
		}
		else if(matchAndRemove(new Token("<="))!=null)
		{
			temp = new Token("<=");
		}
		else if(matchAndRemove(new Token("="))!=null)
		{
			temp = new Token("=");
		}
		else if(matchAndRemove(new Token("<>"))!=null)
		{
			temp = new Token("<>");
		}
		else
		{
			System.out.println("not finding comparison");
			return null;
		}
		Node right = Expression();
		if(right==null)
		{
			System.out.println("EXPRESSION ON RIGHT SIDE OF COMPARISON IF INVALID");
			return null;
		}
		else
		{
			return new CompareNode(left, temp, right);
		}
	}
	
	//WILL BE CALLED WHEN STATEMENTS SUCCESSFULLY MATCHANDREMOVES AN IF TOKEN
	//IF IT FAILS AT ANY POINT IT WILL RETURN NULL
	//FIRST PARSE A COMPARISON THEN MATCHANDREMOVE THEN TOKEN, THEN POP EOL(S)
	//THEN CALL PARSEBODY
	//THEN TRY TO MATCHANDREMOVE AN ELSIF TOKEN, ELSIF STATEMENTS PARSE THE SAME AS IF STATEMENTS SO...
    //RETURN AN IFNODE THAT CALLS PARSEIFSTATEMENT AS THE NEXT NODE FOR THE CHAINED LIST OF IF STATEMENTS
	//IF UNSUCCESSFULLY POPPED AN ELSIF NODE THEN TRY TO MATCHANDREMOVE AN ELSE TOKEN
	//IF SUCCESSFUL THEN RETURN AN IFNODE WITH PARSEELSESTATEMENT() FUNCTION IN PLACE OF THE NEXT NODE WHEN RETURNING THE IF NODE
	//IF THERE IS NOTHING LEFT AFTER IF/ELSIF STATEMENT THEN IT IS THE END OF THE IF CHAIN AND NEXT NODE WILL BE NULL
	public IfNode parseIfStatement()
	{
		Node bool = Expression();
		if(bool == null)
		{
			return null;
		}
		Token temp = matchAndRemove(new Token("then"));
		if(temp==null)
		{
			System.out.println("then statement missing");
			return null;
		}
		if(popEOLS()==false)
		{
			System.out.println("something after then statement");
			return null;
		}
		List<StatementNode> list = ParseBody();
		temp = matchAndRemove(new Token("elsif"));
		if(temp!=null)
		{
			return new IfNode(bool, list, parseIfStatement());
		}
		temp = matchAndRemove(new Token("else"));
		if(temp!=null)
		{
			return new IfNode(bool, list, parseElseStatement());
		}
		return new IfNode(bool, list, null);
	}
	
	//USED AT THE END OF AN IF CHAIN POTENTIALLY
	//PARSEIFSTATEMENT WILL TRY AND POP AN ELSEIF OR AN ELSE AT THE END
	//SO ELSE TOKEN WILL HAVE ALREADY BEEN MATCH AND REMOVED
	//SO THEN POP EOL(S) THEN CALL PARSEBODY
	//RETURN AN IFNODE WITH A NULL BOOL NODE AND A NULL NEXT NODE
	private IfNode parseElseStatement()
	{
		popEOLS();
		List<StatementNode> list = ParseBody();
		return new IfNode(null, list, null);
	}
	
	//USED TO PARSE A WHILE STATEMENT
	//STATEMENTS ALREADY MATCH AND REMOVED WHILE TOKEN
	//SO PARSE A BOOLEAN, POP AN EOL OR MORE, THEN CALL PARSE BODY
	public WhileNode parseWhileStatement()
	{
		Node bool = Expression();
		if(bool == null)
		{
			return null;
		}
		if(popEOLS()==false)
		{
			return null;
		}
		List<StatementNode> list = ParseBody();
		return new WhileNode(bool, list);
	}
	
	//USED TO PARSE A REPEAT STATEMENT
	//STATEMENTS SUCCESSFULLY MATCHANDREMOVED A REPEAT TOKEN SIGNIFYING WE SHOULD BE PARSING A REPEAT BLOCK OF STATEMENTS
	//MATCHANDREMOVE IN THIS ORDER EOL(S),
	//THEN CALL PARSEBODY
	//THEN POPS UNTIL TOKEN, THEN PARSEBOOLEAN, POP EOLS
	public RepeatNode parseRepeatStatement()
	{
		popEOLS(); 
		List<StatementNode> list = ParseBody();
		Token temp = matchAndRemove(new Token("until"));
		if(temp == null)
		{
			return null;
		}
		Node bool = Expression();
		if(bool == null)
		{
			return null;
		}
		if(popEOLS()==false)
		{
			return null;
		}
		return new RepeatNode(bool, list);
	}

	//USED TO PARSE A FOR STATEMENT
	//IF THIS FUNCTION IS CALLED THEN A FOR TOKEN HAS BEEN MATCHANDREMOVED IN STATEMENTS FUNCTION TO CONFIRM THIS WHAT WE SHOULD BE PARSING
	//MATCHANDREMOVE IN THIS ORDER: VARREF, FROM, NUM, TO, NUM, EOL(S)
	//THEN CALL PARSEBODY WHICH POPS BEGIN, EOL(S)
	//PARSEBODY THEN CALLS STATEMENTS
	//PARSEBODY THEN POPS END, EOL(S)
	public ForNode parseForStatement()
	{
		VariableReferenceNode var;
		Node start, end;
		List<StatementNode> statements;
		Token temp = matchAndRemove(new Token("ID")); //POP IDENTIFIER TOKEN
		if(temp == null)
		{
			return null;
		}
		var = new VariableReferenceNode(temp.toString()); //CREATE VARREF NODE OUT ID NAME
		temp = matchAndRemove(new Token("from")); //POP FROM TOKEN
		if(temp == null)
		{
			return null;
		}
		start = Factor(); //STORE NUMBER TOKEN AS NUMBER NODE (REPRESENTS BEGINNING OF RANGE)
		if(start==null)
		{
			return null;
		}
		temp = matchAndRemove(new Token("to")); //POP TO TOKEN
		if(temp==null)
		{
			return null;
		}
		end = Factor();
		if(end==null)
		{
			return null;
		}
		if(popEOLS()==false)
		{
			return null;
		}
		statements = ParseBody(); //CALL PARSEBODY WHICH RETURNS A LIST OF STATEMENT NODES
		return new ForNode(var, start, end, statements); //IF YOU MADE IT THIS FAR, CREATE A FORNODE OUT OF THE INFO WE PARSED IN THIS FUNCTION
	}
	
	//USED TO PARSE FUNCTION CALLS
	//GETS PASSED STRING NAME
	//IN THE PARSEASTATEMENT FUNCTION THE LAST POSSIBLE ATTEMPT AT PARSING A STATEMENT IS PARSING A FUNCTION OR PARSING AN ASSIGNMENT
	//SINCE BOTH OF THESE PARSE PROCESSES INTITIALLY TRY TO POP AN IDENTIFIER TOKEN AND IF IT FAILS AT PARSING AN ASSIGNMENT AND THEN TRYS TO PARSE A FUNCTION
	//THE IDENTIFIER TOKEN WILL THEN BE MISSING. SO INSTEAD I MATCHANDREMOVE THE ID TOKEN FIRST AND SAVE IT WITHIN PARSEASTATEMENT
	//FROM THERE I PASS THE ID TOKEN VIA ITS STRING NAME TO PARSEASSIGNMENT AND PARSEAFUNCTION
	public FunctionCallNode parseFunctionCall(String name)
	{
		String funcName=name; //FUNCTION NAME GETS PASSED FROM PARSEASTATEMENT
		List<ParamNode> params = new ArrayList<ParamNode>(); //ALLOCATE MEMORY FOR A LIST OF PARAMNODES
		boolean isVar = false;
		
		while(popEOLS()==false) //TRY TO POP AN EOL, SHOULD FAIL INITIALLY, BUT WILL LOOP UNTIL WE HIT AN EOL TOKEN
		{
			Token temp = matchAndRemove(new Token("var")); //TRY AND POP A VAR KEYWORD
			if(temp!=null) //IF VAR POPPED SUCCESSFULLY
			{
				isVar = true;
			}
			
			Node tempNode = Expression();
			if(tempNode == null || (!(tempNode instanceof VariableReferenceNode) && isVar==true)) //WAS SUCCESSFUL
			{
				return null;
			}
			else
			{
				params.add(new ParamNode(isVar, tempNode)); //CREATE A PARAMNODE OUT OF THE NODE WE POPPED IN FACTOR
			}
			matchAndRemove(new Token(",")); //TRY AND POP A COMMA, BUT IT DOESNT HAVE TO BE THERE
		}
		return new FunctionCallNode(funcName, params); //IF YOU SUCCESSFULLY MATCHANDREMOVED IN THE CORRECT ORDER THEN RETURN THE FUNCTIONCALLNODE
	}
	
	//WILL CONTINOUSLY MATCHANDREMOVE EOL TOKENS UNTIL IT CANNOT ANYMORE
	//IF IT MATCHANDREMOVES AT LEAST 1 EOL TOKEN IT WILL RETURN TRUE
	//IF IT DOESNT SUCCESSFULLY MATCHANDREMOVE AT LEAST 1 EOL TOKEN IT WILL RETURN FALSE
	//THIS IS IMPLEMENTED LIKE THIS BECAUSE THE FILE WE LEX AND PARSE CAN SYNTACTICALLY HAVE AS MANY EOL TOKENS IN A ROW AS POSSIBLE
	//AKA THERE CAN BE MANY EMPTY LINES IN BETWEEN STATEMENTS
	public boolean popEOLS()
	{
		boolean success = false;
		while(matchAndRemove(new Token("\n"))!=null)
		{
			success=true;
		}
		return success;
	}
}