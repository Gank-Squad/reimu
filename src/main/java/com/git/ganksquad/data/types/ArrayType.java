package com.git.ganksquad.data.types;

public class ArrayType extends AggregateType implements SingleTypeContainer {
	
	private TestReimuType containedType;
	
	public ArrayType(TestReimuType elements) {
		
		this.containedType = elements;
	}
	
	@Override
	public TestReimuType getContainedType() {
		return this.containedType;
	}

	@Override
	public boolean isCompatibleWith(ReimuTypeEnumValue other) {
		switch(other) {

			case ITERABLE:
			case INDEXABLE:
				return true;

			case STRING:
				return this.containedType == PrimitiveType.CHAR;

			default:
				return false;
		}
	}

}