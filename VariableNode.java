public class VariableNode extends Node
{
	public enum dataType
	{
		INT,REAL,CHAR,STRING,BOOL
	}
	
	public dataType type;
	public String name;
	public boolean isConst = false;
	public Node num;
	
	public VariableNode(String n)
	{
		name = n;
	}
	public void setNode(Node n)
	{
		num = n;
	}
	
	public Node getNode()
	{
		return num;
	}
	public void setConst(boolean tof)
	{
		isConst = tof;
	}
	public void SetDataType(Token t)
	{
		switch(t.toString())
		{
			case "real": type = dataType.REAL; break;
			case "integer": type = dataType.INT; break;
			case "string" : type = dataType.STRING; break;
			case "character" : type = dataType.CHAR; break;
			case "boolean": type = dataType.BOOL; break;
			default: throw new RuntimeException();
		}
	}
	public String getName()
	{
		return name;
	}
	
	
	public String toString()
	{
		String c;
		if(isConst==false){c="";}
		else{c="CONSTANT";}
		String output = c+" "+type+" "+name+" = ";
		if(num==null){return output;}
		else{return output + num.toString();}
	}
	
	


}