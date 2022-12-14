import java.util.*;
public class GetRandom extends BuiltInFunctionNode
{
	String functionName = "GetRandom";
	boolean isVariadic = false;
	
	

	public void Execute(List<InterpreterDataType> data)
	{
		if(data.size()==1 && data.get(0) instanceof IntDataType)
		{
			Random rand = new Random();
			int num = rand.nextInt()%2;
			switch(num)
			{
				case 0: num = rand.nextInt(10000); num = num * (-1); break;
				case 1: num = rand.nextInt(10000); break;
			}
			data.get(0).fromString(Integer.toString(num));
		}
		else
		{
			throw new RuntimeException("Wrong argument(s) passed to GetRandom");
		}
	}
}