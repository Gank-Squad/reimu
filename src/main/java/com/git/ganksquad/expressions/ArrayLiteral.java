package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.ArrayData;
import com.git.ganksquad.data.types.AggregateType;
import com.git.ganksquad.data.types.PrimitiveType;
import com.git.ganksquad.data.types.SpecialType;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.exceptions.compiler.NoneTypeException;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;
import com.git.ganksquad.exceptions.runtime.ReimuRuntimeException;

public class ArrayLiteral implements Expression {
	
	List<Expression> arr;
	
	public ArrayLiteral(List<Expression> l) {
		
		this.arr = l;
	}

	
	public static ArrayLiteral from(List<Expression> expr) {
		
		ParseChecks.RequiredNotNull(expr);
		
		return new ArrayLiteral(expr);
	}
	
	@Override
	public ReimuType typeCheck(ReimuTypeResolver resolver) throws ReimuCompileException {
		
		this.trace();

		if(this.arr.size() == 0) {
			
			return AggregateType.UNKNOWN_ARRAY_TYPE;
		}
		
		ReimuType r = this.arr.get(0).typeCheck(resolver);

		if(r == SpecialType.VOID) {

			throw new NoneTypeException("Array cannot contain None resolving expression");
		}

		Iterator<Expression> it = this.arr.iterator();
		
		it.next();

		while (it.hasNext()) {

			ReimuType e = it.next().typeCheck(resolver);
			
			if(r != e) {
				throw new TypeException("Array cannot be created with different types: %s and %s", r, e);
			}
		}
		
		return r;
	}

	@Override
	public Data eval(ReimuRuntime reimuRuntime) throws ReimuRuntimeException {

		List<Data> l = new ArrayList<Data>(this.arr.size());
		
		for(Expression e : this.arr) {

			Data d = e.eval(reimuRuntime); 

			l.add(d);
		}
		
		return new ArrayData<Data>(l);
	}

	@Override
	public String toString() {
		return this.formatToString(this.arr);
	}
}
