package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

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
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data r = this.value.eval(reimuRuntime);
		
		if(this.declare) {
			
			reimuRuntime.declare(this.symbolName, r);	

		} else {
			
			reimuRuntime.assign(this.symbolName, r);	
			
			return r;
		}
		
		return NoneData.instance;
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
