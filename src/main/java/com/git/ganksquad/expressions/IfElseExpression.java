package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class IfElseExpression implements Expression {
	
	public List<IfExpression> ifstatements;
	public BlockExpression elseBody;

	public IfElseExpression(List<IfExpression> ifStatements, BlockExpression elseBody) {

		this.ifstatements = ifStatements;
		this.elseBody = elseBody;
	}
	
	public static IfElseExpression from(List<IfExpression> ifstatements, BlockExpression elseBody) {
		
		if(ifstatements.size() == 0) {
			
			throw new NullPointerException("IfElseExpression created with empty list of IfExpression");
		}
		
		ParseChecks.RequiredNotNull(ifstatements, elseBody);
		
		return new IfElseExpression(ifstatements, elseBody);
	}

	public static IfElseExpression from(List<IfExpression> ifstatements, List<Expression> elseBody) {
		
		if(ifstatements.size() == 0) {
			
			throw new NullPointerException("IfElseExpression created with empty list of IfExpression");
		}

		ParseChecks.RequiredNotNull(ifstatements, elseBody);

		return new IfElseExpression(ifstatements, BlockExpression.fromList(elseBody));
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		ReimuType r = this.ifstatements.get(0).typeCheck(resolver);
		
		for(Expression e : this.ifstatements) {
			
			if(e.typeCheck(resolver) != r) {
				throw new TypeException("IfElse could resolve to different type");
			}
		}
		
		if(this.elseBody.typeCheck(resolver) != r) {
				throw new TypeException("IfElse could resolve to different type");
		}
		
		return r;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		boolean elseShouldRun = true;

		Data r = NoneData.instance;
		
		for(IfExpression i : this.ifstatements) {
			
			r = i.eval(reimuRuntime);
			
			if(i.conditionWasTrue()) {
				
				elseShouldRun = false;

				break;
			}
		}

		if(elseShouldRun) {
			
			r = this.elseBody.eval(reimuRuntime);
		}
		
		return r;
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.ifstatements, this.elseBody);
	}
}
