package com.git.ganksquad.data.types;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.git.ganksquad.ReimuTypeResolver;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.impl.ArrayData;
import com.git.ganksquad.data.types.traits.IterableTrait;
import com.git.ganksquad.exceptions.compiler.ReimuCompileException;
import com.git.ganksquad.exceptions.compiler.TypeException;

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

	public static ArrayType fromNbrackets(ReimuType t, int brackets) {

		brackets--;
		ArrayType innerMost = new ArrayType(t);
		for(int i = 0; i < brackets; i++)
			innerMost = new ArrayType(innerMost);

		return innerMost;
	}
	public static  ArrayType fromNbrackets(ReimuType t, List<Integer> sizes) {

		ListIterator<Integer> it = sizes.listIterator(sizes.size());

		ArrayType innerMost = new ArrayType(t, it.previous());

		while(it.hasPrevious()) {
			
			innerMost = new ArrayType(innerMost, it.previous());
		}

		return innerMost;
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
	public ArrayData<Data> getDefaultEmptyValue() {
		
		ArrayList<Data> a = new ArrayList<>();
		
		if(this.size == -1) {
			
			return new ArrayData<Data>(a, this);
		}

		for(int i = 0; i < this.size; i++) {

			a.add(this.containedType.getDefaultEmptyValue());
		}

		return new ArrayData<Data>(a, this);
	}
	
	@Override
	public boolean canBeBool() {
		return true;
	}
	
	@Override
	public String toString() {
		return this.formatToString(this.containedType, "size: " + this.size);
	}
	@Override
	public String getLookupName() {
		return this.formatToString(this.containedType);
	}
	

	public void inferSizes(ArrayType other) throws ReimuCompileException {
		
		ArrayType tType = this;
		ArrayType oType = other;

		while(true) {

			if(tType.size == -1) {

				tType.size = oType.size;
			}
			else if(tType.size != oType.size) {

				throw new TypeException("Cannot infer size from %s and %s", tType, oType);
			}
			
			if(tType.containedType instanceof ArrayType && oType instanceof ArrayType) {
				
				tType = (ArrayType)tType.containedType;
				oType = (ArrayType)oType.containedType;
				
				continue;
			}

			return;
		}
	}
}
