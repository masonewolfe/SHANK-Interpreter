import java.util.HashMap;
import java.util.*;
public class Interpreter
{
	
	public static HashMap<String, CallableNode> functions = new HashMap<String, CallableNode>();
	
	public Interpreter()
	{
		
	}
	
	public void loadHashMap(List<FuncNode> funcs)
	{
		for(FuncNode n:funcs)
		{
			functions.put(n.getName(), n);
		}
		functions.put("Read", new Read());
		functions.put("Write", new Write());
		functions.put("SquareRoot", new SquareRoot());
		functions.put("GetRandom", new GetRandom());
		functions.put("IntegerToReal", new IntegerToReal());
		functions.put("RealToInteger", new RealToInteger());
	}
	
	public void printMap()
	{
		System.out.println(functions);
	}
	
	
	
	
	public static void InterpretFunction(CallableNode func, List<InterpreterDataType> paras) 
	{
		if(func instanceof FuncNode)
		{
			HashMap<String,InterpreterDataType> paramsAndVars = new HashMap<String, InterpreterDataType>();
			FuncNode function = (FuncNode)func;
			
			for(int i =0; i<function.getParameters().size(); i++)
			{
				if(function.parameters.get(i).isConst == false)
				{
					paramsAndVars.put(function.getParameters().get(i).getName(), paras.get(i));
				}
				else
				{
					VariableNode v = function.parameters.get(i);
					if(v.type == VariableNode.dataType.INT)
					{	
						IntDataType temp = (IntDataType) paras.get(i);
						paramsAndVars.put(v.getName(), new IntDataType(temp.value));
					}
					else if(v.type == VariableNode.dataType.REAL)
					{
						FloatDataType temp = (FloatDataType) paras.get(i);
						paramsAndVars.put(v.getName(), new FloatDataType(temp.value));
					}
					else if(v.type == VariableNode.dataType.STRING)
					{
						StringDataType temp = (StringDataType) paras.get(i);
						paramsAndVars.put(v.getName(), new StringDataType(temp.value));
					}
					else if(v.type == VariableNode.dataType.CHAR)
					{
						CharDataType temp = (CharDataType) paras.get(i);
						paramsAndVars.put(v.getName(), new CharDataType(temp.value));
					}
					else if(v.type == VariableNode.dataType.BOOL)
					{
						BooleanDataType temp = (BooleanDataType) paras.get(i);
						paramsAndVars.put(v.getName(), new BooleanDataType(temp.value));
					}
				}
			}
			
			//ADD LOCAL VARIABLES TO THE HASHMAP
			for(VariableNode v : function.getVariables())
			{
				if(v.type == VariableNode.dataType.INT)
				{
					if(v.getNode()!=null)
					{
						IntegerNode n = (IntegerNode) v.getNode();
						paramsAndVars.put(v.getName(), new IntDataType((int)n.getVal()));
					}
					else
					{
						paramsAndVars.put(v.getName(), new IntDataType());
					}
				}
				else if(v.type == VariableNode.dataType.REAL)
				{
					if(v.getNode()!=null)
					{
						FloatNode n = (FloatNode) v.getNode();
						paramsAndVars.put(v.getName(), new FloatDataType(n.getVal()));
					}
					else
					{
						paramsAndVars.put(v.getName(), new FloatDataType());
					}				}
				else if(v.type == VariableNode.dataType.STRING)
				{
					if(v.getNode()!=null)
					{
						StringNode n = (StringNode) v.getNode();
						paramsAndVars.put(v.getName(), new StringDataType(n.getString()));
					}
					else
					{
						paramsAndVars.put(v.getName(), new StringDataType());
					}
				}
				else if(v.type == VariableNode.dataType.CHAR)
				{
					if(v.getNode()!=null)
					{
						CharNode n = (CharNode) v.getNode();
						paramsAndVars.put(v.getName(), new CharDataType(n.getChar()));
					}
					else
					{
						paramsAndVars.put(v.getName(), new CharDataType());
					}				}
				else if(v.type == VariableNode.dataType.BOOL)
				{
					if(v.getNode()!=null)
					{
						BoolNode n = (BoolNode) v.getNode();
						paramsAndVars.put(v.getName(), new BooleanDataType(n.getBool()));
					}
					else
					{
						paramsAndVars.put(v.getName(), new BooleanDataType());
					}				}
			}
			InterpretBlock(paramsAndVars, function.getStatements());
		}
	}
	
	public static void InterpretBlock(HashMap<String,InterpreterDataType> varAndParamMap, List<StatementNode> statements)
	{
		for(StatementNode statement : statements)
		{
			if(statement instanceof FunctionCallNode) 
			{
				FunctionCallNode funcCall = (FunctionCallNode) statement;
				CallableNode funcDefinition = functions.get(funcCall.getName()); //CHECK IF THE FUNCTION EXISTS AND FIND ITS DEFINITION NODE
				
				if(funcDefinition==null) //IF IT DOESNT EXIST
				{
					throw new RuntimeException();
				}
				else
				{
					List<InterpreterDataType> values = new ArrayList<InterpreterDataType>(); //ALLOCATE NEW LIST OF IDTS
					for(ParamNode parameter : funcCall.getParams()) //FOR EVERY PARAMNODE
					{
						if(parameter.getNode() instanceof VariableReferenceNode)//IF ITS A VAR REF, GET THE IDT FROM THE HASHMAP, IT WILL BE PASSED BY REFERENCE!!!
						{
							VariableReferenceNode varRef = (VariableReferenceNode) parameter.getNode();
							InterpreterDataType output = varAndParamMap.get(varRef.toString());
							if(output!=null)
							{
								if(parameter.isVar==true)
								{
									values.add(output);
								}
								else if(output instanceof IntDataType)
								{
									IntDataType val = (IntDataType) output;
									values.add(new IntDataType(val.value));
								}
								else if(output instanceof FloatDataType)
								{
									FloatDataType val = (FloatDataType) output;
									values.add(new FloatDataType(val.value));
								}
								else if(output instanceof StringDataType)
								{
									StringDataType val = (StringDataType) output;
									values.add(new StringDataType(val.value));
								}
								else if(output instanceof CharDataType)
								{
									CharDataType val = (CharDataType) output;
									values.add(new CharDataType(val.value));
								}
								else if(output instanceof BooleanDataType)
								{
									BooleanDataType val = (BooleanDataType) output;
									values.add(new BooleanDataType(val.value));
								}
							}
							else{throw new RuntimeException();}
						}
						else if(parameter.getNode() instanceof FloatNode) //IF ITS A NUMBER JUST CREATE A NEW IDT AND ADD IT
						{
							FloatNode num = (FloatNode) parameter.getNode();
							values.add(new FloatDataType(num.getVal()));
						}
						else if(parameter.getNode() instanceof IntegerNode)
						{
							IntegerNode num = (IntegerNode) parameter.getNode();
							values.add(new IntDataType((int)num.getVal()));
						}
						else if(parameter.getNode() instanceof StringNode)
						{
							StringNode num = (StringNode) parameter.getNode();
							values.add(new StringDataType(num.getString()));
						}
						else if(parameter.getNode() instanceof CharNode)
						{
							CharNode num = (CharNode) parameter.getNode();
							values.add(new CharDataType(num.getChar()));
						}
						else if(parameter.getNode() instanceof BoolNode)
						{
							BoolNode num = (BoolNode) parameter.getNode();
							values.add(new BooleanDataType(num.getBool()));
						}
						else if(parameter.getNode() instanceof CompareNode)
						{
							boolean b = EvaluateBooleanExp((CompareNode)parameter.getNode(), varAndParamMap);
							values.add(new BooleanDataType(b));
						}
						else if(parameter.getNode() instanceof MathOpNode)
						{
							try
							{
								String s = ResolveString(parameter.getNode(), varAndParamMap);
								values.add(new StringDataType(s));
							}catch(Exception e)
							{
								try
								{
									float f = Resolve(parameter.getNode(), varAndParamMap);
									values.add(new FloatDataType(f));
								}catch(Exception e2)
								{
									throw new RuntimeException("INVALID MATH OPERATION");
								}
							}
						}
					}
					
					if(funcDefinition instanceof FuncNode) //IF ITS A USER DEFINED FUNCTION
					{
						FuncNode userFunc = (FuncNode) funcDefinition;
						if(funcCall.getParams().size() == userFunc.getParameters().size()) //CHECK THAT THE FUNCTION CALL USES THE CORRECT AMOUNT OF PARAMETERS AS THE FUNCTION REQUIRES
						{
							for(int i=0; i<funcCall.getParams().size(); i++)
							{
								if(values.get(i) instanceof IntDataType && userFunc.getParameters().get(i).type == VariableNode.dataType.INT)
								{
									//do nothing
								}
								else if(values.get(i) instanceof FloatDataType && userFunc.getParameters().get(i).type == VariableNode.dataType.REAL)
								{
									//do nothing
								}
								else if(values.get(i) instanceof StringDataType && userFunc.getParameters().get(i).type == VariableNode.dataType.STRING)
								{
									//do nothing
								}
								else if(values.get(i) instanceof CharDataType && userFunc.getParameters().get(i).type == VariableNode.dataType.CHAR)
								{
									//do nothing
								}
								else if(values.get(i) instanceof BooleanDataType && userFunc.getParameters().get(i).type == VariableNode.dataType.BOOL)
								{
									// do nothing
								}
								else
								{
									//if you make it in here data types don't match
									throw new RuntimeException("DATATYPES FOR PARAMETERS DONT MATCH");
								}
							}
							InterpretFunction(userFunc, values); //CALL INTERPRET FUNCTION WITH THE LIST OF IDTS
						}
						else
						{
							throw new RuntimeException("IMPROPER NUMBER OF PARAMETERS USED");
						}
					}
					else if(funcDefinition instanceof BuiltInFunctionNode) //IF ITS A BUILT IN FUNCTION
					{
						BuiltInFunctionNode builtIn = (BuiltInFunctionNode) funcDefinition;
						builtIn.Execute(values); //PASS LIST TO EXECUTE
					}
				}
			}
			else if(statement instanceof AssignmentNode)
			{
				AssignmentNode assign = (AssignmentNode) statement;
				InterpreterDataType target = varAndParamMap.get(assign.getTarget().toString());
				if(target==null)
				{
					throw new RuntimeException();
				}
				
				if(target instanceof IntDataType)
				{
					float answer = Resolve(assign.getExpressionNode(), varAndParamMap);
					int ans = (int) answer;
					target.fromString(Integer.toString(ans));
					varAndParamMap.put(assign.getTarget().toString(), target);
				}
				else if(target instanceof FloatDataType)
				{
					float answer = Resolve(assign.getExpressionNode(), varAndParamMap);
					target.fromString(Float.toString(answer));
					varAndParamMap.put(assign.getTarget().toString(), target);
				}
				else if(target instanceof CharDataType)
				{
					char answer = ResolveChar(assign.getExpressionNode(), varAndParamMap);
					target.fromString(Character.toString(answer));
					varAndParamMap.put(assign.getTarget().toString(), target);
				}
				else if(target instanceof StringDataType)
				{
					String answer = ResolveString(assign.getExpressionNode(), varAndParamMap);
					target.fromString(answer);
					varAndParamMap.put(assign.getTarget().toString(), target);
				}
				else //must be boolean data type
				{
					boolean answer = ResolveBoolean(assign.getExpressionNode(), varAndParamMap);
					target.fromString(Boolean.toString(answer));
					varAndParamMap.put(assign.getTarget().toString(), target);
				}
			}
			else if(statement instanceof IfNode)
			{
				IfNode ifstatement = (IfNode) statement;
				Node comparison = ifstatement.GetComparison();
				while(comparison!=null && ResolveBoolean(comparison, varAndParamMap)==false && ifstatement.GetNext()!=null)
				{
					ifstatement = ifstatement.GetNext();
					comparison = ifstatement.GetComparison();
				}
				
				if(comparison==null || ResolveBoolean(comparison, varAndParamMap)==true)
				{
					InterpretBlock(varAndParamMap, ifstatement.GetStatements());
				}
			}
			else if(statement instanceof WhileNode)
			{
				WhileNode whileStatement = (WhileNode) statement;
				Node comparison = whileStatement.GetComparison();
				while(ResolveBoolean(comparison, varAndParamMap)==true)
				{
					InterpretBlock(varAndParamMap, whileStatement.GetStatements());
				}
			}
			else if(statement instanceof RepeatNode)
			{
				RepeatNode repeatStatement = (RepeatNode) statement;
				Node comparison = repeatStatement.GetComparison();
				do
				{
					InterpretBlock(varAndParamMap, repeatStatement.GetStatements());
				}while(ResolveBoolean(comparison, varAndParamMap)==false);
				
			}
			else if(statement instanceof ForNode)
			{
				ForNode forStatement = (ForNode) statement;
				Node start = forStatement.GetStartValue();
				Node end = forStatement.GetEndValue();
				InterpreterDataType variable = varAndParamMap.get(forStatement.GetVariable().toString());
				if(variable!=null && variable instanceof IntDataType)
				{
					int startVal, endVal;
					if(start instanceof VariableReferenceNode)
					{
						InterpreterDataType temp =  varAndParamMap.get(start.toString());
						if(temp!=null && temp instanceof IntDataType)
						{
							startVal = Integer.parseInt(temp.toString());
						}
						else
						{
							throw new RuntimeException("RANGE USED IN FOR LOOP IS NOT AN INT OR DOES NOT EXIST");
						}
					}
					else if(start instanceof IntegerNode)
					{
						startVal = (int) start.getVal(); 
					}
					else
					{
						throw new RuntimeException("VALUE USED IN FOR LOOP RANGE IS NOT AN INT");
					}
					
					if(end instanceof VariableReferenceNode)
					{
						InterpreterDataType temp =  varAndParamMap.get(end.toString());
						if(temp!=null && temp instanceof IntDataType)
						{
							endVal = Integer.parseInt(temp.toString());
						}
						else
						{
							throw new RuntimeException("RANGE USED IN FOR LOOP IS NOT AN INT OR DOES NOT EXIST");
						}
					}
					else if(end instanceof IntegerNode)
					{
						endVal = (int) end.getVal();
					}
					else
					{
						throw new RuntimeException("VALUE USED IN FOR LOOP RANGE IS NOT AN INT");
					}
					
					for(int i = startVal; i<=endVal;i++)
					{
						variable.fromString(Integer.toString(i));
						varAndParamMap.put(forStatement.GetVariable().toString(), variable);
						InterpretBlock(varAndParamMap, forStatement.GetStatements());
					}
				}
				else
				{
					throw new RuntimeException("VALUE USED IN FOR LOOP IS NOT AN INTEGER");
				}
			}
		}
	}
	

	
	
	//TRAVERSE THE TREE SOLVING EACH PARENT NODE'S CHILDRENS WHICH MAY OR MAYNOT BE A SUBTREE THEMSELVES
	public static float Resolve(Node root, HashMap<String,InterpreterDataType> VariableMap)
	{
		if(root instanceof FloatNode || root instanceof IntegerNode)
		{
			return root.getVal();
		}
		else if(root instanceof VariableReferenceNode)
		{
			InterpreterDataType val = VariableMap.get(root.toString());
			if(val==null)
			{
				throw new RuntimeException();
			}
			
			if(val instanceof IntDataType)
			{
				IntDataType value = (IntDataType) val;
				return (float) value.value;
			}
			else if(val instanceof FloatDataType)
			{
				FloatDataType value = (FloatDataType) val;
				return value.value;
			}
			else
			{
				throw new RuntimeException();
			}
		}
		else if(root instanceof MathOpNode)
		{
			MathOpNode temp = (MathOpNode) root;
			switch(temp.getOp())
			{
				case MULTIPLIED: return Resolve(temp.getLeft(), VariableMap) * Resolve(temp.getRight(), VariableMap);
				case DIVIDED: return Resolve(temp.getLeft(), VariableMap) / Resolve(temp.getRight(), VariableMap);
				case PLUS: return Resolve(temp.getLeft(), VariableMap) + Resolve(temp.getRight(), VariableMap);
				case MINUS: return Resolve(temp.getLeft(), VariableMap) - Resolve(temp.getRight(), VariableMap);
				case MOD: return Resolve(temp.getLeft(), VariableMap) % Resolve(temp.getRight(), VariableMap);
				default: throw new RuntimeException();
			}
		}
		else
		{
			throw new RuntimeException();
		}

	}
	
	public static char ResolveChar(Node root, HashMap<String,InterpreterDataType> VariableMap)
	{
		if(root instanceof CharNode)
		{
			CharNode c = (CharNode)root;
			return c.getChar();
		}
		else if(root instanceof VariableReferenceNode)
		{
			InterpreterDataType val = VariableMap.get(root.toString());
			if(val==null || !(val instanceof CharDataType))
			{
				throw new RuntimeException();
			}
			CharDataType temp = (CharDataType)val;
			return temp.value;
		}
		else
		{
			throw new RuntimeException();
		}
	}
	
	public static String ResolveString(Node root, HashMap<String,InterpreterDataType> VariableMap)
	{
		if(root instanceof StringNode)
		{
			StringNode s = (StringNode)root;
			return s.getString();
		}
		else if(root instanceof CharNode)
		{
			CharNode c = (CharNode)root;
			return Character.toString(c.getChar());
		}
		else if(root instanceof VariableReferenceNode)
		{
			InterpreterDataType val = VariableMap.get(root.toString());
			if(val==null || (!(val instanceof StringDataType) && !(val instanceof CharDataType)))
			{
				throw new RuntimeException();
			}
			return val.toString();
		}
		else if(root instanceof MathOpNode)
		{
			MathOpNode temp = (MathOpNode) root;
			switch(temp.getOp())
			{
				case PLUS: return ResolveString(temp.getLeft(), VariableMap) + ResolveString(temp.getRight(), VariableMap);
				default: throw new RuntimeException("INVALID STRING OPERATION");
			}
		}
		else
		{
			throw new RuntimeException();
		}
	}
	
	public static boolean ResolveBoolean(Node root, HashMap<String,InterpreterDataType> VariableMap)
	{
		if(root instanceof BoolNode)
		{
			BoolNode b = (BoolNode) root;
			return b.getBool();
		}
		else if(root instanceof VariableReferenceNode)
		{
			InterpreterDataType idt = VariableMap.get(root.toString());
			if(idt == null || !(idt instanceof BooleanDataType))
			{
				throw new RuntimeException();
			}
			BooleanDataType b = (BooleanDataType) idt;
			return b.value;
		}
		else if(root instanceof CompareNode)
		{
			return EvaluateBooleanExp((CompareNode)root, VariableMap);
		}
		else
		{
			throw new RuntimeException();
		}
	}
	
	
	public static boolean EvaluateBooleanExp(CompareNode comparison, HashMap<String,InterpreterDataType> VariableMap)
	{
		try
		{
			float left = Resolve(comparison.getLeft(), VariableMap);
			float right = Resolve(comparison.getRight(), VariableMap);
			switch(comparison.getComp())
			{
				case EQUAL: if(left==right){return true;}else{return false;}
				case NOTEQUAL: if(left!=right){return true;}else{return false;}
				case GREATERTHAN: if(left>right){return true;}else{return false;}
				case LESSTHAN: if(left<right){return true;}else{return false;}
				case GREATERTHANOREQUALTO: if(left>=right){return true;}else{return false;}
				case LESSTHANOREQUALTO: if(left<=right){return true;}else{return false;}
				default: throw new RuntimeException();
			}
		}catch(Exception e){}
		
		try
		{
			String left = ResolveString(comparison.getLeft(), VariableMap);
			String right = ResolveString(comparison.getRight(), VariableMap);
			switch(comparison.getComp())
			{
				case EQUAL: if(left.equals(right)){return true;}else{return false;}
				case NOTEQUAL: if(!left.equals(right)){return true;}else{return false;}
				default: throw new RuntimeException();
			}
		}catch(Exception e){}
		
		try
		{
			char left = ResolveChar(comparison.getLeft(), VariableMap);
			char right = ResolveChar(comparison.getRight(), VariableMap);
			switch(comparison.getComp())
			{
				case EQUAL: if(left==right){return true;}else{return false;}
				case NOTEQUAL: if(left!=right){return true;}else{return false;}
				default: throw new RuntimeException();
			}
		}catch(Exception e){}
		
		try
		{
			boolean left = ResolveBoolean(comparison.getLeft(), VariableMap);
			boolean right = ResolveBoolean(comparison.getRight(), VariableMap);
			switch(comparison.getComp())
			{
				case EQUAL: if(left==right){return true;}else{return false;}
				case NOTEQUAL: if(left!=right){return true;}else{return false;}
				default: throw new RuntimeException();
			}
		}catch(Exception e){}
		
		throw new RuntimeException();
	}
}