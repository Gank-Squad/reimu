package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.tinylog.Logger;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class BlockExpression implements Expression {

	public boolean explicitReturn = false;

	public List<Expression> expressions;
	
	public BlockExpression() {

		this.expressions = new ArrayList<Expression>();
	}

	public BlockExpression(List<Expression> expr) {
		
		this.expressions = expr.stream().
				filter(x -> !(x instanceof NoneExpression)).
				toList();
	}
	
	public boolean isEmpty() {
		return this.expressions.isEmpty();
	}
	
	public static BlockExpression empty() {
		
		return new BlockExpression();
	}

	public static BlockExpression fromList(List<Expression> expr) {
		
		ParseChecks.RequiredNotNull(expr);
		
		return new BlockExpression(expr);
	}


	public ReimuType typeCheckPartial(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		this.trace();

		boolean doesReturn = false;

		ReimuType t = SpecialType.VOID;
		
		for(Expression e : this.expressions) {

			t = e.typeCheck(resolver);
			
			if(e instanceof ReturnExpression) {
				
				doesReturn = true;
			}
		}
		
		if(this.explicitReturn) {

			if( doesReturn)
				return t;
			return SpecialType.VOID;
		}

		return t;
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {

		ReimuTypeResolver scope = resolver.subScope();

		return this.typeCheckPartial(scope);
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
			
			if(runtime.hasReturnValue()) {
				return runtime.getReturnValue();
			}

			if(e instanceof ReturnExpression) {
				
				runtime.setReturnValue(result);
				
				return result;
			}
		}
		
		if(this.explicitReturn) {

			return NoneData.instance;
		}

		return result;
	}

	@Override
	public Data eval(ReimuRuntime runtime) throws ReimuRuntimeException {
		
		if(this.expressions.size() == 0) 
			return NoneData.instance;

		ReimuRuntime scope = runtime.subScope();
		
		Data r = this.evalPartial(scope);
		
		if(scope.hasReturnValue()) {
			runtime.setReturnValue(scope.getReturnValue());
			return runtime.getReturnValue();
		}
		return r;
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.expressions.stream()
				.map(x -> x.toString())
				.collect(Collectors.joining(", ")));
		}
}
