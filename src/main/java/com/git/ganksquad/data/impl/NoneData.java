package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.BooleanEvaluable;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;
import com.git.ganksquad.data.types.ReimuType;
import com.git.ganksquad.data.types.SpecialType;

/**
 * Represents a null value, which there exists only one.
 */
public class NoneData implements Data, BooleanEvaluable {
	
	public static final NoneData instance = new NoneData();
	
	protected NoneData() {}
	
	@Override
	public boolean evalAsBool() {
		return false;
	}

	@Override
	public int getClassKey() {

		return ClassKeys.NONE_DATA;
	}
	
	@Override
	public String toString() {
		return "None";
	}
	
	@Override
	public ReimuType getType() {
		return SpecialType.VOID;
	}
	
	@Override
	public Data castTo(ReimuType newType) {
		return this;
	}
}
