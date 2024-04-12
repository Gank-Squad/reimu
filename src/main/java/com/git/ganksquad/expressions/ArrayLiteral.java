package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.ArrayData;
import com.git.ganksquad.data.types.TestReimuType;
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
		
		if(this.arr.size() == 0) {
			
			return ReimuType.UNKNOWN;
		}
		
		ReimuType r = this.arr.get(0).typeCheck(resolver);
		
		
		for(Expression e : this.arr) {
			
			ReimuType t = e.typeCheck(resolver);
			
			if(t == ReimuType.NONE) {

				throw new NoneTypeException("Array cannot contain None resolving expression");
			}

			if(t != r) {
				throw new TypeException("Array cannot be created with different types");
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
