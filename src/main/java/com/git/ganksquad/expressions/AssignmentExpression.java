package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.compiler.NoneTypeException;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class AssignmentExpression implements Expression {
	
	public ReimuType type;
	public String symbolName;
	public Expression value;
	public boolean declare;

	public AssignmentExpression(ReimuType t, String name, Expression value, boolean declare) {
		
		this.type = t;
		this.symbolName = name;
		this.value = value;
		this.declare = declare;
	}
	
	public static AssignmentExpression declare(ReimuType t, String name, Expression value) {
		
		ParseChecks.RequiredNotNull(t, name, value);
		
		return new AssignmentExpression(t, name, value, true);
	}

	public static AssignmentExpression assign(ReimuType t, String name, Expression value) {
		
		ParseChecks.RequiredNotNull(t, name, value);

		return new AssignmentExpression(t, name, value, false);
	}


	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		ReimuType t = this.value.typeCheck(resolver);
		
		if(this.type == ReimuType.UNKNOWN) {

			if(t == ReimuType.UNKNOWN) {
				throw new TypeException(String.format("Could not resolve the type of variable %s", this.symbolName));
			}

			if(t == ReimuType.NONE) {

				throw new NoneTypeException(String.format("Tried to declare variable %s with None type",  this.symbolName));
			} 

			this.type = t;
		}
		
		if(this.declare) {
			
			resolver.declare(this.symbolName, this.type);
			
			return ReimuType.NONE;
		}

		return this.type;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data r = this.value.eval(reimuRuntime);
		
		if(this.declare) {
			
			reimuRuntime.declare(this.symbolName, r);	

			return NoneData.instance;

		}

		reimuRuntime.assign(this.symbolName, r);	

		return r;
	}

	@Override
	public String toString() {
		
		return this.formatToString( 
				this.type,
				this.symbolName,
				this.value,
				this.declare
			);
	}
}
