package com.git.ganksquad.data.types;

import com.git.ganksquad.data.types.traits.IterableTrait;

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

		return other instanceof ArrayType && 
			this.containedType.isAssignableFrom(((ArrayType)other).containedType);
	}

	@Override
	public boolean isAssignableFrom(ReimuType other) {
		
		if(other instanceof ArrayType) {
			return this.containedType.isAssignableFrom(((ArrayType)other).containedType);
		}

		return false;
	}

	@Override
	public ReimuType produces() {
		return this.containedType;
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.containedType);
	}
}
