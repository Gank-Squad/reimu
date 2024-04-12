package com.git.ganksquad.data.types;

public class ArrayType extends AggregateType {
	
	private TestReimuType containedType;
	
	public ArrayType(TestReimuType elements) {
		
		this.containedType = elements;
	}
	
	public TestReimuType getContained() {
		return this.containedType;
	}

	@Override
	public boolean isBooleanEvaluable() {
		return false;
	}

}
