package com.git.ganksquad.expressions;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.ReimuType;
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
	
	public static Expression fromAndOptimize(List<IfExpression> ifstatements, BlockExpression elseBody) {

		ParseChecks.RequiredNotNull(ifstatements, elseBody);

		if(ifstatements.size() == 0) {
			
			throw new NullPointerException("IfElseExpression created with empty list of IfExpression");
		}

		if(ifstatements.size() == 1 && elseBody.isEmpty()) {
			
			return ifstatements.get(0);
		}
		
		return new IfElseExpression(ifstatements, elseBody);
	}


	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		this.trace();

		ReimuType r = this.ifstatements.get(0).typeCheck(resolver);
		ReimuType t;
		
		Iterator<IfExpression> it = this.ifstatements.iterator();
		
		it.next();
		
		while(it.hasNext()) {

			t = it.next().typeCheck(resolver);

			if(r != SpecialType.VOID && !t.isEqualType(r)) {
				r = SpecialType.VOID;
			}
		}
		
		t = this.elseBody.typeCheck(resolver);

		if(r != SpecialType.VOID && !t.isEqualType(r)) {
			r = SpecialType.VOID;
		}
		
		return r;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		boolean elseShouldRun = true;

		Data r = NoneData.instance;
		
		for(IfExpression i : this.ifstatements) {
			
			r = i.eval(reimuRuntime);
			
			if(reimuRuntime.hasReturnValue()) {
				return reimuRuntime.getReturnValue();
			}

			if(i.conditionWasTrue()) {
				
				elseShouldRun = false;

				break;
			}
		}

		if(reimuRuntime.hasReturnValue()) {
			return reimuRuntime.getReturnValue();
		}

		if(elseShouldRun) {
			
			r = this.elseBody.eval(reimuRuntime);
		}
		
		return r;
	}
	
	@Override
	public String toString() {

		return this.formatToString(
				this.ifstatements.stream()
				.map(x -> x.toString())
				.collect(Collectors.joining(", ")), this.elseBody);
	}
}
