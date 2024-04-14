package com.git.ganksquad.expressions;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.IndexKeyData;
import com.git.ganksquad.data.IndexableData;
import com.git.ganksquad.data.types.ArrayType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.CannotIndexException;
import com.git.ganksquad.exceptions.runtime.InvalidIndexKeyException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class ArrayIndexExpression implements Expression, AssignableExpression {
	
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
		
		this.trace();

		if(right.typeCheck(resolver) == SpecialType.VOID) {
			
			throw new TypeException("Cannot index with void type");
		}
		
		ReimuType t =  left.typeCheck(resolver);
		
		if(t instanceof ArrayType) {
			
			return ((ArrayType)t).getContainedType();
		}

		throw new TypeException("Cannot index non-array type: %s", t);
	}


	@Override
	public Data evalAssign(ReimuRuntime reimuRuntime, Data assign) throws ReimuRuntimeException {

		Data l = this.left.eval(reimuRuntime);
		
		if(!(l instanceof IndexableData)) {
			
			throw CannotIndexException.typeNotIndexable(l.getClass());
		}
		
		Data r = this.right.eval(reimuRuntime);
		
		if(!(r instanceof IndexKeyData)) {
			
			throw InvalidIndexKeyException.invalidType(r.getClass());
		}

		@SuppressWarnings("unchecked")
		IndexableData<Data> index = (IndexableData<Data>)l;

		Data old = index.get((IndexKeyData)r);

		assign = assign.castTo(old.getType());

		index.set((IndexKeyData)r, assign);

		return assign;
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
