package com.git.ganksquad.data.types;

public class IterableType extends AggregateType implements SingleTypeContainer {

	private TestReimuType containedType;
	
	public IterableType(TestReimuType elements) {
		
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
				return true;

			default:
				return false;
		}
	}
}
