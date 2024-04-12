package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;
import com.git.ganksquad.data.IndexableData;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.CannotIndexException;
import com.git.ganksquad.exceptions.runtime.InvalidIndexKeyException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class ArrayIndexExpression implements Expression {
	
	public Expression left;
	public Expression right;
	
	public ArrayIndexExpression(Expression l, Expression r) {
		this.left = l;
		this.right = r;
	}
	
	public static ArrayIndexExpression from(Expression l, Expression r) {
		
		ParseChecks.RequiredNotNull(l, r);
		
		return new ArrayIndexExpression(l, r);
	}

	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		switch(right.typeCheck(resolver)) {
			
		case NONE:
			throw new TypeException("Cannot index with unknown type");

		default:
			return left.typeCheck(resolver);
		}

	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {
		
		Data l = this.left.eval(reimuRuntime);
		
		if(!(l instanceof IndexableData)) {
			
			throw CannotIndexException.typeNotIndexable(l.getClass());
		}
		
		Data r = this.right.eval(reimuRuntime);
		
		if(!(r instanceof IndexKeyData)) {
			
			throw InvalidIndexKeyException.invalidType(r.getClass());
		}
		
		return (Data) ((IndexableData<?>)l).get((IndexKeyData)r);
	}

	@Override
	public String toString() {
		return this.formatToString(this.left, this.right);
	}
}
