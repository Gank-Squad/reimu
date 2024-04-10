package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;
import com.git.ganksquad.data.IndexableData;
import com.git.ganksquad.exceptions.CannotIndexException;
import com.git.ganksquad.exceptions.InvalidIndexKeyException;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

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
