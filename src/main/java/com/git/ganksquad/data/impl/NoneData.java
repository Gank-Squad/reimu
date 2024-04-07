package com.git.ganksquad.data.impl;

import com.git.ganksquad.data.BooleanEvaluatable;
import com.git.ganksquad.data.ClassKeys;
import com.git.ganksquad.data.Data;

public class NoneData implements Data, BooleanEvaluatable {
	
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
}
