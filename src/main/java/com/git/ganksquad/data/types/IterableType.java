package com.git.ganksquad.data.types;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.NoneData;
import com.git.ganksquad.data.types.traits.IterableTrait;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;

public class IterableType implements AggregateType, SingleTypeContainer, IterableTrait {

	private ReimuType containedType;
	
	public IterableType(ReimuType elements) {
		
		this.containedType = elements;
	}
	
	@Override
	public ReimuType getContainedType() {
		return this.containedType;
	}

	@Override
	public boolean isAssignableFrom(ReimuType other) {

		if(other instanceof IterableType) {
			
			return this.containedType.isAssignableFrom(((IterableType)other).containedType);
		}

		if(other instanceof ArrayType) {
			
			return this.containedType.isAssignableFrom(((ArrayType)other).getContainedType());
		}

		return false;
	}
	
	
	@Override
	public boolean isEqualType(ReimuType other) {
		
		return other instanceof IterableType &&
			 this.containedType.isEqualType(((IterableType)other).containedType);
	}

	@Override
	public void resolve(ReimuTypeResolver resolver) throws ReimuCompileException{
	}

	@Override
	public ReimuType produces() {
		return this.containedType;
	}
	@Override
	public String toString() {
		return this.formatToString(this.containedType);
	}
	@Override
	public String getLookupName() {
		return this.toString();
	}

	@Override
	public Data getDefaultEmptyValue() {
		return NoneData.instance;
	}
	
	@Override
	public boolean canBeBool() {
		return false;
	}
}
