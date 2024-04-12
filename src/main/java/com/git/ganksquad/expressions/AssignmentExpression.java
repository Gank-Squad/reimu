package com.git.ganksquad.expressions;

import org.tinylog.Logger;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class AssignmentExpression implements Expression {
	
	public String symbolName;
	public Expression value;
	public boolean declare;

	public AssignmentExpression(String name, Expression value, boolean declare) {
		
		this.symbolName = name;
		this.value = value;
		this.declare = declare;
	}
	
	public static AssignmentExpression declare(String name, Expression value) {
		
		ParseChecks.RequiredNotNull(name, value);
		
		return new AssignmentExpression(name, value, true);
	}

	public static AssignmentExpression assign(String name, Expression value) {
		
		ParseChecks.RequiredNotNull(name, value);

		return new AssignmentExpression(name, value, false);
	}


	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		ReimuType t = this.value.typeCheck(resolver);
		
		if(this.declare) {
			
			if(t == ReimuType.UNKNOWN) {
				throw new TypeException(String.format("Could not resolve the type of variable %s", this.symbolName));
			}

			if(t != ReimuType.NONE) {

				resolver.declare(this.symbolName, t);
			} 
			else {

				// TODO: fix this
				Logger.warn("Symbol {} is declared with None type, but we are making it NUMERIC, remember to fix this ", this.symbolName);

				resolver.declare(this.symbolName, ReimuType.NUMERIC);
			}
			
			return ReimuType.NONE;
		}

		return t;
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
				this.symbolName,
				this.value,
				this.declare
			);
	}
}
