import java.util.*;

public class Lexer
{

	private List<Token> tokenList;
	private char peep;
	
	public Lexer()
	{
		tokenList = new ArrayList<Token>();
	}
	
	public void clearList()
	{
		tokenList.clear();
	}
	
	public void Lex(String line)
	{
		String posTok = "";
		int state = 0;
		for(int i = 0;i<line.length();i++)
		{
			
			char pos = line.charAt(i);
			if(i<line.length()-1)
			{
				peep = line.charAt(i+1);
			}
			
			if(state==0) //KNOW NOTHING STATE 
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': posTok = posTok+pos; state=1;break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: posTok=posTok+pos; state = 2; break;
					case '"': posTok=posTok+pos;state=14; break;
					case '.': posTok=posTok+pos;state=3; break;
					case ',',';': posTok=posTok+pos;state=4; break;
					case ':': posTok=posTok+pos;state=5; break;
					case '(': if(peep=='*'){state=13;break;}else{posTok=posTok+pos;state=6;break;}
					case ')': posTok=posTok+pos;state=7; break;
					case '>': posTok=posTok+pos;state=8; break;
					case '<': posTok=posTok+pos;state=9; break;
					case '=': posTok=posTok+pos;state=10; break;
					case '*':
					case '/': posTok=posTok+pos;state=11; break;
					case '+':
					case '-': posTok=posTok+pos;state=12; break;
					case ' ','	': break;
					default:if(pos == 39)
							{
								posTok=posTok+pos;state=15; break;
							}
							else {throw new RuntimeException();}
				}
			}
			else if(state==1) //BUILDING A NUMBER STATE
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': posTok = posTok+pos; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: throw new RuntimeException();
					case '.': posTok=posTok+pos;state=3; break;
					case ',': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=4; break;
					case ';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{throw new RuntimeException();}
					case ')': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=7; break;
					case '>': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=8; break;
					case '<': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=9; break;
					case '=': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=10; break;
					case '*':
					case '/': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=11; break;
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default: throw new RuntimeException();
				}
			}
			else if(state==2) //BUILDING A WORD STATE
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': posTok = posTok+pos; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: posTok=posTok+pos; break;
					case '.': throw new RuntimeException();
					case ',',';': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=4; break;
					case ':': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=5; break;
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=6; break;}
					case ')': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=7; break;
					case '>':tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=8; break;
					case '<': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=9; break;
					case '=': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=10; break;
					case '*':
					case '/': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=11; break;
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default: throw new RuntimeException();
				}
			}
			else if(state==3) //HIT A DECIMAL .
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': posTok = posTok+pos; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: 
					case '.': 
					case ',': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=4; break;
					case ';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{throw new RuntimeException();}
					case ')': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=7; break;
					case '>': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=8; break;
					case '<': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=9; break;
					case '=': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=10; break;
					case '*':
					case '/': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=11; break;
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default: throw new RuntimeException();
				}
			}
			else if(state==4) //HIT A comma , or semicolon ;
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=1; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=2; break;
					case '.': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=3; break;
					case ',',';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{throw new RuntimeException();}
					case ')': 
					case '>','<': 
					case '=': 
					case '*':
					case '/': throw new RuntimeException();
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default: throw new RuntimeException();
				}
			}
			else if(state==5) //HIT A colon :
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': throw new RuntimeException();
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=2; break;
					case '.': 
					case ',',';': 
					case ':': 
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{throw new RuntimeException();}
					case ')': 
					case '>','<': throw new RuntimeException(); 
					case '=': posTok=posTok+pos; state=10; break;
					case '*':
					case '/': 
					case '+':
					case '-': throw new RuntimeException();
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default: throw new RuntimeException();
				}
			}
			else if(state==6) //HIT A (
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=1; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=2; break;
					case '.': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=3; break;
					case '"': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=14; break; 
					case ',',';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=6; break;}
					case ')': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=7; break;
					case '>','<': 
					case '=': 
					case '*':
					case '/': throw new RuntimeException();
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default:if(pos == 39)
							{
								tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=15; break;
							}
							else {throw new RuntimeException();}
				}
			}
			else if(state==7) //HIT A )
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0':
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: 
					case '.': 
					case ',',';': 
					case ':': 
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{throw new RuntimeException();}
					case ')': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=7; break;
					case '>': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=8; break;
					case '<': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=9; break;
					case '=': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=10; break;
					case '*': 
					case '/': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=11; break;
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default: throw new RuntimeException();
				}
			}
			else if(state==8) //HIT A > 
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=1; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=2; break;
					case '.': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=3; break;
					case '"': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=14; break;
					case ',',';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=6; break;}
					case ')': 
					case '>','<':throw new RuntimeException();
					case '=': posTok = posTok + pos; state=10; break;
					case '*': 
					case '/': throw new RuntimeException();
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default:if(pos == 39)
							{
								tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=15; break;
							}
							else {throw new RuntimeException();}
				}
			}
			else if(state==9) //HIT A < 
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=1; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=2; break;
					case '.': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=3; break;
					case ',',';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=6; break;}
					case ')': throw new RuntimeException();
					case '>': posTok=posTok+pos; state=8; break;
					case '<':throw new RuntimeException();
					case '=': posTok = posTok + pos; state=10; break;
					case '*': 
					case '/': throw new RuntimeException();
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default: throw new RuntimeException();
				}
			}
			else if(state==10) //HIT A = 
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=1; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=2; break;
					case '.': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=3; break;
					case '"': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=14; break;
					case ',',';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=6; break;}
					case ')': 
					case '>': 
					case '<': 
					case '=':
					case '*': 
					case '/': throw new RuntimeException();
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default:if(pos == 39)
							{
								tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=15; break;
							}
							else {throw new RuntimeException();}
				}
			}
			else if(state==11) //HIT A * /
			{
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=1; break;
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=2; break;
					case '.': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=3; break;
					case ',',';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=6; break;}
					case ')': 
					case '>': 
					case '<': 
					case '=':
					case '*': 
					case '/': throw new RuntimeException();
					case '+':
					case '-': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default: throw new RuntimeException();
				}
			}
			else if(state==12) //HIT A + -
			{
				boolean isSign=false;
				Token predecessor = tokenList.get(tokenList.size() - 1);
				if(predecessor.toString().equals("mod") || predecessor.toString().equals("from") || predecessor.toString().equals("to") ||
				predecessor.toString().equals("*") || predecessor.toString().equals("/") || predecessor.toString().equals("+") ||
				predecessor.toString().equals("-") || predecessor.toString().equals(">") || predecessor.toString().equals("<") ||
				predecessor.toString().equals("=") || predecessor.toString().equals(":=") || predecessor.toString().equals("(") ||
				predecessor.toString().equals(">=") || predecessor.toString().equals("<=") || predecessor.toString().equals("<>")||
				predecessor.toString().equals(",") || predecessor.toString().equals("if") || predecessor.toString().equals("elsif") || 
				predecessor.toString().equals("while") || predecessor.toString().equals("until") || tokenList.size()==1)
				{
					isSign=true;
				}
				
				switch(pos)
				{
					case '1','2','3','4','5','6','7','8','9','0': 
					if(isSign){posTok=posTok+pos; state=1; break;}
					else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=1; break;}
					case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
					    ,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
						: tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=2; break;
					case '.':
					if(isSign){posTok=posTok+pos; state=3; break;}
					else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=3; break;}
					case '"': tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=14; break;
					case ',',';': 
					case ':': throw new RuntimeException();
					case '(': if(peep=='*'){tokenList.add(new Token(posTok));posTok="";state=13;break;}else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=6; break;}
					case ')': 
					case '>': 
					case '<': 
					case '=':
					case '*': 
					case '/': throw new RuntimeException();
					case '+':
					case '-': 
					if(isSign){throw new RuntimeException();}
					else{tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=12; break;}
					case ' ','	': tokenList.add(new Token(posTok)); posTok=""; state=0; break;
					default:if(pos == 39)
							{
								tokenList.add(new Token(posTok)); posTok=String.valueOf(pos); state=15; break;
							}
							else {throw new RuntimeException();}
				}
			}
			else if(state == 13) //COMMENT STATE
			{
				if(pos=='*' && peep==')')
				{
					state=0;
					i++;
				}
			}
			else if(state==14) //String state
			{
				posTok = posTok+pos;
				if(pos=='"')
				{
					tokenList.add(new Token(posTok)); posTok = ""; state=0;
				}
			}
			else if(state == 15) //char State
			{
				posTok = posTok+pos;
				if(pos==39)
				{
					tokenList.add(new Token(posTok)); posTok = ""; state=0;
				}
			}
		}
		
		
		if(state==13 || state==12 || state==11 || state==10 || state == 9 || state==8 || state==6 || state==5 || state==4)
		{
			//Should only end in space state, ) state, number state, or word state
			throw new RuntimeException();
		}
		
		if(posTok!="")
		{
			tokenList.add(new Token(posTok));
		}
		
		tokenList.add(new Token("\n")); //ADD A NEWLINE CHARACTER TO THE TOKENS WHEN YOU ARE DONE
		
	
	}
	
	public void printTokens()
	{
		for(Token t: tokenList)
		{
			t.printToken();
		}
	}
	
	public List<Token> getList()
	{
		return tokenList;
	}


}