package com.git.ganksquad.expressions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.UserDefinedData;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.ResolvingType;
import com.git.ganksquad.data.types.UserDefinedType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class CreateUserDefinedDataExpression implements Expression {

	private HashMap<String, Expression> namesToExp;
	public String name;
	public List<Expression> values;
	
	
	public CreateUserDefinedDataExpression(String name, List<Expression> val) {
		
		this.name = name;
		this.values = val;
	}
	
	public static CreateUserDefinedDataExpression from(String name, List<Expression>v) {
		
		ParseChecks.RequiredNotNull(name, v);
		
		return new CreateUserDefinedDataExpression(name, v);
	}
	
	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		
		ReimuType t = resolver.resolve(name);
		
		if(!(t instanceof UserDefinedType)) {
			
			throw new TypeException("User type with name %s resolved to %s type", name,  t);
		}
		
		UserDefinedType udt = (UserDefinedType)t;
		

		if(this.values.size() > udt.members.size()) {

			throw new TypeException("User type %s %s is being defined with more expressions than it can hold", this.name, udt);
		}

		namesToExp = new HashMap<String, Expression>();

		Iterator<Expression> exprIter = this.values.iterator();
		Iterator<Entry<String, ReimuType>>  typeIter = ((UserDefinedType)t).members.entrySet().iterator();

		Expression e;
		Entry<String, ReimuType> et1;
		ReimuType et2 = null;

		while(exprIter.hasNext()) {

			if(!typeIter.hasNext()) {

				throw new TypeException("User type %s cannot contain %s",  udt, et2);
			}

			e = exprIter.next();
			et1 = typeIter.next();
			et2 = e.typeCheck(resolver);
			
			if(et1.getValue() instanceof ResolvingType) {
				((ResolvingType)et1.getValue()).resolve(resolver); 
			}
			
			while(!et1.getValue().isAssignableFrom(et2)) {
				
				if(!typeIter.hasNext()) {
					
					throw new TypeException("User type %s cannot contain %s",  udt, et2);
				}

				et1 = typeIter.next();
			}
			
			namesToExp.put(et1.getKey(), e);
		}
		
		return t;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		UserDefinedData dat = new UserDefinedData(this.name);
		
		for (Map.Entry<String, Expression> entry : namesToExp.entrySet())  {
			
			dat.setMember(entry.getKey(), entry.getValue().eval(reimuRuntime));
		}
		
		return dat;
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.name, this.values);
	}
}
