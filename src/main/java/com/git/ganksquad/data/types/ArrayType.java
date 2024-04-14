package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.types.traits.IterableTrait;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public class ArrayType implements AggregateType, SingleTypeContainer, IterableTrait {
	
	private ReimuType containedType;
	
	public ArrayType(ReimuType elements) {
		
		this.containedType = elements;
	}
	
	@Override
	public ReimuType getContainedType() {
		return this.containedType;
	}

	@Override
	public boolean isEqualType(ReimuType other) {
		return (other instanceof ArrayType) && 
			this.containedType.isEqualType(((ArrayType)other).containedType);
	}

	@Override
	public boolean isAssignableFrom(ReimuType other) {
		
		return(other instanceof ArrayType) &&
			this.containedType.isAssignableFrom(((ArrayType)other).containedType);
	}

	@Override
	public ReimuType produces() {
		return this.containedType;
	}

	@Override
	public void resolve(ReimuTypeResolver resolver) throws ReimuCompileException{
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.containedType);
	}
}
