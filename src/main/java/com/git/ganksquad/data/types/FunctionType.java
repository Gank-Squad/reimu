package com.git.ganksquad.data.types;

import java.util.Iterator;
import java.util.List;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.FunctionData;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;


public class FunctionType implements AggregateType {
	
	public ReimuType returnType;

	public List<ReimuType> argumentTypes;
	
	public FunctionType(ReimuType rt, List<ReimuType> ags) {
		
		this.returnType = rt;
		this.argumentTypes = ags;
	}

	@Override
	public boolean isAssignableFrom(ReimuType other) {
		return false;
	}

	@Override
	public boolean isEqualType(ReimuType other) {

		if (!(other instanceof FunctionType)){
			
			return false;
		}

		FunctionType o = (FunctionType)other;
		
		if(o.argumentTypes.size() != this.argumentTypes.size()) {
			return false;
		}
		
		if(!this.returnType.isEqualType(o.returnType)) {
			return false;
		}
		
		Iterator<ReimuType> t1 = this.argumentTypes.iterator();
		Iterator<ReimuType> t2 = o.argumentTypes.iterator();
		
		while(t1.hasNext()) {
			
			if(!t1.next().isEqualType(t2.next())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void resolve(ReimuTypeResolver resolver) throws ReimuCompileException{
	}
	
	@Override
	public Data getDefaultEmptyValue() {
		return NoneData.instance;
	}
	
	@Override
	public String getLookupName() {
		return this.toString();
	}

	@Override
	public String toString() {
		return this.formatToString(this.returnType, this.argumentTypes);
	}
}
