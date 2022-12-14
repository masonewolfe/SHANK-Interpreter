import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.*;

public class Shank
{
	public static void main(String[] args)
	{
		if(args.length==1) //IF MORE THAN ONE FILE PASSED, CRASH
		{
			try
			{
				Lexer lexThis = new Lexer(); //CREATE AN INSTANCE OF THE LEXER
				Parser parseThis = new Parser(); //CREATE AN INSTANCE OF THE PARSER
				Interpreter solve = new Interpreter();
				
				File testCases = new File(args[0]); //FILE GETS FILENAME FROM TERMINAL, IF CANT FIND FILE CRASH
				Scanner myReader = new Scanner(testCases); //OPEN FILE FOR READING
			
				while (myReader.hasNextLine())
				{ 
					lexThis.Lex(myReader.nextLine());//CREATE THE LIST OF TOKENS, IF NOT VALID SYNTAX, WILL THROW EXCEPTION
					parseThis.getTokens(lexThis.getList());
					lexThis.clearList();
				}
				
				
				parseThis.Parse();
				SemanticAnalysis semantics = new SemanticAnalysis(parseThis.getFunctions());
				semantics.Semantisize(); //checks whole file for proper semantics
				
				solve.loadHashMap(parseThis.getFunctions());
				
				Interpreter.InterpretFunction(Interpreter.functions.get("start"),null);
				
				myReader.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("error occurred");
			}
			
		}
		else
		{
			System.out.println("Improper number of files");
		}
	}



}