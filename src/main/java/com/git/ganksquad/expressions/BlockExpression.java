package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class BlockExpression implements Expression {

	public List<Expression> expressions;
	
	public BlockExpression() {

		this.expressions = new ArrayList<Expression>();
	}

	public BlockExpression(List<Expression> expr) {
		
		this.expressions = expr.stream().
				filter(x -> !(x instanceof NoneExpression)).
				toList();
	}
	
	public static BlockExpression empty() {
		
		return new BlockExpression();
	}

	public static BlockExpression fromList(List<Expression> expr) {
		
		ParseChecks.RequiredNotNull(expr);
		
		return new BlockExpression(expr);
	}

	/**
	 * Evaluates this block without making a new scope.
	 * 
	 * This trusts that the caller has already done so
	 */
	public Data evalPartial(ReimuRuntime runtime) throws ReimuRuntimeException {

		Data result = NoneData.instance;
		
		for(Expression e : this.expressions) {
			
			result = e.eval(runtime);
		}
		
		return result;
	}

	@Override
	public Data eval(ReimuRuntime runtime) throws ReimuRuntimeException {
		
		ReimuRuntime scope = runtime.subScope();
		
		return this.evalPartial(scope);
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.expressions.stream()
				.map(x -> x.toString())
				.collect(Collectors.joining(", ")));
		}
}
