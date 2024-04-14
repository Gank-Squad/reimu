package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.types.traits.IterableTrait;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public class ArrayType implements AggregateType, SingleTypeContainer, IterableTrait {
	
	public int size;
	private ReimuType containedType;
	
	public ArrayType(ReimuType elements) {
		
		this(elements, -1);
	}

	public ArrayType(ReimuType elements, int size) {
		
		this.containedType = elements;
		this.size = size;
	}
	public ArrayType(ReimuType elements, String size) {
		
		this(elements, (int)PrimitiveType.INT.getValueFromString(size));
	}
	
	@Override
	public ReimuType getContainedType() {
		return this.containedType;
	}

	@Override
	public boolean isEqualType(ReimuType other) {
		return (other instanceof ArrayType) && 
			(this.size == ((ArrayType)other).size) &&
			this.containedType.isEqualType(((ArrayType)other).containedType);
	}

	@Override
	public boolean isAssignableFrom(ReimuType other) {
		
		return(other instanceof ArrayType) &&
			((this.size == -1) || (this.size == ((ArrayType)other).size)) &&
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
		return this.formatToString(this.containedType, "size: " + this.size);
	}
	@Override
	public String getLookupName() {
		return this.formatToString(this.containedType);
	}
}
