public class Token
{
	//CURRENT ALLOWED TOKES WE ARE STORING
	public enum allowedTokens
	{
		PLUS,MINUS,MULTIPLIED,DIVIDED,NUMBER,NEWLINE,LEFTPAREN,RIGHTPAREN,IDENTIFIER,DEFINE,INTEGER,REAL,BEGIN,END, 
		SEMICOLON,COLON,EQUAL,COMMA,VARIABLES,CONSTANTS,ASSIGNMENT,IF,THEN,ELSE,ELSEIF,FOR,FROM,TO,WHILE,
		REPEAT,UNTIL,MOD,GREATERTHAN,LESSTHAN,GREATERTHANEQUALTO,LESSTHANEQUALTO,NOTEQUAL,VAR,CHARCONTENTS,
		STRINGCONTENTS,TRUE,FALSE,CHAR,STRING,BOOLEAN
	}
	
	//STRING VAL WILL HOLD EITHER A CHARACTER, A NEWLINE, OR A NUMBER
	//TOKEN RESPRESENTS ITS ENUM TYPE AND WILL BE USED IN MATCHANDREMOVE WITHIN THE PARSER
	private String val;
	private allowedTokens token;
	
	//PARAMETERIZED CONSTRUCTOR
	public Token(String input)
	{
		val = input;
		HashMap(input);
	}
	
	//RETURNS ITS STRING LITERAL
	public String toString()
	{
		return val;
	}
	
	//RETURNS ITS ENUM TYPE
	//TOKEN TYPE ENUM INSTANCE IS PRIVATE, BUT ENUM DEFINITION IS PUBLIC
	public allowedTokens getEnum()
	{
		return token;
	}
	
	//SETS THE TOKEN TYPE BASED ON THE STRING LITERAL SENT TO TOKEN WITHIN ITS PARAMETERIZED CONSTRUCTOR
	private void HashMap(String input)
	{
		switch(input)
		{
			case "*": token = allowedTokens.MULTIPLIED; break;
			case "/": token = allowedTokens.DIVIDED; break;
			case "+": token = allowedTokens.PLUS; break;
			case "-": token = allowedTokens.MINUS; break; 
			case "\n": token = allowedTokens.NEWLINE; break;
			case "(": token = allowedTokens.LEFTPAREN; break;
			case ")": token = allowedTokens.RIGHTPAREN; break;
			case "define": token = allowedTokens.DEFINE; break;
			case "integer": token = allowedTokens.INTEGER; break;
			case "real": token = allowedTokens.REAL; break;
			case "string" : token = allowedTokens.STRING; break;
			case "character" : token = allowedTokens.CHAR; break;
			case "boolean" : token = allowedTokens.BOOLEAN; break;
			case "begin": token = allowedTokens.BEGIN; break; 
			case "end": token = allowedTokens.END; break;
			case ";": token = allowedTokens.SEMICOLON; break;
			case ":": token = allowedTokens.COLON; break;
			case "=": token = allowedTokens.EQUAL; break;
			case ",": token = allowedTokens.COMMA; break;
			case ":=": token = allowedTokens.ASSIGNMENT; break;
			case "variables": token = allowedTokens.VARIABLES; break;
			case "constants": token = allowedTokens.CONSTANTS; break;
			case "if": token = allowedTokens.IF; break;
			case "then": token = allowedTokens.THEN; break;
			case "else": token = allowedTokens.ELSE; break;
			case "elsif": token = allowedTokens.ELSEIF; break;
			case "for": token = allowedTokens.FOR; break;
			case "from": token = allowedTokens.FROM; break;
			case "to": token = allowedTokens.TO; break;
			case "while": token = allowedTokens.WHILE; break;
			case "repeat": token = allowedTokens.REPEAT; break;
			case "until": token = allowedTokens.UNTIL; break;
			case "mod": token = allowedTokens.MOD; break;
			case "var": token = allowedTokens.VAR; break;
			case ">": token = allowedTokens.GREATERTHAN; break;
			case "<": token = allowedTokens.LESSTHAN; break;
			case ">=": token = allowedTokens.GREATERTHANEQUALTO; break;
			case "<=": token = allowedTokens.LESSTHANEQUALTO; break;
			case "<>": token = allowedTokens.NOTEQUAL; break;
			case "true": token = allowedTokens.TRUE; break;
			case "false": token = allowedTokens.FALSE; break;
			default: 
			try
			{
				int num = Integer.parseInt(input);
				token = allowedTokens.NUMBER; break;
			}
			catch(NumberFormatException e)
			{
				try
				{
					float num = Float.parseFloat(input);
					token = allowedTokens.NUMBER; break;
				}
				catch(NumberFormatException e2)
				{
					if(input.indexOf("'")==0 && input.lastIndexOf("'")==input.length()-1)
					{
						if(input.length()==3)
						{
							token = allowedTokens.CHARCONTENTS; break;
						}
						else
						{throw new RuntimeException();}
					}
					else if(input.charAt(0)=='"' && input.charAt(input.length()-1)=='"')
					{
						token = allowedTokens.STRINGCONTENTS; break;
					}
					else {token = allowedTokens.IDENTIFIER; break;}
				}
			}
		}
	}
	
	//PRINT THE TOKEN ALL CLASSY LIKE
	public void printToken()
	{
		if(val == "\n")
		{
			System.out.println("EOL ");
		}
		else if(token == allowedTokens.LEFTPAREN)
		{
			System.out.print("L( ");
		}
		else if(token == allowedTokens.RIGHTPAREN)
		{
			System.out.print(")R ");
		}
		else
		{
			System.out.print(token +"(" + val + ") ");
		}
	}
	
}