package com.git.ganksquad.expressions;

import java.util.ArrayList;
import java.util.List;

import com.git.ganksquad.ParseChecks;
import com.git.ganksquad.ReimuRuntime;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.ArrayData;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

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
