package com.git.ganksquad.data.types;

public interface AggregateType extends ReimuType {

	public static ArrayType UNKNOWN_ARRAY_TYPE = new ArrayType(SpecialType.UNKNOWN); 
	public static ArrayType STRING_TYPE = new ArrayType(PrimitiveType.CHAR); 
	public static IterableType NUMERIC_ITERATOR_TYPE = new IterableType(PrimitiveType.INT); 
}
	
